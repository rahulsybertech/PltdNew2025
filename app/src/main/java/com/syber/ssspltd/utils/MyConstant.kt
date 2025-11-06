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

    const val GET_BANNER_LIST: String = BASE_URL + "GetBannerListNew"
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
    const val OrderReportWithAccountID: String = DOMAIN + "api/PltdApp/OrderReportWithAccountID"
    const val GetFilterDetailList: String = DOMAIN + "api/PltdApp/GetFilterDetailList"
    const val GetStockInOfficeReport: String = DOMAIN + "api/PltdApp/GetStockInOfficeReport"
    const val GetStayBookingDataList: String = DOMAIN + "api/StayBooking/GetStayBookingDataList"
    const val PcsTypeList: String = BASE_URL + "PcsTypeList"

   const val GetGuestMasterListByCustomerId: String = DOMAIN + "Api/StayBooking/GetGuestMasterListByCustomerId"



    //
    var THEMECOLUR: String = "ffff"
    var primaryCOLUR: Long = 0xFFFFC1CC
    var pendingBalance: String = "0"
    var currentBalance: String = "0"

}