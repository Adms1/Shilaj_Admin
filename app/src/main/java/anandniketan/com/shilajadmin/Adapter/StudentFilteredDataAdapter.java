package anandniketan.com.shilajadmin.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import anandniketan.com.shilajadmin.Interface.onViewClick;
import anandniketan.com.shilajadmin.Model.Staff.ConsistenceAbsentStaffModel;
import anandniketan.com.shilajadmin.Model.Staff.StaffAttendaceModel;
import anandniketan.com.shilajadmin.Model.Student.FinalArrayStudentFilteredData;
import anandniketan.com.shilajadmin.Model.Student.StudentShowFilteredDataModel;
import anandniketan.com.shilajadmin.R;
import anandniketan.com.shilajadmin.Utility.AppConfiguration;

/**
 * Created by admsandroid on 11/21/2017.
 */

public class StudentFilteredDataAdapter extends RecyclerView.Adapter<StudentFilteredDataAdapter.MyViewHolder> {
    private Context context;
    private StudentShowFilteredDataModel filteredDataModel;
    private onViewClick onViewClick;

    public StudentFilteredDataAdapter(Context mContext, StudentShowFilteredDataModel filteredDataModel, onViewClick onViewClick) {
        this.context = mContext;
        this.filteredDataModel = filteredDataModel;
        this.onViewClick = onViewClick;
    }


    @Override
    public StudentFilteredDataAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_student_list, parent, false);
        return new StudentFilteredDataAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(StudentFilteredDataAdapter.MyViewHolder holder, int position) {
        final FinalArrayStudentFilteredData filter = filteredDataModel.getFinalArray().get(position);
        holder.parentsname_txt.setText(filter.getFatherName());
        holder.studentname_txt.setText(filter.getStudentName());
        holder.grnno_txt.setText(String.valueOf(filter.getGRNO()));
        holder.grade_txt.setText(filter.getGradeSection());

        holder.view_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppConfiguration.StudentId = filter.getStudentID().toString();
                onViewClick.getViewClick();
            }
        });
    }

    @Override
    public int getItemCount() {
        return filteredDataModel.getFinalArray().size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView parentsname_txt, studentname_txt, grnno_txt, grade_txt, view_txt;

        public MyViewHolder(View itemView) {
            super(itemView);
            parentsname_txt = (TextView) itemView.findViewById(R.id.parentsname_txt);
            studentname_txt = (TextView) itemView.findViewById(R.id.studentname_txt);
            grnno_txt = (TextView) itemView.findViewById(R.id.grnno_txt);
            grade_txt = (TextView) itemView.findViewById(R.id.grade_txt);
            view_txt = (TextView) itemView.findViewById(R.id.view_txt);

        }
    }

}