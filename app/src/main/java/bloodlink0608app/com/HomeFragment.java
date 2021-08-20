package bloodlink0608app.com;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {

    private View homeFragment;
    private CardView donor, requestpostcard,request_feed,cinfo, mypost,donationcard;
    ArrayList<String> arrayList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        homeFragment =inflater.inflate(R.layout.fragment_home,container,false);

        ImageSlider imageSlider = homeFragment.findViewById(R.id.image_slider);

        donor = homeFragment.findViewById(R.id.donorsCard);
        requestpostcard = homeFragment.findViewById(R.id.post_request_card);
        request_feed = homeFragment.findViewById(R.id.request_feed);
        cinfo = homeFragment.findViewById(R.id.c_info);
        mypost = homeFragment.findViewById(R.id.mypostcard);
        donationcard = homeFragment.findViewById(R.id.donationCard);

        donationcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"We are working, as soon as possible, this button will enable",Toast.LENGTH_LONG).show();
            }
        });

        List<SlideModel> imagelist = new ArrayList<>();

        imagelist.add(new SlideModel(R.drawable.bannertwo,"২য় বর্ষপূর্তি",ScaleTypes.CENTER_INSIDE));
        imagelist.add(new SlideModel(R.drawable.bannerone,"",ScaleTypes.CENTER_INSIDE));


        imageSlider.setImageList(imagelist,ScaleTypes.CENTER_INSIDE);

        mypost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentmypost = new Intent(getContext(),MyPostActivity.class);
                startActivity(intentmypost);
            }
        });

        cinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentpreprimary = new Intent(getContext(),WebBrowser.class);
                intentpreprimary.putExtra("link",("https://corona.gov.bd/").toString());
                intentpreprimary.putExtra("title","Corona Info");
                startActivity(intentpreprimary);
            }
        });

        donor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),SelectSearchActivity.class);
                startActivity(intent);
            }
        });
        requestpostcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),PostRequestActivity.class);
                startActivity(intent);
            }
        });

        request_feed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),PostViewActivity.class);
                startActivity(intent);
            }
        });


        return homeFragment;
    }
}