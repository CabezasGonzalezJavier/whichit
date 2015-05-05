package com.thedeveloperworldisyours.whichit.listener;

import android.widget.AbsListView;

/**
 * Created by javiergonzalezcabezas on 5/5/15.
 */
public abstract class InfiniteScrollListener implements AbsListView.OnScrollListener {
    private int mBufferItemCount = 10;
    private int mCurrentPage = 0;
    private int mItemCount = 0;
    private boolean mIsLoading = true;

    public InfiniteScrollListener(int bufferItemCount) {
        this.mBufferItemCount = bufferItemCount;
    }



    public abstract void loadMore(int page, int totalItemsCount);

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        // Do Nothing
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (totalItemCount < mItemCount) {
            this.mItemCount = totalItemCount;
            if (totalItemCount == 0) {
                this.mIsLoading = true;
            }
        }

        if (mIsLoading && (totalItemCount > mItemCount)) {
            mIsLoading = false;
            mItemCount = totalItemCount;
            mCurrentPage++;
        }

        if (!mIsLoading && (totalItemCount - visibleItemCount) <= (firstVisibleItem + mBufferItemCount)) {
            loadMore(mCurrentPage + 1, totalItemCount);
            mIsLoading = true;
        }
    }
}
