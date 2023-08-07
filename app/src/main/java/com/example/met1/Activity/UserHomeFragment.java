package com.example.met1.Activity;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.met1.Adapter.ProductListAdapter;
import com.example.met1.Domain.ProductDomain;
import com.example.met1.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;

public class UserHomeFragment extends Fragment {
    //NEW DISPLAY
    private RecyclerView recyclerView;
    private ProductListAdapter adapter;
    private ArrayList<productModel> list;
    SearchView searchView;
    private FirebaseDatabase mdatabase;
    private DatabaseReference mref;
    private FirebaseStorage mstorage;

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
        return view;
    }

}