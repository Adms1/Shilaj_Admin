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

import anandniketan.com.shilajadmin.Activity.DashboardActivity;
import anandniketan.com.shilajadmin.Adapter.ExpandableListAdapterGRstudentdetail;
import anandniketan.com.shilajadmin.Adapter.ExpandableListAdapterInquiryDetail;
import anandniketan.com.shilajadmin.Model.HR.InsertMenuPermissionModel;
import anandniketan.com.shilajadmin.Model.Student.FinalArrayStudentModel;
import anandniketan.com.shilajadmin.Model.Student.StudentAttendanceModel;
import anandniketan.com.shilajadmin.R;
import anandniketan.com.shilajadmin.Utility.ApiHandler;
import anandniketan.com.shilajadmin.Utility.AppConfiguration;
import anandniketan.com.shilajadmin.Utility.Utils;
import anandniketan.com.shilajadmin.databinding.FragmentInquiryProfileDetailBinding;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class InquiryProfileDetailFragment extends Fragment {

    private FragmentInquiryProfileDetailBinding fragmentInquiryProfileDetailBinding;
    private View rootView;
    private Context mContext;
    private Fragment fragment = null;
    private FragmentManager fragmentManager = null;
    List<FinalArrayStudentModel> studentFullDetailArray;
    List<String> listDataHeader;
    String FinalSeletedstudentId;
    HashMap<String, ArrayList<FinalArrayStudentModel>> listDataChild;

    ExpandableListAdapterInquiryDetail expandableListAdapterInquiryDetail;

    private int lastExpandedPosition = -1;

    public InquiryProfileDetailFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentInquiryProfileDetailBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_inquiry_profile_detail, container, false);

        rootView = fragmentInquiryProfileDetailBinding.getRoot();
        mContext = getActivity().getApplicationContext();

        FinalSeletedstudentId = getArguments().getString("StuId");
        initViews();
        setListners();
        callStaffApi();
        return rootView;
    }

    public void initViews() {

    }

    public void setListners() {
        fragmentInquiryProfileDetailBinding.btnmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DashboardActivity.onLeft();
            }
        });
        fragmentInquiryProfileDetailBinding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = new StudentViewInquiryFragment();
                fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                        .replace(R.id.frame_container, fragment).commit();
            }
        });
        fragmentInquiryProfileDetailBinding.lvExpinquriyprofileDetail.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                if (lastExpandedPosition != -1
                        && groupPosition != lastExpandedPosition) {
                    fragmentInquiryProfileDetailBinding.lvExpinquriyprofileDetail.collapseGroup(lastExpandedPosition);
                }
                lastExpandedPosition = groupPosition;
            }

        });
        fragmentInquiryProfileDetailBinding.cancleInquiry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callRejectInquiryApi();
            }
        });
    }

    // CALL Student Full Detail API HERE
    private void callStaffApi() {

        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

        Utils.showDialog(getActivity());
        ApiHandler.getApiService().getInduiryDataByID(getGRRegisterDetail(), new retrofit.Callback<StudentAttendanceModel>() {

            @Override
            public void success(StudentAttendanceModel studentFullDetailModel, Response response) {
//                Utils.dismissDialog();
                if (studentFullDetailModel == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    Utils.dismissDialog();
                    return;
                }
                if (studentFullDetailModel.getSuccess() == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    Utils.dismissDialog();
                    return;
                }
                if (studentFullDetailModel.getSuccess().equalsIgnoreCase("False")) {
                    Utils.ping(mContext, getString(R.string.false_msg));
                    Utils.dismissDialog();
                    if (studentFullDetailModel.getFinalArray().size() == 0) {
                        fragmentInquiryProfileDetailBinding.lvExpinquriyprofileDetail.setVisibility(View.GONE);
                        fragmentInquiryProfileDetailBinding.recyclerLinear.setVisibility(View.GONE);
                        fragmentInquiryProfileDetailBinding.txtNoRecords.setVisibility(View.VISIBLE);
                    }
                    return;
                }
                if (studentFullDetailModel.getSuccess().equalsIgnoreCase("True")) {
                    if (studentFullDetailModel.getFinalArray().size() > 0) {
                        studentFullDetailArray = studentFullDetailModel.getFinalArray();
                        if (studentFullDetailArray != null) {
                            ArrayList<String> arraystu = new ArrayList<String>();
                            arraystu.add("Student Details");
//                          arraystu.add("Transport Details");
                            arraystu.add("Father Details");
                            arraystu.add("Mother Details");
//                            arraystu.add("Communication Details");
                            Log.d("array", "" + arraystu);

                            listDataHeader = new ArrayList<>();
                            listDataChild = new HashMap<String, ArrayList<FinalArrayStudentModel>>();

                            for (int i = 0; i < arraystu.size(); i++) {
                                Log.d("arraystu", "" + arraystu);
                                listDataHeader.add(arraystu.get(i));
                                Log.d("header", "" + listDataHeader);
                                ArrayList<FinalArrayStudentModel> row = new ArrayList<FinalArrayStudentModel>();
                                for (int j = 0; j < studentFullDetailArray.size(); j++) {
                                    row.add(studentFullDetailArray.get(j));
                                }
                                Log.d("row", "" + row);
                                listDataChild.put(listDataHeader.get(i), row);
                                Log.d("child", "" + listDataChild);
                            }
                            expandableListAdapterInquiryDetail = new ExpandableListAdapterInquiryDetail(getActivity(), listDataHeader, listDataChild);
                            fragmentInquiryProfileDetailBinding.lvExpinquriyprofileDetail.setAdapter(expandableListAdapterInquiryDetail);

                            if (!studentFullDetailArray.get(0).getStatus().equalsIgnoreCase("-1")) {
                                fragmentInquiryProfileDetailBinding.cancleInquiry.setVisibility(View.VISIBLE);
                            } else {
                                fragmentInquiryProfileDetailBinding.cancleInquiry.setVisibility(View.GONE);
                            }
                            Utils.dismissDialog();
                        }
                    } else {
                        fragmentInquiryProfileDetailBinding.txtNoRecords.setVisibility(View.VISIBLE);
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

    private Map<String, String> getGRRegisterDetail() {
        Map<String, String> map = new HashMap<>();
        map.put("StudentID", FinalSeletedstudentId);

        return map;
    }

    // CALL RejectInquiry API HERE
    private void callRejectInquiryApi() {

        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

        Utils.showDialog(getActivity());
        ApiHandler.getApiService().RejectInquiry(getRejectInquiryDetail(), new retrofit.Callback<InsertMenuPermissionModel>() {

            @Override
            public void success(InsertMenuPermissionModel rejectInquiryModel, Response response) {
//                Utils.dismissDialog();
                if (rejectInquiryModel == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    Utils.dismissDialog();
                    return;
                }
                if (rejectInquiryModel.getSuccess() == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    Utils.dismissDialog();
                    return;
                }
                if (rejectInquiryModel.getSuccess().equalsIgnoreCase("False")) {
                    Utils.ping(mContext, getString(R.string.false_msg));
                    Utils.dismissDialog();
                    return;
                }
                if (rejectInquiryModel.getSuccess().equalsIgnoreCase("True")) {
                    Utils.ping(mContext, "Delete Sucessfully.");
                    Utils.dismissDialog();
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

    private Map<String, String> getRejectInquiryDetail() {
        Map<String, String> map = new HashMap<>();
        map.put("StudentID", FinalSeletedstudentId);
        return map;
    }
}

