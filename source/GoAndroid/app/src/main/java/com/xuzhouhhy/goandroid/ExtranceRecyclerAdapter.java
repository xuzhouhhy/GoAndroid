package com.xuzhouhhy.goandroid;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * 主界面入口recyclerview的适配器
 * Created by xuzhouhhy on 2017/8/13.
 */

class ExtranceRecyclerAdapter extends RecyclerView.Adapter<ExtranceRecyclerAdapter.ExtranceViewHolder> {


    private RecyclerItemClickListener mListener;

    private List<String> mExtrances = new ArrayList<>();

    ExtranceRecyclerAdapter(@NonNull List<String> strings) {
        mExtrances = strings;
    }

    @Override
    public ExtranceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ExtranceViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_extrance_item_view, parent, false));
    }

    @Override
    public int getItemCount() {
        return mExtrances.size();
    }

    @Override
    public void onBindViewHolder(final ExtranceViewHolder holder, final int position) {
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.itemClick(position);
            }
        });
        holder.mTextView.setText(mExtrances.get(position));
    }

    void setListener(RecyclerItemClickListener listener) {
        mListener = listener;
    }

    class ExtranceViewHolder extends RecyclerView.ViewHolder {

        View mView;

        TextView mTextView;

        ExtranceViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            mTextView = (TextView) itemView.findViewById(R.id.tvExtranceName);
        }
    }
}
