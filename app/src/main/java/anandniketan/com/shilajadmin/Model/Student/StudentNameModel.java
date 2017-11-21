package anandniketan.com.shilajadmin.Model.Student;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admsandroid on 11/21/2017.
 */

public class StudentNameModel {
    @SerializedName("Success")
    @Expose
    private String success;
    @SerializedName("FinalArray")
    @Expose
    private List<FinalArrayStudentNameModel> finalArray = new ArrayList<FinalArrayStudentNameModel>();

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<FinalArrayStudentNameModel> getFinalArray() {
        return finalArray;
    }

    public void setFinalArray(List<FinalArrayStudentNameModel> finalArray) {
        this.finalArray = finalArray;
    }


}
