package com.example.suitcase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.suitcase.databinding.ActivityItmesDetailsPageBinding;

public class Items_Details_Page extends AppCompatActivity {
    ActivityItmesDetailsPageBinding binding;
    //Define variable
    public static final String ID="id";
    public static final String NAME="name";
    public static final String PRICE="price";
    public static final String DESCRIPTION="description";
    public static final String IMAGE="image";
    public static final String IS_PURCHASED="purchased";
    public static final int EDIT_ITEM_REQUEST=10001;

    // get Intent

    public static Intent getIntent(Context context,int id){
        Intent intent=new Intent(context,Items_Details_Page.class);
        intent.putExtra(ID,id);
        return intent;
    }
    ItemsModel item;
    Items_DBHelper itemsDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityItmesDetailsPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        item=new ItemsModel();
        itemsDbHelper=new Items_DBHelper(this);

        Bundle bundle=getIntent().getExtras();
        int id=bundle.getInt(Items_Details_Page.ID);
        Log.d("Items_Detail_Page",id+"");

        item=retrieveData(id);
        binding.imageViewItem.setImageURI(item.getImage());
        binding.textViewName.setText(item.getName());
        binding.textViewPrice.setText(item.getPrice().toString());
        binding.textViewDescription.setText(item.getDescription());

        //click Method of Edit Button

        binding.buttonEditItem.setOnClickListener(v -> startEditItems(v ,item));

        //click Method of Share Button
        binding.buttonShareItem.setOnClickListener(this::startShareItemActivity);
    }
    public void startEditItems(View v  , ItemsModel item){
        startActivity(EditItem.getIntent(getApplicationContext(),item));
    }
    private ItemsModel retrieveData(int id) {
        Cursor cursor=itemsDbHelper.getElementById(id);
        cursor.moveToNext();
        ItemsModel itemsModel=new ItemsModel();
        itemsModel.setId(cursor.getInt(0));
        itemsModel.setName(cursor.getString(1));
        itemsModel.setPrice(cursor.getDouble(2));
        itemsModel.setDescription(cursor.getString(3));
        itemsModel.setImage(Uri.EMPTY);
        try {
            Uri imageUri=Uri.parse(cursor.getString(4));
            itemsModel.setImage(imageUri);
        }catch (NullPointerException e){
            Toast.makeText
                    (this, "Error occurred is identifying image resource  ",
                            Toast.LENGTH_SHORT)
                    .show();
        }
        itemsModel.setPurchased(cursor.getInt(5)==1);
        Log.d("Items_Details_Page",itemsModel.toString());
        return itemsModel;
    }
    public void startShareItemActivity(View view){
        Intent intent=new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT,"");
        intent.putExtra(Intent.EXTRA_TEXT,"Check Your Cool Application ");
        startActivity(Intent.createChooser(intent,"Share via"));
    }
}