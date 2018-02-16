package anandniketan.com.shilajadmin.Adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.text.DecimalFormat;
import java.text.Format;
import java.util.List;
import anandniketan.com.shilajadmin.Model.Transport.FinalArrayTransportChargesModel;
import anandniketan.com.shilajadmin.R;
import anandniketan.com.shilajadmin.databinding.TransportChargesListBinding;

/**
 * Created by admsandroid on 11/23/2017.
 */

public class TransportChargesListAdapter extends BaseAdapter {
    private Context mContext;
    private List<FinalArrayTransportChargesModel> transportChargesModelsList;
    TransportChargesListBinding binding;
    String amount1 = "", amount2 = "";
    Double longval1 = null, longval2 = null;
    Format formatter = new DecimalFormat("##,##,###");
    String formattedString1, formattedString2;
    public TransportChargesListAdapter(Context mContext, List<FinalArrayTransportChargesModel> transportChargesModelsList) {
        this.mContext = mContext;
        this.transportChargesModelsList = transportChargesModelsList;
    }


    private class ViewHolder {

    }

    @Override
    public int getCount() {
        return transportChargesModelsList.size();
    }

    @Override
    public Object getItem(int position) {
        return transportChargesModelsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        View view = null;
        convertView = null;
        if (convertView == null) {

        }

        binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.transport_charges_list, parent, false);
        convertView = binding.getRoot();

        try {
            binding.kmTxt.setText(transportChargesModelsList.get(position).getKm());
            amount1 = String.valueOf(transportChargesModelsList.get(position).getTerm1());
            amount2 = String.valueOf(transportChargesModelsList.get(position).getTerm2());

            longval1 = Double.parseDouble(amount1);
            longval2 = Double.parseDouble(amount2);
            formattedString1 = formatter.format(longval1);
            formattedString2 = formatter.format(longval2);
            binding.term1Txt.setText("₹" + " " +formattedString1);
            binding.term2Txt.setText("₹" + " " +formattedString2);

        } catch (Exception e) {
            e.printStackTrace();

        }
        return convertView;
    }


}


