package com.example.met1.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.met1.Activity.productModel;
import com.example.met1.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class productAdapter extends RecyclerView.Adapter<productAdapter.myViewHolder> {

    Context context;
    ArrayList<productModel> list;

    public productAdapter(Context context, ArrayList<productModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_product_list, parent,false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
    productModel model= list.get(position);
    holder.productName.setText(model.getProductName());
    holder.description.setText(model.getDescription());
    holder.productPrice.setText("UGX "+" " + model.getProductPrice());
    holder.discount.setText(model.getDiscount());
    holder.description_details.setText(model.getDescription_details());
    String imageUri;
    imageUri= model.getProductImage();
    Picasso.get().load(imageUri).into(holder.productImage);


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class  myViewHolder extends RecyclerView.ViewHolder{
        TextView productName;
        TextView description;
        TextView description_details;
        TextView productPrice;
        TextView discount;
        ImageView productImage;
        CardView cardView;
        Spinner selection;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            productName= itemView.findViewById(R.id.product_name);
            description= itemView.findViewById(R.id.location);
            productPrice= itemView.findViewById(R.id.product_price);
            discount= itemView.findViewById(R.id.Itemdiscount);
            productImage=itemView.findViewById(R.id.ItemImage);
            description_details = itemView.findViewById(R.id.descriptionTxt);


        }
    }
}
