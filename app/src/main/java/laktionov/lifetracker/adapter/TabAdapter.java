package laktionov.lifetracker.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import laktionov.lifetracker.fragment.AchievementFragment;
import laktionov.lifetracker.fragment.WishFragment;

public class TabAdapter extends FragmentPagerAdapter {

    public static final int FRAGMENT_WISHES = 0;
    public static final int FRAGMENT_ACHIEVEMENT = 1;
    public static final int FRAGMENT_COUNT = 2;

    private String[] tabTitles = new String[]{"wishes", "achievements"};
    private WishFragment wishFragment;
    private AchievementFragment achievementFragment;


    public TabAdapter(FragmentManager fm) {
        super(fm);
        wishFragment = new WishFragment();
        achievementFragment = new AchievementFragment();
    }

    public WishFragment getWishFragment() {
        return wishFragment;
    }

    public AchievementFragment getAchievementFragment() {
        return achievementFragment;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        Bundle args;
        switch (position) {
            case FRAGMENT_WISHES:
                fragment = wishFragment;
                args = new Bundle();
                args.putInt("position", position);
                break;
            case FRAGMENT_ACHIEVEMENT:
                fragment = achievementFragment;
                args = new Bundle();
                args.putInt("position", position + 1);
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return FRAGMENT_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }


}
