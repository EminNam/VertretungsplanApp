package com.example.vertretungsplan;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class PagerAdapter extends FragmentPagerAdapter
{
    private int anzahlSeiten;

    public PagerAdapter(FragmentManager fragmentManager, int pAnzahlSeiten)
    {
        super(fragmentManager);
        this.anzahlSeiten = pAnzahlSeiten;
    }
    @Override
    public Fragment getItem(int i) {
        switch(i)
        {
            case 0:
                Montag montag = new Montag();
                return montag;
            case 1:
                Dienstag dienstag = new Dienstag();
                return dienstag;
            case 2:
                Mittwoch mittwoch = new Mittwoch();
                return mittwoch;
            case 3:
                Donnerstag donnerstag = new Donnerstag();
                return donnerstag;
            case 4:
                Freitag freitag = new Freitag();
                return freitag;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return anzahlSeiten;
    }
}
