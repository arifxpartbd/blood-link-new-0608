package bloodlink0608app.com;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

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

import bloodlink0608app.com.databinding.ActivityPostDetialsBinding;

public class PostDetialsActivity extends AppCompatActivity {


    ActivityPostDetialsBinding binding;
    DatabaseReference databaseReference;
    private String currentUserID;
    private FirebaseAuth mAuth;
    private DatabaseReference RootRef;

    private String accpetuid ="";
    private AlertDialog.Builder alertDialogBuilder;

    private AdView mAdView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityPostDetialsBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();
        RootRef = FirebaseDatabase.getInstance().getReference();

        String postid = getIntent().getExtras().get("postid").toString();

        databaseReference = FirebaseDatabase.getInstance().getReference().child("post").child(postid);

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                DataPost dataPost = snapshot.getValue(DataPost.class);

                binding.bloodGroupTV.setText(dataPost.getBloodgroup());
                binding.postDProblem.setText(dataPost.getPatientproblem());
                binding.postDBag.setText(dataPost.getHowmaybag());
                binding.postDHospital.setText(dataPost.getNeedhospital());
                binding.postDDistrict.setText(dataPost.getNeeddistrict());
                binding.postDCrName.setText(dataPost.getPostcreatername());
                binding.postDRecever.setText(dataPost.getDonorrecivername());
                binding.postDReceverNumber.setText(dataPost.getDonorreciverphone());
                binding.postDTime.setText(dataPost.getNeedtime());
                binding.postDDate.setText(dataPost.getNeeddate());
                binding.postATime.setText(dataPost.getAccpetbytime());
                binding.postADate.setText(dataPost.getAccpetbydate());
                //accpetuid = dataPost.getAccpetbyuid();

                if (dataPost.getPostcreateruid().equals(currentUserID)){
                    binding.postAPhone.setVisibility(View.VISIBLE);
                    binding.callbutton.setVisibility(View.VISIBLE);
                }else {
                    binding.postAPhone.setVisibility(View.INVISIBLE);
                    binding.callbutton.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        binding.sharepostbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentshare = new Intent(Intent.ACTION_SEND);
                intentshare.setType("text/plain");

                String body= "Emergency Blood Needed"+"\n"+

                        "রক্তের গ্রুপঃ"+binding.bloodGroupTV.getText().toString()+"\n"+
                        "রোগীর সমস্যাঃ"+binding.postDProblem.getText().toString()+"\n"+
                        "রক্তের পরিমাণঃ"+binding.postDBag.getText().toString()+"\n"+
                        "জেলাঃ"+binding.postDDistrict.getText().toString()+"\n"+
                        "তারিখঃ"+binding.postDDate.getText().toString()+"\n"+
                        "সময়ঃ"+binding.postDTime.getText().toString()+"\n"+
                        "রক্তদানের স্থানঃ"+binding.postDHospital.getText().toString()+"\n"+
                        "ডোনার রিসিভার নামঃ "+binding.postDRecever.getText().toString()+"\n"+
                        "মোবাইল নংঃ"+binding.postDReceverNumber.getText().toString();

                intentshare.putExtra(Intent.EXTRA_TEXT,body);
                startActivity(Intent.createChooser(intentshare,"Complet share"));
            }
        });

        accpetuid = getIntent().getExtras().get("accpetbyuid").toString();
        RootRef.child("User").child(accpetuid)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        User user = snapshot.getValue(User.class);

                        binding.postABloodGroup.setText(user.getBloodGroup());
                        binding.postAcceptName.setText(user.getUsername());
                        binding.postAEmail.setText(user.getEmail());
                        binding.postALastDate.setText(user.getLastdate());
                        binding.postAPhone.setText(user.getPhone1());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        binding.callbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                alertDialogBuilder=new AlertDialog.Builder(PostDetialsActivity.this);
                alertDialogBuilder.setTitle("Notice");
                alertDialogBuilder.setMessage(R.string.notice);
                alertDialogBuilder.setIcon(R.drawable.ic_baseline_error_24);

                alertDialogBuilder.setPositiveButton("CALL", new DialogInterface.OnClickListener() {


                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent intent=new Intent(Intent.ACTION_DIAL);

                        String phone=binding.postAPhone.getText().toString();

                        try {
                            if (phone.trim().isEmpty()){
                                Toast.makeText(getApplicationContext(),"Number not Foount",Toast.LENGTH_LONG).show();

                            }else {
                                intent.setData(Uri.parse("tel:"+phone));

                            }
                            if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED){

                                Toast.makeText(PostDetialsActivity.this,"Please Grant Permission",Toast.LENGTH_LONG).show();
                                requestpermision();
                            }
                            else {
                                startActivity(intent);
                            }
                        }catch (Exception e){

                        }


                    }
                });
                AlertDialog alertDialog=alertDialogBuilder.create();
                alertDialog.show();



            }
            private void requestpermision(){

                ActivityCompat.requestPermissions(PostDetialsActivity.this,new String[]{Manifest.permission.CALL_PHONE},1);

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();


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