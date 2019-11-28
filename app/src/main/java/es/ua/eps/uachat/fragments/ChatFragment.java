package es.ua.eps.uachat.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.stfalcon.chatkit.messages.MessageInput;
import com.stfalcon.chatkit.messages.MessagesList;
import com.stfalcon.chatkit.messages.MessagesListAdapter;

import java.util.List;

import es.ua.eps.uachat.R;
import es.ua.eps.uachat.connection.base.data.ChatMessage;
import es.ua.eps.uachat.connection.base.data.ChatMessageListRequest;
import es.ua.eps.uachat.connection.base.data.ChatUser;

/**
 * Este fragment permite recibir y enviar mensajes al servidor, para chatear con otro usuario.
 * Cuando se muestra el fragment, pediremos una lista al servidor con los mensajes pendientes de leer.
 * Luego, escucharemos nuevos mensajes, y gestionamos el envío de mensajes.
 */
public class ChatFragment extends BaseChatConnectionFragment {
    private static final String ARG_CHAT_USER_ID = "ARG_CHAT_USER_ID";
    private static final String ARG_CHAT_USER_NAME = "ARG_CHAT_USER_NAME";

    private ChatUser mDstUser;

    private MessagesList mMessagesList;
    private MessageInput mMessageInput;
    private MessagesListAdapter<ChatMessage> mMessagesAdapter;
    private long mLastMessageTimestamp = 0;

    public ChatFragment() {
       // Los fragments necesitan un constructor vacío público
    }

    // Los fragments hay que instanciarlos solo usando el constructor vacío, nunca con parámetros,
    // porque a veces Android re-crea un fragment previamente destruido (pero manteniendo el Bundle enviado con setArguments)
    // Así que si tenemos que pasar parámetros, los añadiremos a este método, y éste los pasa
    // al fragment con un Bundle, mediante setArguments. Y se recogen en onCreate.
    public static ChatFragment newInstance(String chatUserId, String chatUserName) {
        Bundle args = new Bundle();
        args.putString(ARG_CHAT_USER_ID, chatUserId);
        args.putString(ARG_CHAT_USER_NAME, chatUserName);

        ChatFragment fragment = new ChatFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            String id = getArguments().getString(ARG_CHAT_USER_ID);
            String user = getArguments().getString(ARG_CHAT_USER_NAME);

            mDstUser = new ChatUser(id, user);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);

        mMessagesList = view.findViewById(R.id.messagesList);
        mMessageInput = view.findViewById(R.id.input);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mMessagesAdapter = new MessagesListAdapter<>(getUser().getId(), null);
        mMessagesList.setAdapter(mMessagesAdapter);
        mMessageInput.setInputListener(mInputListener);
    }

    @Override
    public void onResumeLoggedIn() {
        // Al mostrar el fragment pedimos los mensajes anteriores al servidor
        ChatMessageListRequest request = new ChatMessageListRequest(getUser().getId(), mDstUser.getId(), mLastMessageTimestamp);
        getChatConnection().requestMessageList(request);
    }

    // Listener del campo input para enviar mensajes
    private MessageInput.InputListener mInputListener = new MessageInput.InputListener() {
        @Override
        public boolean onSubmit(CharSequence input) {
            ChatMessage message = new ChatMessage(getUser(), mDstUser, input.toString());
            mMessagesAdapter.addToStart(message, true);
            getChatConnection().sendMessage(message);

            return true;
        }
    };

    // Este método se ejecuta cuando recibo la lista de mensajes pendientes del usuario con el que estoy hablando
    @Override
    public void onMessageListReceived(final List<ChatMessage> messages) {
        super.onMessageListReceived(messages);

        // Compruebo que la persona con la que estoy hablando sea el destinatario o el emisor del mensaje
        if (messages.size() == 0 ||
                (!messages.get(0).getUser().getId().equals(getDstUserId()) &&
                 !messages.get(0).getDstUser().getId().equals(getDstUserId())
                ) ||
                getActivity() == null || !isAdded()) return;

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                for (int i=0; i<messages.size(); i++) {
                    addMessageToList(messages.get(i));
                }
            }
        });
    }

    // Eeste método se ejecuta cuando cualquier usuario me envía un mensaje
    @Override
    public void onMessageReceived(final ChatMessage message) {
        super.onMessageReceived(message);

        // Si el mensaje que recibo no es del usuario con el que estoy hablando, no lo muestro
        if (!message.getUser().getId().equals(getDstUserId()) || getActivity() == null || !isAdded()) return;

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                addMessageToList(message);
            }
        });
    }

    private void addMessageToList(ChatMessage message) {
        mMessagesAdapter.addToStart(message, true);
        if (message.getTimestamp() > mLastMessageTimestamp) {
            mLastMessageTimestamp = message.getTimestamp();
        }
    }

    public String getDstUserId() {
        return getArguments() == null ? "" : getArguments().getString(ARG_CHAT_USER_ID);
    }

    public String getDstUserName() {
        return getArguments() == null ? "" :  getArguments().getString(ARG_CHAT_USER_NAME);
    }
}
