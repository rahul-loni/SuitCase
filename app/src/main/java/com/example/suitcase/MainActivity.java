package com.example.suitcase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.Switch;
import android.widget.Toast;

import com.example.suitcase.Adapter.RecyclerItemsClickView;
import com.example.suitcase.databinding.ActivityMainBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    FloatingActionButton fab;
    private DatabaseHelper databaseHelper;
    private RecyclerItemsClickView recyclerItemsClickView;
    private Adapter adapter;
    private NavigationView navigationView;
    private ArrayList<ItemsModel> itemsModels;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id=item.getItemId();
                if (id==R.id.item_home){
                    Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                    Toast.makeText(MainActivity.this, "Click to home ", Toast.LENGTH_SHORT).show();
                }
                if (id==R.id.item_about){
                    Toast.makeText(MainActivity.this, "Clickto about ", Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });
        final DrawerLayout drawerLayout=findViewById(R.id.drawer);
        findViewById(R.id.nav_menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });


        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),Add_Items.class);
                startActivity(intent);
            }
        });
    }
}