package com.example.milan.LoginRegister;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.milan.R;

import java.util.List;

public class InterestAdapter extends RecyclerView.Adapter<InterestAdapter.ViewHolder> {
    ItemClick activity;
    List<Interest> list;

    public interface ItemClick {
        void onItemClick(int i);
    }

    public InterestAdapter(Context context, List<Interest> list) {
        this.list = list;
        this.activity = (ItemClick) context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView interest_icon;
        TextView interest_text;
        CardView interest_card;

        public ViewHolder(View itemView) {
            super(itemView);
            this.interest_icon=itemView.findViewById(R.id.interest_icon);
            this.interest_card=itemView.findViewById(R.id.interest_card);
            this.interest_text=itemView.findViewById(R.id.interest_text);
            itemView.setOnClickListener(v -> InterestAdapter.this.activity.onItemClick(InterestAdapter.this.list.indexOf((Interest) v.getTag())));
        }
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.interest_layout, parent, false));
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.itemView.setTag(this.list.get(position));
        if(this.list.get(position).getInterest().equals("music")) {
            holder.interest_text.setText("Music");
            holder.interest_icon.setImageResource(R.drawable.music_interest);
            holder.interest_card.getBackground().setTint(Color.parseColor("#B2DFDB"));
        }
        else if(this.list.get(position).getInterest().equals("poetry")) {
            holder.interest_text.setText("Poetry");
            holder.interest_icon.setImageResource(R.drawable.poetry_interest);
            holder.interest_card.getBackground().setTint(Color.parseColor("#FFCDD2"));
        }
        else if(this.list.get(position).getInterest().equals("sports")) {
            holder.interest_text.setText("Sports");
            holder.interest_icon.setImageResource(R.drawable.sports_interest);
            holder.interest_card.getBackground().setTint(Color.parseColor("#EAEAEA"));
        }
        else if(this.list.get(position).getInterest().equals("literature")) {
            holder.interest_text.setText("Literature");
            holder.interest_icon.setImageResource(R.drawable.literature_interest);
            holder.interest_card.getBackground().setTint(Color.parseColor("#90A4AE"));
        }
        else if(this.list.get(position).getInterest().equals("games")) {
            holder.interest_text.setText("Games");
            holder.interest_icon.setImageResource(R.drawable.game_interest);
            holder.interest_card.getBackground().setTint(Color.parseColor("#BCABE1"));
        }
        else if(this.list.get(position).getInterest().equals("movies")) {
            holder.interest_text.setText("Movies");
            holder.interest_icon.setImageResource(R.drawable.movies_interest);
            holder.interest_card.getBackground().setTint(Color.parseColor("#B9F6CA"));
        }
        else if(this.list.get(position).getInterest().equals("debate")) {
            holder.interest_text.setText("Debate");
            holder.interest_icon.setImageResource(R.drawable.debate_interest);
            holder.interest_card.getBackground().setTint(Color.parseColor("#92C6C6"));
        }
        else if(this.list.get(position).getInterest().equals("technology")) {
            holder.interest_text.setText("Technology");
            holder.interest_icon.setImageResource(R.drawable.tech_interest);
            holder.interest_card.getBackground().setTint(Color.parseColor("#C5CAE9"));
        }
        else if(this.list.get(position).getInterest().equals("comedy")) {
            holder.interest_text.setText("Comedy");
            holder.interest_icon.setImageResource(R.drawable.comedy_interest);
            holder.interest_card.getBackground().setTint(Color.parseColor("#F0A9C2"));
        }
        else if(this.list.get(position).getInterest().equals("fashion")) {
            holder.interest_text.setText("Fashion");
            holder.interest_icon.setImageResource(R.drawable.fashion_interest);
            holder.interest_card.getBackground().setTint(Color.parseColor("#DDE398"));
        }
    }

    public int getItemCount() {
        return this.list.size();
    }
}

