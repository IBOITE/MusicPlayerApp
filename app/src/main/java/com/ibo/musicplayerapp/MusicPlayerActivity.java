package com.ibo.musicplayerapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.IOException;

public class MusicPlayerActivity extends AppCompatActivity {

    TextView tvTime,tvDuration,tvTitile,tvArtist ;
    SeekBar seekBarVolum,seekBarTime;
    Button btnPlay;

    MediaPlayer  mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //hide the actionBar
//        getSupportActionBar().hide();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Song song=(Song) getIntent().getSerializableExtra("song");

        btnPlay=findViewById(R.id.btnPlay);
        seekBarVolum=findViewById(R.id.seekBarVolum);
        seekBarTime=findViewById(R.id.seekBarTime);
        tvTime=findViewById(R.id.tvTime);
        tvDuration=findViewById(R.id.tvDuration);
        tvTitile=findViewById(R.id.tvTitle);
        tvArtist=findViewById(R.id.tvArtist);

        tvArtist.setText(song.getArtist());
        tvTitile.setText(song.getTitle());

//        mediaPlayer=MediaPlayer.create(this,R.raw.fre_fd);
        mediaPlayer=new MediaPlayer();
        try {
            mediaPlayer.setDataSource(song.path);
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }

        mediaPlayer.setLooping(true);
        mediaPlayer.seekTo(0);
        mediaPlayer.setVolume(0.5f,0.5f);
//        mediaPlayer.start();

        tvDuration.setText(milliSecToStri(mediaPlayer.getDuration()));

        seekBarTime.setMax(mediaPlayer.getDuration());
        seekBarTime.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean isFromUser) {
                if(isFromUser){
                    mediaPlayer.seekTo(progress);
                    seekBar.setProgress(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        seekBarVolum.setProgress(50);
        seekBarVolum.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean isFromUser) {
                float volum=progress/100f;
                mediaPlayer.setVolume(volum,volum);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(view.getId()==R.id.btnPlay){
                    if(mediaPlayer.isPlaying()){
                        mediaPlayer.pause();
                        btnPlay.setBackgroundResource(R.drawable.ic_play);

                    }else {
                        mediaPlayer.start();
                        btnPlay.setBackgroundResource(R.drawable.ic_pause);

                    }
                }
            }
        });



        new Thread(new Runnable() {
            @Override
            public void run() {
                while (mediaPlayer!=null){
                    if(mediaPlayer.isPlaying()){

                        try {
                            final double current=mediaPlayer.getCurrentPosition();
                            final String elapsedTime=milliSecToStri((int) current);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    tvTime.setText(elapsedTime);
                                    seekBarTime.setProgress((int) current);
                                }
                            });
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }
                }
            }
        }).start();





    }


    public String milliSecToStri(int time){
        String elapsedTime="";
        int minutes=time/1000/60;
        int second=time/1000%60;
        elapsedTime+=minutes+":";
        if(second<10){
            elapsedTime+=0;
        }
        elapsedTime+=second;



        return elapsedTime;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            finish();
            if(mediaPlayer.isPlaying()){
                mediaPlayer.stop();
            }
        }
        return super.onOptionsItemSelected(item);
    }
}