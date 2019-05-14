package com.example.dungaa;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FoodMenu extends AppCompatActivity

        implements NavigationView.OnNavigationItemSelectedListener {

    FirebaseAuth mAuth;
    FirebaseUser mUser;
    DatabaseReference reference;
    RecyclerView recyclerView;
    ArrayList<Category> list;
    FoodMenuAdapter adapt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_menu);
        mAuth=FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();
        recyclerView=findViewById(R.id.recycler_menu);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list=new ArrayList<Category>();


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Your food orders will appear here!", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        reference= FirebaseDatabase.getInstance().getReference().child("Categories");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){

                    Category c= dataSnapshot1.getValue(Category.class);
                    list.add(c);
                }
                adapt= new FoodMenuAdapter(FoodMenu.this,list);
                recyclerView.setAdapter(adapt);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(FoodMenu.this, databaseError.getMessage().trim(), Toast.LENGTH_SHORT).show();

            }
        });




        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_menu) {
            // Handle the camera action
        } else if (id == R.id.nav_cart) {

        } else if (id == R.id.nav_orders) {

        } else if (id == R.id.nav_exit) {
            signOut();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void signOut(){
        popUpSignOut();
    }

    private void popUpSignOut(){
        final AlertDialog.Builder popup= new AlertDialog.Builder(this);
        popup.setCancelable(true);
        popup.setTitle("Sign Out");
        popup.setMessage("Are sure you want to Sign Out");
        popup.setIcon(R.drawable.loginpic);
        popup.setPositiveButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        popup.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
               mAuth.signOut();

                Intent startup= new Intent(FoodMenu.this,MainActivity.class);
                startup.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startup.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                finish();
                startActivity(startup);


            }
        });
        popup.create().show();
    }
}
