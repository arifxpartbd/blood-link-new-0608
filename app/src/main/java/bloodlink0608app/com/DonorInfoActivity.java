package bloodlink0608app.com;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;


import bloodlink0608app.com.databinding.ActivityDonorInfoBinding;

public class DonorInfoActivity extends AppCompatActivity {

    ActivityDonorInfoBinding binding;

    private String currentUserID;
    private FirebaseAuth mAuth;
    private DatabaseReference RootRef;

    private String Uid="";
    private String name ="";

    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDonorInfoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        name = getIntent().getExtras().get("name").toString();

        getSupportActionBar().setTitle(name+ " Profile");
        getSupportActionBar().setSubtitle("Blood Link 0608");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();
        RootRef = FirebaseDatabase.getInstance().getReference();

        Uid = getIntent().getExtras().get("uid").toString();

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    @Override
    protected void onStart() {
        super.onStart();

        RootRef.child("User").child(Uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);

                Picasso.get()
                        .load(user.getImage())
                        .placeholder(R.drawable.logowhite)
                        .into(binding.donorProfileImage);
                binding.donorName.setText(user.getUsername());
                binding.donorbloodgroup.setText(user.getBloodGroup());
                binding.dinfoAddress.setText(user.getVillage());
                binding.dinfoDistrict.setText(user.getDistrict());
                binding.dinfoLastDate.setText(user.getLastdate());
                binding.dinfoLastWeight.setText(user.weight);
                binding.status.setText(user.status);
                binding.dinfoGender.setText(user.gender);
                binding.dinfoEmail.setText(user.email);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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