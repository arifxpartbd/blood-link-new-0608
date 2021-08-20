package bloodlink0608app.com;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgetPassword extends AppCompatActivity {



    private EditText resetemailId;
    private Button resetbtn;
    private ImageView backbtnreset;

    private FirebaseAuth mAuth;

    private ProgressDialog loadingBar;

    private AdView mAdView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);


        mAuth=FirebaseAuth.getInstance();

        resetbtn=findViewById(R.id.resetbtn);
        resetemailId=findViewById(R.id.resetemailId);

        loadingBar=new ProgressDialog(this);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        resetbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String userEmail=resetemailId.getText().toString();

                if (TextUtils.isEmpty(userEmail))
                {
                    resetemailId.setError("Please type your email");
                    resetemailId.requestFocus();
                    return;
                }else
                {

                    loadingBar.setTitle("Reseting Password");
                    loadingBar.setMessage("Please wait");
                    loadingBar.setCanceledOnTouchOutside(true);
                    loadingBar.show();
                    mAuth.sendPasswordResetEmail(userEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task)
                        {
                            if (task.isSuccessful())
                            {
                                Toast.makeText(getApplicationContext(), "Please Check Your Email Address. If you want to reset your password", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(ForgetPassword.this, LoginActivity.class));
                                finish();
                            }else
                            {
                                String message=task.getException().getMessage();
                                Toast.makeText(getApplicationContext(), "Error : "+message, Toast.LENGTH_LONG).show();

                                loadingBar.dismiss();
                            }
                        }
                    });
                }

            }
        });
    }
}