package com.example.suitcase.Adapter;

import android.content.ClipData;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.suitcase.ItemsModel;
import com.example.suitcase.R;

import java.util.ArrayList;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ItemViewHolder> {
    private final RecyclerItemsClickView recyclerItemsClickView;
    private ArrayList<ItemsModel> itemsModels;

    public ItemsAdapter(ArrayList<ItemsModel>itemsModels,RecyclerItemsClickView recyclerItemsClickView){
        this.recyclerItemsClickView=recyclerItemsClickView;
        this.itemsModels=itemsModels;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_item,parent,false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemsAdapter.ItemViewHolder holder, int position) {
        ItemsModel itemsModel=itemsModels.get(position);
        holder.txt_name.setText(itemsModel.getName());
        if (itemsModel.isPurchased()){
            holder.txt_name.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_check,0);
        }
        holder.txt_price.setText(String.valueOf(itemsModel.getPrice()));
        holder.txt_description.setText(itemsModel.getDescription());
        Uri imageUri=itemsModel.getImage();
        if (imageUri !=null){
            holder.imageView.setImageURI(imageUri);
        }
    }

    @Override
    public int getItemCount() {
        return itemsModels.size();
    }
    public class ItemViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView txt_name,txt_price,txt_description;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.item_image);
            txt_name=itemView.findViewById(R.id.item_name);
            txt_price=itemView.findViewById(R.id.item_price);
            txt_description=itemView.findViewById(R.id.item_dis);
            itemView.setOnClickListener(this::itemViewOnClick);
        }
        private void itemViewOnClick(View view){
            recyclerItemsClickView.onItemClick(view,getAdapterPosition());
        }
    }
}
