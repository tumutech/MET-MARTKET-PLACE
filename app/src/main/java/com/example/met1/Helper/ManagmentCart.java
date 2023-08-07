package com.example.met1.Helper;

import android.content.Context;
import android.widget.Toast;

import com.example.met1.Activity.productModel;

import java.util.ArrayList;

public class ManagmentCart {

    private Context context;
    private TinyDB tinyDB;

    public ManagmentCart(Context context) {
        this.context = context;
        this.tinyDB = new TinyDB(context);
    }

    public void insertProduct(productModel item) {
        ArrayList<productModel> listfood = getListCart();
        boolean existAlready = false;
        int n = 0;
        for (int y = 0; y < listfood.size(); y++) {
            if (listfood.get(y).getProductName().equals(item.getProductName())) {
                existAlready = true;
                n = y;
                break;
            }
        }
        if (existAlready) {
            listfood.get(n).setNumberinCart(item.getNumberinCart());
            Toast.makeText(context, "Item already in cart", Toast.LENGTH_SHORT).show();
        } else {
            listfood.add(item);
            Toast.makeText(context, "Added to your Cart", Toast.LENGTH_SHORT).show();
        }
        tinyDB.putListObject("CartList", listfood);

    }

    public ArrayList<productModel> getListCart() {
        return tinyDB.getListObject("CartList");
    }

    public void minusNumberFood(ArrayList<productModel> listfood, int position, ChangeNumberItemsListener changeNumberItemsListener) {
        if (listfood.get(position).getNumberinCart() == 1) {
            listfood.remove(position);
        } else {
            listfood.get(position).setNumberinCart(listfood.get(position).getNumberinCart() - 1);
        }
        tinyDB.putListObject("CartList", listfood);
        changeNumberItemsListener.changed();
    }

    public void plusNumberFood(ArrayList<productModel> listfood, int position, ChangeNumberItemsListener changeNumberItemsListener) {
        listfood.get(position).setNumberinCart(listfood.get(position).getNumberinCart() + 1);
        tinyDB.putListObject("CartList", listfood);
        changeNumberItemsListener.changed();
    }

    public Double getTotalFee() {
        ArrayList<productModel> listfood2 = getListCart();
        double fee = 0;
        for (int i = 0; i < listfood2.size(); i++) {
            Double prc = Double.valueOf(listfood2.get(i).getProductPrice());
            fee = fee + prc * listfood2.get(i).getNumberinCart();
        }
        return fee;
    }
}
