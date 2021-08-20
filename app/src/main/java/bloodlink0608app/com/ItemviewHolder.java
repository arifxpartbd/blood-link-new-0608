package bloodlink0608app.com;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ItemviewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView name,time,subtitle;
    public Button bloodgroup;
    public ItemviewHolder(@NonNull View itemView) {
        super(itemView);

        name = itemView.findViewById(R.id.noti_name);
        time = itemView.findViewById(R.id.noti_time);
        subtitle = itemView.findViewById(R.id.noti_subtitle);

        bloodgroup = itemView.findViewById(R.id.noti_bloodgroup);
    }

    @Override
    public void onClick(View v) {

    }
}
