package bloodlink0608app.com;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.HashMap;
import java.util.Objects;

import bloodlink0608app.com.databinding.ActivitySignUpPage1Binding;

public class SignUpPage1 extends AppCompatActivity {

    ActivitySignUpPage1Binding binding;

    private FirebaseAuth mAuth;
    private ProgressDialog loadingbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpPage1Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth=FirebaseAuth.getInstance();
        loadingbar = new ProgressDialog(this);




        binding.singnup1NextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = binding.userMailId.getText().toString();
                String password = binding.usrPasswordId.getText().toString();
                String confirmpassword = binding.confirmPasswordId.getText().toString();
                String username = binding.userNameId.getText().toString();


                if (email.isEmpty()){
                    binding.userMailId.setError("Please type your mail address");
                    binding.userMailId.requestFocus();
                    return;
                }if (password.isEmpty()){
                    binding.usrPasswordId.setError("Please type your password");
                    binding.usrPasswordId.requestFocus();
                    return;
                }if (password.length()<6){
                    binding.usrPasswordId.setError("Minimum Password 6 word");
                    binding.usrPasswordId.requestFocus();
                    return;
                }if (!confirmpassword.matches(password)){
                    binding.usrPasswordId.setError("Password not match");
                    binding.confirmPasswordId.setError("Password not match");
                    binding.confirmPasswordId.requestFocus();
                    binding.usrPasswordId.requestFocus();
                    return;
                }else {

                    loadingbar.setTitle("Creating new Account");
                    loadingbar.setMessage("Please wait");
                    loadingbar.setCanceledOnTouchOutside(true);
                    loadingbar.show();

                    mAuth.createUserWithEmailAndPassword(email,password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()){
                                        String currentuserId= FirebaseAuth.getInstance().getUid();
                                        String devicetoken = FirebaseInstanceId.getInstance().getToken().toString();

                                        HashMap info = new HashMap();

                                        DatabaseReference user = FirebaseDatabase.getInstance().getReference().child("User").child(currentuserId);
                                        info.put("username",username);
                                        info.put("email",email);
                                        info.put("uid",currentuserId);
                                        info.put("token",devicetoken);
                                        user.setValue(info).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                loadingbar.dismiss();

                                                if (task.isSuccessful()){
                                                    Intent intent = new Intent(SignUpPage1.this,ContactInformationPage.class);

                                                    startActivity(intent);

                                                }else {
                                                    loadingbar.dismiss();
                                                    String message = task.getException().toString();
                                                    Toast.makeText(SignUpPage1.this,"Error: "+message,Toast.LENGTH_LONG).show();
                                                }
                                            }
                                        });

                                    }else {
                                        String message = task.getException().toString();
                                        Toast.makeText(SignUpPage1.this,"Error: "+message,Toast.LENGTH_LONG).show();
                                        loadingbar.dismiss();
                                    }
                                }
                            });
                }

            }
        });
    }
}