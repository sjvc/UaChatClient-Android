package es.ua.eps.uachat.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import es.ua.eps.uachat.R;
import es.ua.eps.uachat.connection.base.data.ChatUser;

/*
    Adapter para mostrar los usuarios conectados al chat en una lista.
    Usa el patrón ViewHolder para reciclar las vistas y mejorar el rendimiento.
 */

public class UserArrayAdapter extends ArrayAdapter<ChatUser> {

    public UserArrayAdapter(@NonNull Context context, List<ChatUser> users) {
        super(context, 0, users); // El segundo argumento es solo para cuando el item tiene un solo TextView
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ChatUser user = getItem(position);

        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_user, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.mNameTextView = convertView.findViewById(R.id.userNameTextView);
            viewHolder.mStatusImageView = convertView.findViewById(R.id.userConnectionStatusImageView);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.mNameTextView.setText(user.getName());
        viewHolder.mStatusImageView.setImageResource(user.isConnected() ? R.drawable.user_connected : R.drawable.user_disconnected);


        return super.getView(position, convertView, parent);
    }

    private static class ViewHolder {
        TextView mNameTextView;
        ImageView mStatusImageView;
    }
}
