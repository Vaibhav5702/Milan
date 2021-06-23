package com.example.milan.InterestSection;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.milan.R;

import java.util.List;

public class InterestDetailsAdapter extends RecyclerView.Adapter<InterestDetailsAdapter.ViewHolder> {
    ItemClick activity;
    List<InterestDetails> list;

    public interface ItemClick {
        void onItemClick(int i);
    }

    public InterestDetailsAdapter(Context context, List<InterestDetails> list) {
        this.list = list;
        this.activity = (ItemClick) context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView interest_icon,dropdown;
        TextView interest_text,active_rooms;

        public ViewHolder(View itemView) {
            super(itemView);
            interest_icon=itemView.findViewById(R.id.icon_interest);
            dropdown=itemView.findViewById(R.id.dropdown);
            interest_text=itemView.findViewById(R.id.interest_text);
            active_rooms=itemView.findViewById(R.id.active_rooms);
            itemView.setOnClickListener(v -> InterestDetailsAdapter.this.activity.onItemClick(InterestDetailsAdapter.this.list.indexOf((InterestDetails) v.getTag())));
        }
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.interest_details, parent, false));
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.itemView.setTag(this.list.get(position));
        switch(list.get(position).getInterest())
        {
            case "music":
                holder.interest_icon.setImageResource(R.drawable.interests_music);
                holder.interest_text.setText("Music");
                break;
            case "games":
                holder.interest_icon.setImageResource(R.drawable.interests_games);
                holder.interest_text.setText("Games");
                break;
            case "poetry":
                holder.interest_icon.setImageResource(R.drawable.interests_book);
                holder.interest_text.setText("Poetry");
                break;
            case "sports":
                holder.interest_icon.setImageResource(R.drawable.interests_ball);
                holder.interest_text.setText("Sports");
                break;
        }
    }

    public int getItemCount() {
        return this.list.size();
    }
}
