package bloodlink0608app.com;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import bloodlink0608app.com.databinding.ActivityContactInformationPageBinding;

public class ContactInformationPage extends AppCompatActivity {

    ActivityContactInformationPageBinding binding;

    private FirebaseAuth mAuth;
    private ProgressDialog loadingbar;
    //private AdView mAdView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityContactInformationPageBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

//        mAdView = findViewById(R.id.adView);
//        AdRequest adRequest = new AdRequest.Builder().build();
//        mAdView.loadAd(adRequest);


        mAuth=FirebaseAuth.getInstance();
        loadingbar = new ProgressDialog(this);

        binding.contactnextbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String phone1 = binding.phone1id.getText().toString();
                //String phone2 = binding.phone2id.getText().toString();
                String facebookid = binding.facebookId.getText().toString();
                String currentuserId= FirebaseAuth.getInstance().getUid();

                if (phone1.isEmpty()){
                    binding.phone1id.setError("Please type your phone no");
                    binding.phone1id.requestFocus();
                    return;
                }else {

                    loadingbar.setTitle("Saving Your Information");
                    loadingbar.setMessage("Please wait");
                    loadingbar.setCanceledOnTouchOutside(true);
                    loadingbar.show();


                    HashMap info = new HashMap();
                    info.put("phone1",phone1);
                    //info.put("phone2",phone2);
                    info.put("facebookid",facebookid);

                    DatabaseReference user = FirebaseDatabase.getInstance().getReference("User").child(currentuserId);
                    user.updateChildren(info).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                loadingbar.dismiss();
                                Intent intent = new Intent(ContactInformationPage.this,RegBloodGroupSelect.class);
                                startActivity(intent);

                            }else {
                                loadingbar.dismiss();
                            }
                        }
                    });
                }

            }
        });
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