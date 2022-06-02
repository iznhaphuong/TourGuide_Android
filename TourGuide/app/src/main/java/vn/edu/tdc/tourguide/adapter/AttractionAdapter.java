package vn.edu.tdc.tourguide.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import vn.edu.tdc.tourguide.R;
import vn.edu.tdc.tourguide.modle.Attraction;

public class AttractionAdapter extends RecyclerView.Adapter<AttractionAdapter.AttractionViewHolder>{
    private final List<Attraction> myAttractionList;
    private OnItemClickListener onItemClickListener;
    private String[] types = {"Địa điểm du lịch", "Chợ"};

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public AttractionAdapter(List<Attraction> myAttractionList) {
        this.myAttractionList = myAttractionList;
    }

    @NonNull
    @Override
    public AttractionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_attraction, parent, false);
        return new AttractionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AttractionViewHolder holder, int position) {
        Attraction attraction = myAttractionList.get(position);
        String TAG = "TAG";
        if (attraction == null) {
            Log.d(TAG, "onBindViewHolder: loi");
            return;
        }
        Log.d(TAG, "onBindViewHolder: " + attraction.getTitle());
        holder.attractionTitle.setText(attraction.getTitle());
        holder.attractionLogo.setImageResource(R.drawable.user_logo);
        holder.ratingValue.setRating(attraction.getRatingValue());
        holder.type.setText(types[attraction.getTypes()]);
        holder.address.setText(attraction.getAddress());
        
        holder.onClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(position, holder.itemView);
                }
            }
        };

    }

    @Override
    public int getItemCount() {
        if (myAttractionList != null) {
            return myAttractionList.size();
        }
        return 0;
    }

    static class AttractionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView attractionTitle;
        private final ImageView attractionLogo;
        private final RatingBar ratingValue;
        private final TextView type;
        private final TextView address;
        private View.OnClickListener onClick;

        public AttractionViewHolder(@NonNull View itemView) {
            super(itemView);
            attractionTitle = itemView.findViewById(R.id.lblTitleAttraction);
            attractionLogo = itemView.findViewById(R.id.imgLogo);
            ratingValue = itemView.findViewById(R.id.ratingValueAttraction);
            type = itemView.findViewById(R.id.lblTypeAttraction);
            address = itemView.findViewById(R.id.lblAddressAttraction);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (this.onClick != null) {
                this.onClick.onClick(v);
            }
        }
    }
    public interface OnItemClickListener {
        public void onItemClick(int position, View v);
    }
}
