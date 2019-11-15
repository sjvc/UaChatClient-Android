package es.ua.eps.uachat.activities;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import es.ua.eps.uachat.R;
import es.ua.eps.uachat.UaChatApplication;
import es.ua.eps.uachat.connection.base.IChatConnection;
import es.ua.eps.uachat.connection.base.IChatConnectionListener;
import es.ua.eps.uachat.connection.base.data.ChatMessage;
import es.ua.eps.uachat.connection.base.data.ChatUser;
import es.ua.eps.uachat.fragments.BaseChatConnectionFragment;
import es.ua.eps.uachat.fragments.ChatFragment;
import es.ua.eps.uachat.fragments.UserListFragment;

public class MainActivity extends AppCompatActivity implements IChatConnectionListener {
    private IChatConnection mConnection;
    private ChatUser mUser;

    private UserListFragment mUserListFragment;
    private ChatFragment mChatFragment;
    private BaseChatConnectionFragment mLastFragment;

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

        mConnection.setChatConnectionListener(this);
        mConnection.connect(mUser);
    }

    @Override
    protected void onStop() {
        mLastFragment = getCurrentFragment();

        mConnection.setChatConnectionListener(null);
        mConnection.disconnect();

        super.onStop();
    }

    @Override
    public void onConnected() {
        mLoadingLayout.setVisibility(View.INVISIBLE);

        BaseChatConnectionFragment fragment = mLastFragment != null ? mLastFragment : mUserListFragment;
        showFragment(fragment);
    }

    @Override
    public void onConnectionError() {

    }

    @Override
    public void onConnectionTimeOut() {

    }

    @Override
    public void onMessageListReceived(ChatMessage[] list) {

    }

    @Override
    public void onMessageReceived(ChatMessage message) {

    }

    @Override
    public void onUserListReceived(ChatUser[] list) {

    }

    private void showChatFragment(String userId, String userName) {
        ChatFragment fragment = ChatFragment.newInstance(userId, userName);
        showFragment(fragment);
    }

    private void showFragment(BaseChatConnectionFragment fragment) {
        mConnection.setChatConnectionListener(fragment);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_layout, fragment).commit();
    }

    private BaseChatConnectionFragment getCurrentFragment() {
        return (BaseChatConnectionFragment)getSupportFragmentManager().findFragmentById(R.id.fragment_layout);
    }
}
