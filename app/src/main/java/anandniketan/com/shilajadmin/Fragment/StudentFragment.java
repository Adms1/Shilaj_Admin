package anandniketan.com.shilajadmin.Fragment;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import anandniketan.com.shilajadmin.Adapter.StudentSubMenuAdapter;
import anandniketan.com.shilajadmin.Model.Student.FinalArrayStudentModel;
import anandniketan.com.shilajadmin.Model.Student.StudentAttendanceModel;
import anandniketan.com.shilajadmin.R;
import anandniketan.com.shilajadmin.Utility.ApiHandler;
import anandniketan.com.shilajadmin.Utility.Utils;
import anandniketan.com.shilajadmin.databinding.FragmentStudentBinding;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class StudentFragment extends Fragment {

    private FragmentStudentBinding fragmentStudentBinding;
    private View rootView;
    private Context mContext;
    private Fragment fragment = null;
    private FragmentManager fragmentManager = null;
    int Year, Month, Day;
    Calendar calendar;
    private String Datestr;

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
        calendar = Calendar.getInstance();
        Year = calendar.get(Calendar.YEAR);
        Month = calendar.get(Calendar.MONTH);
        Day = calendar.get(Calendar.DAY_OF_MONTH);

        Datestr = Utils.getTodaysDate();
        Log.d("TodayDate", Datestr);
        fragmentStudentBinding.studentSubmenuGridView.setAdapter(new StudentSubMenuAdapter(mContext));
        callStudentApi();
    }

    public void setListners() {
        fragmentStudentBinding.btnBackAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = new HomeFragment();
                fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .setCustomAnimations(0, 0)
                        .replace(R.id.frame_container, fragment).commit();
            }
        });
        fragmentStudentBinding.viewSummayTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = new AttendaceSummaryFragment();
                fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .setCustomAnimations(0, 0)
                        .replace(R.id.frame_container, fragment).commit();
            }
        });
        fragmentStudentBinding.studentSubmenuGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    fragment = new SearchStudentFragment();
                    fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(0, 0)
                            .replace(R.id.frame_container, fragment).commit();
                }
            }
        });
    }


    // CALL Student Attendace API HERE
    private void callStudentApi() {

        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

        Utils.showDialog(getActivity());
        ApiHandler.getApiService().getStudentAttendace(getStudentDetail(), new retrofit.Callback<StudentAttendanceModel>() {
            @Override
            public void success(StudentAttendanceModel studentUser, Response response) {
                Utils.dismissDialog();
                if (studentUser == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (studentUser.getSuccess() == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (studentUser.getSuccess().equalsIgnoreCase("false")) {
                    Utils.ping(mContext, getString(R.string.false_msg));
                    return;
                }
                if (studentUser.getSuccess().equalsIgnoreCase("True")) {
                    List<FinalArrayStudentModel> studentArray = studentUser.getFinalArray();
                    for (int i = 0; i < studentArray.size(); i++) {
                        FinalArrayStudentModel studentObj = studentArray.get(i);
                        if (studentObj != null) {
                            fragmentStudentBinding.totalStudentCount.setText(studentObj.getTotalStudent());
                            fragmentStudentBinding.totalPresentstudentCount.setText(studentObj.getStudentPresent());
                            fragmentStudentBinding.totalAbsentstudentCount.setText(studentObj.getStudentAbsent());
                            fragmentStudentBinding.totalLeavestudentCount.setText(studentObj.getStudentLeave());
                        }
                    }

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

    private Map<String, String> getStudentDetail() {
        Map<String, String> map = new HashMap<>();
        map.put("Date", Datestr);
        return map;
    }


}

