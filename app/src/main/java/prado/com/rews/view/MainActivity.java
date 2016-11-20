package prado.com.rews.view;

import android.app.DialogFragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.github.stkent.bugshaker.BugShaker;

import net.dean.jraw.RedditClient;

import java.util.ArrayList;
import java.util.List;

import prado.com.rews.BuildConfig;
import prado.com.rews.R;
import prado.com.rews.helper.FragmentListener;
import prado.com.rews.helper.TransferData;
import prado.com.rews.view.fragment.FragmentAccount;
import prado.com.rews.view.fragment.FragmentContent;
import prado.com.rews.view.fragment.FragmentDialog;

public class MainActivity extends AppCompatActivity implements TransferData {

    private int[] tabIcons = {R.drawable.ic_news, R.drawable.ic_person};
    private RedditClient redditClient;
    private Toolbar toolbar;
    private String subReddit;
    private ViewPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setSubReddit("WorldNews");
        getSupportActionBar().setTitle(getSubReddit());

        final ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        setupViewPager(viewPager);

        final TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons(tabLayout);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(final TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        toolbar.setTitle(getSubReddit());
                        toolbar.findViewById(R.id.menu_sort).setVisibility(View.VISIBLE);
                        ((FragmentListener) adapter.getItem(0)).onResumeFragment();
                        ((FragmentListener) adapter.getItem(1)).onPauseFragment();
                        break;
                    case 1:
                        toolbar.setTitle("Profile");
                        toolbar.findViewById(R.id.menu_sort).setVisibility(View.GONE);
                        ((FragmentListener) adapter.getItem(1)).onResumeFragment();
                        ((FragmentListener) adapter.getItem(0)).onPauseFragment();
                        break;
                }
            }

            @Override
            public void onTabUnselected(final TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(final TabLayout.Tab tab) {

            }
        });

        BugShaker.get(getApplication())
                .setEmailAddresses("gaa.prado@hotmail.com")
                .setEmailSubjectLine("Bug Report")
                .setLoggingEnabled(BuildConfig.DEBUG)
                .setIgnoreFlagSecure(true)
                .start();
    }

    private void setupViewPager(ViewPager viewPager) {
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new FragmentContent(), null);
        adapter.addFragment(new FragmentAccount(), null);
        viewPager.setAdapter(adapter);
    }

    private void setupTabIcons(TabLayout tabLayout) {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_sort:
                showDialog();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showDialog() {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        android.app.Fragment prev = getFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        DialogFragment newFragment = FragmentDialog.newInstance();
        newFragment.show(ft, "dialog");
    }

    public String getSubReddit() {return subReddit;}

    public void setSubReddit(String subReddit) {
        this.subReddit = subReddit;
    }

    @Override
    public RedditClient getRedditClient() {
        return redditClient;
    }

    @Override
    public void setRedditClient(final RedditClient redditClient) {
        this.redditClient = redditClient;
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {

        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
