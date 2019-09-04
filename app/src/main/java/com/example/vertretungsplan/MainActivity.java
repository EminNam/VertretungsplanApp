package com.example.vertretungsplan;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements Montag.OnFragmentInteractionListener,Dienstag.OnFragmentInteractionListener,Mittwoch.OnFragmentInteractionListener,Donnerstag.OnFragmentInteractionListener,Freitag.OnFragmentInteractionListener
{
    private Toolbar toolbar;
    private TabLayout seitenAnsicht;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        seitenAnsicht = (TabLayout)findViewById(R.id.seitenAnsicht);
        seitenAnsicht.addTab(seitenAnsicht.newTab().setText("Mo"));
        seitenAnsicht.addTab(seitenAnsicht.newTab().setText("Di"));
        seitenAnsicht.addTab(seitenAnsicht.newTab().setText("Mi"));
        seitenAnsicht.addTab(seitenAnsicht.newTab().setText("Do"));
        seitenAnsicht.addTab(seitenAnsicht.newTab().setText("Fr"));
        seitenAnsicht.setTabGravity(TabLayout.GRAVITY_FILL);


        final ViewPager viewpager = (ViewPager)findViewById(R.id.pager);
        final PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager(), seitenAnsicht.getTabCount());
        viewpager.setAdapter(pagerAdapter);

        viewpager.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(seitenAnsicht));

        seitenAnsicht.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewpager.setCurrentItem(tab.getPosition());

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        int id2 = R.id.menu_1;
        if (id == id2) {
            openActivitySettings();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void openActivitySettings() {
        Intent intent = new Intent(this, Activity_Settings.class);
        startActivity(intent);
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }



}
