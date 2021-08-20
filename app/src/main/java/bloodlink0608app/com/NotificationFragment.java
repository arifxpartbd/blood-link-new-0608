package bloodlink0608app.com;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;


public class NotificationFragment extends Fragment {

    private View NotificationFragment;
    private RecyclerView recyclerView;

    private String currentUserID;
    private FirebaseAuth mAuth;

    List<DataPost> dataPosts ;
    PostViewActivity.Adapter myAdapter;
    DatabaseReference databaseReference;
    private DatabaseReference RootRef;
    private String accpetby ="";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        NotificationFragment = inflater.inflate(R.layout.fragment_notification,container,false);

        RootRef = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();

        recyclerView = NotificationFragment.findViewById(R.id.recyclerId);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        dataPosts= new ArrayList<>();

        final NotificationFragment.Adapter myAdepter = new NotificationFragment.Adapter(getContext(),dataPosts);
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
                    NotificationFragment.findViewById(R.id.progress_bar).setVisibility(View.GONE);
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
        return NotificationFragment;
    }



    public class Adapter extends androidx.recyclerview.widget.RecyclerView.Adapter<NotificationFragment.Adapter.MyHolder>
    {

        Context context;
        List<DataPost>dataPosts;

        public Adapter(Context context, List<DataPost> data2Lists) {
            this.context = context;
            this.dataPosts = data2Lists;
        }

        @NonNull
        @Override
        public NotificationFragment.Adapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_view,parent,false);
            NotificationFragment.Adapter.MyHolder HV = new NotificationFragment.Adapter.MyHolder(view);

            return HV;
        }

        @Override
        public int getItemCount() {
            return dataPosts.size();
        }

        @Override
        public void onBindViewHolder(@NonNull final NotificationFragment.Adapter.MyHolder holder, final int position) {

            holder.title.setText(dataPosts.get(position).getPostcreatername()+" Created Post");
            holder.subtile.setText(dataPosts.get(position).getBloodgroup()+" Blood Need");
            holder.time.setText(dataPosts.get(position).getPostid());
            holder.bloodgroup.setText(dataPosts.get(position).getBloodgroup());


//            databaseReference.addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot snapshot) {
//                    try {
//                        if (snapshot.child(dataPosts.get(position).getAccpetby()).exists())
//                        {
//
//                            holder.accpetok.setVisibility(View.VISIBLE);
//                            //holder.cartbtn.setVisibility(View.GONE);
//                        }else
//                        {
//                            holder.newaccpetbtn.setVisibility(View.GONE);
//                            holder.accpetok.setVisibility(View.VISIBLE);
//                        }
//                    }
//                    catch (Exception e)
//                    {
//
//                    }
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError error) {
//
//                }
//            });

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(getContext(),PostDetialsActivity.class);
                    intent.putExtra("postid",dataPosts.get(position).getPostid());
                    intent.putExtra("accpetbyuid",dataPosts.get(position).getAccpetbyuid());
                    intent.putExtra("postcreatoruid",dataPosts.get(position).getPostcreateruid());
                    startActivity(intent);

                }
            });

//            holder.newaccpetbtn.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                    databaseReference = FirebaseDatabase.getInstance().getReference().child("post");
//                    HashMap accpetbye = new HashMap();
//
//                    //accpetbye.put("accpetby","already accept by "+accpetby);
//                    accpetbye.put("accpetbyuid",currentUserID);
//
//                    databaseReference.child(dataPosts.get(position).getPostid()).updateChildren(accpetbye);
//                }
//            });

        }


        class MyHolder extends  androidx.recyclerview.widget.RecyclerView.ViewHolder
        {

            private TextView time,date,title,subtile;
            private Button bloodgroup;

            public MyHolder(@NonNull View itemView) {
                super(itemView);

                time =itemView.findViewById(R.id.noti_time);
                //date =itemView.findViewById(R.id.noti_date);
                title =itemView.findViewById(R.id.noti_name);
                subtile =itemView.findViewById(R.id.noti_subtitle);
                bloodgroup =itemView.findViewById(R.id.noti_bloodgroup);




            }
        }

        // Inflate the layout for this fragment

    }

}