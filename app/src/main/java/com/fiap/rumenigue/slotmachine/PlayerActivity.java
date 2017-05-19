package com.fiap.rumenigue.slotmachine;

import android.content.Intent;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class PlayerActivity extends AppCompatActivity {

    private RadioGroup rgPoke;
    private EditText etPlayerName;
    private Spinner spChipsAmount;

    private int pokeSelected = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/unown.ttf");
        Typeface pokeFont = Typeface.createFromAsset(getAssets(), "fonts/poke.ttf");

        TextView tvLogo = (TextView) findViewById(R.id.tvLogo);
        Button btnPlay = (Button) findViewById(R.id.btnPlay);
        rgPoke = (RadioGroup)findViewById(R.id.rgPoke);
        etPlayerName = (EditText)findViewById(R.id.etPlayerName);
        spChipsAmount = (Spinner)findViewById(R.id.spChipsAmount);

        // estilo do spinner
        ArrayAdapter adapter = ArrayAdapter.createFromResource(
                this, R.array.chips_options, R.layout.spinner_textcolor);
        adapter.setDropDownViewResource(R.layout.spinner_dropdownitem);
        spChipsAmount.setAdapter(adapter);

        tvLogo.setTypeface(pokeFont);
        btnPlay.setTypeface(font);

        rgPoke.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                int rawID = R.raw.bulbasaur_cry;

                switch (checkedId){
                    case R.id.rbCharmander:
                        rawID = R.raw.charmander_cry;
                        break;
                    case R.id.rbSquirtle:
                        rawID = R.raw.squirtle_cry;
                        break;
                    default:
                        break;
                }

                MediaPlayer player =
                        MediaPlayer.create(PlayerActivity.this, rawID);
                player.start();
            }
        });

        if (savedInstanceState != null){
            etPlayerName.setText(
                    savedInstanceState.getString(getString(R.string.TAG_NAME)));
            rgPoke.check(
                    savedInstanceState.getInt(getString(R.string.TAG_POKE)));
            spChipsAmount.setSelection(
                    savedInstanceState.getInt(getString(R.string.TAG_CHIP)));
        }
    }

    public void play(View view) {
        pokeSelected = rgPoke.getCheckedRadioButtonId();

        switch(pokeSelected){
            case R.id.rbBulbasaur:
                pokeSelected = 0;
                break;
            case R.id.rbSquirtle:
                pokeSelected = 1;
                break;
            case R.id.rbCharmander:
                pokeSelected = 2;
                break;
            default:
                pokeSelected = -1;
                break;
        }

        if(validateFields()){
            int chipAmount = Integer.parseInt(spChipsAmount.getSelectedItem().toString());

            MediaPlayer player = MediaPlayer.create(this, R.raw.play);
            player.start();

            Intent intent = new Intent(PlayerActivity.this, GameActivity.class);
            intent.putExtra("CHIP_AMOUNT", chipAmount);
            intent.putExtra("POKE_SELECTED", pokeSelected);
            intent.putExtra("NAME", etPlayerName.getText().toString());

            startActivity(intent);
        }else{
            Toast.makeText(this, R.string.fillForm, Toast.LENGTH_SHORT).show();
        }
    }

    private boolean validateFields() {
        if (pokeSelected == -1) return false;

        if (spChipsAmount.getSelectedItem().toString().equals(
                getResources().getString(R.string.chips_amount)))
            return false;

        if (etPlayerName.getText().toString().isEmpty()) return false;

        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString(getString(R.string.TAG_NAME),
                etPlayerName.getText().toString());
        outState.putInt(getString(R.string.TAG_POKE),
                rgPoke.getCheckedRadioButtonId());
        outState.putInt(getString(R.string.TAG_CHIP),
                spChipsAmount.getSelectedItemPosition());
    }
}
