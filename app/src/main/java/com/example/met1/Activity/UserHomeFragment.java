package com.example.met1.Activity;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.met1.Adapter.FoodListAdapter;
import com.example.met1.Domain.ProductDomain;
import com.example.met1.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserHomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserHomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private RecyclerView.Adapter adapterFoodList;
    private RecyclerView recyclerViewFood;
    private String mParam1;
    private String mParam2;

    public UserHomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserHomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UserHomeFragment newInstance(String param1, String param2) {
        UserHomeFragment fragment = new UserHomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    private void initRecyclerview() {
        ArrayList<ProductDomain> items=new ArrayList<>();
        items.add(new ProductDomain("Cheese Burger","Satisfy your cravings with our juicy Cheese Burger. \n" +
                "Made with 100% Angus beef patty and topped with\n" +
                " melted cheddar cheese, fresh lettuce, tomato, and\n" +
                " our secret sauce, this classic burger will leave you\n" +
                " wanting more. Served with crispy fries and a drink,\n" +
                " it's the perfect meal for any occasion.","fast_1",15,20,120,4));
        items.add(new ProductDomain("Pizza Peperoni","Get a taste of Italy with our delicious Pepperoni Pizza. Made with freshly rolled dough, zesty tomato sauce, mozzarella cheese, and topped with spicy pepperoni slices, this pizza is sure to be a crowd-pleaser. Perfectly baked in a wood-fired oven, it's the perfect choice for a quick lunch or a family dinner."
                ,"fast_2",10,25,200,5));
        items.add(new ProductDomain("Vegetable Pizza","Looking for a healthier option? Try our Vegetable Pizza, made with a variety of fresh veggies such as bell peppers, onions, mushrooms, olives, and tomatoes. Topped with mozzarella cheese and a tangy tomato sauce, this pizza is full of flavor and goodness. Perfect for vegetarians and anyone who wants to add more greens to their diet."
                ,"fast_3",13,30,100,4.5));
        recyclerViewFood.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));

        adapterFoodList=new FoodListAdapter(items);
        recyclerViewFood.setAdapter(adapterFoodList);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_home, container, false);
        recyclerViewFood= view.findViewById(R.id.view1);

        // Inflate the layout for this fragment
        initRecyclerview();
        return view;
    }

}