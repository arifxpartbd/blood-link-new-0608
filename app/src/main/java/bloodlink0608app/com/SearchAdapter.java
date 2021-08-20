package bloodlink0608app.com;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {

    private Context context;
    private List<User> userList;

    public SearchAdapter(Context context, List<User> userList) {
        this.context = context;
        this.userList = userList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.rv_search_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final User currentUser = userList.get(position);
        holder.nameTV.setText(currentUser.getUsername());
        holder.bloodGroupTV.setText(currentUser.getBloodGroup());

        holder.donateDateTV.setText(currentUser.getLastdate());
        holder.statusTv.setText(currentUser.getStatus());
        holder.areaTV.setText(currentUser.getDistrict());

        Picasso.get()
                .load(currentUser.getImage())
                .placeholder(R.drawable.logowhite)
                .into(holder.cardimage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DonorInfoActivity.class);
                intent.putExtra("uid",currentUser.getUid().toString());
                intent.putExtra("name",currentUser.getUsername());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView bloodGroupTV, nameTV, areaTV, statusTv, donateDateTV, phoneTV;
        private CircleImageView cardimage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cardimage = itemView.findViewById(R.id.card_image);
            bloodGroupTV = itemView.findViewById(R.id.bloodGroupTV);
            nameTV = itemView.findViewById(R.id.nameTV);
            areaTV = itemView.findViewById(R.id.areaTV);
            statusTv = itemView.findViewById(R.id.statusTv);
            donateDateTV = itemView.findViewById(R.id.donateDateTV);


        }
    }
}