package bloodlink0608app.com;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment {

    private View profileFragment;
    private TextView username,userbloodgroup,usermobile,usermail,userlastblooddonate, logout, status, ratingus,aboutus,invite,privacy;
    private ImageView profileimageadd;
    private CircleImageView profileImage;

    private String currentUserID;
    private FirebaseAuth mAuth;
    private DatabaseReference RootRef;
    private Button update;

    FirebaseStorage storage;
    FirebaseDatabase database;
    private CardView userprofileup,mypoststatus;

    private ProgressDialog loadingbar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();
        RootRef = FirebaseDatabase.getInstance().getReference();

        storage = FirebaseStorage.getInstance();
        database = FirebaseDatabase.getInstance();

        profileFragment = inflater.inflate(R.layout.fragment_profile,container,false);



        loadingbar = new ProgressDialog(getContext());
        username = profileFragment.findViewById(R.id.proifle_username);
        userbloodgroup = profileFragment.findViewById(R.id.profile_blood_group);
        //update = profileFragment.findViewById(R.id.updateId);
        profileImage = profileFragment.findViewById(R.id.profile_Image_View);
        profileimageadd = profileFragment.findViewById(R.id.image_add_button);
        usermobile = profileFragment.findViewById(R.id.profile_mobile);
        usermail = profileFragment.findViewById(R.id.profile_email);
        userlastblooddonate = profileFragment.findViewById(R.id.profile_last_blood_give);
        logout = profileFragment.findViewById(R.id.logoutBtn);
        mypoststatus =profileFragment.findViewById(R.id.mypostcard);
        userprofileup = profileFragment.findViewById(R.id.profileupdatecard);
        status = profileFragment.findViewById(R.id.profile_status);

        ratingus = profileFragment.findViewById(R.id.ratingUs);
        aboutus = profileFragment.findViewById(R.id.aboutId);
        invite = profileFragment.findViewById(R.id.inviteId);
        privacy = profileFragment.findViewById(R.id.privacyId);



        profileFragment.findViewById(R.id.ratingUs).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=bloodlink0608app.com"));
                startActivity(intent);
            }
        });

        profileFragment.findViewById(R.id.update_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=bloodlink0608app.com"));
                startActivity(intent);
            }
        });
        profileFragment.findViewById(R.id.ambulancecard).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentambulance = new Intent(getContext(),AmbulanceActivity.class);
                startActivity(intentambulance);
            }
        });
        profileFragment.findViewById(R.id.facebook_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentfb = new Intent(Intent.ACTION_VIEW, Uri.parse("fb://facewebmodal/f?href=https://www.facebook.com/bloodlink0608"));
                startActivity(intentfb);
            }
        });


        invite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentshare = new Intent(Intent.ACTION_SEND);
                intentshare.setType("text/plain");
                String subject = "Blood Link 0608";
                String body = "https://play.google.com/store/apps/details?id=bloodlink0608app.com";

                intentshare.putExtra(Intent.EXTRA_SUBJECT, subject);
                intentshare.putExtra(Intent.EXTRA_TEXT, body);
                startActivity(Intent.createChooser(intentshare, "Complete share"));
            }
        });
        aboutus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentabout=new Intent(getContext(),AboutActivity.class);
                startActivity(intentabout);
            }
        });
        Retrivedata();

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Intent loginintent = new Intent(getContext(), LoginActivity.class);
                loginintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(loginintent);
            }
        });

        userprofileup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),EditProfileActivity.class);
                startActivity(intent);
            }
        });


        profileimageadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent ,33);
            }
        });
        profileFragment.findViewById(R.id.privacyId).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentprivacy = new Intent(getContext(),PrivacyPolicy.class);
                startActivity(intentprivacy);
            }
        });

        mypoststatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intenttips = new Intent(getContext(),TipsActivity.class);
                startActivity(intenttips);
                //Toast.makeText(getContext(),"We are working now, as soon as possible we are active this button",Toast.LENGTH_LONG).show();
            }
        });



        return profileFragment;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        loadingbar.setTitle("Saving Your Profile Image");
        loadingbar.setMessage("Please wait");
        loadingbar.setCanceledOnTouchOutside(true);
        loadingbar.show();

      try {
          if (data.getData() != null){
              Uri sFile = data.getData();
              profileImage.setImageURI(sFile);

              final StorageReference reference = storage.getReference().child("profile_picture")
                      .child(FirebaseAuth.getInstance().getUid());

              reference.putFile(sFile).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                  @Override
                  public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                      reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                          @Override
                          public void onSuccess(Uri uri) {
                              database.getReference().child("User")
                                      .child(FirebaseAuth.getInstance().getUid())
                                      .child("image").setValue(uri.toString());
                              loadingbar.dismiss();
                          }
                      });
                      Toast.makeText(getContext(),"Image Upload Sccessfuly",Toast.LENGTH_LONG).show();
                      loadingbar.dismiss();
                  }
              });
          }

      }catch (Exception e){
          loadingbar.dismiss();
      }
    }

    private void Retrivedata() {

        RootRef.child("User").child(currentUserID)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        User user = snapshot.getValue(User.class);

                        Picasso.get()
                                .load(user.getImage())
                                .placeholder(R.drawable.logowhite)
                                .into(profileImage);

                        username.setText(user.getUsername());
                        userbloodgroup.setText(user.getBloodGroup());
                        usermobile.setText(user.getPhone1());
                        usermail.setText(user.getEmail());
                        userlastblooddonate.setText(user.getLastdate());
                        status.setText(user.getStatus());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

}