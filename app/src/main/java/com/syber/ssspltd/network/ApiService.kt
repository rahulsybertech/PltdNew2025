package com.syber.ssspltd.network

import com.google.gson.JsonObject
import com.syber.ssspltd.data.model.bookingRequest.StayBookingRequest
import com.syber.ssspltd.data.model.userType.ApiResponse
import com.syber.ssspltd.utils.MyConstant.BRANCH_LIST
import com.syber.ssspltd.utils.MyConstant.BlackListedName
import com.syber.ssspltd.utils.MyConstant.CHECKOTP
import com.syber.ssspltd.utils.MyConstant.CheckMobile_New
import com.syber.ssspltd.utils.MyConstant.DeleteGuestMasterData
import com.syber.ssspltd.utils.MyConstant.FYEARLIST
import com.syber.ssspltd.utils.MyConstant.GETAPPMENUPERMISIONDATA
import com.syber.ssspltd.utils.MyConstant.GET_APP_VERSION
import com.syber.ssspltd.utils.MyConstant.GET_BANNER_LIST
import com.syber.ssspltd.utils.MyConstant.GetAllEventImages
import com.syber.ssspltd.utils.MyConstant.GetAppThemeDetailsData
import com.syber.ssspltd.utils.MyConstant.GetBankDetails
import com.syber.ssspltd.utils.MyConstant.GetBranches
import com.syber.ssspltd.utils.MyConstant.GetBrandMasterDetails
import com.syber.ssspltd.utils.MyConstant.GetBrands
import com.syber.ssspltd.utils.MyConstant.GetCourierReport
import com.syber.ssspltd.utils.MyConstant.GetCreditNoteReport
import com.syber.ssspltd.utils.MyConstant.GetDashboardDetail_Graph
import com.syber.ssspltd.utils.MyConstant.GetDashboardDetails_BalanceTillDate
import com.syber.ssspltd.utils.MyConstant.GetDashboardDetails_Interest_Discount
import com.syber.ssspltd.utils.MyConstant.GetDashboardDetails_PendingOrder
import com.syber.ssspltd.utils.MyConstant.GetDashboardDetails_StockInOffice
import com.syber.ssspltd.utils.MyConstant.GetDebitNoteToCustomerReport
import com.syber.ssspltd.utils.MyConstant.GetDispatchTypeList
import com.syber.ssspltd.utils.MyConstant.GetFilterDetailList
import com.syber.ssspltd.utils.MyConstant.GetFilterListNew
import com.syber.ssspltd.utils.MyConstant.GetGuestMasterListByCustomerId
import com.syber.ssspltd.utils.MyConstant.GetLedgerReportWithBalance
import com.syber.ssspltd.utils.MyConstant.GetMainPartyAndSubPartyList
import com.syber.ssspltd.utils.MyConstant.GetNickNameList
import com.syber.ssspltd.utils.MyConstant.GetProfileDetails
import com.syber.ssspltd.utils.MyConstant.GetSaleReport
import com.syber.ssspltd.utils.MyConstant.GetSaleServiceReport
import com.syber.ssspltd.utils.MyConstant.GetSecurityCheckReport
import com.syber.ssspltd.utils.MyConstant.GetStayBookingDataById
import com.syber.ssspltd.utils.MyConstant.GetStayBookingDataList
import com.syber.ssspltd.utils.MyConstant.GetStayBookingDataListByBranchId
import com.syber.ssspltd.utils.MyConstant.GetStockInOfficeReport
import com.syber.ssspltd.utils.MyConstant.ItemNameList
import com.syber.ssspltd.utils.MyConstant.LOGIN
import com.syber.ssspltd.utils.MyConstant.MarketerListWithSupplierCode
import com.syber.ssspltd.utils.MyConstant.MaxOrderNoByMarketer
import com.syber.ssspltd.utils.MyConstant.OrderReportWithAccountID
import com.syber.ssspltd.utils.MyConstant.PartyDetailsByPartyCode
import com.syber.ssspltd.utils.MyConstant.PcsTypeList
import com.syber.ssspltd.utils.MyConstant.SAVEANDUPDATESTAYBOOKING
import com.syber.ssspltd.utils.MyConstant.SalesPartyCodeList
import com.syber.ssspltd.utils.MyConstant.SaveNewUserDetails
import com.syber.ssspltd.utils.MyConstant.SaveOrder
import com.syber.ssspltd.utils.MyConstant.SaveUpdateGuestMasterDetails
import com.syber.ssspltd.utils.MyConstant.SchemeListWithSupplierCode
import com.syber.ssspltd.utils.MyConstant.SupplierNickName
import com.syber.ssspltd.utils.MyConstant.USERTYPELIST
import com.syber.ssspltd.utils.MyConstant.UpdateStayBookingActualTime
import com.syber.ssspltd.utils.MyConstant.VERIFY_REFERRAL
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.PartMap
import retrofit2.http.Query

interface ApiService {
    @POST(LOGIN)
    suspend fun loginApii(@Body jsonObject: JsonObject): JsonObject
    @POST(USERTYPELIST)
    suspend fun userTypeApii(@Body jsonObject: JsonObject): JsonObject
    @POST(SaveNewUserDetails)
    suspend fun signupApii(@Body jsonObject: JsonObject): JsonObject

    @POST(VERIFY_REFERRAL)
    suspend fun verifyReferralApii(@Query("referralCode") referralCode: String): JsonObject
    @POST(GET_BANNER_LIST)
    suspend fun bannerApii(@Body jsonObject: JsonObject): JsonObject
    @POST(CHECKOTP)
    suspend fun checkOtpApii(@Body jsonObject: JsonObject): JsonObject
    @POST(CheckMobile_New)
    suspend fun resendOtpApii(@Body jsonObject: JsonObject): JsonObject

    @POST(GET_APP_VERSION)
    suspend fun appVersionApii(@Body jsonObject: JsonObject): JsonObject
    @POST(FYEARLIST)
    suspend fun fYearListApii(@Body jsonObject: JsonObject): JsonObject

    @POST(GetDashboardDetail_Graph)
    suspend fun paymentDashboardApii(@Body jsonObject: JsonObject): JsonObject

    @POST(GetDashboardDetails_BalanceTillDate)
    suspend fun GetDashboardDetailsBalanceTillDateApi(@Body jsonObject: JsonObject): JsonObject

    @POST(GetDashboardDetails_Interest_Discount)
    suspend fun dashboardDetailsInterestDiscountApi(@Body jsonObject: JsonObject): JsonObject

    @POST(GetDashboardDetails_StockInOffice)
    suspend fun dashboardDetailsStockInOfficeApi(@Body jsonObject: JsonObject): JsonObject
    @POST(GetDashboardDetails_PendingOrder)
    suspend fun dashboardDetailsPendingOrderApi(@Body jsonObject: JsonObject): JsonObject


    @POST(GetAppThemeDetailsData)
    suspend fun appThemeDetailsDataeApii(): JsonObject
    @POST(GetSecurityCheckReport)
    suspend fun securityCheckReportApii(@Body jsonObject: JsonObject): JsonObject
    @POST(GETAPPMENUPERMISIONDATA)
    suspend fun appMenuPermissionDataApii(@Body jsonObject: JsonObject): JsonObject

    @POST(MarketerListWithSupplierCode)
    suspend fun marketerListWithSupplierCodeApii(@Body jsonObject: JsonObject): JsonObject

    @POST(SalesPartyCodeList)
    suspend fun salePartyApii(): JsonObject

    @POST(SchemeListWithSupplierCode)
    suspend fun schemeListApii(): JsonObject

    @POST(SupplierNickName)
    suspend fun suplierNickNameApii(@Body jsonObject: JsonObject): JsonObject

    @POST(MaxOrderNoByMarketer)
    suspend fun orderNoByMarketerApii(@Body jsonObject: JsonObject): JsonObject


    @POST(PartyDetailsByPartyCode)
    suspend fun partyDetailsByPartyCodeApii( @Query("accountId") accountId: String, @Query("supplierId") supplierId: String): JsonObject

    @POST(GetDispatchTypeList)
    suspend fun dispatchTypeReqApii(): JsonObject

    @POST(PcsTypeList)
    suspend fun picTypeApii(@Body jsonObject: JsonObject): JsonObject

    @POST(ItemNameList)
    suspend fun fatchItemApii(): JsonObject

    @POST(GetSaleReport)
    suspend fun saleReportApii(@Body jsonObject: JsonObject): JsonObject

    @POST(GetLedgerReportWithBalance)
    suspend fun fatchLedgerReportWithBalanceApii(@Body jsonObject: JsonObject): JsonObject

    @POST(GetSaleServiceReport)
    suspend fun fatchSaleServicesApi(@Body jsonObject: JsonObject): JsonObject

    @POST(OrderReportWithAccountID)
    suspend fun pendingOrderApii(@Body jsonObject: JsonObject): JsonObject
    @POST(GetStockInOfficeReport)
    suspend fun stockInOfficeApii(@Body jsonObject: JsonObject): JsonObject
    @POST(GetStayBookingDataList)
    suspend fun stayBookingApii(@Query("partyCode") partyCode: String): JsonObject

    @Multipart
    @POST("api/OrderBook/AddUpdateOrderBook")
    suspend fun saveOrder(
        @PartMap params: HashMap<String, RequestBody?>,
        @Part documents: List<MultipartBody.Part> = emptyList()
    ): ApiResponse

    @POST(OrderReportWithAccountID)
    suspend fun brandListApii(@Body jsonObject: JsonObject): JsonObject

    @POST(SaveOrder)
    suspend fun addOrderApii(@Body jsonObject: RequestBody
    ): JsonObject
    @POST(GetGuestMasterListByCustomerId)
    suspend fun guestListApii( @Query("accountId") accountId: String, @Query("partyCode") supplierId: String): JsonObject

    @POST(GetGuestMasterListByCustomerId)
    suspend fun guestListByPhoneNumApii( @Query("mobileNo") accountId: String, @Query("partyCode") supplierId: String): JsonObject

    @POST(BRANCH_LIST)
    suspend fun branchListApii(): JsonObject

    @POST(GetNickNameList)
    suspend fun nickNameApii(): JsonObject

    @POST(GetMainPartyAndSubPartyList)
    suspend fun customerApii(   @Query("nickNameId") nickNameId:String): JsonObject

    @POST(SAVEANDUPDATESTAYBOOKING)
    suspend fun addStayBookingApi(@Body request: JsonObject): JsonObject
    @POST(GetStayBookingDataListByBranchId)
    suspend fun fatchStayBookingListByBranchIdApi(@Query("partyCode") partyCode: String): JsonObject

    @POST(GetStayBookingDataById)
    suspend fun fatchStayBookingListByRecordIDApi(@Query("recordId") recordId: String,@Query("partyCode") partyCode: String): JsonObject

    @POST(UpdateStayBookingActualTime)
    suspend fun updateStayBookingActualTimeReqApii(@Query("bookingId") bookingId: String,@Query("actualCheckInDate") actualCheckInDate: String): JsonObject

    @POST(UpdateStayBookingActualTime)
    suspend fun updateStayBookingActualCheckOutTimeReqApii(@Query("bookingId") bookingId: String,@Query("actualCheckoutDate") actualCheckInDate: String): JsonObject

    @POST(DeleteGuestMasterData)
    suspend fun deleteGuestdApi(@Query("recordId") partyCode: String): JsonObject

    @POST(SaveUpdateGuestMasterDetails)
    suspend fun addGuestgApi(@Body request: JsonObject): JsonObject

    @POST(GetFilterListNew)
    suspend fun saleReportFilterApii(@Body jsonObject: JsonObject): JsonObject

    @POST(GetCourierReport)
    suspend fun courierReportApii(@Body jsonObject: JsonObject): JsonObject

    @POST(BlackListedName)
    suspend fun honarListApii(@Body jsonObject: JsonObject): JsonObject

    @POST(GetAllEventImages)
    suspend fun eventApii(@Body jsonObject: JsonObject): JsonObject

    @POST(GetFilterDetailList)
    suspend fun courierReportFilterApii(@Body jsonObject: JsonObject): JsonObject

    @POST(GetBrands)
    suspend fun brandListByBranchApii(@Body jsonObject: JsonObject): JsonObject

    @POST(GetBrandMasterDetails)
    suspend fun brandMasterDetailsApii(@Body jsonObject: JsonObject): JsonObject

    @POST(GetDebitNoteToCustomerReport)
    suspend fun debitNoteToCustomerReportApii(@Body jsonObject: JsonObject): JsonObject

    @POST(GetCreditNoteReport)
    suspend fun creditNoteReportApii(@Body jsonObject: JsonObject): JsonObject

    @POST(GetProfileDetails)
    suspend fun profileApii(@Body jsonObject: JsonObject): JsonObject

    @POST(GetBranches)
    suspend fun branchApii(@Body jsonObject: JsonObject): JsonObject

    @POST(GetBankDetails)
    suspend fun bankDetailApi(@Body jsonObject: JsonObject): JsonObject


}