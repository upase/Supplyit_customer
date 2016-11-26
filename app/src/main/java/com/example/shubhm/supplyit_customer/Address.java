package com.example.shubhm.supplyit_customer;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Arrays;
import java.util.List;

public class Address extends Activity {
    EditText citytext,areatext,flattext,localitytext,postaltext,landmarktext;
    Button setAddress;
    String city,area,flat,locality,postal,landmark;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        citytext=(EditText) findViewById(R.id.mycitytext);
        areatext=(EditText) findViewById(R.id.myareatext);
        flattext=(EditText) findViewById(R.id.myhouseno);
        localitytext=(EditText) findViewById(R.id.mylocality);
        postaltext=(EditText) findViewById(R.id.mypincode);
        landmarktext=(EditText) findViewById(R.id.mylandmark);
        setAddress=(Button) findViewById(R.id.setaddress);

        checkalready();

        setAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                city=citytext.getText().toString();
                area=areatext.getText().toString();
                locality=localitytext.getText().toString();
                flat=flattext.getText().toString();
                postal=postaltext.getText().toString();
                landmark=landmarktext.getText().toString();

                if(TextUtils.isEmpty(city))
                {

                    citytext.setError("This Field can't be Empty");
                    return;
                }
                if(TextUtils.isEmpty(area))
                {

                    areatext.setError("This Field can't be Empty");
                    return;
                }
                if(TextUtils.isEmpty(flat))
                {

                    flattext.setError("This Field can't be Empty");
                    return;
                }
                if(TextUtils.isEmpty(locality))
                {

                    localitytext.setError("This Field can't be Empty");
                    return;
                }

                String address=city+"@"+area+"@"+flat+"@"+locality+"@"+postal+"@"+landmark;
                //save to database
            }
        });

    }

    private void checkalready() {

        String address="bashbsh@nansjn@bxhbhba@hxhbhnh@hahsbhb@jxnjanj";
        boolean exists=false;
        //FETCH Address FROM database
        // if address set exists=true or set exists=false

        citytext.setText(Location.city);
        areatext.setText(Location.location);
        if(exists)
        {
            String [] items = address.split("@");
            flattext.setText(items[2]);
            localitytext.setText(items[3]);
            postaltext.setText(items[4]);
            landmarktext.setText(items[5]);
        }

    }
}
