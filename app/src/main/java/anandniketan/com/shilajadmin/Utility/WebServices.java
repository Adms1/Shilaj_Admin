package anandniketan.com.shilajadmin.Utility;

import java.util.Map;

import anandniketan.com.shilajadmin.Model.Account.AccountFeesStatusModel;
import anandniketan.com.shilajadmin.Model.Account.AccountFeesStructureModel;
import anandniketan.com.shilajadmin.Model.Account.DailyFeeCollectionModel;
import anandniketan.com.shilajadmin.Model.Account.GetDiscountDetailsModel;
import anandniketan.com.shilajadmin.Model.Account.GetImprestDetailModel;
import anandniketan.com.shilajadmin.Model.Account.GetStandardModel;
import anandniketan.com.shilajadmin.Model.Staff.StaffAttendaceModel;
import anandniketan.com.shilajadmin.Model.Staff.TimeTableModel;
import anandniketan.com.shilajadmin.Model.Student.ParentsNameModel;
import anandniketan.com.shilajadmin.Model.Student.StudentAttendanceModel;
import anandniketan.com.shilajadmin.Model.Student.StudentFullDetailModel;
import anandniketan.com.shilajadmin.Model.Student.StudentNameModel;
import anandniketan.com.shilajadmin.Model.Student.StudentShowFilteredDataModel;
import anandniketan.com.shilajadmin.Model.Student.StudentTransportDetailModel;
import anandniketan.com.shilajadmin.Model.Transport.GetRoutePickUpPointDetailModel;
import anandniketan.com.shilajadmin.Model.Transport.TermModel;
import anandniketan.com.shilajadmin.Model.Transport.TransportChargesModel;
import anandniketan.com.shilajadmin.Model.Transport.VehicleDetailModel;
import anandniketan.com.shilajadmin.Model.Transport.VehicleRouteDetailModel;
import retrofit.Callback;
import retrofit.http.FieldMap;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

/**
 * Created by admsandroid on 11/20/2017.
 */

public interface WebServices {
    @FormUrlEncoded
    @POST("/Admin_StudentAttendence")
    public void getStudentAttendace(@FieldMap Map<String, String> map, Callback<StudentAttendanceModel> callback);

    @FormUrlEncoded
    @POST("/Admin_StaffAttendence")
    public void getStaffAttendace(@FieldMap Map<String, String> map, Callback<StaffAttendaceModel> callback);

    @FormUrlEncoded
    @POST("/Admin_StudentSearchByParentName")
    public void getParentName(@FieldMap Map<String, String> map, Callback<ParentsNameModel> callback);

    @FormUrlEncoded
    @POST("/Admin_StudentSearchByStuName")
    public void getStudentName(@FieldMap Map<String, String> map, Callback<StudentNameModel> callback);

    @FormUrlEncoded
    @POST("/Admin_StudentShowFilteredData")
    public void getStudentFilterData(@FieldMap Map<String, String> map, Callback<StudentShowFilteredDataModel> callback);

    @FormUrlEncoded
    @POST("/Admin_StudentFullDetail")
    public void getStudentFullDetail(@FieldMap Map<String, String> map, Callback<StudentFullDetailModel> callback);

    @FormUrlEncoded
    @POST("/GetTerm")
    public void getTerm(@FieldMap Map<String, String> map, Callback<TermModel> callback);

    @FormUrlEncoded
    @POST("/GetRoutePickUpPointDetail")
    public void getRouteDetail(@FieldMap Map<String, String> map, Callback<GetRoutePickUpPointDetailModel> callback);

    @FormUrlEncoded
    @POST("/StudentTransportDetail")
    public void getStudentTransportDetail(@FieldMap Map<String, String> map, Callback<StudentTransportDetailModel> callback);

    @FormUrlEncoded
    @POST("/GetTrasportCharges")
    public void getTransportChargesDetail(@FieldMap Map<String, String> map, Callback<TransportChargesModel> callback);

    @FormUrlEncoded
    @POST("/GetVehicleDetail")
    public void getVehicleDetail(@FieldMap Map<String, String> map, Callback<VehicleDetailModel> callback);

    @FormUrlEncoded
    @POST("/GetVehicleToRouteDetail")
    public void getVehicleRouteDetail(@FieldMap Map<String, String> map, Callback<VehicleRouteDetailModel> callback);

    @FormUrlEncoded
    @POST("/Admin_AccountFeesStatus")
    public void getAccountFeesStatusDetail(@FieldMap Map<String, String> map, Callback<AccountFeesStatusModel> callback);

    @FormUrlEncoded
    @POST("/Admin_AccountFeesStructure")
    public void getAccountFeesStructureDetail(@FieldMap Map<String, String> map, Callback<AccountFeesStructureModel> callback);

    @FormUrlEncoded
    @POST("/GetStandardSection")
    public void getStandardDetail(@FieldMap Map<String, String> map, Callback<GetStandardModel> callback);

    @FormUrlEncoded
    @POST("/GetDiscountDetail")
    public void getDiscountDetail(@FieldMap Map<String, String> map, Callback<GetDiscountDetailsModel> callback);

    @FormUrlEncoded
    @POST("/GetImprestDetail")
    public void getImprestDetail(@FieldMap Map<String, String> map, Callback<GetImprestDetailModel> callback);

    @FormUrlEncoded
    @POST("/DailyFeeColleCtion")
    public void getDailyFeeColleCtionDetail(@FieldMap Map<String, String> map, Callback<DailyFeeCollectionModel> callback);

    @FormUrlEncoded
    @POST("/TeacherGetTimetable")
    public void getTimeTable(@FieldMap Map<String, String> map, Callback<TimeTableModel> callback);
}
