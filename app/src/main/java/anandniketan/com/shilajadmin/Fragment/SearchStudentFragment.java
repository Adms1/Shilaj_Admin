package anandniketan.com.shilajadmin.Fragment;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import anandniketan.com.shilajadmin.Adapter.StudentFilteredDataAdapter;
import anandniketan.com.shilajadmin.Interface.onViewClick;
import anandniketan.com.shilajadmin.Model.Student.ParentsNameModel;
import anandniketan.com.shilajadmin.Model.Student.StudentNameModel;
import anandniketan.com.shilajadmin.Model.Student.StudentShowFilteredDataModel;
import anandniketan.com.shilajadmin.R;
import anandniketan.com.shilajadmin.Utility.ApiHandler;
import anandniketan.com.shilajadmin.Utility.Utils;
import anandniketan.com.shilajadmin.databinding.FragmentSearchStudentBinding;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class SearchStudentFragment extends Fragment {

    public SearchStudentFragment() {
    }

    private FragmentSearchStudentBinding fragmentSearchStudentBinding;
    private View rootView;
    private Context mContext;
    private Fragment fragment = null;
    private FragmentManager fragmentManager = null;
    private ArrayList parentName = new ArrayList();
    private ArrayList studentName = new ArrayList();
    private String searchtypeStr, studentNameStr = "", parentNameStr = "", grnoStr = "";
    private StudentFilteredDataAdapter studentFilteredDataAdapter;

    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        fragmentSearchStudentBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_search_student, container, false);

        rootView = fragmentSearchStudentBinding.getRoot();
        mContext = getActivity().getApplicationContext();
        initViews();
        fillsearchType();
        setListner();


        return rootView;
    }

    public void initViews() {


    }

    public void setListner() {
        fragmentSearchStudentBinding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = new StudentFragment();
                fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .setCustomAnimations(0, 0)
                        .replace(R.id.frame_container, fragment).commit();
            }
        });
        fragmentSearchStudentBinding.searchTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                searchtypeStr = parent.getItemAtPosition(position).toString();
                Log.d("searchtypestr", searchtypeStr);
                callParentNameApi();
                callStudentNameApi();
                callStudentShowFilteredDataApi();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        fragmentSearchStudentBinding.parentsnameTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


            }

            @Override
            public void afterTextChanged(Editable s) {
                parentNameStr = fragmentSearchStudentBinding.parentsnameTxt.getText().toString();
//                if (s.length() > 4) {
//                    callParentNameApi();
//                }
            }
        });
        fragmentSearchStudentBinding.studentnameTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


            }

            @Override
            public void afterTextChanged(Editable s) {
                studentNameStr = fragmentSearchStudentBinding.studentnameTxt.getText().toString();
//                if (s.length() > 6) {
//                    callStudentNameApi();
//                }
            }
        });

        fragmentSearchStudentBinding.searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                grnoStr = fragmentSearchStudentBinding.grnoTxt.getText().toString();
                callStudentShowFilteredDataApi();
            }
        });
    }

    public void fillsearchType() {
        try {
            Field popup = Spinner.class.getDeclaredField("mPopup");
            popup.setAccessible(true);

            // Get private mPopup member variable and try cast to ListPopupWindow
            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(fragmentSearchStudentBinding.searchTypeSpinner);

            popupWindow.setHeight(getResources().getStringArray(R.array.SearchStudent).length > 5 ? 500 : getResources().getStringArray(R.array.SearchStudent).length * 100);
        } catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
            // silently fail...
        }

        ArrayAdapter<String> adapterYear = new ArrayAdapter<String>(mContext, R.layout.spinner_layout, getResources().getStringArray(R.array.SearchStudent));
        fragmentSearchStudentBinding.searchTypeSpinner.setAdapter(adapterYear);

    }

    // CALL Parent Name API HERE
    private void callParentNameApi() {

        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

        Utils.showDialog(getActivity());
        ApiHandler.getApiService().getParentName(getParentDetail(), new retrofit.Callback<ParentsNameModel>() {
            @Override
            public void success(ParentsNameModel parentsNameModel, Response response) {
                Utils.dismissDialog();
                if (parentsNameModel == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (parentsNameModel.getSuccess() == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (parentsNameModel.getSuccess().equalsIgnoreCase("False")) {
                    Utils.ping(mContext, getString(R.string.false_msg));
                    parentName.clear();
                    final ArrayAdapter adb = new ArrayAdapter(mContext, R.layout.spinner_layout, parentName);
                    fragmentSearchStudentBinding.parentsnameTxt.setThreshold(1);
                    fragmentSearchStudentBinding.parentsnameTxt.setAdapter(adb);
                    fragmentSearchStudentBinding.studentnameTxt.setText("");
                    fragmentSearchStudentBinding.parentsnameTxt.setText("");
                    fragmentSearchStudentBinding.grnoTxt.setText("");
                    return;
                }
                if (parentsNameModel.getSuccess().equalsIgnoreCase("True")) {
                    for (int i = 0; i < parentsNameModel.getFinalArray().size(); i++) {
                        parentName.add(parentsNameModel.getFinalArray().get(i).getName());
                    }
                    final ArrayAdapter adb = new ArrayAdapter(mContext, R.layout.spinner_layout, parentName);
                    fragmentSearchStudentBinding.parentsnameTxt.setThreshold(1);
                    fragmentSearchStudentBinding.parentsnameTxt.setAdapter(adb);
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

    private Map<String, String> getParentDetail() {
        Map<String, String> map = new HashMap<>();
        map.put("SearchType", searchtypeStr);
        map.put("InputValue", parentNameStr);
        return map;
    }


    // CALL Parent Name API HERE
    private void callStudentNameApi() {

        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

//        Utils.showDialog(getActivity());
        ApiHandler.getApiService().getStudentName(getStudentDetail(), new retrofit.Callback<StudentNameModel>() {
            @Override
            public void success(StudentNameModel studentNameModel, Response response) {
//                Utils.dismissDialog();
                if (studentNameModel == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (studentNameModel.getSuccess() == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (studentNameModel.getSuccess().equalsIgnoreCase("False")) {
                    Utils.ping(mContext, getString(R.string.false_msg));
                    studentName.clear();
                    final ArrayAdapter adb = new ArrayAdapter(mContext, R.layout.spinner_layout, studentName);
                    fragmentSearchStudentBinding.studentnameTxt.setThreshold(1);
                    fragmentSearchStudentBinding.studentnameTxt.setAdapter(adb);
                    fragmentSearchStudentBinding.studentnameTxt.setText("");
                    fragmentSearchStudentBinding.parentsnameTxt.setText("");
                    fragmentSearchStudentBinding.grnoTxt.setText("");
                    return;
                }
                if (studentNameModel.getSuccess().equalsIgnoreCase("True")) {
                    for (int i = 0; i < studentNameModel.getFinalArray().size(); i++) {
                        studentName.add(studentNameModel.getFinalArray().get(i).getName());
                    }
                    final ArrayAdapter adb = new ArrayAdapter(mContext, R.layout.spinner_layout, studentName);
                    fragmentSearchStudentBinding.studentnameTxt.setThreshold(1);
                    fragmentSearchStudentBinding.studentnameTxt.setAdapter(adb);
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

    private Map<String, String> getStudentDetail() {
        Map<String, String> map = new HashMap<>();
        map.put("SearchType", searchtypeStr);
        map.put("InputValue", studentNameStr);
        return map;
    }


    // CALL StudentShowFilteredData API HERE
    private void callStudentShowFilteredDataApi() {

        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

//        Utils.showDialog(getActivity());
        ApiHandler.getApiService().getStudentFilterData(getStudentShowFilteredDataDetail(), new retrofit.Callback<StudentShowFilteredDataModel>() {
            @Override
            public void success(StudentShowFilteredDataModel filteredDataModel, Response response) {
//                Utils.dismissDialog();
                if (filteredDataModel == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (filteredDataModel.getSuccess() == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (filteredDataModel.getSuccess().equalsIgnoreCase("False")) {
                    Utils.ping(mContext, getString(R.string.false_msg));
                    if (filteredDataModel.getFinalArray().size() == 0) {
                       fragmentSearchStudentBinding.studentSearchList.setVisibility(View.GONE);
                        fragmentSearchStudentBinding.recyclerLinear.setVisibility(View.GONE);
                        fragmentSearchStudentBinding.listHeader.setVisibility(View.GONE);
                        fragmentSearchStudentBinding.txtNoRecords.setVisibility(View.VISIBLE);
                    }
                    return;
                }
                if (filteredDataModel.getSuccess().equalsIgnoreCase("True")) {
                    if (filteredDataModel.getFinalArray().size() > 0) {
                        fragmentSearchStudentBinding.txtNoRecords.setVisibility(View.GONE);
                        fragmentSearchStudentBinding.studentSearchList.setVisibility(View.VISIBLE);
                        fragmentSearchStudentBinding.recyclerLinear.setVisibility(View.VISIBLE);
                        fragmentSearchStudentBinding.listHeader.setVisibility(View.VISIBLE);
                        studentFilteredDataAdapter = new StudentFilteredDataAdapter(mContext, filteredDataModel, new onViewClick() {
                            @Override
                            public void getViewClick() {
                                fragment = new AllDepartmentDetailsFragment();
                                fragmentManager = getFragmentManager();
                                fragmentManager.beginTransaction()
                                        .setCustomAnimations(0, 0)
                                        .replace(R.id.frame_container, fragment).commit();
                            }
                        });
                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                        fragmentSearchStudentBinding.studentSearchList.setLayoutManager(mLayoutManager);
                        fragmentSearchStudentBinding.studentSearchList.setItemAnimator(new DefaultItemAnimator());
                        fragmentSearchStudentBinding.studentSearchList.setAdapter(studentFilteredDataAdapter);
                    } else {
                        fragmentSearchStudentBinding.txtNoRecords.setVisibility(View.VISIBLE);
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

    private Map<String, String> getStudentShowFilteredDataDetail() {
        Map<String, String> map = new HashMap<>();
        map.put("SearchType", searchtypeStr);
        map.put("ParentName", parentNameStr);
        map.put("StudentName", studentNameStr);
        map.put("GRNO", grnoStr);
        return map;
    }

}

