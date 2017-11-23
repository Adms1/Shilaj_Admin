package anandniketan.com.shilajadmin.Fragment;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import anandniketan.com.shilajadmin.Adapter.StudentSubMenuAdapter;
import anandniketan.com.shilajadmin.Adapter.TransportSubMenuAdapter;
import anandniketan.com.shilajadmin.R;
import anandniketan.com.shilajadmin.databinding.FragmentStudentBinding;
import anandniketan.com.shilajadmin.databinding.FragmentTransportBinding;


public class TransportFragment extends Fragment {

    private FragmentTransportBinding fragmentTransportBinding;
    private View rootView;
    private Context mContext;
    private Fragment fragment = null;
    private FragmentManager fragmentManager = null;

    public TransportFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentTransportBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_transport, container, false);

        rootView = fragmentTransportBinding.getRoot();
        mContext = getActivity().getApplicationContext();

        initViews();
        setListners();
        return rootView;
    }

    public void initViews() {
        fragmentTransportBinding.transportSubmenuGridView.setAdapter(new TransportSubMenuAdapter(mContext));
    }

    public void setListners() {
        fragmentTransportBinding.btnBackTransport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = new HomeFragment();
                fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .setCustomAnimations(0, 0)
                        .replace(R.id.frame_container, fragment).commit();
            }
        });

        fragmentTransportBinding.transportSubmenuGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    fragment = new TransportChargesFragment();
                    fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(0, 0)
                            .replace(R.id.frame_container, fragment).commit();
                }else if(position==1){
                    fragment = new RoutePickUpDetailFragment();
                    fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(0, 0)
                            .replace(R.id.frame_container, fragment).commit();
                }
                else if(position==2){
                    fragment = new VehicleDetailFragment();
                    fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(0, 0)
                            .replace(R.id.frame_container, fragment).commit();
                }else if(position==3){
                    fragment = new VehicleRouteFragment();
                    fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(0, 0)
                            .replace(R.id.frame_container, fragment).commit();
                }
            }
        });

    }
}

