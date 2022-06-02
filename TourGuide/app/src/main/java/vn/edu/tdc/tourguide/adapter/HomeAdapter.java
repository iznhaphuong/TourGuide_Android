package vn.edu.tdc.tourguide.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import vn.edu.tdc.tourguide.R;
import vn.edu.tdc.tourguide.modle.Home;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeViewHolder>{
    private final List<Home> myHomeList;
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public HomeAdapter(List<Home> myHomeList) {
        this.myHomeList = myHomeList;
    }

    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home, parent, false);
        return new HomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeViewHolder holder, int position) {
        Home home = myHomeList.get(position);
        String TAG = "TAG";
        if (home == null) {
            Log.d(TAG, "onBindViewHolder: loi");
            return;
        }
        Log.d(TAG, "onBindViewHolder: " + home.getName());
        holder.homeTitle.setText(home.getName());
        holder.homeLogo.setImageResource(R.drawable.user_logo);
        
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
        if (myHomeList != null) {
            return myHomeList.size();
        }
        return 0;
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
