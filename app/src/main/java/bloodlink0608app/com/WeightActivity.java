package bloodlink0608app.com;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import bloodlink0608app.com.databinding.ActivityWeightBinding;

public class WeightActivity extends AppCompatActivity {

    ActivityWeightBinding binding;

    private FirebaseAuth mAuth;
    private ProgressDialog loadingbar;

    private String Weight="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWeightBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mAuth=FirebaseAuth.getInstance();
        loadingbar = new ProgressDialog(this);


        binding.less50kg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Weight="Less 50 KG";
                binding.less50kg.setBackgroundResource(R.drawable.background_selected_tv);
                binding.up50kg.setBackgroundResource(0);
                binding.singnUp4NextButton.setVisibility(View.VISIBLE);
            }
        });
        binding.up50kg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Weight="UP 50 KG";
                binding.up50kg.setBackgroundResource(R.drawable.background_selected_tv);
                binding.less50kg.setBackgroundResource(0);
                binding.singnUp4NextButton.setVisibility(View.VISIBLE);
            }
        });
        binding.singnUp4NextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loadingbar.setTitle("Saving Your Information");
                loadingbar.setMessage("Please wait");
                loadingbar.setCanceledOnTouchOutside(true);
                loadingbar.show();

                String currentuserId= FirebaseAuth.getInstance().getUid();
                HashMap info = new HashMap();

                DatabaseReference user = FirebaseDatabase.getInstance().getReference().child("User")
                        .child(currentuserId);
                info.put("weight",Weight);

                user.updateChildren(info).addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {

                        loadingbar.dismiss();
                        if (task.isSuccessful()){
                            Intent intent = new Intent(WeightActivity.this,GenderSelectActivity.class);

                            startActivity(intent);

                        }else {
                            loadingbar.dismiss();
                            String message = task.getException().toString();
                            Toast.makeText(WeightActivity.this,"Error: "+message,Toast.LENGTH_LONG).show();
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
}