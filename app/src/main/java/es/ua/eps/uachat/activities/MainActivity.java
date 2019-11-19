package es.ua.eps.uachat.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import es.ua.eps.uachat.R;
import es.ua.eps.uachat.UaChatApplication;
import es.ua.eps.uachat.connection.base.IChatConnection;
import es.ua.eps.uachat.connection.base.IChatConnectionListener;
import es.ua.eps.uachat.connection.base.data.ChatMessage;
import es.ua.eps.uachat.connection.base.data.ChatUser;
import es.ua.eps.uachat.fragments.BaseChatConnectionFragment;
import es.ua.eps.uachat.fragments.ChatFragment;
import es.ua.eps.uachat.fragments.UserListFragment;
import es.ua.eps.uachat.persistence.SharedPrefs;

/*
    Esta activity realiza la conexión al servidor, y loguea al usuario.
    Una vez que el usuario se ha logueado, muestra un fragment con la lista de usuarios.
    Al hacer click sobre cualquier usuario, se muestra el fragment del chat con ese usuario.
    Ambos fragments (lista de usuarios y chat) implementan IChatConnectionListener, por lo que
    cuando mostremos uno de los fragments, estableceremos ese fragment con listener de eventos de
    la conexión (IChatConnection) para que sean gestionados por ese fragment.
 */

public class MainActivity extends AppCompatActivity implements IChatConnectionListener, UserListFragment.OnUserListInteractionListener {
    private IChatConnection mConnection;
    private ChatUser mUser;

    private ViewGroup mLoadingLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        mConnection = ((UaChatApplication)getApplication()).getChatConnection();
        mUser = ((UaChatApplication)getApplication()).getChatUser();

        mLoadingLayout = findViewById(R.id.loading_layout);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (SharedPrefs.getUserName(MainActivity.this) == null) {
            startActivity(new Intent(MainActivity.this, WelcomeActivity.class));
        } else {
            mLoadingLayout.setVisibility(View.VISIBLE);
            mConnection.addListener(this);
            mConnection.connect(mUser);
        }
    }

    @Override
    protected void onPause() {
        mConnection.removeListener(this);
        mConnection.disconnect();
        onDisconnected(); // Llamo yo al método del listener porque no quiero esperar a que el servidor me avise que he desconectado, y además ya he quitado el listener arriba

        super.onPause();
    }

    @Override
    public void onConnected() {

    }

    @Override
    public void onConnectionError() {

    }

    @Override
    public void onConnectionTimeOut() {

    }

    @Override
    public void onLoggedIn() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mLoadingLayout.setVisibility(View.GONE);

                BaseChatConnectionFragment currentFragment = getCurrentFragment();
                showFragment(currentFragment != null ? currentFragment : UserListFragment.newInstance());
            }
        });
    }

    @Override
    public void onDisconnected() {
        // Si me desconecto del servidor, quito el fragment que esté activo
        removeCurrentFragment();
    }

    @Override
    public void onMessageListReceived(List<ChatMessage> messages) {

    }

    @Override
    public void onMessageReceived(ChatMessage message) {

    }

    @Override
    public void onUserListReceived(List<ChatUser> users) {

    }

    @Override
    public void onUserListItemClick(ChatUser user) {
        ChatFragment fragment = ChatFragment.newInstance(user.getId(), user.getName());
        showFragment(fragment);
    }

    @Override
    public void onBackPressed() {
        // Al pulsar atrás, si estamos en el chat, volvemos a la lista
        if (getCurrentFragment() instanceof ChatFragment) {
            showFragment(UserListFragment.newInstance());
        } else {
            super.onBackPressed();
        }
    }

    // Muestra el fragment pasado como parámetro, y lo asigna como listener de los eventos de la conexión.
    // Si ya se estaba mostrando dicho fragment, simplemente lo asignamos como listener de los eventos.
    private void showFragment(BaseChatConnectionFragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_layout, fragment).commit();
    }

    private void removeCurrentFragment() {
        BaseChatConnectionFragment currentFragment = getCurrentFragment();

        if (currentFragment != null) {
            getSupportFragmentManager().beginTransaction().remove(getCurrentFragment()).commit();
        }
    }

    private BaseChatConnectionFragment getCurrentFragment() {
        return (BaseChatConnectionFragment)getSupportFragmentManager().findFragmentById(R.id.fragment_layout);
    }
}
