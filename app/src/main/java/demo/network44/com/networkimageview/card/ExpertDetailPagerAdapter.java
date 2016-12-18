package demo.network44.com.networkimageview.card;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class ExpertDetailPagerAdapter extends FragmentPagerAdapter {

    private String userId = "";

    private String[] tabTitles = new String[]{"点师介绍","话题","益答","动态"};


    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }

    public ExpertDetailPagerAdapter(FragmentManager fm, String userId) {
        super(fm);
        this.userId = userId;
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new RececViewFragment();
            case 1:
                return new RececViewFragment();
            case 2:
                return new RececViewFragment();
            case 3:
                return new RececViewFragment();
        }
        return null;

    }
}