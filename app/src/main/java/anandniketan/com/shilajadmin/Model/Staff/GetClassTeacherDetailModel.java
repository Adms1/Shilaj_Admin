package anandniketan.com.shilajadmin.Model.Staff;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admsandroid on 12/20/2017.
 */

public class GetClassTeacherDetailModel {
    @SerializedName("Success")
    @Expose
    private String success;
    @SerializedName("FinalArray")
    @Expose
    private List<FinalArrayClassTeacherDetailModel> finalArray = new ArrayList<FinalArrayClassTeacherDetailModel>();

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<FinalArrayClassTeacherDetailModel> getFinalArray() {
        return finalArray;
    }

    public void setFinalArray(List<FinalArrayClassTeacherDetailModel> finalArray) {
        this.finalArray = finalArray;
    }

}
