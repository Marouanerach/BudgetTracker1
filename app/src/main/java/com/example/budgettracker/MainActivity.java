package com.example.budgettracker;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    EditText username, password, repassword;
    Button signup, signin;
    private Button btnLogin,btnSignup;
    DBHelper DB;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = (EditText) findViewById(R.id.username1);
        password = (EditText) findViewById(R.id.password1);
        repassword = (EditText) findViewById(R.id.repassword);
        signup = (Button) findViewById(R.id.btnsignin1);
        btnLogin=(Button)findViewById(R.id.btnLogin);
        btnSignup=(Button)findViewById(R.id.btnSignup);
        DB = new DBHelper(this);

        signup.setOnClickListener(new View.OnClickListener(){
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {

                Log.d(TAG, "Start registration ");
                String user = username.getText().toString();
                String pass = password.getText().toString();
                String repass = repassword.getText().toString();

                //Checking if all the fields are not empty before inserting into the database
                if(user.equals("")||pass.equals("")||repass.equals(""))
                    Toast.makeText(MainActivity.this,"please enter all the fields",Toast.LENGTH_SHORT).show();
                else{
                    if(pass.equals(repass)){
                        //Here we check if the username is unique(there is no user with the same username)
                        Boolean checkuser = DB.checkusername(user);
                        if(checkuser==false){

                            Boolean insert = DB.insertData(user, pass); // we check if the data was insert in database
                            if(insert==true){
                                Log.d(TAG, "Registered successfully");
                                Toast.makeText(MainActivity.this, "Registered successfully", Toast.LENGTH_SHORT).show();  // if yes
                                Intent intent  = new Intent(getApplicationContext(),LoginActivity.class);
                                startActivity(intent);
                            }else{
                                Log.d(TAG, "Registered failed");
                                Toast.makeText(MainActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show(); // if not
                            }
                        }
                        else{
                            Toast.makeText(MainActivity.this, "User already exists! Please sign in", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(MainActivity.this, "Passwords not matching", Toast.LENGTH_SHORT).show();
                    }
                }
                //Job scheduler to notify the user if the balance is < 0

            }

        });


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });


    }




}