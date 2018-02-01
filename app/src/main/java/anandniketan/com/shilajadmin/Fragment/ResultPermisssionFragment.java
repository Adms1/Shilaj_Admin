package anandniketan.com.shilajadmin.Fragment;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.ExpandableListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.nostra13.universalimageloader.core.assist.LoadedFrom;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import anandniketan.com.shilajadmin.Activity.DashboardActivity;
import anandniketan.com.shilajadmin.Adapter.ExpandableListAdapterStudentTransportDetail;
import anandniketan.com.shilajadmin.Adapter.ResultPermissionAdapter;
import anandniketan.com.shilajadmin.Adapter.StandardAdapter;
import anandniketan.com.shilajadmin.Interface.getEditpermission;
import anandniketan.com.shilajadmin.Model.Account.FinalArrayStandard;
import anandniketan.com.shilajadmin.Model.Account.GetStandardModel;
import anandniketan.com.shilajadmin.Model.HR.InsertMenuPermissionModel;
import anandniketan.com.shilajadmin.Model.Student.FinalArrayResultPermissionModel;
import anandniketan.com.shilajadmin.Model.Student.FinalArrayStudentTransportModel;
import anandniketan.com.shilajadmin.Model.Student.GetResultPermissionModel;
import anandniketan.com.shilajadmin.Model.Student.StudentTransportDetailModel;
import anandniketan.com.shilajadmin.Model.Transport.FinalArrayGetTermModel;
import anandniketan.com.shilajadmin.Model.Transport.FinalArrayRouteDetailModel;
import anandniketan.com.shilajadmin.Model.Transport.GetRoutePickUpPointDetailModel;
import anandniketan.com.shilajadmin.Model.Transport.PickupPointDetailModel;
import anandniketan.com.shilajadmin.Model.Transport.TermModel;
import anandniketan.com.shilajadmin.R;
import anandniketan.com.shilajadmin.Utility.ApiHandler;
import anandniketan.com.shilajadmin.Utility.Utils;
import anandniketan.com.shilajadmin.databinding.FragmentResultPermisssionBinding;
import anandniketan.com.shilajadmin.databinding.FragmentStudentTranspotBinding;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class ResultPermisssionFragment extends Fragment {

    private FragmentResultPermisssionBinding fragmentResultPermisssionBinding;
    private View rootView;
    private Context mContext;
    private Fragment fragment = null;
    private FragmentManager fragmentManager = null;
    //Use for fill TermSpinner
    List<FinalArrayGetTermModel> finalArrayGetTermModels;
    HashMap<Integer, String> spinnerTermMap;
    //Use for fill List
    List<FinalArrayResultPermissionModel> finalArrayResultPermissionList;
    ResultPermissionAdapter resultPermissionAdapter;

    //Use for fill section
    List<FinalArrayStandard> finalArrayStandardsList;
    StandardAdapter standardAdapter;
    String FinalTermIdStr, FinalGradeIsStr, FinalStatusStr = "1";
    public ResultPermisssionFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentResultPermisssionBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_result_permisssion, container, false);

        rootView = fragmentResultPermisssionBinding.getRoot();
        mContext = getActivity().getApplicationContext();

        setListners();
        callTermApi();
        callStandardApi();

        return rootView;
    }


    public void setListners() {
        fragmentResultPermisssionBinding.btnmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DashboardActivity.onLeft();
            }
        });
        fragmentResultPermisssionBinding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = new StudentPermissionFragment();
                fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                        .replace(R.id.frame_container, fragment).commit();
            }
        });
        fragmentResultPermisssionBinding.termSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String name = fragmentResultPermisssionBinding.termSpinner.getSelectedItem().toString();
                String getid = spinnerTermMap.get(fragmentResultPermisssionBinding.termSpinner.getSelectedItemPosition());

                Log.d("value", name + " " + getid);
                FinalTermIdStr = getid.toString();
                Log.d("FinalTermIdStr", FinalTermIdStr);
                callResultPermission();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        fragmentResultPermisssionBinding.addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFinalIdStr();
                if (!FinalGradeIsStr.equalsIgnoreCase("")) {
                    callInsertResultPermission();
                } else {
                    Utils.ping(mContext, "Please Select Grade.");
                }
            }
        });
        fragmentResultPermisssionBinding.statusGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                RadioButton rb = (RadioButton) radioGroup.findViewById(checkedId);
                if (null != rb && checkedId > -1) {
                    // checkedId is the RadioButton selected
                    switch (checkedId) {
                        case R.id.done_chk:
                            FinalStatusStr = fragmentResultPermisssionBinding.doneChk.getTag().toString();
                            break;
                        case R.id.pendding_chk:
                            FinalStatusStr = fragmentResultPermisssionBinding.penddingChk.getTag().toString();
                            break;
                    }
                }
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
        ApiHandler.getApiService().getTerm(getTermDetail(), new retrofit.Callback<TermModel>() {
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
                    finalArrayGetTermModels = termModel.getFinalArray();
                    if (finalArrayGetTermModels != null) {
                        fillTermSpinner();
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

    private Map<String, String> getTermDetail() {
        Map<String, String> map = new HashMap<>();
        return map;
    }

    public void fillTermSpinner() {
        ArrayList<Integer> TermId = new ArrayList<Integer>();
        for (int i = 0; i < finalArrayGetTermModels.size(); i++) {
            TermId.add(finalArrayGetTermModels.get(i).getTermId());
        }
        ArrayList<String> Term = new ArrayList<String>();
        for (int j = 0; j < finalArrayGetTermModels.size(); j++) {
            Term.add(finalArrayGetTermModels.get(j).getTerm());
        }

        String[] spinnertermIdArray = new String[TermId.size()];

        spinnerTermMap = new HashMap<Integer, String>();
        for (int i = 0; i < TermId.size(); i++) {
            spinnerTermMap.put(i, String.valueOf(TermId.get(i)));
            spinnertermIdArray[i] = Term.get(i).trim();
        }
        try {
            Field popup = Spinner.class.getDeclaredField("mPopup");
            popup.setAccessible(true);

            // Get private mPopup member variable and try cast to ListPopupWindow
            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(fragmentResultPermisssionBinding.termSpinner);

            popupWindow.setHeight(spinnertermIdArray.length > 4 ? 500 : spinnertermIdArray.length * 100);
        } catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
            // silently fail...
        }

        ArrayAdapter<String> adapterTerm = new ArrayAdapter<String>(mContext, R.layout.spinner_layout, spinnertermIdArray);
        fragmentResultPermisssionBinding.termSpinner.setAdapter(adapterTerm);
    }

    // CALL ResultPermission API HERE
    private void callResultPermission() {

        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

        Utils.showDialog(getActivity());
        ApiHandler.getApiService().getResultPermission(getResultPermission(), new retrofit.Callback<GetResultPermissionModel>() {
            @Override
            public void success(GetResultPermissionModel resultPermissionModel, Response response) {
                Utils.dismissDialog();
                if (resultPermissionModel == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (resultPermissionModel.getSuccess() == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (resultPermissionModel.getSuccess().equalsIgnoreCase("false")) {
//                    Utils.ping(mContext, getString(R.string.false_msg));
                    fragmentResultPermisssionBinding.txtNoRecords.setVisibility(View.VISIBLE);
                    fragmentResultPermisssionBinding.lvHeader.setVisibility(View.GONE);
                    fragmentResultPermisssionBinding.recyclerLinear.setVisibility(View.GONE);
                    return;
                }
                if (resultPermissionModel.getSuccess().equalsIgnoreCase("True")) {
                    finalArrayResultPermissionList = resultPermissionModel.getFinalArray();
                    if (resultPermissionModel.getFinalArray().size() > 0) {
                        fragmentResultPermisssionBinding.txtNoRecords.setVisibility(View.GONE);
                        fragmentResultPermisssionBinding.lvHeader.setVisibility(View.VISIBLE);
                        fragmentResultPermisssionBinding.recyclerLinear.setVisibility(View.VISIBLE);
                        resultPermissionAdapter = new ResultPermissionAdapter(mContext, resultPermissionModel, new getEditpermission() {
                            @Override
                            public void getEditpermission() {
                                UpdatePermission();
                            }
                        });
                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                        fragmentResultPermisssionBinding.studentResultPermissionList.setLayoutManager(mLayoutManager);
                        fragmentResultPermisssionBinding.studentResultPermissionList.setItemAnimator(new DefaultItemAnimator());
                        fragmentResultPermisssionBinding.studentResultPermissionList.setAdapter(resultPermissionAdapter);
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

    private Map<String, String> getResultPermission() {
        Map<String, String> map = new HashMap<>();
        map.put("TermId", FinalTermIdStr);
        return map;
    }

    // CALL Standard API HERE
    private void callStandardApi() {

        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

//        Utils.showDialog(getActivity());
        ApiHandler.getApiService().getStandardDetail(getStandardDetail(), new retrofit.Callback<GetStandardModel>() {
            @Override
            public void success(GetStandardModel standardModel, Response response) {
                Utils.dismissDialog();
                if (standardModel == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (standardModel.getSuccess() == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (standardModel.getSuccess().equalsIgnoreCase("false")) {
                    Utils.ping(mContext, getString(R.string.false_msg));
                    return;
                }
                if (standardModel.getSuccess().equalsIgnoreCase("True")) {
                    finalArrayStandardsList = standardModel.getFinalArray();
                    if (finalArrayStandardsList != null) {
                        for (int i = 0; i < finalArrayStandardsList.size(); i++) {
                            finalArrayStandardsList.get(i).setCheckedStatus("0");
                        }
                        standardAdapter = new StandardAdapter(mContext, finalArrayStandardsList);
                        fragmentResultPermisssionBinding.gradeGridView.setAdapter(standardAdapter);
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

    private Map<String, String> getStandardDetail() {
        Map<String, String> map = new HashMap<>();
        return map;
    }

    // CALL InsertResultPermission
    public void callInsertResultPermission() {
        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

        Utils.showDialog(getActivity());
        ApiHandler.getApiService().InsertResultPermission(getInsertResultPermission(), new retrofit.Callback<InsertMenuPermissionModel>() {
            @Override
            public void success(InsertMenuPermissionModel permissionModel, Response response) {
                Utils.dismissDialog();
                if (permissionModel == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (permissionModel.getSuccess() == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (permissionModel.getSuccess().equalsIgnoreCase("false")) {
                    Utils.ping(mContext, getString(R.string.false_msg));
                    return;
                }
                if (permissionModel.getSuccess().equalsIgnoreCase("True")) {
//                    Utils.ping(mContext, getString(R.string.true_msg));
                    callResultPermission();
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

    private Map<String, String> getInsertResultPermission() {
        Map<String, String> map = new HashMap<>();
        map.put("TermID", FinalTermIdStr);
        map.put("GradeID", FinalGradeIsStr);
        map.put("Status", FinalStatusStr);
        return map;
    }

    // Use for get the AllFinalValue for InsertPermission
    public void getFinalIdStr() {
        ArrayList<String> gradeArray = new ArrayList<String>();
        List<FinalArrayStandard> standardArray = standardAdapter.getDatas();
        for (int i = 0; i < standardArray.size(); i++) {
            if (standardArray.get(i).getCheckedStatus().equalsIgnoreCase("1")) {
                gradeArray.add(standardArray.get(i).getStandardID().toString());
            }
        }
        FinalGradeIsStr = String.valueOf(gradeArray);
        FinalGradeIsStr = FinalGradeIsStr.substring(1, FinalGradeIsStr.length() - 1);
        Log.d("FinalGradeIsStr", FinalGradeIsStr);
    }

    // Use For UpdatePermission
    public void UpdatePermission() {
        fragmentResultPermisssionBinding.addBtn.setText("Update");
        ArrayList<String> academicYearArray = new ArrayList<String>();
        String statusArray = "", gradeArray = "";

        for (int k = 0; k < resultPermissionAdapter.getRowValue().size(); k++) {
            String rowValueStr = resultPermissionAdapter.getRowValue().get(k);
            Log.d("rowValueStr", rowValueStr);
            String[] spiltString = rowValueStr.split("\\|");
            academicYearArray.add(spiltString[0]);
            gradeArray = spiltString[1];
            statusArray = spiltString[2];
//            statusArray = statusArray.substring(0, statusArray.length() - 1);

            Log.d("statusArray", statusArray);
        }
        if (statusArray.equalsIgnoreCase(fragmentResultPermisssionBinding.doneChk.getText().toString())) {
            fragmentResultPermisssionBinding.doneChk.setChecked(true);
        } else if (statusArray.equalsIgnoreCase(fragmentResultPermisssionBinding.penddingChk.getText().toString())) {
            fragmentResultPermisssionBinding.penddingChk.setChecked(true);

        }
        List<FinalArrayStandard> standardArray = standardAdapter.getDatas();
        for (int i = 0; i < standardArray.size(); i++) {
            if (gradeArray.equalsIgnoreCase(standardArray.get(i).getStandard())) {
                standardArray.get(i).setCheckedStatus("1");
                standardAdapter.notifyDataSetChanged();
            }
        }

    }
}

