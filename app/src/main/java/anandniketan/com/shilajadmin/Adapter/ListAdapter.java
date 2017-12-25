package anandniketan.com.shilajadmin.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import anandniketan.com.shilajadmin.Activity.BaseItem;
import anandniketan.com.shilajadmin.Activity.CustomDataProvider;
import anandniketan.com.shilajadmin.R;
import pl.openrnd.multilevellistview.ItemInfo;
import pl.openrnd.multilevellistview.MultiLevelListAdapter;

/**
 * Created by admsandroid on 12/25/2017.
 */

public class ListAdapter extends MultiLevelListAdapter {
    private Context context;

    private class ViewHolder {
        TextView nameView;
        TextView infoView;
        ImageView arrowView, image_menu;

    }

    public ListAdapter(Context context) {
        this.context = context;
    }

    @Override
    public List<?> getSubObjects(Object object) {
        // DIEKSEKUSI SAAT KLIK PADA GROUP-ITEM
        return CustomDataProvider.getSubItems((BaseItem) object);
    }

    @Override
    public boolean isExpandable(Object object) {
        return CustomDataProvider.isExpandable((BaseItem) object);
    }

    @Override
    public View getViewForObject(Object object, View convertView, ItemInfo itemInfo) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.menu_list_header, null);
            //viewHolder.infoView = (TextView) convertView.findViewById(R.id.dataItemInfo);
            viewHolder.nameView = (TextView) convertView.findViewById(R.id.title);
            viewHolder.arrowView = (ImageView) convertView.findViewById(R.id.image_arrow);
            viewHolder.image_menu = (ImageView) convertView.findViewById(R.id.image_menu);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.nameView.setText(((BaseItem) object).getName());


        if (itemInfo.isExpandable()) {
            viewHolder.arrowView.setVisibility(View.VISIBLE);
            viewHolder.arrowView.setImageResource(itemInfo.isExpanded() ?
                    R.drawable.arrow_1_42 : R.drawable.arrow_1_42_down);
        } else {
            viewHolder.arrowView.setVisibility(View.GONE);
        }


        return convertView;
    }
}

