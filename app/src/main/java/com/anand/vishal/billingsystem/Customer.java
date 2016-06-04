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

public class Customer extends AppCompatActivity {
    SQLiteDatabase dbase;
    EditText cName,cContact,cAddress,display,del,id;
    Button addCusomer,show,delb,searchB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);
        display = (EditText)findViewById(R.id.display);
        show = (Button)findViewById(R.id.show);
        cName = (EditText)findViewById(R.id.c_id);
        id = (EditText)findViewById(R.id.id);
        searchB = (Button)findViewById(R.id.searchButton);
        del = (EditText)findViewById(R.id.del);
        delb = (Button)findViewById(R.id.delb);
        cContact = (EditText)findViewById(R.id.c_contact);
        cAddress = (EditText)findViewById(R.id.c_address);
        addCusomer = (Button)findViewById(R.id.addCustomerButton);
        try{
            dbase = this.openOrCreateDatabase("AddCustomerDataBase",MODE_PRIVATE,null);
            dbase.execSQL("CREATE TABLE IF NOT EXISTS customer" + "(id integer primary key,name VARCHAR,contact VARCHAR,address VARCHAR);");
            File database =
                    getApplicationContext().getDatabasePath("AddCustomerDataBase.db");
            Log.e("AddCategory", "DateBase Created");
            if(!database.exists()){
                Toast.makeText(Customer.this, "Add Customer!", Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(Customer.this, "DataBase Missing!", Toast.LENGTH_SHORT).show();
            }
        }
        catch(Exception e)
        {
            Log.e("AddCategory","Error Creating DataBase");

        }
        addCusomer.setClickable(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_customer, menu);
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

    public void addCustomer(View view) {

        try {
            String cID = id.getText().toString();
            String name = cName.getText().toString();
            String contact = cContact.getText().toString();
            String address = cAddress.getText().toString();
            dbase.execSQL("INSERT INTO customer (id,name,contact,address) VALUES (" + cID + ",'" + name + "'," + contact + ",'" + address + "');");
            Toast.makeText(Customer.this, "Data Inserted", Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            Toast.makeText(Customer.this, "Customer Already Exist", Toast.LENGTH_SHORT).show();
        }
    }

    public void ShowCustomers(View view) {

        Cursor cursor = dbase.rawQuery("SELECT * FROM customer",null);
        int idColumn = cursor.getColumnIndex("id");
        int nameColumn = cursor.getColumnIndex("name");
        int count = 0;
        int contactColumn = cursor.getColumnIndex("contact");
        int addressColumn = cursor.getColumnIndex("address");
        cursor.moveToFirst();
        String customerlist= "";
        if(cursor!=null && (cursor.getCount()>0)){
            do {
                String id = cursor.getString(idColumn);
                String name = cursor.getString(nameColumn);
                String contact = cursor.getString(contactColumn);
                String address = cursor.getString(addressColumn);
                customerlist = customerlist + id + " : " + name + " : " + contact + " : " + address +"\n";
                count++;
            }while(cursor.moveToNext());
            display.setText(customerlist);
            Toast.makeText(Customer.this, count +" Results Found", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(Customer.this, "No Result To Show!", Toast.LENGTH_SHORT).show();
            display.setText("No Result To Show");
        }
    }

    public void deleteCustomer(View view) {
        try {
            String id = del.getText().toString();
            dbase.execSQL("DELETE FROM customer WHERE id = " + id + ";");
            Toast.makeText(Customer.this, "Data Deleted", Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            Toast.makeText(Customer.this, "No Customer Found!", Toast.LENGTH_SHORT).show();
        }
    }

    public void searchCustomer(View view) {
        String name1 = cName.getText().toString();
        int count=0;
        Cursor cursor = dbase.rawQuery("SELECT * FROM customer WHERE name LIKE '" + name1 + "%';", null);
        int idColumn = cursor.getColumnIndex("id");
        int nameColumn = cursor.getColumnIndex("name");
        int priceColumn = cursor.getColumnIndex("contact");
        int stockColumn = cursor.getColumnIndex("address");
        cursor.moveToFirst();
        String customerList= "";
        if(cursor!=null && (cursor.getCount()>0)){
            do {
                String id = cursor.getString(idColumn);
                String name = cursor.getString(nameColumn);
                String contact = cursor.getString(priceColumn);
                String address = cursor.getString(stockColumn);
                customerList = customerList + id + " : " + name + " : " + contact + " : " + address + "\n";
                count++;
            }while(cursor.moveToNext());
            display.setText(customerList);
            Toast.makeText(Customer.this, count +" Results Found", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(Customer.this, "No Data Found", Toast.LENGTH_SHORT).show();
            display.setText("No Data Found");
        }
    }

}
