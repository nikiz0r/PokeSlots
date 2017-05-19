package com.fiap.rumenigue.slotmachine;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MenuActivity extends AppCompatActivity {

    private Button btnPlay, btnAbout;
    private TextView tvLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/unown.ttf");
        Typeface pokeFont = Typeface.createFromAsset(getAssets(), "fonts/poke.ttf");

        tvLogo = (TextView)findViewById(R.id.tvLogo);
        btnPlay = (Button)findViewById(R.id.btnPlay);
        btnAbout = (Button)findViewById(R.id.btnAbout);

        tvLogo.setTypeface(pokeFont);
        btnPlay.setTypeface(font);
        btnAbout.setTypeface(font);
    }

    public void play(View view) {
        Intent intent = new Intent(MenuActivity.this, PlayerActivity.class);
        startActivity(intent);
    }

    public void about(View view) {
        Intent intent = new Intent(MenuActivity.this, AboutActivity.class);
        startActivity(intent);
    }
}
