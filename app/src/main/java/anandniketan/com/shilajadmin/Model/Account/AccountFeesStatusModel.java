package anandniketan.com.shilajadmin.Model.Account;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admsandroid on 11/24/2017.
 */

public class AccountFeesStatusModel {
    @SerializedName("Success")
    @Expose
    private String success;
    @SerializedName("FinalArray")
    @Expose
    private List<FinalArrayAccountFeesCollectionModel> finalArray = new ArrayList<FinalArrayAccountFeesCollectionModel>();

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<FinalArrayAccountFeesCollectionModel> getFinalArray() {
        return finalArray;
    }

    public void setFinalArray(List<FinalArrayAccountFeesCollectionModel> finalArray) {
        this.finalArray = finalArray;
    }

}
