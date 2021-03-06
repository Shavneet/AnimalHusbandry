package com.animalhusbandry.utils;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class EndlessRecyclerViewScrollListenerImplementation extends EndlessRecyclerViewScrollListener {

    private OnScrollPageChangeListener listener;

    public EndlessRecyclerViewScrollListenerImplementation(LinearLayoutManager layoutManager, OnScrollPageChangeListener listener) {
        super(layoutManager);
        this.listener = listener;
    }

    @Override
    public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
        listener.onLoadMore(page, totalItemsCount, view);
    }

    public interface OnScrollPageChangeListener {
        void onLoadMore(int page, int totalItemsCount, RecyclerView view);
    }
}