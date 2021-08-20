package bloodlink0608app.com;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import bloodlink0608app.com.databinding.ActivityRegBloodGroupSelectBinding;

public class RegBloodGroupSelect extends AppCompatActivity {


    ActivityRegBloodGroupSelectBinding binding;
    private String BloodGroup="";
    private FirebaseAuth mAuth;
    private ProgressDialog loadingbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegBloodGroupSelectBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        mAuth=FirebaseAuth.getInstance();
        loadingbar = new ProgressDialog(this);


        binding.apositiveId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BloodGroup = "A+";
                binding.bloodgroupnextbtn.setVisibility(View.VISIBLE);
                binding.bloodgroupnextbtn.setText("Your Blood Group is "+BloodGroup+" Next");
            }
        });
        binding.anegativeid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BloodGroup = "A-";
                binding.bloodgroupnextbtn.setVisibility(View.VISIBLE);
                binding.bloodgroupnextbtn.setText("Your Blood Group is "+BloodGroup+" Next");
            }
        });
        binding.bpositiveid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BloodGroup = "B+";
                binding.bloodgroupnextbtn.setVisibility(View.VISIBLE);
                binding.bloodgroupnextbtn.setText("Your Blood Group is "+BloodGroup+" Next");
            }
        });
        binding.bnegativeid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BloodGroup = "B-";
                binding.bloodgroupnextbtn.setVisibility(View.VISIBLE);
                binding.bloodgroupnextbtn.setText("Your Blood Group is "+BloodGroup+" Next");
            }
        });

        binding.opositiveid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BloodGroup = "O+";
                binding.bloodgroupnextbtn.setVisibility(View.VISIBLE);
                binding.bloodgroupnextbtn.setText("Your Blood Group is "+BloodGroup+" Next");
            }
        });
        binding.onegativeid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BloodGroup = "O-";
                binding.bloodgroupnextbtn.setVisibility(View.VISIBLE);
                binding.bloodgroupnextbtn.setText("Your Blood Group is "+BloodGroup+" Next");
            }
        });
        binding.abpositiveid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BloodGroup = "AB+";
                binding.bloodgroupnextbtn.setVisibility(View.VISIBLE);
                binding.bloodgroupnextbtn.setText("Your Blood Group is "+BloodGroup+" Next");
            }
        });
        binding.abnegativeid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BloodGroup = "AB-";
                binding.bloodgroupnextbtn.setVisibility(View.VISIBLE);
                binding.bloodgroupnextbtn.setText("Your Blood Group is "+BloodGroup+" Next");
            }
        });

        binding.bloodgroupnextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loadingbar.setTitle("Saving Your Information");
                loadingbar.setMessage("Please wait");
                loadingbar.setCanceledOnTouchOutside(true);
                loadingbar.show();

                String currentuserId= FirebaseAuth.getInstance().getUid();
                HashMap info = new HashMap();
                info.put("bloodGroup",BloodGroup);

                DatabaseReference user = FirebaseDatabase.getInstance().getReference("User").child(currentuserId);
                user.updateChildren(info).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            loadingbar.dismiss();
                            Intent intent = new Intent(RegBloodGroupSelect.this,WeightActivity.class);
                            startActivity(intent);

                        }else {
                            loadingbar.dismiss();
                        }
                    }
                });

            }
        });

    }
}