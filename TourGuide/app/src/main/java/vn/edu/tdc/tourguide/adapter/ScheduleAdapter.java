package vn.edu.tdc.tourguide.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import vn.edu.tdc.tourguide.R;
import vn.edu.tdc.tourguide.models.EventSchedule;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ScheduleViewHolder> {
    //Properties
    public ArrayList<EventSchedule> listEvent;
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    //Constructor
    public ScheduleAdapter(ArrayList<EventSchedule> listEvent) {
        this.listEvent = listEvent;
    }
    public ArrayList<EventSchedule> getListEvent(){return listEvent;}

    public static class ScheduleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        //fields need catch
        TextView nameDestination;
        TextView noteEvent;
        TextView timeEvent;
        TextView dateEvent;
        TextView monthEvent;
        View.OnClickListener onClick;


        public ScheduleViewHolder(@NonNull View itemView) {
            super(itemView);
            nameDestination = itemView.findViewById(R.id.nameDestination);
            timeEvent = itemView.findViewById(R.id.timeEvent);
            dateEvent = itemView.findViewById(R.id.dateEvent);
            noteEvent = itemView.findViewById(R.id.noteEvent);
            monthEvent = itemView.findViewById(R.id.monthEvent);
        }

        @Override
        public void onClick(View v) {
//            Log.d("test", "aaa");
            if (onClick != null) {
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
        //get data from datasource
        EventSchedule event = listEvent.get(position);
        holder.nameDestination.setText(event.getNameDestination());
        holder.dateEvent.setText(String.valueOf(event.getDateEvent()));
        holder.timeEvent.setText(event.getTimeEvent());
        holder.noteEvent.setText(event.getNoteEvent());
        holder.monthEvent.setText("Tháng "+String.valueOf(event.getMonthEvent()));

        holder.itemView.setOnClickListener(view -> {
            onItemClickListener.onItemClick(event, position);// this will get the position of out item in RecycleView
        });

        //set onclkc
//        holder.onClick = new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                Log.d("test", "Called");
//                if (onItemClickListener != null) {
//                    onItemClickListener.onItemClick(position, holder.itemView);
//                } else {
//                    Log.d("adapter", "view must set on item click listener ");
//                }
//            }
//        };
    }

    //Define interface for co che uy quyen

    public interface OnItemClickListener {
        public void onItemClick(EventSchedule eventSchedule,int position);
    }
    @Override
    public int getItemCount() {
        return listEvent.size();
    }
}
