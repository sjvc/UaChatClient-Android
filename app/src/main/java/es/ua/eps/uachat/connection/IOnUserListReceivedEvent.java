package es.ua.eps.uachat.connection;

import es.ua.eps.uachat.serverdata.User;

public interface IOnUserListReceivedEvent {
    void onUserListReceived(User[] list);
}
