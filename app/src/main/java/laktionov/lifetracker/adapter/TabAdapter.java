package laktionov.lifetracker.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import laktionov.lifetracker.fragment.AchievementFragment;
import laktionov.lifetracker.fragment.WishFragment;
import laktionov.lifetracker.utils.GlobalVariables;

public class TabAdapter extends FragmentPagerAdapter {

    private String[] tabTitles = new String[]{"wishes", "achievements"};
    private WishFragment wishFragment;
    private AchievementFragment achievementFragment;


    public TabAdapter(FragmentManager fm) {
        super(fm);
        wishFragment = new WishFragment();
        achievementFragment = new AchievementFragment();
    }

    public AchievementFragment getAchievementFragment() {
        return achievementFragment;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        Bundle args;
        switch (position) {
            case GlobalVariables.FRAGMENT_WISHES:
                fragment = wishFragment;
                args = new Bundle();
                args.putInt("position", position);
                break;
            case GlobalVariables.FRAGMENT_ACHIEVEMENT:
                fragment = achievementFragment;
                args = new Bundle();
                args.putInt("position", position + 1);
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return GlobalVariables.FRAGMENT_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }


}
