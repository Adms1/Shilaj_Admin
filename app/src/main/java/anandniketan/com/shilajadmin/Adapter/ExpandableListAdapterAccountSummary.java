package anandniketan.com.shilajadmin.Adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import anandniketan.com.shilajadmin.Model.Account.FinalArrayPaymentLedgerModel;
import anandniketan.com.shilajadmin.R;
import anandniketan.com.shilajadmin.databinding.ListGroupFeesStructureDetailBinding;
import anandniketan.com.shilajadmin.databinding.ListItemAccountSummaryBinding;

/**
 * Created by admsandroid on 11/27/2017.
 */

public class ExpandableListAdapterAccountSummary extends BaseExpandableListAdapter {

    private Context _context;
    private List<String> _listDataHeader;
    private HashMap<String, ArrayList<FinalArrayPaymentLedgerModel>> _listDataChild;

    public ExpandableListAdapterAccountSummary(Context _context, List<String> listDataHeader,
                                               HashMap<String, ArrayList<FinalArrayPaymentLedgerModel>> listDataChild) {
        this._context = _context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listDataChild;
    }


    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        ListItemAccountSummaryBinding itembinding;
        ArrayList<FinalArrayPaymentLedgerModel> detail = getChild(groupPosition, 0);
        if (convertView == null) {

        }
        itembinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.list_item_account_summary, parent, false);
        convertView = itembinding.getRoot();
        itembinding.previousBalTxt.setText("₹" + " " + detail.get(childPosition).getPreviousBalance());
        itembinding.tutionfeesTxt.setText("₹" + " " + detail.get(childPosition).getTutionFees());
        itembinding.admissionTxt.setText("₹" + " " + detail.get(childPosition).getAdmissionFees());
        itembinding.cautionTxt.setText("₹" + " " + detail.get(childPosition).getCautionFees());
        itembinding.transportTxt.setText("₹" + " " + detail.get(childPosition).getTransportFees());
        itembinding.imprestTxt.setText("₹" + " " + detail.get(childPosition).getImprest());
        itembinding.lateTxt.setText("₹" + " " + detail.get(childPosition).getTermLateFee());
        itembinding.discountTxt.setText("₹" + " " + detail.get(childPosition).getTermDiscount());
        itembinding.totalPayTxt.setText("₹" + " " + detail.get(childPosition).getTermDuePay());
        itembinding.paidfeesTxt.setText("₹" + " " + detail.get(childPosition).getTermPaid());
        itembinding.balnceTxt.setText("₹" + " " + detail.get(childPosition).getTermTotal());
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }

    @Override
    public ArrayList<FinalArrayPaymentLedgerModel> getChild(int groupPosition, int childPosititon) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition));
    }

    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        ListGroupFeesStructureDetailBinding groupbinding;
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {

        }
        groupbinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.list_group_fees_structure_detail, parent, false);
        convertView = groupbinding.getRoot();

        groupbinding.standardNameTxt.setText(headerTitle);
        groupbinding.standardNameTxt.setTextColor(_context.getResources().getColor(R.color.scheduleheadertextcolor));
        if (isExpanded) {
            groupbinding.arrowImg.setImageResource(R.drawable.arrow_1_42_down);
        } else {
            groupbinding.arrowImg.setImageResource(R.drawable.arrow_1_42);
        }

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}

