package bloodlink0608app.com;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.util.ArrayList;
import java.util.List;

public class AmbulanceActivity extends AppCompatActivity {

    private ListView ambulanceLV;

    private AlertDialog.Builder alertDialogBuilder;
    ArrayAdapter<String> adapter;

    private AdView mAdView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ambulance);


        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);



        ambulanceLV = findViewById(R.id.ambulancelistviewId);

        String[] ambulanceList = getResources().getStringArray(R.array.ambulance);


        adapter = new ArrayAdapter<String>(AmbulanceActivity.this, R.layout.ambulance_layout, R.id.ambulanceviewId, ambulanceList);

        ambulanceLV.setAdapter(adapter);

        ambulanceLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {


                alertDialogBuilder = new AlertDialog.Builder(AmbulanceActivity.this);
                alertDialogBuilder.setTitle("CALL");
                alertDialogBuilder.setMessage("Do You Want to Call?");
                alertDialogBuilder.setIcon(R.drawable.callicon);

                alertDialogBuilder.setNeutralButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                alertDialogBuilder.setPositiveButton("Call", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if (position == 0) {
                            Intent intent = new Intent(Intent.ACTION_DIAL);

                            String phone = "01765436700";

                            try {
                                if (phone.trim().isEmpty()) {
                                    Toast.makeText(getApplicationContext(), "Number not Foount", Toast.LENGTH_LONG).show();

                                } else {
                                    intent.setData(Uri.parse("tel:" + phone));

                                }
                                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

                                    Toast.makeText(AmbulanceActivity.this, "Please Grant Permission", Toast.LENGTH_LONG).show();
                                    requestpermision();
                                } else {
                                    startActivity(intent);
                                }
                            } catch (Exception e) {

                                //Log.d(TAG, "onClick: "+e.getLocalizedMessage());

                            }
                        }
                        if (position == 1) {
                            Intent intent = new Intent(Intent.ACTION_DIAL);

                            String phone = "01941354079";

                            try {
                                if (phone.trim().isEmpty()) {
                                    Toast.makeText(getApplicationContext(), "Number not Foount", Toast.LENGTH_LONG).show();

                                } else {
                                    intent.setData(Uri.parse("tel:" + phone));

                                }
                                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

                                    Toast.makeText(AmbulanceActivity.this, "Please Grant Permission", Toast.LENGTH_LONG).show();
                                    requestpermision();
                                } else {
                                    startActivity(intent);
                                }
                            } catch (Exception e) {

                            }
                        }
                        if (position == 2) {
                            Intent intent = new Intent(Intent.ACTION_DIAL);

                            String phone = "01998556514";

                            try {
                                if (phone.trim().isEmpty()) {
                                    Toast.makeText(getApplicationContext(), "Number not Foount", Toast.LENGTH_LONG).show();

                                } else {
                                    intent.setData(Uri.parse("tel:" + phone));

                                }
                                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

                                    Toast.makeText(AmbulanceActivity.this, "Please Grant Permission", Toast.LENGTH_LONG).show();
                                    requestpermision();
                                } else {
                                    startActivity(intent);
                                }
                            } catch (Exception e) {

                            }
                        }
                        if (position == 3) {
                            Intent intent = new Intent(Intent.ACTION_DIAL);

                            String phone = "01761853924";

                            try {
                                if (phone.trim().isEmpty()) {
                                    Toast.makeText(getApplicationContext(), "Number not Foount", Toast.LENGTH_LONG).show();

                                } else {
                                    intent.setData(Uri.parse("tel:" + phone));

                                }
                                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

                                    Toast.makeText(AmbulanceActivity.this, "Please Grant Permission", Toast.LENGTH_LONG).show();
                                    requestpermision();
                                } else {
                                    startActivity(intent);
                                }
                            } catch (Exception e) {

                            }
                        }
                        if (position == 4) {
                            Intent intent = new Intent(Intent.ACTION_DIAL);

                            String phone = "01671059513";

                            try {
                                if (phone.trim().isEmpty()) {
                                    Toast.makeText(getApplicationContext(), "Number not Foount", Toast.LENGTH_LONG).show();

                                } else {
                                    intent.setData(Uri.parse("tel:" + phone));

                                }
                                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

                                    Toast.makeText(AmbulanceActivity.this, "Please Grant Permission", Toast.LENGTH_LONG).show();
                                    requestpermision();
                                } else {
                                    startActivity(intent);
                                }
                            } catch (Exception e) {

                            }
                        }
                        if (position == 5) {
                            Intent intent = new Intent(Intent.ACTION_DIAL);

                            String phone = "01937697201";

                            try {
                                if (phone.trim().isEmpty()) {
                                    Toast.makeText(getApplicationContext(), "Number not Foount", Toast.LENGTH_LONG).show();

                                } else {
                                    intent.setData(Uri.parse("tel:" + phone));

                                }
                                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

                                    Toast.makeText(AmbulanceActivity.this, "Please Grant Permission", Toast.LENGTH_LONG).show();
                                    requestpermision();
                                } else {
                                    startActivity(intent);
                                }
                            } catch (Exception e) {

                            }
                        }
                        if (position == 6) {
                            Intent intent = new Intent(Intent.ACTION_DIAL);

                            String phone = "01721520585";

                            try {
                                if (phone.trim().isEmpty()) {
                                    Toast.makeText(getApplicationContext(), "Number not Foount", Toast.LENGTH_LONG).show();

                                } else {
                                    intent.setData(Uri.parse("tel:" + phone));

                                }
                                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

                                    Toast.makeText(AmbulanceActivity.this, "Please Grant Permission", Toast.LENGTH_LONG).show();
                                    requestpermision();
                                } else {
                                    startActivity(intent);
                                }
                            } catch (Exception e) {

                            }
                        }
                        if (position == 7) {
                            Intent intent = new Intent(Intent.ACTION_DIAL);

                            String phone = "01715636615";

                            try {
                                if (phone.trim().isEmpty()) {
                                    Toast.makeText(getApplicationContext(), "Number not Foount", Toast.LENGTH_LONG).show();

                                } else {
                                    intent.setData(Uri.parse("tel:" + phone));

                                }
                                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

                                    Toast.makeText(AmbulanceActivity.this, "Please Grant Permission", Toast.LENGTH_LONG).show();
                                    requestpermision();
                                } else {
                                    startActivity(intent);
                                }
                            } catch (Exception e) {

                            }
                        }
                        if (position == 8) {
                            Intent intent = new Intent(Intent.ACTION_DIAL);

                            String phone = "01716159228";

                            try {
                                if (phone.trim().isEmpty()) {
                                    Toast.makeText(getApplicationContext(), "Number not Foount", Toast.LENGTH_LONG).show();

                                } else {
                                    intent.setData(Uri.parse("tel:" + phone));

                                }
                                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

                                    Toast.makeText(AmbulanceActivity.this, "Please Grant Permission", Toast.LENGTH_LONG).show();
                                    requestpermision();
                                } else {
                                    startActivity(intent);
                                }
                            } catch (Exception e) {

                            }
                        }
                        if (position == 9) {
                            Intent intent = new Intent(Intent.ACTION_DIAL);

                            String phone = "01911506698";

                            try {
                                if (phone.trim().isEmpty()) {
                                    Toast.makeText(getApplicationContext(), "Number not Foount", Toast.LENGTH_LONG).show();

                                } else {
                                    intent.setData(Uri.parse("tel:" + phone));

                                }
                                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

                                    Toast.makeText(AmbulanceActivity.this, "Please Grant Permission", Toast.LENGTH_LONG).show();
                                    requestpermision();
                                } else {
                                    startActivity(intent);
                                }
                            } catch (Exception e) {

                            }
                        }
                        if (position == 10) {
                            Intent intent = new Intent(Intent.ACTION_DIAL);

                            String phone = "01761859324";

                            try {
                                if (phone.trim().isEmpty()) {
                                    Toast.makeText(getApplicationContext(), "Number not Foount", Toast.LENGTH_LONG).show();

                                } else {
                                    intent.setData(Uri.parse("tel:" + phone));

                                }
                                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

                                    Toast.makeText(AmbulanceActivity.this, "Please Grant Permission", Toast.LENGTH_LONG).show();
                                    requestpermision();
                                } else {
                                    startActivity(intent);
                                }
                            } catch (Exception e) {

                            }
                        }
                        if (position == 11) {
                            Intent intent = new Intent(Intent.ACTION_DIAL);

                            String phone = "01786003430";

                            try {
                                if (phone.trim().isEmpty()) {
                                    Toast.makeText(getApplicationContext(), "Number not Foount", Toast.LENGTH_LONG).show();

                                } else {
                                    intent.setData(Uri.parse("tel:" + phone));

                                }
                                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

                                    Toast.makeText(AmbulanceActivity.this, "Please Grant Permission", Toast.LENGTH_LONG).show();
                                    requestpermision();
                                } else {
                                    startActivity(intent);
                                }
                            } catch (Exception e) {

                            }
                        }
                        if (position == 12) {
                            Intent intent = new Intent(Intent.ACTION_DIAL);

                            String phone = "01877744622";

                            try {
                                if (phone.trim().isEmpty()) {
                                    Toast.makeText(getApplicationContext(), "Number not Foount", Toast.LENGTH_LONG).show();

                                } else {
                                    intent.setData(Uri.parse("tel:" + phone));

                                }
                                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

                                    Toast.makeText(AmbulanceActivity.this, "Please Grant Permission", Toast.LENGTH_LONG).show();
                                    requestpermision();
                                } else {
                                    startActivity(intent);
                                }
                            } catch (Exception e) {

                            }
                        }

                        if (position == 13) {
                            Intent intent = new Intent(Intent.ACTION_DIAL);

                            String phone = "01819380000";

                            try {
                                if (phone.trim().isEmpty()) {
                                    Toast.makeText(getApplicationContext(), "Number not Foount", Toast.LENGTH_LONG).show();

                                } else {
                                    intent.setData(Uri.parse("tel:" + phone));

                                }
                                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

                                    Toast.makeText(AmbulanceActivity.this, "Please Grant Permission", Toast.LENGTH_LONG).show();
                                    requestpermision();
                                } else {
                                    startActivity(intent);
                                }
                            } catch (Exception e) {

                            }
                        }
                        if (position == 14) {
                            Intent intent = new Intent(Intent.ACTION_DIAL);

                            String phone = "031-650000";

                            try {
                                if (phone.trim().isEmpty()) {
                                    Toast.makeText(getApplicationContext(), "Number not Foount", Toast.LENGTH_LONG).show();

                                } else {
                                    intent.setData(Uri.parse("tel:" + phone));

                                }
                                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

                                    Toast.makeText(AmbulanceActivity.this, "Please Grant Permission", Toast.LENGTH_LONG).show();
                                    requestpermision();
                                } else {
                                    startActivity(intent);
                                }
                            } catch (Exception e) {

                            }
                        }
                        if (position == 15) {
                            Intent intent = new Intent(Intent.ACTION_DIAL);

                            String phone = "031-619584";

                            try {
                                if (phone.trim().isEmpty()) {
                                    Toast.makeText(getApplicationContext(), "Number not Foount", Toast.LENGTH_LONG).show();

                                } else {
                                    intent.setData(Uri.parse("tel:" + phone));

                                }
                                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

                                    Toast.makeText(AmbulanceActivity.this, "Please Grant Permission", Toast.LENGTH_LONG).show();
                                    requestpermision();
                                } else {
                                    startActivity(intent);
                                }
                            } catch (Exception e) {

                            }
                        }
                        if (position == 16) {
                            Intent intent = new Intent(Intent.ACTION_DIAL);

                            String phone = "01716074497";

                            try {
                                if (phone.trim().isEmpty()) {
                                    Toast.makeText(getApplicationContext(), "Number not Foount", Toast.LENGTH_LONG).show();

                                } else {
                                    intent.setData(Uri.parse("tel:" + phone));

                                }
                                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

                                    Toast.makeText(AmbulanceActivity.this, "Please Grant Permission", Toast.LENGTH_LONG).show();
                                    requestpermision();
                                } else {
                                    startActivity(intent);
                                }
                            } catch (Exception e) {

                            }
                        }

                    }
                });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }

            private void requestpermision() {

                ActivityCompat.requestPermissions(AmbulanceActivity.this, new String[]{Manifest.permission.CALL_PHONE}, 1);

            }
        });


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}