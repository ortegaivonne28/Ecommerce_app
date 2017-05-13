package com.example.ivonneortega.ecommerceapp.MainActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ivonneortega.ecommerceapp.CartActivity.CartFragment;
import com.example.ivonneortega.ecommerceapp.CartActivity.CartViewActivity;
import com.example.ivonneortega.ecommerceapp.CartActivity.CartViewRecylerViewAdapter;
import com.example.ivonneortega.ecommerceapp.DBSetup.DBAssetHelper;
import com.example.ivonneortega.ecommerceapp.DetailView.DetailViewActivity;
import com.example.ivonneortega.ecommerceapp.DetailView.DetailViewFragment;
import com.example.ivonneortega.ecommerceapp.Interface.MainActivityInterface;
import com.example.ivonneortega.ecommerceapp.R;
import com.example.ivonneortega.ecommerceapp.Shoe;
import com.example.ivonneortega.ecommerceapp.ShoeDecord;
import com.example.ivonneortega.ecommerceapp.Shoes_DBHelper;
import com.example.ivonneortega.ecommerceapp.Wishlist.WishlistActivity;
import com.example.ivonneortega.ecommerceapp.Wishlist.WishlistFragment;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MainActivityInterface, DetailViewFragment.OnFragmentInteractionListener, CartFragment.CartOnFragmentInteractionListener, WishlistFragment.WishlistOnFragmentInteractionListener {

    // -- Intent Constant -- //
    public static final String GOTODETAILVIEW = "detail_view";
    public static final int VIEW_MOVE_LINEAR = 1;
    public static final int VIEW_MOVE_GRID = 0;



    // -- Member Variables -- //
    private SearchView mSearch;
    //private TextView mSortBy;
    private ImageButton mSortBy;
    private TextView mSortByText;
    private ImageButton mLayoutManager;
    MainActivityRecylerViewAdapter adapter;
    private RecyclerView recyclerView;
    private ImageView mImageViewLongPress;
    private View mRootImageViewLongPress;
    boolean mSinglePane;
    private int mViewMode;
    List<Integer> mList_Selected;
    private ImageView mImage_delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DBAssetHelper dbSetup = new DBAssetHelper(MainActivity.this);
        dbSetup.getReadableDatabase();

        mImageViewLongPress = (ImageView) findViewById(R.id.image_view_long_press);
        mImageViewLongPress.setVisibility(View.GONE);
        mRootImageViewLongPress = findViewById(R.id.background_image_view_long_press);
        mRootImageViewLongPress.setVisibility(View.GONE);
        mImage_delete = (ImageView) findViewById(R.id.remove);
        mImage_delete.setVisibility(View.GONE);
        mList_Selected = new ArrayList<>();
        mViewMode = VIEW_MOVE_GRID;


        if (findViewById(R.id.frame_container_main_activity) == null) {
            mSinglePane = true;
        } else
            mSinglePane = false;

//        DetailViewFragment detailFragment = DetailViewFragment.newInstance(1);
//        getSupportFragmentManager().beginTransaction()
//                .replace(R.id.frame_container_main_activity,detailFragment)
//                .commit();

        // ------------------------------ //
        // -- SETTING UP RECYCLER VIEW -- //
        // ------------------------------ //

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_main_activity);
        recyclerView.setNestedScrollingEnabled(false);

        ShoeDecord space = new ShoeDecord(9);
        recyclerView.addItemDecoration(space);

        //Checking if Single Pane since I don't think this would make sense in tablets
        if (mSinglePane) {
            mLayoutManager = (ImageButton) findViewById(R.id.layoutManager);
            mLayoutManager.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mViewMode == VIEW_MOVE_GRID) {
                        mLayoutManager.setImageResource(R.mipmap.ic_list_black_24dp);
                        mViewMode = VIEW_MOVE_LINEAR;
                    } else {
                        mViewMode = VIEW_MOVE_GRID;
                        mLayoutManager.setImageResource(R.mipmap.ic_apps_black_24dp);
                    }
                    changeLayout();

                }
            });
        }


        // ------------------------------------- //
        // -- SORT BY BUTTON ONCLICKLISTENER -- //
        // ------------------------------------ //

        //mSortBy = (TextView) findViewById(R.id.sort_by);
        if (mSinglePane){

            mSortBy = (ImageButton) findViewById(R.id.sort_by);
            mSortBy.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    createDialog();
                }
            });
        }
        else{
            mSortByText = (TextView) findViewById(R.id.sort_by_tablet);
            mSortByText.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    createDialog();
                }
            });
        }





        // ---------------------------------------------------- //
        // -- SETTING UP SEARCH VIEW AND SEARCH FUNCIONALITY -- //
        // ---------------------------------------------------- //

        mSearch = (SearchView) findViewById(R.id.search_editText);
        mSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                InputMethodManager imm = (InputMethodManager)mSearch.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(mSearch.getWindowToken(), 0);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                List<Shoe> list_aux = new ArrayList<>();
                list_aux = Shoes_DBHelper.getInstance(mSearch.getContext()).searchByBrandOrName(newText);
                MainActivityRecylerViewAdapter adapter1;




                {
                    adapter1 = new MainActivityRecylerViewAdapter(list_aux, mSearch.getContext(), false,mViewMode);
                }
                else
                {
                    adapter1 = new MainActivityRecylerViewAdapter(list_aux,mSearch.getContext(),true,mViewMode);
                }

                recyclerView.setAdapter(adapter1);
                adapter1.notifyDataSetChanged();
                return true;
            }
        });

        //Title on custom toolbar

        TextView textView = (TextView) findViewById(R.id.title_custom_toolbar);
        textView.setText("Shoe Store");

        //Listener for cart icon on custom toolbar
        findViewById(R.id.cart_main_activity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mSinglePane)
                {
                    moveToCheckoutActivity();
                }
                else
                {
                    CartFragment detailFragment = CartFragment.newInstance();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frame_container_main_activity,detailFragment)
                            .commit();
                }
            }
        });


        //Listener for overflow icon on custom toolbar
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
                                if(mSinglePane)
                                {
                                    Intent intent = new Intent(MainActivity.this, WishlistActivity.class);
                                    startActivity(intent);
                                }
                                else
                                {
                                    WishlistFragment detailFragment = WishlistFragment.newInstance();
                                    getSupportFragmentManager().beginTransaction()
                                            .replace(R.id.frame_container_main_activity,detailFragment)
                                            .commit();
                                }
                                break;
                        }
                        return false;
                    }
                });

            }
        });

        //Long click on product images to show a larger image
        mRootImageViewLongPress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onReleaseLongClick(-1);
                findViewById(R.id.scroll_view_main_activity).setClickable(true);

            }
        });

    }


    //Method to change the layout
    public void changeLayout()
    {
        if(mSinglePane == true)
        {
            Display display = ((WindowManager) getSystemService(WINDOW_SERVICE))
                    .getDefaultDisplay();

            int orientation = display.getRotation();

            if (orientation == Surface.ROTATION_90 || orientation == Surface.ROTATION_270) {
                if(mViewMode == VIEW_MOVE_GRID)
                {
                    GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3, LinearLayoutManager.VERTICAL, false);
                    recyclerView.setLayoutManager(gridLayoutManager);
                }
                else
                {
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
                    recyclerView.setLayoutManager(linearLayoutManager);
                }
            }
            else
            {
                if(mViewMode == VIEW_MOVE_GRID)
                {
                    GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2, LinearLayoutManager.VERTICAL, false);
                    recyclerView.setLayoutManager(gridLayoutManager);
                }
                else
                {
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
                    recyclerView.setLayoutManager(linearLayoutManager);

                }
            }
            adapter = new MainActivityRecylerViewAdapter(Shoes_DBHelper.getInstance(this).getShoeList(),this,false,mViewMode);
            recyclerView.setAdapter(adapter);
        }
        else
        {

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
            recyclerView.setLayoutManager(linearLayoutManager);
            adapter = new MainActivityRecylerViewAdapter(Shoes_DBHelper.getInstance(this).getShoeList(),this,true,mViewMode);
            recyclerView.setAdapter(adapter);
        }




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
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if(id == R.id.cart)
        {
            if(mSinglePane)
            {
                moveToCheckoutActivity();
            }
            else
            {
                CartFragment detailFragment = CartFragment.newInstance();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_container_main_activity,detailFragment)
                        .commit();
            }

        }

        if(id == R.id.action_wishList)
        {
            if(mSinglePane)
            {
                Intent intent = new Intent(MainActivity.this, WishlistActivity.class);
                startActivity(intent);
            }
            else
            {
                WishlistFragment detailFragment = WishlistFragment.newInstance();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_container_main_activity,detailFragment)
                        .commit();
            }
        }


        return super.onOptionsItemSelected(item);
    }


    // -- Method from Interface -- //
    // -- Used by Main Activity Recycler View to move from Main Activity to DetailViewActivity -- //
    @Override
    public void moveToDetailActivity(int shoe_id)
    {
        if(mSinglePane)
        {
            Intent intent = new Intent(MainActivity.this, DetailViewActivity.class);
            intent.putExtra(GOTODETAILVIEW,shoe_id);
            startActivity(intent);
        }

        else
        {
            DetailViewFragment detailFragment = DetailViewFragment.newInstance(shoe_id);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frame_container_main_activity,detailFragment)
                    .commit();
        }
    }


    //Handle long click on products
    @Override
    public void onLongClick(int id) {
        ((LockableScrollView)findViewById(R.id.scroll_view_main_activity)).setScrollingEnabled(false);
        recyclerView.setClickable(false);
        mRootImageViewLongPress.setVisibility(View.VISIBLE);
        mRootImageViewLongPress.requestFocus();
        mImageViewLongPress.setVisibility(View.VISIBLE);
        Picasso.with(this)
                .load(Shoes_DBHelper.getInstance(this).getShoeById(id).getPicture_Name())
                .resize(170, 150)
                .into(mImageViewLongPress);






    }


    //Method when the long click has been released
    @Override
    public void onReleaseLongClick(int id) {
        mImageViewLongPress.setVisibility(View.GONE);
        mRootImageViewLongPress.setVisibility(View.GONE);
        ((LockableScrollView)findViewById(R.id.scroll_view_main_activity)).setScrollingEnabled(true);
        recyclerView.setClickable(true);


    }

    //Move to check out activity
    public void moveToCheckoutActivity()
    {
        Intent intent = new Intent(MainActivity.this, CartViewActivity.class);
        startActivity(intent);
    }


    //Create SORT BY dialog
    public void createDialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.sort_by)
                .setItems(R.array.sort_by_dialog, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which)
                        {
                            case 0:
                                adapter.changeShoeList(Shoes_DBHelper.getInstance(mSearch.getContext()).getListSortedByBrand());
                                adapter.notifyDataSetChanged();
                                break;
                            case 1:
                                adapter.changeShoeList(Shoes_DBHelper.getInstance(mSearch.getContext()).getListSortedByType());
                                adapter.notifyDataSetChanged();
                                break;
                            case 2:
                                adapter.changeShoeList(Shoes_DBHelper.getInstance(mSearch.getContext()).getListSortedByCategory());
                                adapter.notifyDataSetChanged();
                                break;
                            case 3:
                                //TODO FIX SORT BY PRICE
                                adapter.changeShoeList(Shoes_DBHelper.getInstance(mSearch.getContext()).getListSortedByPrice());
                                adapter.notifyDataSetChanged();
                                break;

                        }
                    }
                });
        builder.create();
        builder.show();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt("view",mViewMode);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        mViewMode = savedInstanceState.getInt("view");
        if(mViewMode == VIEW_MOVE_GRID)
        {
            mLayoutManager.setImageResource(R.mipmap.ic_apps_black_24dp);
        }
        else
        {
            mLayoutManager.setImageResource(R.mipmap.ic_list_black_24dp);
        }
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        TextView cart_quantity = (TextView) findViewById(R.id.cart_quantity_main_activity);
        cart_quantity.setText(String.valueOf(Shoes_DBHelper.getInstance(this).getNumberOfItemsInCart()));

        changeLayout();

    }


    //Method that handles when add to cart has been clicked
    @Override
    public void clickAddtoCart(int id)
    {
        Shoes_DBHelper.getInstance(this).addToCart(id);
        float aux = Shoes_DBHelper.getInstance(MainActivity.this).cartTotal();
        Snackbar snackbar =  Snackbar.make(findViewById(android.R.id.content), "Total: $"+aux, Snackbar.LENGTH_LONG);
        View snackbarView = snackbar.getView();
        snackbarView.setBackgroundColor(getResources().getColor(R.color.colorAuxProductPage));
        snackbar.setAction("GO TO CART", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mSinglePane ==true)
                {
                    moveToCheckoutActivity();
                }
                else
                {
                    CartFragment detailFragment = CartFragment.newInstance();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frame_container_main_activity,detailFragment)
                            .commit();
                }
            }
        })
                .setActionTextColor(Color.WHITE)
                .show();

        setTextCounterCartIcon();



    }


    @Override
    public void clickAddToWishlist(int id) {
        if(mSinglePane ==true)
        {
            Intent intent = new Intent(MainActivity.this, WishlistActivity.class);
            startActivity(intent);
        }
        else
        {
            addToWishlist(id);
        }

    }

    //Set the text inside the cart icon
    public void setTextCounterCartIcon()
    {
        TextView cart_quantity = (TextView) findViewById(R.id.cart_quantity_main_activity);
        cart_quantity.setText(String.valueOf(Shoes_DBHelper.getInstance(this).getNumberOfItemsInCart()));
    }


    //Add to wishlist
    public void addToWishlist(int id)
    {
        if(Shoes_DBHelper.getInstance(MainActivity.this).isInWishlist(id))
        {
            Toast.makeText(this, "Item is already in the Wishlist", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Shoes_DBHelper.getInstance(MainActivity.this).addToWishlist(id);
            Snackbar snackbar =  Snackbar.make(findViewById(android.R.id.content), "Shoe added to wishlist", Snackbar.LENGTH_LONG);
            View snackbarView = snackbar.getView();
            snackbarView.setBackgroundColor(getResources().getColor(R.color.colorAuxProductPage));
            snackbar.setAction("GO TO WISHLIST", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mSinglePane ==true)
                    {
                        Intent intent = new Intent(MainActivity.this,WishlistActivity.class);
                        startActivity(intent);
                    }
                    else
                    {
                        WishlistFragment detailFragment = WishlistFragment.newInstance().newInstance();
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.frame_container_main_activity,detailFragment)
                                .commit();
                    }
                }
            })
                    .setActionTextColor(Color.WHITE)
                    .show();
        }
    }


    //Update the cart icon count
    @Override
    public void updateCartIconCount() {
        setTextCounterCartIcon();
    }


    //Handle when products have been click to remove several at once
    @Override
    public void CarthandlePressToRemove(int id, boolean selected, final CartViewRecylerViewAdapter adapter) {
        if(!selected)
        {
            for(int i=0;i<mList_Selected.size();i++)
            {
                if(mList_Selected.get(i)==id)
                    mList_Selected.remove(i);
            }
            if(mList_Selected.size()<=0)
            {
                mImage_delete.setVisibility(View.GONE);
                findViewById(R.id.frame_layout_cart_icon_container).setVisibility(View.VISIBLE);

            }
        }
        else
        {
            mImage_delete.setVisibility(View.VISIBLE);
            findViewById(R.id.frame_layout_cart_icon_container).setVisibility(View.GONE);

            mList_Selected.add(id);
            mImage_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    removeAllSelectedFromCart(adapter);
                    mImage_delete.setVisibility(View.GONE);
                    findViewById(R.id.frame_layout_cart_icon_container).setVisibility(View.VISIBLE);

                }
            });
        }
    }

    @Override
    public void wishlistUpdateIconCart() {
        setTextCounterCartIcon();
    }

    public void removeAllSelectedFromCart(CartViewRecylerViewAdapter adapter)
    {
        List<Shoe> aux_list = new ArrayList<>();
        for(int i=0;i<mList_Selected.size();i++)
        {
            aux_list = Shoes_DBHelper.getInstance(this).getShoesInCheckout();
            Shoes_DBHelper.getInstance(this).removeFromCheckout(mList_Selected.get(i));
            Shoe shoe = Shoes_DBHelper.getInstance(this).getShoeById(mList_Selected.get(i));
            int index = aux_list.indexOf(shoe);
            aux_list.remove(shoe);
            adapter.updateList(aux_list);

        }
        updateCartIconCount();
        float sum=0;
        for(int i=0;i<aux_list.size();i++)
        {
            if(aux_list.get(i).isCheckout()== true)
            {
                sum+=aux_list.get(i).getPrice();
            }
        }

        DecimalFormat format = new DecimalFormat("#.##");
        sum = Float.valueOf(format.format(sum));
        TextView mTotal  = (TextView) findViewById(R.id.priceText_checkout);
        mTotal.setText("$"+sum);
    }


}
