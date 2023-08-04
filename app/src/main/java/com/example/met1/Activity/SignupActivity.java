package com.example.met1.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.met1.Models.User;
import com.example.met1.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity extends AppCompatActivity {
    EditText name,email,password;
    CheckBox adminrole,customerrole;
    FirebaseAuth fAuth;
    Button regbtn;
    DatabaseReference dr;
    String Role;
    private ProgressDialog regbar;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        regbtn = findViewById(R.id.regbtn);
        name = findViewById(R.id.regname);
        email = findViewById(R.id.regemail);
        password = findViewById(R.id.regpassword);
        adminrole = findViewById(R.id.adminrole);
        customerrole = findViewById(R.id.customerrole);
        fAuth = FirebaseAuth.getInstance();
        dr = FirebaseDatabase.getInstance().getReference("Users");
        regbar = new ProgressDialog(this);

        regbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Name = name.getText().toString();
                String Password = password.getText().toString().trim();
                String Email = email.getText().toString().trim();
                regbar.setTitle("Creating account");
                regbar.setMessage("Please wait");
                regbar.setCancelable(false);
                regbar.show();


                if (adminrole.isChecked()){
                    Role = "Admin";
                }
                if (customerrole.isChecked()){
                    Role = "Customer";
                }

                fAuth.createUserWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            String id = fAuth.getCurrentUser().getUid();
                            User usermodel = new User(id,Name,Email,Password,Role);
                            dr.child(id).setValue(usermodel).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    regbar.dismiss();
                                    Toast.makeText(SignupActivity.this, "Account created", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getApplicationContext(),Login.class));
                                    finish();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    regbar.dismiss();
                                    Toast.makeText(SignupActivity.this, "Registration failed!", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(SignupActivity.this, "Registration failed!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });



    }
}
