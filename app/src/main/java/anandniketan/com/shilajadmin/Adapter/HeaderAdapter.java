package anandniketan.com.shilajadmin.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import anandniketan.com.shilajadmin.R;

/**
 * Created by admsandroid on 11/16/2017.
 */

public class HeaderAdapter extends BaseAdapter {
    private Context mContext;

    public Integer[] mThumbIds = {R.drawable.student};


    // Constructor
    public HeaderAdapter(Context c) {
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
        convertView = mInflater.inflate(R.layout.grid_image_cell, null);

        imgGridOptions = (ImageView) convertView.findViewById(R.id.imgGridOptions);

//        imgGridOptions.setImageResource(mThumbIds[position]);
        return convertView;
    }

}
