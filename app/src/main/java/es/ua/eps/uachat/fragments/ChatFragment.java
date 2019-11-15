package es.ua.eps.uachat.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.stfalcon.chatkit.messages.MessagesList;
import com.stfalcon.chatkit.messages.MessagesListAdapter;

import java.util.List;

import es.ua.eps.uachat.R;
import es.ua.eps.uachat.connection.base.data.ChatMessage;
import es.ua.eps.uachat.connection.base.data.ChatMessageListRequest;
import es.ua.eps.uachat.connection.base.data.ChatUser;

public class ChatFragment extends BaseChatConnectionFragment {
    private static final String ARG_CHAT_USER_ID = "ARG_CHAT_USER_ID";
    private static final String ARG_CHAT_USER_NAME = "ARG_CHAT_USER_NAME";

    private ChatUser mDstUser;

    private MessagesList mMessagesList;
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

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mMessagesAdapter = new MessagesListAdapter<>(mDstUser.getId(), null);
        mMessagesList.setAdapter(mMessagesAdapter);
    }

    @Override
    public void onStartLoggedIn() {
        // Al entrar al fragment pedimos la lista de mensajes pendientes al servidor
        ChatMessageListRequest request = new ChatMessageListRequest(getUser().getId(), mDstUser.getId(), mLastMessageTimestamp);
        getChatConnection().requestMessageList(request);
    }

    @Override
    public void onMessageListReceived(List<ChatMessage> messages) {
        mMessagesAdapter.addToEnd(messages, false);
    }

    @Override
    public void onMessageReceived(ChatMessage message) {
        super.onMessageReceived(message);

        mMessagesAdapter.addToStart(message, true);
        if (message.getTimestamp() > mLastMessageTimestamp) {
            mLastMessageTimestamp = message.getTimestamp();
        }
    }
}
