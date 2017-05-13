package com.example.ivonneortega.ecommerceapp.Swipe;

/**
 * Created by ivonneortega on 4/10/17.
 */

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.ivonneortega.ecommerceapp.CartActivity.CartViewHolder;
import com.example.ivonneortega.ecommerceapp.CartActivity.CartViewRecylerViewAdapter;
import com.example.ivonneortega.ecommerceapp.R;
import com.example.ivonneortega.ecommerceapp.Wishlist.WishlistRecyclerViewAdapter;
import com.example.ivonneortega.ecommerceapp.Wishlist.WishlistViewHolder;



public class SimpleItemTouchHelperCallback extends ItemTouchHelper.Callback {

    public static final float ALPHA_FULL = 1.0f;

    private final CartViewRecylerViewAdapter mAdapterCart;
    private final WishlistRecyclerViewAdapter mAdapterWishlist;

    public SimpleItemTouchHelperCallback(CartViewRecylerViewAdapter adapter) {
        mAdapterCart = adapter;
        mAdapterWishlist=null;
    }
    public SimpleItemTouchHelperCallback(WishlistRecyclerViewAdapter adapter) {
        mAdapterWishlist = adapter;
        mAdapterCart = null;
    }

    @Override
    public boolean isLongPressDragEnabled()
    {
        if(mAdapterCart==null)
        {
            return true;
        }
        return false;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return true;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {



        // Set movement flags based on the layout manager

        if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
            final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
            final int swipeFlags = 0;
            return makeMovementFlags(dragFlags, swipeFlags);
        } else {
            final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
            final int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
            return makeMovementFlags(dragFlags, swipeFlags);
        }
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder source, RecyclerView.ViewHolder target) {


//        if(source instanceof CartViewHolder)
//        {
//            mAdapterCart.onItemMove(source.getAdapterPosition(),target.getAdapterPosition());
//            return true;
//        }
//        else
//        {
        if(source instanceof WishlistViewHolder) {
            mAdapterWishlist.onItemMove(source.getAdapterPosition(), target.getAdapterPosition());
            return true;
        }
        return false;
        //}


    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int i) {
        // Notify the adapter of the dismissal
        if(viewHolder instanceof CartViewHolder)
        {
            if (i  == ItemTouchHelper.END){
                System.out.println("sdfsdfsdfsdfsdfds");
                mAdapterCart.onItemAddToWishlist(viewHolder.getAdapterPosition());
            } else {
                mAdapterCart.onItemDismiss(viewHolder.getAdapterPosition());
            }

        }
        else
        {
            if (i  == ItemTouchHelper.END){
                mAdapterWishlist.onItemAddToCart(viewHolder.getAdapterPosition());
            } else {
                mAdapterWishlist.onItemDismiss(viewHolder.getAdapterPosition());
            }
        }
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        if(actionState == ItemTouchHelper.ACTION_STATE_SWIPE && viewHolder instanceof CartViewHolder)
        {
            View itemView = viewHolder.itemView;
            Paint p = new Paint();
            Bitmap icon;

            float height = (float) itemView.getBottom() - (float) itemView.getTop();
            float width = height / 3;

            if (dX > 0) {
            /* Set your color for positive displacement */

                // Draw Rect with varying right side, equal to displacement dX
//                c.drawRect((float) itemView.getLeft(), (float) itemView.getTop(), dX,
//                        (float) itemView.getBottom(), p);

                p.setColor(Color.parseColor("#388E3C"));
                RecyclerView.LayoutParams lp = (RecyclerView.LayoutParams) itemView.getLayoutParams();
                RectF background = new RectF((float) itemView.getLeft(), (float) itemView.getTop(), dX + lp.leftMargin,(float) itemView.getBottom());
                c.drawRect(background,p);
                icon = BitmapFactory.decodeResource(itemView.getContext().getResources(), R.drawable.ic_action_name);
                RectF icon_dest = new RectF((float) itemView.getLeft() + width / 2 ,(float) itemView.getTop() + width,(float) itemView.getLeft()+ 1.5f*width,(float)itemView.getBottom() - width);
                c.drawBitmap(icon,null,icon_dest,p);
            } else {
            /* Set your color for negative displacement */

                p.setColor(Color.parseColor("#D32F2F"));
                RectF background = new RectF((float) itemView.getRight() + dX, (float) itemView.getTop(),(float) itemView.getRight(), (float) itemView.getBottom());
                c.drawRect(background,p);
                icon = BitmapFactory.decodeResource(itemView.getContext().getResources(), R.mipmap.ic_delete_white_24dp);
                RectF icon_dest = new RectF((float) itemView.getRight() - 2*width ,(float) itemView.getTop() + width,(float) itemView.getRight() - width,(float)itemView.getBottom() - width);
                c.drawBitmap(icon,null,icon_dest,p);     c.drawBitmap(icon,null,icon_dest,p);
            }

        }

        if(actionState == ItemTouchHelper.ACTION_STATE_SWIPE && viewHolder instanceof WishlistViewHolder)
        {
            View itemView = viewHolder.itemView;
            Paint p = new Paint();
            Bitmap icon;

            float height = (float) itemView.getBottom() - (float) itemView.getTop();
            float width = height / 3;

            if (dX > 0) {
            /* Set your color for positive displacement */

                // Draw Rect with varying right side, equal to displacement dX
//                c.drawRect((float) itemView.getLeft(), (float) itemView.getTop(), dX,
//                        (float) itemView.getBottom(), p);

                p.setColor(Color.parseColor("#388E3C"));
                RecyclerView.LayoutParams lp = (RecyclerView.LayoutParams) itemView.getLayoutParams();
                RectF background = new RectF((float) itemView.getLeft(), (float) itemView.getTop(), dX + lp.leftMargin,(float) itemView.getBottom());
                c.drawRect(background,p);
                icon = BitmapFactory.decodeResource(itemView.getContext().getResources(), R.mipmap.ic_shopping_cart_white_24dp);
                RectF icon_dest = new RectF((float) itemView.getLeft() + width / 2 ,(float) itemView.getTop() + width,(float) itemView.getLeft()+ 1.5f*width,(float)itemView.getBottom() - width);
                c.drawBitmap(icon,null,icon_dest,p);
            } else {
            /* Set your color for negative displacement */

                p.setColor(Color.parseColor("#D32F2F"));
                RectF background = new RectF((float) itemView.getRight() + dX, (float) itemView.getTop(),(float) itemView.getRight(), (float) itemView.getBottom());
                c.drawRect(background,p);
                icon = BitmapFactory.decodeResource(itemView.getContext().getResources(), R.mipmap.ic_delete_white_24dp);
                RectF icon_dest = new RectF((float) itemView.getRight() - 2*width ,(float) itemView.getTop() + width,(float) itemView.getRight() - width,(float)itemView.getBottom() - width);
                c.drawBitmap(icon,null,icon_dest,p);     c.drawBitmap(icon,null,icon_dest,p);
            }

        }



        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
    }
}
