package anandniketan.com.shilajadmin.Fragment;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import anandniketan.com.shilajadmin.Adapter.StandardwiseStudentAttendaceAdapter;
import anandniketan.com.shilajadmin.Model.Staff.StaffAttendaceModel;
import anandniketan.com.shilajadmin.Model.Student.StudentAttendanceModel;
import anandniketan.com.shilajadmin.R;
import anandniketan.com.shilajadmin.Utility.ApiHandler;
import anandniketan.com.shilajadmin.Utility.Utils;
import anandniketan.com.shilajadmin.databinding.FragmentAttendaceSummaryBinding;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class AttendaceSummaryFragment extends Fragment {

    public AttendaceSummaryFragment() {
    }

    private FragmentAttendaceSummaryBinding fragmentAttendaceSummaryBinding;
    private View rootView;
    private Context mContext;
    private Fragment fragment = null;
    private FragmentManager fragmentManager = null;
    int Year, Month, Day;
    Calendar calendar;
    private String Datestr;
    private StandardwiseStudentAttendaceAdapter standardwiseStudentAttendaceAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        fragmentAttendaceSummaryBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_attendace_summary, container, false);

        rootView = fragmentAttendaceSummaryBinding.getRoot();
        mContext = getActivity().getApplicationContext();
        initViews();


        return rootView;
    }

    public void initViews() {
        calendar = Calendar.getInstance();
        Year = calendar.get(Calendar.YEAR);
        Month = calendar.get(Calendar.MONTH);
        Day = calendar.get(Calendar.DAY_OF_MONTH);

        Datestr = Utils.getTodaysDate();
        Log.d("TodayDate", Datestr);

        callStudentApi();
        callStaffApi();
    }

    // CALL Staff Attendace API HERE
    private void callStaffApi() {

        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

//        Utils.showDialog(getActivity());
        ApiHandler.getApiService().getStaffAttendace(getStaffDetail(), new retrofit.Callback<StaffAttendaceModel>() {

            @Override
            public void success(StaffAttendaceModel staffUser, Response response) {
//                Utils.dismissDialog();
                if (staffUser == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (staffUser.getSuccess() == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (staffUser.getSuccess().equalsIgnoreCase("False")) {
                    Utils.ping(mContext, getString(R.string.false_msg));
                    return;
                }
                if (staffUser.getSuccess().equalsIgnoreCase("True")) {
                    for (int i = 0; i < staffUser.getFinalArray().size(); i++) {
                        fragmentAttendaceSummaryBinding.absentstaffCountTxt.setText(staffUser.getFinalArray().get(i).getStaffAbsent());
                        fragmentAttendaceSummaryBinding.leavestaffCountTxt.setText(staffUser.getFinalArray().get(i).getStaffLeave());
                        fragmentAttendaceSummaryBinding.presentstaffCountTxt.setText(staffUser.getFinalArray().get(i).getStaffPresent());
                        fragmentAttendaceSummaryBinding.totalstaffCountTxt.setText(staffUser.getFinalArray().get(i).getTotalStaff());
                    }

                }
            }

            @Override
            public void failure(RetrofitError error) {
//                Utils.dismissDialog();
                error.printStackTrace();
                error.getMessage();
                Utils.ping(mContext, getString(R.string.something_wrong));
            }
        });

    }

    private Map<String, String> getStaffDetail() {
        Map<String, String> map = new HashMap<>();
        map.put("Date", Datestr);
        return map;
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
                if (studentUser.getSuccess().equalsIgnoreCase("False")) {
                    Utils.ping(mContext, getString(R.string.false_msg));
                    return;
                }
                if (studentUser.getSuccess().equalsIgnoreCase("True")) {
                    for (int i = 0; i < studentUser.getFinalArray().size(); i++) {
                        fragmentAttendaceSummaryBinding.absentstudentCountTxt.setText(studentUser.getFinalArray().get(i).getStudentAbsent());
                        fragmentAttendaceSummaryBinding.leavestudentCountTxt.setText(studentUser.getFinalArray().get(i).getStudentLeave());
                        fragmentAttendaceSummaryBinding.presentstudentCountTxt.setText(studentUser.getFinalArray().get(i).getStudentPresent());
                        fragmentAttendaceSummaryBinding.totalstudentCountTxt.setText(studentUser.getFinalArray().get(i).getTotalStudent());
                    }
                    if (studentUser.getFinalArray().size() > 0) {
                        fragmentAttendaceSummaryBinding.txtNoRecords.setVisibility(View.GONE);
                        standardwiseStudentAttendaceAdapter = new StandardwiseStudentAttendaceAdapter(mContext, studentUser);
                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                        fragmentAttendaceSummaryBinding.standardwiseStudentAttendacelist.setLayoutManager(mLayoutManager);
                        fragmentAttendaceSummaryBinding.standardwiseStudentAttendacelist.setItemAnimator(new DefaultItemAnimator());
                        fragmentAttendaceSummaryBinding.standardwiseStudentAttendacelist.setAdapter(standardwiseStudentAttendaceAdapter);
                    } else {
                        fragmentAttendaceSummaryBinding.txtNoRecords.setVisibility(View.VISIBLE);
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
