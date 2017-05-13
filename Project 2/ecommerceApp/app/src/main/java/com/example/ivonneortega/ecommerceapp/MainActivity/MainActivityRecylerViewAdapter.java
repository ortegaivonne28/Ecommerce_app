package com.example.ivonneortega.ecommerceapp.MainActivity;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.example.ivonneortega.ecommerceapp.Interface.MainActivityInterface;
import com.example.ivonneortega.ecommerceapp.R;
import com.example.ivonneortega.ecommerceapp.Shoe;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by ivonneortega on 4/6/17.
 */

public class MainActivityRecylerViewAdapter extends RecyclerView.Adapter<MainActivityViewHolder> {

    private List<Shoe> mShoes;
    private MainActivityInterface mInterface;
    private boolean mIsTablet;
    private int mViewMode;

    public MainActivityRecylerViewAdapter(List<Shoe> shoes,Context context,boolean isTablet, int viewMode)
    {
        mShoes = shoes;
        mIsTablet = isTablet;
        mInterface = (MainActivityInterface) context;
        mViewMode = viewMode;
    }

    @Override
    public MainActivityViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if(mIsTablet)
        {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recycler_view_main_activity_tablet, parent, false);
            return new MainActivityViewHolder(view);
        }
        else {

            if(mViewMode == MainActivity.VIEW_MOVE_GRID)
            {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.recycler_view_main_activity, parent, false);
                return new MainActivityViewHolder(view);
            }
            else
            {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.recycler_view_main_activity_tablet, parent, false);
                return new MainActivityViewHolder(view);
            }
        }
    }

    @Override
    public void onBindViewHolder(final MainActivityViewHolder holder, int position) {

        final Shoe shoe = mShoes.get(position);
        holder.mTitle.setText(mShoes.get(position).getModel());
        holder.mPrice.setText("$"+String.valueOf((mShoes.get(position).getPrice())));
        holder.mBrand.setText(mShoes.get(position).getBrand());

        // -- Setting Image From Picasso -- //
        Picasso.with(holder.mImageView.getContext())
                .load(mShoes.get(holder.getAdapterPosition()).getPicture_Name())
                .resize(170, 150)
                .into(holder.mImageView);

        holder.mRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mInterface.moveToDetailActivity(mShoes.get(holder.getAdapterPosition()).getId());
            }
        });

        holder.mRoot.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mInterface.onLongClick(shoe.getId());
                return true;
            }
        });

        holder.mRoot.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {

                } else if (event.getAction() == MotionEvent.ACTION_UP){//|| event.getAction() == android.view.MotionEvent.ACTION_CANCEL) {
                    mInterface.onReleaseLongClick(shoe.getId());
                    //return true;
                }
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mShoes.size();
    }

    public void changeShoeList(List<Shoe> list)
    {
        mShoes = list;
    }
}
