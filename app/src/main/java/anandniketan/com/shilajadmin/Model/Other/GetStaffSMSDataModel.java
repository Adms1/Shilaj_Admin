package anandniketan.com.shilajadmin.Model.Other;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admsandroid on 12/21/2017.
 */

public class GetStaffSMSDataModel {
    @SerializedName("Success")
    @Expose
    private String success;
    @SerializedName("FinalArray")
    @Expose
    private List<FinalArraySMSDataModel> finalArray = new ArrayList<FinalArraySMSDataModel>();

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<FinalArraySMSDataModel> getFinalArray() {
        return finalArray;
    }

    public void setFinalArray(List<FinalArraySMSDataModel> finalArray) {
        this.finalArray = finalArray;
    }

}
