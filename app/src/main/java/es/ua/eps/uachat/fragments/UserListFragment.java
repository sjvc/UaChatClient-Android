package es.ua.eps.uachat.fragments;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import es.ua.eps.uachat.R;
import es.ua.eps.uachat.adapters.UserArrayAdapter;
import es.ua.eps.uachat.connection.base.data.ChatUser;

/**
 * Este fragment muestra la lista de usuarios del chat, indicando si están o no conectados.
 * Cuando se muestra este fragment comienza a realizar peticiones de la lista de usuarios
 * cada REQUEST_LIST_INTERVAL_MILLIS milisegundos, de forma que siempre esté actualizada.
 */
public class UserListFragment extends BaseChatConnectionFragment implements AdapterView.OnItemClickListener {
    private final static int REQUEST_LIST_INTERVAL_MILLIS = 5000;

    private ListView mListView;
    private TextView mEmptyListTextView;
    private UserArrayAdapter mListAdapter;
    private Handler mHandler;
    private OnUserListInteractionListener mListener;

    public UserListFragment() {
        // Los fragments necesitan un constructor vacío público
    }

    // Los fragments hay que instanciarlos solo usando el constructor vacío, nunca con parámetros,
    // porque a veces Android re-crea un fragment previamente destruido (pero manteniendo el Bundle enviado con setArguments)
    // Así que si tenemos que pasar parámetros, los añadiremos a este método, y éste los pasa
    // al fragment con un Bundle, mediante setArguments. Y se recogen en onCreate.
    public static UserListFragment newInstance() {
        return new UserListFragment();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof OnUserListInteractionListener) {
            mListener = (OnUserListInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnUserListInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();

        mListener = null;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_list, container, false);

        mListView = view.findViewById(R.id.usersListView);
        mEmptyListTextView = view.findViewById(R.id.emptyListTextView);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mHandler = new Handler();
        mListView.setOnItemClickListener(this);
    }

    @Override
    public void onResumeLoggedIn() {
        // Al mostrarse el fragment empezamos a pedir la lista de usuarios periódicamente
        mRequestUserListRunnable.run();
    }

    @Override
    public void onPause() {
        super.onPause();

        // Al ocultar el fragment, dejamos de pedir la lista al servidor
        mHandler.removeCallbacks(mRequestUserListRunnable);
    }

    // Pide la lista de usuarios al servidor
    private Runnable mRequestUserListRunnable = new Runnable() {
        @Override
        public void run() {
            if (isAdded()) {
                getChatConnection().requestUserList();
            }
        }
    };

    @Override
    public void onUserListReceived(final List<ChatUser> users) {
        super.onUserListReceived(users);

        if (getActivity() == null) return;

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // Si cuando se ejecuta este callback, ya no está activo el fragment, no hacemos nada
                if (!isAdded() || getContext() == null) return;

                // Mostrar usuarios en la lista
                if (mListAdapter == null) {
                    mListAdapter = new UserArrayAdapter(getContext(), users);
                    mListView.setAdapter(mListAdapter);
                } else {
                    mListAdapter.clear();
                    mListAdapter.addAll(users);
                    mListAdapter.notifyDataSetChanged();
                }

                mListView.setVisibility(users.size() > 0 ? View.VISIBLE : View.GONE);
                mEmptyListTextView.setVisibility(users.size() == 0 ? View.VISIBLE : View.GONE);

                // Programar siguiente petición de lista para que la lista siempre esté actualizada
                mHandler.postDelayed(mRequestUserListRunnable, REQUEST_LIST_INTERVAL_MILLIS);
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        if (mListener != null) {
            mListener.onUserListItemClick( mListAdapter.getItem(i) );
        }
    }

    // Interfaz para comunicarnos con la activity que esté usando este fragment
    public interface OnUserListInteractionListener {
        void onUserListItemClick(ChatUser user);
    }
}
