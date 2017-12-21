package anandniketan.com.shilajadmin.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

import anandniketan.com.shilajadmin.Model.HR.FinalArrayPageListModel;
import anandniketan.com.shilajadmin.Model.Staff.FinalArrayClassTeacherDetailModel;
import anandniketan.com.shilajadmin.R;

/**
 * Created by admsandroid on 12/20/2017.
 */

public class PageDeatilListAdapter extends RecyclerView.Adapter<PageDeatilListAdapter.MyViewHolder> {
    private Context context;
    List<FinalArrayPageListModel> pageListmodel;
    String addAllStr, updateStr, deleteStr;

    public PageDeatilListAdapter(Context mContext, List<FinalArrayPageListModel> pageListmodel) {
        this.context = mContext;
        this.pageListmodel = pageListmodel;
    }


    @Override
    public PageDeatilListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.page_detail_list_item, parent, false);

        return new PageDeatilListAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PageDeatilListAdapter.MyViewHolder holder, int position) {
        String sr = String.valueOf(position + 1);

        holder.index_txt.setText(sr);
        holder.pagename_txt.setText(pageListmodel.get(position).getPageNam());
        addAllStr = pageListmodel.get(position).getStatus().toString();
        updateStr = pageListmodel.get(position).getIsUserUpdate().toString();
        deleteStr = pageListmodel.get(position).getIsUserDelete().toString();

        if (addAllStr.equalsIgnoreCase("true")) {
            holder.addall_chk.setChecked(true);
        } else {
            holder.addall_chk.setChecked(false);
        }

        if (updateStr.equalsIgnoreCase("true")) {
            holder.update_chk.setChecked(true);
        } else {
            holder.update_chk.setChecked(true);
        }

        if (deleteStr.equalsIgnoreCase("true")) {
            holder.delete_chk.setChecked(true);
        } else {
            holder.delete_chk.setChecked(true);
        }
    }

    @Override
    public int getItemCount() {
        return pageListmodel.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView index_txt, pagename_txt;
        CheckBox addall_chk, update_chk, delete_chk;

        public MyViewHolder(View itemView) {
            super(itemView);
            index_txt = (TextView) itemView.findViewById(R.id.index_txt);
            pagename_txt = (TextView) itemView.findViewById(R.id.pagename_txt);
            addall_chk = (CheckBox) itemView.findViewById(R.id.addall_chk);
            update_chk = (CheckBox) itemView.findViewById(R.id.update_chk);
            delete_chk = (CheckBox) itemView.findViewById(R.id.delete_chk);
        }
    }

}

