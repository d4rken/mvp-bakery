package eu.darken.mvpbakery.example.screens;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

import eu.darken.mvpbakery.example.screens.counting.CountingFragment;
import eu.darken.mvpbakery.example.screens.text.TextFragment;

/**
 * Created by darken(darken@darken.eu) on 29.04.2018.
 */
public class PagerAdapter extends FragmentStatePagerAdapter {
    private final List<Page> pages = new ArrayList<>();

    public PagerAdapter(FragmentManager fm, int startPages) {
        super(fm);
        for (int i = 0; i < startPages; i++) addPage(false);
    }

    @Override
    public Fragment getItem(int position) {
        return createFragment(position);
    }

    Fragment createFragment(int position) {
        Fragment fragment;
        if (position % 2 == 0) {
            fragment = new TextFragment();
        } else {
            fragment = new CountingFragment();
        }
        Bundle bundle = new Bundle();
        bundle.putString("identifier", pages.get(position).getIdentifier());
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getCount() {
        return pages.size();
    }

    public void addPage(boolean notify) {
        pages.add(new Page(String.valueOf(pages.size())));
        if (notify) notifyDataSetChanged();
    }

    static class Page {
        private final String identifier;

        Page(String identifier) {
            this.identifier = identifier;
        }

        String getIdentifier() {
            return identifier;
        }
    }
}
