package anandniketan.com.shilajadmin.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import anandniketan.com.shilajadmin.R;
import anandniketan.com.shilajadmin.Utility.AppConfiguration;

/**
 * Created by admsandroid on 11/17/2017.
 */

public class OtherSubMenuAdapter extends BaseAdapter {
    private Context mContext;

    public String[] mThumbIds = {
            AppConfiguration.BASEURL_IMAGES + "Other/" + "Student%20Absent.png",
            AppConfiguration.BASEURL_IMAGES + "Other/" + "Bulk%20SMS.png",
            AppConfiguration.BASEURL_IMAGES + "Other/" + "Single%20SMS.png",
            AppConfiguration.BASEURL_IMAGES + "Other/" + "Employee%20SMS.png",
            AppConfiguration.BASEURL_IMAGES + "Other/" + "Summary.png",
            AppConfiguration.BASEURL_IMAGES + "Other/" + "Holiday.png",
            AppConfiguration.BASEURL_IMAGES + "Other/" + "PTM.png",
            AppConfiguration.BASEURL_IMAGES + "Other/" + "Activity%20Logging.png",
            AppConfiguration.BASEURL_IMAGES + "Other/" + "Announcement.png",
            AppConfiguration.BASEURL_IMAGES + "Other/" + "Quick%20Email.png",
    };

    public String[] mThumbNames = {"Student Absent", "Bulk SMS", "Single SMS", "Employee SMS", "Summary", "Holiday",
            "PTM", "Activity Logging", "Announcement", "Quick Email",};

    // Constructor
    public OtherSubMenuAdapter(Context c) {
        mContext = c;
    }

    @Override
    public int getCount() {
        return mThumbIds.length;
    }

    @Override
    public Object getItem(int position) {
        return mThumbIds[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imgGridOptions = null;
        TextView txtGridOptionsName = null;

        LayoutInflater mInflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        convertView = mInflater.inflate(R.layout.sub_menu_grid_cell, null);

        imgGridOptions = (ImageView) convertView.findViewById(R.id.imgGridOptions);
        txtGridOptionsName = (TextView) convertView.findViewById(R.id.txtGridOptionsName);

        String url = mThumbIds[position];
//        Log.d("url", url);
        Glide.with(mContext)
                .load(url)
                .fitCenter()
                .into(imgGridOptions);
//        imgGridOptions.setImageResource(mThumbIds[position]);
        txtGridOptionsName.setText(mThumbNames[position]);
        return convertView;
    }

}


