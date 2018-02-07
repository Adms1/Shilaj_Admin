package anandniketan.com.shilajadmin.Fragment;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.LargeValueFormatter;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import anandniketan.com.shilajadmin.Activity.DashboardActivity;
import anandniketan.com.shilajadmin.Model.Other.FinalArraySMSDataModel;
import anandniketan.com.shilajadmin.Model.Other.GetStaffSMSDataModel;
import anandniketan.com.shilajadmin.R;
import anandniketan.com.shilajadmin.Utility.ApiHandler;
import anandniketan.com.shilajadmin.Utility.Utils;
import anandniketan.com.shilajadmin.databinding.FragmentActivityLoggingBinding;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class ActivityLoggingFragment extends Fragment {

    private FragmentActivityLoggingBinding fragmentActivityLoggingBinding;
    private View rootView;
    private Context mContext;
    private Fragment fragment = null;
    private FragmentManager fragmentManager = null;
    float groupSpace, barSpace, barWidth;

    //Use for fill Barchart
    List<FinalArraySMSDataModel> monthNumArrayList;
    ArrayList<String> monthNameArray;
    ArrayList<Integer> countStudentArray;
    ArrayList<Integer> countTeacherArray;
    ArrayList<Integer> countAdminArray;

    public ActivityLoggingFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentActivityLoggingBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_activity_logging, container, false);

        rootView = fragmentActivityLoggingBinding.getRoot();
        mContext = getActivity().getApplicationContext();

        init();
        setListners();
        callMonthlyCountApi();

        return rootView;
    }


    public void init() {
        groupSpace = 0.1f;
        barSpace = 0.02f;
        barWidth = 0.43f;
    }

    public void setListners() {

        fragmentActivityLoggingBinding.btnmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DashboardActivity.onLeft();
            }
        });
        fragmentActivityLoggingBinding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = new OtherFragment();
                fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                        .replace(R.id.frame_container, fragment).commit();
            }
        });
    }

    // CALL MonthlyCount API HERE
    private void callMonthlyCountApi() {

        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

        Utils.showDialog(getActivity());
        ApiHandler.getApiService().getMonthlyCount(getMonthlyCountDetail(), new retrofit.Callback<GetStaffSMSDataModel>() {
            @Override
            public void success(GetStaffSMSDataModel monthlyCount, Response response) {
                Utils.dismissDialog();
                if (monthlyCount == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (monthlyCount.getSuccess() == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (monthlyCount.getSuccess().equalsIgnoreCase("false")) {
                    fragmentActivityLoggingBinding.txtNoRecords.setVisibility(View.VISIBLE);
                    fragmentActivityLoggingBinding.listHeader.setVisibility(View.GONE);
                    Utils.dismissDialog();
                    return;
                }
                if (monthlyCount.getSuccess().equalsIgnoreCase("True")) {
                    monthNumArrayList = monthlyCount.getFinalArray();
                    if (monthlyCount.getFinalArray().size() > 0) {
                        fillBarChartArray();
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

    private Map<String, String> getMonthlyCountDetail() {
        Map<String, String> map = new HashMap<>();
        return map;
    }


    public void fillBarChartArray() {
        ArrayList<Integer> monthNumber = new ArrayList<>();
        countStudentArray = new ArrayList<>();
        countTeacherArray = new ArrayList<>();
        countAdminArray = new ArrayList<>();

        for (int i = 0; i < monthNumArrayList.size(); i++) {
            monthNumber.add(monthNumArrayList.get(i).getMonth());
            if (monthNumArrayList.get(i).getType().equalsIgnoreCase("Student")) {
                countStudentArray.add(monthNumArrayList.get(i).getCount());
            } else if (monthNumArrayList.get(i).getType().equalsIgnoreCase("Teacher")) {
                countTeacherArray.add(monthNumArrayList.get(i).getCount());
            } else if (monthNumArrayList.get(i).getType().equalsIgnoreCase("Admin")) {
                countAdminArray.add(monthNumArrayList.get(i).getCount());
            }

            HashSet hs = new HashSet();
            hs.addAll(monthNumber);
            monthNumber.clear();
            monthNumber.addAll(hs);
            Log.d("marks", "" + monthNumber);
            Collections.sort(monthNumber);
            System.out.println("Sorted ArrayList in Java - Ascending order : " + monthNumber);
        }
        for (int j = 0; j < monthNumber.size(); j++) {
            getMonth(monthNumber.get(j));
        }
        fillBarChartValue();
    }

    public void getMonth(int month) {
        monthNameArray = new ArrayList<String>();
        monthNameArray.add(new DateFormatSymbols().getMonths()[month - 1]);
        Log.d("month", "" + monthNameArray);
//
    }


    public void fillBarChartValue() {

        fragmentActivityLoggingBinding.barChart.setDescription(null);
        fragmentActivityLoggingBinding.barChart.setPinchZoom(false);
        fragmentActivityLoggingBinding.barChart.setScaleEnabled(true);
        fragmentActivityLoggingBinding.barChart.setDrawBarShadow(false);
        fragmentActivityLoggingBinding.barChart.setDrawGridBackground(false);

        int groupCount = 3;

        ArrayList xVals = new ArrayList();
        for (int i = 0; i < monthNameArray.size(); i++) {
            xVals.add(monthNameArray.get(i));
        }

        ArrayList yVals1 = new ArrayList();
        ArrayList yVals2 = new ArrayList();
        ArrayList yVals3 = new ArrayList();

        for (int j = 0; j < countStudentArray.size(); j++) {
            yVals1.add(countStudentArray.get(j));
        }
        for (int j = 0; j < countTeacherArray.size(); j++) {
            yVals2.add(countTeacherArray.get(j));
        }
        for (int j = 0; j < countAdminArray.size(); j++) {
            yVals3.add(countAdminArray.get(j));
        }
        Log.d("value Of Array", "yVals1 : " + yVals1 + "yVals2" + yVals2 + "yVals3" + yVals3);

        List<BarEntry> entriesGroup1 = new ArrayList<>();
        List<BarEntry> entriesGroup2 = new ArrayList<>();
        List<BarEntry> entriesGroup3 = new ArrayList<>();


// fill the lists




        BarDataSet set1, set2, set3;
        set1 = new BarDataSet(entriesGroup1, "Student");
        set1.setColor(getResources().getColor(R.color.darkblue));
        set2 = new BarDataSet(entriesGroup2, "Teacher");
        set2.setColor(getResources().getColor(R.color.yellow));
        set3 = new BarDataSet(entriesGroup3, "Admin");
        set3.setColor(getResources().getColor(R.color.absent));

        BarData data = new BarData(set1, set2, set3);
        data.setValueFormatter(new LargeValueFormatter());
        fragmentActivityLoggingBinding.barChart.setData(data);
        fragmentActivityLoggingBinding.barChart.getBarData().setBarWidth(barWidth);
        fragmentActivityLoggingBinding.barChart.getXAxis().setTextSize(12);
        fragmentActivityLoggingBinding.barChart.groupBars(0, groupSpace, barSpace);

//        Legend l = fragmentActivityLoggingBinding.barChart.getLegend();
//        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
//        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
//        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
//        l.setDrawInside(true);
//        l.setYOffset(20f);
//        l.setXOffset(0f);
//        l.setYEntrySpace(0f);
//        l.setTextSize(8f);
    }
}

