package vn.edu.tdc.tourguide.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import vn.edu.tdc.tourguide.R;
import vn.edu.tdc.tourguide.models.City;
import vn.edu.tdc.tourguide.models.Destination;

public class AttractionAdapter extends RecyclerView.Adapter<AttractionAdapter.AttractionViewHolder> implements Filterable {
    private List<Destination> mAttractionList;
    private final List<Destination> mAttractionListOld;
    private OnItemClickListener onItemClickListener;
    private String[] types = {"Địa điểm du lịch", "Chợ"};

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public AttractionAdapter(List<Destination> mAttractionList) {
        this.mAttractionList = mAttractionList;
        this.mAttractionListOld = mAttractionList;
    }

    @NonNull
    @Override
    public AttractionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_attraction, parent, false);
        return new AttractionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AttractionViewHolder holder, int position) {
        Destination attraction = mAttractionList.get(position);
        String TAG = "TAG";
        if (attraction == null) {
            Log.d(TAG, "onBindViewHolder: loi");
            return;
        }
        City.getImage(attraction.getImage(), holder.attractionLogo);
        holder.attractionTitle.setText(attraction.getName());
        holder.ratingValue.setRating(attraction.getRating());
        holder.type.setText(attraction.getType());
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
        if (mAttractionList != null) {
            return mAttractionList.size();
        }
        return 0;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String keyWord = constraint.toString();
                if (keyWord.isEmpty()) {
                    mAttractionList = mAttractionListOld;
                } else {
                    List<Destination> attractions = new ArrayList<>();
                    for (Destination attraction : mAttractionListOld) {
                        if (attraction.getName().toLowerCase().contains(keyWord.toLowerCase())) {
                            attractions.add(attraction);
                        }
                    }
                    mAttractionList = attractions;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mAttractionList;

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                mAttractionList = (List<Destination>) results.values;
                notifyDataSetChanged();
            }
        };
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
