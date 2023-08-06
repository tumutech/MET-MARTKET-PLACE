package com.example.met1.Activity;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.met1.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class AddProductFragment extends Fragment {
TextInputEditText name, description, price, discount, details;
    Spinner selection;
    ImageView productImage;

    RelativeLayout Imagechoose;
    Uri ImageUri ;
    ProgressDialog progressDialog;
    private FirebaseDatabase mdatabase;
    private DatabaseReference mref;
    private FirebaseStorage mstorage;
    private static int PICK_IMAGE_REQUEST = 1;
    Button upload;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_product, container, false);
        name= view.findViewById(R.id.ItemName);
        price= view.findViewById(R.id.ItemPrice);
        discount= view.findViewById(R.id.Itemdiscount);
        description= view.findViewById(R.id.ItemDesc);
        description= view.findViewById(R.id.ItemDetails);
        selection= view.findViewById(R.id.product_category);
        productImage= view.findViewById(R.id.ItemImage);
        Imagechoose= view.findViewById(R.id.ChooseImage);
        upload= view.findViewById(R.id.UploadBtn);


        //Firebase database initialisation
        mdatabase= FirebaseDatabase.getInstance();
        mref= mdatabase.getReference().child("Uploads");
        mstorage= FirebaseStorage.getInstance();
        progressDialog= new ProgressDialog(getContext());
        progressDialog.setTitle("Uploading Data");
        progressDialog.setMessage("Please Wait While Uploading Your data...");
        progressDialog.setCancelable(false);


        Imagechoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CheckPermission();
            }
        });
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //validating user input
                String Productname= name.getText().toString().trim();
                String prices= price.getText().toString().trim();
                String pdiscount= discount.getText().toString().trim();
                String desc= description.getText().toString().trim();
                String descDetail= description.getText().toString().trim();
                String selected= selection.getSelectedItem().toString().trim();
                if(Productname.isEmpty()){
                    name.setError("Product Name Required");
                    name.requestFocus();
                    return;

                }
                else if(prices.isEmpty()){
                    price.setError("Product Price Required");
                    price.requestFocus();
                    return;
                }
                else if(pdiscount.isEmpty()){
                    discount.setError("Product Price Required");
                    discount.requestFocus();
                    return;
                }
                else if(desc.isEmpty()){
                    description.setError("Product Price Required");
                    description.requestFocus();
                    return;
                }
                else if(descDetail.isEmpty()){
                    description.setError("Product Price Required");
                    description.requestFocus();
                    return;
                }
                else if(selected.isEmpty()){

                    selection.requestFocus();

                    return;
                }
                progressDialog.show();
                final StorageReference reference = mstorage.getReference().
                        child(System.currentTimeMillis() + "");
                reference.putFile(ImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {

                                productModel modal = new productModel();
                                modal.setProductImage(uri.toString());
                                modal.setProductName(Productname);
                                modal.setDescription(desc);
                                modal.setProductPrice(prices);
                                modal.setDiscount(pdiscount);
                                modal.setDescription_details(descDetail);
                                modal.setSelection(selected);
                                mdatabase.getReference().child("Products").push().setValue(modal).
                                        addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                Toast.makeText(getContext(), "Product Uploaded Successfully", Toast.LENGTH_SHORT).show();
                                                progressDialog.dismiss();
                                                productImage.setImageResource(R.drawable.bell);
                                                name.setText("");
                                                price.setText("");
                                                description.setText("");
                                                discount.setText("");
                                                description.setText("");
                                                selection.setSelection(-1);
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(getContext(), "Upload failed", Toast.LENGTH_LONG).show();
                                            }
                                        });

                            }
                        });
                    }
                });
            }
        });



        return view;
    }

    

    private void CheckPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(getContext(),
                    android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions((Activity) getContext(), new
                        String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 2);
            } else {
                PickImageFromgallry();
            }

        }
    }

    private void PickImageFromgallry() {
        //here we goto to the gallery and select the image
        Intent intent= new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            ImageUri = data.getData();
            productImage = productImage.findViewById(R.id.ItemImage);
            productImage.setImageURI(ImageUri);



        }
    }
}