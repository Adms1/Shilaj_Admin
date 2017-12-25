package anandniketan.com.shilajadmin.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import anandniketan.com.shilajadmin.Model.MenuoptionItemModel;
import anandniketan.com.shilajadmin.R;

/**
 * Created by admsandroid on 11/16/2017.
 */

public class MenuoptionItemAdapter extends BaseAdapter {

    private Context context;

    public Integer[] mThumbIds = {R.drawable.home,
            R.drawable.menu_student, R.drawable.menu_staff, R.drawable.menu_account,
            R.drawable.menu_transport, R.drawable.menu_hr, R.drawable.menu_others,R.drawable.logout
    };

    public String[] mThumbNames = {"Home","Student", "Staff", "Account", "Transport", "HR", "Other","Logout"};


    public MenuoptionItemAdapter(Context context) {
        this.context = context;
    }

    //    private ArrayList<MenuoptionItemModel> menuOptionItems;
//
//    public MenuoptionItemAdapter(Context context, ArrayList<MenuoptionItemModel> menuOptionItems){
//        this.context = context;
//        this.menuOptionItems = menuOptionItems;
//    }
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
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.menu_drawer_item, null);
        }
        ImageView img=(ImageView)convertView.findViewById(R.id.image_menu);
        img.setImageResource(mThumbIds[position]);
        TextView txtTitle = (TextView) convertView.findViewById(R.id.title);
        txtTitle.setText(mThumbNames[position]);
        return convertView;
    }

}

