package com.syber.ssspltd.Constants;

public class NewErpUrls {
    static String DOMAIN                                                        = "http://appapi.ssspltd.com/";
    public static String API_CONTROLLER                                         = "api/PltdApp/";
    public static String BASE_URL                                               = DOMAIN + API_CONTROLLER;
    public static String LOGIN = BASE_URL + "CheckMobileNo";
    // using the old API end point checking with new one
//    public static String CHECK_OTP                                              = BASE_URL + "CheckOTP";
    public static String CHECK_OTP_GO                                           = BASE_URL + "CheckOTP";
    public static String CHECK_OTP                                              = BASE_URL + "CheckMobile_New";
    public static String CLUB_TYPE_BY_ACOUNT_ID                                 = BASE_URL + "GetClubTypeByAccountId";
    public static String CLUB_TYPE_DETAILS                                      = BASE_URL + "GetClubTypeDetail";
    public static String BANK_DETAILS                                           = BASE_URL + "GetBankDetails";
    public static String GET_BRANCH_ACCOUNT                                     = BASE_URL + "GetBranchAccounts";
    public static String GET_BRANCH_BILLING                                     = BASE_URL + "GetBranchBilling";
    public static String GET_BRANCH_DETAILS                                     = BASE_URL + "GetBranchDetails";
    public static String GET_BRANCHES                                           = BASE_URL + "GetBranches";
    public static String GET_BRANCHES_GODOWN_PACKING                            = BASE_URL + "GetBranchGodownPacking";
    public static String GET_BRANCHES_GOODS_RETURN                              = BASE_URL + "GetBranchGoodsReturn";
    public static String GET_BRANCHES_MARKETERS                                 = BASE_URL + "GetBranchMarketer";
    public static String GET_COMPLETE_LEDGER_PDF                                = BASE_URL + "GetCompleteLedgerPDF";
    public static String GET_COURIER_REPORT                                     = BASE_URL + "GetCourierReport";
    public static String GET_CREDIT_NOTE_REPORT                                 = BASE_URL + "GetCreditNoteReport";
    public static String GET_CREDIT_NOTE_TO_SUPPLIER_REPORT                     = BASE_URL + "GetCreditNoteToSupplierReport";
    public static String GET_DEBIT_NOTE_REPORT                                  = BASE_URL + "GetDebitNoteReport";
    public static String GET_DEBIT_NOTE_TO_CUSTOMER_REPORT                      = BASE_URL + "GetDebitNoteToCustomerReport";
    public static String GET_DASHBOARD_ALL_DATA                                 = BASE_URL + "GetDashboardAllData";
    public static String GET_FILTER_DETAIL_LIST                                 = BASE_URL + "GetFilterDetailList";
    public static String GET_FY_YEAR_LIST                                       = BASE_URL + "GetFYearList";
    public static String GET_USER_LIST                                          = BASE_URL + "GetUserList";
    //abhinav_poor_connection
    public static String GET_USER_TYPE_LIST                                     = BASE_URL + "GetUsersTypeList";
    public static String UPDATE_POSTAGE_STATUS                                  = BASE_URL + "UpdatePostageStatus";
    public static String GET_STOCK_IN_OFFICE_REPORT                             = BASE_URL + "GetStockInOfficeReport";
    public static String GET_STOCK_IN_OFFICE_BRANCH_WISE                        = BASE_URL + "GetStockInOfficeBranchWise";
    public static String GET_SALE_AND_SERVICE_REPORT                            = BASE_URL + "GetSaleServiceReport";
    public static String GET_SALE_REPORT                                        = BASE_URL + "GetSaleReport";
    public static String GET_PENDING_ORDER_REPORT                               = BASE_URL + "GetPendingOrderReport";
    public static String GET_PENDING_ORDER_BRANCH_WISE                          = BASE_URL + "GetPendingOrderBranchWise";
    public static String GET_PROFILR_DEATILS                                    = BASE_URL + "GetProfileDetails";
    public static String GET_DASHBOARD__DEATILS_STOCK_IN_OFFICE                 = BASE_URL + "GetDashboardDetails_StockInOffice";
    public static String GET_DASHBOARD__DEATILS_BALANCE_TILL_DATE               = BASE_URL + "GetDashboardDetails_BalanceTillDate";
    public static String GET_DASHBOARD__DEATILS_BALANCE_INTREST_DISCOUNT        = BASE_URL + "GetDashboardDetails_Interest_Discount";
    public static String GET_DASHBOARD_DETAILS_PENDING_ORDER                    = BASE_URL + "GetDashboardDetails_PendingOrder";
    public static String GET_LEDGER_REPORT_WITH_BALANCE                         = BASE_URL + "GetLedgerReportWithBalance";
    public static String GET_LEDGER_REPORT                                      = BASE_URL + "GetLedgerReport";
    public static String GET_FILTER_LIST_NEW                                    = BASE_URL + "GetFilterListNew";
    //abhinav_poor_connection
    public static String GET_SECURITY_CHECK_REPORT                              = BASE_URL + "GetSecurityCheckReport";
    public static String GET_BANNER_LIST                                        = BASE_URL + "GetBannerListNew";
    public static String GET_DASHBOARD_DETAIL_GRAPH                             = BASE_URL + "GetDashboardDetail_Graph";
    public static String GET_BLACK_LIST_NAME                                    = BASE_URL + "BlackListedName";



    public static String GET_COUPON_DETAILS                                     = BASE_URL + "GetCouponDetails";
    public static String GET_KYC_INFO                                           = BASE_URL + "GetKYCinfo";
    public static String ADD_FEEDBACK                                           = BASE_URL + "AddFeedback";
    public static String SAVE_NEW_USER_DETAILS                                  = BASE_URL + "SaveNewUserDetails";
    public static String VERIFY_REFERRAL                                        = BASE_URL + "VerifyReferral";
    public static String GET_PLTD_VERSION                                       = BASE_URL + "GetPltdVersion";
    public static String GET_IMAGE_LIST_DETAILS_APP                             = BASE_URL + "GetImageListDetailsApp";

    // Below apis is not working with new base url need checking

//    public static String GET_ALL_EVENT_IMAGE                                    = BASE_URL + "GetAllEventImages";
    public static String GET_ALL_EVENT_IMAGE                                    = "http://App.ssspltd.com/apipltd/GetAllEventImages";

//    public static String GET_BRAND_MASTER_DETAIL                                = BASE_URL + "GetBrandMasterDetails";
    public static String GET_BRAND_MASTER_DETAIL                                = "http://app.ssspltd.com/apipltd/GetBrandMasterDetails";

//    public static String GET_APP_VERSION                                        = BASE_URL + "GetAppVersion";
    public static String GET_APP_VERSION                                        = "http://app.ssspltd.com/apipltd/GetAppVersion";

//    public static String GET_YEAR_WISE_ALL_IMAGES                               = BASE_URL + "GetYearWiseAllImages";
    public static String GET_YEAR_WISE_ALL_IMAGES                               = "http://app.ssspltd.com/apipltd/GetYearWiseAllImages";

    //    public static String GET_ALL_YEAR_WISE_EVENT_IMAGE                          = BASE_URL + "GetAllYearWiseEventImages";
    public static String GET_ALL_YEAR_WISE_EVENT_IMAGE                          = "http://app.ssspltd.com/apipltd/GetAllYearWiseEventImages";



    public static String ITEM_LIST                  = BASE_URL + "ItemNameList";
    public static String MARKETER_LIST              = BASE_URL + "MarketerListWithSupplierCode";
    public static String NICK_NAME                  = BASE_URL + "SupplierNickName";
    public static String ORDER_NO                   = BASE_URL + "MaxOrderNoByMarketer ";
    public static String PCS_TYPE                   = BASE_URL + "PcsTypeList";
    public static String SALE_PARTY                 = BASE_URL + "SalesPartyCodeList";
    public static String SAVE_ORDER                 = BASE_URL + "SaveOrder";
    public static String SCHEME_LIST                = BASE_URL + "SchemeListWithSupplierCode";
    public static String STATION_LIST               = BASE_URL + "StationList";
    public static String SUB_PARTY                  = BASE_URL + "SubPartyCodeList";
    public static String TRANSPORT                  = BASE_URL + "TransportStationbyAccountID";
    public static String TRANSPORT_LIST             = BASE_URL + "TransportList";
    public static String CLUB_TYPE_DETAILS_OBJECT   = BASE_URL + "ClubTypeDetails";
    public static String blackList                  = BASE_URL + "BlackListedName";
    public static String ORDER_REPORT               = BASE_URL + "OrderReportWithAccountID";
    public static String UPDATE_ORDER_STATUS        = BASE_URL + "ChangeOrderStatus";

    //Booking order module
    public static String BRANCH_LIST               = DOMAIN + "Api/StayBooking/GetBranchDetailList";
    public static String SAVE_UPDATEBOOKING = DOMAIN + "Api/StayBooking/SaveAndUpdateStayBooking";
    public static String StayBookingDataList = DOMAIN + "api/StayBooking/GetStayBookingDataList";
    public static String CancelStayBooking = DOMAIN + "api/StayBooking/CancelStayBooking";

  }
