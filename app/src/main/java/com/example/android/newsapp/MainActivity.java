package com.example.android.newsapp;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener {

    public static final String LOG_TAG = MainActivity.class.getName();

    /** A key used to pass the query*/
    final static String KEYWORD = "Keyword";

    private DrawerLayout mDrawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.nav_action_bar);
        setSupportActionBar(toolbar);

        mDrawer = (DrawerLayout) findViewById(R.id.drawer_main);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawer, toolbar,
                R.string.drawer_open, R.string.drawer_close);

        mDrawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        displaySelectedScreen(R.id.nav_latest_news);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.searchview_menu, menu);
        MenuItem item = menu.findItem(R.id.search_bar_item);
        SearchView searchView = (SearchView) item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Bundle bundle = new Bundle();
                bundle.putString(KEYWORD, query);

                QueryFragment fragment = new QueryFragment();
                getSupportActionBar().setTitle(query);
                fragment.setArguments(bundle);

                //replacing the fragment
                if (fragment != null) {
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.frame_layout, fragment);
                    ft.commit();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }

    @Override
    public void onBackPressed() {
        if (mDrawer.isDrawerOpen(GravityCompat.START)) {
            mDrawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void displaySelectedScreen(int itemId) {

        //creating fragment object
        Fragment fragment = null;

        //initializing the fragment object which is selected
        switch (itemId) {
            case R.id.nav_latest_news:
                fragment = new LatestFragment();
                getSupportActionBar().setTitle(R.string.news_latest);
                break;
            case R.id.nav_environment:
                fragment = new EnvironmentFragment();
                getSupportActionBar().setTitle(R.string.news_environment);
                break;
            case R.id.nav_fashion:
                fragment = new FashionFragment();
                getSupportActionBar().setTitle(R.string.news_fashion);
                break;
            case R.id.nav_film:
                fragment = new FilmFragment();
                getSupportActionBar().setTitle(R.string.news_film);
                break;
            case R.id.nav_music:
                fragment = new MusicFragment();
                getSupportActionBar().setTitle(R.string.news_music);
                break;
            case R.id.nav_politics:
                fragment = new PoliticsFragment();
                getSupportActionBar().setTitle(R.string.news_politics);
                break;
            case R.id.nav_world_news:
                fragment = new WorldFragment();
                getSupportActionBar().setTitle(R.string.news_world);
                break;
        }

        //replacing the fragment
        if (fragment != null) {
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.frame_layout, fragment);
            ft.commit();
        }

        mDrawer.closeDrawer(GravityCompat.START);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        // Calling the method {@link displaySelectedScreen} and passing the id of selected menu
        displaySelectedScreen(item.getItemId());

        return true;
    }
}
