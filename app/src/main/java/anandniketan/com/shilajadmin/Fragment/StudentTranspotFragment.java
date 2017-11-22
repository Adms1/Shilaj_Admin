package anandniketan.com.shilajadmin.Fragment;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ExpandableListView;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import anandniketan.com.shilajadmin.Adapter.StudentSubMenuAdapter;
import anandniketan.com.shilajadmin.Model.Student.FinalArrayStudentModel;
import anandniketan.com.shilajadmin.Model.Student.StudentAttendanceModel;
import anandniketan.com.shilajadmin.Model.Transport.TermModel;
import anandniketan.com.shilajadmin.R;
import anandniketan.com.shilajadmin.Utility.ApiHandler;
import anandniketan.com.shilajadmin.Utility.AppConfiguration;
import anandniketan.com.shilajadmin.Utility.Utils;
import anandniketan.com.shilajadmin.databinding.FragmentStudentBinding;
import anandniketan.com.shilajadmin.databinding.FragmentStudentTranspotBinding;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class StudentTranspotFragment extends Fragment {

    private FragmentStudentTranspotBinding fragmentStudentTranspotBinding;
    private View rootView;
    private Context mContext;
    private Fragment fragment = null;
    private FragmentManager fragmentManager = null;
    private int lastExpandedPosition = -1;

    public StudentTranspotFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentStudentTranspotBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_student_transpot, container, false);

        rootView = fragmentStudentTranspotBinding.getRoot();
        mContext = getActivity().getApplicationContext();

        initViews();
        setListners();
        callTermApi();
        return rootView;
    }

    public void initViews() {

    }

    public void setListners() {
        fragmentStudentTranspotBinding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = new StudentFragment();
                fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .setCustomAnimations(0, 0)
                        .replace(R.id.frame_container, fragment).commit();
            }
        });
        fragmentStudentTranspotBinding.lvExpStudenttransport.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {


            @Override

            public void onGroupExpand(int groupPosition) {
                if (lastExpandedPosition != -1
                        && groupPosition != lastExpandedPosition) {
                    fragmentStudentTranspotBinding.lvExpStudenttransport.collapseGroup(lastExpandedPosition);
                }
                lastExpandedPosition = groupPosition;
            }

        });
    }


    // CALL Term API HERE
    private void callTermApi() {

        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

        Utils.showDialog(getActivity());
        ApiHandler.getApiService().getTerm(getTermDetail(),new retrofit.Callback<TermModel>() {
            @Override
            public void success(TermModel termModel, Response response) {
                Utils.dismissDialog();
                if (termModel == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (termModel.getSuccess() == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (termModel.getSuccess().equalsIgnoreCase("false")) {
                    Utils.ping(mContext, getString(R.string.false_msg));
                    return;
                }
                if (termModel.getSuccess().equalsIgnoreCase("True")) {


                }
            }

            @Override
            public void failure(RetrofitError error) {
                Utils.dismissDialog();
                error.printStackTrace();
                error.getMessage();
                Utils.ping(mContext, getString(R.string.something_wrong));
            }
        });

    }
    private Map<String, String> getTermDetail() {
        Map<String, String> map = new HashMap<>();

        return map;
    }
}

