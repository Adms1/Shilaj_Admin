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

import anandniketan.com.shilajadmin.Adapter.StaffSubMenuAdapter;
import anandniketan.com.shilajadmin.Adapter.StudentSubMenuAdapter;
import anandniketan.com.shilajadmin.R;
import anandniketan.com.shilajadmin.databinding.FragmentStaffBinding;
import anandniketan.com.shilajadmin.databinding.FragmentStudentBinding;

public class StaffFragment extends Fragment {

    private FragmentStaffBinding fragmentStaffBinding;
    private View rootView;
    private Context mContext;
    private Fragment fragment = null;
    private FragmentManager fragmentManager = null;

    public StaffFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentStaffBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_staff, container, false);

        rootView = fragmentStaffBinding.getRoot();
        mContext = getActivity().getApplicationContext();

        initViews();
        setListners();
        return rootView;
    }

    public void initViews() {
        fragmentStaffBinding.staffSubmenuGridView.setAdapter(new StaffSubMenuAdapter(mContext));

    }

    public void setListners() {
        fragmentStaffBinding.btnBackstaffAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              fragment = new HomeFragment();
                fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .setCustomAnimations(0, 0)
                        .replace(R.id.frame_container, fragment).commit();
            }
        });
        fragmentStaffBinding.staffSubmenuGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 5) {
                    fragment = new TimeTableFragment();
                    fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(0, 0)
                            .replace(R.id.frame_container, fragment).commit();
                }
            }
        });
    }
}

