package es.ua.eps.uachat;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import es.ua.eps.uachat.connection.IChatConnection;
import es.ua.eps.uachat.connection.IOnConnectionResult;
import es.ua.eps.uachat.connection.SocketIoChatConnection;
import es.ua.eps.uachat.serverdata.User;

public class MainActivity extends AppCompatActivity {
    private IChatConnection mConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mConnection = ((UaChatApplication)getApplication()).getChatConnection();

        mConnection.connect(new User(mConnection.getClientId(), "Pepito"), new IOnConnectionResult() {
            @Override
            public void onConnected() {

            }

            @Override
            public void onError() {

            }

            @Override
            public void onTimeOut() {

            }
        });
    }
}
