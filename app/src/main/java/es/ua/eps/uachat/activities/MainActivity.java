package es.ua.eps.uachat.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AlertDialog;
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

/**
    Esta activity realiza la conexión al servidor, y posteriormente el login (enviar mis datos de usuario).
    Desde que se realiza la conexión, hasta que recibimos el callback del servidor indicando que se ha
    hecho el login, la interfaz estará bloqueada porque estaremos mostrando mLoadingLayout.
    Una vez que el usuario está logueado, gestionamos qué fragment se mostrará en cada momento:
    inicialmente se mostrará la lista de usuarios del chat, y al seleccionar uno, mostraremos
    el fragment de chat con el usuario seleccionado.
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

        // Si es la primera vez que se usa la app (no sabemos el nombre del usuario)
        // entonces mostramos la pantalla de bienvenida, de donde no saldrá hasta que nos diga su nombre
        if (SharedPrefs.getUserName(MainActivity.this) == null) {
            startActivity(new Intent(MainActivity.this, WelcomeActivity.class));
        // Cuando ya tenemos su nombre, procedemos a conectar bloqueando mientras el acceso a la app
        // mostrando mLoadingLayout, y esperando a recibir el callback onConnected
        } else {
            mLoadingLayout.setVisibility(View.VISIBLE);
            mConnection.addListener(this);
            mConnection.connect();
        }
    }

    @Override
    protected void onPause() {
        mConnection.removeListener(this);
        mConnection.disconnect();

        super.onPause();
    }

    @Override
    public void onConnected() {
        // Una vez conectados, nos logueamos en el servidor, y esperamos a onLoggedIn
        mConnection.login(mUser);
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
                // Una vez que el usuario se ha conectado y logueado desbloqueamos el acceso
                mLoadingLayout.setVisibility(View.GONE);

                // Y mostramos el fragment que estuviera visible anteriormente (la lista de usuarios si no hubiera ninguno)
                BaseChatConnectionFragment currentFragment = getCurrentFragment();
                showFragment(currentFragment != null ? currentFragment : UserListFragment.newInstance());
            }
        });
    }

    @Override
    public void onBanned() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new AlertDialog.Builder(MainActivity.this)
                    .setTitle("BOOM!")
                    .setMessage("Te has portado mal. Has sido baneado del servidor")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            MainActivity.this.finish();
                        }
                    }).show();
            }
        });
    }

    @Override
    public void onDisconnected() {

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
        // Al pulsar en un usuario de la lista, mostramos el chat con ese usuario
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

    // Obtiene el fragment que se está mostrando ahora mismo
    private BaseChatConnectionFragment getCurrentFragment() {
        return (BaseChatConnectionFragment)getSupportFragmentManager().findFragmentById(R.id.fragment_layout);
    }
}
