package com.fiap.rumenigue.slotmachine;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class GameActivity extends AppCompatActivity {

    private ImageView ivSlot1, ivSlot2, ivSlot3;
    private Wheel slot1, slot2, slot3;
    private Button btnPlay;
    private TextView tvChips;
    private Chronometer crTime;

    public static final Random RANDOM = new Random();
    public static long randomLong(long lower, long upper){
        return lower + (long)(RANDOM.nextDouble() * (upper - lower));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        TextView tvPlayingAs = (TextView) findViewById(R.id.tvPlayingAs);
        ImageView ivPlayingAs = (ImageView) findViewById(R.id.ivPlayingAs);
        View rlGame = findViewById(R.id.rlGame);
        ivSlot1 = (ImageView)findViewById(R.id.ivSlot1);
        ivSlot2 = (ImageView)findViewById(R.id.ivSlot2);
        ivSlot3 = (ImageView)findViewById(R.id.ivSlot3);
        btnPlay = (Button)findViewById(R.id.btnPlay);
        tvChips = (TextView)findViewById(R.id.tvChips);
        crTime = (Chronometer) findViewById(R.id.crTime);

        crTime.start();

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                play(v);
            }
        });

        if (getIntent() != null){
            String pokeName = "";
            int pokeImg = 0, bgImage = 0;

            switch (getIntent().getIntExtra("POKE_SELECTED", 0)){
                case 0:
                    pokeName = getResources().getString(R.string.p_bulbasaur);
                    pokeImg = R.drawable.bulbasaur;
                    bgImage = R.drawable.w_bulba;
                    break;
                case 1:
                    pokeName = getResources().getString(R.string.p_squirtle);
                    pokeImg = R.drawable.squirtle;
                    bgImage = R.drawable.w_squir;
                    break;
                case 2:
                    pokeName = getResources().getString(R.string.p_charmander);
                    pokeImg = R.drawable.charmander;
                    bgImage = R.drawable.w_charm;
                    break;
            }

            tvChips.setText(String.valueOf(getIntent().getIntExtra("CHIP_AMOUNT", 0)));

            String playingAsText = tvPlayingAs.getText().toString();

            playingAsText = playingAsText
                    .replace("{0}", getIntent().getStringExtra("NAME"))
                    .replace("{1}", pokeName);
            tvPlayingAs.setText(playingAsText);
            ivPlayingAs.setBackgroundResource(pokeImg);
            rlGame.setBackgroundResource(bgImage);
        }
    }

    public void play(View v){
        spinWheel1();
        spinWheel2();
        spinWheel3();

        MediaPlayer player = MediaPlayer.create(this, R.raw.wheel);
        player.start();

        btnPlay.setEnabled(false);
        btnPlay.setText(R.string.bt_spining);

        int chips = Integer.parseInt(tvChips.getText().toString());
        chips--;
        tvChips.setText(String.valueOf(chips));

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                slot1.stopSpinning();
                slot2.stopSpinning();
                slot3.stopSpinning();
                
                showResult();

                btnPlay.setEnabled(true);
                int chips = Integer.parseInt(tvChips.getText().toString());
                if (chips > 0){
                    btnPlay.setText(R.string.play);
                }else{
                    crTime.stop();
                    btnPlay.setText(R.string.game_over);
                    btnPlay.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(GameActivity.this, PlayerActivity.class);
                            startActivity(intent);
                        }
                    });
                }
            }
        }, 3000);
    }

    private void showResult() {
        int chips = Integer.parseInt(tvChips.getText().toString());

        if(slot1.currentIndex == slot2.currentIndex && slot2.currentIndex == slot3.currentIndex){
            Toast.makeText(this, R.string.you_won, Toast.LENGTH_SHORT).show();
            chips += 5;
            tvChips.setText(String.valueOf(chips));
        }else if(slot1.currentIndex == slot2.currentIndex
                || slot1.currentIndex == slot3.currentIndex
                || slot2.currentIndex == slot3.currentIndex){
            Toast.makeText(this, R.string.small_prize, Toast.LENGTH_SHORT).show();
            chips += 2;
            tvChips.setText(String.valueOf(chips));
        }else{
            Toast.makeText(this, R.string.you_lost, Toast.LENGTH_SHORT).show();
        }
    }

    private void spinWheel1() {
        slot1 = new Wheel(new Wheel.WheelListener(){
            @Override
            public void newImage(final int resourceID) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ivSlot1.setImageResource(resourceID);
                    }
                });
            }
        }, 200, randomLong(0, 200));
        slot1.start();
    }

    private void spinWheel2() {
        slot2 = new Wheel(new Wheel.WheelListener(){
            @Override
            public void newImage(final int resourceID) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ivSlot2.setImageResource(resourceID);
                    }
                });
            }
        }, 200, randomLong(150, 400));
        slot2.start();
    }

    private void spinWheel3() {
        slot3 = new Wheel(new Wheel.WheelListener(){
            @Override
            public void newImage(final int resourceID) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ivSlot3.setImageResource(resourceID);
                    }
                });
            }
        }, 200, randomLong(300, 600));
        slot3.start();
    }
}
