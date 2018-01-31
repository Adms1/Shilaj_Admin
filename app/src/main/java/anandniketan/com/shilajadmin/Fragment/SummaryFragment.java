package anandniketan.com.shilajadmin.Fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import anandniketan.com.shilajadmin.Activity.DashboardActivity;
import anandniketan.com.shilajadmin.Adapter.SummarypagerAdapter;
import anandniketan.com.shilajadmin.R;
import anandniketan.com.shilajadmin.databinding.FragmentSummaryBinding;


public class SummaryFragment extends Fragment {

    FragmentSummaryBinding fragmentSummaryBinding;
    private View rootView;
    private Fragment fragment = null;
    private FragmentManager fragmentManager = null;

    private Context mContext;


    public SummaryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentSummaryBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_summary, container, false);

        rootView = fragmentSummaryBinding.getRoot();
        mContext = getActivity().getApplicationContext();
        init();
        setListner();
        return rootView;

    }

    public void init() {
//Initializing the tablayout

        fragmentSummaryBinding.tabLayoutSchedule.addTab(fragmentSummaryBinding.tabLayoutSchedule.newTab().setText("Today Schedule"),true);
        fragmentSummaryBinding.tabLayoutSchedule.addTab(fragmentSummaryBinding.tabLayoutSchedule.newTab().setText(Html.fromHtml("Lesson Plan \n Schedule")));
        fragmentSummaryBinding.tabLayoutSchedule.setTabMode(TabLayout.MODE_FIXED);
        fragmentSummaryBinding.tabLayoutSchedule.setTabGravity(TabLayout.GRAVITY_FILL);


        SummarypagerAdapter adapter = new SummarypagerAdapter(getFragmentManager(), fragmentSummaryBinding.tabLayoutSchedule.getTabCount());
//Adding adapter to pager
        fragmentSummaryBinding.pager.setAdapter(adapter);
//        tabLayout_schedule.setupWithViewPager(viewPager);

        if (android.os.Build.VERSION.SDK_INT >= 21) {
            fragmentSummaryBinding.view.setVisibility(View.GONE);
        } else {
            fragmentSummaryBinding.view.setVisibility(View.VISIBLE);
        }
    }

    public void setListner() {
        fragmentSummaryBinding.btnmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DashboardActivity.onLeft();
            }
        });
        fragmentSummaryBinding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragment = new OtherFragment();
                fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .setCustomAnimations(0, 0)
                        .replace(R.id.frame_container, fragment).commit();
            }
        });

        fragmentSummaryBinding.pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(
                fragmentSummaryBinding.tabLayoutSchedule));
        fragmentSummaryBinding.tabLayoutSchedule.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                fragmentSummaryBinding.pager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}
