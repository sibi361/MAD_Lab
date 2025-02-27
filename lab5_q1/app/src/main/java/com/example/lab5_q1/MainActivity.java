package com.example.lab5_q1;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.LinkedList;
import java.util.Queue;

public class MainActivity extends AppCompatActivity {
    Button playBtn;
    private MediaPlayer mediaPlayer;
    private Queue<Integer> songQueue;
    private int currentSongIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button backBtn = findViewById(R.id.backBtn);
        Button rewindBtn = findViewById(R.id.rewindBtn);
        Button seekBtn = findViewById(R.id.seekBtn);
        Button skipBtn = findViewById(R.id.skipBtn);
        playBtn = findViewById(R.id.playBtn);

        initQueue();

        playBtn.setOnClickListener(v -> {
            if (mediaPlayer != null) {
                if (mediaPlayer.isPlaying()) {
                    playBtn.setText("Play");
                    mediaPlayer.pause();
                } else {
                    playBtn.setText("Pause");
                    mediaPlayer.start();
                }
            } else {
                playNextSong();
            }
        });

        backBtn.setOnClickListener(v -> {
            if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                playPreviousSong();
            }
        });

        rewindBtn.setOnClickListener(v -> {
            if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                int currentPos = mediaPlayer.getCurrentPosition();
                mediaPlayer.seekTo(Math.max(currentPos - 5000, 0));
            }
        });

        seekBtn.setOnClickListener(v -> {
            if (mediaPlayer != null) {
                int currentPos = mediaPlayer.getCurrentPosition();
                int maxPos = mediaPlayer.getDuration();
                mediaPlayer.seekTo(Math.max(currentPos + 5000, maxPos - 5000));
            }
        });

        skipBtn.setOnClickListener(v -> {
            if (mediaPlayer != null) {
                playNextSong();
            }
        });
    }

    private void initQueue() {
        songQueue = new LinkedList<>();
        songQueue.add(R.raw.song);
        songQueue.add(R.raw.song2);
        songQueue.add(R.raw.song3);
    }

    private void playPreviousSong() {
        if (songQueue.isEmpty()) return;

        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }

        currentSongIndex = (currentSongIndex - 1 + songQueue.size()) % songQueue.size();
        Integer previousSong = songQueue.toArray(new Integer[0])[currentSongIndex];
        mediaPlayer = MediaPlayer.create(this, previousSong);
        mediaPlayer.setOnCompletionListener(mp -> playPreviousSong());
        mediaPlayer.start();
    }

    private void playNextSong() {
        if (songQueue.isEmpty()) return;

        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }

        playBtn.setText("Pause");

        if (songQueue.isEmpty()) {
            initQueue();
        }

        Integer nextSong = songQueue.poll();

        currentSongIndex = (currentSongIndex + 1) % songQueue.size();
        mediaPlayer = MediaPlayer.create(this, nextSong);
        mediaPlayer.setOnCompletionListener(mp -> playNextSong());
        mediaPlayer.start();
    }
}