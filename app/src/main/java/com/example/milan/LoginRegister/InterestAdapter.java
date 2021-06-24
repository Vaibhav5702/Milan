package com.example.milan.LoginRegister;

import android.annotation.SuppressLint;
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

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class InterestAdapter extends RecyclerView.Adapter<InterestAdapter.ViewHolder> {
    ItemClick activity;
    List<Interest> list;

    public interface ItemClick {
        void onItemClick(int i, boolean itemTap);
    }

    public InterestAdapter(Context context, List<Interest> list) {
        this.list = list;
        this.activity = (ItemClick) context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView interest_icon;
        TextView interest_text;
        CardView interest_card;
        boolean onTap = false;

        public ViewHolder(View itemView) {
            super(itemView);
            this.interest_icon = itemView.findViewById(R.id.interest_icon);
            this.interest_card = itemView.findViewById(R.id.interest_card);
            this.interest_text = itemView.findViewById(R.id.interest_text);

            itemView.setOnClickListener(v -> {
                if(!onTap){
                    interest_card.getBackground().setTint(Color.parseColor("#ffffff"));
                    onTap=true;
                }
                else{
                    onTap=false;
                    interest_card.getBackground().setTint(list.get(InterestAdapter.this.list.indexOf((Interest) v.getTag())).getColor());
                }
                InterestAdapter.this.activity.onItemClick(InterestAdapter.this.list.indexOf((Interest) v.getTag()),
                        onTap);
            });
        }
    }

    public @NotNull ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.interest_layout, parent, false));
    }

    @SuppressLint("SetTextI18n")
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.itemView.setTag(this.list.get(position));
        switch (this.list.get(position).getInterest()) {
            case "Music":
                holder.interest_text.setText("Music");
                holder.interest_icon.setImageResource(R.drawable.music_interest);
                holder.interest_card.getBackground().setTint(list.get(position).getColor());
                break;
            case "Poetry":
                holder.interest_text.setText("Poetry");
                holder.interest_icon.setImageResource(R.drawable.poetry_interest);
                holder.interest_card.getBackground().setTint(list.get(position).getColor());
                break;
            case "Sports":
                holder.interest_text.setText("Sports");
                holder.interest_icon.setImageResource(R.drawable.sports_interest);
                holder.interest_card.getBackground().setTint(list.get(position).getColor());
                break;
            case "Literature":
                holder.interest_text.setText("Literature");
                holder.interest_icon.setImageResource(R.drawable.literature_interest);
                holder.interest_card.getBackground().setTint(list.get(position).getColor());
                break;
            case "Games":
                holder.interest_text.setText("Games");
                holder.interest_icon.setImageResource(R.drawable.game_interest);
                holder.interest_card.getBackground().setTint(list.get(position).getColor());
                break;
            case "Movies":
                holder.interest_text.setText("Movies");
                holder.interest_icon.setImageResource(R.drawable.movies_interest);
                holder.interest_card.getBackground().setTint(list.get(position).getColor());
                break;
            case "Debate":
                holder.interest_text.setText("Debate");
                holder.interest_icon.setImageResource(R.drawable.debate_interest);
                holder.interest_card.getBackground().setTint(list.get(position).getColor());
                break;
            case "Technology":
                holder.interest_text.setText("Technology");
                holder.interest_icon.setImageResource(R.drawable.tech_interest);
                holder.interest_card.getBackground().setTint(list.get(position).getColor());
                break;
            case "Comedy":
                holder.interest_text.setText("Comedy");
                holder.interest_icon.setImageResource(R.drawable.comedy_interest);
                holder.interest_card.getBackground().setTint(list.get(position).getColor());
                break;
            case "Fashion":
                holder.interest_text.setText("Fashion");
                holder.interest_icon.setImageResource(R.drawable.fashion_interest);
                holder.interest_card.getBackground().setTint(list.get(position).getColor());
                break;
        }
    }

    public int getItemCount() {
        return this.list.size();
    }
}

