package bloodlink0608app.com;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ProgressBar;

public class WelcomeScreen extends AppCompatActivity {


    private ProgressBar progressBar;
    private  int progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        progressBar=findViewById(R.id.progressBar);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                doWork();

                startApp();

            }
        });

        thread.start();
    }
    public void doWork (){

        for (progress=25;progress<=100;progress=progress+25){

            try {
                Thread.sleep(500);

                progressBar.setProgress(progress);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


    }
    public void startApp(){

        Intent intent =new Intent(WelcomeScreen.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}