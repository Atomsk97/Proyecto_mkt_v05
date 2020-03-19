package com.example.proyecto_mkt_v05;

import android.net.Uri;
import android.os.Bundle;

//import androidx.annotation.NonNull;
//import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
//import androidx.fragment.app.Fragment;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
/*
import com.example.proyecto_mkt_v05.ui.HomeFragment;
import com.example.proyecto_mkt_v05.ui.ContenedorImpulsoFragment;
import com.example.proyecto_mkt_v05.ui.LogoutFragment;
import com.example.proyecto_mkt_v05.ui.MercaderismoFragment;
*/
import com.example.proyecto_mkt_v05.ui.DatosDelClienteFragment;
import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
//import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements DatosDelClienteFragment.OnFragmentInteractionListener{

    private AppBarConfiguration mAppBarConfiguration;
    public static String cargo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cargo = getIntent().getStringExtra("USER_WORK");
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

/*
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();
                Fragment myFragment = null;
                boolean fragmentIsSelect = false;

                if (id == R.id.nav_home) {
                    myFragment = new HomeFragment();
                    fragmentIsSelect = true;
                } else if (id == R.id.nav_impulso){
                    myFragment = new ContenedorImpulsoFragment();
                    fragmentIsSelect = true;
                } else if (id == R.id.nav_mercaderismo){
                    myFragment = new MercaderismoFragment();
                    fragmentIsSelect = true;
                } else if (id == R.id.nav_log_out){
                    myFragment = new LogoutFragment();
                    fragmentIsSelect = true;
                }

                if(fragmentIsSelect){
                    getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, myFragment)
                            .commit();
                }

                DrawerLayout drawer = findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });
*/
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_impulso, R.id.nav_mercaderismo,
                R.id.nav_log_out)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if(drawer.isDrawerOpen(GravityCompat.START)){
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
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);

        TextView userName = findViewById(R.id.id_userName);
        String name = getIntent().getStringExtra("USER_FULL_NAME");
        if(name != null){
            userName.setText(name);
        }

        TextView userEmail = findViewById(R.id.id_userEmail);
        String email = getIntent().getStringExtra("USER_EMAIL");
        if(email != null){
            userEmail.setText(email);
        }

        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
