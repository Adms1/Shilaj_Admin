package anandniketan.com.shilajadmin.Adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import anandniketan.com.shilajadmin.Model.Student.FinalArrayStudentModel;
import anandniketan.com.shilajadmin.R;
import anandniketan.com.shilajadmin.Utility.AppConfiguration;
import anandniketan.com.shilajadmin.databinding.GrStudentListDetailBinding;
import anandniketan.com.shilajadmin.databinding.InquriyStudentListItemDetailBinding;
import anandniketan.com.shilajadmin.databinding.StudentListItemCommunicationDetailBinding;
import anandniketan.com.shilajadmin.databinding.StudentListItemFatherDetailBinding;
import anandniketan.com.shilajadmin.databinding.StudentListItemMotherDetailBinding;
import anandniketan.com.shilajadmin.databinding.StudetnListItemTransportDetailBinding;

/**
 * Created by admsandroid on 2/20/2018.
 */

public class ExpandableListAdapterInquiryDetail extends BaseExpandableListAdapter {

    private Context _context;
    private List<String> _listDataHeader;
    private HashMap<String, ArrayList<FinalArrayStudentModel>> _listDataChild;

    public ExpandableListAdapterInquiryDetail(Context context, List<String> listDataHeader, HashMap<String,
            ArrayList<FinalArrayStudentModel>> listDataChild) {
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
        final ArrayList<FinalArrayStudentModel> childData = getChild(groupPosition, 0);
        InquriyStudentListItemDetailBinding binding;
        if (convertView == null) {
            Log.d("groupposition", "" + groupPosition);
        }
        String headerTitle = (String) getGroup(groupPosition);
        if (headerTitle.equalsIgnoreCase("Student Details")) {
            binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.inquriy_student_list_item_detail, parent, false);
            convertView = binding.getRoot();

            binding.firstnameTxt.setText(childData.get(childPosition).getFirstName());
            binding.middleNameTxt.setText(childData.get(childPosition).getMiddleName());
            binding.lastnameTxt.setText(childData.get(childPosition).getLastName());
            binding.dobTxt.setText(childData.get(childPosition).getDateOfBirth());
            binding.birthplaceTxt.setText(childData.get(childPosition).getBirthPlace());
            binding.stateTxt.setText(childData.get(childPosition).getState());
            binding.genderTxt.setText(childData.get(childPosition).getGender());
            binding.motherTongueTxt.setText(childData.get(childPosition).getMotherTongue());
            binding.nationalityTxt.setText(childData.get(childPosition).getNationality());
            binding.seekingAdmissionTxt.setText(childData.get(childPosition).getSeekingAdmissionForGrade());
            binding.transportRequiredTxt.setText(childData.get(childPosition).getTransportRequired());
            binding.boardTxt.setText(childData.get(childPosition).getBoard());
            binding.siblingsAnandniketanTxt.setText(childData.get(childPosition).getSiblingInAnandNiketanSchool());
            binding.nameSibling1Txt.setText(childData.get(childPosition).getNameOfSibling1());
            binding.gradeOfSibling1Txt.setText(childData.get(childPosition).getSibling1Grade());
            binding.nameOfSibling2Txt.setText(childData.get(childPosition).getNameOfSibling2());
            binding.gradeOfSibling2Txt.setText(childData.get(childPosition).getSibling2Grade());
            binding.addressTxt.setText(childData.get(childPosition).getAddress());
            binding.kmsTxt.setText(childData.get(childPosition).getDistanceFromSchoolInKms());
        }
//        else if (headerTitle.equalsIgnoreCase("Transport Details")) {
//            StudetnListItemTransportDetailBinding transportDetailBinding;
//            transportDetailBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.studetn_list_item_transport_detail, parent, false);
//            convertView = transportDetailBinding.getRoot();
//            transportDetailBinding.linerKms.setVisibility(View.VISIBLE);
//            transportDetailBinding.kmsView.setText("Distance from School in Kms");
//            transportDetailBinding.kmsTxt.setText(childData.get(childPosition).getDistanceFromSchoolInKms());
//            transportDetailBinding.pickupLinear.setVisibility(View.GONE);
//            transportDetailBinding.pickupPointLiner.setVisibility(View.GONE);
//            transportDetailBinding.pickuptimeLinear.setVisibility(View.GONE);
//            transportDetailBinding.dropbusLinear.setVisibility(View.GONE);
//            transportDetailBinding.droppointLinear.setVisibility(View.GONE);
//            transportDetailBinding.droppointtimeLinear.setVisibility(View.GONE);
//
//        }
        else if (headerTitle.equalsIgnoreCase("Father Details")) {
            StudentListItemFatherDetailBinding fatherDetailBinding;
            fatherDetailBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.student_list_item_father_detail, parent, false);
            convertView = fatherDetailBinding.getRoot();
            fatherDetailBinding.fatherfirstnameTxt.setText(childData.get(childPosition).getFatherName());
            fatherDetailBinding.fatherlastnameLinear.setVisibility(View.GONE);
            fatherDetailBinding.designationLinear.setVisibility(View.GONE);
            fatherDetailBinding.fatherphoneLinear.setVisibility(View.GONE);
            fatherDetailBinding.fmobileTxt.setText(childData.get(childPosition).getFatherMobileNo());
            fatherDetailBinding.femailTxt.setText(childData.get(childPosition).getFatherEmailAddress());
            fatherDetailBinding.fqualificationTxt.setText(childData.get(childPosition).getFatherQualification());
            fatherDetailBinding.foccupationTxt.setText(childData.get(childPosition).getFatherOccupation());
            fatherDetailBinding.forganisationTxt.setText(childData.get(childPosition).getFatherOrganisation());


        } else if (headerTitle.equalsIgnoreCase("Mother Details")) {
            StudentListItemMotherDetailBinding motherDetailBinding;
            motherDetailBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.student_list_item_mother_detail, parent, false);
            convertView = motherDetailBinding.getRoot();

            motherDetailBinding.motherfirstnameTxt.setText(childData.get(childPosition).getMotherName());
            motherDetailBinding.motherDesignationLinear.setVisibility(View.GONE);
            motherDetailBinding.motherPhoneLinear.setVisibility(View.GONE);
            motherDetailBinding.motherLastnameLinear.setVisibility(View.GONE);
            motherDetailBinding.mmobileTxt.setText(childData.get(childPosition).getMotherMobileNo());
            motherDetailBinding.memailTxt.setText(childData.get(childPosition).getMotherEmailAddress());
            motherDetailBinding.mqualificationTxt.setText(childData.get(childPosition).getMotherQualification());
            motherDetailBinding.moccupationTxt.setText(childData.get(childPosition).getMotherOccupation());
            motherDetailBinding.morganisationTxt.setText(childData.get(childPosition).getMotherOrganisation());


        }
//        else if (headerTitle.equalsIgnoreCase("Communication Details")) {
//            StudentListItemCommunicationDetailBinding communicationDetailBinding;
//            communicationDetailBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.student_list_item_communication_detail, parent, false);
//            convertView = communicationDetailBinding.getRoot();
//
//            communicationDetailBinding.smsLinear.setVisibility(View.GONE);
//            communicationDetailBinding.cityLinear.setVisibility(View.GONE);
//            communicationDetailBinding.zipLinear.setVisibility(View.GONE);
//            communicationDetailBinding.addressTxt.setText(childData.get(childPosition).getAddress());
//
//        }


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
    public ArrayList<FinalArrayStudentModel> getChild(int groupPosition, int childPosititon) {
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
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_group_student_full_detail, null);
        }
        TextView lblListHeader = (TextView) convertView.findViewById(R.id.lblListHeader);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);
        LinearLayout linear_group = (LinearLayout) convertView.findViewById(R.id.linear_group);
        if (headerTitle.equalsIgnoreCase("Student Details")) {
            linear_group.setBackgroundColor(Color.parseColor("#3597D3"));
        }
//        else if (headerTitle.equalsIgnoreCase("Transport Details")) {
//            linear_group.setBackgroundColor(Color.parseColor("#FF6BAE18"));
//        }
        else if (headerTitle.equalsIgnoreCase("Father Details")) {
            linear_group.setBackgroundColor(Color.parseColor("#FFE6B12E"));
        } else if (headerTitle.equalsIgnoreCase("Mother Details")) {
            linear_group.setBackgroundColor(Color.parseColor("#FF48ADDE"));
        }
//        else if (headerTitle.equalsIgnoreCase("Communication Details")) {
//            linear_group.setBackgroundColor(Color.parseColor("#FF607D8B"));
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

