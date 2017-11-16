package anandniketan.com.shilajadmin.Fragment;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import anandniketan.com.shilajadmin.R;
import anandniketan.com.shilajadmin.databinding.FragmentStudentBinding;

public class StudentFragment extends Fragment {

    private FragmentStudentBinding fragmentStudentBinding;
    private View rootView;
    private Context mContext;
    private Fragment fragment = null;
    private FragmentManager fragmentManager = null;

    public StudentFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentStudentBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_student, container, false);

        rootView = fragmentStudentBinding.getRoot();
        mContext = getActivity().getApplicationContext();

        initViews();
        setListners();
        return rootView;
    }

    public void initViews() {
    }

    public void setListners() {
        fragmentStudentBinding.btnmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
}

