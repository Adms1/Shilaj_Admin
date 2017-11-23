package anandniketan.com.shilajadmin.Utility;

import java.util.Map;

import anandniketan.com.shilajadmin.Model.Staff.StaffAttendaceModel;
import anandniketan.com.shilajadmin.Model.Student.ParentsNameModel;
import anandniketan.com.shilajadmin.Model.Student.StudentAttendanceModel;
import anandniketan.com.shilajadmin.Model.Student.StudentFullDetailModel;
import anandniketan.com.shilajadmin.Model.Student.StudentNameModel;
import anandniketan.com.shilajadmin.Model.Student.StudentShowFilteredDataModel;
import anandniketan.com.shilajadmin.Model.Student.StudentTransportDetailModel;
import anandniketan.com.shilajadmin.Model.Transport.GetRoutePickUpPointDetailModel;
import anandniketan.com.shilajadmin.Model.Transport.PickupPointDetailModel;
import anandniketan.com.shilajadmin.Model.Transport.TermModel;
import anandniketan.com.shilajadmin.Model.Transport.TransportChargesModel;
import anandniketan.com.shilajadmin.Model.Transport.VehicleDetailModel;
import anandniketan.com.shilajadmin.Model.Transport.VehicleRouteDetailModel;
import retrofit.Callback;
import retrofit.http.FieldMap;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;
import retrofit.http.QueryMap;

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

}
