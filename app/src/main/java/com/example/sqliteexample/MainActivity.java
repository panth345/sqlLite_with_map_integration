package com.example.sqliteexample;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText editText_name,editText_email,editText_address;
    Button button_add,button_view,button_map;
    TextView textlatitude,textlongitude;
    Double lat, lon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText_name = findViewById(R.id.edittext_name);
        editText_email = findViewById(R.id.edittext_email);
        editText_address = findViewById(R.id.edittext_address);
        textlongitude = findViewById(R.id.textlongitude);
        textlatitude= findViewById(R.id.textlatitude);
        button_add = findViewById(R.id.button_add);
        button_view = findViewById(R.id.button_view);
        button_map = findViewById(R.id.button_map);


        button_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String stringName = editText_name.getText().toString();
                String stringEmail = editText_email.getText().toString();
                String stringAddress = editText_address.getText().toString();

                Geocoder coder = new Geocoder(getApplicationContext());
                List<Address> geocodeResults = null;

                try {
                    geocodeResults = coder.getFromLocationName(stringAddress, 10);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Iterator<Address> locations = geocodeResults.iterator();
                while (locations.hasNext()) {
                    Address loc = locations.next();
                    lat = loc.getLatitude();
                    lon = loc.getLongitude();

                }
                String  result1 =String.valueOf(lat);
                String result = String.valueOf(lon);
              //  textlatitude.setText(result1);
               // textlongitude.setText(result);
                Double stringlatitude = Double.parseDouble(result1);
                Double stringlongitude = Double.parseDouble(result);


                if (stringName.length() <=0 || stringEmail.length() <=0 || stringAddress.length() <=0){
                    Toast.makeText(MainActivity.this, "Enter All Data", Toast.LENGTH_SHORT).show();
                }else {
                    DatabaseHelperClass databaseHelperClass = new DatabaseHelperClass(MainActivity.this);
                    EmployeeModelClass employeeModelClass = new EmployeeModelClass(stringName,stringEmail,stringAddress,stringlatitude,stringlongitude);
                    databaseHelperClass.addEmployee(employeeModelClass);

                    Toast.makeText(MainActivity.this, "Add Employee Successfully", Toast.LENGTH_SHORT).show();
                    finish();
                    startActivity(getIntent());
                }
            }
        });


        button_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,ViewEmployeeActivity.class);
                startActivity(intent);
            }
        });

        button_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,MapsActivity.class);
                startActivity(intent);
            }
        });

    }
}
