package com.example.milan.RestrictedSection;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.milan.InterestSection.RoomAdapter;
import com.example.milan.R;
import com.example.milan.RoomDetails;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class RestrictedRoomAdapter extends  RecyclerView.Adapter<RestrictedRoomAdapter.ViewHolder> {

    ItemClick activity;
    List<RoomDetails> list;

    public interface ItemClick {
        void onItemClickRestricted(String roomName,String section);
    }

    public RestrictedRoomAdapter(Context context, List<RoomDetails> list) {
        this.list = list;
        this.activity = (ItemClick) context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView roomName,sub_categoryName;
        ImageView icon;
        String section;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            roomName=itemView.findViewById(R.id.restricted_text);
            sub_categoryName=itemView.findViewById(R.id.res_sub_category);
            icon=itemView.findViewById(R.id.icon_restricted);
            itemView.setOnClickListener(v -> {
                String roomCode;
                if(list.get(RestrictedRoomAdapter.this.list.indexOf((RoomDetails) v.getTag())).getSection().equals("anonymous"))
                {
                    section="anonymous";
                }
                else {
                    section = "restricted";
                }
                roomCode = roomName.getText().toString().trim();
                RestrictedRoomAdapter.this.activity.onItemClickRestricted(roomCode,section);
            });
        }
    }
    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.restricted_details, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RestrictedRoomAdapter.ViewHolder holder, int position) {
        holder.itemView.setTag(list.get(position));
        holder.roomName.setText(list.get(position).getRoomName());

        if(list.get(position).getSection().equals("anonymous"))
            holder.icon.setImageResource(R.drawable.anonymous_logo);

        else if(list.get(position).getSection().equals("restricted"))
            holder.icon.setImageResource(R.drawable.restricted);

        if(list.get(position).getCategory().equals("N/A"))
            holder.sub_categoryName.setVisibility(View.INVISIBLE);

        else
           holder.sub_categoryName.setText(list.get(position).getCategory());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}
