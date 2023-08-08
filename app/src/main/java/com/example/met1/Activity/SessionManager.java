package com.example.met1.Activity;

import android.content.SharedPreferences;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import android.content.Context;

public class SessionManager {
    private static final String PREF_NAME = "UserSession";
    private static final String KEY_USER_ID = "userId";
    private static final String KEY_TOKEN = "token";

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private FirebaseAuth firebaseAuth;

    public SessionManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        firebaseAuth = FirebaseAuth.getInstance();
    }

    public void loginUser(FirebaseUser user) {
        editor.putString(KEY_USER_ID, user.getUid());
        editor.putString(KEY_TOKEN, user.getIdToken(false).getResult().getToken());
        editor.apply();
    }

    public boolean isLoggedIn() {
        return sharedPreferences.contains(KEY_USER_ID) && sharedPreferences.contains(KEY_TOKEN);
    }

    public void logoutUser() {
        firebaseAuth.signOut();
        editor.clear();
        editor.apply();
    }

    public String getUserId() {
        return sharedPreferences.getString(KEY_USER_ID, null);
    }

    public String getToken() {
        return sharedPreferences.getString(KEY_TOKEN, null);
    }
}
