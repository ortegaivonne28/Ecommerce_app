package com.example.ivonneortega.ecommerceapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ivonneortega on 4/5/17.
 */

public class Shoes_DBHelper extends SQLiteOpenHelper {

    private static final String TAG = Shoes_DBHelper.class.getCanonicalName();

    private static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "SHOES_DB.db";
    public static final String SHOES_TABLE_NAME = "SHOES_TABLE";

    public static final String COL_ID = "id";
    public static final String COL_BRAND = "brand";
    public static final String COL_MODEL = "model";
    public static final String COL_TYPE_OF_SHOE = "type_of_shoe";
    public static final String COL_CATEGORY = "category";
    public static final String COL_CHECKOUT = "checkout";
    public static final String COL_WISHLIST = "wishlist";
    public static final String COL_UNITS_AVAILABLE = "unit_available";
    public static final String COL_PICTURE = "picture_name";
    public static final String COL_PRICE = "price";
    public static final String COL_DESCRIPTION = "description";

    public static final String[] SHOPPING_COLUMNS = {COL_ID,COL_PICTURE, COL_BRAND, COL_TYPE_OF_SHOE, COL_MODEL, COL_CATEGORY,COL_CHECKOUT,COL_WISHLIST,COL_UNITS_AVAILABLE,COL_PRICE,COL_DESCRIPTION};

    private static final String CREATE_SHOES_LIST_TABLE =
            "CREATE TABLE " + SHOES_TABLE_NAME +
                    "(" +
                    COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COL_BRAND + " TEXT, " +
                    COL_TYPE_OF_SHOE + " TEXT, " +
                    COL_MODEL + " TEXT, " +
                    COL_CATEGORY + " TEXT " +
                    COL_CHECKOUT + "INTEGER" +
                    COL_WISHLIST + "INTEGER" +
                    COL_UNITS_AVAILABLE + "INTEGER"+
                    COL_PICTURE +"TEXT"+
                    COL_PRICE + "TEXT" +
                    COL_DESCRIPTION + "TEXT"+")";

    private static Shoes_DBHelper sInstance;

    public static Shoes_DBHelper getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new Shoes_DBHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    private Shoes_DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_SHOES_LIST_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + SHOES_TABLE_NAME);
        this.onCreate(db);
    }

    public long addShoe(String name, String description, String price, String type) {

        //TODO CHANGE THIS AND INCLUDE ITEMS FROM THE DATABASE

        ContentValues values = new ContentValues();
        values.put(COL_BRAND, name);
        values.put(COL_TYPE_OF_SHOE, description);
        values.put(COL_MODEL, price);
        values.put(COL_CATEGORY, type);

        SQLiteDatabase db = this.getWritableDatabase();
        long returnId = db.insert(SHOES_TABLE_NAME, null, values);
        db.close();
        return returnId;
    }

    public List<Shoe> getShoeList() {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(SHOES_TABLE_NAME, // a. table
                SHOPPING_COLUMNS, // b. column names
                null, // c. selections
                null, // d. selections args
                null, // e. group by
                null, // f. having
                null, // g. order by
                null); // h. limit

        List<Shoe> shoeItems = new ArrayList<>();

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                shoeItems.add(new Shoe(cursor.getInt(cursor.getColumnIndex(COL_ID)),
                        cursor.getInt(cursor.getColumnIndex(COL_UNITS_AVAILABLE)),
                        cursor.getString(cursor.getColumnIndex(COL_BRAND)),
                        cursor.getString(cursor.getColumnIndex(COL_MODEL)),
                        cursor.getString(cursor.getColumnIndex(COL_TYPE_OF_SHOE)),
                        cursor.getString(cursor.getColumnIndex(COL_CATEGORY)),
                        cursor.getString(cursor.getColumnIndex(COL_PICTURE)),
                        cursor.getInt(cursor.getColumnIndex(COL_CHECKOUT)),
                        cursor.getInt(cursor.getColumnIndex(COL_WISHLIST)),
                        Float.parseFloat(cursor.getString(cursor.getColumnIndex(COL_PRICE))),
                        cursor.getString(cursor.getColumnIndex(COL_DESCRIPTION))));
                cursor.moveToNext();
            }
        }
        cursor.close();
        return shoeItems;
    }

    public Shoe getShoeById(int id) {
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(SHOES_TABLE_NAME, // a. table
                SHOPPING_COLUMNS, // b. column names
                COL_ID + " = ?", // c. selections
                new String[]{String.valueOf(id)}, // d. selections args
                null, // e. group by
                null, // f. having
                null, // g. order by
                null); // h. limit

        if (cursor.moveToFirst()) {
            String brand = cursor.getString(cursor.getColumnIndex(COL_BRAND));
            String type_of_shoe = cursor.getString(cursor.getColumnIndex(COL_TYPE_OF_SHOE));
            String model = cursor.getString(cursor.getColumnIndex(COL_MODEL));
            String category = cursor.getString(cursor.getColumnIndex(COL_CATEGORY));
            int checkout = cursor.getInt(cursor.getColumnIndex(COL_CHECKOUT));
            int unitsAvailable = cursor.getInt(cursor.getColumnIndex(COL_UNITS_AVAILABLE));
            int wishlist = cursor.getInt(cursor.getColumnIndex(COL_WISHLIST));
            float price = Float.parseFloat(cursor.getString(cursor.getColumnIndex(COL_PRICE)));
            String description = cursor.getString(cursor.getColumnIndex(COL_DESCRIPTION));
            String picture_name = cursor.getString(cursor.getColumnIndex(COL_PICTURE));


            cursor.close();
            return new Shoe(id,unitsAvailable, brand, type_of_shoe, model, category,picture_name,checkout,wishlist,price,description);
        } else {
            cursor.close();
            return null;
        }
    }

//    public void updateGameYear(String name,int year)
//    {
//        SQLiteDatabase db = getWritableDatabase();
//        ContentValues values = new ContentValues();
//
//        values.put(COL_GAME_YEAR,year);
//
//        db.update(FAVORITES_TABLE_NAME,values,COL_GAME_NAME+"= ?",new String[]{name});
//        db.close();
//
//    }


    public List<Shoe> shoesInCart()
    {
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        //builder.setTables(SHOES_TABLE_NAME + " JOIN "+ JobTable.TABLE_NAME + " ON "+
          //      EmployeeTable.TABLE_NAME+"."+EmployeeTable.COLUMN_SSN + " = "+JobTable.TABLE_NAME+"."+JobTable.COLUMN_SSN);
        //Cursor cursor = builder.query(db,new String[]{EmployeeTable.COLUMN_FIRST_NAME,EmployeeTable.COLUMN_LAST_NAME},JobTable.COLUMN_COMPANY+" = ?",new String[]{"Macys"},null,null,null);

        Cursor cursor = db.query(SHOES_TABLE_NAME,null,
                COL_CHECKOUT+"= ?",
                new String[]{String.valueOf(Shoe.TRUE)},null,null,null);

        List<Shoe> list = new ArrayList<>();
        if(cursor.moveToFirst())
        {
            list = new ArrayList<>();
            while(!cursor.isAfterLast())
            {
                list.add(new Shoe(cursor.getInt(cursor.getColumnIndex(COL_ID)),
                        cursor.getInt(cursor.getColumnIndex(COL_UNITS_AVAILABLE)),
                        cursor.getString(cursor.getColumnIndex(COL_BRAND)),
                        cursor.getString(cursor.getColumnIndex(COL_MODEL)),
                        cursor.getString(cursor.getColumnIndex(COL_TYPE_OF_SHOE)),
                        cursor.getString(cursor.getColumnIndex(COL_CATEGORY)),
                        cursor.getString(cursor.getColumnIndex(COL_PICTURE)),
                        cursor.getInt(cursor.getColumnIndex(COL_CHECKOUT)),
                        cursor.getInt(cursor.getColumnIndex(COL_WISHLIST)),
                        Float.parseFloat(cursor.getString(cursor.getColumnIndex(COL_PRICE))),
                        cursor.getString(cursor.getColumnIndex(COL_DESCRIPTION))));
                cursor.moveToNext();
            }
        }
        cursor.close();
        return list;
    }

    public List<Shoe> shoesInWishList()
    {
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        //builder.setTables(SHOES_TABLE_NAME + " JOIN "+ JobTable.TABLE_NAME + " ON "+
        //      EmployeeTable.TABLE_NAME+"."+EmployeeTable.COLUMN_SSN + " = "+JobTable.TABLE_NAME+"."+JobTable.COLUMN_SSN);
        //Cursor cursor = builder.query(db,new String[]{EmployeeTable.COLUMN_FIRST_NAME,EmployeeTable.COLUMN_LAST_NAME},JobTable.COLUMN_COMPANY+" = ?",new String[]{"Macys"},null,null,null);

        Cursor cursor = db.query(SHOES_TABLE_NAME,null,
                COL_WISHLIST+"= ?",
                new String[]{String.valueOf(Shoe.TRUE)},null,null,null);

        List<Shoe> list = new ArrayList<>();
        if(cursor.moveToFirst())
        {
            list = new ArrayList<>();
            while(!cursor.isAfterLast())
            {
                list.add(new Shoe(cursor.getInt(cursor.getColumnIndex(COL_ID)),
                        cursor.getInt(cursor.getColumnIndex(COL_UNITS_AVAILABLE)),
                        cursor.getString(cursor.getColumnIndex(COL_BRAND)),
                        cursor.getString(cursor.getColumnIndex(COL_MODEL)),
                        cursor.getString(cursor.getColumnIndex(COL_TYPE_OF_SHOE)),
                        cursor.getString(cursor.getColumnIndex(COL_CATEGORY)),
                        cursor.getString(cursor.getColumnIndex(COL_PICTURE)),
                        cursor.getInt(cursor.getColumnIndex(COL_CHECKOUT)),
                        cursor.getInt(cursor.getColumnIndex(COL_WISHLIST)),
                        Float.parseFloat(cursor.getString(cursor.getColumnIndex(COL_PRICE))),
                        cursor.getString(cursor.getColumnIndex(COL_DESCRIPTION))));
                cursor.moveToNext();
            }
        }
        cursor.close();
        return list;
    }

    public List<Shoe> searchByBrandOrName(String aux)
    {
        SQLiteDatabase db = getReadableDatabase();
        //SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        //builder.setTables(SHOES_TABLE_NAME + " JOIN "+ JobTable.TABLE_NAME + " ON "+
        //      EmployeeTable.TABLE_NAME+"."+EmployeeTable.COLUMN_SSN + " = "+JobTable.TABLE_NAME+"."+JobTable.COLUMN_SSN);
        //Cursor cursor = builder.query(db,new String[]{EmployeeTable.COLUMN_FIRST_NAME,EmployeeTable.COLUMN_LAST_NAME},JobTable.COLUMN_COMPANY+" = ?",new String[]{"Macys"},null,null,null);

        Cursor cursor = db.query(SHOES_TABLE_NAME,null,
                COL_BRAND+" LIKE ? OR "+COL_MODEL+" LIKE ?",
                new String[]{aux+"%",aux+"%"},null,null,null);

        List<Shoe> list = new ArrayList<>();
        if(cursor.moveToFirst())
        {
            list = new ArrayList<>();
            while(!cursor.isAfterLast())
            {
                list.add(new Shoe(cursor.getInt(cursor.getColumnIndex(COL_ID)),
                        cursor.getInt(cursor.getColumnIndex(COL_UNITS_AVAILABLE)),
                        cursor.getString(cursor.getColumnIndex(COL_BRAND)),
                        cursor.getString(cursor.getColumnIndex(COL_MODEL)),
                        cursor.getString(cursor.getColumnIndex(COL_TYPE_OF_SHOE)),
                        cursor.getString(cursor.getColumnIndex(COL_CATEGORY)),
                        cursor.getString(cursor.getColumnIndex(COL_PICTURE)),
                        cursor.getInt(cursor.getColumnIndex(COL_CHECKOUT)),
                        cursor.getInt(cursor.getColumnIndex(COL_WISHLIST)),
                        Float.parseFloat(cursor.getString(cursor.getColumnIndex(COL_PRICE))),
                        cursor.getString(cursor.getColumnIndex(COL_DESCRIPTION))));
                cursor.moveToNext();
            }
        }
        cursor.close();
        return list;
    }

    public void addToCart(int id)
    {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COL_CHECKOUT,Shoe.TRUE);

        db.update(SHOES_TABLE_NAME,values,COL_ID+"= "+id,null);
        db.close();
    }

    public void addToWishlist(int id)
    {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COL_WISHLIST,Shoe.TRUE);

        db.update(SHOES_TABLE_NAME,values,COL_ID+"= "+id,null);
        db.close();
    }

    public void removeFromCheckout(int id)
    {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COL_CHECKOUT,Shoe.FALSE);

        db.update(SHOES_TABLE_NAME,values,COL_ID+"= "+id,null);
        db.close();
    }

    public void removeFromWishlist(int id)
    {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COL_WISHLIST,Shoe.FALSE);

        db.update(SHOES_TABLE_NAME,values,COL_ID+"= "+id,null);
        db.close();
    }

    public List<Shoe> getShoesInCheckout()
    {
        SQLiteDatabase db = getReadableDatabase();
        //SQLiteQueryBuilder builder = new SQLiteQueryBuilder();

        //int value_true = Shoe.TRUE;

        Cursor cursor = db.query(SHOES_TABLE_NAME,null,
                COL_CHECKOUT+" = ? ",
                new String[]{String.valueOf(Shoe.TRUE)},null,null,null);

        List<Shoe> list = new ArrayList<>();
        if(cursor.moveToFirst())
        {
            list = new ArrayList<>();
            while(!cursor.isAfterLast())
            {
                list.add(new Shoe(cursor.getInt(cursor.getColumnIndex(COL_ID)),
                        cursor.getInt(cursor.getColumnIndex(COL_UNITS_AVAILABLE)),
                        cursor.getString(cursor.getColumnIndex(COL_BRAND)),
                        cursor.getString(cursor.getColumnIndex(COL_MODEL)),
                        cursor.getString(cursor.getColumnIndex(COL_TYPE_OF_SHOE)),
                        cursor.getString(cursor.getColumnIndex(COL_CATEGORY)),
                        cursor.getString(cursor.getColumnIndex(COL_PICTURE)),
                        cursor.getInt(cursor.getColumnIndex(COL_CHECKOUT)),
                        cursor.getInt(cursor.getColumnIndex(COL_WISHLIST)),
                        Float.parseFloat(cursor.getString(cursor.getColumnIndex(COL_PRICE))),
                        cursor.getString(cursor.getColumnIndex(COL_DESCRIPTION))));
                cursor.moveToNext();
            }
        }
        cursor.close();
        return list;
    }

    public List<Shoe> getShoesInWishlist()
    {
        SQLiteDatabase db = getReadableDatabase();
        //SQLiteQueryBuilder builder = new SQLiteQueryBuilder();

        Cursor cursor = db.query(SHOES_TABLE_NAME,null,
                COL_WISHLIST+" = ? ",
                new String[]{String.valueOf(Shoe.TRUE)},null,null,null);

        List<Shoe> list = new ArrayList<>();
        if(cursor.moveToFirst())
        {
            list = new ArrayList<>();
            while(!cursor.isAfterLast())
            {
                list.add(new Shoe(cursor.getInt(cursor.getColumnIndex(COL_ID)),
                        cursor.getInt(cursor.getColumnIndex(COL_UNITS_AVAILABLE)),
                        cursor.getString(cursor.getColumnIndex(COL_BRAND)),
                        cursor.getString(cursor.getColumnIndex(COL_MODEL)),
                        cursor.getString(cursor.getColumnIndex(COL_TYPE_OF_SHOE)),
                        cursor.getString(cursor.getColumnIndex(COL_CATEGORY)),
                        cursor.getString(cursor.getColumnIndex(COL_PICTURE)),
                        cursor.getInt(cursor.getColumnIndex(COL_CHECKOUT)),
                        cursor.getInt(cursor.getColumnIndex(COL_WISHLIST)),
                        Float.parseFloat(cursor.getString(cursor.getColumnIndex(COL_PRICE))),
                        cursor.getString(cursor.getColumnIndex(COL_DESCRIPTION))));
                cursor.moveToNext();
            }
        }
        cursor.close();
        return list;
    }

    public List<Shoe> getListSortedByBrand()
    {
        SQLiteDatabase db = getReadableDatabase();
        //SQLiteQueryBuilder builder = new SQLiteQueryBuilder();

        Cursor cursor = db.query(SHOES_TABLE_NAME,null,
                null, null,null,null,COL_BRAND);

        List<Shoe> list = new ArrayList<>();
        if(cursor.moveToFirst())
        {
            list = new ArrayList<>();
            while(!cursor.isAfterLast())
            {
                list.add(new Shoe(cursor.getInt(cursor.getColumnIndex(COL_ID)),
                        cursor.getInt(cursor.getColumnIndex(COL_UNITS_AVAILABLE)),
                        cursor.getString(cursor.getColumnIndex(COL_BRAND)),
                        cursor.getString(cursor.getColumnIndex(COL_MODEL)),
                        cursor.getString(cursor.getColumnIndex(COL_TYPE_OF_SHOE)),
                        cursor.getString(cursor.getColumnIndex(COL_CATEGORY)),
                        cursor.getString(cursor.getColumnIndex(COL_PICTURE)),
                        cursor.getInt(cursor.getColumnIndex(COL_CHECKOUT)),
                        cursor.getInt(cursor.getColumnIndex(COL_WISHLIST)),
                        Float.parseFloat(cursor.getString(cursor.getColumnIndex(COL_PRICE))),
                        cursor.getString(cursor.getColumnIndex(COL_DESCRIPTION))));
                cursor.moveToNext();
            }
        }
        cursor.close();
        return list;
    }

    public List<Shoe> getListSortedByType()
    {
        SQLiteDatabase db = getReadableDatabase();
        //SQLiteQueryBuilder builder = new SQLiteQueryBuilder();

        Cursor cursor = db.query(SHOES_TABLE_NAME,null,
                null, null,null,null,COL_TYPE_OF_SHOE);

        List<Shoe> list = new ArrayList<>();
        if(cursor.moveToFirst())
        {
            list = new ArrayList<>();
            while(!cursor.isAfterLast())
            {
                list.add(new Shoe(cursor.getInt(cursor.getColumnIndex(COL_ID)),
                        cursor.getInt(cursor.getColumnIndex(COL_UNITS_AVAILABLE)),
                        cursor.getString(cursor.getColumnIndex(COL_BRAND)),
                        cursor.getString(cursor.getColumnIndex(COL_MODEL)),
                        cursor.getString(cursor.getColumnIndex(COL_TYPE_OF_SHOE)),
                        cursor.getString(cursor.getColumnIndex(COL_CATEGORY)),
                        cursor.getString(cursor.getColumnIndex(COL_PICTURE)),
                        cursor.getInt(cursor.getColumnIndex(COL_CHECKOUT)),
                        cursor.getInt(cursor.getColumnIndex(COL_WISHLIST)),
                        Float.parseFloat(cursor.getString(cursor.getColumnIndex(COL_PRICE))),
                        cursor.getString(cursor.getColumnIndex(COL_DESCRIPTION))));
                cursor.moveToNext();
            }
        }
        cursor.close();
        return list;
    }

    public List<Shoe> getListSortedByCategory()
    {
        SQLiteDatabase db = getReadableDatabase();
        //SQLiteQueryBuilder builder = new SQLiteQueryBuilder();

        Cursor cursor = db.query(SHOES_TABLE_NAME,null,
                null, null,null,null,COL_CATEGORY);

        List<Shoe> list = new ArrayList<>();
        if(cursor.moveToFirst())
        {
            list = new ArrayList<>();
            while(!cursor.isAfterLast())
            {
                list.add(new Shoe(cursor.getInt(cursor.getColumnIndex(COL_ID)),
                        cursor.getInt(cursor.getColumnIndex(COL_UNITS_AVAILABLE)),
                        cursor.getString(cursor.getColumnIndex(COL_BRAND)),
                        cursor.getString(cursor.getColumnIndex(COL_MODEL)),
                        cursor.getString(cursor.getColumnIndex(COL_TYPE_OF_SHOE)),
                        cursor.getString(cursor.getColumnIndex(COL_CATEGORY)),
                        cursor.getString(cursor.getColumnIndex(COL_PICTURE)),
                        cursor.getInt(cursor.getColumnIndex(COL_CHECKOUT)),
                        cursor.getInt(cursor.getColumnIndex(COL_WISHLIST)),
                        Float.parseFloat(cursor.getString(cursor.getColumnIndex(COL_PRICE))),
                        cursor.getString(cursor.getColumnIndex(COL_DESCRIPTION))));
                cursor.moveToNext();
            }
        }
        cursor.close();
        return list;
    }

    public List<Shoe> getListSortedByPrice()
    {
        SQLiteDatabase db = getReadableDatabase();
        //SQLiteQueryBuilder builder = new SQLiteQueryBuilder();

        Cursor cursor = db.query(SHOES_TABLE_NAME,null,
                null, null,null,null,COL_PRICE);

        List<Shoe> list = new ArrayList<>();
        if(cursor.moveToFirst())
        {
            list = new ArrayList<>();
            while(!cursor.isAfterLast())
            {
                list.add(new Shoe(cursor.getInt(cursor.getColumnIndex(COL_ID)),
                        cursor.getInt(cursor.getColumnIndex(COL_UNITS_AVAILABLE)),
                        cursor.getString(cursor.getColumnIndex(COL_BRAND)),
                        cursor.getString(cursor.getColumnIndex(COL_MODEL)),
                        cursor.getString(cursor.getColumnIndex(COL_TYPE_OF_SHOE)),
                        cursor.getString(cursor.getColumnIndex(COL_CATEGORY)),
                        cursor.getString(cursor.getColumnIndex(COL_PICTURE)),
                        cursor.getInt(cursor.getColumnIndex(COL_CHECKOUT)),
                        cursor.getInt(cursor.getColumnIndex(COL_WISHLIST)),
                        Float.parseFloat(cursor.getString(cursor.getColumnIndex(COL_PRICE))),
                        cursor.getString(cursor.getColumnIndex(COL_DESCRIPTION))));
                cursor.moveToNext();
            }
        }
        cursor.close();
        return list;
    }

    public boolean isInCart(int id)
    {
        SQLiteDatabase db = getReadableDatabase();
        //SQLiteQueryBuilder builder = new SQLiteQueryBuilder();

        Cursor cursor = db.query(SHOES_TABLE_NAME,null,
                COL_ID+" = ? ",
                new String[]{String.valueOf(id)},null,null,null);

        List<Shoe> list = new ArrayList<>();
        if(cursor.moveToFirst())
        {
            if(cursor.getInt(cursor.getColumnIndex(COL_CHECKOUT))==Shoe.TRUE)
            {
                cursor.close();
                return true;
            }
        }
        cursor.close();
        return false;
    }

    public boolean isInWishlist(int id)
    {
        SQLiteDatabase db = getReadableDatabase();
        //SQLiteQueryBuilder builder = new SQLiteQueryBuilder();

        Cursor cursor = db.query(SHOES_TABLE_NAME,null,
                COL_ID+" = ? ",
                new String[]{String.valueOf(id)},null,null,null);

        List<Shoe> list = new ArrayList<>();
        if(cursor.moveToFirst())
        {
            if(cursor.getInt(cursor.getColumnIndex(COL_WISHLIST))==Shoe.TRUE)
            {
                cursor.close();
                return true;
            }
        }
        cursor.close();
        return false;
    }

    public void removeAllFromCart()
    {
        SQLiteDatabase db = getReadableDatabase();
        //SQLiteQueryBuilder builder = new SQLiteQueryBuilder();

        Cursor cursor = db.query(SHOES_TABLE_NAME,null,
                COL_CHECKOUT+" = ? ",
                new String[]{String.valueOf(Shoe.TRUE)},null,null,null);

        if(cursor.moveToFirst())
        {
            while(!cursor.isAfterLast())
            {
                removeFromCheckout(cursor.getInt(cursor.getColumnIndex(COL_ID)));
                cursor.moveToNext();
            }

        }
        cursor.close();
    }

    public void removeAllFromWishlist()
    {
        SQLiteDatabase db = getReadableDatabase();
        //SQLiteQueryBuilder builder = new SQLiteQueryBuilder();

        Cursor cursor = db.query(SHOES_TABLE_NAME,null,
                COL_WISHLIST+" = ? ",
                new String[]{String.valueOf(Shoe.TRUE)},null,null,null);

        if(cursor.moveToFirst())
        {
            while(!cursor.isAfterLast())
            {
                removeFromCheckout(cursor.getInt(cursor.getColumnIndex(COL_ID)));
                cursor.moveToNext();

            }

        }
        cursor.close();
    }

    public float cartTotal()
    {
        SQLiteDatabase db = getReadableDatabase();
        float aux=0;
        //SQLiteQueryBuilder builder = new SQLiteQueryBuilder();

        Cursor cursor = db.query(SHOES_TABLE_NAME,null,
                COL_CHECKOUT+" = ? ",
                new String[]{String.valueOf(Shoe.TRUE)},null,null,null);

        if(cursor.moveToFirst())
        {
            while(!cursor.isAfterLast())
            {
                aux+= Float.parseFloat(cursor.getString(cursor.getColumnIndex(COL_PRICE)));
                cursor.moveToNext();

            }

        }
        cursor.close();
        DecimalFormat format = new DecimalFormat("#.##");
        aux = Float.valueOf(format.format(aux));
        return aux;
    }

    public int getNumberOfItemsInCart()
    {
        SQLiteDatabase db = getReadableDatabase();
        int aux=0;
        //SQLiteQueryBuilder builder = new SQLiteQueryBuilder();

        Cursor cursor = db.query(SHOES_TABLE_NAME,null,
                COL_CHECKOUT+" = ? ",
                new String[]{String.valueOf(Shoe.TRUE)},null,null,null);

        if(cursor.moveToFirst())
        {
            while(!cursor.isAfterLast())
            {
                aux++;
                cursor.moveToNext();

            }

        }
        cursor.close();
        return aux;
    }


}
