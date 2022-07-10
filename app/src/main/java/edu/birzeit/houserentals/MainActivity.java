package edu.birzeit.houserentals;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

//import com.example.a1180445_1180060_advancedpropertydealer.ui.reservations.ReservationsFragment;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button connect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DataBaseHelper db = new DataBaseHelper(MainActivity.this,"HomeRent", null,1);
        Cursor max = db.getMaxIDReservations();
        if(max != null && max.getCount() > 0) {
            max.moveToNext();
            DataBaseHelper.reservationID = max.getInt(0) + 1;
        }

        connect = findViewById(R.id.connect);
        connect.setOnClickListener(v -> {
            ConnectionAsyncTask connectionAsyncTask = new ConnectionAsyncTask(MainActivity.this);
            connectionAsyncTask.execute("https://www.mocky.io/v3/a6a07679-aac4-4e16-a35c-a2e26c7bdeb0");
        });

        MainActivity.this.setTitle("House Rent");
    }

    public void setButtonText(String text) {
        connect.setText(text);
    }

    public void showNullMessage(){
        Toast toast = Toast.makeText(this, "No Received Data", Toast.LENGTH_LONG);
        toast.show();
    }

    public void moveToSignInPage(){
        Intent intent = new Intent(MainActivity.this, SignIn.class);
        MainActivity.this.startActivity(intent);
        finish();
    }


    public void addPropertiesToDataBase(List<Property> properties){
        DataBaseHelper db = new DataBaseHelper(MainActivity.this, "HomeRent", null, 1);
        for(Property property :properties){
            db.insertProperty(property);
        }
    }
}