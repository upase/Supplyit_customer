package com.example.shubhm.supplyit_customer;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Signup extends Activity {
    EditText nametext,emailtext,phonetext,passwordtext,reenterpasswordtext;
    Button signup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        nametext=(EditText) findViewById(R.id.nametext);
        emailtext=(EditText) findViewById(R.id.emailtext);
        phonetext=(EditText) findViewById(R.id.phonetext);
        passwordtext=(EditText) findViewById(R.id.passwordtext);
        reenterpasswordtext=(EditText) findViewById(R.id.confirmpasswordtext);
        signup=(Button) findViewById(R.id.signup);


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name=nametext.getText().toString();
                String email=emailtext.getText().toString();
                String phone=phonetext.getText().toString();
                String password=passwordtext.getText().toString();
                String confirmpassword=reenterpasswordtext.getText().toString();

                //Toast.makeText(getApplicationContext(),name+" "+email+" "+phone+" "+password+" "+confirmpassword+" ",Toast.LENGTH_SHORT).show();
                if(TextUtils.isEmpty(name))
                {

                    nametext.setError("This Field can't be Empty");
                    return;
                }
                if(TextUtils.isEmpty(email))
                {

                    emailtext.setError("This Field can't be Empty");
                    return;
                }

                if(TextUtils.isEmpty(phone))
                {

                    phonetext.setError("This Field can't be Empty");
                    return;
                }

                if(TextUtils.isEmpty(password))
                {

                    passwordtext.setError("This Field can't be Empty");
                    return;
                }
                if(TextUtils.isEmpty(confirmpassword))
                {

                    reenterpasswordtext.setError("This Field can't be Empty");
                    return;
                }

                if(!email.contains("@")||!email.contains(".")||(email.indexOf('@')>email.indexOf('.'))||!(email.indexOf('@')>0)||!(email.indexOf('.')<email.length()-1)||(email.indexOf('@')+1==email.indexOf('.')))
                {
                    int i=0;emailtext.setError("Invalid Email");
                    return;//

                }

                if(phone.length()<10 || phone.toCharArray()[0]=='0' || Long.parseLong(phone)<0 || Long.parseLong(phone)>9999999999.0)
                {

                    phonetext.setError("Invalid Number");
                    return;
                }

                if(password.length()<6)
                {

                    passwordtext.setError("Password length should be atleast 6");
                    return;
                }
                if(confirmpassword.length()<6)
                {

                    reenterpasswordtext.setError("Password length should be atleast 6");
                    return;
                }

                if(!confirmpassword.equals(password))
                {

                    reenterpasswordtext.setError("Passwords must match");
                    return;
                }

                //save in database
            }
        });


    }
}
