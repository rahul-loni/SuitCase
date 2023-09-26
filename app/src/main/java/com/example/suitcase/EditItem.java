package com.example.suitcase;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.suitcase.databinding.ActivityEditItemBinding;

public class EditItem extends AppCompatActivity {
ActivityEditItemBinding binding;
    public static final String ID="id";
    public static final String NAME="name";
    public static final String PRICE="price";
    public static final String DESCRIPTION="description";
    public static final String IMAGE="image";
    public static final String IS_PURCHASED="purchased";

    private Items_DBHelper items_dbHelper;
    private Uri imageUri;
    private int id;
    private boolean isPurchased;

    public static Intent getIntent(Context context,ItemsModel itemsModel){
        Intent intent=new Intent(context,EditItem.class);
        intent.putExtra(ID ,itemsModel.getId());
        intent.putExtra(NAME,itemsModel.getName());
        intent.putExtra(PRICE,itemsModel.getPrice());
        intent.putExtra(DESCRIPTION,itemsModel.getDescription());
        intent.putExtra(IMAGE,itemsModel.getImage().toString());
        intent.putExtra(IS_PURCHASED,itemsModel.isPurchased());
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityEditItemBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        items_dbHelper=new Items_DBHelper(this);
        Bundle bundle=getIntent().getExtras();
        id=bundle.getInt(Items_Details_Page.ID);
        isPurchased=bundle.getBoolean(Items_Details_Page.IS_PURCHASED);
        String name=bundle.getString(Items_Details_Page.NAME);
        String price=bundle.getString(Items_Details_Page.PRICE);
        String description=bundle.getString(Items_Details_Page.DESCRIPTION);
        imageUri=Uri.EMPTY;

        try {
            imageUri=Uri.parse(bundle.getString(Items_Details_Page.IMAGE));
        }catch (NullPointerException e){
            Toast.makeText(this, "Error occurred in identifying Image ", Toast.LENGTH_SHORT).show();
        }
        binding.editItemName.setText(name);
        binding.editItemPrice.setText(price);
        binding.editItemDescription.setText(description);
        binding.editItemImage.setImageURI(imageUri);

        binding.editItemImage.setOnClickListener(this::pickImage);
        binding.btnEdit.setOnClickListener(this::saveItem);
    }
    private void pickImage(View view){
        ImagePickUtility.pickImage(view,EditItem.this);
    }
    private void saveItem(View view){
        String name=binding.editItemName.getText().toString().trim();
        if (name.isEmpty()){
            binding.editItemName.setError("Name field is empty");
            binding.editItemName.requestFocus();
        }
        double price =0 ;
        try {
            price=Double.parseDouble(binding.editItemPrice.getText().toString().trim());
        }catch (NullPointerException e){
            Toast.makeText(this, "Something wrong with price ", Toast.LENGTH_SHORT).show();
        }catch (NumberFormatException e){
            Toast.makeText(this, "Price should be a number ", Toast.LENGTH_SHORT).show();
        }
        if (price<=0){
            binding.editItemPrice.setError("Price should be greater than 0");
            binding.editItemPrice.requestFocus();
        }
        String description= binding.editItemDescription.getText().toString().trim();
        if (description.isEmpty()){
            binding.editItemDescription.setError("Description is empty ");
            binding.editItemDescription.requestFocus(); 
        }
        Log.d("EditItem","saving : {"+ "id:"+id+",name:"+name+",price:"+price+"" +
                ",description:"+description+",imageUri:"+imageUri.toString()+"" +
                ",isPurchased:"+isPurchased+"}");
        if (items_dbHelper.update(id,name,price,description,imageUri.toString(),isPurchased)){
            Toast.makeText(this, "Saves Successfully", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
        }else {
            Toast.makeText(this, "Failed to save ", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (data!=null){
            imageUri=data.getData();
            binding.editItemImage.setImageURI(imageUri);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    @Override
    public void finish(){
        super.finish();
    }
}