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
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.LargeValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

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
    ArrayList<Integer> monthNumber = new ArrayList<>();
    ArrayList<String> monthNameArray = new ArrayList<>();
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
        groupSpace = 0.5f;
        barSpace = 0f;
        barWidth = 0.3f;
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
        fragmentActivityLoggingBinding.barChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                String x = fragmentActivityLoggingBinding.barChart.getXAxis().getValueFormatter().getFormattedValue(e.getX(), fragmentActivityLoggingBinding.barChart.getXAxis());
                Log.d("clickValue", x);

                Calendar cal = Calendar.getInstance();
                try {
                    cal.setTime(new SimpleDateFormat("MMM").parse(x));
                } catch (ParseException e1) {
                    e1.printStackTrace();
                }
                int monthInt = cal.get(Calendar.MONTH) + 1;
                Log.d("Selectedmonth", "" + monthInt);

            }

            @Override
            public void onNothingSelected() {

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
                    fragmentActivityLoggingBinding.totalStudentTxt.setText(String.valueOf(monthlyCount.getTotalStudentCount()));
                    fragmentActivityLoggingBinding.activatedStudentTxt.setText(String.valueOf(monthlyCount.getActiveStudentCount()));
                    fragmentActivityLoggingBinding.totalTeacherTxt.setText(String.valueOf(monthlyCount.getTotalTeacherCount()));
                    fragmentActivityLoggingBinding.activatedTeacherTxt.setText(String.valueOf(monthlyCount.getActiveTeacherCount()));
                    fragmentActivityLoggingBinding.totalAdminTxt.setText(String.valueOf(monthlyCount.getTotalAdminCount()));
                    fragmentActivityLoggingBinding.activatedAdminTxt.setText(String.valueOf(monthlyCount.getActiveadminCount()));

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
        countStudentArray = new ArrayList<>();
        countTeacherArray = new ArrayList<>();
        countAdminArray = new ArrayList<>();

        HashMap<Integer, Integer> student = new HashMap<>();
        HashMap<Integer, Integer> teacher = new HashMap<>();
        HashMap<Integer, Integer> admin = new HashMap<>();

        for (int i = 0; i < monthNumArrayList.size(); i++) {
            monthNumber.add(monthNumArrayList.get(i).getMonth());
            if (monthNumArrayList.get(i).getType().equalsIgnoreCase("Student")) {
                countStudentArray.add(monthNumArrayList.get(i).getCount());
                student.put(monthNumArrayList.get(i).getMonth(), monthNumArrayList.get(i).getCount());
            } else if (monthNumArrayList.get(i).getType().equalsIgnoreCase("Teacher")) {
                countTeacherArray.add(monthNumArrayList.get(i).getCount());
                teacher.put(monthNumArrayList.get(i).getMonth(), monthNumArrayList.get(i).getCount());
            } else if (monthNumArrayList.get(i).getType().equalsIgnoreCase("Admin")) {
                countAdminArray.add(monthNumArrayList.get(i).getCount());
                admin.put(monthNumArrayList.get(i).getMonth(), monthNumArrayList.get(i).getCount());
            }
        }
        HashSet hs = new HashSet();
        hs.addAll(monthNumber);
        monthNumber.clear();
        monthNumber.addAll(hs);
        Log.d("marks", "" + monthNumber);
        Collections.sort(monthNumber);
        for (int j = 0; j < monthNumber.size(); j++) {
            getMonthFun(monthNumber.get(j));
        }
        Map<Integer, Integer> treeMap = new TreeMap<Integer, Integer>(student);
        Map<Integer, Integer> teacherMap = new TreeMap<Integer, Integer>(teacher);
        Map<Integer, Integer> adminMap = new TreeMap<Integer, Integer>(admin);

        fillBarChartValueNew(treeMap, teacherMap,adminMap);
    }

    public void getMonthFun(int month) {
        SimpleDateFormat monthParse = new SimpleDateFormat("MM");
        SimpleDateFormat monthDisplay = new SimpleDateFormat("MMM");
        try {
            monthNameArray.add(monthDisplay.format(monthParse.parse(String.valueOf(month))));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Log.d("month", "" + monthNameArray);
    }

    private void fillBarChartValueNew(Map<Integer, Integer> treeMap, Map<Integer, Integer> teacherMap, Map<Integer, Integer> adminMap) {
        fragmentActivityLoggingBinding.barChart.setDescription(null);
        fragmentActivityLoggingBinding.barChart.setPinchZoom(false);
        fragmentActivityLoggingBinding.barChart.setScaleEnabled(true);
        fragmentActivityLoggingBinding.barChart.setDrawBarShadow(false);
        fragmentActivityLoggingBinding.barChart.setDrawGridBackground(false);
//        fragmentActivityLoggingBinding.barChart.animateY(3000);

        int groupCount = 12;

        ArrayList<String> xVals = new ArrayList<String>();
        for (int i = 0; i < monthNameArray.size(); i++) {
            xVals.add(monthNameArray.get(i));
        }

        ArrayList<Integer> yVals1 = new ArrayList<Integer>();
        ArrayList<Integer> yVals2 = new ArrayList<Integer>();
        ArrayList<Integer> yVals3 = new ArrayList<Integer>();

        ArrayList<BarEntry> entries1 = new ArrayList<BarEntry>();
        ArrayList<BarEntry> entries2 = new ArrayList<BarEntry>();
        ArrayList<BarEntry> entries3 = new ArrayList<BarEntry>();

        for (Map.Entry<Integer,Integer> entry : treeMap.entrySet()) {
            yVals1.add(entry.getValue());
        }

        for (Map.Entry<Integer,Integer> entry : teacherMap.entrySet()) {
            yVals2.add(entry.getValue());
        }

        for (Map.Entry<Integer,Integer> entry : adminMap.entrySet()) {
            yVals3.add(entry.getValue());
        }

        for (int i = 0; i < monthNumber.size(); i++) {
            entries1.add(new BarEntry(monthNumber.get(i), yVals1.get(i)));
            entries2.add(new BarEntry(monthNumber.get(i), yVals2.get(i)));
            entries3.add(new BarEntry(monthNumber.get(i), yVals3.get(i)));
        }


        BarDataSet set1, set2, set3;
        set1 = new BarDataSet(entries1, "Student");
        set1.setColor(getResources().getColor(R.color.darkblue));
        set2 = new BarDataSet(entries2, "Teacher");
        set2.setColor(getResources().getColor(R.color.yellow));
        set3 = new BarDataSet(entries3, "Teacher");
        set3.setColor(getResources().getColor(R.color.orange));

        BarData data = new BarData(set1, set2,set3);
        data.setValueFormatter(new LargeValueFormatter());
        fragmentActivityLoggingBinding.barChart.setData(data);
        Log.d("getBarData", "" + fragmentActivityLoggingBinding.barChart.getBarData());

        fragmentActivityLoggingBinding.barChart.getBarData().setBarWidth(barWidth);
        fragmentActivityLoggingBinding.barChart.getXAxis().setAxisMinimum(0);
        fragmentActivityLoggingBinding.barChart.getXAxis().setAxisMaximum(0 + fragmentActivityLoggingBinding.barChart.getBarData().getGroupWidth(groupSpace, barSpace) * groupCount);
        fragmentActivityLoggingBinding.barChart.groupBars(0, groupSpace, barSpace);
        fragmentActivityLoggingBinding.barChart.invalidate();
        Log.d("value", "" + fragmentActivityLoggingBinding.barChart.getBarData().getGroupWidth(groupSpace, barSpace) * groupCount);
        Legend l = fragmentActivityLoggingBinding.barChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setYOffset(0f);
        l.setXOffset(20f);
        l.setYEntrySpace(10f);
        l.setTextSize(12f);

        //X-axis
        XAxis xAxis = fragmentActivityLoggingBinding.barChart.getXAxis();
        xAxis.setGranularity(1f);
        xAxis.setGranularityEnabled(true);
        xAxis.setCenterAxisLabels(true);
        xAxis.setDrawGridLines(false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(xVals));

        //Y-axis
        fragmentActivityLoggingBinding.barChart.getAxisRight().setEnabled(false);
        YAxis leftAxis = fragmentActivityLoggingBinding.barChart.getAxisLeft();
        leftAxis.setValueFormatter(new LargeValueFormatter());
        leftAxis.setDrawGridLines(true);
        leftAxis.setSpaceTop(5f);
        leftAxis.setAxisMinimum(0f);


    }







//    public void fillBarChartValue() {
//
//        fragmentActivityLoggingBinding.barChart.setDescription(null);
//        fragmentActivityLoggingBinding.barChart.setPinchZoom(false);
//        fragmentActivityLoggingBinding.barChart.setScaleEnabled(true);
//        fragmentActivityLoggingBinding.barChart.setDrawBarShadow(false);
//        fragmentActivityLoggingBinding.barChart.setDrawGridBackground(false);
////        fragmentActivityLoggingBinding.barChart.animateY(3000);
//
//        int groupCount=12;
//
//        ArrayList<String> xVals = new ArrayList<String>();
//        for (int i = 0; i < monthNameArray.size(); i++) {
//            xVals.add(monthNameArray.get(i));
//        }
//        Log.d("x-Axis", "" + xVals);
//
//
//        ArrayList<Integer> yVals1 = new ArrayList<Integer>();
//        ArrayList<Integer> yVals2 = new ArrayList<Integer>();
//        ArrayList<Integer> yVals3 = new ArrayList<Integer>();
//
//        ArrayList<BarEntry> entries1 = new ArrayList<BarEntry>();
//        ArrayList<BarEntry> entries2 = new ArrayList<BarEntry>();
//        ArrayList<BarEntry> entries3 = new ArrayList<BarEntry>();
//
//        for (int j = 0; j < countStudentArray.size(); j++) {
//            yVals1.add(countStudentArray.get(j));
//        }
//        for (int j = 0; j < countTeacherArray.size(); j++) {
//            yVals2.add(countTeacherArray.get(j));
//        }
//        for (int j = 0; j < countAdminArray.size(); j++) {
//            yVals3.add(countAdminArray.get(j));
//        }
//        for (int i = 0; i < monthNumber.size(); i++) {
//            entries1.add(new BarEntry(monthNumber.get(i), yVals1.get(i)));
//            entries2.add(new BarEntry(monthNumber.get(i), yVals2.get(i)));
//            entries3.add(new BarEntry(monthNumber.get(i), yVals3.get(i)));
//        }
//
//        BarDataSet set1, set2, set3;
//        set1 = new BarDataSet(entries1, "Student");
//        set1.setColor(getResources().getColor(R.color.darkblue));
//        set2 = new BarDataSet(entries2, "Teacher");
//        set2.setColor(getResources().getColor(R.color.yellow));
//        set3 = new BarDataSet(entries3, "Admin");
//        set3.setColor(getResources().getColor(R.color.orange));
//
//        BarData data = new BarData(set1, set2, set3);
//        data.setValueFormatter(new LargeValueFormatter());
//        fragmentActivityLoggingBinding.barChart.setData(data);
//        Log.d("getBarData", "" + fragmentActivityLoggingBinding.barChart.getBarData());
//
//        fragmentActivityLoggingBinding.barChart.getBarData().setBarWidth(barWidth);
//        fragmentActivityLoggingBinding.barChart.getXAxis().setAxisMinimum(0);
//        fragmentActivityLoggingBinding.barChart.getXAxis().setAxisMaximum(0 + fragmentActivityLoggingBinding.barChart.getBarData().getGroupWidth(groupSpace, barSpace) * groupCount);
//        fragmentActivityLoggingBinding.barChart.groupBars(0, groupSpace, barSpace);
//        fragmentActivityLoggingBinding.barChart.invalidate();
//        Log.d("value", "" + fragmentActivityLoggingBinding.barChart.getBarData().getGroupWidth(groupSpace, barSpace) * groupCount);
//        Legend l = fragmentActivityLoggingBinding.barChart.getLegend();
//        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
//        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
//        l.setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);
//        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
//        l.setDrawInside(false);
//        l.setYOffset(0f);
//        l.setXOffset(20f);
//        l.setYEntrySpace(10f);
//        l.setTextSize(12f);
//
//        //X-axis
//        XAxis xAxis = fragmentActivityLoggingBinding.barChart.getXAxis();
//        xAxis.setGranularity(1f);
//        xAxis.setGranularityEnabled(true);
//        xAxis.setCenterAxisLabels(true);
//        xAxis.setDrawGridLines(false);
//        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
//        xAxis.setValueFormatter(new IndexAxisValueFormatter(xVals));
//
//        //Y-axis
//        fragmentActivityLoggingBinding.barChart.getAxisRight().setEnabled(false);
//        YAxis leftAxis = fragmentActivityLoggingBinding.barChart.getAxisLeft();
//        leftAxis.setValueFormatter(new LargeValueFormatter());
//        leftAxis.setDrawGridLines(true);
//        leftAxis.setSpaceTop(5f);
//        leftAxis.setAxisMinimum(0f);
//    }
}

