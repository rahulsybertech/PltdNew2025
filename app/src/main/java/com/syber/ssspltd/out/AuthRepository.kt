package com.app.naturalhigh.out

import com.google.gson.JsonObject
import com.syber.ssspltd.data.model.bookingRequest.StayBookingRequest
import com.syber.ssspltd.network.ApiService
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject


class AuthRepository @Inject constructor(private val api: ApiService, ) : BaseRepository(api) {

    suspend fun loginApi(jsonObject: JsonObject) = safeApiCall {
        api.loginApii(jsonObject)
    }
     suspend fun userTypeApi(jsonObject: JsonObject) = safeApiCall {
        api.userTypeApii(jsonObject)
    }
    suspend fun signupApi(jsonObject: JsonObject) = safeApiCall {
        api.signupApii(jsonObject)
    }
    suspend fun verifyReferralApi(jsonObject: JsonObject, refferalCode: String) = safeApiCall {
        api.verifyReferralApii(refferalCode)
    }

    suspend fun bannerApi(jsonObject: JsonObject) = safeApiCall {
        api.bannerApii(jsonObject)
    }
    suspend fun checkOtpApi(jsonObject: JsonObject) = safeApiCall {
        api.checkOtpApii(jsonObject)
    }

    suspend fun resendOtpApi(jsonObject: JsonObject) = safeApiCall {
        api.resendOtpApii(jsonObject)
    }
    suspend fun appVersionApi(jsonObject: JsonObject) = safeApiCall {
        api.appVersionApii(jsonObject)
    }
    suspend fun fYearListApi(jsonObject: JsonObject) = safeApiCall {
        api.fYearListApii(jsonObject)
    }
    suspend fun paymentDashboard(jsonObject: JsonObject) = safeApiCall {
        api.paymentDashboardApii(jsonObject)
    }
    suspend fun getDashboardDetailsBalanceTillDate(jsonObject: JsonObject) = safeApiCall {
        api.GetDashboardDetailsBalanceTillDateApi(jsonObject)
    }

    suspend fun dashboardDetailsInteresDiscountReq(jsonObject: JsonObject) = safeApiCall {
        api.dashboardDetailsInterestDiscountApi(jsonObject)
    }

    suspend fun dashboardDetailsStockInOfficetReq(jsonObject: JsonObject) = safeApiCall {
        api.dashboardDetailsStockInOfficeApi(jsonObject)
    }
    suspend fun dashboardDetailsPendingOrderReq(jsonObject: JsonObject) = safeApiCall {
        api.dashboardDetailsPendingOrderApi(jsonObject)
    }
    suspend fun appThemeDetailsApi(jsonObject: JsonObject) = safeApiCall {
        api.appThemeDetailsDataeApii()
    }
    suspend fun securityCheckReportApi(jsonObject: JsonObject) = safeApiCall {
        api.securityCheckReportApii(jsonObject)
    }
    suspend fun appMenuPermissionDataApi(jsonObject: JsonObject) = safeApiCall {
        api.appMenuPermissionDataApii(jsonObject)
    }

    suspend fun marketerListWithSupplierCodeApi(jsonObject: JsonObject) = safeApiCall {
        api.marketerListWithSupplierCodeApii(jsonObject)
    }
    suspend fun salePartyApi() = safeApiCall {
        api.salePartyApii()
    }
    suspend fun schemeListApi() = safeApiCall {
        api.schemeListApii()
    }
    suspend fun suplierNickNameApi(jsonObject: JsonObject) = safeApiCall {
        api.suplierNickNameApii(jsonObject)
    }

    suspend fun orderNoByMarketerApi(jsonObject: JsonObject) = safeApiCall {
        api.orderNoByMarketerApii(jsonObject)
    }
    suspend fun partyDetailsByPartyCodeApi(accountId: String,supplierId: String) = safeApiCall {
        api.partyDetailsByPartyCodeApii(accountId,supplierId)
    }
    suspend fun dispatchTypeReqApi() = safeApiCall {
        api.dispatchTypeReqApii()
    }
    suspend fun picTypeApi(jsonObject: JsonObject) = safeApiCall {
        api.picTypeApii(jsonObject)
    }
    suspend fun dispatchTypeApi() = safeApiCall {
        api.fatchItemApii()
    }
    suspend fun saleReportApi(jsonObject: JsonObject) = safeApiCall {
        api.saleReportApii(jsonObject)
    }
    suspend fun fatchLedgerReportWithBalanceApi(jsonObject: JsonObject) = safeApiCall {
        api.fatchLedgerReportWithBalanceApii(jsonObject)
    }
    suspend fun fatchSaleServicesApi(jsonObject: JsonObject) = safeApiCall {
        api.fatchSaleServicesApi(jsonObject)
    }
    suspend fun pendingOrderApi(jsonObject: JsonObject) = safeApiCall {
        api.pendingOrderApii(jsonObject)
    }
    suspend fun stockInOfficeApi(jsonObject: JsonObject) = safeApiCall {
        api.stockInOfficeApii(jsonObject)
    }
    suspend fun stayBookingApi(partyCode: String) = safeApiCall {
        api.stayBookingApii(partyCode)
    }

    suspend fun fatchStayBookingListByBranchId(partyCode: String) = safeApiCall {
        api.fatchStayBookingListByBranchIdApi(partyCode)
    }
    suspend fun fatchStayBookingListByRecordID(recordID: String,partyCode: String) = safeApiCall {
        api.fatchStayBookingListByRecordIDApi(recordID,partyCode)
    }



    suspend fun updateStayBookingActualTimeReqApi(bookingId: String,actualCheckInDate: String) = safeApiCall {
        api.updateStayBookingActualTimeReqApii(bookingId,actualCheckInDate)
    }
    suspend fun updateStayBookingActualCheckOutTimeTimeReqApi(bookingId: String,actualCheckInDate: String) = safeApiCall {
        api.updateStayBookingActualCheckOutTimeReqApii(bookingId,actualCheckInDate)
    }

    suspend fun placeOrder(
        params: HashMap<String, RequestBody?>,
        documents: List<MultipartBody.Part>?
    )=safeApiCall {
        // Directly call Retrofit API and return response
        if (documents != null) {
            api.saveOrder(params, documents)
        }
    }

    suspend fun brandListApi(jsonObject: JsonObject) = safeApiCall {
        api.pendingOrderApii(jsonObject)
    }
    suspend fun guestListApi(accountId: String,partyCode: String) = safeApiCall {
        api.guestListApii(accountId,partyCode)
    }

    suspend fun guestListByPhoneNumApi(accountId: String,partyCode: String) = safeApiCall {
        api.guestListByPhoneNumApii(accountId,partyCode)
    }

    suspend fun guestBranchApi() = safeApiCall {
        api.branchListApii()
    }
    suspend fun nickNameApi() = safeApiCall {
        api.nickNameApii()
    }
    suspend fun customerApi(nickNameId:String) = safeApiCall {
        api.customerApii(nickNameId)
    }
    suspend fun addOrderApi(jsonObject: RequestBody) = safeApiCall {
        api.addOrderApii(jsonObject)
    }
    suspend fun addStayBooking(request: JsonObject) = safeApiCall {
        api.addStayBookingApi(request)
    }
    suspend fun addGuest(request: JsonObject) = safeApiCall {
        api.addGuestgApi(request)
    }
    suspend fun deleteGuest(request: String) = safeApiCall {
        api.deleteGuestdApi(request)
    }
    suspend fun saleReportFilterApi(jsonObject: JsonObject) = safeApiCall {
        api.saleReportFilterApii(jsonObject)
    }
    suspend fun courierReportApi(jsonObject: JsonObject) = safeApiCall {
        api.courierReportApii(jsonObject)
    }

    suspend fun honarListApi(jsonObject: JsonObject) = safeApiCall {
        api.honarListApii(jsonObject)
    }
    suspend fun eventApi(jsonObject: JsonObject) = safeApiCall {
        api.eventApii(jsonObject)
    }
    suspend fun courierReportFilterApi(jsonObject: JsonObject) = safeApiCall {
        api.courierReportFilterApii(jsonObject)
    }


    suspend fun brandListByBranchApi(jsonObject: JsonObject) = safeApiCall {
        api.brandListByBranchApii(jsonObject)
    }
    suspend fun brandMasterDetailsApi(jsonObject: JsonObject) = safeApiCall {
        api.brandMasterDetailsApii(jsonObject)
    }
    suspend fun debitNoteToCustomerReportApi(jsonObject: JsonObject) = safeApiCall {
        api.debitNoteToCustomerReportApii(jsonObject)
    }
    suspend fun creditNoteReportApi(jsonObject: JsonObject) = safeApiCall {
        api.creditNoteReportApii(jsonObject)
    }
}