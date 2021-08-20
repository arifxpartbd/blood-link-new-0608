package bloodlink0608app.com;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class SelectSearchActivity extends AppCompatActivity {


    public static final String TAG = "SelectSearchActivity";
    private String bloodGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_search);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

    }

    public void aPositiveClicked(View view) {
        Log.d(TAG, "aPositiveClicked: started");
        bloodGroup = "A+";

        Intent intent = new Intent(this, SearchActivity.class);
        intent.putExtra("bloodGroup", bloodGroup);
        startActivity(intent);

    }

    public void aNegativeClicked(View view) {
        Log.d(TAG, "aNegativeClicked: started");
        bloodGroup = "A-";
        Intent intent = new Intent(this, SearchActivity.class);
        intent.putExtra("bloodGroup", bloodGroup);
        startActivity(intent);
    }

    public void bPositiveClicked(View view) {
        Log.d(TAG, "bPositiveClicked: Clicked");
        bloodGroup = "B+";
        Intent intent = new Intent(this, SearchActivity.class);
        intent.putExtra("bloodGroup", bloodGroup);
        startActivity(intent);
    }

    public void bNegativeClicked(View view) {
        Log.d(TAG, "bNegativeClicked: Clicked");
        bloodGroup = "B-";
        Intent intent = new Intent(this, SearchActivity.class);
        intent.putExtra("bloodGroup", bloodGroup);
        startActivity(intent);
    }


    public void oPositiveClicked(View view) {
        Log.d(TAG, "oPositiveClicked: Clicked");
        bloodGroup = "O+";
        Intent intent = new Intent(this, SearchActivity.class);
        intent.putExtra("bloodGroup", bloodGroup);
        startActivity(intent);
    }

    public void oNegativeClicked(View view) {
        Log.d(TAG, "oNegativeClicked: Clicked");
        bloodGroup = "O-";
        Intent intent = new Intent(this, SearchActivity.class);
        intent.putExtra("bloodGroup", bloodGroup);
        startActivity(intent);
    }

    public void abPositiveClicked(View view) {
        Log.d(TAG, "abPositiveClicked: Clicked");
        bloodGroup = "AB+";
        Intent intent = new Intent(this, SearchActivity.class);
        intent.putExtra("bloodGroup", bloodGroup);
        startActivity(intent);
    }

    public void abNegativeClicked(View view) {
        Log.d(TAG, "abNegativeClicked: Clicked");
        bloodGroup = "AB-";
        Intent intent = new Intent(this, SearchActivity.class);
        intent.putExtra("bloodGroup", bloodGroup);
        startActivity(intent);
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