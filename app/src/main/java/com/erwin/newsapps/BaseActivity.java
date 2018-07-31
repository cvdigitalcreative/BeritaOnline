package com.erwin.newsapps;

import android.annotation.SuppressLint;
import android.support.v4.app.Fragment;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;

import com.erwin.newsapps.Boundary.AccountFrag;
import com.erwin.newsapps.Boundary.CatagoryFrag;
import com.erwin.newsapps.Boundary.FavoriteFrag;
import com.erwin.newsapps.Boundary.NewsFrag;

import static com.erwin.newsapps.R.drawable.sumekslogo;

public class BaseActivity extends AppCompatActivity {
    private ActionBar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        toolbar = getSupportActionBar();
//        toolbar.setDisplayShowTitleEnabled(true);

        BottomNavigationView bottomNavigationView = findViewById(R.id.menunews);
        bottomNavigationView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);
        loadfragment(new NewsFrag());
    }

    private void loadfragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment)
                .commit();

    }

    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.news:
                    fragment = new NewsFrag();
                    loadfragment(fragment);
                    return true;
                case R.id.katagori:
                    fragment = new CatagoryFrag();
                    loadfragment(fragment);
                    return true;
                case R.id.favorite_menu:
                    fragment = new FavoriteFrag();
                    loadfragment(fragment);
                    return true;
                case R.id.akun:
                    fragment = new AccountFrag();
                    loadfragment(fragment);
                    return true;
            }
            return false;
        }
    };

    @Override
    public void onBackPressed(){
        moveTaskToBack(false);
    }
}