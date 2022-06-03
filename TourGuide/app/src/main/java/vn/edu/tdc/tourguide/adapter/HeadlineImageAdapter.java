package vn.edu.tdc.tourguide.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import vn.edu.tdc.tourguide.R;
import vn.edu.tdc.tourguide.model.HeadlineImage;

public class HeadlineImageAdapter extends RecyclerView.Adapter<HeadlineImageAdapter.HeadlineImageViewHolder>{
    private List<HeadlineImage> myHeadlineImage;
    public HeadlineImageAdapter(List<HeadlineImage> myHeadlineImage) {
        this.myHeadlineImage = myHeadlineImage;
    }



    @NonNull
    @Override
    public HeadlineImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.headline_image_item, parent , false);
        return new HeadlineImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HeadlineImageViewHolder holder, int position) {
        HeadlineImage headlineImage = myHeadlineImage.get(position);
        if(headlineImage == null){
            return;
        }
        holder.headlineImage1.setImageResource(headlineImage.getHeadlineImage1());
        holder.headlineImage2.setImageResource(headlineImage.getHeadlineImage2());
        holder.headlineImage3.setImageResource(headlineImage.getHeadlineImage3());
    }

    @Override
    public int getItemCount() {
        if(myHeadlineImage != null){
            return myHeadlineImage.size();
        }
        return 0;
    }

    class HeadlineImageViewHolder extends RecyclerView.ViewHolder{
        private ImageView headlineImage1;
        private ImageView headlineImage2;
        private ImageView headlineImage3;
        public HeadlineImageViewHolder(@NonNull View itemView) {
            super(itemView);
            headlineImage1 = itemView.findViewById(R.id.headImage1);
            headlineImage2 = itemView.findViewById(R.id.headImage2);
            headlineImage3 = itemView.findViewById(R.id.headImage3);

        }
    }
}
