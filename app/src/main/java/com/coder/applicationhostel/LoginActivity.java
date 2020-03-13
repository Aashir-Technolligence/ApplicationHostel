package com.coder.applicationhostel;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

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

public class LoginActivity extends AppCompatActivity {
    private Button register, login;
    private EditText email, password;
    ProgressBar progressBar;
    FirebaseAuth auth;
    String Email, Password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.setTitle("Login");
        register = (Button) findViewById(R.id.btnRegister);
        login = (Button) findViewById(R.id.btnLogin);
        email = (EditText) findViewById(R.id.edtEmail);
        password = (EditText) findViewById(R.id.edtPassword);
        progressBar = (ProgressBar) findViewById(R.id.loginProgress);
        progressBar.setVisibility(View.GONE);
        auth = FirebaseAuth.getInstance();
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Email = email.getText().toString();
                Password = password.getText().toString();
                if (!Email.isEmpty() && !Password.isEmpty())
                {
                    if(Email.contains("@")||Email.contains(".com"))
                    {
                            progressBar.setVisibility(View.VISIBLE);

                                auth.signInWithEmailAndPassword(Email , Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful())
                                        {
                                            try
                                            {
                                                final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                                final String userid = user.getUid();
                                                FirebaseDatabase.getInstance().getReference()
                                                        .child("Hostel_Admin")
                                                        .child(userid).addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(DataSnapshot dataSnapshot)
                                                    {
                                                        if(dataSnapshot.exists())
                                                        {
                                                                Toast.makeText(getApplicationContext() , "Wellcome" , Toast.LENGTH_LONG).show();
                                                                Intent intent = new Intent(LoginActivity.this,NavActivity.class);
                                                                startActivity(intent);
                                                                finish();
                                                            SharedPreferences.Editor editor = getSharedPreferences("Log", MODE_PRIVATE).edit();
                                                            editor.putBoolean("isLoggedIn", true );
                                                            editor.commit();
                                                        }
                                                    }


                                                    @Override
                                                    public void onCancelled(DatabaseError databaseError) {

                                                    }
                                                });

                                            } catch (NullPointerException e) {
                                                Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                                progressBar.setVisibility(View.GONE);

                                            }

                                        } else {
                                            progressBar.setVisibility(View.GONE);
                                            Toast.makeText(LoginActivity.this,  task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                        }

                                    }
                                });
                    }
                    else
                    {
                        Toast.makeText(LoginActivity.this, "Invalid Email", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(LoginActivity.this, "Please Enter the Credentials", Toast.LENGTH_SHORT).show();
                }



            }
        });


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
    }


}
