package es.ua.eps.uachat.activities;


import android.os.Bundle;
import android.widget.EditText;

import com.heinrichreimersoftware.materialintro.app.IntroActivity;
import com.heinrichreimersoftware.materialintro.app.NavigationPolicy;
import com.heinrichreimersoftware.materialintro.slide.FragmentSlide;
import com.heinrichreimersoftware.materialintro.slide.SimpleSlide;

import es.ua.eps.uachat.R;
import es.ua.eps.uachat.UaChatApplication;
import es.ua.eps.uachat.persistence.SharedPrefs;

/**
    Mostramos esta Activity al usuario, nada más arrancar la aplicación, la primera vez que la abre.
    De esta forma, le presentamos las características de la misma, y le preguntamos su nombre.
 */

public class WelcomeActivity extends IntroActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Añadimos las slides
        addSlide(new SimpleSlide.Builder().image(R.drawable.ic_chat_alert)     .title(R.string.welcome_title_01).description(R.string.welcome_desc_01).background(R.color.colorPrimary).backgroundDark(R.color.colorPrimaryDark).build());
        addSlide(new SimpleSlide.Builder().image(R.drawable.ic_brush)          .title(R.string.welcome_title_02).description(R.string.welcome_desc_02).background(R.color.colorPrimary).backgroundDark(R.color.colorPrimaryDark).build());
        addSlide(new SimpleSlide.Builder().image(R.drawable.ic_code_braces_box).title(R.string.welcome_title_03).description(R.string.welcome_desc_03).background(R.color.colorPrimary).backgroundDark(R.color.colorPrimaryDark).build());
        addSlide(new SimpleSlide.Builder().image(R.drawable.ic_account_group)  .title(R.string.welcome_title_04).description(R.string.welcome_desc_04).background(R.color.colorPrimary).backgroundDark(R.color.colorPrimaryDark).build());
        addSlide(new SimpleSlide.Builder().image(R.drawable.ic_lock_question)  .title(R.string.welcome_title_05).description(R.string.welcome_desc_05).background(R.color.colorPrimary).backgroundDark(R.color.colorPrimaryDark).build());
        addSlide(new SimpleSlide.Builder().image(R.drawable.ic_account_off)    .title(R.string.welcome_title_06).description(R.string.welcome_desc_06).background(R.color.colorPrimary).backgroundDark(R.color.colorPrimaryDark).build());
        addSlide(new SimpleSlide.Builder().image(R.drawable.ic_account_alert)  .title(R.string.welcome_title_07).description(R.string.welcome_desc_07).background(R.color.colorPrimary).backgroundDark(R.color.colorPrimaryDark).build());
        final FragmentSlide userNameFragmentSlide = new FragmentSlide.Builder().background(R.color.colorPrimary).backgroundDark(R.color.colorPrimaryDark).fragment(R.layout.fragment_welcome_user_name, R.style.SlideFragmentTheme).build();
        addSlide(userNameFragmentSlide);

        // El botón de la izquierda servirá para volver al Slide anterior
        setButtonBackFunction(BUTTON_BACK_FUNCTION_BACK);

        // No dejamos continuar si no se ha introducido un nombre
        // Y si se ha introducido, lo guardamos en el objeto ChatUser y en SharedPreferences para que quede guardado
        setNavigationPolicy(new NavigationPolicy() {
            @Override
            public boolean canGoForward(int position) {
                if (getSlide(position) == userNameFragmentSlide) {
                    EditText editText = userNameFragmentSlide.getFragment().getView().findViewById(R.id.userNameEditText);

                    if (editText.getText().length() > 0) {
                        ((UaChatApplication)getApplication()).getChatUser().setName(editText.getText().toString());
                        SharedPrefs.setUserName(WelcomeActivity.this, editText.getText().toString());
                    }

                    return editText.getText().length() > 0;
                }

                return true;
            }

            @Override
            public boolean canGoBackward(int position) {
                return true;
            }
        });
    }
}
