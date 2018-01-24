package anandniketan.com.shilajadmin.Model.Student;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admsandroid on 1/24/2018.
 */

public class GetResultPermissionModel {
    @SerializedName("Success")
    @Expose
    private String success;
    @SerializedName("Year")
    @Expose
    private String year;
    @SerializedName("FinalArray")
    @Expose
    private List<FinalArrayResultPermissionModel> finalArray = new ArrayList<FinalArrayResultPermissionModel>();

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public List<FinalArrayResultPermissionModel> getFinalArray() {
        return finalArray;
    }

    public void setFinalArray(List<FinalArrayResultPermissionModel> finalArray) {
        this.finalArray = finalArray;
    }

}
