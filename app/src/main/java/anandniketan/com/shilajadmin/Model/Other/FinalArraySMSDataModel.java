package anandniketan.com.shilajadmin.Model.Other;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by admsandroid on 12/21/2017.
 */

public class FinalArraySMSDataModel {
    @SerializedName("PK_EmployeeID")
    @Expose
    private Integer pKEmployeeID;
    @SerializedName("EmpName")
    @Expose
    private String empName;
    @SerializedName("Emp_MobileNo")
    @Expose
    private Object empMobileNo;
    @SerializedName("CheckboxStatus")
    @Expose
    private String check;
    @SerializedName("Count")
    @Expose
    private Integer count;
    @SerializedName("Month")
    @Expose
    private Integer month;
    @SerializedName("Type")
    @Expose
    private String type;


    public Integer getPKEmployeeID() {
        return pKEmployeeID;
    }

    public void setPKEmployeeID(Integer pKEmployeeID) {
        this.pKEmployeeID = pKEmployeeID;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public Object getEmpMobileNo() {
        return empMobileNo;
    }

    public void setEmpMobileNo(Object empMobileNo) {
        this.empMobileNo = empMobileNo;
    }

    public String getCheck() {
        return check;
    }

    public void setCheck(String check) {
        this.check = check;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
