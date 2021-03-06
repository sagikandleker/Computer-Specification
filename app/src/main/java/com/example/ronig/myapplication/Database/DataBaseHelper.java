package com.example.ronig.myapplication.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.ronig.myapplication.Activities.MainActivity;
import com.example.ronig.myapplication.Objects.Products.CPU;
import com.example.ronig.myapplication.Objects.Products.Case;
import com.example.ronig.myapplication.Objects.Products.GPU;
import com.example.ronig.myapplication.Objects.Products.RAM;
import com.example.ronig.myapplication.Objects.Products.MotherBoard;
import com.example.ronig.myapplication.Objects.Order;
import com.example.ronig.myapplication.Objects.Products.Product;
import com.example.ronig.myapplication.Objects.Products.SSD;
import com.example.ronig.myapplication.Objects.User;

import java.util.ArrayList;

public class DataBaseHelper extends SQLiteOpenHelper {


    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "Database.db";

    // User table name
    private static final String TABLE_USER = "user";
    private static final String TABLE_CPU = "cpu";
    private static final String TABLE_GPU = "gpu";
    private static final String TABLE_RAM = "ram";
    private static final String TABLE_MOTHERBOARD = "motherboard";
    private static final String TABLE_SSD = "SSD";
    private static final String TABLE_CASE = "case_";
    private static final String TABLE_ORDERS = "orders";


    // User Table Columns names
    private static final String COLUMN_USER_ID = "ID";
    private static final String COLUMN_USER_NAME = "Username";
    private static final String COLUMN_USER_PASSWORD = "Password";
    private static final String COLUMN_USER_EMAIL = "Email";

    // User Table Columns names
    private static final String COLUMN_ID = "ID";
    private static final String COLUMN_NAME = "Name";
    private static final String COLUMN_PRICE = "Price";


    //  Product Table Columns names

    private static final String COLUMN_STATUS = "Status";
    private static final String COLUMN_TOTAL_PRICE = "TotalPrice";
    private static final String COLUMN_USER_ORDER = "UserOrder";
    private static final String COLUMN_ID_ORDER ="ID";



    // create table sql query
    private String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "("
            + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_USER_NAME + " TEXT,"
            + COLUMN_USER_PASSWORD + " TEXT," + COLUMN_USER_EMAIL + " TEXT" + ")";

    private String CREATE_CPU_TABLE = "CREATE TABLE " + TABLE_CPU + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_NAME + " TEXT,"
            + COLUMN_PRICE + " TEXT" + ")";
    private String CREATE_MOTHERBOARD_TABLE = "CREATE TABLE " + TABLE_MOTHERBOARD + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_NAME + " TEXT,"
            + COLUMN_PRICE + " TEXT" + ")";

    private String CREATE_RAM_TABLE = "CREATE TABLE " + TABLE_RAM + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_NAME + " TEXT,"
            + COLUMN_PRICE + " TEXT" + ")";
    private String CREATE_SSD_TABLE = "CREATE TABLE " + TABLE_SSD + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_NAME + " TEXT,"
            + COLUMN_PRICE + " TEXT" + ")";
    private String CREATE_GPU_TABLE = "CREATE TABLE " + TABLE_GPU + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_NAME + " TEXT,"
            + COLUMN_PRICE + " TEXT" + ")";
    private String CREATE_CASE_TABLE = "CREATE TABLE " + TABLE_CASE + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_NAME + " TEXT,"
            + COLUMN_PRICE + " TEXT" + ")";
    private String CREATE_ORDERS_TABLE = "CREATE TABLE " + TABLE_ORDERS + "("
            + COLUMN_ID_ORDER + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_USER_ORDER + " TEXT,"
            + COLUMN_TOTAL_PRICE + " TEXT, " + COLUMN_STATUS + " TEXT" + ")";




    // drop table sql query
    private String DROP_USER_TABLE = "DROP TABLE IF EXISTS " + TABLE_USER;

    private String DROP_CPU_TABLE = "DROP TABLE IF EXISTS " + TABLE_CPU;
    private String DROP_MOTHERBOARD_TABLE = "DROP TABLE IF EXISTS " + TABLE_MOTHERBOARD;
    private String DROP_RAM_TABLE = "DROP TABLE IF EXISTS " + TABLE_RAM;
    private String DROP_SSD_TABLE = "DROP TABLE IF EXISTS " + TABLE_SSD;
    private String DROP_GPU_TABLE = "DROP TABLE IF EXISTS " + TABLE_GPU;
    private String DROP_CASE_TABLE = "DROP TABLE IF EXISTS " + TABLE_CASE;
    private String DROP_ORDERS_TABLE = "DROP TABLE IF EXISTS " + TABLE_ORDERS;


    /**
     * Constructor
     *
     * @param context
     */
    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        //Drop User Table if exist
        db.execSQL(DROP_USER_TABLE);
        db.execSQL(DROP_CPU_TABLE);
        db.execSQL(DROP_MOTHERBOARD_TABLE);
        db.execSQL(DROP_RAM_TABLE);
        db.execSQL(DROP_SSD_TABLE);
        db.execSQL(DROP_GPU_TABLE);
        db.execSQL(DROP_CASE_TABLE);
        db.execSQL(DROP_ORDERS_TABLE);

        // Create tables again
        onCreate(db);

    }


    public void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_NAME, user.getName());
        values.put(COLUMN_USER_PASSWORD, user.getPassword());
        values.put(COLUMN_USER_EMAIL, user.getEmail());

        // Inserting Row
        db.insert(TABLE_USER, null, values);
        db.close();
    }

    public boolean isEmailExists(String Email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USER,// Selecting Table
                new String[]{COLUMN_USER_ID, COLUMN_USER_NAME, COLUMN_USER_PASSWORD, COLUMN_USER_EMAIL},//Selecting columns want to query
                COLUMN_USER_EMAIL + "=?",
                new String[]{Email},//Where clause
                null, null, null);

        if (cursor != null && cursor.moveToFirst()&& cursor.getCount()>0) {
            //if cursor has value then in user database there is user associated with this given email so return true
            db.close();
            return true;
        }

        //if email does not exist return false
        db.close();
        return false;
    }

    public User Authenticate(User user) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USER,// Selecting Table
                new String[]{COLUMN_USER_ID, COLUMN_USER_NAME, COLUMN_USER_PASSWORD, COLUMN_USER_EMAIL},//Selecting columns want to query
                COLUMN_USER_NAME +"=?",
                new String[]{user.getName()},//Where clause
                null, null, null);

        if (cursor != null && cursor.moveToFirst()&& cursor.getCount()>0) {

            //if cursor has value then in user database there is user associated with this given email
            User user1 = new User(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3));

            //Match both passwords check they are same or not
          if (user.getPassword().equalsIgnoreCase(user1.getPassword())) {
              cursor.close();
               return user1;
           }
        }

        //if user password does not matches or there is no record with that email then return @false
        cursor.close();
        return null;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_CPU_TABLE);
        db.execSQL(CREATE_MOTHERBOARD_TABLE);
        db.execSQL(CREATE_RAM_TABLE);
        db.execSQL(CREATE_SSD_TABLE);
        db.execSQL(CREATE_GPU_TABLE);
        db.execSQL(CREATE_CASE_TABLE);
        db.execSQL(CREATE_ORDERS_TABLE);


    }

    public void addProduct(String name, String price,String Table_name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_NAME, name);
        values.put(COLUMN_PRICE, price);

        // Inserting Row
        db.insert(Table_name, null, values);

        db.close();
    }

    public void addProcut(String name, String price,String Table_name){

    }

    public void changeStatus(String status, int id){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_STATUS, status);

        db.update(TABLE_ORDERS,values,COLUMN_ID_ORDER+"="+id, null );

        db.close();
    }


    public ArrayList<Order> myOrder() {

        ArrayList<Order> array = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor;
        Log.i("SQLite", "DataBaseHelper myOrder function");


        if(MainActivity.current_user.getName().equals("admin")){
             cursor = db.rawQuery("select * from " + TABLE_ORDERS, null);
        }
        //Cursor cursor = db.query(table_Name, new String[]{COLUMN_NAME, COLUMN_PRICE}, COLUMN_ID, null, null, null, null);
       else {
             cursor = db.rawQuery("select * from " + TABLE_ORDERS + " WHERE " + COLUMN_USER_ORDER + " = " + "'" + MainActivity.current_user.getName() + "'", null);
        }
        String name, price, status;

        if (cursor.moveToFirst()) {

            name = cursor.getString(cursor.getColumnIndex(COLUMN_USER_ORDER));
            price = cursor.getString(cursor.getColumnIndex(COLUMN_TOTAL_PRICE));
            status = cursor.getString(cursor.getColumnIndex(COLUMN_STATUS));
            array.add(new Order(name,price,status));

            while (cursor.moveToNext()) {

                name = cursor.getString(cursor.getColumnIndex(COLUMN_USER_ORDER));
                price = cursor.getString(cursor.getColumnIndex(COLUMN_TOTAL_PRICE));
                status = cursor.getString(cursor.getColumnIndex(COLUMN_STATUS));

                array.add(new Order(name,price,status));

            }

        }
            cursor.close();
            db.close();
return array;
        }



    public void addOrder()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

       // values.put(COLUMN_ID_ORDER, "1");
        values.put(COLUMN_USER_ORDER, MainActivity.current_user.getName());
        values.put(COLUMN_TOTAL_PRICE, MainActivity.current_user.my_pc.totalprice);
        values.put(COLUMN_STATUS, "Order pending approval");

        // Inserting Row
        db.insert(TABLE_ORDERS, null, values);

        db.close();

    }

    public void Build_DataBase ()
    {

       Product component_arr[]= new Product[4];
        component_arr[0]=new CPU("Intel Core i7, 8700k, 3.7GHz", "1800 ₪");
        component_arr[1]=new CPU("Intel Core i5, 6500 4x, 3.2Ghz", "930 ₪");
        component_arr[2]=new CPU("Intel Core i3, 8100,8th genaration", "670 ₪");
        component_arr[3]=new CPU("AMD Ryzen 7, 1800x, 8 cores, 3.6GHz", "1120 ₪");

        for(int i=0; i<component_arr.length; i++)
        {
            addProduct(component_arr[i].getName(),component_arr[i].getPrice(),TABLE_CPU);
        }

        component_arr[0]=new MotherBoard("Gigabyte, Z97x, gaming", "750 ₪");
        component_arr[1]=new MotherBoard("Intel B360, Aorus_with rgb fusion and digital leds", "610 ₪");
        component_arr[2]=new MotherBoard("Intel H370, Ultra durable", "430 ₪");
        component_arr[3]=new MotherBoard("Gigabyte unveils, 990fx gaming", "330 ₪");

       for(int i=0; i<component_arr.length; i++)
       {
           addProduct(component_arr[i].getName(),component_arr[i].getPrice(),TABLE_MOTHERBOARD);
       }

        component_arr[0]=new RAM("Fury Hyperx,_DDR4, 8gb", "360 ₪");
        component_arr[1]=new RAM("G.SKILL, DDR4, 32GB, 4400MHz", "2320 ₪");
        component_arr[2]=new RAM("G.Skill, Announces, 32GB, DDR4-4266MHz Trident", "2110 ₪");
        component_arr[3]=new RAM("DDR4, RAM-4, 8GB-2133", "435 ₪");

        for(int i=0; i<component_arr.length; i++)
        {
            addProduct(component_arr[i].getName(),component_arr[i].getPrice(),TABLE_RAM);
        }

        component_arr[0]=new SSD("Samsung Evo 970, 1TB", "750 ₪");
        component_arr[1]=new SSD("SanDisk PLUS SDSSDA, 240GB", "220 ₪");
        component_arr[2]=new SSD("Kingston SSDNow V100 ,128GB", "110 ₪");
        component_arr[3]=new SSD("Corsair Force LE200, 120GB, Sata 3", "100 ₪");

        for(int i=0; i<component_arr.length; i++)
        {
            addProduct(component_arr[i].getName(),component_arr[i].getPrice(),TABLE_SSD);
        }

        component_arr[0]=new GPU("MSI R9, 390 GAMING, 8G Graphics Card", "1420 ₪");
        component_arr[1]=new GPU("GTX 1050, Ti 4GB", "830 ₪");
        component_arr[2]=new GPU("Gigabyte GTX 950, 2GB", "450 ₪");
        component_arr[3]=new GPU("Geforce_GTX 1070, Windforce_8GB", "2200 ₪");

        for(int i=0; i<component_arr.length; i++)
        {
            addProduct(component_arr[i].getName(),component_arr[i].getPrice(),TABLE_GPU);
        }

        component_arr[0]=new Case("Cougar, MX350, TG", "399 ₪");
        component_arr[1]=new Case("Game Max Eclipse, RGB Tempered, Glass Midi PC Gaming", "370 ₪");
        component_arr[2]=new Case("Cougar, MX310", "200 ₪");
        component_arr[3]=new Case("Corsair, SPEC-03, Red", "299 ₪");

        for(int i=0; i<component_arr.length; i++)
        {
            addProduct(component_arr[i].getName(),component_arr[i].getPrice(),TABLE_CASE);
        }

    }


    public ArrayList<String> fetch(String table_Name) {
        ArrayList<String> array = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Log.i("SQLite","DataBaseHelper fetch function");

        //Cursor cursor = db.query(table_Name, new String[]{COLUMN_NAME, COLUMN_PRICE}, COLUMN_ID, null, null, null, null);
        Cursor cursor = db.rawQuery("select * from "+table_Name,null);

        String name, price;


        if(cursor.moveToFirst()) {

             name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
            array.add(name);
             price = cursor.getString(cursor.getColumnIndex(COLUMN_PRICE));
            array.add(price);

            while (cursor.moveToNext()) {

                 name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
                array.add(name);
                 price = cursor.getString(cursor.getColumnIndex(COLUMN_PRICE));
                array.add(price);

            }
        }

        cursor.close();
        db.close();
        return array;
    }
}
