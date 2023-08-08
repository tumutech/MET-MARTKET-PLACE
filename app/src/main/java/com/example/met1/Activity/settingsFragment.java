package com.example.met1.Activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.met1.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class settingsFragment extends Fragment {
    FirebaseDatabase fDb;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    DatabaseReference dR;
    Button logout,changeaddressBtn,changepassword;
    EditText namef,location,phone,oldpasswd,newpasswd,confirmpasswd;
    

    public settingsFragment() {
        // Required empty public constructor
    }

    public static settingsFragment newInstance(String param1, String param2) {
        settingsFragment fragment = new settingsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_settings, container, false);
        namef = view.findViewById(R.id.nameaddress);
        oldpasswd = view.findViewById(R.id.currentpassword);
        newpasswd = view.findViewById(R.id.newpassword);
        confirmpasswd = view.findViewById(R.id.confirmpassword);
        changepassword = view.findViewById(R.id.changepwd);
        fDb = FirebaseDatabase.getInstance();
        dR = fDb.getReference().child("Users");
        logout = view.findViewById(R.id.signoutBtn);
        changeaddressBtn = view.findViewById(R.id.changeaddress);
        location = view.findViewById(R.id.locationaddress);
        phone = view.findViewById(R.id.phoneaddress);
        SessionManager sessionManager = new SessionManager(getContext());

        //fetch user details
        String useridentifier = sessionManager.getUserId();

        //changing user address information
        changeaddressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    dR.child(useridentifier).child("name").setValue(namef.getText().toString());
                    dR.child(useridentifier).child("address").setValue(location.getText().toString());
                    dR.child(useridentifier).child("phonenumber").setValue(phone.getText().toString());
                } catch (Exception e) {
                    Toast.makeText(getContext(), "Error, failed to save", Toast.LENGTH_SHORT).show();
                    throw new RuntimeException(e);
                }
                Toast.makeText(getContext(), "Address updated", Toast.LENGTH_SHORT).show();
            }
        });
        dR.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String myname = snapshot.child(useridentifier).child("name").getValue(String.class);
                    String myaddress = snapshot.child(useridentifier).child("address").getValue(String.class);
                    String myphone = snapshot.child(useridentifier).child("phonenumber").getValue(String.class);
                    namef.setText(myname);
                    location.setText(myaddress);
                    phone.setText(myphone);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    Activity MainActivity = getActivity();
                    sessionManager.logoutUser();
                    startActivity(new Intent(getContext(), Login.class));
                    MainActivity.finish();

                } catch (Exception e) {
                    Toast.makeText(getContext(), "Failed", Toast.LENGTH_SHORT).show();
                    throw new RuntimeException(e);
                }
            }
        });
        changepassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newPassword = newpasswd.getText().toString();
                String oldPassword = oldpasswd.getText().toString();

                if (TextUtils.isEmpty(newPassword) || TextUtils.isEmpty(oldPassword)) {
                    Toast.makeText(getContext(), "Please enter both old and new passwords", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(isValidPassword(newPassword,confirmpasswd.getText().toString(),newpasswd,confirmpasswd)){
                    // Get the current user
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    if (user != null) {
                        // Build the credential for re-authentication
                        AuthCredential credential = EmailAuthProvider.getCredential(user.getEmail(), oldPassword);
                        user.reauthenticate(credential)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            // Re-authentication successful, now update the password
                                            user.updatePassword(newPassword)
                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if (task.isSuccessful()) {
                                                                // Password updated successfully
                                                                dR.child(useridentifier).child("password").setValue(newPassword);
                                                                Toast.makeText(getContext(), "Password Changed", Toast.LENGTH_SHORT).show();
                                                            } else {
                                                                // Handle password update failure
                                                                Toast.makeText(getContext(), "Password update failed", Toast.LENGTH_SHORT).show();
                                                            }
                                                        }
                                                    }).addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception e) {
                                                            Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                                                        }
                                                    });
                                        }
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(getContext(), "Enter current password", Toast.LENGTH_SHORT).show();
                                        oldpasswd.setError("Wrong password");
                                        oldpasswd.requestFocus();
                                    }
                                });
                    }

                }
            }
        });
        return view;
    }
    public static boolean isValidPassword(String password,String confirmpass,EditText passwdfield, EditText confirm) {
        // Regular expression pattern for at least 8 characters and one digit
        String pattern = "^(?=.*[0-9]).{8,}$";
        Pattern regex = Pattern.compile(pattern);
        Matcher matcher = regex.matcher(password);
        if(!matcher.matches()){
            passwdfield.setError("Put atleast 8 characters and a digit");
            passwdfield.requestFocus();
            return false;
        }
        if(!(password.equals(confirmpass))){
            confirm.setError("Password does not match");
            confirm.requestFocus();
            return false;
        }
        return matcher.matches();
    }

}