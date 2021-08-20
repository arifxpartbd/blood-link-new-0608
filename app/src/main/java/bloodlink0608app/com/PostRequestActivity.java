package bloodlink0608app.com;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;

import bloodlink0608app.com.databinding.ActivityPostRequestBinding;

public class PostRequestActivity extends AppCompatActivity {

    ActivityPostRequestBinding binding;

    private String currentUserID;
    private FirebaseAuth mAuth;
    private DatabaseReference RootRef;
    private ProgressDialog loadingbar;
    private String Postname="";
    private String Phone ="";
    private String currentDate,currentTime;
    private DatabaseReference databaseReference;

    String[] bag=new String[]{"1 Bag","2 Bag","3 Bag","4 Bag","5 Bag","6 Bag"};

    private AdView mAdView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPostRequestBinding.inflate(getLayoutInflater());
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

        getSupportActionBar().setTitle("Blood Request");
        FirebaseMessaging.getInstance().subscribeToTopic("all");

        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();
        RootRef = FirebaseDatabase.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        loadingbar = new ProgressDialog(this);

        String[] districts = getResources().getStringArray(R.array.bd_districts);
        String[] bloodGroups = getResources().getStringArray(R.array.blood_groups);

        binding.bloodgroup.setThreshold(1);
        binding.bloodgroup.setAdapter(new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_dropdown_item_1line, bloodGroups));

        binding.requestDistrict.setThreshold(1);
        binding.requestDistrict.setAdapter(new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_dropdown_item_1line, districts));

        binding.requestHowManyBag.setThreshold(1);
        binding.requestHowManyBag.setAdapter(new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1,bag));

        binding.requestDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openStartDatePicker();
            }
        });


        RootRef.child("User").child(currentUserID)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        User user = snapshot.getValue(User.class);
                        Phone = user.getPhone1();
                        Postname = user.getUsername();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        binding.publishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String postbloodgroup = binding.bloodgroup.getText().toString();
                String postdate = binding.requestDate.getText().toString();
                String posttime = binding.requestTime.getText().toString();
                String postdistrict = binding.requestDistrict.getText().toString();
                String posthospital = binding.requestHospital.getText().toString();
                String donorrecevername = binding.requestReceiverName.getText().toString();
                String donorreceverphone = binding.requestReceiverPhone.getText().toString();


                if (postbloodgroup.isEmpty()){
                    binding.bloodgroup.setError("Please type blood group");
                    binding.bloodgroup.requestFocus();
                    return;
                }if (postdate.isEmpty()){
                    binding.requestDate.setError("Please type your date");
                    binding.requestDate.requestFocus();
                    return;
                }if (posttime.isEmpty()){
                    binding.requestTime.setError("Please type your time");
                    binding.requestTime.requestFocus();
                    return;
                }if (postdistrict.isEmpty()){
                    binding.requestDistrict.setError("Please type your district");
                    binding.requestDistrict.requestFocus();
                    return;
                }if (posthospital.isEmpty()){
                    binding.requestHospital.setError("Please type your hospital name");
                    binding.requestHospital.requestFocus();
                    return;
                }if (donorrecevername.isEmpty()){
                    binding.requestReceiverName.setError("Please type your receiver name");
                    binding.requestReceiverName.requestFocus();
                    return;
                }if (donorreceverphone.isEmpty()){
                    binding.requestReceiverPhone.setError("Please type your receiver phone no");
                    binding.requestReceiverPhone.requestFocus();
                    return;
                }else {

                    loadingbar.setTitle("Saving Your Information");
                    loadingbar.setMessage("Please wait");
                    loadingbar.setCanceledOnTouchOutside(true);
                    loadingbar.show();

                Calendar calForDate =Calendar.getInstance();
                SimpleDateFormat currentDateFormat2=new SimpleDateFormat("MMM dd yyyy");
                currentDate=currentDateFormat2.format(calForDate.getTime());


                Calendar calForTime =Calendar.getInstance();
                SimpleDateFormat currentTimeFormat2=new SimpleDateFormat("hh:mm:ss a");
                currentTime=currentTimeFormat2.format(calForTime.getTime());


                String postid=currentDate+" "+currentTime;

                String currentuserId= FirebaseAuth.getInstance().getUid();

                databaseReference = FirebaseDatabase.getInstance().getReference().child("post").child(postid);
                HashMap info = new HashMap();

                info.put("bloodgroup",binding.bloodgroup.getText().toString());
                info.put("patientproblem",binding.requestPatientProblem.getText().toString());
                info.put("howmaybag",binding.requestHowManyBag.getText().toString());
                info.put("needdistrict",binding.requestDistrict.getText().toString());
                info.put("needdate",binding.requestDate.getText().toString());
                info.put("needtime",binding.requestTime.getText().toString());
                info.put("needhospital",binding.requestHospital.getText().toString());
                info.put("donorrecivername",binding.requestReceiverName.getText().toString());
                info.put("donorreciverphone",binding.requestReceiverPhone.getText().toString());
                info.put("postcreatername",Postname);
                info.put("postcreateruid",currentuserId);
                info.put("postid",postid);
                info.put("accpetbyuid","");



                databaseReference.setValue(info)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                if (task.isSuccessful()){
                                    //String currentuserId= FirebaseAuth.getInstance().getUid();
                                    databaseReference = FirebaseDatabase.getInstance().getReference().child("notification").child(postid);

                                    HashMap notiinfo = new HashMap();
                                    notiinfo.put("postcreatername",Postname);
                                    notiinfo.put("needtime",binding.requestTime.getText().toString());
                                    notiinfo.put("bloodgroup",binding.bloodgroup.getText().toString());
                                    notiinfo.put("needhospital",binding.requestHospital.getText().toString());

                                    databaseReference.setValue(info).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()){
                                                databaseReference = FirebaseDatabase.getInstance().getReference().child("mypost").child(currentuserId).child(postid);
                                                HashMap infomypost = new HashMap();

                                                infomypost.put("bloodgroup",binding.bloodgroup.getText().toString());
                                                infomypost.put("patientproblem",binding.requestPatientProblem.getText().toString());
                                                infomypost.put("howmaybag",binding.requestHowManyBag.getText().toString());
                                                infomypost.put("needdistrict",binding.requestDistrict.getText().toString());
                                                infomypost.put("needdate",binding.requestDate.getText().toString());
                                                infomypost.put("needtime",binding.requestTime.getText().toString());
                                                infomypost.put("needhospital",binding.requestHospital.getText().toString());
                                                infomypost.put("donorrecivername",binding.requestReceiverName.getText().toString());
                                                infomypost.put("donorreciverphone",binding.requestReceiverPhone.getText().toString());
                                                infomypost.put("postcreatername",Postname);
                                                infomypost.put("postcreateruid",currentuserId);
                                                infomypost.put("postid",postid);
                                                infomypost.put("accpetbyuid","");

                                                databaseReference.setValue(infomypost);
                                                loadingbar.dismiss();
                                            }
                                        }
                                    });

                                    Intent intent = new Intent(PostRequestActivity.this,MainActivity.class);
                                    startActivity(intent);
                                    Toast.makeText(PostRequestActivity.this,"Post Created Successfully",Toast.LENGTH_LONG).show();
                                    loadingbar.dismiss();

                                    FcmNotificationsSender notificationsSender = new FcmNotificationsSender("/topics/all",binding.bloodgroup.getText().toString()+" Blood Need",
                                            Postname.toString(),getApplicationContext(),PostRequestActivity.this);
                                    notificationsSender.SendNotifications();
                                }else {
                                    String message = task.getException().toString();
                                    loadingbar.dismiss();
                                    Toast.makeText(PostRequestActivity.this,"Error: "+message,Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                }
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
                binding.requestDate.setText(stringDate);
            }
        };

        Calendar calendar = Calendar.getInstance();

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(PostRequestActivity.this, onDateSetListener, year, month,day);
        datePickerDialog.show();
    }

    public void popTimePicker(View view) {

      Calendar  calendar = Calendar.getInstance();
      int hours = calendar.get(Calendar.HOUR_OF_DAY);
      int mins = calendar.get(Calendar.MINUTE);

      TimePickerDialog timePickerDialog = new TimePickerDialog(PostRequestActivity.this, R.style.Theme_AppCompat, new TimePickerDialog.OnTimeSetListener() {
          @Override
          public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
              Calendar c = Calendar.getInstance();
              c.set(Calendar.HOUR_OF_DAY,hourOfDay);
              c.set(Calendar.MINUTE,minute);
              c.setTimeZone(TimeZone.getDefault());
              SimpleDateFormat format = new SimpleDateFormat("k:mm a");
              String time = format.format(c.getTime());
              binding.requestTime.setText(time);
          }
      },hours, mins,false);
      timePickerDialog.show();

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