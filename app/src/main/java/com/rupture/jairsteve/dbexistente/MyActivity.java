package com.rupture.jairsteve.dbexistente;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.sql.SQLException;


public class MyActivity extends Activity {

    public DBHelper dbHelper;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        dbHelper = new DBHelper(this);
        try {
            dbHelper.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        cursor = dbHelper.readVendor();

        showVendor(cursor);

        /*String databases = getApplicationContext().getDatabasePath("scan.db").getAbsolutePath();
        TextView textView = (TextView) findViewById(R.id.textView_showVendor);
        textView.setText(databases);*/
    }

    @Override
    protected void onPause() {
        super.onPause();
        dbHelper.close();
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            dbHelper.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void showVendor(Cursor cursor){

        StringBuilder builder = new StringBuilder("Table vendor results : \n");
        while (cursor.moveToNext()){

            int _id = cursor.getInt(0);
            String id_vendor = cursor.getString(1);
            String vendor_name = cursor.getString(2);

            builder.append(_id).append(": ");
            builder.append(id_vendor).append("\n");
            builder.append(vendor_name).append("\n");
            builder.append("---------------------------------------").append("\n");


        }
        TextView textView = (TextView) findViewById(R.id.textView_showVendor);
        textView.setText(builder);

    }
}
