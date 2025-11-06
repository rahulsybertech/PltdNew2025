package com.syber.ssspltd.out

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.app.naturalhigh.out.AuthRepository
import com.app.naturalhigh.out.BaseViewModell
import com.google.gson.JsonObject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import net.simplifiedcoding.data.network.Resource
import javax.inject.Inject
import com.google.gson.Gson
import com.ssspvtltd.quick.model.order.add.additem.PackType
import com.syber.ssspltd.data.model.ImageModel
import com.syber.ssspltd.data.model.OrderData
import com.syber.ssspltd.data.model.OrderResponse
import com.syber.ssspltd.data.model.PcsTypeResponse
import com.syber.ssspltd.data.model.addorder.DispatchTypeResponse
import com.syber.ssspltd.data.model.addorder.ItemResponse
import com.syber.ssspltd.data.model.addorder.PartyDetailsResponse
import com.syber.ssspltd.data.model.addorder.SupplierNickNameResponse
import com.syber.ssspltd.data.model.ledger.LedgerResponse
import com.syber.ssspltd.data.model.saleReport.SaleReportResponse
import com.syber.ssspltd.data.model.staybooking.GuestMasterDetail
import com.syber.ssspltd.data.model.staybooking.GuestResponse
import com.syber.ssspltd.data.model.staybooking.StayBookingResponse
import com.syber.ssspltd.data.model.staybooking.StayBookingResult
import com.syber.ssspltd.data.model.stockinoffice.StockInOfficeResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.HttpException
import java.io.File
import java.io.IOException


@HiltViewModel
 class AuthViewModel @Inject constructor(private val gson: Gson,private val repository: AuthRepository) : BaseViewModell(repository){

    var addItemDataList = ArrayList<PackType>()
    var addImageDataList = ArrayList<ImageModel>()


    // Login Api
        val _loginResponse: MutableLiveData<Resource<JsonObject>> = MutableLiveData()
        val loginResponse: LiveData<Resource<JsonObject>> get() = _loginResponse

        fun login(jsonObject: JsonObject) = viewModelScope.launch {
            _loginResponse.value = Resource.Loading
            _loginResponse.value = repository.loginApi(jsonObject)
        }


    // User Type Api
    val _userTypeResponse: MutableLiveData<Resource<JsonObject>> = MutableLiveData()
    val userTypeResponse: LiveData<Resource<JsonObject>> get() = _userTypeResponse

    fun userType(jsonObject: JsonObject) = viewModelScope.launch {
        _userTypeResponse.value = Resource.Loading
        _userTypeResponse.value = repository.userTypeApi(jsonObject)
    }

    // Signup Api
    val _signUpResponse: MutableLiveData<Resource<JsonObject>> = MutableLiveData()
    val signUpResponse: LiveData<Resource<JsonObject>> get() = _signUpResponse

    fun signUpResponse(jsonObject: JsonObject) = viewModelScope.launch {
        _signUpResponse.value = Resource.Loading
        _signUpResponse.value = repository.signupApi(jsonObject)
    }

    // Referral Api
    val _verifyReferralResponse: MutableLiveData<Resource<JsonObject>> = MutableLiveData()
    val verifyReferralResponse: LiveData<Resource<JsonObject>> get() = _verifyReferralResponse

    fun verifyReferralResponseResponse(jsonObject: JsonObject, refferalCode: String) = viewModelScope.launch {
        _verifyReferralResponse.value = Resource.Loading
        _verifyReferralResponse.value = repository.verifyReferralApi(jsonObject,refferalCode)
    }

    // BANNER LIST API
    val _bannerListResponse: MutableLiveData<Resource<JsonObject>> = MutableLiveData()
    val bannerListResponse: LiveData<Resource<JsonObject>> get() = _bannerListResponse

    fun bannerList(jsonObject: JsonObject) = viewModelScope.launch {
        _bannerListResponse.value = Resource.Loading
        _bannerListResponse.value = repository.bannerApi(jsonObject)

    }
    // CheckOTP  API
    val _checkOtpResponse: MutableLiveData<Resource<JsonObject>> = MutableLiveData()
    val checkOtpResponse: LiveData<Resource<JsonObject>> get() = _checkOtpResponse

    fun checkOtp(jsonObject: JsonObject) = viewModelScope.launch {
        _checkOtpResponse.value = Resource.Loading
        _checkOtpResponse.value = repository.checkOtpApi(jsonObject)

    }

    // Login Api
    val _resendOtpResponse: MutableLiveData<Resource<JsonObject>> = MutableLiveData()
    val resendOtpResponse: LiveData<Resource<JsonObject>> get() = _resendOtpResponse

    fun resendOtp(jsonObject: JsonObject) = viewModelScope.launch {
        _resendOtpResponse.value = Resource.Loading
        _resendOtpResponse.value = repository.resendOtpApi(jsonObject)
    }


    // Login Api
    val _appVersionResponse: MutableLiveData<Resource<JsonObject>> = MutableLiveData()
    val appVersionResponse: LiveData<Resource<JsonObject>> get() = _appVersionResponse

    fun appVersion(jsonObject: JsonObject) = viewModelScope.launch {
        _appVersionResponse.value = Resource.Loading
        _appVersionResponse.value = repository.appVersionApi(jsonObject)
    }

    val _fYearListResponse: MutableLiveData<Resource<JsonObject>> = MutableLiveData()
    val fYearListResponse: LiveData<Resource<JsonObject>> get() = _fYearListResponse

    fun fYearReq(jsonObject: JsonObject) = viewModelScope.launch {
        _fYearListResponse.value = Resource.Loading
        _fYearListResponse.value = repository.fYearListApi(jsonObject)
    }


    val _appThemeDetailsResponse: MutableLiveData<Resource<JsonObject>> = MutableLiveData()
    val appThemeDetailsResponse: LiveData<Resource<JsonObject>> get() = _appThemeDetailsResponse

    fun appThemeDetailsReq(jsonObject: JsonObject) = viewModelScope.launch {
        _appThemeDetailsResponse.value = Resource.Loading
        _appThemeDetailsResponse.value = repository.appThemeDetailsApi(jsonObject)
    }


    val _securityCheckReportResponse: MutableLiveData<Resource<JsonObject>> = MutableLiveData()
    val securityCheckReportResponse: LiveData<Resource<JsonObject>> get() = _securityCheckReportResponse

    fun securityCheckReportReq(jsonObject: JsonObject) = viewModelScope.launch {
        _securityCheckReportResponse.value = Resource.Loading
        _securityCheckReportResponse.value = repository.securityCheckReportApi(jsonObject)

    }


    val _appMenuPermissionDataResponse: MutableLiveData<Resource<JsonObject>> = MutableLiveData()
    val appMenuPermissionDataResponse: LiveData<Resource<JsonObject>> get() = _appMenuPermissionDataResponse

    fun appMenuPermissionDataReq(jsonObject: JsonObject) = viewModelScope.launch {
        _appMenuPermissionDataResponse.value = Resource.Loading
        _appMenuPermissionDataResponse.value = repository.appMenuPermissionDataApi(jsonObject)

    }

    val _marketerListWithSupplierCodeResponse: MutableLiveData<Resource<JsonObject>> = MutableLiveData()
    val marketerListWithSupplierCodeResponse: LiveData<Resource<JsonObject>> get() = _marketerListWithSupplierCodeResponse

    fun marketerListWithSupplierCodeReq(jsonObject: JsonObject) = viewModelScope.launch {
        _marketerListWithSupplierCodeResponse.value = Resource.Loading
        _marketerListWithSupplierCodeResponse.value = repository.marketerListWithSupplierCodeApi(jsonObject)

    }


    val _salesPartyCodeListResponse: MutableLiveData<Resource<JsonObject>> = MutableLiveData()
    val salesPartyCodeListResponse: LiveData<Resource<JsonObject>> get() = _salesPartyCodeListResponse

    fun salesPartyReq() = viewModelScope.launch {
        _salesPartyCodeListResponse.value = Resource.Loading
        _salesPartyCodeListResponse.value = repository.salePartyApi()

    }

    val _schemeListResponse: MutableLiveData<Resource<JsonObject>> = MutableLiveData()
    val schemeListResponse: LiveData<Resource<JsonObject>> get() = _schemeListResponse

    fun schemListReq() = viewModelScope.launch {
        _schemeListResponse.value = Resource.Loading
        _schemeListResponse.value = repository.schemeListApi()

    }


    fun suplierNickNameFlow(jsonObject: JsonObject): Flow<Resource<SupplierNickNameResponse>> = flow {
        emit(Resource.Loading)
        when (val result = repository.suplierNickNameApi(jsonObject)) {
            is Resource.Success -> {
                try {
                    val parsed = Gson().fromJson(result.value, SupplierNickNameResponse::class.java)
                    emit(Resource.Success(parsed))
                } catch (e: Exception) {
                    emit(Resource.Failure(
                        isNetworkError = e.message!!,
                        errorCode = null,
                        errorBody = "Parsing error: ${e.localizedMessage}"
                    ))
                }
            }

            is Resource.Failure -> {
                emit(Resource.Failure(
                    isNetworkError = "",
                    errorCode = result.errorCode,
                    errorBody = result.errorBody ?: "Unknown error"
                ))
            }

            else -> {
                emit(Resource.Failure(
                    isNetworkError = "false",
                    errorCode = null,
                    errorBody = "Unexpected error occurred"
                ))
            }
        }
    }.flowOn(Dispatchers.IO)


    fun orderNoByMarketerReq(jsonObject: JsonObject): Flow<Resource<OrderResponse>> = flow {
        emit(Resource.Loading)
        when (val result = repository.orderNoByMarketerApi(jsonObject)) {
            is Resource.Success -> {
                try {
                    val parsed = Gson().fromJson(result.value, OrderResponse::class.java)
                    emit(Resource.Success(parsed))
                } catch (e: Exception) {
                    emit(Resource.Failure(
                        isNetworkError = e.message!!,
                        errorCode = null,
                        errorBody = "Parsing error: ${e.localizedMessage}"
                    ))
                }
            }

            is Resource.Failure -> {
                emit(Resource.Failure(
                    isNetworkError = "",
                    errorCode = result.errorCode,
                    errorBody = result.errorBody ?: "Unknown error"
                ))
            }

            else -> {
                emit(Resource.Failure(
                    isNetworkError = "false",
                    errorCode = null,
                    errorBody = "Unexpected error occurred"
                ))
            }
        }
    }.flowOn(Dispatchers.IO)

    fun partyDetailsByPartyCodeReq(accountId: String,supplierId: String): Flow<Resource<PartyDetailsResponse>> = flow {
        emit(Resource.Loading)
        when (val result = repository.partyDetailsByPartyCodeApi(accountId,supplierId)) {
            is Resource.Success -> {
                try {
                    val parsed = Gson().fromJson(result.value, PartyDetailsResponse::class.java)
                    emit(Resource.Success(parsed))
                } catch (e: Exception) {
                    emit(Resource.Failure(
                        isNetworkError = e.message!!,
                        errorCode = null,
                        errorBody = "Parsing error: ${e.localizedMessage}"
                    ))
                }
            }

            is Resource.Failure -> {
                emit(Resource.Failure(
                    isNetworkError = "",
                    errorCode = result.errorCode,
                    errorBody = result.errorBody ?: "Unknown error"
                ))
            }

            else -> {
                emit(Resource.Failure(
                    isNetworkError = "false",
                    errorCode = null,
                    errorBody = "Unexpected error occurred"
                ))
            }
        }
    }.flowOn(Dispatchers.IO)


    fun dispatchTypeReq(): Flow<Resource<DispatchTypeResponse>> = flow {
        emit(Resource.Loading)
        when (val result = repository.dispatchTypeReqApi()) {
            is Resource.Success -> {
                try {
                    val parsed = Gson().fromJson(result.value, DispatchTypeResponse::class.java)
                    emit(Resource.Success(parsed))
                } catch (e: Exception) {
                    emit(Resource.Failure(
                        isNetworkError = e.message!!,
                        errorCode = null,
                        errorBody = "Parsing error: ${e.localizedMessage}"
                    ))
                }
            }

            is Resource.Failure -> {
                emit(Resource.Failure(
                    isNetworkError = "",
                    errorCode = result.errorCode,
                    errorBody = result.errorBody ?: "Unknown error"
                ))
            }

            else -> {
                emit(Resource.Failure(
                    isNetworkError = "false",
                    errorCode = null,
                    errorBody = "Unexpected error occurred"
                ))
            }
        }
    }.flowOn(Dispatchers.IO)

    fun picTypeeReq(jsonObject: JsonObject): Flow<Resource<PcsTypeResponse>> = flow {
        emit(Resource.Loading)
        when (val result = repository.picTypeApi(jsonObject)) {
            is Resource.Success -> {
                try {
                    val parsed = Gson().fromJson(result.value, PcsTypeResponse::class.java)
                    emit(Resource.Success(parsed))
                } catch (e: Exception) {
                    emit(Resource.Failure(
                        isNetworkError = e.message!!,
                        errorCode = null,
                        errorBody = "Parsing error: ${e.localizedMessage}"
                    ))
                }
            }

            is Resource.Failure -> {
                emit(Resource.Failure(
                    isNetworkError = "",
                    errorCode = result.errorCode,
                    errorBody = result.errorBody ?: "Unknown error"
                ))
            }

            else -> {
                emit(Resource.Failure(
                    isNetworkError = "false",
                    errorCode = null,
                    errorBody = "Unexpected error occurred"
                ))
            }
        }
    }.flowOn(Dispatchers.IO)

    fun fatchItemReq(): Flow<Resource<ItemResponse>> = flow {
        emit(Resource.Loading)
        when (val result = repository.dispatchTypeApi()) {
            is Resource.Success -> {
                try {

                    val parsed = Gson().fromJson(result.value, ItemResponse::class.java)
                    emit(Resource.Success(parsed))
                } catch (e: Exception) {
                    emit(Resource.Failure(
                        isNetworkError = e.message!!,
                        errorCode = null,
                        errorBody = "Parsing error: ${e.localizedMessage}"
                    ))
                }
            }

            is Resource.Failure -> {
                emit(Resource.Failure(
                    isNetworkError = "",
                    errorCode = result.errorCode,
                    errorBody = result.errorBody ?: "Unknown error"
                ))
            }

            else -> {
                emit(Resource.Failure(
                    isNetworkError = "false",
                    errorCode = null,
                    errorBody = "Unexpected error occurred"
                ))
            }
        }
    }.flowOn(Dispatchers.IO)

    private val _saleItems = MutableStateFlow<List<SaleReportResponse>>(emptyList())
    val saleItems: StateFlow<List<SaleReportResponse>> = _saleItems

    private var page = 0
    private val pageSize = 10
    var isLoading by mutableStateOf(false)
        private set



    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    fun fetchSaleReport(jsonObject: JsonObject) {
        viewModelScope.launch {
            saleReport(jsonObject).collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        _loading.value = true
                    }
                    is Resource.Success -> {
                        _loading.value = false
                        _saleItems.value = _saleItems.value + resource.value
                    }
                    is Resource.Failure -> {
                        _loading.value = false
                        // handle error state
                    }
                }
            }
        }
    }

    private fun saleReport(jsonObject: JsonObject): Flow<Resource<SaleReportResponse>> = flow {
        emit(Resource.Loading)
        when (val result = repository.saleReportApi(jsonObject)) {
            is Resource.Success -> {
                try {
                    Log.e("sale report list",result.value.toString())
                    val parsed = Gson().fromJson(result.value, SaleReportResponse::class.java)

                    emit(Resource.Success(parsed))
                } catch (e: Exception) {
                    emit(Resource.Failure(
                        isNetworkError = e.message ?: "Parse Error",
                        errorCode = null,
                        errorBody = "Parsing error: ${e.localizedMessage}"
                    ))
                }
            }
            is Resource.Failure -> emit(result)
            Resource.Loading -> TODO()
        }
    }.flowOn(Dispatchers.IO)

    val _dashboardDetailGraphResponse: MutableLiveData<Resource<JsonObject>> = MutableLiveData()
    val dashboardDetailGraphResponse: LiveData<Resource<JsonObject>> get() = _dashboardDetailGraphResponse

    val _getDashboardDetailsBalanceTillDate: MutableLiveData<Resource<JsonObject>> = MutableLiveData()
    val getDashboardDetailsBalanceTillDate: LiveData<Resource<JsonObject>> get() = _getDashboardDetailsBalanceTillDate

    val _getDashboardDetailsInteresDiscount: MutableLiveData<Resource<JsonObject>> = MutableLiveData()
    val getDashboardDetailsInteresDiscount: LiveData<Resource<JsonObject>> get() = _getDashboardDetailsInteresDiscount

    val _getdashboardDetailsStockInOffice: MutableLiveData<Resource<JsonObject>> = MutableLiveData()
    val getdashboardDetailsStockInOffice: LiveData<Resource<JsonObject>> get() = _getdashboardDetailsStockInOffice

    val _dashboardDetailsPendingOrder: MutableLiveData<Resource<JsonObject>> = MutableLiveData()
    val dashboardDetailsPendingOrder: LiveData<Resource<JsonObject>> get() = _dashboardDetailsPendingOrder


    fun fetchAllDashboardApis(jsonObject: JsonObject, jsonObjectBal: JsonObject) = viewModelScope.launch {
        // Set all LiveData to Loading
        _dashboardDetailGraphResponse.value = Resource.Loading
        _getDashboardDetailsBalanceTillDate.value = Resource.Loading
        _getDashboardDetailsInteresDiscount.value = Resource.Loading
        _getdashboardDetailsStockInOffice.value = Resource.Loading
        _dashboardDetailsPendingOrder.value = Resource.Loading

        try {
            // Launch all API calls in parallel
            val graphDeferred = async { repository.paymentDashboard(jsonObject) }
            val balanceDeferred = async { repository.getDashboardDetailsBalanceTillDate(jsonObjectBal) }
            val discountDeferred = async { repository.dashboardDetailsInteresDiscountReq(jsonObject) }
            val stockDeferred = async { repository.dashboardDetailsStockInOfficetReq(jsonObject) }
            val pendingOrderDeferred = async { repository.dashboardDetailsPendingOrderReq(jsonObject) }

            // Await results and update LiveData
            _dashboardDetailGraphResponse.value = graphDeferred.await()
            _getDashboardDetailsBalanceTillDate.value = balanceDeferred.await()
            _getDashboardDetailsInteresDiscount.value = discountDeferred.await()
            _getdashboardDetailsStockInOffice.value = stockDeferred.await()
            _dashboardDetailsPendingOrder.value = pendingOrderDeferred.await()

        } catch (e: Exception) {
            // Handle errors for all APIs
            _dashboardDetailGraphResponse.value = Resource.Failure(
                isNetworkError = "true",        // or "false" depending on your check
                errorCode = null,                // HTTP error code if available
                errorBody = e.message ?: "Error"
            )
            _getDashboardDetailsBalanceTillDate.value = Resource.Failure(
                isNetworkError = "true",        // or "false" depending on your check
                errorCode = null,                // HTTP error code if available
                errorBody = e.message ?: "Error"
            )
            _getdashboardDetailsStockInOffice.value = Resource.Failure(
                isNetworkError = "true",        // or "false" depending on your check
                errorCode = null,                // HTTP error code if available
                errorBody = e.message ?: "Error"
            )
            _dashboardDetailsPendingOrder.value = Resource.Failure(
                isNetworkError = "true",        // or "false" depending on your check
                errorCode = null,                // HTTP error code if available
                errorBody = e.message ?: "Error"
            )
       /*     _getDashboardDetailsBalanceTillDate.value = Resource.Failure(e.message ?: "Error")
            _getDashboardDetailsInteresDiscount.value = Resource.Failure(e.message ?: "Error")
            _getdashboardDetailsStockInOffice.value = Resource.Failure(e.message ?: "Error")
            _dashboardDetailsPendingOrder.value = Resource.Failure(e.message ?: "Error")*/
        }
    }

    private val _ledgerReportWithBalance = MutableStateFlow<List<LedgerResponse>>(emptyList())

    val ledgerReportWithBalance: StateFlow<List<LedgerResponse>> = _ledgerReportWithBalance


    fun fetchLedgerReport(jsonObject: JsonObject) {
        viewModelScope.launch(Dispatchers.IO) {
            _loading.value = true
            try {
                val result = repository.fatchLedgerReportWithBalanceApi(jsonObject)
                if (result is Resource.Success) {
                    // Parse the full response
                    val parsedResponse = Gson().fromJson(result.value, LedgerResponse::class.java)

                    _ledgerReportWithBalance.value = listOf(parsedResponse)
                } else {
                    _ledgerReportWithBalance.value = emptyList()
                }
            } catch (e: Exception) {
                _ledgerReportWithBalance.value = emptyList()
            } finally {
                _loading.value = false
            }
        }
    }

    private val _pendingOrder = MutableStateFlow<List<OrderData>>(emptyList())
    val pendingOrder: StateFlow<List<OrderData>> = _pendingOrder

    fun fetchPendingOrder(jsonObject: JsonObject) {
        viewModelScope.launch(Dispatchers.IO) {
            _loading.value = true
            try {
                val result = repository.pendingOrderApi(jsonObject)
                if (result is Resource.Success) {
                    // Parse the full response
                    val parsedResponse = Gson().fromJson(result.value, OrderResponse::class.java)

                    // Log full JSON response
                    Log.d("PendingOrder", "Full Response: ${result.value}")

                    // Log parsed data
                    Log.d("PendingOrder", "Parsed Orders: ${parsedResponse.orderDetails}")

                    _pendingOrder.value = parsedResponse.orderDetails ?: emptyList()
                } else {
                    Log.d("PendingOrder", "API returned error or empty response")
                    _pendingOrder.value = emptyList()
                }
            } catch (e: Exception) {
                Log.e("PendingOrder", "Exception: ${e.message}", e)
                _pendingOrder.value = emptyList()
            } finally {
                _loading.value = false
            }
        }
    }

    private val _stockInoffice = MutableStateFlow<List<StockInOfficeResponse>>(emptyList())
    val stockInoffice: StateFlow<List<StockInOfficeResponse>> = _stockInoffice

    fun fetchStockInOffice(jsonObject: JsonObject) {
        viewModelScope.launch(Dispatchers.IO) {
            _loading.value = true
            try {
                val result = repository.stockInOfficeApi(jsonObject)
                if (result is Resource.Success) {
                    val parsedResponse =
                        Gson().fromJson(result.value, StockInOfficeResponse::class.java)
                    _stockInoffice.value = (listOf(parsedResponse))
                } else {
                    _stockInoffice.value = emptyList()
                }
            } catch (e: Exception) {
                _stockInoffice.value = emptyList()
            } finally {
                _loading.value = false
            }
        }
    }

    private val _stayBooking = MutableStateFlow<List<StayBookingResult>>(emptyList())
    val stayBooking: StateFlow<List<StayBookingResult>> = _stayBooking

    fun fetchStayBooking(partyCode: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _loading.value = true
            try {
                val result = repository.stayBookingApi(partyCode)
                if (result is Resource.Success) {
                    val parsedResponse =
                        Gson().fromJson(result.value, StayBookingResponse::class.java)
                    _stayBooking.value = parsedResponse.StayBookingList ?: emptyList()
                } else {
                    _stayBooking.value = emptyList()
                }
            } catch (e: Exception) {
                _stayBooking.value = emptyList()
            } finally {
                _loading.value = false
            }
        }


    }

    fun placeOrder(params: HashMap<String, RequestBody?>): Flow<Resource<Resource<Unit>>> = flow {
        emit(Resource.Loading)

        try {
            // Add OrderBook list
            params["OrderBookSecondaryList"] =
                gson.toJson(addItemDataList).toRequestBody()

            // Prepare documents
            val documents = addImageDataList.mapNotNull { imageModel ->
                imageModel.filePath?.let { path ->
                    val file = File(path)
                    if (file.exists()) {
                        MultipartBody.Part.createFormData(
                            "documents",
                            file.name,
                            file.asRequestBody("image/*".toMediaTypeOrNull())
                        )
                    } else null
                }
            }

            // Call repository (assume it throws exceptions on failure)
            val response = repository.placeOrder(params, documents)

            // Emit success
            emit(Resource.Success(response))

        } catch (e: IOException) {
            // Network error
            emit(Resource.Failure(isNetworkError = "true", errorCode = null, errorBody = "Network error: ${e.message}"))
        } catch (e: HttpException) {
            // API error
            emit(Resource.Failure(isNetworkError = "false", errorCode = e.code(), errorBody = e.message()))
        } catch (e: Exception) {
            // Other exceptions
            emit(Resource.Failure(isNetworkError = "false", errorCode = null, errorBody = "Unexpected error: ${e.localizedMessage}"))
        }
    }.flowOn(Dispatchers.IO)

    private val _brandlist = MutableStateFlow<List<OrderData>>(emptyList())
    val brandlist: StateFlow<List<OrderData>> = _brandlist

    fun fetchBrandList(jsonObject: JsonObject) {
        viewModelScope.launch(Dispatchers.IO) {
            _loading.value = true
            try {
                val result = repository.brandListApi(jsonObject)
                if (result is Resource.Success) {
                    // Parse the full response
                    val parsedResponse = Gson().fromJson(result.value, OrderResponse::class.java)

                    // Log full JSON response
                    Log.d("PendingOrder", "Full Response: ${result.value}")

                    // Log parsed data
                    Log.d("PendingOrder", "Parsed Orders: ${parsedResponse.orderDetails}")

                    _pendingOrder.value = parsedResponse.orderDetails ?: emptyList()
                } else {
                    Log.d("PendingOrder", "API returned error or empty response")
                    _pendingOrder.value = emptyList()
                }
            } catch (e: Exception) {
                Log.e("PendingOrder", "Exception: ${e.message}", e)
                _pendingOrder.value = emptyList()
            } finally {
                _loading.value = false
            }
        }
    }

    private val _guestList = MutableStateFlow<List<GuestMasterDetail>>(emptyList())
    val guestList: StateFlow<List<GuestMasterDetail>> = _guestList

    fun fetchGuestList(accountId: String,partyCode: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _loading.value = true
            try {
                val result = repository.guestListApi(accountId,partyCode)
                if (result is Resource.Success) {
                    // Parse the full response
                    val parsedResponse = Gson().fromJson(result.value, GuestResponse::class.java)

                    // Log full JSON response
                    Log.d("Guest ", "Full Response: ${result.value}")

                    // Log parsed data
                    Log.d("PendingOrder", "Parsed Orders: ${parsedResponse.GuestMasterDetailList}")

                    _guestList.value = parsedResponse.GuestMasterDetailList ?: emptyList()
                } else {
                    Log.d("PendingOrder", "API returned error or empty response")
                    _guestList.value = emptyList()
                }
            } catch (e: Exception) {
                Log.e("PendingOrder", "Exception: ${e.message}", e)
                _guestList.value = emptyList()
            } finally {
                _loading.value = false
            }
        }
    }

}