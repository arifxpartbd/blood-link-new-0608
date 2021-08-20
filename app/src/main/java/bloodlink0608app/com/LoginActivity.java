package bloodlink0608app.com;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import bloodlink0608app.com.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {


    ActivityLoginBinding binding;

    private CheckBox saveLoginCheckBox;
    private SharedPreferences loginPreferences;
    private SharedPreferences.Editor loginPrefsEditor;
    private Boolean saveLogin;

    private FirebaseUser currentUser;

    private FirebaseAuth mAuth;
    private ProgressBar progressbarId;

    private AlertDialog.Builder alertDialogBuilder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        mAuth = FirebaseAuth.getInstance();
        currentUser =mAuth.getCurrentUser();
        progressbarId=findViewById(R.id.progressbarId);

        binding.signUpText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentsignup = new Intent(LoginActivity.this,SignUpPage1.class);
                startActivity(intentsignup);

            }
        });
        binding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();

//                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
//                startActivity(intent);
            }
        });
        binding.forgetpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentreset = new Intent(LoginActivity.this,ForgetPassword.class);
                startActivity(intentreset);
            }
        });



        //check box
        saveLoginCheckBox = (CheckBox)findViewById(R.id.saveLoginCheckBox);
        loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        loginPrefsEditor = loginPreferences.edit();


        saveLogin = loginPreferences.getBoolean("saveLogin", false);
        if (saveLogin) {
            binding.emailId.setText(loginPreferences.getString("username", ""));
            binding.passwordId.setText(loginPreferences.getString("password", ""));
            saveLoginCheckBox.setChecked(true);
        }

    }

    private void login() {
        String email = binding.emailId.getText().toString().trim();
        String password = binding.passwordId.getText().toString().trim();



//validity Check for email and password
        if (email.isEmpty()) {
            binding.emailId.setError("Enter an email address");
            binding.emailId.requestFocus();
            return;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.emailId.setError("Enter a valid email address");
            binding.emailId.requestFocus();
            return;
        }

        //checking the validity of the password
        if (password.isEmpty()) {
            binding.passwordId.setError("Enter a password");
            binding.passwordId.requestFocus();
            return;
        }
        if (password.length() < 6) {
            binding.passwordId.setError("You must give 6 charaster password");
            binding.passwordId.requestFocus();
            return;
        }



        progressbarId.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                progressbarId.setVisibility(View.GONE);
                if (task.isSuccessful()) {
                    Intent intent=new Intent(LoginActivity.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
                else {
                    alertDialogBuilder = new AlertDialog.Builder(LoginActivity.this);
                    alertDialogBuilder.setTitle("Sorry !");
                    alertDialogBuilder.setMessage("Email or Password is not correct");
                    alertDialogBuilder.setIcon(R.drawable.ic_baseline_error_24);

                    alertDialogBuilder.setNegativeButton("Try Again", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }

            }
        });

        if (saveLoginCheckBox.isChecked()) {
            loginPrefsEditor.putBoolean("saveLogin", true);
            loginPrefsEditor.putString("username", email);
            loginPrefsEditor.putString("password", password);
            loginPrefsEditor.commit();


        } else {
            loginPrefsEditor.clear();
            loginPrefsEditor.commit();
        }

    }
    @Override
    protected void onStart() {
        super.onStart();
        if (currentUser !=null)
        {
            sentUserToMainActivity();
        }
    }

    private void sentUserToMainActivity() {

        Intent loginintent=new Intent(LoginActivity.this, MainActivity.class);
        loginintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(loginintent);
        finish();
    }
}