package com.example.dungaa;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    TextView txtVw;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtVw=findViewById(R.id.txtVw);
        mAuth=FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();
        Typeface myTypeFace=Typeface.createFromAsset(getAssets(),"fonts/chapline.otf");
        txtVw.setTypeface(myTypeFace);

        if (mUser!=null){
            Intent foodMenu= new Intent(MainActivity.this,FoodMenu.class);
            startActivity(foodMenu);
            finish();
        }
    }

    public void onSignIn(View view){
        Intent i= new Intent(MainActivity.this,Signin.class);
        startActivity(i);
    }
    public void onSignUp(View view){
        Intent intent= new Intent(MainActivity.this,SignUp.class);
        startActivity(intent);
    }

}
