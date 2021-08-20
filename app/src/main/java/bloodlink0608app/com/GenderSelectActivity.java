package bloodlink0608app.com;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
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

import bloodlink0608app.com.databinding.ActivityGenderSelectBinding;

public class GenderSelectActivity extends AppCompatActivity {

    ActivityGenderSelectBinding binding;
    private String Gender = "";

    private FirebaseAuth mAuth;
    private ProgressDialog loadingbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGenderSelectBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        mAuth=FirebaseAuth.getInstance();
        loadingbar = new ProgressDialog(this);

        binding.femalcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Gender = "Female";
                binding.singnUp5NextButton.setVisibility(View.VISIBLE);
                binding.femalId.setBackgroundResource(R.drawable.background_selected_tv);
                binding.maleId.setBackgroundResource(0);
            }
        });
        binding.malecard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Gender = "Male";
                binding.singnUp5NextButton.setVisibility(View.VISIBLE);
                binding.maleId.setBackgroundResource(R.drawable.background_selected_tv);
                binding.femalId.setBackgroundResource(0);
            }
        });

        binding.singnUp5NextButton.setOnClickListener(new View.OnClickListener() {
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
                info.put("gender",Gender);
                user.updateChildren(info).addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()){
                            loadingbar.dismiss();
                            Intent intent = new Intent(GenderSelectActivity.this,LiveingAddressActivity.class);
                            startActivity(intent);

                        }else {
                            loadingbar.dismiss();
                            String message = task.getException().toString();
                            Toast.makeText(GenderSelectActivity.this,"Error: "+message,Toast.LENGTH_LONG).show();
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