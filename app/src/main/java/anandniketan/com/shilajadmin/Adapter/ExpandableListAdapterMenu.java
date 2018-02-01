package anandniketan.com.shilajadmin.Adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import anandniketan.com.shilajadmin.Model.Staff.Datum;
import anandniketan.com.shilajadmin.R;
import anandniketan.com.shilajadmin.databinding.ListGroupTimetableBinding;

/**
 * Created by admsandroid on 2/1/2018.
 */

public class ExpandableListAdapterMenu extends BaseExpandableListAdapter {

    private Context _context;
    private List<String> _listDataHeader;
    private HashMap<String, ArrayList<String>> _listDataChild;


    public ExpandableListAdapterMenu(Context context, List<String> listDataHeader,
                                     HashMap<String, ArrayList<String>> listDataChild) {
        this._context = context;
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
//        ListItemTimeTableBinding itembinding;
        if (convertView == null) {

            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);


            convertView = infalInflater.inflate(R.layout.list_item_menu, null);
            TextView txtLecture;
            txtLecture = (TextView) convertView.findViewById(R.id.txtLecture);


            txtLecture.setText(getChild(groupPosition,childPosition));

        }
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
    public String getChild(int groupPosition, int childPosititon) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .get(childPosititon);
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
        String headerTitle = (String) getGroup(groupPosition);

        Log.d("headerTitle", headerTitle);
//        String[] h1=headerTitle.split("\\|");
//        Integer url= Integer.valueOf(h1[1]);
        if (convertView == null) {

        }
        LayoutInflater infalInflater = (LayoutInflater) this._context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        convertView = infalInflater.inflate(R.layout.list_group_menu, null);

        TextView lblListHeader = (TextView) convertView.findViewById(R.id.title);
        ImageView image_menu = (ImageView) convertView.findViewById(R.id.image_menu);

        lblListHeader.setText(headerTitle);
//        image_menu.setBackgroundResource(url);

//        if (isExpanded) {
//            groupbinding.lblListHeader.setTextColor(_context.getResources().getColor(R.color.present));
//        } else {
//            groupbinding.lblListHeader.setTextColor(_context.getResources().getColor(R.color.orange));
//        }

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






