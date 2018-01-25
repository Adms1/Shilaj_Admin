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
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import anandniketan.com.shilajadmin.Adapter.ExpandableListAdapterStudentFullDetail;
import anandniketan.com.shilajadmin.Model.Student.FinalArrayStudentFullDetailModel;
import anandniketan.com.shilajadmin.Model.Student.StudentFullDetailModel;
import anandniketan.com.shilajadmin.R;
import anandniketan.com.shilajadmin.Utility.ApiHandler;
import anandniketan.com.shilajadmin.Utility.AppConfiguration;
import anandniketan.com.shilajadmin.Utility.Utils;
import anandniketan.com.shilajadmin.databinding.FragmentAllDepartmentDetailsBinding;
import anandniketan.com.shilajadmin.databinding.FragmentGrnoStudentDetailBinding;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class GRNoStudentDetailFragment extends Fragment {

    private FragmentGrnoStudentDetailBinding fragmentGrnoStudentDetailBinding;
    private View rootView;
    private Context mContext;
    private Fragment fragment = null;
    private FragmentManager fragmentManager = null;
    List<FinalArrayStudentFullDetailModel> studentFullDetailArray;
    List<String> listDataHeader;

    HashMap<String, ArrayList<FinalArrayStudentFullDetailModel>> listDataChild;

    ExpandableListAdapterStudentFullDetail listAdapterStudentFullDetail;

    private int lastExpandedPosition = -1;

    public GRNoStudentDetailFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentGrnoStudentDetailBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_grno_student_detail, container, false);

        rootView = fragmentGrnoStudentDetailBinding.getRoot();
        mContext = getActivity().getApplicationContext();

        initViews();
        setListners();
        callStaffApi();
        return rootView;
    }

    public void initViews() {

    }

    public void setListners() {
        fragmentGrnoStudentDetailBinding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = new SearchStudentFragment();
                fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .setCustomAnimations(0, 0)
                        .replace(R.id.frame_container, fragment).commit();
            }
        });
        fragmentGrnoStudentDetailBinding.lvExpStudentDetail.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {


            @Override

            public void onGroupExpand(int groupPosition) {
                if (lastExpandedPosition != -1
                        && groupPosition != lastExpandedPosition) {
                    fragmentGrnoStudentDetailBinding.lvExpStudentDetail.collapseGroup(lastExpandedPosition);
                }
                lastExpandedPosition = groupPosition;
            }

        });


    }

    // CALL Stuednt Full Detail API HERE
    private void callStaffApi() {

        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

//        Utils.showDialog(getActivity());
        ApiHandler.getApiService().getStudentFullDetail(getStudentFullDetail(), new retrofit.Callback<StudentFullDetailModel>() {

            @Override
            public void success(StudentFullDetailModel studentFullDetailModel, Response response) {
//                Utils.dismissDialog();
                if (studentFullDetailModel == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (studentFullDetailModel.getSuccess() == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (studentFullDetailModel.getSuccess().equalsIgnoreCase("False")) {
                    Utils.ping(mContext, getString(R.string.false_msg));
                    return;
                }
                if (studentFullDetailModel.getSuccess().equalsIgnoreCase("True")) {
                    studentFullDetailArray = studentFullDetailModel.getFinalArray();
                    if (studentFullDetailArray != null) {
                        ArrayList<String> arraystu = new ArrayList<String>();
                        arraystu.add("Student Details");
                        arraystu.add("Transport Details");
                        arraystu.add("Father Details");
                        arraystu.add("Mother Details");
                        arraystu.add("Communication Details");
                        Log.d("array", "" + arraystu);

                        listDataHeader = new ArrayList<>();
                        listDataChild = new HashMap<String, ArrayList<FinalArrayStudentFullDetailModel>>();

                        for (int i = 0; i < arraystu.size(); i++) {
                            Log.d("arraystu", "" + arraystu);
                            listDataHeader.add(arraystu.get(i));
                            Log.d("header", "" + listDataHeader);
                            ArrayList<FinalArrayStudentFullDetailModel> row = new ArrayList<FinalArrayStudentFullDetailModel>();
                            for (int j = 0; j < studentFullDetailArray.size(); j++) {
                                row.add(studentFullDetailArray.get(j));
                            }
                            Log.d("row", "" + row);
                            listDataChild.put(listDataHeader.get(i), row);
                            Log.d("child", "" + listDataChild);
                        }
                        listAdapterStudentFullDetail = new ExpandableListAdapterStudentFullDetail(getActivity(), listDataHeader, listDataChild);
                        fragmentGrnoStudentDetailBinding.lvExpStudentDetail.setAdapter(listAdapterStudentFullDetail);
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

    private Map<String, String> getStudentFullDetail() {
        Map<String, String> map = new HashMap<>();
        map.put("StudentID", AppConfiguration.StudentId);
        return map;
    }
}

