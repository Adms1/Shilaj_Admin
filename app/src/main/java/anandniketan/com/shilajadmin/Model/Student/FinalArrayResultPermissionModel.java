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
    @SerializedName("Category")
    @Expose
    private String category;
    @SerializedName("Pk_CategoryId")
    @Expose
    private Integer pkCategoryId;
    @SerializedName("StartDT")
    @Expose
    private String startDT;
    @SerializedName("EndDT")
    @Expose
    private String endDT;
    @SerializedName("Description")
    @Expose
    private String description;
    @SerializedName("HolidayName")
    @Expose
    private String holidayName;
    @SerializedName("PK_HolidayID")
    @Expose
    private Integer pKHolidayID;

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

    public Integer getpKStatusID() {
        return pKStatusID;
    }

    public void setpKStatusID(Integer pKStatusID) {
        this.pKStatusID = pKStatusID;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getPkCategoryId() {
        return pkCategoryId;
    }

    public void setPkCategoryId(Integer pkCategoryId) {
        this.pkCategoryId = pkCategoryId;
    }

    public String getStartDT() {
        return startDT;
    }

    public void setStartDT(String startDT) {
        this.startDT = startDT;
    }

    public String getEndDT() {
        return endDT;
    }

    public void setEndDT(String endDT) {
        this.endDT = endDT;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHolidayName() {
        return holidayName;
    }

    public void setHolidayName(String holidayName) {
        this.holidayName = holidayName;
    }

    public Integer getpKHolidayID() {
        return pKHolidayID;
    }

    public void setpKHolidayID(Integer pKHolidayID) {
        this.pKHolidayID = pKHolidayID;
    }
}
