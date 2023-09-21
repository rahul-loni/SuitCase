package com.example.suitcase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.suitcase.Adapter.ItemsAdapter;
import com.example.suitcase.Adapter.RecyclerItemsClickView;
import com.example.suitcase.databinding.ActivityMainBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    FloatingActionButton fab;
    private Items_DBHelper items_dbHelper;
    private RecyclerItemsClickView recyclerItemsClickView;
    private ItemsAdapter itemsAdapter;
    private NavigationView navigationView;
    private ArrayList<ItemsModel> itemsModels;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //Nav Menu Item Click
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

        //initialize data
        itemsModels=new ArrayList<>();
        items_dbHelper=new Items_DBHelper(this);
        setRecyclerView();
        setupItemTouchHelper();
        binding.fab.setOnClickListener(view->startActivity(Add_Items.getIntent(getApplicationContext())));
    }
    private void setupItemTouchHelper(){
        ItemTouchHelper itemTouchHelper=new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                        return false;
                    }
                    @Override
                    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                        int position=viewHolder.getAdapterPosition();
                        ItemsModel itemsModel=itemsModels.get(position);
                        if (direction==ItemTouchHelper.LEFT){
                            items_dbHelper.delete(itemsModel.getId());
                            itemsModels.remove(position);
                            itemsAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
                            Toast.makeText(MainActivity.this, "Item Deleted", Toast.LENGTH_SHORT).show();
                        }else if(direction==ItemTouchHelper.RIGHT){
                            itemsModel.setPurchased(true);
                            items_dbHelper.update(
                                    itemsModel.getId(),
                                    itemsModel.getName(),
                                    itemsModel.getPrice(),
                                    itemsModel.getDescription(),
                                    itemsModel.getImage().toString(),
                                    itemsModel.isPurchased()
                            );
                            itemsAdapter.notifyItemChanged(position);
                            Toast.makeText(MainActivity.this, "Item is Updated ", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
        itemTouchHelper.attachToRecyclerView(binding.recycler);
    }

    @Override
    protected void onStart() {
        super.onStart();
        retrieveData();
    }
    private void retrieveData(){
        Cursor cursor=items_dbHelper.getAll();
        if (cursor==null){
            return;
        }
        itemsModels.clear();
        while (cursor.moveToNext()){
            ItemsModel itemsModel=new ItemsModel();
            itemsModel.setId(cursor.getInt(0));
            itemsModel.setName(cursor.getString(1));
            itemsModel.setPrice(cursor.getDouble(2));
            itemsModel.setDescription(cursor.getString(3));
            itemsModel.setImage(Uri.parse(cursor.getString(4)));

            itemsModels.add(cursor.getPosition(),itemsModel);
            itemsAdapter.notifyItemChanged(cursor.getPosition());
            Log.d("MainActivity","Items" +itemsModel.getId()+"added at "+cursor.getPosition());
        }

    }
    private void setRecyclerView(){
        itemsAdapter=new ItemsAdapter(itemsModels,
                (view ,position)->startActivity(Items_Details_Page.getIntent(
                        getApplicationContext(),
                        itemsModels.get(position).getId())
                ));
        binding.recycler.setLayoutManager(new LinearLayoutManager(this));
        binding.recycler.setAdapter(itemsAdapter);
    }
}