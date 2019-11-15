package es.ua.eps.uachat.activities;


import android.os.Bundle;

import com.heinrichreimersoftware.materialintro.app.IntroActivity;
import com.heinrichreimersoftware.materialintro.slide.SimpleSlide;

import es.ua.eps.uachat.R;

public class WelcomeActivity extends IntroActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addSlide(new SimpleSlide.Builder().image(R.drawable.ic_chat_alert)     .title(R.string.welcome_title_01).description(R.string.welcome_desc_01).background(R.color.colorPrimary).backgroundDark(R.color.colorPrimaryDark).build());
        addSlide(new SimpleSlide.Builder().image(R.drawable.ic_brush)          .title(R.string.welcome_title_02).description(R.string.welcome_desc_02).background(R.color.colorPrimary).backgroundDark(R.color.colorPrimaryDark).build());
        addSlide(new SimpleSlide.Builder().image(R.drawable.ic_code_braces_box).title(R.string.welcome_title_03).description(R.string.welcome_desc_03).background(R.color.colorPrimary).backgroundDark(R.color.colorPrimaryDark).build());
        addSlide(new SimpleSlide.Builder().image(R.drawable.ic_account_group)  .title(R.string.welcome_title_04).description(R.string.welcome_desc_04).background(R.color.colorPrimary).backgroundDark(R.color.colorPrimaryDark).build());
        addSlide(new SimpleSlide.Builder().image(R.drawable.ic_lock_question)  .title(R.string.welcome_title_05).description(R.string.welcome_desc_05).background(R.color.colorPrimary).backgroundDark(R.color.colorPrimaryDark).build());
        addSlide(new SimpleSlide.Builder().image(R.drawable.ic_account_off)    .title(R.string.welcome_title_06).description(R.string.welcome_desc_06).background(R.color.colorPrimary).backgroundDark(R.color.colorPrimaryDark).build());
        addSlide(new SimpleSlide.Builder().image(R.drawable.ic_account_alert)  .title(R.string.welcome_title_07).description(R.string.welcome_desc_07).background(R.color.colorPrimary).backgroundDark(R.color.colorPrimaryDark).build());

        setButtonBackFunction(BUTTON_BACK_FUNCTION_BACK);
    }
}
