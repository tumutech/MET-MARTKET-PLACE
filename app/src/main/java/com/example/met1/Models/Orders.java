package com.example.met1.Models;

import android.content.Context;

import com.example.met1.Helper.ManagmentCart;

public class Orders extends ManagmentCart {
    String orderid,sellerid,totalamount,products;
    public Orders(Context context) {
        super(context);
    }
}
