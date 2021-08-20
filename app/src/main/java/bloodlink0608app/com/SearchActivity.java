package bloodlink0608app.com;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import bloodlink0608app.com.databinding.ActivityRegBloodGroupSelectBinding;
import bloodlink0608app.com.databinding.ActivitySearchBinding;

public class SearchActivity extends AppCompatActivity {


    ActivitySearchBinding binding;

    private static final String TAG = "SearchActivity";
    private String bloodGroup;
    private DatabaseReference databaseReference;
    private List<User> userList;
    private RecyclerView recyclerView;
    private SearchAdapter adapter;
    private ProgressBar progressBar;
    private ImageView backBtnIV;


    private String searchOption;
    private AdView mAdView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySearchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);




        Log.d(TAG, "onCreate: started");

        getSupportActionBar().hide();

        final String[] districts = getResources().getStringArray(R.array.bd_districts);

        databaseReference = FirebaseDatabase.getInstance().getReference();

        initViews();
        initRecyclerView();

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle != null) {
            bloodGroup = bundle.getString("bloodGroup");
        }

        //Log.d(TAG, "onCreate: " + bloodGroup);
       /* searchATV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(SearchActivity.this, "District: "+districtAdapter.getItem(position), Toast.LENGTH_SHORT).show();
                search(districtAdapter.getItem(position));

            }
        });*/

        searchOption = "district";
        binding.searchET.setThreshold(1);
        binding.searchET.setAdapter(new ArrayAdapter<>(SearchActivity.this, R.layout.support_simple_spinner_dropdown_item, districts));
        binding.districtSearchTV.setBackgroundResource(R.drawable.background_selected_tv);
        binding.searchET.setHint("Search by district");
        binding.searchET.setInputType(EditorInfo.TYPE_TEXT_FLAG_CAP_WORDS);


//        binding.phoneSearchTV.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                searchOption = "phone";
//
//                binding.phoneSearchTV.setBackgroundResource(R.drawable.background_selected_tv);
//                binding.searchET.setHint("Search by phone");
//                binding.searchET.setInputType(EditorInfo.TYPE_CLASS_PHONE);
//                binding.districtSearchTV.setBackgroundResource(R.drawable.background_option);
//                binding.areaSearchTV.setBackgroundResource(R.drawable.background_option);
//                binding.universitySearchTV.setBackgroundResource(R.drawable.background_option);
//            }
//        });

        binding.districtSearchTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchOption = "district";

                binding.searchET.setThreshold(1);
                binding.searchET.setAdapter(new ArrayAdapter<>(SearchActivity.this, R.layout.support_simple_spinner_dropdown_item, districts));

                binding.districtSearchTV.setBackgroundResource(R.drawable.background_selected_tv);
                binding.searchET.setHint("Search by district");
                binding.searchET.setInputType(EditorInfo.TYPE_TEXT_FLAG_CAP_WORDS);
//                binding.phoneSearchTV.setBackgroundResource(R.drawable.background_option);
                binding.areaSearchTV.setBackgroundResource(R.drawable.background_option);
                binding.universitySearchTV.setBackgroundResource(R.drawable.background_option);


            }
        });

        binding.areaSearchTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                searchOption = "village";
                binding.areaSearchTV.setBackgroundResource(R.drawable.background_selected_tv);
                binding.searchET.setHint("Search by area");
                binding.searchET.setInputType(EditorInfo.TYPE_TEXT_FLAG_CAP_WORDS);
//                binding.phoneSearchTV.setBackgroundResource(R.drawable.background_option);
                binding.districtSearchTV.setBackgroundResource(R.drawable.background_option);
                binding.universitySearchTV.setBackgroundResource(R.drawable.background_option);

            }
        });

        binding.universitySearchTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                searchOption = "university";

                binding.universitySearchTV.setBackgroundResource(R.drawable.background_selected_tv);
                binding.searchET.setHint("Search by university");
                binding.searchET.setInputType(EditorInfo.TYPE_TEXT_FLAG_CAP_WORDS);
//                binding.phoneSearchTV.setBackgroundResource(R.drawable.background_option);
                binding.districtSearchTV.setBackgroundResource(R.drawable.background_option);
                binding.areaSearchTV.setBackgroundResource(R.drawable.background_option);

            }
        });


        backBtnIV = findViewById(R.id.backBtnIV);
        backBtnIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        binding.searchET.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    String text = binding.searchET.getText().toString().trim();
                    Log.d(TAG, "onClick: " + text);
                    //Toast.makeText(SearchActivity.this, text, Toast.LENGTH_SHORT).show();
                    if (text.isEmpty()) {
                        initFirebase();
                    } else {
                        search(searchOption,text);
                    }

                    return true;
                }

                return false;
            }
        });


    }


    private void search(String searchOption, String searchText) {

        if (searchOption.equals("phone")){
            searchText = "+88"+searchText;
            Log.d(TAG, "search: searchOption: "+searchOption);
        }


        Query query = databaseReference.child("User").orderByChild(searchOption)
                .startAt(searchText)
                .endAt(searchText+"\\uf8ff");


        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(TAG, "onDataChange: started: district");
                if (dataSnapshot.exists())
                {
                    Log.d(TAG, "data: exist ");
                    userList.clear();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        User user = snapshot.getValue(User.class);
                        if (bloodGroup.equals(user.getBloodGroup())) {
                            userList.add(user);
                            Log.d(TAG, "onDataChange: " + user.toString());
                        }
                    }
                    adapter.notifyDataSetChanged();
                } else {
                    userList.clear();
                    adapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // display message if error occurs
                Log.d(TAG, "onCancelled: started");

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        initFirebase();
        //progressBar.setVisibility(View.VISIBLE);
    }

    private void initFirebase() {
        Log.d(TAG, "initFirebase: started!");
        progressBar.setVisibility(View.VISIBLE);



        DatabaseReference searchRef = databaseReference.child("User");

        searchRef.orderByChild("bloodGroup").equalTo(bloodGroup)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if (dataSnapshot.exists()) {
                            userList.clear();
                            for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                User user = ds.getValue(User.class);
                                userList.add(user);
                                Log.d(TAG, "onDataChange: " + user.toString());
                            }
                            Log.d(TAG, "onDataChange: donorList :size: "+userList.size());
                            adapter.notifyDataSetChanged();
                            progressBar.setVisibility(View.GONE);
                        }
                        else {
                            progressBar.setVisibility(View.GONE);

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                        Toast.makeText(SearchActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                    }
                });
    }

    private void initRecyclerView() {

        userList = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SearchAdapter(SearchActivity.this, userList);
        recyclerView.setAdapter(adapter);

    }

    private void initViews() {
        progressBar = findViewById(R.id.progressBar);
        recyclerView = findViewById(R.id.recyclerView);


    }

}