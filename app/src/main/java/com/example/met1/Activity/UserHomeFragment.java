package com.example.met1.Activity;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.met1.Adapter.ProductListAdapter;
import com.example.met1.Domain.ProductDomain;
import com.example.met1.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.List;

public class UserHomeFragment extends Fragment {
    //NEW DISPLAY
    private RecyclerView recyclerView;
    private ProductListAdapter adapter;
    private ArrayList<productModel> list;
    SearchView searchView;
    private FirebaseDatabase mdatabase;
    private DatabaseReference mref;
    private FirebaseStorage mstorage;
    private EditText searchTxt;

    public UserHomeFragment() {
    }
    public static UserHomeFragment newInstance() {
        UserHomeFragment fragment = new UserHomeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_home, container, false);
        /**
         * intialising all the xml components and Firebase components
         */
        mdatabase=FirebaseDatabase.getInstance();
        mref= mdatabase.getReference().child("Products");
        mstorage=FirebaseStorage.getInstance();
        recyclerView= view.findViewById(R.id.view1);
        searchTxt = view.findViewById(R.id.searchText);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        recyclerView.setHasFixedSize(true);
        list= new ArrayList<>();
        adapter = new ProductListAdapter(getContext(),list);
        recyclerView.setAdapter(adapter);
        mref.addChildEventListener(new ChildEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                productModel model= snapshot.getValue(productModel.class);
                list.add(model);
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        searchTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // This method is called before the text is changed
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // This method is called when the text is being changed
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // This method is called after the text has changed
//                performSearch(editable.toString());
            }
        });

        return view;


    }
    private void performSearch(String query) {
        mref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear(); // Clear the previous list

                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    for (DataSnapshot productSnapshot : userSnapshot.getChildren()) {
                        productModel product = productSnapshot.getValue(productModel.class);
                        if (product != null && product.getDescription().equalsIgnoreCase(query)) {
                            list.add(product);
                        }
                    }
                }

                adapter.notifyDataSetChanged(); // Update the UI with the new filtered list
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(TAG, "Database read error: " + databaseError.getMessage());
            }
        });
    }

//    private void performSearch(String query) {
//        mref.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                list.clear(); // Clear the previous list
//
//                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
//                    for (DataSnapshot productSnapshot : userSnapshot.getChildren()) {
//                        productModel product = productSnapshot.getValue(productModel.class);
//                        if (product != null && product.getDescription().equalsIgnoreCase(query)) {
//                            list.add(product);
//                        }
//                    }
//                }
//
//                adapter.notifyDataSetChanged(); // Update the UI with the new filtered list
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                Log.e(TAG, "Database read error: " + databaseError.getMessage());
//            }
//        });
//    }

//    private void performSearch(String query) {
//        list.clear(); // Clear previous results
//
//        mref.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
//                    for (DataSnapshot productSnapshot : userSnapshot.getChildren()) {
//                        productModel product = productSnapshot.getValue(productModel.class);
//                        if (product != null && product.getDescription().equalsIgnoreCase(query)) {
//                            list.add(product);
//                        }
//                    }
//                }
//
//                // Update your UI with the search results
//                adapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                Log.e(TAG, "Database read error: " + databaseError.getMessage());
//            }
//        });
//    }

//    private void performSearch(String query) {
//        Query searchQuery = mref.orderByChild("location").equalTo(query);
//
//        searchQuery.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                // This method is called when the data at the specified location changes.
//
//                // Clear any previous search results or initialize a list to hold results
//                List<productModel> searchResults = new ArrayList<>();
//
//                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                    // Parse the Product object and add it to the searchResults list
//                    productModel product = snapshot.getValue(productModel.class);
//                    searchResults.add(product);
//                    list.add(product);
//                }
//
//                // Update your UI with the search results, for example, using an Adapter
//                adapter.notifyDataSetChanged();
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                // This method is called if the read operation is cancelled
//                Log.e(TAG, "Database read error: " + databaseError.getMessage());
//            }
//        });
//    }



}