package bloodlink0608app.com;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
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
import com.google.firebase.messaging.FirebaseMessaging;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class PostViewActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    List<DataPost> dataPosts ;
    Adapter myAdapter;
    DatabaseReference databaseReference;
    private String currentUserID;
    private FirebaseAuth mAuth;
    private DatabaseReference RootRef;

    private String accpetby ="";
    private String accpetUid ="";
    private String postId ="";
    private String currentDate,currentTime;

    private AdView mAdView;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_view);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        FirebaseMessaging.getInstance().subscribeToTopic("all");


        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        recyclerView = findViewById(R.id.recyclerView);
        progressBar = findViewById(R.id.progress_bar);


        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();
        RootRef = FirebaseDatabase.getInstance().getReference();

        recyclerView = findViewById(R.id.RecyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        //  linearLayoutManager.setStackFromEnd(true);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        dataPosts= new ArrayList<>();

        final PostViewActivity.Adapter myAdepter = new PostViewActivity.Adapter(this,dataPosts);
        recyclerView.setAdapter(myAdepter);


        databaseReference = FirebaseDatabase.getInstance().getReference().child("post");
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
                    progressBar.setVisibility(View.GONE);
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



    public class Adapter extends androidx.recyclerview.widget.RecyclerView.Adapter<PostViewActivity.Adapter.MyHolder>
    {

        Context context;
        List<DataPost>dataPosts;

        public Adapter(Context context, List<DataPost> data2Lists) {
            this.context = context;
            this.dataPosts = data2Lists;
        }

        @NonNull
        @Override
        public PostViewActivity.Adapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.rv_post_layout,parent,false);
            PostViewActivity.Adapter.MyHolder HV = new PostViewActivity.Adapter.MyHolder(view);

            return HV;
        }

        @Override
        public int getItemCount() {
            return dataPosts.size();
        }

        @Override
        public void onBindViewHolder(@NonNull final PostViewActivity.Adapter.MyHolder holder, final  int position) {

            holder.user_name.setText(dataPosts.get(position).getPostcreatername());
            holder.user_number.setText(dataPosts.get(position).getDonorreciverphone());
            holder.user_blood.setText(dataPosts.get(position).getBloodgroup());
            holder.user_detials.setText(dataPosts.get(position).getNeedhospital());

            holder.top_username.setText(dataPosts.get(position).getPostcreatername());
            holder.reasonTV.setText(dataPosts.get(position).getPatientproblem());
            holder.currenttime.setText(dataPosts.get(position).getPostid());
            holder.accpetok.setText(dataPosts.get(position).getAccpetby());



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

                    Intent intent = new Intent(PostViewActivity.this,PostDetialsActivity.class);
                    intent.putExtra("postid",dataPosts.get(position).getPostid());
                    intent.putExtra("accpetbyuid",dataPosts.get(position).getAccpetbyuid());
                    intent.putExtra("postcreatoruid",dataPosts.get(position).getPostcreateruid());
                    startActivity(intent);

                }
            });

            holder.newaccpetbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    Calendar calForDate =Calendar.getInstance();
                    SimpleDateFormat currentDateFormat2=new SimpleDateFormat("MMM dd yyyy");
                    currentDate=currentDateFormat2.format(calForDate.getTime());


                    Calendar calForTime =Calendar.getInstance();
                    SimpleDateFormat currentTimeFormat2=new SimpleDateFormat("hh:mm:ss a");
                    currentTime=currentTimeFormat2.format(calForTime.getTime());


                    databaseReference = FirebaseDatabase.getInstance().getReference();
                    HashMap accpetbye = new HashMap();

                    accpetbye.put("accpetby","Already accepted by "+accpetby);
                    accpetbye.put("accpetbytime",currentTime);
                    accpetbye.put("accpetbydate",currentDate);
                    accpetbye.put("accpetbyuid",currentUserID);

                    databaseReference.child("post").child(dataPosts.get(position).getPostid()).updateChildren(accpetbye).addOnCompleteListener(new OnCompleteListener() {
                        @Override
                        public void onComplete(@NonNull Task task) {
                            if (task.isSuccessful()){
                                FcmNotificationsSender notificationsSender = new FcmNotificationsSender("/topics/all",accpetby+ " Accepted "+ dataPosts.get(position).getBloodgroup()+" Blood Request",
                                        " Please check your post status",getApplicationContext(),PostViewActivity.this);
                                notificationsSender.SendNotifications();
                            }
                        }
                    });
                    databaseReference.child("mypost").child(dataPosts.get(position).getPostcreateruid()).child(dataPosts.get(position).getPostid()).updateChildren(accpetbye);

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

                user_name = (TextView)itemView.findViewById(R.id.top_username);
                user_number = (TextView)itemView.findViewById(R.id.user_numberTV);
                user_blood=itemView.findViewById(R.id.bloodGroupTV);

                user_detials=itemView.findViewById(R.id.rv_hospital);
                newaccpetbtn=itemView.findViewById(R.id.rv_accept_btn);
                accpetok=itemView.findViewById(R.id.rv_accept_ok);

                top_username=itemView.findViewById(R.id.top_username);
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