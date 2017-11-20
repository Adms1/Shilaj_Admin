package anandniketan.com.shilajadmin.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import anandniketan.com.shilajadmin.Model.Student.FinalArrayStudentModel;
import anandniketan.com.shilajadmin.Model.Student.StandardWiseAttendanceModel;
import anandniketan.com.shilajadmin.Model.Student.StudentAttendanceModel;
import anandniketan.com.shilajadmin.R;

/**
 * Created by admsandroid on 11/20/2017.
 */

public class StandardwiseStudentAttendaceAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private StudentAttendanceModel studentAttendanceModel;
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == TYPE_ITEM) {
            View itemview = LayoutInflater.from(parent.getContext()).inflate(R.layout.standardwise_attendace_list, parent, false);
            return new MyViewHolder(itemview);
        } else if (viewType == TYPE_HEADER) {
            View itemview = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_attendace_header, parent, false);
            return new HeaderViewHolder(itemview);
        }
        throw new RuntimeException("there is no type that matches the type " + viewType + " + make sure your using types correctly");
    }


    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HeaderViewHolder) {

//similarly bind other UI components or perform operations
        } else if (holder instanceof MyViewHolder) {
            StandardWiseAttendanceModel detail = studentAttendanceModel.getFinalArray().get(0).getStandardWiseAttendance().get(position);
            ((MyViewHolder) holder).standard_txt.setText(detail.getStandard());
            ((MyViewHolder) holder).status_txt.setText(detail.getStatus());
        }
    }


    @Override
    public int getItemCount() {
        return studentAttendanceModel.getFinalArray().get(0).getStandardWiseAttendance().size() + 1;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView standard_txt, status_txt;

        public MyViewHolder(View itemView) {
            super(itemView);

            standard_txt = (TextView) itemView.findViewById(R.id.standard_txt);
            status_txt = (TextView) itemView.findViewById(R.id.status_txt);
        }
    }

    class HeaderViewHolder extends RecyclerView.ViewHolder {
        public View View;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            View = itemView;
// add your ui components here like this below
        }
    }

    public StandardwiseStudentAttendaceAdapter(Context context, StudentAttendanceModel studentAttendanceModel) {
        this.context = context;
        this.studentAttendanceModel = studentAttendanceModel;

    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEADER;
        }
        return TYPE_ITEM;

    }

}
