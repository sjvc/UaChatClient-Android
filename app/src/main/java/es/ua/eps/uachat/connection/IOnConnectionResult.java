package es.ua.eps.uachat.connection;

public interface IOnConnectionResult {
    void onConnected();
    void onError();
    void onTimeOut();
}
