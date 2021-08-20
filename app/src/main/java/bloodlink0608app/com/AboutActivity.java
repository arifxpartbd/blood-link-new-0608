package bloodlink0608app.com;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        findViewById(R.id.arifcall).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_DIAL);
                String phone = "+8801792927676";
                try {
                    if (phone.trim().isEmpty()) {
                        Toast.makeText(getApplicationContext(), "Number not Foount", Toast.LENGTH_LONG).show();

                    } else {
                        intent.setData(Uri.parse("tel:" + phone));

                    }
                    if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

                        Toast.makeText(AboutActivity.this, "Please Grant Permission", Toast.LENGTH_LONG).show();
                        requestPermission();
                    } else {
                        startActivity(intent);
                    }
                } catch (Exception e) {

                }
            }
        });

        findViewById(R.id.ariffb).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentarif = new Intent(Intent.ACTION_VIEW, Uri.parse("fb://facewebmodal/f?href=https://www.facebook.com/arifxpartbd"));
                startActivity(intentarif);
            }
        });
        findViewById(R.id.arifmail).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = "arifkhancsebd@gmail.com";

                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto", email, null));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Blood Link 0608)");
                emailIntent.putExtra(Intent.EXTRA_TEXT, "Hello ");
                startActivity(Intent.createChooser(emailIntent, "Send email..."));
            }
        });



        findViewById(R.id.saifcall).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_DIAL);
                String phone = "+8801716000012";
                try {
                    if (phone.trim().isEmpty()) {
                        Toast.makeText(getApplicationContext(), "Number not Foount", Toast.LENGTH_LONG).show();

                    } else {
                        intent.setData(Uri.parse("tel:" + phone));

                    }
                    if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

                        Toast.makeText(AboutActivity.this, "Please Grant Permission", Toast.LENGTH_LONG).show();
                        requestPermission();
                    } else {
                        startActivity(intent);
                    }
                } catch (Exception e) {

                }
            }
        });

        findViewById(R.id.saiffb).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentarif = new Intent(Intent.ACTION_VIEW, Uri.parse("fb://facewebmodal/f?href=https://www.facebook.com/saifuddinshakilBD"));
                startActivity(intentarif);
            }
        });


        findViewById(R.id.saifmail).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = "saif.csebd@gmail.com";

                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto", email, null));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Blood Link 0608)");
                emailIntent.putExtra(Intent.EXTRA_TEXT, "Hello ");
                startActivity(Intent.createChooser(emailIntent, "Send email..."));
            }
        });
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(AboutActivity.this, new String[]{Manifest.permission.CALL_PHONE}, 1);
    }@Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId()==android.R.id.home)
        {
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}