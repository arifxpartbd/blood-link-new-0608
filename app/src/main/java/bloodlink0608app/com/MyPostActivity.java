package bloodlink0608app.com;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class MyPostActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    List<DataPost> dataPosts ;
    PostViewActivity.Adapter myAdapter;
    DatabaseReference databaseReference;
    private String currentUserID;
    private FirebaseAuth mAuth;
    private DatabaseReference RootRef;

    private String accpetby ="";
    private String accpetUid ="";
    private String postId ="";

    private AdView mAdView;

    private ProgressBar progressBar;
    private InterstitialAd mInterstitialAd;
    private final String TAG ="Admob";

    private boolean euConsent= false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_post);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        recyclerView = findViewById(R.id.recyclerView);
        progressBar = findViewById(R.id.progressbarId);

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

        recyclerView = findViewById(R.id.RecyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        //  linearLayoutManager.setStackFromEnd(true);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        dataPosts= new ArrayList<>();

        final MyPostActivity.Adapter myAdepter = new MyPostActivity.Adapter(this,dataPosts);
        recyclerView.setAdapter(myAdepter);


        //progressBar.setVisibility(View.VISIBLE);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("mypost")
                .child(currentUserID);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dataPosts.clear();
                if (dataSnapshot.exists())

                {
                    for (DataSnapshot ItemSnapshot : dataSnapshot.getChildren())
                    {
                        DataPost String = ItemSnapshot.getValue(DataPost.class);
                        dataPosts.add(String);
                    }
                    Collections.reverse(dataPosts);
                    myAdepter.notifyDataSetChanged();

                }else {
                    Toast.makeText(MyPostActivity.this,"No Data Found",Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        RootRef.child("User").child(currentUserID)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        User user = snapshot.getValue(User.class);

                        accpetby = user.getUsername();
//                        accpetUid = user.getUid();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

    }

    public class Adapter extends androidx.recyclerview.widget.RecyclerView.Adapter<MyPostActivity.Adapter.MyHolder>
    {

        Context context;
        List<DataPost>dataPosts;

        public Adapter(Context context, List<DataPost> data2Lists) {
            this.context = context;
            this.dataPosts = data2Lists;
        }

        @NonNull
        @Override
        public MyPostActivity.Adapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.my_post_layout,parent,false);
            MyPostActivity.Adapter.MyHolder HV = new MyPostActivity.Adapter.MyHolder(view);

            return HV;
        }

        @Override
        public int getItemCount() {
            return dataPosts.size();
        }

        @Override
        public void onBindViewHolder(@NonNull final MyPostActivity.Adapter.MyHolder holder, final int position) {



            holder.user_blood.setText(dataPosts.get(position).getBloodgroup());
            holder.reasonTV.setText(dataPosts.get(position).getPatientproblem());
            holder.currenttime.setText(dataPosts.get(position).getPostid());
            holder.user_detials.setText(dataPosts.get(position).getNeedhospital());


            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    try {
                        if (snapshot.child(dataPosts.get(position).getAccpetby()).exists())
                        {

                            holder.accpetok.setVisibility(View.VISIBLE);
                            //holder.cartbtn.setVisibility(View.GONE);
                        }else
                        {
                            holder.newaccpetbtn.setVisibility(View.GONE);
                            holder.accpetok.setVisibility(View.VISIBLE);
                        }
                    }
                    catch (Exception e)
                    {

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(MyPostActivity.this,PostDetialsActivity.class);
                    intent.putExtra("postid",dataPosts.get(position).getPostid());
                    intent.putExtra("accpetbyuid",dataPosts.get(position).getAccpetbyuid());
                    intent.putExtra("postcreatoruid",dataPosts.get(position).getPostcreateruid());
                    startActivity(intent);

                }

            });



        }


        class MyHolder extends  androidx.recyclerview.widget.RecyclerView.ViewHolder
        {

            private TextView user_name,user_number,user_blood,user_detials,user_donaername,user_donerphone,top_username,reasonTV,
                    currentdate,currenttime
                    ;
            private Button newaccpetbtn, accpetok;

            public MyHolder(@NonNull View itemView) {
                super(itemView);


                user_blood=itemView.findViewById(R.id.bloodGroupTV);

                user_detials=itemView.findViewById(R.id.rv_hospital);

                reasonTV=itemView.findViewById(R.id.reasonTV);
                currentdate=itemView.findViewById(R.id.currentdate);
                currenttime=itemView.findViewById(R.id.currentdate);

            }
        }
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