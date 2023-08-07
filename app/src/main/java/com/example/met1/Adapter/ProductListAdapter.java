package com.example.met1.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners;
import com.example.met1.Activity.DetailActivity;
import com.example.met1.Activity.UserHomeFragment;
import com.example.met1.Activity.productModel;
import com.example.met1.Domain.ProductDomain;
import com.example.met1.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ViewHolder> {
    private final ArrayList<productModel> list;
    ArrayList<ProductDomain> items;
    Context context;
    public ProductListAdapter(Context context, ArrayList<productModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_product_list, parent, false);
        context = parent.getContext();
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        productModel model= list.get(position);
        holder.titleTxt.setText(list.get(position).getProductName());
        holder.location.setText(list.get(position).getDescription());
        holder.price.setText("UGX" + " "+list.get(position).getProductPrice());
        String imageUri;
        imageUri= model.getProductImage();
        Picasso.get().load(imageUri).into(holder.pic);
        int drawableResourceId = holder.itemView.getResources().getIdentifier(list.get(position).getProductImage(), "drawable", holder.itemView.getContext().getPackageName());

        Glide.with(holder.itemView.getContext())
                .load(drawableResourceId)
                .transform(new GranularRoundedCorners(30, 30, 0, 0))
                .into(holder.pic);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(holder.itemView.getContext(), DetailActivity.class);
                intent.putExtra("object",list.get(position));
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleTxt, location, price;
        ImageView pic;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTxt = itemView.findViewById(R.id.product_name);
            location = itemView.findViewById(R.id.location);
            price = itemView.findViewById(R.id.product_price);
            pic = itemView.findViewById(R.id.pic);

        }
    }
}
