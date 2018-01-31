package anandniketan.com.shilajadmin.Fragment;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.bumptech.glide.Glide;

import anandniketan.com.shilajadmin.Adapter.OtherSubMenuAdapter;
import anandniketan.com.shilajadmin.R;
import anandniketan.com.shilajadmin.Utility.AppConfiguration;
import anandniketan.com.shilajadmin.databinding.FragmentOtherBinding;


public class OtherFragment extends Fragment {

    private FragmentOtherBinding fragmentOtherBinding;
    private View rootView;
    private Context mContext;
    private Fragment fragment = null;
    private FragmentManager fragmentManager = null;

    public OtherFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentOtherBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_other, container, false);

        rootView = fragmentOtherBinding.getRoot();
        mContext = getActivity().getApplicationContext();

        initViews();
        setListners();
        return rootView;
    }

    public void initViews() {
        Glide.with(mContext)
                .load(AppConfiguration.BASEURL_IMAGES + "Other/" + "other_inside.png")
                .fitCenter()
                .into(fragmentOtherBinding.circleImageView);
        fragmentOtherBinding.otherSubmenuGridView.setAdapter(new OtherSubMenuAdapter(mContext));

    }

    public void setListners() {
        fragmentOtherBinding.btnBackother.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new HomeFragment();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .setCustomAnimations(0, 0)
                        .replace(R.id.frame_container, fragment).commit();
            }
        });

        fragmentOtherBinding.otherSubmenuGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    fragment = new StudentAbsentFragment();
                    fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(0, 0)
                            .replace(R.id.frame_container, fragment).commit();
                } else if (position == 1) {
                    fragment = new BullkSmsFragment();
                    fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(0, 0)
                            .replace(R.id.frame_container, fragment).commit();
                } else if (position == 2) {
                    fragment = new SingleSmsFragment();
                    fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(0, 0)
                            .replace(R.id.frame_container, fragment).commit();
                } else if (position == 3) {
                    fragment = new EmployeeSmsFragment();
                    fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(0, 0)
                            .replace(R.id.frame_container, fragment).commit();
                }else if(position==4){
                    fragment = new SummaryFragment();
                    fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(0, 0)
                            .replace(R.id.frame_container, fragment).commit();
                }
//                else if(position==5){
//                    fragment = new MenuPermissionFragment();
//                    fragmentManager = getFragmentManager();
//                    fragmentManager.beginTransaction()
//                            .setCustomAnimations(0, 0)
//                            .replace(R.id.frame_container, fragment).commit();
//                }else if(position==6){
//                    fragment = new MenuPermissionFragment();
//                    fragmentManager = getFragmentManager();
//                    fragmentManager.beginTransaction()
//                            .setCustomAnimations(0, 0)
//                            .replace(R.id.frame_container, fragment).commit();
//                }else if(position==7){
//                    fragment = new MenuPermissionFragment();
//                    fragmentManager = getFragmentManager();
//                    fragmentManager.beginTransaction()
//                            .setCustomAnimations(0, 0)
//                            .replace(R.id.frame_container, fragment).commit();
//                }
                else if (position == 8) {
                    fragment = new AnnouncementFragment();
                    fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(0, 0)
                            .replace(R.id.frame_container, fragment).commit();
                }
//                else if(position==9){
//                    fragment = new MenuPermissionFragment();
//                    fragmentManager = getFragmentManager();
//                    fragmentManager.beginTransaction()
//                            .setCustomAnimations(0, 0)
//                            .replace(R.id.frame_container, fragment).commit();
//                }
            }
        });

    }
}

