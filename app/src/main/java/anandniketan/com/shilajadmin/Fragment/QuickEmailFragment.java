package anandniketan.com.shilajadmin.Fragment;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;
import java.util.Map;

import anandniketan.com.shilajadmin.Activity.DashboardActivity;
import anandniketan.com.shilajadmin.Model.HR.InsertMenuPermissionModel;
import anandniketan.com.shilajadmin.R;
import anandniketan.com.shilajadmin.Utility.ApiHandler;
import anandniketan.com.shilajadmin.Utility.Utils;
import anandniketan.com.shilajadmin.databinding.FragmentQuickEmailBinding;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class QuickEmailFragment extends Fragment {

    private FragmentQuickEmailBinding fragmentQuickEmailBinding;
    private View rootView;
    private Context mContext;
    private Fragment fragment = null;
    private FragmentManager fragmentManager = null;
    String FinalSMSStr, FinalMobileNoStr;

    public QuickEmailFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentQuickEmailBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_quick_email, container, false);

        rootView = fragmentQuickEmailBinding.getRoot();
        mContext = getActivity().getApplicationContext();

        setListners();


        return rootView;
    }


    public void setListners() {
        fragmentQuickEmailBinding.btnmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DashboardActivity.onLeft();
            }
        });
        fragmentQuickEmailBinding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = new OtherFragment();
                fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                        .replace(R.id.frame_container, fragment).commit();
            }
        });
        fragmentQuickEmailBinding.sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              
            }
        });
        fragmentQuickEmailBinding.clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentQuickEmailBinding.toMultiselectionEdt.setText("");
                fragmentQuickEmailBinding.subjectEdt.setText("");
                fragmentQuickEmailBinding.descriptionEdt.setText("");
            }
        });
    }


//    // CALL InsertSingleSMSData API HERE
//    private void callInsertSingleSMSDataApi() {
//
//        if (!Utils.checkNetwork(mContext)) {
//            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
//            return;
//        }
//
////        Utils.showDialog(getActivity());
//        ApiHandler.getApiService().InsertSingleSMSData(InsertSingleSMSDetail(), new retrofit.Callback<InsertMenuPermissionModel>() {
//            @Override
//            public void success(InsertMenuPermissionModel insertsms, Response response) {
//                Utils.dismissDialog();
//                if (insertsms == null) {
//                    Utils.ping(mContext, getString(R.string.something_wrong));
//                    return;
//                }
//                if (insertsms.getSuccess() == null) {
//                    Utils.ping(mContext, getString(R.string.something_wrong));
//                    return;
//                }
//                if (insertsms.getSuccess().equalsIgnoreCase("false")) {
//                    Utils.ping(mContext, getString(R.string.false_msg));
//                    return;
//                }
//                if (insertsms.getSuccess().equalsIgnoreCase("True")) {
//                    Utils.ping(mContext,"Message Sent Successfully");
//                    fragmentQuickEmailBinding.mobilenoEdt.setText("");
//                    fragmentQuickEmailBinding.messageEdt.setText("");
//                }
//            }
//
//            @Override
//            public void failure(RetrofitError error) {
//                Utils.dismissDialog();
//                error.printStackTrace();
//                error.getMessage();
//                Utils.ping(mContext, getString(R.string.something_wrong));
//            }
//        });
//
//    }
//
//    private Map<String, String> InsertSingleSMSDetail() {
//        Map<String, String> map = new HashMap<>();
//        map.put("SMS", FinalSMSStr);
//        map.put("MobileNo", FinalMobileNoStr);
//        return map;
//    }
}

