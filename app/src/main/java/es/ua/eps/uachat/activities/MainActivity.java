package es.ua.eps.uachat.activities;

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

public class MainActivity extends AppCompatActivity implements IChatConnectionListener, UserListFragment.OnUserListInteractionListener {
    private IChatConnection mConnection;
    private ChatUser mUser;

    private UserListFragment mUserListFragment;

    private ViewGroup mLoadingLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        mConnection = ((UaChatApplication)getApplication()).getChatConnection();
        mUser = ((UaChatApplication)getApplication()).getChatUser();

        mUserListFragment = UserListFragment.newInstance();
        mLoadingLayout = findViewById(R.id.loading_layout);
    }

    @Override
    protected void onStart() {
        super.onStart();

        mLoadingLayout.setVisibility(View.VISIBLE);
        mConnection.connect(mUser);
    }

    @Override
    protected void onStop() {
        mConnection.setChatConnectionListener(null);
        mConnection.disconnect();

        super.onStop();
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
        mLoadingLayout.setVisibility(View.INVISIBLE);

        BaseChatConnectionFragment currentFragment = getCurrentFragment();
        showFragment(currentFragment != null ? currentFragment : mUserListFragment);
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

    private void showFragment(BaseChatConnectionFragment fragment) {
        BaseChatConnectionFragment currentFragment = getCurrentFragment();

        if (currentFragment != fragment) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_layout, fragment).commit();
        }

        mConnection.setChatConnectionListener(fragment);
        fragment.onStartLoggedIn();
    }

    private BaseChatConnectionFragment getCurrentFragment() {
        return (BaseChatConnectionFragment)getSupportFragmentManager().findFragmentById(R.id.fragment_layout);
    }
}
