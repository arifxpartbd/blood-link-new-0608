package bloodlink0608app.com;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import com.google.ads.mediation.admob.AdMobAdapter;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import bloodlink0608app.com.databinding.ActivityAgeBinding;
import kotlin.text.UStringsKt;

public class AgeActivity extends AppCompatActivity {


    ActivityAgeBinding binding;

    private ProgressDialog loadingbar;
    private FirebaseAuth mAuth;

    //private AdView mAdView;
    private InterstitialAd mInterstitialAd;

    private boolean euConsent= false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAgeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {

                if (euConsent){
                    createPersonailAd();
                }else {
                    createnonPersonailAd();
                }

            }
        });

       // mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        //mAdView.loadAd(adRequest);



        mAuth=FirebaseAuth.getInstance();
        loadingbar = new ProgressDialog(this);

        binding.dateofbirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openStartDatePicker();
            }
        });
        binding.lastDonateDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openStartDatePicker2();
            }
        });

        binding.alldoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mInterstitialAd != null) {
                    mInterstitialAd.show(AgeActivity.this);
                } else {
                    Log.d("TAG", "The interstitial ad wasn't ready yet.");

                    openMainActivity();
                }

            }
        });
    }

    private void openMainActivity() {

        loadingbar.setTitle("Saving Your Information");
        loadingbar.setMessage("Please wait");
        loadingbar.setCanceledOnTouchOutside(true);
        loadingbar.show();

        String currentuserId= FirebaseAuth.getInstance().getUid();
        HashMap info = new HashMap();

        DatabaseReference user = FirebaseDatabase.getInstance().getReference().child("User")
                .child(currentuserId);
        info.put("dateofbirth",binding.dateofbirth.getText().toString());
        info.put("lastdate",binding.lastDonateDate.getText().toString());
        info.put("status","Ready for donating blood");
        user.updateChildren(info).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if (task.isSuccessful()){
                    loadingbar.dismiss();

                    Intent intent = new Intent(AgeActivity.this,MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

                    startActivity(intent);

                }else {
                    loadingbar.dismiss();
                    String message = task.getException().toString();
                    Toast.makeText(AgeActivity.this,"Error: "+message,Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void createPersonailAd() {
        AdRequest adRequest = new AdRequest.Builder().build();
        createIntersititialsAd(adRequest);

    }

    private void createnonPersonailAd() {

        Bundle networkExtrasBundle = new Bundle();
        networkExtrasBundle.putInt("rdp", 1);
        AdRequest adRequest = new AdRequest.Builder()
                .addNetworkExtrasBundle(AdMobAdapter.class, networkExtrasBundle)
                .build();
        createIntersititialsAd(adRequest);

    }
    private void createIntersititialsAd(AdRequest adRequest){
        InterstitialAd.load(this,"ca-app-pub-4198182563462163/2981277908", adRequest, new InterstitialAdLoadCallback() {
            @Override
            public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                // The mInterstitialAd reference will be null until
                // an ad is loaded.
                mInterstitialAd = interstitialAd;
                Log.d("----Admob", "onAdLoaded");


                mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback(){
                    @Override
                    public void onAdDismissedFullScreenContent() {
                        // Called when fullscreen content is dismissed.
                        Log.d("TAG", "The ad was dismissed.");

                        openMainActivity();
                    }

                    @Override
                    public void onAdFailedToShowFullScreenContent(AdError adError) {
                        // Called when fullscreen content failed to show.
                        Log.d("TAG", "The ad failed to show.");
                        openMainActivity();
                    }

                    @Override
                    public void onAdShowedFullScreenContent() {
                        // Called when fullscreen content is shown.
                        // Make sure to set your reference to null so you don't
                        // show it a second time.
                        mInterstitialAd = null;
                        Log.d("TAG", "The ad was shown.");

                    }
                });

            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                // Handle the error
                Log.d("----Admob", loadAdError.getMessage());
                mInterstitialAd = null;
            }
        });
    }

    private void openStartDatePicker() {


        DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                month = month + 1;
                String currentDate = year + "/" + month + "/" + day + " 00:00:00";
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
                Date date = null;
                try {
                    date = simpleDateFormat.parse(currentDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                //eventStartDate = date.getTime()/1000;
                long epochTime = date.getTime()/1000;
                String stringDate = SDF.getDate(epochTime);
                binding.dateofbirth.setText(stringDate);
            }
        };

        Calendar calendar = Calendar.getInstance();

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(AgeActivity.this, onDateSetListener, year, month,day);
        datePickerDialog.show();
    }

    private void openStartDatePicker2() {


        DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                month = month + 1;
                String currentDate = year + "/" + month + "/" + day + " 00:00:00";
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
                Date date = null;
                try {
                    date = simpleDateFormat.parse(currentDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                //eventStartDate = date.getTime()/1000;
                long epochTime = date.getTime()/1000;
                String stringDate = SDF.getDate(epochTime);
                binding.lastDonateDate.setText(stringDate);
            }
        };

        Calendar calendar = Calendar.getInstance();

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(AgeActivity.this, onDateSetListener, year, month,day);
        datePickerDialog.show();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId()==android.R.id.home)
        {
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}