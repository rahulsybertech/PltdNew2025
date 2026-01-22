package com.syber.ssspltd.utils

object MyConstant {
    //url
   // http://appapi.ssspltd.com/api/SssPltdApp

   /////////////////new url
    const val DOMAIN: String = "http://appapi.ssspltd.com/"
    const val API_CONTROLLER: String = "api/SssPltdApp/"


/*   ////////////////////old url
    const val DOMAIN: String = "http://appapi.ssspltd.com/"
    const val API_CONTROLLER: String = "api/PltdApp/"*/

    const val BASE_URL: String = DOMAIN + API_CONTROLLER

    const val LOGIN: String = BASE_URL + "CheckMobileNo"
    const val SaveNewUserDetails: String = BASE_URL + "SaveNewUserDetails"
    const val USERTYPELIST: String = BASE_URL + "GetUsersTypeList"
    const val VERIFY_REFERRAL: String = BASE_URL + "VerifyReferral"

    const val GET_BANNER_LIST: String = DOMAIN + "api/PltdApp/GetBannerListNew"
    const val CHECKOTP: String = BASE_URL + "CheckOTP"
    const val CheckMobile_New: String = BASE_URL + "CheckMobile_New"
    const val GET_APP_VERSION: String = "http://app.ssspltd.com/apipltd/GetAppVersion"
    const val FYEARLIST: String = BASE_URL+"GetFYearList"
    const val GetDashboardDetail_Graph: String = DOMAIN+"api/PltdApp/GetDashboardDetail_Graph"
    const val GetSecurityCheckReport: String = BASE_URL+"GetSecurityCheckReport"
    const val GetDashboardDetails_BalanceTillDate: String = DOMAIN+"api/PltdApp/GetDashboardDetails_BalanceTillDate"
    const val GetDashboardDetails_Interest_Discount: String = DOMAIN+"api/PltdApp/GetDashboardDetails_Interest_Discount"
    const val GetDashboardDetails_StockInOffice: String = DOMAIN+"api/PltdApp/GetDashboardDetails_StockInOffice"
    const val GetDashboardDetails_PendingOrder: String = DOMAIN+"api/PltdApp/GetDashboardDetails_PendingOrder"
    const val GETAPPMENUPERMISIONDATA: String = BASE_URL+"GetAppMenuPermissionData"
    const val GetAppThemeDetailsData: String = BASE_URL + "GetAppThemeDetailsData"
    const val MarketerListWithSupplierCode: String = BASE_URL + "MarketerListWithSupplierCode"
    const val SalesPartyCodeList: String = BASE_URL + "SalesPartyCodeList"
    const val SchemeListWithSupplierCode: String = BASE_URL + "SchemeListWithSupplierCode"
    const val SupplierNickName: String = BASE_URL + "SupplierNickName"
    const val MaxOrderNoByMarketer: String = BASE_URL + "MaxOrderNoByMarketer"
    const val PartyDetailsByPartyCode: String = BASE_URL + "PartyDetailsByPartyCode"
    const val GetDispatchTypeList: String = BASE_URL + "GetDispatchTypeList"
    const val ItemNameList: String = BASE_URL + "ItemNameList"
    const val GetSaleReport: String = DOMAIN + "api/PltdApp/GetSaleReport"
    const val GetLedgerReportWithBalance: String = DOMAIN + "api/PltdApp/GetLedgerReportWithBalance"
    const val GetSaleServiceReport: String = DOMAIN + "api/PltdApp/GetSaleServiceReport"
    const val OrderReportWithAccountID: String = DOMAIN + "api/PltdApp/OrderReportWithAccountID"

    const val GetStockInOfficeReport: String = DOMAIN + "api/PltdApp/GetStockInOfficeReport"
    const val GetStayBookingDataList: String = DOMAIN + "api/StayBooking/GetStayBookingDataList"
    const val PcsTypeList: String = BASE_URL + "PcsTypeList"

   const val GetGuestMasterListByCustomerId: String = DOMAIN + "Api/StayBooking/GetGuestMasterListByCustomerId"
   const val BRANCH_LIST: String = DOMAIN + "Api/StayBooking/GetBranchDetailList"
   const val SAVEANDUPDATESTAYBOOKING: String = DOMAIN + "Api/StayBooking/SaveAndUpdateStayBooking1"

    const val GetStayBookingDataListByBranchId: String =
        DOMAIN + "api/StayBooking/GetStayBookingDataListByBranchId"
    const val GetStayBookingDataById: String =
        DOMAIN + "api/StayBooking/GetStayBookingDataById"

    const val GetFilterListNew: String = DOMAIN + "api/PltdApp/GetFilterListNew"
    const val GetBrands: String = DOMAIN + "api/PltdApp/GetBrands"
    const val GetBrandMasterDetails: String = DOMAIN + "api/PltdApp/GetBrandMasterDetails"


    const val GetAllEventImages: String = DOMAIN + "api/PltdApp/GetAllEventImages"
    const val BlackListedName: String = DOMAIN + "api/PltdApp/BlackListedName"


    //CourierReport
    const val GetCourierReport: String = DOMAIN + "api/PltdApp/GetCourierReport"
    const val GetFilterDetailList: String = DOMAIN + "api/PltdApp/GetFilterDetailList"
    const val GetFYearList: String = DOMAIN + "api/PltdApp/GetFYearList"
    const val GetUserList: String = DOMAIN + "api/PltdApp/GetUserList"

    //GetDebitNoteToCustomerReport
    const val GetDebitNoteToCustomerReport: String = DOMAIN + "api/PltdApp/GetDebitNoteToCustomerReport"



    /*    GUEST*/
  const val SaveUpdateGuestMasterDetails: String = DOMAIN + "Api/StayBooking/SaveUpdateGuestMasterDetails"
  const val DeleteGuestMasterData: String = DOMAIN + "Api/StayBooking/DeleteGuestMasterData"
  const val GetNickNameList: String = DOMAIN + "Api/StayBooking/GetNickNameList"
  const val GetMainPartyAndSubPartyList: String = DOMAIN + "Api/StayBooking/GetMainPartyAndSubPartyList"
  const val UpdateStayBookingActualTime: String = DOMAIN + "api/StayBooking/UpdateStayBookingActualTime"





 const val SaveOrder: String = DOMAIN + "api/SssPltdApp/SaveOrder"
    //
    var THEMECOLUR: String = "ffff"
    var primaryCOLUR: Long = 0xFFFFC1CC
    var pendingBalance: String = "0"
    var currentBalance: String = "0"

}