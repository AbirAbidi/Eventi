package com.example.eventi_20;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eventi_20.ui.DatabaseHelperPost;
import com.example.eventi_20.ui.home.HomeFragment;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eventi_20.databinding.ActivityHomePageBinding;

import java.util.ArrayList;

public class Home_page extends AppCompatActivity {
    DatabaseHelperPost myBD ;
    RecyclerView recyclerView;
    CustomAdapter customAdapter ;
    ArrayList<String> event_name;
    ArrayList<String> event_place;
    ArrayList<String> event_date;
    ArrayList<byte[]> image ;
    private AppBarConfiguration mAppBarConfiguration;
    public ActivityHomePageBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
setContentView(R.layout.fragment_home);


        binding = ActivityHomePageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.appBarHomePage.toolbar);
        Button fab = findViewById(R.id.add_event_btn);


        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
       // NavigationView navigationView = findViewById(R.id.nav_view);

        // end abir here

        View headerView = navigationView.getHeaderView(0);
        TextView emailTextView = headerView.findViewById(R.id.email_nav_bar);
        TextView usernameTextView = headerView.findViewById(R.id.name_nav_bar);
        String username = getIntent().getStringExtra("username");
        String email = getIntent().getStringExtra("email");
        if (usernameTextView != null && emailTextView !=null) {
            emailTextView.setText(email);
            usernameTextView.setText(username);
            // The TextView was found in the layout
        } else {
            // The TextView was not found in the layout
            System.out.println("error");
        }
        //  */
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_home_page);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);




        Button button = findViewById(R.id.add_event_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Home_page.this,Container.class);
                startActivity(i);

            }
        });


        // abir work here

        myBD = new DatabaseHelperPost(Home_page.this);
        event_name = new ArrayList<>();
        event_place = new ArrayList<>();
        event_date = new ArrayList<>();
        image = new ArrayList<byte[]>();
        display_all();
        recyclerView = findViewById(R.id.list);
        customAdapter = new CustomAdapter(Home_page.this,event_name,event_date,event_place,image);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(Home_page.this));






    }



    void display_all(){
        Cursor cursor = myBD.readAllData();
        if (cursor.getCount() == 0){
            Toast.makeText(Home_page.this, "No data", Toast.LENGTH_SHORT).show();
        }else {
            while (cursor.moveToNext()){
                event_name.add(cursor.getString(1));
                event_place.add(cursor.getString(2));
                event_date.add(cursor.getString(3));
                image.add(cursor.getBlob(4));
            }
        }
    }





}