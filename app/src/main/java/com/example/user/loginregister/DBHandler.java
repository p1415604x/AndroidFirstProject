package com.example.user.loginregister;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class DBHandler extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "database";

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // SQL statement to create item table
        String CREATE_ITEM_TABLE = "CREATE TABLE items ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "item TEXT, "+
                "description TEXT, "+
                "price REAL )";

        // create items table
        db.execSQL(CREATE_ITEM_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older items table if existed
        db.execSQL("DROP TABLE IF EXISTS items");

        // create fresh items table
        this.onCreate(db);
    }
    //---------------------------------------------------------------------

    /**
     * CRUD operations (create "add", read "get", update, delete) item + get all items + delete all items
     */

    // items table name
    private static final String TABLE_ITEMS = "items";

    // items Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_ITEM = "item";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_PRICE = "price";

    private static final String[] COLUMNS = {KEY_ID,KEY_ITEM,KEY_DESCRIPTION, KEY_PRICE};

    public void addItem(ItemClass item){
        Log.d("additem", item.toString());
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(KEY_ITEM, item.getItem()); // get title
        values.put(KEY_DESCRIPTION, item.getDescription()); // get author
        values.put(KEY_PRICE, item.getPrice()); // get author

        // 3. insert
        db.insert(TABLE_ITEMS, // table
                null, //nullColumnHack
                values); // key/value -> keys = column names/ values = column values

        // 4. close
        db.close();
    }

    public ItemClass getItem(int id){

        // 1. get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();

        // 2. build query
        Cursor cursor =
                db.query(TABLE_ITEMS, // a. table
                        COLUMNS, // b. column names
                        " id = ?", // c. selections
                        new String[] { String.valueOf(id) }, // d. selections args
                        null, // e. group by
                        null, // f. having
                        null, // g. order by
                        null); // h. limit

        // 3. if we got results get the first one
        if (cursor != null)
            cursor.moveToFirst();

        // 4. build item object
        ItemClass item = new ItemClass();
        item.setId(Integer.parseInt(cursor.getString(0)));
        item.setItem(cursor.getString(1));
        item.setDescription(cursor.getString(2));
        item.setPrice(Double.parseDouble(cursor.getString(3)));

        Log.d("getItem("+id+")", item.toString());

        // 5. return item
        return item;
    }

    // Get All Items
    public List<ItemClass> getAllItems() {
        List<ItemClass> items = new ArrayList<>();

        // 1. build the query
        String query = "SELECT  * FROM " + TABLE_ITEMS;

        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // 3. go over each row, build item and add it to list
        ItemClass item = null;
        if (cursor.moveToFirst()) {
            do {
                item = new ItemClass();
                item.setId(Integer.parseInt(cursor.getString(0)));
                item.setItem(cursor.getString(1));
                item.setDescription(cursor.getString(2));
                item.setPrice(Double.parseDouble(cursor.getString(3)));

                // Add item to items
                items.add(item);
            } while (cursor.moveToNext());
        }

        Log.d("getAllItems()", items.toString());

        // return items
        return items;
    }

    // Updating single item
    public int updateItem(ItemClass item) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put("item", item.getItem()); // get title
        values.put("description", item.getDescription()); // get author
        values.put("price", item.getPrice());

        // 3. updating row
        int i = db.update(TABLE_ITEMS, //table
                values, // column/value
                KEY_ID+" = ?", // selections
                new String[] { String.valueOf(item.getId()) }); //selection args

        // 4. close
        db.close();

        return i;

    }

    // Deleting single item
    public void deleteItem(ItemClass item) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. delete
        db.delete(TABLE_ITEMS,
                KEY_ID+" = ?",
                new String[] { String.valueOf(item.getId()) });
        // 3. close
        db.close();
        Log.d("deleteItem", item.toString());
    }

    // Deleting all items
    public void deleteAllItems() {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. delete
        db.delete(TABLE_ITEMS, null, null);

        // 3. close
        db.close();
        Log.d("deleted All Items", "");
    }

}
