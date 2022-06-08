package vn.edu.tdc.tourguide.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import vn.edu.tdc.tourguide.R;
import vn.edu.tdc.tourguide.ReviewScreenActivity;
import vn.edu.tdc.tourguide.SideMenuActivity;
import vn.edu.tdc.tourguide.models.Comments;

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.CommentsHolder>{
    private List<Comments> myCommentList;
    public CommentsAdapter(List<Comments> myCommentList) {
        this.myCommentList = myCommentList;
    }

    @NonNull
    @Override
    public CommentsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment, parent, false);
        return new CommentsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentsHolder holder, int position) {
        Comments comments = myCommentList.get(position);
        if(comments == null){
            return;
        }
        holder.user_name.setText(comments.getUser_name());
        Glide.with(ReviewScreenActivity.rcvComment).load(comments.getUser_image()).error(R.drawable.avarta2).into(holder.user_image);
        holder.date.setText(comments.getDate());
        holder.reviews.setText(comments.getReviews());
        holder.user_rating.setRating(comments.getUser_rating());
    }

    @Override
    public int getItemCount() {
        if(myCommentList != null){
            return myCommentList.size();
        }
        return 0;
    }

    class CommentsHolder extends RecyclerView.ViewHolder{
        private TextView user_name;
        private RatingBar user_rating;
        private ImageView user_image;
        private TextView date;
        private TextView reviews;
        private TextView amountCmt;
        public CommentsHolder(@NonNull View itemView) {
            super(itemView);
            user_name = itemView.findViewById(R.id.userName);
            user_image = itemView.findViewById(R.id.userImage);
            date = itemView.findViewById(R.id.review_date);
            reviews = itemView.findViewById(R.id.review_cmt);
            user_rating = itemView.findViewById(R.id.ratingStar);
        }
    }
}
