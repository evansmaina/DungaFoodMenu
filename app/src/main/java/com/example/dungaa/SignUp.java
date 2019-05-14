package com.example.dungaa;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.MenuPopupHelper;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import de.hdodenhof.circleimageview.CircleImageView;

public class SignUp extends AppCompatActivity {
    TextView accountlogtxt;
    CircleImageView profilepic;
    public static final int REQUEST_IMAGE=100;
    TextInputEditText useremail,username,userpassword;
    Button signUpBtn;
    FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        accountlogtxt=findViewById(R.id.accountlogtxt);
        profilepic=findViewById(R.id.profilepic);
        signUpBtn=findViewById(R.id.signUpbutton);
        username=findViewById(R.id.username);
        useremail=findViewById(R.id.emailsignup);
        userpassword=findViewById(R.id.passwordsignup);
        mAuth=FirebaseAuth.getInstance();

        Typeface myTypeFace=Typeface.createFromAsset(getAssets(),"fonts/chapline.otf");
        accountlogtxt.setTypeface(myTypeFace);

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userDetails();
            }
        });



    }

    public void onSignInAccount(View view){
        Intent signIn= new Intent(SignUp.this,Signin.class);
        startActivity(signIn);
        finish();
    }
    public void profileOptions(View view){
        PopupMenu popupMenu= new PopupMenu(SignUp.this,profilepic);
        popupMenu.getMenuInflater().inflate(R.menu.profilepic_options,popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.gallery:
                        openGallery();
                        break;
                    case R.id.camera:
                        break;
                }

                return true;
            }
        });
        popupMenu.show();

    }

    public void openGallery(){
        Intent gallery=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        gallery.setType("image/*");
        startActivityForResult(gallery,12);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==12 && resultCode==RESULT_OK && data!=null && data.getData()!=null){
            Uri imgUri=data.getData();
            profilepic.setImageURI(imgUri);
        }

        if (requestCode==REQUEST_IMAGE && requestCode==RESULT_OK){
            if (data !=null && data.getExtras() !=null){
                Bitmap bitmap=(Bitmap) data.getExtras().get("data");
                profilepic.setImageBitmap(bitmap);
            }

        }
    }

    private void openCamera(){
        Intent camera= new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
       if (camera.resolveActivity(getPackageManager()) !=null){
            startActivityForResult(camera,REQUEST_IMAGE);

        }
    }

    public void userDetails(){
        String myemail=useremail.getText().toString().trim();
        String mypass= userpassword.getText().toString().trim();
        String myusername=username.getText().toString().trim();

        if (checkUsername(myusername)&& checkEmail(myemail)&&checkPass(mypass)){
            final ProgressDialog progressDialog=new ProgressDialog(SignUp.this);
            progressDialog.setMessage("Creating account...");
            progressDialog.show();

            String email=useremail.getText().toString().trim();
            String pass=userpassword.getText().toString().trim();

            mAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    progressDialog.dismiss();
                    if (task.isSuccessful()){
                        Intent foodmenu=new Intent(SignUp.this,FoodMenu.class);
                        foodmenu.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        foodmenu.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        finish();
                        startActivity(foodmenu);
                        Toast.makeText(SignUp.this, "account created successfully!", Toast.LENGTH_LONG).show();

                    }
                    else {

                        Toast.makeText(SignUp.this, task.getException().getMessage().trim(), Toast.LENGTH_SHORT).show();
                    }
                }
            });



        }

    }

    private boolean checkUsername(String musername){

        if (TextUtils.isEmpty(musername)){
            username.setError("Fill username!");
            return false;
        }
        return true;
    }
    private boolean checkEmail(String memail){

        if (TextUtils.isEmpty(memail)){
            useremail.setError("Fill email!");
            return false;
        }
        return true;
    }
    private boolean checkPass(String mpass){

        if (TextUtils.isEmpty(mpass)){
           userpassword.setError("Fill Password!");
            return false;
        }
        return true;
    }


}

