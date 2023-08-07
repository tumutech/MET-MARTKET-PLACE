package com.example.met1.Activity;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.met1.Helper.ManagmentCart;
import com.example.met1.Models.User;
import com.example.met1.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.Objects;

public class DetailActivity extends AppCompatActivity {
    private Button addToCartBtn;
    private TextView plusBtn, minusBtn, titleTxt, feeTxt, descriptionTxt, numberOrderTxt, startTxt, locationtxt,sellernamefield,sellcontactTxt;
    private ImageView picproduct;
    private productModel object;
    private int numberOrder = 1;
    private ManagmentCart managmentCart;
    private FirebaseDatabase fDb;
    private DatabaseReference dR;
    DecimalFormat decimalFormat = new DecimalFormat("#,##0'/='");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        fDb = FirebaseDatabase.getInstance();
        dR = fDb.getReference().child("Users");

        managmentCart = new ManagmentCart(DetailActivity.this);

        initView();
        getBundle();
    }

    @SuppressLint("SetTextI18n")
    private void getBundle() {
        object = (productModel) getIntent().getSerializableExtra("object");
        String productowner = object.getPuserid();
        // Retrieving user information to get the seller information
        dR.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange( DataSnapshot snapshot) {
                for (DataSnapshot child : snapshot.getChildren()) {
                    if (child.getKey().equals(productowner)) {
                        sellernamefield.setText(child.child("name").getValue(String.class));
                        sellcontactTxt.setText(child.child("email").getValue(String.class));
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        String imageUri;
        imageUri= object.getProductImage();
        Glide.with(this)
                .load(imageUri)
                .into(picproduct);

        titleTxt.setText(object.getProductName());
        int fprice = Integer.parseInt(object.getProductPrice());
        feeTxt.setText("UGX " + decimalFormat.format(fprice));
        descriptionTxt.setText(object.getDescription_details());
        numberOrderTxt.setText("" + numberOrder);
        startTxt.setText(object.getDiscount() + "");
        locationtxt.setText(object.getDescription());
        int pricetag = Integer.parseInt(object.getProductPrice());
        addToCartBtn.setText("Add to cart - UGX " + decimalFormat.format(Math.round(numberOrder * pricetag)));

        plusBtn.setOnClickListener(v -> {
            numberOrder = numberOrder + 1;
            numberOrderTxt.setText("" + numberOrder);
            addToCartBtn.setText("Add to cart - UGX " + decimalFormat.format(Math.round(numberOrder * pricetag)));
        });

        minusBtn.setOnClickListener(v -> {
            numberOrder = numberOrder - 1;
            numberOrderTxt.setText("" + numberOrder);
            addToCartBtn.setText("Add to cart - UGX " + decimalFormat.format(Math.round(numberOrder * pricetag)));
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
        sellernamefield = findViewById(R.id.sellername);
        sellcontactTxt = findViewById(R.id.sellercontact);


    }
}