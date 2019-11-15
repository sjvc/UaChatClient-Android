package es.ua.eps.uachat.persistence;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefs {
    private final static String FILE_NAME = "uaChatPrefs";
    private final static String KEY_USER_NAME = "userName";

    private static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
    }

    public static String getUserName(Context context) {
        return getSharedPreferences(context).getString(KEY_USER_NAME, null);
    }

    public static void setUserName(Context context, String value) {
        getSharedPreferences(context).edit().putString(KEY_USER_NAME, value).apply();
    }
}
