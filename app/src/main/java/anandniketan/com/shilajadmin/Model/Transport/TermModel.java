package anandniketan.com.shilajadmin.Model.Transport;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admsandroid on 11/22/2017.
 */

public class TermModel {
    @SerializedName("Success")
    @Expose
    private String success;
    @SerializedName("TermID")
    @Expose
    private Integer termID;
    @SerializedName("Term")
    @Expose
    private String term;

    @SerializedName("FinalArray")
    @Expose
    private List<FinalArrayGetTermModel> finalArray = new ArrayList<FinalArrayGetTermModel>();

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public Integer getTermID() {
        return termID;
    }

    public void setTermID(Integer termID) {
        this.termID = termID;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public List<FinalArrayGetTermModel> getFinalArray() {
        return finalArray;
    }

    public void setFinalArray(List<FinalArrayGetTermModel> finalArray) {
        this.finalArray = finalArray;
    }

}
