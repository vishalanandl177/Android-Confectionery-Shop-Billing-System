package com.anand.vishal.billingsystem;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;


public class AddCategory extends AppCompatActivity {
    SQLiteDatabase dbase;
    EditText getName, dis, deleteid, categoryId;
    Button addCategory, display, deleteB, searchName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);

        getName = (EditText) findViewById(R.id.cat2);
        addCategory = (Button) findViewById(R.id.addcat);
        display = (Button) findViewById(R.id.disp);
        categoryId = (EditText) findViewById(R.id.categoryid);
        dis = (EditText) findViewById(R.id.dis);
        deleteB = (Button) findViewById(R.id.dbutton);
        deleteid = (EditText) findViewById(R.id.deleteid);
        searchName = (Button) findViewById(R.id.searchB);
        try {
            dbase = this.openOrCreateDatabase("AddCategoryDataBase", MODE_PRIVATE, null);
            dbase.execSQL("CREATE TABLE IF NOT EXISTS category" + "(id integer primary key,name VARCHAR);");
            File database =
                    getApplicationContext().getDatabasePath("AddCategoryDataBase.db");
            Log.e("AddCategory", "DateBase Created");
            if (!database.exists()) {
                Toast.makeText(AddCategory.this, "Add Category!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(AddCategory.this, "DataBase Missing!", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Log.e("AddCategory", "Error Creating DataBase");

        }
        addCategory.setClickable(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_category, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void addCategorynew(View view) {
        try {
            String id = categoryId.getText().toString();
            String name = getName.getText().toString();
            dbase.execSQL("INSERT INTO category (id,name) VALUES (" + id + ",'" + name + "');");
            Toast.makeText(AddCategory.this, "Data Inserted", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(AddCategory.this, "Category Already Exist!", Toast.LENGTH_SHORT).show();
        }
    }

    public void displayCategory(View view) {
        int count = 0;
        Cursor cursor = dbase.rawQuery("SELECT * FROM category", null);
        int idColumn = cursor.getColumnIndex("id");
        int nameColumn = cursor.getColumnIndex("name");
        cursor.moveToFirst();
        String categorylist = "";
        if (cursor != null && (cursor.getCount() > 0)) {
            do {
                String id = cursor.getString(idColumn);
                String name = cursor.getString(nameColumn);
                categorylist = categorylist + id + " : " + name + "\n";
                count++;
            } while (cursor.moveToNext());
            dis.setText(categorylist);
            Toast.makeText(AddCategory.this, count + " Results Found", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(AddCategory.this, "No Result To Show", Toast.LENGTH_SHORT).show();
            dis.setText("No Result To Show");
        }
    }

    public void deletecategory(View view) {
        try {
            String id = deleteid.getText().toString();
            dbase.execSQL("DELETE FROM category WHERE id = " + id + ";");
            Toast.makeText(AddCategory.this, "Data Deleted", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(AddCategory.this, "No Category Found", Toast.LENGTH_SHORT).show();
        }
    }

    public void SearchCategory(View view) {
        String name1 = getName.getText().toString();
        int count = 0;
        Cursor cursor = dbase.rawQuery("SELECT * FROM category WHERE name LIKE '" + name1 + "%';", null);
        int idColumn = cursor.getColumnIndex("id");
        int nameColumn = cursor.getColumnIndex("name");
        cursor.moveToFirst();
        String categorylist = "";
        if (cursor != null && (cursor.getCount() > 0)) {
            do {
                String id = cursor.getString(idColumn);
                String name = cursor.getString(nameColumn);
                categorylist = categorylist + id + " : " + name + "\n";
                count++;
            } while (cursor.moveToNext());
            dis.setText(categorylist);
            Toast.makeText(AddCategory.this, count + " Results Found", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(AddCategory.this, "No Data Found", Toast.LENGTH_SHORT).show();
            dis.setText("No Data Found");
        }

    }


    public static class AddProduct extends AppCompatActivity {
        SQLiteDatabase dbase;
        EditText pname,price,stock,display,del,pid;
        Button addprod,displaybutton,delb,searchB;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_add_product);
            display = (EditText)findViewById(R.id.pedit);
            displaybutton = (Button)findViewById(R.id.show);
            price = (EditText)findViewById(R.id.price);
            pname = (EditText)findViewById(R.id.pname);
            pid = (EditText)findViewById(R.id.pid);
            stock = (EditText)findViewById(R.id.stock);
            addprod = (Button)findViewById(R.id.addprod);
            searchB = (Button)findViewById(R.id.se);
            del = (EditText)findViewById(R.id.del);
            delb = (Button)findViewById(R.id.delb);
            try{
                dbase = this.openOrCreateDatabase("AddProductDataBase",MODE_PRIVATE,null);
                dbase.execSQL("CREATE TABLE IF NOT EXISTS product" + "(id number primary key,name VARCHAR,price number,stock number);");
                File database =
                        getApplicationContext().getDatabasePath("AddProductDataBase.db");
                Log.e("AddProduct", "DateBase Created");
                if(!database.exists()){
                    Toast.makeText(AddProduct.this, "Add Product!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(AddProduct.this, "DataBase Missing!", Toast.LENGTH_SHORT).show();
                }
            }
            catch(Exception e)
            {
                Log.e("AddProduct","Error Creating DataBase");

            }
            addprod.setClickable(true);
        }


        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            // Inflate the menu; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.menu_add_product, menu);
            return true;
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            // Handle action bar item clicks here. The action bar will
            // automatically handle clicks on the Home/Up button, so long
            // as you specify a parent activity in AndroidManifest.xml.
            int id = item.getItemId();

            //noinspection SimplifiableIfStatement
            if (id == R.id.action_settings) {
                finish();
                return true;
            }

            return super.onOptionsItemSelected(item);
        }

        public void addProduct(View view) {
            try{
            String p_id = pid.getText().toString();
            String p_name = pname.getText().toString();
            String p_price = price.getText().toString();
            String p_stock = stock.getText().toString();
            dbase.execSQL("INSERT INTO product (id,name,price,stock) VALUES (" + p_id + ",'" + p_name + "'," + p_price + "," + p_stock + ");");
            Toast.makeText(AddProduct.this, "Data Inserted", Toast.LENGTH_SHORT).show();

        }catch (Exception e){
                Toast.makeText(AddProduct.this, "Product Already Exist", Toast.LENGTH_SHORT).show();
            }
        }

        public void displayProducts(View view) {
            int count=0;
            Cursor cursor = dbase.rawQuery("SELECT * FROM product",null);
            int idColumn = cursor.getColumnIndex("id");
            int nameColumn = cursor.getColumnIndex("name");
            int priceColumn = cursor.getColumnIndex("price");
            int stockColumn = cursor.getColumnIndex("stock");
            cursor.moveToFirst();
            String productlist= "";
            if(cursor!=null && (cursor.getCount()>0)){
                do {
                    String id = cursor.getString(idColumn);
                    String name = cursor.getString(nameColumn);
                    String price = cursor.getString(priceColumn);
                    String stock = cursor.getString(stockColumn);
                    productlist = productlist + id + " : " + name + " : " + price + "Rs : " + stock +"\n";
                    count++;
                }while(cursor.moveToNext());
                display.setText(productlist);
                Toast.makeText(AddProduct.this, count +" Results Found", Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(AddProduct.this, "No Result To Show", Toast.LENGTH_SHORT).show();
                display.setText("No Result To Show");
            }
        }

        public void deleteProduct(View view) {

            try {
                String id = del.getText().toString();
                dbase.execSQL("DELETE FROM product WHERE id = " + id + ";");
                Toast.makeText(AddProduct.this, "Data Deleted", Toast.LENGTH_SHORT).show();
            } catch (Exception e){
                Toast.makeText(AddProduct.this, "No Product Found", Toast.LENGTH_SHORT).show();
            }
        }
        public void SearchProduct(View view) {
            int count = 0;
            String name1 = pname.getText().toString();
            Cursor cursor = dbase.rawQuery("SELECT * FROM product WHERE name LIKE '" + name1 + "%';", null);
            int idColumn = cursor.getColumnIndex("id");
            int nameColumn = cursor.getColumnIndex("name");
            int priceColumn = cursor.getColumnIndex("price");
            int stockColumn = cursor.getColumnIndex("stock");
            cursor.moveToFirst();
            String productlist= "";
            if(cursor!=null && (cursor.getCount()>0)){
                do {
                    String id = cursor.getString(idColumn);
                    String name = cursor.getString(nameColumn);
                    String price = cursor.getString(priceColumn);
                    String stock = cursor.getString(stockColumn);
                    productlist = productlist + id + " : " + name + " : " + price + "Rs : " + stock + "\n";
                    count++;
                }while(cursor.moveToNext());
                display.setText(productlist);
                Toast.makeText(AddProduct.this, count +" Results Found", Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(AddProduct.this, "No Data Found", Toast.LENGTH_SHORT).show();
                display.setText("No Data Found");
            }
        }
    }
}
