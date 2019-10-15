package com.example.recycleviewwithscrolling;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    List<Product> productList;
    ScrollingLinearLayoutManager scrollManager;
    ProductAdapter adapter;
    Timer swipeTimer;
    SwipeTask swipeTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        // recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true));
        productList = new ArrayList<>();
        swipeTask = new SwipeTask();
        productList.add(
                new Product(
                        1,
                        "Apple MacBook Air Core i5 5th Gen )",
                        R.drawable.image1));

        productList.add(
                new Product(
                        2,
                        "Microsoft Surface Pro 4 Core m3 6th Gen)",
                        R.drawable.image2));
        productList.add(
                new Product(
                        3,
                        " Pro 4 Core m3 6th Gen)",
                        R.drawable.image1));

        //creating recyclerview adapter

        adapter = new ProductAdapter(this, productList);
        //setting adapter to recyclerview
        scrollManager = new ScrollingLinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, true, 2000);
        recyclerView.setLayoutManager(scrollManager);
        recyclerView.setAdapter(adapter);
        swipeTask.playCarousel();
    }

    public class SwipeTask extends TimerTask {


        public void run() {
            recyclerView.post(() ->
            {
                int nextPage = (scrollManager.findFirstVisibleItemPosition() + 1) % adapter.getItemCount();
                recyclerView.smoothScrollToPosition(nextPage);
            });

        }

        public void stopScrollTimer() {

            if (null != swipeTimer) {
                swipeTimer.cancel();
            }
            if (null != swipeTask) {
                swipeTask.cancel();
            }
        }

        public void resetScrollTimer() {
            stopScrollTimer();
            swipeTask = new SwipeTask();
            swipeTimer = new Timer();
        }

        public void playCarousel() {

            resetScrollTimer();
            swipeTimer.schedule(swipeTask, 0, 2000);
        }
    }
}







