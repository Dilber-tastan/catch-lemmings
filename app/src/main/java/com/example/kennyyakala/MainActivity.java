package com.example.kennyyakala;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;
import java.util.Random;

public class MainActivity extends AppCompatActivity {


    private ImageView movableImage;
    private TextView scoreText;
    private TextView timeText;
    private int score = 0;
    private Button exitButton; // Oyundan çık butonu
    private Button restartButton;

    private Handler handler;
    private Runnable runnable;
    private long timeLeft = 10000; // 10 saniye (10000 ms)
    private boolean isGameActive = true;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        movableImage = findViewById(R.id.imageView2);
        scoreText = findViewById(R.id.Score);
        timeText = findViewById(R.id.time); // Zamanı gösterecek TextView
        exitButton = findViewById(R.id.button3); // "Oyundan Çık" butonu
        restartButton = findViewById(R.id.button4); // "Tekrar Oyna" butonu


        handler = new Handler();

        // Resmin tıklama olayını yakalamak
        movableImage.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (isGameActive && event.getAction() == MotionEvent.ACTION_DOWN) {
                    score++; // Skoru artır
                    scoreText.setText("Score " + score);
                }
                return true;
            }
        });

        // Zamanlayıcı başlat
        startTimer();

        // Resmin hareketini başlat
        moveImage();

        // "Oyundan Çık" butonuna tıklandığında oyun kapanacak
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Aktiviteyi sonlandır ve uygulamayı kapat
            }
        });

        // "Tekrar Oyna" butonuna tıklandığında oyunu sıfırlayıp yeniden başlat
        restartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetGame(); // Oyun sıfırlansın
            }
        });

    }


    private void startTimer() {
        runnable = new Runnable() {
            @Override
            public void run() {
                if (isGameActive) {
                    timeLeft -= 100; // Her 100ms'de zaman kaybet
                    long seconds = timeLeft / 1000; // Kalan süreyi saniyeye çevir

                    timeText.setText("Time: " + seconds); // Zamanı güncelle

                    if (timeLeft <= 0) {
                        isGameActive = false;
                        timeText.setText("time is over!"); // Zaman bittiğinde mesaj
                        scoreText.setText("Score: " + score); // Skoru göster

                        // "Oyundan Çık" ve "Tekrar Oyna" butonlarını göster
                        exitButton.setVisibility(View.VISIBLE);
                        restartButton.setVisibility(View.VISIBLE);
                    } else {
                        handler.postDelayed(this, 100); // 100ms sonra tekrar çalıştır
                    }
                }
            }
        };
        handler.postDelayed(runnable, 100); // Zamanlayıcıyı başlat
    }

    private void moveImage() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isGameActive) {
                    // Resmin yeni pozisyonu
                    int x = (int) (Math.random() * (getWindowManager().getDefaultDisplay().getWidth() - movableImage.getWidth()));
                    int y = (int) (Math.random() * (getWindowManager().getDefaultDisplay().getHeight() - movableImage.getHeight()));

                    // Yeni pozisyona taşı
                    movableImage.setX(x);
                    movableImage.setY(y);

                    // 1 saniye sonra tekrar hareket et
                    handler.postDelayed(this, 100);
                }
            }
        }, 1000);
    }

    // Oyunu sıfırlama fonksiyonu
    private void resetGame() {
        score = 0;
        timeLeft = 10000; // 10 saniyeyi sıfırla
        isGameActive = true;

        scoreText.setText("Score: 0");
        timeText.setText("Time: 10");

      /* Butonları gizle
        exitButton.setVisibility(View.GONE);
        restartButton.setVisibility(View.GONE);*/


        // Resmi başlat
        moveImage();

        // Zamanlayıcıyı yeniden başlat
        startTimer();
    }
}




















