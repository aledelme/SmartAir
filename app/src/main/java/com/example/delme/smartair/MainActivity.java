package com.example.delme.smartair;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.delme.smartair.Fragments.ConsumptionFragment;
import com.example.delme.smartair.Fragments.OverviewFragment;
import com.example.delme.smartair.Fragments.PlanesFragment;
import com.example.delme.smartair.Fragments.ConfigurationsFragment;
import com.example.delme.smartair.Fragments.SuggestionsFragments;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private TextView infoUser;
    private FirebaseAuth mAuth;
    private RelativeLayout main;
    private EditText emailEd, passwordEd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,
                drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Fierbase signin
        RelativeLayout register = findViewById(R.id.contentMain_register);
        main = findViewById(R.id.contentMain_main);
        infoUser = findViewById(R.id.contentMain_user);
        emailEd = findViewById(R.id.contentMain_emailEdit);
        passwordEd = findViewById(R.id.contentMain_passEdit);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Obtenemos el texto de los EditTexts
                String email = emailEd.getText().toString();
                String password = passwordEd.getText().toString();

                if (correoValido(email) && password.length() > 5){

                    mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(MainActivity.this,
                                    new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {

                                            FirebaseUser user = mAuth.getCurrentUser();
                                            updateUI(user);

                                            if (!task.isSuccessful()) {

                                                if (task.getException().getMessage().
                                                        contains("badly formatted")){
                                                    Snackbar.make(
                                                            main,
                                                            "Correo incorrecto",
                                                            Snackbar.LENGTH_SHORT).show();
                                                }
                                                updateUI(null);
                                            }

                                        }
                                    });
                } else {
                    Snackbar.make(main, "Datos de registo incorrectos",
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

    private void updateUI(FirebaseUser user){

        if (user != null) {
            infoUser.setText("Bienvenido " + user.getEmail());

            findViewById(R.id.contentMain_user).setVisibility(View.VISIBLE);
            findViewById(R.id.contentMain_passEdit).setVisibility(View.INVISIBLE);
            findViewById(R.id.contentMain_emailEdit).setVisibility(View.INVISIBLE);

        } else {
            findViewById(R.id.contentMain_user).setVisibility(View.INVISIBLE);
            findViewById(R.id.contentMain_passEdit).setVisibility(View.VISIBLE);
            findViewById(R.id.contentMain_emailEdit).setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        switch (item.getItemId()){
            case R.id.nav_overview:
                cambiarFragment(new OverviewFragment());
                break;
            case R.id.nav_planes:
                cambiarFragment(new PlanesFragment());
                break;
            case R.id.nav_configuration:
                cambiarFragment(new ConfigurationsFragment());
                break;
            case R.id.nav_consumption:
                cambiarFragment(new ConsumptionFragment());
                break;
            case R.id.nav_suggestions:
                cambiarFragment(new SuggestionsFragments());
                break;
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void cambiarFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameContainer, fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .disallowAddToBackStack()
                .commit();
        return;
    }
}
