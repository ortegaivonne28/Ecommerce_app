package com.example.ivonneortega.ecommerceapp.Wishlist;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.ivonneortega.ecommerceapp.CartActivity.CartViewActivity;
import com.example.ivonneortega.ecommerceapp.DetailView.DetailViewActivity;
import com.example.ivonneortega.ecommerceapp.MainActivity.MainActivity;
import com.example.ivonneortega.ecommerceapp.R;
import com.example.ivonneortega.ecommerceapp.Shoe;
import com.example.ivonneortega.ecommerceapp.Shoes_DBHelper;
import com.example.ivonneortega.ecommerceapp.Swipe.SimpleItemTouchHelperCallback;

import java.util.List;

public class WishlistActivity extends AppCompatActivity implements WishlistChanged{

    RecyclerView mRecyclerView;
    WishlistRecyclerViewAdapter mAdapter;
    List<Shoe> mShoeList;
    Shoe mLastShoe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wishlist);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_wishlist_activity);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        mShoeList = Shoes_DBHelper.getInstance(this).shoesInWishList();
        mRecyclerView.setLayoutManager(linearLayoutManager);
        findViewById(R.id.remove).setVisibility(View.GONE);


        TextView textView = (TextView) findViewById(R.id.title_custom_toolbar);
        textView.setText("Wishlist");
        findViewById(R.id.title_custom_toolbar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(WishlistActivity.this,MainActivity.class);
                intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent1);
                finish();

            }
        });

        findViewById(R.id.cart_main_activity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WishlistActivity.this,CartViewActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.overflow_main_activity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(v.getContext(),v);
                popupMenu.inflate(R.menu.menu_tool_bar_overflow);
                popupMenu.show();

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        int id = item.getItemId();
                        switch (id)
                        {
                            case R.id.settings_main_activity:
                                //TODO SETTINGS STUFF
                                break;
                            case R.id.wishlist_main_activity:
                                break;
                        }
                        return false;
                    }
                });

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if(id == R.id.cart)
        {
            Intent intent = new Intent(WishlistActivity.this,CartViewActivity.class);
            startActivity(intent);
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void removeFromDatabase(final int position) {
        Shoes_DBHelper.getInstance(this).removeFromWishlist(mShoeList.get(position).getId());
        mLastShoe = mShoeList.get(position);
        mShoeList.remove(position);
        mAdapter.notifyItemRemoved(position);

        Snackbar.make(findViewById(android.R.id.content), "Shoe removed from wishlist", Snackbar.LENGTH_LONG)
                .setAction("UNDO", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mShoeList.add(position,mLastShoe);
                        mAdapter.notifyItemInserted(position);
                        Shoes_DBHelper.getInstance(v.getContext()).addToWishlist(mShoeList.get(position).getId());

                    }
                })
                .setActionTextColor(getResources().getColor(R.color.colorPrimaryDarkCheckoutPage))
                .show();
    }

    @Override
    public void addToCart(final int position) {
        Shoes_DBHelper.getInstance(this).removeFromWishlist(mShoeList.get(position).getId());
        Shoes_DBHelper.getInstance(this).addToCart(mShoeList.get(position).getId());
        mLastShoe = mShoeList.get(position);
        mShoeList.remove(position);
        mAdapter.notifyItemRemoved(position);
        setTextCounterCartIcon();

        Snackbar.make(findViewById(android.R.id.content), "Shoe added to Cart", Snackbar.LENGTH_LONG)
                .setAction("UNDO", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mShoeList.add(position,mLastShoe);
                        mAdapter.notifyItemInserted(position);
                        Shoes_DBHelper.getInstance(v.getContext()).removeFromCheckout(mShoeList.get(position).getId());
                        Shoes_DBHelper.getInstance(v.getContext()).addToWishlist(mShoeList.get(position).getId());
                        setTextCounterCartIcon();

                    }
                })
                .setActionTextColor(getResources().getColor(R.color.colorPrimaryDarkCheckoutPage))
                .show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mAdapter = new WishlistRecyclerViewAdapter(mShoeList,this);
        mRecyclerView.setAdapter(mAdapter);

        ItemTouchHelper.Callback callback =
                new SimpleItemTouchHelperCallback(mAdapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(mRecyclerView);

        setTextCounterCartIcon();
    }

    public void setTextCounterCartIcon()
    {
        TextView cart_quantity = (TextView) findViewById(R.id.cart_quantity_main_activity);
        cart_quantity.setText(String.valueOf(Shoes_DBHelper.getInstance(this).getNumberOfItemsInCart()));
    }
}
