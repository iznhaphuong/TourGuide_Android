package vn.edu.tdc.tourguide.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import vn.edu.tdc.tourguide.R;
import vn.edu.tdc.tourguide.models.City;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeViewHolder> implements Filterable {
    private List<City> mHomeList;
    private final List<City> mHomeListOld;
    private OnItemClickListener onItemClickListener;
    private String TAG = "TAG";

    public List<City> getmHomeList() {
        return mHomeList;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public HomeAdapter(List<City> mHomeList) {
        this.mHomeList = mHomeList;
        this.mHomeListOld = mHomeList;
    }

    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home, parent, false);
        return new HomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeViewHolder holder, int position) {
        City home = mHomeList.get(position);
        String TAG = "TAG";
        if (home == null) {
            Log.d(TAG, "onBindViewHolder: loi");
            return;
        }
        Log.d(TAG, "onBindViewHolder: " + home.getName());
        holder.homeTitle.setText(home.getName());
        City.getImage(home.getImage(), holder.homeLogo);

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
        if (mHomeList != null) {
            return mHomeList.size();
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
                    mHomeList = mHomeListOld;
                } else {
                    List<City> homes = new ArrayList<>();
                    for (City home : mHomeListOld) {
                        if (home.getName().toLowerCase().contains(keyWord.toLowerCase())) {
                            homes.add(home);
                        }
                    }
                    mHomeList = homes;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mHomeList;

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                mHomeList = (List<City>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    static class HomeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView homeTitle;
        private final ImageView homeLogo;
        private View.OnClickListener onClick;

        public HomeViewHolder(@NonNull View itemView) {
            super(itemView);
            homeTitle = itemView.findViewById(R.id.home_title);
            homeLogo = itemView.findViewById(R.id.home_logo);
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
