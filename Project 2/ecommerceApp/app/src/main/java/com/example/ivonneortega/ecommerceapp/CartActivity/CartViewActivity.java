package com.example.ivonneortega.ecommerceapp.CartActivity;

import android.animation.LayoutTransition;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ivonneortega.ecommerceapp.MainActivity.MainActivity;
import com.example.ivonneortega.ecommerceapp.R;
import com.example.ivonneortega.ecommerceapp.Shoe;
import com.example.ivonneortega.ecommerceapp.Shoes_DBHelper;
import com.example.ivonneortega.ecommerceapp.Swipe.SimpleItemTouchHelperCallback;
import com.example.ivonneortega.ecommerceapp.Wishlist.WishlistActivity;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class CartViewActivity extends AppCompatActivity implements CartChanged {

    //Contants

    List<Shoe> mList;
    CartViewRecylerViewAdapter mAdapter;
    RecyclerView mRecyclerView;
    TextView mTotal;
    ImageView mIcon_delete;
    Shoe mLastShoe;
    ViewGroup mViewGroup;
    List<Integer> mList_Selected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_view);


        //Animations
        LayoutTransition l = new LayoutTransition();
        l.enableTransitionType(LayoutTransition.CHANGING);

        //Linear layout for the transition
        mViewGroup = (ViewGroup) findViewById(R.id.linear_layout_cart_view);
        mViewGroup.setLayoutTransition(l);


        //Tool bar creation
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
       // toolbar.setTitle("Checkout");

        //List of selected items. Used to determine the items that have been selected to be deleted
        mList_Selected = new ArrayList<>();


        // Lists of items to populate the recycler view
        mList = Shoes_DBHelper.getInstance(this).getShoesInCheckout();

        //Recycler view setup
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_cart_activity);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setNestedScrollingEnabled(false);

        //Custom icon to show when there are items selected
        //The icon stays GONE while there are no items selected
        mIcon_delete = (ImageView) findViewById(R.id.remove);
        mIcon_delete.setVisibility(View.GONE);

        //Total cart
        mTotal  = (TextView) findViewById(R.id.priceText_checkout);
        setTotalCart();

        //Textview inside custom toolbar
        TextView textView = (TextView) findViewById(R.id.title_custom_toolbar);
        textView.setText("Cart");


        //On click listener for checkout button
        findViewById(R.id.checkout_button_cart_activity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater li = LayoutInflater.from(v.getContext());
                View promptsView = li.inflate(R.layout.checkout_alert_dialog, null);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(v.getContext());
                alertDialogBuilder.setView(promptsView);
                TextView textView = (TextView) promptsView.findViewById(R.id.total_checkout);
                textView.setText("Total: $"+String.valueOf(cartTotal()));
                alertDialogBuilder
                        .setCancelable(true)
                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        checkout();
                                    }
                                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();


            }
        });

        //On click listener for title in custom toolbar
        findViewById(R.id.title_custom_toolbar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(CartViewActivity.this,MainActivity.class);
                intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent1);
                finish();

            }
        });

        //Setting color of Custom Toolbar
        findViewById(R.id.root_toolbar).setBackgroundColor(getResources().getColor(R.color.colorPrimaryCheckoutPage));

        // Removing Cart Icon and Cart TextView From ToolBar
        findViewById(R.id.cart_quantity_main_activity).setVisibility(View.GONE);
        findViewById(R.id.cart_main_activity).setVisibility(View.GONE);


        // ON CLICK LISTENER ON OVERFLOW ICON FROM CUSTOM TOOLBAR
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
                                Intent intent = new Intent(CartViewActivity.this,WishlistActivity.class);
                                startActivity(intent);
                                break;
                        }
                        return false;
                    }
                });

            }
        });


    }

    //METHOD TO GET THE TOTAL SUM OF CART
    public float cartTotal()
    {
        float sum=0;
        for(int i=0;i<mList.size();i++)
        {
            if(mList.get(i).isCheckout()== true)
            {
                sum+=mList.get(i).getPrice();
            }
        }

        DecimalFormat format = new DecimalFormat("#.##");
        sum = Float.valueOf(format.format(sum));
        return sum;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_cart, menu);
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

        if(id == R.id.action_wishList)
        {
            Intent intent = new Intent(CartViewActivity.this, WishlistActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onResume() {
        super.onResume();
        mAdapter = new CartViewRecylerViewAdapter(mList,this);
        mRecyclerView.setAdapter(mAdapter);

        ItemTouchHelper.Callback callback =
                new SimpleItemTouchHelperCallback(mAdapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(mRecyclerView);
        setTotalCart();

    }


    //Method from CartChanged interface
    //This methods handles when an item have been deleted by swiping
    @Override
    public void removeFromDatabase(final int position) {
        Shoes_DBHelper.getInstance(this).removeFromCheckout(mList.get(position).getId());
        mLastShoe = mList.get(position);
        mList.remove(position);
        mAdapter.notifyItemRemoved(position);
        setTotalCart();


        Snackbar.make(findViewById(android.R.id.content), "Shoe removed from cart", Snackbar.LENGTH_LONG)
                .setAction("UNDO", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mList.add(position,mLastShoe);
                        mAdapter.notifyItemInserted(position);
                        Shoes_DBHelper.getInstance(v.getContext()).addToCart(mList.get(position).getId());
                        setTotalCart();
                    }
                })
                .setActionTextColor(getResources().getColor(R.color.colorPrimaryDarkCheckoutPage))
                .show();
    }

    //Checouk method
    public void checkout()
    {
        mList.clear();
        mAdapter.notifyDataSetChanged();
        setTotalCart();
        Shoes_DBHelper.getInstance(this).removeAllFromCart();
    }

    //Method that sets the total in the textView total
    @Override
    public void setTotalCart() {
        mTotal.setText("$"+String.valueOf(cartTotal()));
    }


    //Method from interface that handles when an item have been selected
    @Override
    public void handlePressToRemove(int id,boolean selected) {
        //If and item was clicked and was already selected means is not selected
        if(!selected)
        {
            for(int i=0;i<mList_Selected.size();i++)
            {
                if(mList_Selected.get(i)==id)
                    mList_Selected.remove(i);
            }
            System.out.println(mList_Selected.size());
            if(mList_Selected.size()<=0)
            {

                mIcon_delete.setVisibility(View.GONE);


            }
        }
        else
        {
            mIcon_delete.setVisibility(View.VISIBLE);

            mList_Selected.add(id);
            mIcon_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    removeAllSelectedFromCart();
                    mIcon_delete.setVisibility(View.GONE);
                }
            });
        }



    }


    //Method to move item to wishlist
    @Override
    public void moveToWishlist(final int position) {
        Shoes_DBHelper.getInstance(this).removeFromCheckout(mList.get(position).getId());
        Shoes_DBHelper.getInstance(this).addToWishlist(mList.get(position).getId());
        mLastShoe = mList.get(position);
        mList.remove(position);
        mAdapter.notifyItemRemoved(position);
        setTotalCart();


        Snackbar.make(findViewById(android.R.id.content), "Shoe added to wishlit", Snackbar.LENGTH_LONG)
                .setAction("UNDO", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mList.add(position,mLastShoe);
                        mAdapter.notifyItemInserted(position);
                        Shoes_DBHelper.getInstance(v.getContext()).addToCart(mList.get(position).getId());
                        Shoes_DBHelper.getInstance(v.getContext()).removeFromWishlist(mList.get(position).getId());
                        setTotalCart();
                    }
                })
                .setActionTextColor(getResources().getColor(R.color.colorPrimaryDarkCheckoutPage))
                .show();
    }


    //Remove all items that are selected from the cart
    public void removeAllSelectedFromCart()
    {
        for(int i=0;i<mList_Selected.size();i++)
        {
            System.out.println(mList_Selected.get(i));
            Shoes_DBHelper.getInstance(this).removeFromCheckout(mList_Selected.get(i));
            Shoe shoe = Shoes_DBHelper.getInstance(this).getShoeById(mList_Selected.get(i));
            int index = mList.indexOf(shoe);
            mList.remove(shoe);
            mAdapter.notifyItemRemoved(index);
        }
        setTotalCart();
    }

}
