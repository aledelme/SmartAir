package com.example.delme.smartair;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends Activity {

    private FirebaseAuth mAuth;
    private RelativeLayout main;
    private EditText emailEd, passwordEd;

    @Override
    protected void onStart(){
        super.onStart();
        FirebaseApp.initializeApp(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_view);

        mAuth = FirebaseAuth.getInstance();

        // Fierbase signin
        RelativeLayout register = findViewById(R.id.contentMain_register);
        RelativeLayout login = findViewById(R.id.contentMain_login);
        main = findViewById(R.id.contentMain_main);
        emailEd = findViewById(R.id.contentMain_emailEdit);
        passwordEd = findViewById(R.id.contentMain_passEdit);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailEd.getText().toString();
                String password = passwordEd.getText().toString();

                if (correoValido(email) && password.length() > 5){

                    mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(LoginActivity.this,
                                    new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {

                                            FirebaseUser user = mAuth.getCurrentUser();
                                            Intent i = new Intent(
                                                    LoginActivity.this,
                                                    MainActivity.class);
                                            i.putExtra("email",user.getEmail());
                                            startActivity(i);

                                            if (!task.isSuccessful()) {
                                                if (task.getException().getMessage().
                                                        contains("badly formatted")){
                                                    Snackbar.make(
                                                            main,
                                                            "Correo incorrecto",
                                                            Snackbar.LENGTH_SHORT).show();
                                                }
                                            }
                                        }
                                    });
                } else {
                    Snackbar.make(main, "Datos de registo incorrectos",
                            Snackbar.LENGTH_SHORT).show();
                }
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailEd.getText().toString();
                String password = passwordEd.getText().toString();

                if(correoValido(email) && password.length() > 5){
                    mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(LoginActivity.this,
                                    new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {

                                            FirebaseUser user = mAuth.getCurrentUser();
                                            Intent i = new Intent(
                                                    LoginActivity.this,
                                                    MainActivity.class);
                                            i.putExtra("email",user.getEmail());
                                            Toast.makeText(getApplicationContext(),"Bienvenido",Toast.LENGTH_SHORT).show();
                                            startActivity(i);

                                            // Si firebase nos devuelve un error, le deimos al usuario que
                                            // se ha equivocado
                                            if (!task.isSuccessful()) {
                                                Snackbar.make(main,
                                                        "Datos de registo incorrectos",
                                                        Snackbar.LENGTH_SHORT).show();
                                            }
                                        }
                                    });

                }else{ Snackbar.make(main, "Datos de registo incorrectos",
                        Snackbar.LENGTH_SHORT).show();
                }
            }
        });

    }

    private boolean correoValido(String correo) {
        if (!Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
            emailEd.setError("Email invalido");
            return false;
        } else { emailEd.setError(null); }

        return true;
    }
}
