package anandniketan.com.shilajadmin.Model.Account;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admsandroid on 11/27/2017.
 */

public class FinalArrayAllPaymentLedgerModel {
    @SerializedName("PayDate")
    @Expose
    private String payDate;
    @SerializedName("Paid")
    @Expose
    private String paid;
    @SerializedName("Data")
    @Expose
    private List<DatumPaymentLedgerModel> data = new ArrayList<DatumPaymentLedgerModel>();

    public String getPayDate() {
        return payDate;
    }

    public void setPayDate(String payDate) {
        this.payDate = payDate;
    }

    public String getPaid() {
        return paid;
    }

    public void setPaid(String paid) {
        this.paid = paid;
    }

    public List<DatumPaymentLedgerModel> getData() {
        return data;
    }

    public void setData(List<DatumPaymentLedgerModel> data) {
        this.data = data;
    }

}
