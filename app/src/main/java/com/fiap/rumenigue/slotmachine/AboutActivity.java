package com.fiap.rumenigue.slotmachine;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/poke.ttf");
        TextView tvLogo = (TextView) findViewById(R.id.tvLogo);
        tvLogo.setTypeface(font);
    }
}
