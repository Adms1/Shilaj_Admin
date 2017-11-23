package anandniketan.com.shilajadmin.Adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import anandniketan.com.shilajadmin.Model.Transport.FinalArrayVehicleDetail;
import anandniketan.com.shilajadmin.R;
import anandniketan.com.shilajadmin.databinding.VehicleDetailListItemBinding;

/**
 * Created by admsandroid on 11/23/2017.
 */

public class VehicleDetailListAdapter extends RecyclerView.Adapter<VehicleDetailListAdapter.MyViewHolder> {
    private Context context;
    List<FinalArrayVehicleDetail> vehicleDetailList;
//    VehicleDetailListItemBinding binding;

    public VehicleDetailListAdapter(Context mContext, List<FinalArrayVehicleDetail> vehicleDetailList) {
        this.context = mContext;
        this.vehicleDetailList = vehicleDetailList;
    }


    @Override
    public VehicleDetailListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.vehicle_detail_list_item, parent, false);
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.vehicle_detail_list_item, parent, false);

//        View itemView = binding.getRoot();
        return new VehicleDetailListAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(VehicleDetailListAdapter.MyViewHolder holder, int position) {
        String sr = String.valueOf(position + 1);
        holder.index_txt.setText(sr);
        holder.vehiclename_txt.setText(vehicleDetailList.get(position).getVehicleName());
        holder.registrationno_txt.setText(vehicleDetailList.get(position).getRegistrationNo());
        holder.passenger_txt.setText(String.valueOf(vehicleDetailList.get(position).getPassngCapacity()));
    }

    @Override
    public int getItemCount() {
        return vehicleDetailList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView index_txt, vehiclename_txt, registrationno_txt, passenger_txt;

        public MyViewHolder(View itemView) {
            super(itemView);
            index_txt = (TextView) itemView.findViewById(R.id.index_txt);
            vehiclename_txt = (TextView) itemView.findViewById(R.id.vehiclename_txt);
            registrationno_txt = (TextView) itemView.findViewById(R.id.registrationno_txt);
            passenger_txt = (TextView) itemView.findViewById(R.id.passenger_txt);
        }
    }

}
