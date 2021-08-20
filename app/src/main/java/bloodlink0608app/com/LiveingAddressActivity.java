package bloodlink0608app.com;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

import bloodlink0608app.com.databinding.ActivityContactInformationPageBinding;
import bloodlink0608app.com.databinding.ActivityLiveingAddressBinding;

public class LiveingAddressActivity extends AppCompatActivity {


    ActivityLiveingAddressBinding binding;
    private ProgressDialog loadingbar;
    private FirebaseAuth mAuth;

    ArrayList<String> arrayList;
    Dialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLiveingAddressBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mAuth=FirebaseAuth.getInstance();
        loadingbar = new ProgressDialog(this);

        binding.addressnextId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String address = binding.addressId.getText().toString();
                String city = binding.cityId.getText().toString();
                String postcode = binding.postcodeId.getText().toString();

                if (address.isEmpty()){
                    binding.addressId.setError("Please type your address");
                    binding.addressId.requestFocus();
                    return;
                }
                if (city.isEmpty()){
                    binding.cityId.setError("Please type your city");
                    binding.cityId.requestFocus();
                    return;
                }if (city.equals("Select Your District")){
                    Toast.makeText(LiveingAddressActivity.this,"Please Select your District",Toast.LENGTH_LONG).show();
                    return;
                }

                else {

                    loadingbar.setTitle("Saving Your Information");
                    loadingbar.setMessage("Please wait");
                    loadingbar.setCanceledOnTouchOutside(true);
                    loadingbar.show();

                    String currentuserId= FirebaseAuth.getInstance().getUid();
                    HashMap info = new HashMap();

                    DatabaseReference user = FirebaseDatabase.getInstance().getReference().child("User")
                            .child(currentuserId);
                    info.put("village",address);
                    info.put("district",city);
                    info.put("postcode",postcode);
                    user.updateChildren(info).addOnCompleteListener(new OnCompleteListener() {
                        @Override
                        public void onComplete(@NonNull Task task) {
                            if (task.isSuccessful()){
                                loadingbar.dismiss();
                                Intent intent = new Intent(LiveingAddressActivity.this,AgeActivity.class);
                                startActivity(intent);

                            }else {
                                loadingbar.dismiss();
                                String message = task.getException().toString();
                                Toast.makeText(LiveingAddressActivity.this,"Error: "+message,Toast.LENGTH_LONG).show();
                            }
                        }
                    });

                }
            }
        });

        arrayList = new ArrayList<>();

        arrayList.add("Bagerhat");
        arrayList.add("Bandarban");
        arrayList.add("Barguna");
        arrayList.add("Barisal");
        arrayList.add("Bhola");
        arrayList.add("Bogra");
        arrayList.add("Brahmanbaria");
        arrayList.add("Chandpur");
        arrayList.add("Chittagong");
        arrayList.add("Chuadanga");
        arrayList.add("Comilla");
        arrayList.add("Cox's Bazar");
        arrayList.add("Dhaka");
        arrayList.add("Dinajpur");
        arrayList.add("Faridpur");
        arrayList.add("Feni");
        arrayList.add("Gaibandha");
        arrayList.add("Gazipur");
        arrayList.add("Gopalganj");
        arrayList.add("Habiganj");
        arrayList.add("Jaipurhat");
        arrayList.add("Jamalpur");
        arrayList.add("Jessore");
        arrayList.add("Jhalakati");
        arrayList.add("Jhenaidah");
        arrayList.add("Khagrachari");
        arrayList.add("Khulna");
        arrayList.add("Kishoreganj");
        arrayList.add("Kurigram");
        arrayList.add("Kushtia");
        arrayList.add("Lakshmipur");
        arrayList.add("Lalmonirhat");
        arrayList.add("Madaripur");
        arrayList.add("Magura");
        arrayList.add("Manikganj");
        arrayList.add("Meherpur");
        arrayList.add("Moulvibazar");
        arrayList.add("Munshiganj");
        arrayList.add("Mymensingh");
        arrayList.add("Naogaon");
        arrayList.add("Narail");
        arrayList.add("Narayanganj");
        arrayList.add("Narsingdi");
        arrayList.add("Natore");
        arrayList.add("Nawabganj");
        arrayList.add("Netrakona");
        arrayList.add("Nilphamari");
        arrayList.add("Noakhali");
        arrayList.add("Pabna");
        arrayList.add("Panchagarh");
        arrayList.add("Parbattya Chattagram");
        arrayList.add("Patuakhali");
        arrayList.add("Pirojpur");
        arrayList.add("Rajbari");
        arrayList.add("Rajshahi");
        arrayList.add("Rangpur");
        arrayList.add("Satkhira");
        arrayList.add("Shariatpur");
        arrayList.add("Sherpur");
        arrayList.add("Sirajganj");
        arrayList.add("Sunamganj");
        arrayList.add("Sylhet");
        arrayList.add("Tangail");
        arrayList.add("Thakurgaon");


        binding.cityId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new Dialog(LiveingAddressActivity.this);
                dialog.setContentView(R.layout.dialog_searchable_spinner);
                //set custom height and wedight
                dialog.getWindow().setLayout(650,800);

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                dialog.show();
                EditText editText = dialog.findViewById(R.id.edit_text);
                ListView listView = dialog.findViewById(R.id.list_view);

                ArrayAdapter<String> adapter = new ArrayAdapter<>(LiveingAddressActivity.this
                        , android.R.layout.simple_list_item_1,arrayList);

                listView.setAdapter(adapter);

                editText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                        adapter.getFilter().filter(s);
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        binding.cityId.setText(adapter.getItem(position));
                        dialog.dismiss();

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