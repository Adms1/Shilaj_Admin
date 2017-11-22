package anandniketan.com.shilajadmin.Adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import anandniketan.com.shilajadmin.Model.Student.FinalArrayStudentFullDetailModel;
import anandniketan.com.shilajadmin.Model.Student.StudentFullDetailModel;
import anandniketan.com.shilajadmin.R;
import anandniketan.com.shilajadmin.databinding.ListGroupStudentFullDetailBinding;
import anandniketan.com.shilajadmin.databinding.StudentListItemStudentFullDetailBinding;
import anandniketan.com.shilajadmin.databinding.StudetnListItemTransportDetailBinding;

/**
 * Created by admsandroid on 11/22/2017.
 */

public class ExpandableListAdapterStudentFullDetail extends BaseExpandableListAdapter {

    private Context _context;
    private List<String> _listDataHeader;
    private HashMap<String, ArrayList<FinalArrayStudentFullDetailModel>> _listDataChild;
    ImageLoader imageLoader;


    public ExpandableListAdapterStudentFullDetail(Context context, List<String> listDataHeader, HashMap<String,
            ArrayList<FinalArrayStudentFullDetailModel>> listDataChild) {
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
        final ArrayList<FinalArrayStudentFullDetailModel> childData = getChild(groupPosition, 0);
        StudentListItemStudentFullDetailBinding binding;
        if (convertView == null) {
            Log.d("groupposition", "" + groupPosition);
            if (groupPosition == 0) {
                binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.student_list_item_student_full_detail, parent, false);
                convertView = binding.getRoot();
                imageLoader = ImageLoader.getInstance();
                DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                        .cacheInMemory(true)
                        .cacheOnDisk(true)
                        .imageScaleType(ImageScaleType.EXACTLY)
                        .displayer(new FadeInBitmapDisplayer(300))
                        .build();
                ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                        _context)
                        .threadPriority(Thread.MAX_PRIORITY)
                        .defaultDisplayImageOptions(defaultOptions)
                        .memoryCache(new WeakMemoryCache())
                        .denyCacheImageMultipleSizesInMemory()
                        .tasksProcessingOrder(QueueProcessingType.LIFO)// .enableLogging()
                        .build();
                imageLoader.init(config.createDefault(_context));
                imageLoader.displayImage(childData.get(childPosition).getStudentImage(), binding.profileImage);
                String[] name = childData.get(childPosition).getName().split("\\ ");
                binding.tagTxt.setText(childData.get(childPosition).getTag());
                binding.firstnameTxt.setText(name[0]);
                binding.middleTxt.setText(name[1]);
                binding.lastnameTxt.setText(name[2]);
                binding.dobTxt.setText(childData.get(childPosition).getDateOfBirth());
                binding.birthplaceTxt.setText(childData.get(childPosition).getBirthPlace());
                binding.ageTxt.setText(childData.get(childPosition).getAge());
                binding.genderTxt.setText(childData.get(childPosition).getGender());
                binding.aadharTxt.setText(childData.get(childPosition).getAadhaarCardNo());
                binding.acedamicTxt.setText(childData.get(childPosition).getAcedamicYear());
                binding.gradeTxt.setText(childData.get(childPosition).getGrade());
                binding.sectionTxt.setText(childData.get(childPosition).getSection());
                binding.lastschoolTxt.setText(childData.get(childPosition).getLastSchool());
                binding.doaTxt.setText(childData.get(childPosition).getDateOfAdmission());
                binding.bloodgroupTxt.setText(childData.get(childPosition).getBloodGroup());
                binding.houseTxt.setText(childData.get(childPosition).getHouse());
                binding.oldgrTxt.setText(childData.get(childPosition).getOldGRNO());
                binding.religionTxt.setText(childData.get(childPosition).getReligion());
                binding.usernameTxt.setText(childData.get(childPosition).getUserName());
                binding.passwordTxt.setText(childData.get(childPosition).getPassword());
                binding.grnoTxt.setText(childData.get(childPosition).getGRNO());
                binding.registrationTxt.setText(childData.get(childPosition).getRegistrationDate());
            } else if (groupPosition == 1) {
                StudetnListItemTransportDetailBinding transportDetailBinding;
                transportDetailBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.studetn_list_item_transport_detail, parent, false);
                convertView = transportDetailBinding.getRoot();

                transportDetailBinding.pickupTxt.setText(childData.get(childPosition).getPickupBus());
                transportDetailBinding.pickuppointTxt.setText(childData.get(childPosition).getPickupPoint());
                transportDetailBinding.pickuptimeTxt.setText(childData.get(childPosition).getPickupPointTime());
                transportDetailBinding.dropbusTxt.setText(childData.get(childPosition).getDropBus());
                transportDetailBinding.droppointTxt.setText(childData.get(childPosition).getDropPoint());
                transportDetailBinding.droptimeTxt.setText(childData.get(childPosition).getDropPointTime());
            }
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
    public ArrayList<FinalArrayStudentFullDetailModel> getChild(int groupPosition, int childPosititon) {
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

        ListGroupStudentFullDetailBinding groupbinding;
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            groupbinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.list_group_student_full_detail, parent, false);
            convertView = groupbinding.getRoot();

            groupbinding.lblListHeader.setTypeface(null, Typeface.BOLD);
            groupbinding.lblListHeader.setText(headerTitle);
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
