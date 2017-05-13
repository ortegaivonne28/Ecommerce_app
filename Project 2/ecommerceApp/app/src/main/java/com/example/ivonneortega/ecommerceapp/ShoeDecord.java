package com.example.ivonneortega.ecommerceapp;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by ivonneortega on 4/8/17.
 */

public class ShoeDecord extends RecyclerView.ItemDecoration {

        private int mSpace;

        public ShoeDecord(int space) {
            this.mSpace = space;
        }


        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);

            outRect.left = mSpace;
            outRect.right = mSpace;
            //outRect.bottom = mSpace;

            if (parent.getChildAdapterPosition(view) == 0)
                outRect.top = mSpace;
        }
}
