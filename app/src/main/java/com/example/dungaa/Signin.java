package com.example.dungaa;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Signin extends AppCompatActivity {
    TextView accounttxt;
    TextInputEditText useremail,userpassword;
    Button signInBtn;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        accounttxt=findViewById(R.id.accountxt);
        useremail=findViewById(R.id.emailsignin);
        userpassword=findViewById(R.id.passwordsignin);
        signInBtn=findViewById(R.id.signInBtn);
        mAuth=FirebaseAuth.getInstance();
        Typeface myTypeFace=Typeface.createFromAsset(getAssets(),"fonts/chapline.otf");
        accounttxt.setTypeface(myTypeFace);

        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                userInput();

            }
        });
    }
    public void onSignupAccount(View view){
        Intent signUp= new Intent(Signin.this,SignUp.class);
        startActivity(signUp);
        finish();
    }

    public void userInput(){
        String myemail=useremail.getText().toString().trim();
        String mypass=userpassword.getText().toString().trim();
       if (checkEmail(myemail)&& checkPass(mypass)){
           final ProgressDialog progressDialog= new ProgressDialog(Signin.this);
           progressDialog.setMessage("Login...");
           progressDialog.show();
           String email,pass;
           email=useremail.getText().toString().trim();
           pass=userpassword.getText().toString().trim();

           mAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
               @Override
               public void onComplete(@NonNull Task<AuthResult> task) {
                   progressDialog.dismiss();
                   if (task.isSuccessful()){

                       Intent foodmenu=new Intent(Signin.this,FoodMenu.class);
                       foodmenu.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                       foodmenu.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                       finish();
                       startActivity(foodmenu);
                       Toast.makeText(Signin.this, "Login Successfully!", Toast.LENGTH_LONG).show();
                   }

                   else{

                       Toast.makeText(Signin.this, task.getException().getMessage().trim(), Toast.LENGTH_SHORT).show();
                   }
               }
           });



       }


    }

    private boolean checkEmail(String myemail){
        if (TextUtils.isEmpty(myemail)){
            useremail.setError("Fill Email!");
            return false;
        }
        return true;
    }
    private boolean checkPass(String mypass){
        if (TextUtils.isEmpty(mypass)){
            userpassword.setError("Fill Password!");
            return false;
        }
        return true;
    }
}
