package anandniketan.com.shilajadmin.Model.Staff;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by admsandroid on 11/20/2017.
 */

public class StaffAttendaceModel {
    @SerializedName("Success")
    @Expose
    private String success;
    @SerializedName("FinalArray")
    @Expose
    private List<FinalArrayStaffModel> finalArray = null;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<FinalArrayStaffModel> getFinalArray() {
        return finalArray;
    }

    public void setFinalArray(List<FinalArrayStaffModel> finalArray) {
        this.finalArray = finalArray;
    }
}
