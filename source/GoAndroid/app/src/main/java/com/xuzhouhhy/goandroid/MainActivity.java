package com.xuzhouhhy.goandroid;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;

    private ExtranceRecyclerAdapter mAdapter;

    private RecyclerItemClickListener mListener = new RecyclerItemClickListener() {
        @Override
        public void itemClick(int position) {
            Extarnce.values()[position].enter(MainActivity.this);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initview();
    }

    private void initview() {
        mAdapter = new ExtranceRecyclerAdapter(Extarnce.getStrings());
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerExtrance);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setListener(mListener);
    }

}
