package com.example.shubhm.supplyit_customer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class Location extends Activity {
    Spinner s,s2;
    Context context;
    static String city="delhi";
    static String location="";
    Button setlocation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        context=this;

        setlocation=(Button) findViewById(R.id.setlocation) ;
        String[] citySpinner = new String[] {"Delhi"};
        String[] arraySpinner = new String[] {
                "Govindpuri", "Harkesh Nagar", "Shyam Nagar", "Nehru Place", "Kalkaji"};
        s = (Spinner) findViewById(R.id.spinner);
        s2 = (Spinner) findViewById(R.id.spinner2);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, citySpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, arraySpinner);

        s.setAdapter(adapter);
        s2.setAdapter(adapter1);
       int i=0;

            s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                   location = s.getSelectedItem().toString();
                    Toast.makeText(context,location,Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });


        s2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                city = s2.getSelectedItem().toString();
                Toast.makeText(context,city,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        setlocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent i=new Intent(context,HomeAtivity.class);
                //startActivity(i);
            }
        });

    }



}
