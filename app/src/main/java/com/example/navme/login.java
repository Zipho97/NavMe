package com.example.navme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class login extends AppCompatActivity {

    //variables
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListner;
    private FirebaseUser mUser;
    private Button btnLogin;
    private Button register;
    private EditText emailField;
    private EditText passField;
    public static final String TAG = "map";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        btnLogin = (Button) findViewById(R.id.Lbutton);
        emailField = (EditText) findViewById(R.id.Lemail);
        passField = (EditText) findViewById(R.id.Lpass);

        btnLogin = (Button) findViewById(R.id.Lbutton);

        mAuthListner = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                mUser = firebaseAuth.getCurrentUser();
                if(mUser != null){
                    //takes to another view if the validation was successful
                    startActivity(new Intent(login.this, map.class));
                    Toast.makeText(login.this,"Welcome, You're signed in",Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(login.this,"Login failed please try again",Toast.LENGTH_LONG).show();
                }
            }
        };

        //when the the button is clicked the method bellow will check the Firebase database to see if the user already exists and sign them in if they exist
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailField.getText().toString();
                String pwd = passField.getText().toString();

                if(!pwd.equals("") && !email.equals("")){
                    mAuth.signInWithEmailAndPassword(email,pwd).addOnCompleteListener(login.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()){
                                // Sign in success, update UI with the signed-in user's information
                                Toast.makeText(login.this, "Login failed please try again",Toast.LENGTH_LONG).show();
                            }else {
                                // Sign in success, update UI with the signed-in user's information
                                Toast.makeText(login.this, "Welcome to my app",Toast.LENGTH_LONG).show();
                                //takes to another view if the validation was successful
                                startActivity(new Intent(login.this, map.class));
                            }
                        }
                    });
                }

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListner);
        // Check if user is signed in (non-null) and update UI accordingly.
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mAuthListner!=null) {
            mAuth.removeAuthStateListener(mAuthListner);
        }
    }
}
