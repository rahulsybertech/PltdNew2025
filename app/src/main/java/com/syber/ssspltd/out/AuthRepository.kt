package com.app.naturalhigh.out

import com.google.gson.JsonObject
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
    suspend fun pendingOrderApi(jsonObject: JsonObject) = safeApiCall {
        api.pendingOrderApii(jsonObject)
    }
    suspend fun stockInOfficeApi(jsonObject: JsonObject) = safeApiCall {
        api.stockInOfficeApii(jsonObject)
    }
    suspend fun stayBookingApi(partyCode: String) = safeApiCall {
        api.stayBookingApii(partyCode)
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


}

  /*  suspend fun loginApi(jsonObject: JsonObject) = safeApiCall {
        api.registerApi(jsonObject)
    }

 /*   suspend fun googleLoginApi(jsonObject: JsonObject) = safeApiCall {
        api.googleLoginApii(jsonObject)
    }

    suspend fun forgotPasswordReq(jsonObject: JsonObject) = safeApiCall {
        api.forgotPasswordApi(jsonObject)
    }

    suspend fun resetPasswordReq(jsonObject: JsonObject) = safeApiCall {
        api.resetPasswordApi(jsonObject)
    }

    suspend fun verifyOtpReq(jsonObject: JsonObject) = safeApiCall {
        api.verifyOtpApi(jsonObject)
    }

    suspend fun saveProfile(jsonObject: JsonObject) = safeApiCall {
        api.saveProfileApi(jsonObject)
    }

    suspend fun viewProfile() = safeApiCall {
        api.viewProfileApi()
    }

    suspend fun logoutReq() = safeApiCall {
        api.logoutApi()
    }

    suspend fun getLastPlungeData() = safeApiCall {
        api.getLastPlungeData()
    }

    suspend fun savePlungeData(jsonObject: JsonObject) = safeApiCall {
        api.savePlungeData(jsonObject)
    }

    suspend fun updatePlungeData(jsonObject: JsonObject) = safeApiCall {
        api.updatePlungeData(jsonObject)
    }

    suspend fun viewPlungeData() = safeApiCall {
        api.viewPlungeData()
    }

    suspend fun registerApi(jsonObject: JsonObject) = safeApiCall {
        api.registerApi(jsonObject)
    }

    suspend fun forgetPasswordApi(jsonObject: JsonObject) = safeApiCall {
        api.forgetPassword(jsonObject)
    }

    suspend fun otpVerificationApi(jsonObject: JsonObject) = safeApiCall {
        api.otpVerification(jsonObject)
    }

    suspend fun getEmployeeRangeApi() = safeApiCall {
        api.getEmployeeRange()
    }

    suspend fun getIndustriesApi() = safeApiCall {
        api.getIndustries()
    }

    suspend fun createCompanyProfileApi(jsonObject: JsonObject) = safeApiCall {
        api.createCompanyProfile(jsonObject)
    }

    suspend fun getJobPositionsApi(jsonObject: JsonObject) = safeApiCall {
        api.getJobPositions(jsonObject)
    }

    suspend fun getCompanyPositionsApi(jsonObject: JsonObject) = safeApiCall {
        api.getCompanyPositions(jsonObject)
    }

    suspend fun createPositionApi(jsonObject: JsonObject) = safeApiCall {
        api.createPosition(jsonObject)
    }

    suspend fun setCompanyPositionApi(setCompanyPositionRequest: SetCompanyPositionRequest) =
        safeApiCall {
            api.setCompanyPosition(setCompanyPositionRequest)
        }

    suspend fun setImportShiftTemplatesApi(data: Map<String, String>) = safeApiCall {
        api.setShiftTemplates(data)
    }

    suspend fun getUserRoleApi() = safeApiCall {
        api.getUserRole()
    }

    suspend fun getShiftTemplateListApi(jsonObject: JsonObject) = safeApiCall {
        api.getShiftTemplateList(jsonObject)
    }

    suspend fun getCompanyTagsListApi(jsonObject: JsonObject) = safeApiCall {
        api.getCompanyTagsList(jsonObject)
    }

    suspend fun getBenefitsListApi(jsonObject: JsonObject) = safeApiCall {
        api.getBenefitsList(jsonObject)
    }

    suspend fun getTimeZoneApi() = safeApiCall {
        api.getTimeZone()
    }

    suspend fun createTagsApi(jsonObject: JsonObject) = safeApiCall {
        api.createTags(jsonObject)
    }

    suspend fun createShiftTemplateApi(jsonObject: JsonObject) = safeApiCall {
        api.createShiftTemplate(jsonObject)
    }

    suspend fun createBenefitsApi(jsonObject: JsonObject) = safeApiCall {
        api.createBenefit(jsonObject)
    }

    suspend fun updateEmployeeApi(file: RequestBody) = safeApiCall {
        api.updateEmployee(file)
    }

    *//*mamta*//*
    suspend fun getEmployeeListApi(jsonObject: JsonObject) = safeApiCall {
        api.getEmployeeList(jsonObject)
    }

    suspend fun getReportingToListApi(jsonObject: JsonObject) = safeApiCall {
        api.createReportingTo(jsonObject)
    }

    suspend fun getEmployeeDetailsApi(jsonObject: JsonObject) = safeApiCall {
        api.getEmployeeProfileViewTo(jsonObject)
    }

    suspend fun getEmployeeDetailsApiForEdit(jsonObject: JsonObject) = safeApiCall {
        api.getEmployeeEmployeeDetailForEdit(jsonObject)
    }

    suspend fun updateCompanyPositionApi(jsonObject: JsonObject) = safeApiCall {
        api.updateCompanyPosition(jsonObject)
    }

    suspend fun deletePositionApi(jsonObject: JsonObject) = safeApiCall {
        api.deletePosition(jsonObject)
    }

    suspend fun updateTagsApi(jsonObject: JsonObject) = safeApiCall {
        api.updateTags(jsonObject)
    }

    suspend fun deleteTagApi(jsonObject: JsonObject) = safeApiCall {
        api.deleteTag(jsonObject)
    }

    suspend fun updateShiftTemplateApi(jsonObject: JsonObject) = safeApiCall {
        api.updateShiftTemplate(jsonObject)
    }

    suspend fun deleteShiftTemplatesApi(jsonObject: JsonObject) = safeApiCall {
        api.deleteShiftTemplates(jsonObject)
    }

    suspend fun updateBenefitsApi(jsonObject: JsonObject) = safeApiCall {
        api.updateBenefit(jsonObject)
    }

    suspend fun deleteBenefitsApi(jsonObject: JsonObject) = safeApiCall {
        api.deleteBenefits(jsonObject)
    }

    suspend fun assignBonusApi(jsonObject: JsonObject) = safeApiCall {
        api.assignBonus(jsonObject)
    }

    suspend fun employeeBonusListApi(jsonObject: JsonObject) = safeApiCall {
        api.employeeBonusList(jsonObject)
    }

    suspend fun updateBonusApi(jsonObject: JsonObject) = safeApiCall {
        api.updateBonus(jsonObject)
    }

    suspend fun deleteBonusApi(jsonObject: JsonObject) = safeApiCall {
        api.deleteBonus(jsonObject)
    }

    suspend fun uploadDocumentsApi(file: RequestBody) = safeApiCall {
        api.uploadDocuments(file)
    }

    suspend fun companyDocListApi(jsonObject: JsonObject) = safeApiCall {
        api.companyDocList(jsonObject)
    }

    suspend fun deleteCompanyDocApi(jsonObject: JsonObject) = safeApiCall {
        api.deleteCompanyDoc(jsonObject)
    }

    suspend fun updateCompanyDocApi(file: RequestBody) = safeApiCall {
        api.updateCompanyDoc(file)
    }

    suspend fun deleteEmployeeApi(jsonObject: JsonObject) = safeApiCall {
        api.deleteEmployee(jsonObject)
    }

    suspend fun deleteBenefitEmployeeApi(jsonObject: JsonObject) = safeApiCall {
        api.deleteBenefitEmployee(jsonObject)
    }

    suspend fun createAddEmployeeShiftApi(jsonObject: JsonObject) = safeApiCall {
        api.createSchedule(jsonObject)
    }

    suspend fun updateScheduleApi(jsonObject: JsonObject) = safeApiCall {
        api.updateScheduler(jsonObject)
    }

    suspend fun getEmployeeOpenShiftApi(jsonObject: JsonObject) = safeApiCall {
        api.getEmployeeOpenShift(jsonObject)
    }

    suspend fun updateScheduleDateApi(jsonObject: JsonObject) = safeApiCall {
        api.updateScheduleDate(jsonObject)
    }

    suspend fun editScheduleApi(jsonObject: JsonObject) = safeApiCall {
        api.editScheduleData(jsonObject)
    }

    suspend fun getAllLeaveRequestsListApi(jsonObject: JsonObject) = safeApiCall {
        api.getAllLeaveRequestsList(jsonObject)
    }

    suspend fun getPendingLeaveRequestsListApi(jsonObject: JsonObject) = safeApiCall {
        api.getPendingLeaveRequestsList(jsonObject)
    }

    suspend fun getViewLeaveDetailsApi(jsonObject: JsonObject) = safeApiCall {
        api.getViewLeaveRequestData(jsonObject)
    }

    suspend fun checkEmployeeAvailabilityApi(jsonObject: JsonObject) = safeApiCall {
        api.checkEmployeeAvailabilityList(jsonObject)
    }

    suspend fun shareDocumentApi(jsonObject: JsonObject) = safeApiCall {
        api.shareDocumentEmployeeList(jsonObject)
    }

    suspend fun deleteScheduleApi(jsonObject: JsonObject) = safeApiCall {
        api.deleteSchedule(jsonObject)
    }

    suspend fun assignScheduleToEmpApi(jsonObject: JsonObject) = safeApiCall {
        api.assignSchedulerToEmployee(jsonObject)
    }


    suspend fun getAllTimeTrackApi(jsonObject: JsonObject) = safeApiCall {
        api.getAllTimeTrack(jsonObject)
    }

    suspend fun getEmployeeTrackingApi(jsonObject: JsonObject) = safeApiCall {
        api.getEmployeeTracking(jsonObject)
    }

    suspend fun getEmpTrackingEditApi(jsonObject: JsonObject) = safeApiCall {
        api.getEmpTrackingEditData(jsonObject)
    }

    suspend fun employerProfileApi(jsonObject: JsonObject) = safeApiCall {
        api.employerProfile(jsonObject)
    }

    suspend fun updateEmpProfileApi(file: RequestBody) = safeApiCall {
        api.updateEmployerProfile(file)
    }

    suspend fun employerDashboardApi(jsonObject: JsonObject) = safeApiCall {
        api.employerDashboard(jsonObject)
    }

    suspend fun getNotificationReq(jsonObject: JsonObject) = safeApiCall {
        api.getNotification(jsonObject)
    }

    suspend fun requestLeaveTypeApi() = safeApiCall {
        api.requestLeaveTypeApi()
    }

    suspend fun importFileApi(file: RequestBody) = safeApiCall {
        api.importEmployeeFile(file)
    }

    suspend fun getDocShareHistoryApi(jsonObject: JsonObject) = safeApiCall {
        api.getDocShareHistory(jsonObject)
    }

    suspend fun serverKeyApi() = safeApiCall {
        api.getServerKey()
    }


    suspend fun updatePasswordReq(jsonObject: JsonObject) = safeApiCall {
        api.updatePasswordApi(jsonObject)
    }

    suspend fun updateUserProfileReq(jsonObject: JsonObject) = safeApiCall {
        api.updateUserProfileApi(jsonObject)
    }

    suspend fun weeklystatsReq(jsonObject: JsonObject)= safeApiCall {
        api.weeklystatsApi(jsonObject)
    }
    suspend fun monthlystatsReq(jsonObject: JsonObject)= safeApiCall {
        api.monthlytatsApi(jsonObject)
    }
    suspend fun yearlystatsReq(jsonObject: JsonObject)= safeApiCall {
        api.yearlystatsApi(jsonObject)
    }

    suspend fun getstreakData()= safeApiCall {
        api.getstreakData()
    }


    suspend fun getCoachListReq() = safeApiCall {
        api.getCoachListApi()
    }

    suspend fun getCoachDetailsReq(jsonObject: JsonObject) = safeApiCall {
        api.getCoachDetailsApi(jsonObject)
    }
    suspend fun getSessionListReq(jsonObject: JsonObject) = safeApiCall {
        api.getSessionListApi(jsonObject)
    }

    suspend fun getSessionDetail(jsonObject: JsonObject) = safeApiCall {
        api.sessionDetailsApi(jsonObject)
    }
    suspend fun getRecommendationDetail(jsonObject: JsonObject) = safeApiCall {
        api.recommendationApi(jsonObject)
    }

    suspend fun screenApi() = safeApiCall {
        api.getScreenApi()
    }
    suspend fun modeApi() = safeApiCall {
        api.modeApi()
    }

    suspend fun braintreeToken() = safeApiCall {
        api.getBraintreeTokenApi()
    }

    suspend fun paymentProcess(jsonObject: JsonObject) = safeApiCall {
        api.paymentProcessApi(jsonObject)
    }
*/