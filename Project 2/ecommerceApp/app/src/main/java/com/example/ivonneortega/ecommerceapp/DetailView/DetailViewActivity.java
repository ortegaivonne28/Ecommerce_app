package com.example.ivonneortega.ecommerceapp.DetailView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ivonneortega.ecommerceapp.CartActivity.CartViewActivity;
import com.example.ivonneortega.ecommerceapp.MainActivity.MainActivity;
import com.example.ivonneortega.ecommerceapp.R;
import com.example.ivonneortega.ecommerceapp.Shoe;
import com.example.ivonneortega.ecommerceapp.Shoes_DBHelper;
import com.example.ivonneortega.ecommerceapp.Wishlist.WishlistActivity;
import com.squareup.picasso.Picasso;

public class DetailViewActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String GOTOCARTVIEWID = "cart_view";

    private int mId;
    private TextView mBrand,mModel,mDescription,mPrice,mTypeOfShoe;
    private ImageView mImage;
    private Spinner mSize;
    private String mSelectedSize;
    private Button mAddToCart,mAddToCart2,mSize6,mSize7,mSize8,mSize9,mSize10,mSize11,mWishlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final Intent intent = getIntent();
        mId = intent.getIntExtra(MainActivity.GOTODETAILVIEW,-1);
        if(mId!=-1)
        {
            setViews();
        }

        TextView textView = (TextView) findViewById(R.id.title_custom_toolbar);
        textView.setText("Shoe Store");

        findViewById(R.id.root_toolbar).setBackgroundColor(getResources().getColor(R.color.colorPrimaryProductPage));

        findViewById(R.id.cart_main_activity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailViewActivity.this,CartViewActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.title_custom_toolbar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(DetailViewActivity.this,MainActivity.class);
                intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent1);
                finish();

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
                                Intent intent = new Intent(DetailViewActivity.this,WishlistActivity.class);
                                startActivity(intent);
                                break;
                        }
                        return false;
                    }
                });

            }
        });


    }

    public void setViews()
    {
        Shoe shoe = Shoes_DBHelper.getInstance(this).getShoeById(mId);
        mBrand = (TextView) findViewById(R.id.brand_detail_view);
        mModel = (TextView) findViewById(R.id.model_detail_view);
        mDescription = (TextView) findViewById(R.id.description_detail_view);
        mPrice = (TextView) findViewById(R.id.price_detail_view);
        mImage = (ImageView) findViewById(R.id.detail_view_image);
        mTypeOfShoe = (TextView) findViewById(R.id.type_of_shoe_detail_view);
       // mSize = (Spinner) findViewById(R.id.size_spinner);
        mAddToCart = (Button) findViewById(R.id.detail_view_add_to_cart);
        mAddToCart2 = (Button) findViewById(R.id.addToCart_detail_view2);
        mWishlist = (Button) findViewById(R.id.addToWishlist_detail_view);
        mSize6 = (Button) findViewById(R.id.size6);
        mSize7 = (Button) findViewById(R.id.size7);
        mSize8 = (Button) findViewById(R.id.size8);
        mSize9 = (Button) findViewById(R.id.size9);
        mSize10 = (Button) findViewById(R.id.size10);
        mSize11 = (Button) findViewById(R.id.size11);
        mSize6.setOnClickListener(this);
        mSize7.setOnClickListener(this);
        mSize8.setOnClickListener(this);
        mSize9.setOnClickListener(this);
        mSize10.setOnClickListener(this);
        mSize11.setOnClickListener(this);
        mAddToCart.setOnClickListener(this);
        mAddToCart2.setOnClickListener(this);
        mWishlist.setOnClickListener(this);

        findViewById(R.id.remove).setVisibility(View.GONE);

        mBrand.setText(shoe.getBrand());
        mTypeOfShoe.setText(shoe.getType());
        mModel.setText(shoe.getModel());
        mPrice.setText("$"+String.valueOf(shoe.getPrice()));
        mPrice.setClickable(false);
        mDescription.setText(shoe.getDescription());
        Picasso.with(this)
                .load(shoe.getPicture_Name())
                .resize(480, 400)
                .into(mImage);

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
            Intent intent = new Intent(DetailViewActivity.this,CartViewActivity.class);
            startActivity(intent);
        }

        if(id == R.id.action_wishList)
        {
            Intent intent = new Intent(DetailViewActivity.this, WishlistActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {

        mSize6.setBackgroundColor(Color.parseColor("#ededed"));
        mSize7.setBackgroundColor(Color.parseColor("#ededed"));
        mSize8.setBackgroundColor(Color.parseColor("#ededed"));
        mSize9.setBackgroundColor(Color.parseColor("#ededed"));
        mSize10.setBackgroundColor(Color.parseColor("#ededed"));
        mSize11.setBackgroundColor(Color.parseColor("#ededed"));
        switch (v.getId())
        {
            case R.id.size6:
                mSize6.setBackgroundColor(getResources().getColor(R.color.colorSelectedSizeProductPage));
                mSelectedSize = "6.0";
                break;
            case R.id.size7:
                mSize7.setBackgroundColor(getResources().getColor(R.color.colorSelectedSizeProductPage));
                mSelectedSize = "7.0";
                break;
            case R.id.size8:
                mSize8.setBackgroundColor(getResources().getColor(R.color.colorSelectedSizeProductPage));
                mSelectedSize = "8.0";
                break;
            case R.id.size9:
                mSize9.setBackgroundColor(getResources().getColor(R.color.colorSelectedSizeProductPage));
                mSelectedSize = "9.0";
                break;
            case R.id.size10:
                mSize10.setBackgroundColor(getResources().getColor(R.color.colorSelectedSizeProductPage));
                mSelectedSize = "10.0";
                break;
            case R.id.size11:
                mSize11.setBackgroundColor(getResources().getColor(R.color.colorSelectedSizeProductPage));
                mSelectedSize = "11.0";
                break;
            case R.id.detail_view_add_to_cart:
                addToCard();
                break;
            case R.id.addToCart_detail_view2:
                addToCard();
                break;
            case R.id.addToWishlist_detail_view:
                addToWishlist();


        }
    }


    public void addToWishlist()
    {
        if(Shoes_DBHelper.getInstance(DetailViewActivity.this).isInWishlist(mId))
        {
            Toast.makeText(this, "Item is already in the Wishlist", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Shoes_DBHelper.getInstance(DetailViewActivity.this).addToWishlist(mId);
            Snackbar snackbar =  Snackbar.make(findViewById(android.R.id.content), "Shoe added to wishlist", Snackbar.LENGTH_LONG);
            View snackbarView = snackbar.getView();
            snackbarView.setBackgroundColor(getResources().getColor(R.color.colorAuxProductPage));
            snackbar.setAction("GO TO WISHLIST", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(DetailViewActivity.this, WishlistActivity.class);
                    startActivity(intent);
                }
            })
                    .setActionTextColor(Color.WHITE)
                    .show();
        }
    }


    public void addToCard()
    {
        Shoes_DBHelper.getInstance(this).addToCart(mId);
        float aux = Shoes_DBHelper.getInstance(DetailViewActivity.this).cartTotal();
        Snackbar snackbar =  Snackbar.make(findViewById(android.R.id.content), "Total: $"+aux, Snackbar.LENGTH_LONG);
        View snackbarView = snackbar.getView();
        snackbarView.setBackgroundColor(getResources().getColor(R.color.colorAuxProductPage));
                snackbar.setAction("GO TO CART", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(DetailViewActivity.this, CartViewActivity.class);
                        startActivity(intent);
                    }
                })
                .setActionTextColor(Color.WHITE)
                .show();

        setTextCounterCartIcon();

//        Intent intent = new Intent(DetailViewActivity.this, CartViewActivity.class);
//        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setTextCounterCartIcon();
    }

    public void setTextCounterCartIcon()
    {
        TextView cart_quantity = (TextView) findViewById(R.id.cart_quantity_main_activity);
        cart_quantity.setText(String.valueOf(Shoes_DBHelper.getInstance(this).getNumberOfItemsInCart()));
    }
}
