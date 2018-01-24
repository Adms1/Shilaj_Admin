package anandniketan.com.shilajadmin.Model.Student;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by admsandroid on 1/24/2018.
 */

public class FinalArrayResultPermissionModel {
    @SerializedName("PK_StatusID")
    @Expose
    private Integer pKStatusID;
    @SerializedName("Standard")
    @Expose
    private String standard;
    @SerializedName("Status")
    @Expose
    private String status;

    public Integer getPKStatusID() {
        return pKStatusID;
    }

    public void setPKStatusID(Integer pKStatusID) {
        this.pKStatusID = pKStatusID;
    }

    public String getStandard() {
        return standard;
    }

    public void setStandard(String standard) {
        this.standard = standard;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
