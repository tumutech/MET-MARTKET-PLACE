package com.example.met1.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.met1.Helper.ManagmentCart;
import com.example.met1.R;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {
    private Button addToCartBtn;
    private TextView plusBtn, minusBtn, titleTxt, feeTxt, descriptionTxt, numberOrderTxt, startTxt, locationtxt;
    private ImageView picproduct;
    private productModel object;
    private int numberOrder = 1;
    private ManagmentCart managmentCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        managmentCart = new ManagmentCart(DetailActivity.this);

        initView();
        getBundle();
    }

    private void getBundle() {
        object = (productModel) getIntent().getSerializableExtra("object");
        String imageUri;
        imageUri= object.getProductImage();
        Glide.with(this)
                .load(imageUri)
                .into(picproduct);

        titleTxt.setText(object.getProductName());
        feeTxt.setText("UGX " + object.getProductPrice());
        descriptionTxt.setText(object.getDescription_details());
        numberOrderTxt.setText("" + numberOrder);
        startTxt.setText(object.getDiscount() + "");
        locationtxt.setText(object.getDescription());
        int pricetag = Integer.parseInt(object.getProductPrice());
        addToCartBtn.setText("Add to cart - UGX " + Math.round(numberOrder * pricetag));

        plusBtn.setOnClickListener(v -> {
            numberOrder = numberOrder + 1;
            numberOrderTxt.setText("" + numberOrder);
            addToCartBtn.setText("Add to cart - UGX " + Math.round(numberOrder * pricetag));
        });

        minusBtn.setOnClickListener(v -> {
            numberOrder = numberOrder - 1;
            numberOrderTxt.setText("" + numberOrder);
            addToCartBtn.setText("Add to cart - UGX " + Math.round(numberOrder * pricetag));
        });

        addToCartBtn.setOnClickListener(v -> {
            object.setNumberinCart(numberOrder);
            managmentCart.insertProduct(object);
        });
    }

    private void initView() {
        addToCartBtn = findViewById(R.id.addToCartBtn);
        locationtxt = findViewById(R.id.location);
        feeTxt = findViewById(R.id.priceTxt);
        titleTxt=findViewById(R.id.product_name);
        descriptionTxt = findViewById(R.id.descriptionTxt);
        numberOrderTxt = findViewById(R.id.numberItemTxt);
        plusBtn = findViewById(R.id.plusCardBtn);
        minusBtn = findViewById(R.id.MinusCartBtn);
        picproduct = findViewById(R.id.foodPic);
        startTxt = findViewById(R.id.StarTxt);


    }
}