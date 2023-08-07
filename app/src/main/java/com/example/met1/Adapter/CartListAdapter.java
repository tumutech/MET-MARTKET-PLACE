package com.example.met1.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners;
import com.example.met1.Activity.productModel;
import com.example.met1.Helper.ChangeNumberItemsListener;
import com.example.met1.Helper.ManagmentCart;
import com.example.met1.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CartListAdapter extends RecyclerView.Adapter<CartListAdapter.ViewHolder> {

    ArrayList<productModel> listProductSelected;
    private ManagmentCart managmentCart;
    ChangeNumberItemsListener changeNumberItemsListener;

    public CartListAdapter(ArrayList<productModel> listProductSelected, Context context, ChangeNumberItemsListener changeNumberItemsListener) {
        this.listProductSelected = listProductSelected;
        managmentCart=new ManagmentCart(context);
        this.changeNumberItemsListener = changeNumberItemsListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_cart, parent, false);

        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.title.setText(listProductSelected.get(position).getProductName());
        holder.feeEachItem.setText("UGX "+" "+ listProductSelected.get(position).getProductPrice());
        int prctag = Integer.parseInt(listProductSelected.get(position).getProductPrice());
        holder.totalEachItem.setText("UGX"+" " + Math.round((listProductSelected.get(position).getNumberinCart() * prctag)));
        holder.num.setText(String.valueOf(listProductSelected.get(position).getNumberinCart()));
        String imageUri;
        imageUri= listProductSelected.get(position).getProductImage();
        Picasso.get().load(imageUri).into(holder.pic);

        //int drawableResourceId=holder.itemView.getContext().getResources().getIdentifier(listProductSelected.get(position).getProductImage(),"drawable",holder.itemView.getContext().getPackageName());

//        Glide.with(holder.itemView.getContext())
//                .load(drawableResourceId)
//                .transform(new GranularRoundedCorners(30,30,30,30))
//                .into(holder.pic);


        holder.plusItem.setOnClickListener(v -> managmentCart.plusNumberFood(listProductSelected, position, () -> {
            notifyDataSetChanged();
            changeNumberItemsListener.changed();
        }));

        holder.minusItem.setOnClickListener(v -> managmentCart.minusNumberFood(listProductSelected, position, () -> {
            notifyDataSetChanged();
            changeNumberItemsListener.changed();
        }));
    }

    @Override
    public int getItemCount() {
        return listProductSelected.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, feeEachItem, plusItem, minusItem;
        ImageView pic;
        TextView totalEachItem, num;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.product_name);
            pic = itemView.findViewById(R.id.pic);
            feeEachItem = itemView.findViewById(R.id.feeEachItem);
            totalEachItem = itemView.findViewById(R.id.totalEachItem);
            plusItem = itemView.findViewById(R.id.plusCartBtn);
            minusItem = itemView.findViewById(R.id.minusCartBtn);
            num = itemView.findViewById(R.id.numberItemTxt);
        }
    }
}
