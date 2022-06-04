package vn.edu.tdc.tourguide;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import vn.edu.tdc.tourguide.adapter.CommentsAdapter;
import vn.edu.tdc.tourguide.models.Comments;

public class ReviewScreenActivity extends AppCompatActivity {
    private RecyclerView rcvComment;
    private List<Comments> myCommentList;
    private CommentsAdapter commentsAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.review_screen);

        rcvComment = findViewById(R.id.rcv_cmt);
        myCommentList = new ArrayList<>();
        //du lieu gia
        Comments comment1 = new Comments("Christiana", R.drawable.avarta2, "17 April 2015", "Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an ", 3);
        Comments comment2 = new Comments("Chrana", R.drawable.avarta2, "17 April 2015", "Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an ", 3);
        Comments comment3 = new Comments("Boulkd Che", R.drawable.avarta2, "17 April 2015", "Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an ", 3);
        myCommentList.add(comment1);
        myCommentList.add(comment2);
        myCommentList.add(comment3);

        commentsAdapter = new CommentsAdapter(myCommentList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,  LinearLayoutManager.VERTICAL, false);
        rcvComment.setLayoutManager(linearLayoutManager);
        RecyclerView.ItemDecoration decoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        rcvComment.addItemDecoration(decoration);
        rcvComment.setAdapter(commentsAdapter);

    }
}
