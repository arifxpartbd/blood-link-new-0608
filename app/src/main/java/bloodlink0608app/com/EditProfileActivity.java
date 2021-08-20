package bloodlink0608app.com;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import bloodlink0608app.com.databinding.ActivityEditProfileBinding;

public class EditProfileActivity extends AppCompatActivity {
    ActivityEditProfileBinding binding;
    private String currentUserID;
    private FirebaseAuth mAuth;
    private DatabaseReference RootRef;
    private ProgressDialog loadingbar;

    ArrayList<String> arrayList;
    Dialog dialog;

    ArrayList<String> arrayList2;

    ArrayList<String> arrayList3;
    private AdView mAdView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditProfileBinding.inflate(getLayoutInflater());
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

        loadingbar = new ProgressDialog(this);

        Retrivedata();

        arrayList3 = new ArrayList<>();
        arrayList3.add("Ready for donating blood");
        arrayList3.add("Not ready for donating blood");

        arrayList2 = new ArrayList<>();
        arrayList2.add("A+");
        arrayList2.add("A-");
        arrayList2.add("AB+");
        arrayList2.add("AB-");
        arrayList2.add("B+");
        arrayList2.add("B-");
        arrayList2.add("O+");
        arrayList2.add("O-");



        arrayList = new ArrayList<>();
        arrayList.add("Bagerhat");
        arrayList.add("Bandarban");
        arrayList.add("Barguna");
        arrayList.add("Barisal");
        arrayList.add("Bhola");
        arrayList.add("Bogra");
        arrayList.add("Brahmanbaria");
        arrayList.add("Chandpur");
        arrayList.add("Chittagong");
        arrayList.add("Chuadanga");
        arrayList.add("Comilla");
        arrayList.add("Cox's Bazar");
        arrayList.add("Dhaka");
        arrayList.add("Dinajpur");
        arrayList.add("Faridpur");
        arrayList.add("Feni");
        arrayList.add("Gaibandha");
        arrayList.add("Gazipur");
        arrayList.add("Gopalganj");
        arrayList.add("Habiganj");
        arrayList.add("Jaipurhat");
        arrayList.add("Jamalpur");
        arrayList.add("Jessore");
        arrayList.add("Jhalakati");
        arrayList.add("Jhenaidah");
        arrayList.add("Khagrachari");
        arrayList.add("Khulna");
        arrayList.add("Kishoreganj");
        arrayList.add("Kurigram");
        arrayList.add("Kushtia");
        arrayList.add("Lakshmipur");
        arrayList.add("Lalmonirhat");
        arrayList.add("Madaripur");
        arrayList.add("Magura");
        arrayList.add("Manikganj");
        arrayList.add("Meherpur");
        arrayList.add("Moulvibazar");
        arrayList.add("Munshiganj");
        arrayList.add("Mymensingh");
        arrayList.add("Naogaon");
        arrayList.add("Narail");
        arrayList.add("Narayanganj");
        arrayList.add("Narsingdi");
        arrayList.add("Natore");
        arrayList.add("Nawabganj");
        arrayList.add("Netrakona");
        arrayList.add("Nilphamari");
        arrayList.add("Noakhali");
        arrayList.add("Pabna");
        arrayList.add("Panchagarh");
        arrayList.add("Parbattya Chattagram");
        arrayList.add("Patuakhali");
        arrayList.add("Pirojpur");
        arrayList.add("Rajbari");
        arrayList.add("Rajshahi");
        arrayList.add("Rangpur");
        arrayList.add("Satkhira");
        arrayList.add("Shariatpur");
        arrayList.add("Sherpur");
        arrayList.add("Sirajganj");
        arrayList.add("Sunamganj");
        arrayList.add("Sylhet");
        arrayList.add("Tangail");
        arrayList.add("Thakurgaon");


        binding.updateCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new Dialog(EditProfileActivity.this);
                dialog.setContentView(R.layout.dialog_searchable_spinner);
                //set custom height and wedight
                dialog.getWindow().setLayout(650,800);

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                dialog.show();
                EditText editText = dialog.findViewById(R.id.edit_text);
                ListView listView = dialog.findViewById(R.id.list_view);

                ArrayAdapter<String> adapter = new ArrayAdapter<>(EditProfileActivity.this
                        , android.R.layout.simple_list_item_1,arrayList);

                listView.setAdapter(adapter);

                editText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                        adapter.getFilter().filter(s);
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        binding.updateCity.setText(adapter.getItem(position));
                        dialog.dismiss();

                    }
                });

            }
        });

        binding.status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new Dialog(EditProfileActivity.this);
                dialog.setContentView(R.layout.dialog_searchable_spinner);
                //set custom height and wedight
                dialog.getWindow().setLayout(650,800);

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                dialog.show();
                EditText editText = dialog.findViewById(R.id.edit_text);
                ListView listView = dialog.findViewById(R.id.list_view);

                ArrayAdapter<String> adapter = new ArrayAdapter<>(EditProfileActivity.this
                        , android.R.layout.simple_list_item_1,arrayList3);

                listView.setAdapter(adapter);

                editText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                        adapter.getFilter().filter(s);
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        binding.status.setText(adapter.getItem(position));
                        dialog.dismiss();

                    }
                });

            }
        });

        binding.updateBloodGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new Dialog(EditProfileActivity.this);
                dialog.setContentView(R.layout.dialog_searchable_spinner);
                //set custom height and wedight
                dialog.getWindow().setLayout(650,800);

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.RED));

                dialog.show();
                EditText editText = dialog.findViewById(R.id.edit_text);
                ListView listView = dialog.findViewById(R.id.list_view);

                ArrayAdapter<String> adapter2 = new ArrayAdapter<>(EditProfileActivity.this
                        , android.R.layout.simple_list_item_1,arrayList2);

                listView.setAdapter(adapter2);

                editText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                        adapter2.getFilter().filter(s);
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        binding.updateBloodGroup.setText(adapter2.getItem(position));
                        dialog.dismiss();

                    }
                });

            }
        });
    }

    private void Retrivedata() {

        RootRef.child("User").child(currentUserID)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        User user = snapshot.getValue(User.class);

                        binding.updateAddress.setText(user.getVillage());
                        binding.updatePostCode.setText(user.getPostcode());
                        binding.updateEmail.setText(user.getEmail());
                        binding.updateWeight.setText(user.getWeight());
                        binding.updateLastBloodDate.setText(user.getLastdate());
                        binding.updateFacebookid.setText(user.getFacebookid());
                        binding.updateCity.setText(user.getDistrict());
                        binding.updateAlternativePhoneId.setText(user.getPhone2());
                        binding.updateUsername.setText(user.getUsername());
                        binding.updateBloodGroup.setText(user.getBloodGroup());
                        binding.updatePhoneId.setText(user.getPhone1());
                        binding.updateDateOfBirth.setText(user.getDateofbirth());
                        binding.updateGender.setText(user.getGender());
                        binding.status.setText(user.status);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        binding.profileUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loadingbar.setTitle("Saving Your Information");
                loadingbar.setMessage("Please wait");
                loadingbar.setCanceledOnTouchOutside(true);
                loadingbar.show();

                String currentuserId= FirebaseAuth.getInstance().getUid();
                HashMap info = new HashMap();

                info.put("village",binding.updateAddress.getText().toString());
                info.put("bloodGroup",binding.updateBloodGroup.getText().toString());
                info.put("district",binding.updateCity.getText().toString());
                info.put("dateofbirth",binding.updateDateOfBirth.getText().toString());
                info.put("email",binding.updateEmail.getText().toString());
                info.put("facebookid",binding.updateFacebookid.getText().toString());
                info.put("gender",binding.updateGender.getText().toString());
                info.put("phone1",binding.updatePhoneId.getText().toString());
                info.put("phone2",binding.updateAlternativePhoneId.getText().toString());
                info.put("postcode",binding.updatePostCode.getText().toString());
                info.put("username",binding.updateUsername.getText().toString());
                info.put("weight",binding.updateWeight.getText().toString());
                info.put("lastdate",binding.updateLastBloodDate.getText().toString());
                info.put("status",binding.status.getText().toString());

                RootRef.child("User").child(currentuserId).updateChildren(info).addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {

                        loadingbar.dismiss();
                        if (task.isSuccessful()){
                            Intent intent = new Intent(EditProfileActivity.this,MainActivity.class);

                            startActivity(intent);
                        }else {
                            loadingbar.dismiss();
                            String message = task.getException().toString();
                            Toast.makeText(EditProfileActivity.this,"Error: "+message,Toast.LENGTH_LONG).show();
                        }
                    }
                });
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


    public void openStartDatePicker(View view) {


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
                binding.updateLastBloodDate.setText(stringDate);
            }
        };

        Calendar calendar = Calendar.getInstance();

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(EditProfileActivity.this, onDateSetListener, year, month,day);
        datePickerDialog.show();
    }

    public void openStartDatePicker1(View view) {
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
                binding.updateDateOfBirth.setText(stringDate);
            }
        };

        Calendar calendar = Calendar.getInstance();

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(EditProfileActivity.this, onDateSetListener, year, month,day);
        datePickerDialog.show();
    }
}