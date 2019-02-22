package eu.darken.mvpbakery.example.screens;

import android.content.Context;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

/**
 * Created by darken(darken@darken.eu) on 29.04.2018.
 */
public class PagerAdapter extends FragmentStatePagerAdapter {
    private final List<Page> pages = new ArrayList<>();
    private final Context context;

    public PagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        return createFragment(position);
    }

    private Fragment createFragment(int position) {
        Page page = pages.get(position);
        Fragment fragment = Fragment.instantiate(context, page.getClazz().getName());
        Bundle bundle = new Bundle();
        bundle.putString("identifier", page.getIdentifier());
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getCount() {
        return pages.size();
    }

    public void addPage(Class<? extends Fragment> clazz, boolean notify) {
        pages.add(new Page(clazz, String.valueOf(pages.size())));
        if (notify) notifyDataSetChanged();
    }

    static class Page {
        private final Class<? extends Fragment> clazz;
        private final String identifier;

        Page(Class<? extends Fragment> clazz, String identifier) {
            this.clazz = clazz;
            this.identifier = identifier;
        }

        Class<? extends Fragment> getClazz() {
            return clazz;
        }

        String getIdentifier() {
            return identifier;
        }
    }
}
