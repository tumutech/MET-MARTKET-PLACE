package com.example.met1.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.met1.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {
    FirebaseAuth fAuth;
    DatabaseReference dr;
    Button login;
    EditText email,password;
    TextView regtxt;
    FirebaseUser fUser;
    private ProgressDialog loginDialog;
    private FirebaseUser currentUser;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        regtxt = findViewById(R.id.regtxt);
        login = findViewById(R.id.loginbtn);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        fAuth = FirebaseAuth.getInstance();
        dr = FirebaseDatabase.getInstance().getReference("Users");
        loginDialog=new ProgressDialog(this);


        regtxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),SignupActivity.class));
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginDialog.setTitle("Login in progress");
                loginDialog.setMessage("plese wait");
                loginDialog.setCancelable(false);
                loginDialog.show();
                //defining email and password
                String Email = email.getText().toString();
                String Password = password.getText().toString();
                fAuth.signInWithEmailAndPassword(Email, Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            currentUser = fAuth.getCurrentUser();
                            String userId = currentUser.getUid();
                            Log.d("ID",userId);

                            // Retrieve user data from Realtime Database
                            dr.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.exists()) {
                                        String role = dataSnapshot.child("role").getValue(String.class);

                                        if (role != null && role.equals("Admin")) {
                                            // Update the user's email in Firebase Authentication
                                            currentUser.updateEmail(Email).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        // Update the user's email in Realtime Database
                                                        dr.child(userId).child("email").setValue(Email).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                if (task.isSuccessful()) {
                                                                    loginDialog.dismiss();
                                                                    Toast.makeText(Login.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                                                    startActivity(new Intent(Login.this, Admin.class));
                                                                    finish();
                                                                } else {
                                                                    loginDialog.dismiss();
                                                                    Toast.makeText(Login.this, "Failed to update email", Toast.LENGTH_SHORT).show();
                                                                }
                                                            }
                                                        });
                                                    } else {
                                                        loginDialog.dismiss();
                                                        Toast.makeText(Login.this, "Failed to update email", Toast.LENGTH_SHORT).show();
                                                    }
                                                    loginDialog.dismiss();
                                                }
                                            });
                                        }else if (role != null && role.equals("Customer")) {
                                            // Update the user's email in Firebase Authentication
                                            currentUser.updateEmail(Email).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        // Update the user's email in Realtime Database
                                                        dr.child(userId).child("email").setValue(Email).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                if (task.isSuccessful()) {
                                                                    loginDialog.dismiss();
                                                                    Toast.makeText(Login.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                                                    startActivity(new Intent(Login.this, MainActivity.class));
                                                                    finish();
                                                                } else {
                                                                    loginDialog.dismiss();
                                                                    Toast.makeText(Login.this, "Failed to update email", Toast.LENGTH_SHORT).show();
                                                                }
                                                            }
                                                        });
                                                    } else {
                                                        loginDialog.dismiss();
                                                        Toast.makeText(Login.this, "Failed to update email", Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });
                                        }else {
                                            loginDialog.dismiss();
                                            Toast.makeText(Login.this, "Login Failed. Please try again", Toast.LENGTH_SHORT).show();
                                            loginDialog.dismiss();
                                        }
                                    } else {
                                        loginDialog.dismiss();
                                        Toast.makeText(Login.this, "User not found", Toast.LENGTH_SHORT).show();
                                        loginDialog.dismiss();
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                    loginDialog.dismiss();
                                    Toast.makeText(Login.this, "Login Failed. Please try again", Toast.LENGTH_SHORT).show();
                                    loginDialog.dismiss();
                                }
                            });
                        } else {
                            loginDialog.dismiss();
                            Toast.makeText(Login.this, "Check your Network", Toast.LENGTH_SHORT).show();
                            loginDialog.dismiss();
                        }
                    }
                });
            }
        });
    }
}