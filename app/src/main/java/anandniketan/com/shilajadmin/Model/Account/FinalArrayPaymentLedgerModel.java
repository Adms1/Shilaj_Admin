package anandniketan.com.shilajadmin.Model.Account;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by admsandroid on 11/27/2017.
 */

public class FinalArrayPaymentLedgerModel {
    @SerializedName("Term")
    @Expose
    private String term;
    @SerializedName("StudentName")
    @Expose
    private String studentName;
    @SerializedName("Standard")
    @Expose
    private String standard;
    @SerializedName("ClassName")
    @Expose
    private String className;
    @SerializedName("GRNO")
    @Expose
    private String gRNO;
    @SerializedName("SMSNo")
    @Expose
    private String sMSNo;
    @SerializedName("Date of Joining")
    @Expose
    private String dateOfJoining;
    @SerializedName("TermTotal")
    @Expose
    private String termTotal;
    @SerializedName("TermPaid")
    @Expose
    private String termPaid;
    @SerializedName("TermDuePay")
    @Expose
    private String termDuePay;
    @SerializedName("TermDiscount")
    @Expose
    private String termDiscount;
    @SerializedName("TermLateFee")
    @Expose
    private String termLateFee;
    @SerializedName("PreviousBalance")
    @Expose
    private String previousBalance;
    @SerializedName("AdmissionFees")
    @Expose
    private String admissionFees;
    @SerializedName("CautionFees")
    @Expose
    private String cautionFees;
    @SerializedName("TutionFees")
    @Expose
    private String tutionFees;
    @SerializedName("TransportFees")
    @Expose
    private String transportFees;
    @SerializedName("Imprest")
    @Expose
    private String imprest;

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

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

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getGRNO() {
        return gRNO;
    }

    public void setGRNO(String gRNO) {
        this.gRNO = gRNO;
    }

    public String getSMSNo() {
        return sMSNo;
    }

    public void setSMSNo(String sMSNo) {
        this.sMSNo = sMSNo;
    }

    public String getDateOfJoining() {
        return dateOfJoining;
    }

    public void setDateOfJoining(String dateOfJoining) {
        this.dateOfJoining = dateOfJoining;
    }

    public String getTermTotal() {
        return termTotal;
    }

    public void setTermTotal(String termTotal) {
        this.termTotal = termTotal;
    }

    public String getTermPaid() {
        return termPaid;
    }

    public void setTermPaid(String termPaid) {
        this.termPaid = termPaid;
    }

    public String getTermDuePay() {
        return termDuePay;
    }

    public void setTermDuePay(String termDuePay) {
        this.termDuePay = termDuePay;
    }

    public String getTermDiscount() {
        return termDiscount;
    }

    public void setTermDiscount(String termDiscount) {
        this.termDiscount = termDiscount;
    }

    public String getTermLateFee() {
        return termLateFee;
    }

    public void setTermLateFee(String termLateFee) {
        this.termLateFee = termLateFee;
    }

    public String getPreviousBalance() {
        return previousBalance;
    }

    public void setPreviousBalance(String previousBalance) {
        this.previousBalance = previousBalance;
    }

    public String getAdmissionFees() {
        return admissionFees;
    }

    public void setAdmissionFees(String admissionFees) {
        this.admissionFees = admissionFees;
    }

    public String getCautionFees() {
        return cautionFees;
    }

    public void setCautionFees(String cautionFees) {
        this.cautionFees = cautionFees;
    }

    public String getTutionFees() {
        return tutionFees;
    }

    public void setTutionFees(String tutionFees) {
        this.tutionFees = tutionFees;
    }

    public String getTransportFees() {
        return transportFees;
    }

    public void setTransportFees(String transportFees) {
        this.transportFees = transportFees;
    }

    public String getImprest() {
        return imprest;
    }

    public void setImprest(String imprest) {
        this.imprest = imprest;
    }
}
