package anandniketan.com.shilajadmin.Model.Other;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by admsandroid on 12/21/2017.
 */

public class FinalArrayBulkSMSModel {
    @SerializedName("StudentName")
    @Expose
    private String studentName;
    @SerializedName("Standard")
    @Expose
    private String standard;
    @SerializedName("FamilyName")
    @Expose
    private String familyName;
    @SerializedName("Sms_No")
    @Expose
    private String smsNo;
    @SerializedName("Fk_StudentID")
    @Expose
    private Integer fkStudentID;
    @SerializedName("Fk_StandardID")
    @Expose
    private Integer fkStandardID;
    @SerializedName("Fk_ClassID")
    @Expose
    private Integer fkClassID;
    @SerializedName("CheckboxStatus")
    @Expose
    private String check;

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStandard() {
        return standard;
    }

    public void setStandard(String standard) {
        this.standard = standard;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public String getSmsNo() {
        return smsNo;
    }

    public void setSmsNo(String smsNo) {
        this.smsNo = smsNo;
    }

    public Integer getFkStudentID() {
        return fkStudentID;
    }

    public void setFkStudentID(Integer fkStudentID) {
        this.fkStudentID = fkStudentID;
    }

    public Integer getFkStandardID() {
        return fkStandardID;
    }

    public void setFkStandardID(Integer fkStandardID) {
        this.fkStandardID = fkStandardID;
    }

    public Integer getFkClassID() {
        return fkClassID;
    }

    public void setFkClassID(Integer fkClassID) {
        this.fkClassID = fkClassID;
    }

    public String getCheck() {
        return check;
    }

    public void setCheck(String check) {
        this.check = check;
    }
}
