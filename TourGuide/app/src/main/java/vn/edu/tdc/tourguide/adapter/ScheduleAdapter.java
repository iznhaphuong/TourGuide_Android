package vn.edu.tdc.tourguide.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import vn.edu.tdc.tourguide.R;
import vn.edu.tdc.tourguide.modle.EventSchedule;
import vn.edu.tdc.tourguide.modle.Home;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ScheduleViewHolder> {
    //Properties
    public ArrayList<EventSchedule> listEvent;
    private OnItemClickListener onItemClickListener;

    //Constructor
    public ScheduleAdapter(ArrayList<EventSchedule> listEvent) {
        this.listEvent = listEvent;
    }


    public static class ScheduleViewHolder extends  RecyclerView.ViewHolder implements View.OnClickListener  {
        View.OnClickListener onClick;

        public ScheduleViewHolder(@NonNull View itemView){
            super(itemView);
        }
        @Override
        public void onClick(View v) {
//            Log.d("test", "aaa");
            if (onClick != null){
                onClick.onClick(v); //goi onclick trong onbind?
            }
        }
    }
    @NonNull
    @Override
    public ScheduleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView view = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.schedule_customize_listview, parent, false);
        return new ScheduleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ScheduleViewHolder holder, int position) {
        //set onclkc
        holder.onClick = new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
//                Log.d("test", "Called");
                if(onItemClickListener != null){
                    onItemClickListener.onItemClick(position, holder.itemView);
                }else {
                    Log.d("adapter", "view must set on item click listener ");
                }
            }
        };
    }
    //Define interface for co che uy quyen
    public interface OnItemClickListener{
        public void onItemClick (int position, View view);

    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
