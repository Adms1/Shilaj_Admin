package anandniketan.com.shilajadmin.Adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import anandniketan.com.shilajadmin.Model.Account.FinalArrayDailyCollection;
import anandniketan.com.shilajadmin.Model.Staff.FinalArrayAssignSubjectModel;
import anandniketan.com.shilajadmin.R;
import anandniketan.com.shilajadmin.databinding.ListGroupLessonPlanDetailBinding;
import anandniketan.com.shilajadmin.databinding.ListGroupStudentDiscountDetailBinding;
import anandniketan.com.shilajadmin.databinding.ListItemDailyCollectionBinding;
import anandniketan.com.shilajadmin.databinding.ListItemLessonPlanBinding;

/**
 * Created by admsandroid on 2/9/2018.
 */

public class ExpandableListAdapterLessonPlan extends BaseExpandableListAdapter {

    private Context _context;
    private List<String> _listDataHeader;
    private HashMap<String, ArrayList<FinalArrayAssignSubjectModel>> _listDataChild;

    public ExpandableListAdapterLessonPlan(Context context, List<String> listDataHeader, HashMap<String, ArrayList<FinalArrayAssignSubjectModel>> listDataChild) {
         this._context=context;
         this._listDataHeader=listDataHeader;
         this._listDataChild=listDataChild;

    }


    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        ListItemLessonPlanBinding itembinding;
        ArrayList<FinalArrayAssignSubjectModel> detail = getChild(groupPosition, 0);
        if (convertView == null) {

        }
        itembinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.list_item_lesson_plan, parent, false);
        convertView = itembinding.getRoot();

        itembinding.objectiveTxt.setText(detail.get(childPosition).getObjective());
        itembinding.keypointTxt.setText(detail.get(childPosition).getKeyPoint());
        itembinding.assesmentQuestionTxt.setText(detail.get(childPosition).getAssessmentQuestion());

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
    public ArrayList<FinalArrayAssignSubjectModel> getChild(int groupPosition, int childPosititon) {
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
        ListGroupLessonPlanDetailBinding groupbinding;
        String headerTitle = (String) getGroup(groupPosition);
        String[] spiltValue = headerTitle.split("\\|");
        if (convertView == null) {

        }
        groupbinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.list_group_lesson_plan_detail, parent, false);
        convertView = groupbinding.getRoot();

        String sr = String.valueOf(groupPosition + 1);
        groupbinding.indexTxt.setText(sr);
        groupbinding.chapterNoTxt.setText(spiltValue[0]);
        groupbinding.chapterNameTxt.setText(spiltValue[1]);
        Log.d("studentname", spiltValue[0]);
        if (isExpanded) {
            groupbinding.viewTxt.setTextColor(_context.getResources().getColor(R.color.present));
        } else {
            groupbinding.viewTxt.setTextColor(_context.getResources().getColor(R.color.absent));
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





