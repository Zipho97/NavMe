package com.example.navme;

import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class register extends AppCompatActivity {
    //variables
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListner;
    private FirebaseUser mUser;
    private Button btnLogin;
    private Button register;
    private EditText emailField;
    private EditText passField;
    public static final String TAG = "register";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        btnLogin = (Button) findViewById(R.id.Rbutton);
        emailField = (EditText) findViewById(R.id.Ruser);
        passField = (EditText) findViewById(R.id.Rpass);

        btnLogin = (Button) findViewById(R.id.Rbutton);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailField.getText().toString();
                String pwd = passField.getText().toString();
                if(!pwd.equals("") && !email.equals("")) {
                    mAuth.createUserWithEmailAndPassword(email, pwd).addOnCompleteListener(register.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()){
                                // Sign in success, update UI with the signed-in user's information
                                Toast.makeText(register.this, "SignUp failed please try again",Toast.LENGTH_LONG).show();
                            }else {
                                // Sign in success, update UI with the signed-in user's information
                                Toast.makeText(register.this, "Welcome to my app",Toast.LENGTH_LONG).show();
                                //takes to another view if the validation was successful
                                startActivity(new Intent(register.this, map.class));
                            }
                        }
                    });
                }
            }
        });

    }
}
