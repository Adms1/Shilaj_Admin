package anandniketan.com.shilajadmin.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import anandniketan.com.shilajadmin.Interface.getEditpermission;
import anandniketan.com.shilajadmin.Model.Student.FinalArrayResultPermissionModel;
import anandniketan.com.shilajadmin.Model.Student.GetResultPermissionModel;
import anandniketan.com.shilajadmin.R;

/**
 * Created by admsandroid on 1/25/2018.
 */

public class OnlinePaymentPermissionAdapter extends RecyclerView.Adapter<OnlinePaymentPermissionAdapter.MyViewHolder> {
    private Context context;
    private GetResultPermissionModel resultPermissionModel;
    private ArrayList<String> rowvalue;
    getEditpermission listner;

    public OnlinePaymentPermissionAdapter(Context mContext, GetResultPermissionModel resultPermissionModel, getEditpermission listner) {
        this.context = mContext;
        this.resultPermissionModel = resultPermissionModel;
        this.listner = listner;
    }


    @Override
    public OnlinePaymentPermissionAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.online_payment_permission_list, parent, false);
        return new OnlinePaymentPermissionAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(OnlinePaymentPermissionAdapter.MyViewHolder holder, int position) {
        String sr = String.valueOf(position + 1);
        final FinalArrayResultPermissionModel result = resultPermissionModel.getFinalArray().get(position);
        holder.index_txt.setText(sr);
        holder.academicyear_txt.setText(resultPermissionModel.getYear());
        holder.grade_txt.setText(String.valueOf(result.getStandard()));
        holder.resultstatus_txt.setText(result.getStatus());
        holder.termdetail_txt.setText(resultPermissionModel.getTerm());

        holder.edit_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rowvalue = new ArrayList<String>();
                rowvalue.add(resultPermissionModel.getYear() + "|" + result.getStandard() + "|" + result.getStatus()+"|"+resultPermissionModel.getTerm());
                listner.getEditpermission();
            }
        });

    }

    @Override
    public int getItemCount() {
        return resultPermissionModel.getFinalArray().size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView index_txt, academicyear_txt, grade_txt, resultstatus_txt, termdetail_txt;
        ImageView edit_img;

        public MyViewHolder(View itemView) {
            super(itemView);
            index_txt = (TextView) itemView.findViewById(R.id.index_txt);
            academicyear_txt = (TextView) itemView.findViewById(R.id.academicyear_txt);
            termdetail_txt = (TextView) itemView.findViewById(R.id.termdetail_txt);
            grade_txt = (TextView) itemView.findViewById(R.id.grade_txt);
            resultstatus_txt = (TextView) itemView.findViewById(R.id.resultstatus_txt);
            edit_img = (ImageView) itemView.findViewById(R.id.edit_img);

        }
    }

    public ArrayList<String> getRowValue() {
        return rowvalue;
    }
}

