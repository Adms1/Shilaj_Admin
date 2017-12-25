package anandniketan.com.shilajadmin.Fragment;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.Spinner;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import anandniketan.com.shilajadmin.Adapter.FeesStructureExpandableListAdapter;
import anandniketan.com.shilajadmin.Adapter.TransportChargesListAdapter;
import anandniketan.com.shilajadmin.Model.Account.AccountFeesStructureModel;
import anandniketan.com.shilajadmin.Model.Account.FinalArrayFeesStructureModel;
import anandniketan.com.shilajadmin.Model.Student.FinalArrayStudentTransportModel;
import anandniketan.com.shilajadmin.Model.Transport.FinalArrayGetTermModel;
import anandniketan.com.shilajadmin.Model.Transport.TermModel;
import anandniketan.com.shilajadmin.R;
import anandniketan.com.shilajadmin.Utility.ApiHandler;
import anandniketan.com.shilajadmin.Utility.Utils;
import anandniketan.com.shilajadmin.databinding.FragmentFeeStructureBinding;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class FeeStructureFragment extends Fragment {

    public FeeStructureFragment() {
    }

    private FragmentFeeStructureBinding fragmentFeeStructureBinding;
    private View rootView;
    private Context mContext;
    private Fragment fragment = null;
    private FragmentManager fragmentManager = null;
    List<FinalArrayGetTermModel> finalArrayGetTermModels;
    HashMap<Integer, String> spinnerTermMap;
    String FinalTermIdStr;
    List<FinalArrayFeesStructureModel> finalArrayFeesStructureModelList;
    FeesStructureExpandableListAdapter feesStructureExpandableListAdapter;
    List<String> listDataHeader;
    HashMap<String, ArrayList<FinalArrayFeesStructureModel>> listDataChild;
    private int lastExpandedPosition = -1;
    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        fragmentFeeStructureBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_fee_structure, container, false);

        rootView = fragmentFeeStructureBinding.getRoot();
        mContext = getActivity().getApplicationContext();

        callTermApi();
        setListner();


        return rootView;
    }


    public void setListner() {
        fragmentFeeStructureBinding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = new AccountFragment();
                fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .setCustomAnimations(0, 0)
                        .replace(R.id.frame_container, fragment).commit();
            }
        });
        fragmentFeeStructureBinding.termSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String name = fragmentFeeStructureBinding.termSpinner.getSelectedItem().toString();
                String getid = spinnerTermMap.get(fragmentFeeStructureBinding.termSpinner.getSelectedItemPosition());

                Log.d("value", name + " " + getid);
                FinalTermIdStr = getid.toString();
                Log.d("FinalTermIdStr", FinalTermIdStr);
                callAccountFeesStructureApi();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        fragmentFeeStructureBinding.lvExpfeesstructure.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {


            @Override

            public void onGroupExpand(int groupPosition) {
                if (lastExpandedPosition != -1
                        && groupPosition != lastExpandedPosition) {
                    fragmentFeeStructureBinding.lvExpfeesstructure.collapseGroup(lastExpandedPosition);
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
            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(fragmentFeeStructureBinding.termSpinner);

            popupWindow.setHeight(spinnertermIdArray.length > 4 ? 500 : spinnertermIdArray.length * 100);
        } catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
            // silently fail...
        }

        ArrayAdapter<String> adapterTerm = new ArrayAdapter<String>(mContext, R.layout.spinner_layout, spinnertermIdArray);
        fragmentFeeStructureBinding.termSpinner.setAdapter(adapterTerm);

    }

    // CALL AccountFeesStructure API HERE
    private void callAccountFeesStructureApi() {

        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }
//        Utils.showDialog(getActivity());
        ApiHandler.getApiService().getAccountFeesStructureDetail(geFeesStructureDetail(), new retrofit.Callback<AccountFeesStructureModel>() {
            @Override
            public void success(AccountFeesStructureModel accountFeesStructureModel, Response response) {
//                Utils.dismissDialog();
                if (accountFeesStructureModel == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (accountFeesStructureModel.getSuccess() == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (accountFeesStructureModel.getSuccess().equalsIgnoreCase("False")) {
                    Utils.ping(mContext, getString(R.string.false_msg));
                    if (accountFeesStructureModel.getFinalArray().size() == 0) {
                        fragmentFeeStructureBinding.lvExpfeesstructure.setVisibility(View.GONE);
                        fragmentFeeStructureBinding.txtNoRecords.setVisibility(View.VISIBLE);
                        fragmentFeeStructureBinding.expHeader.setVisibility(View.GONE);
                    }
                    return;
                }
                if (accountFeesStructureModel.getSuccess().equalsIgnoreCase("True")) {
                    finalArrayFeesStructureModelList = accountFeesStructureModel.getFinalArray();
                    if (finalArrayFeesStructureModelList.size() > 0) {
                        fragmentFeeStructureBinding.lvExpfeesstructure.setVisibility(View.VISIBLE);
                        fragmentFeeStructureBinding.txtNoRecords.setVisibility(View.GONE);
                        fragmentFeeStructureBinding.expHeader.setVisibility(View.VISIBLE);
                        fillExpLV();
                        feesStructureExpandableListAdapter = new FeesStructureExpandableListAdapter(getActivity(), listDataHeader, listDataChild);
                        fragmentFeeStructureBinding.lvExpfeesstructure.setAdapter(feesStructureExpandableListAdapter);
                        Utils.dismissDialog();
                    } else {
                        fragmentFeeStructureBinding.txtNoRecords.setVisibility(View.VISIBLE);
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

    private Map<String, String> geFeesStructureDetail() {
        Map<String, String> map = new HashMap<>();
        map.put("Term_ID", FinalTermIdStr);
        return map;
    }


    public void fillExpLV() {
        listDataHeader = new ArrayList<>();
        listDataChild = new HashMap<String, ArrayList<FinalArrayFeesStructureModel>>();

        for (int i = 0; i < finalArrayFeesStructureModelList.size(); i++) {
            listDataHeader.add(finalArrayFeesStructureModelList.get(i).getStandard());
            Log.d("header", "" + listDataHeader);
            ArrayList<FinalArrayFeesStructureModel> row = new ArrayList<FinalArrayFeesStructureModel>();

            row.add(finalArrayFeesStructureModelList.get(i));
            Log.d("row", "" + row);
            listDataChild.put(listDataHeader.get(i), row);
            Log.d("child", "" + listDataChild);
        }

    }
}

