package com.syber.ssspltd.out

import android.util.Log
import android.util.Log.e
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.app.naturalhigh.out.AuthRepository
import com.app.naturalhigh.out.BaseViewModell
import com.google.android.gms.common.internal.AccountType
import com.google.gson.JsonObject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import net.simplifiedcoding.data.network.Resource
import javax.inject.Inject
import com.google.gson.Gson
import com.ssspvtltd.quick.model.order.add.additem.PackType
import com.syber.ssspltd.data.model.CreditNotTo.CreditNoteRepPojo
import com.syber.ssspltd.data.model.CreditNotTo.CreditNoteReportResult
import com.syber.ssspltd.data.model.ImageModel
import com.syber.ssspltd.data.model.OrderData
import com.syber.ssspltd.data.model.OrderResponse
import com.syber.ssspltd.data.model.PcsTypeResponse
import com.syber.ssspltd.data.model.additem.OrderNoResponse
import com.syber.ssspltd.data.model.addorder.DispatchTypeResponse
import com.syber.ssspltd.data.model.addorder.ItemResponse
import com.syber.ssspltd.data.model.addorder.PartyDetailsResponse
import com.syber.ssspltd.data.model.addorder.SaveOrderResponse
import com.syber.ssspltd.data.model.addorder.SupplierNickNameResponse
import com.syber.ssspltd.data.model.bank_detail.BankDetailsResult
import com.syber.ssspltd.data.model.bookingRequest.BranchList
import com.syber.ssspltd.data.model.bookingRequest.BranchListResponse
import com.syber.ssspltd.data.model.branch.BranchResult
import com.syber.ssspltd.data.model.brand.Branch
import com.syber.ssspltd.data.model.brand.BranchResponse
import com.syber.ssspltd.data.model.brand.BrandResponse
import com.syber.ssspltd.data.model.brand.ProductImage
import com.syber.ssspltd.data.model.debitNoteToCustomer.DNToCustomerResponse
import com.syber.ssspltd.data.model.debitNoteToCustomer.DebitNoteToCustomerReportResult
import com.syber.ssspltd.data.model.gallery.EventItem

import com.syber.ssspltd.data.model.honar.BlackListedName
import com.syber.ssspltd.data.model.ledger.LedgerResponse
import com.syber.ssspltd.data.model.ledger.LedgerResponse.LedgerReportItem
import com.syber.ssspltd.data.model.profile.CommonApiResponse
import com.syber.ssspltd.data.model.profile.ProfileDetailsResult
import com.syber.ssspltd.data.model.saleReport.AccountType1
import com.syber.ssspltd.data.model.saleReport.AdjustmentType
import com.syber.ssspltd.data.model.saleReport.BranchItem
import com.syber.ssspltd.data.model.saleReport.BrandItem
import com.syber.ssspltd.data.model.saleReport.EntryType
import com.syber.ssspltd.data.model.saleReport.SaleReportFilterResponse
import com.syber.ssspltd.data.model.saleReport.SaleReportResponse
import com.syber.ssspltd.data.model.saleReport.SubPartyItem
import com.syber.ssspltd.data.model.saleReport.TransporterItem
import com.syber.ssspltd.data.model.saleservice.SaleServiceReportItem
import com.syber.ssspltd.data.model.saleservice.SaleServiceReportResponse
import com.syber.ssspltd.data.model.staybooking.AccountNameItem
import com.syber.ssspltd.data.model.staybooking.AccountNameResponse
import com.syber.ssspltd.data.model.staybooking.GuestMasterDetail
import com.syber.ssspltd.data.model.staybooking.GuestResponse
import com.syber.ssspltd.data.model.staybooking.NickNameItem
import com.syber.ssspltd.data.model.staybooking.NickNameResponse
import com.syber.ssspltd.data.model.staybooking.StayBookingResponse
import com.syber.ssspltd.data.model.staybooking.StayBookingResult
import com.syber.ssspltd.data.model.stockinoffice.StockInOfficeReportResult
import com.syber.ssspltd.data.model.stockinoffice.StockInOfficeResponse
import com.syber.ssspltd.data.model.userType.ApiResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import okhttp3.RequestBody
import org.json.JSONObject


@HiltViewModel
 class AuthViewModel @Inject constructor(private val gson: Gson,private val repository: AuthRepository) : BaseViewModell(repository){

    var addItemDataList = ArrayList<PackType>()
    var addImageDataList = ArrayList<ImageModel>()
    var selectedBooking: StayBookingResult? = null

    private val _branchID = MutableStateFlow("")
    val branchID = _branchID.asStateFlow()
    fun setBranchId(id: String) {
        _branchID.value = id

    }

    private val _itemCount = MutableLiveData(0)
    val itemCount: LiveData<Int> = _itemCount

    fun addItem() {
        _itemCount.value = (_itemCount.value ?: 0) + 1
    }

    fun removeItem() {
        _itemCount.value = (_itemCount.value ?: 0) - 1
    }

    var images by mutableStateOf<List<ProductImage>>(emptyList())

    fun updateImages(list: List<ProductImage>) {
        images = list
    }
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


    fun orderNoByMarketerReq(jsonObject: JsonObject): Flow<Resource<OrderNoResponse>> = flow {
        emit(Resource.Loading)
        when (val result = repository.orderNoByMarketerApi(jsonObject)) {
            is Resource.Success -> {
                try {
                    val parsed = Gson().fromJson(result.value, OrderNoResponse::class.java)
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
        _saleItems.value = emptyList()
        viewModelScope.launch {
            saleReport(jsonObject).collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        _loading.value = true
                    }
                    is Resource.Success -> {
                        _loading.value = false
                        _saleItems.value = listOf(resource.value)
                    }
                    is Resource.Failure -> {
                        _loading.value = false
                        // handle error state
                    }
                }
            }
        }
    }


    //filter sale report screen
    private val _adjustmentType = MutableStateFlow<List<AdjustmentType>>(emptyList())
    private val _entryType = MutableStateFlow<List<EntryType>>(emptyList())
    private val _accountType = MutableStateFlow<List<AccountType1>>(emptyList())
    private val _branchItemList = MutableStateFlow<List<BranchItem>>(emptyList())
    private val _subPartyItem = MutableStateFlow<List<SubPartyItem>>(emptyList())
    private val _transporterItem = MutableStateFlow<List<TransporterItem>>(emptyList())
    private val _supplier = MutableStateFlow<List<BrandItem>>(emptyList())
    /*  private val _saleResportfilterList = MutableStateFlow<List<StayBookingResult>>(emptyList())
      private val _saleResportfilterList = MutableStateFlow<List<StayBookingResult>>(emptyList())
    */
    val adjustmentType: StateFlow<List<AdjustmentType>> = _adjustmentType
    val entryType: StateFlow<List<EntryType>> = _entryType
    val accountType: StateFlow<List<AccountType1>> = _accountType
    val branch: StateFlow<List<BranchItem>> = _branchItemList
    val subParty: StateFlow<List<SubPartyItem>> = _subPartyItem
    val transporter: StateFlow<List<TransporterItem>> = _transporterItem
    val supplier: StateFlow<List<BrandItem>> = _supplier

    fun fatchSaleReportFilter(jsonObject: JsonObject) {
        viewModelScope.launch(Dispatchers.IO) {
            _loading.value = true
            try {
                val result = repository.saleReportFilterApi(jsonObject)
                if (result is Resource.Success) {
                    val parsedResponse =
                        Gson().fromJson(result.value, SaleReportFilterResponse::class.java)
                    _adjustmentType.value = parsedResponse.AdjustmentType ?: emptyList()
                    _accountType.value = parsedResponse.AccountType ?: emptyList()
                    _entryType.value = parsedResponse.EntryType ?: emptyList()
                    _branchItemList.value = parsedResponse.Branch ?: emptyList()
                    _subPartyItem.value = parsedResponse.SubParty ?: emptyList()
                    _transporterItem.value = parsedResponse.Transporter ?: emptyList()
                    _supplier.value = parsedResponse.Brand ?: emptyList()
                } else {
                    _adjustmentType.value = emptyList()
                }
            } catch (e: Exception) {
                _adjustmentType.value = emptyList()
            } finally {
                _loading.value = false
            }
        }


    }

    private val _ledgerReportWithBalance = MutableStateFlow<List<LedgerResponse>>(emptyList())
    private val _ledgerReportItem = MutableStateFlow<List<LedgerReportItem>>(emptyList())

    val ledgerReportWithBalance: StateFlow<List<LedgerResponse>> = _ledgerReportWithBalance
     var ledgerReportResult: StateFlow<List<LedgerReportItem>> = _ledgerReportItem
    private val _hasFetched = MutableStateFlow(false)
    val hasFetched = _hasFetched

    fun fetchLedgerReport(jsonObject: JsonObject) {

        viewModelScope.launch(Dispatchers.IO) {
            _loading.value = true
            try {
                val result = repository.fatchLedgerReportWithBalanceApi(jsonObject)
                if (result is Resource.Success) {
                    // Parse the full response
                 //   _hasFetched.value = true
                    _loading.value = false
                    val parsedResponse = Gson().fromJson(result.value, LedgerResponse::class.java)

                    _ledgerReportWithBalance.value = listOf(parsedResponse)
                    _ledgerReportItem.value = parsedResponse.LedgerReportResult!!
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



    private val _courierReportList= MutableStateFlow<List<LedgerResponse.CourierReportItem>>(emptyList())
    val courierReportList: StateFlow<List<LedgerResponse.CourierReportItem>> = _courierReportList

    fun fetchCourierReport(jsonObject: JsonObject) {

        viewModelScope.launch(Dispatchers.IO) {
            _loading.value = true
            try {
                val result = repository.courierReportApi(jsonObject)
                if (result is Resource.Success) {
                    // Parse the full response
                    //   _hasFetched.value = true
                    _loading.value = false
                    val parsedResponse = Gson().fromJson(result.value, LedgerResponse::class.java)

                    _ledgerReportWithBalance.value = listOf(parsedResponse)
                    _courierReportList.value =
                        parsedResponse.CourierReportResult ?: emptyList()
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

    private val _honarList= MutableStateFlow<List<BlackListedName>>(emptyList())
    val honarList: StateFlow<List<BlackListedName>> = _honarList

    fun fetchHonarList(jsonObject: JsonObject) {

        viewModelScope.launch(Dispatchers.IO) {
            _loading.value = true
            try {

                val result = repository.honarListApi(jsonObject)
                Log.e("honhar list",result.toString())
                if (result is Resource.Success) {
                    // Parse the full response
                    //   _hasFetched.value = true
                    _loading.value = false
                    val parsedResponse = Gson().fromJson(result.value, LedgerResponse::class.java)

                    _ledgerReportWithBalance.value = listOf(parsedResponse)
                    _honarList.value =
                        parsedResponse.BlackListedName ?: emptyList()
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


    private val _saleServices = MutableStateFlow<List<SaleServiceReportItem>>(emptyList())
    val saleServices: StateFlow<List<SaleServiceReportItem>> = _saleServices
    fun fatchSaleServices(jsonObject: JsonObject) {

        viewModelScope.launch(Dispatchers.IO) {
            _loading.value = true
            try {
                val result = repository.fatchSaleServicesApi(jsonObject)
                if (result is Resource.Success) {
                    // Parse the full response
                    _hasFetched.value = true
                    _loading.value = false
                    val parsedResponse = Gson().fromJson(result.value, SaleServiceReportResponse::class.java)
                    _saleServices.value = parsedResponse.SaleServiceReportResult
                } else {
                    _saleServices.value = emptyList()
                }
            } catch (e: Exception) {
                _saleServices.value = emptyList()
            } finally {
                _loading.value = false
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


    private val _eventList= MutableStateFlow<List<EventItem>>(emptyList())
    val eventList: StateFlow<List<EventItem>> = _eventList

    fun fetchEventList(jsonObject: JsonObject) {

        viewModelScope.launch(Dispatchers.IO) {
            _loading.value = true
            try {
                val result = repository.eventApi(jsonObject)
                if (result is Resource.Success) {
                    // Parse the full response
                    //   _hasFetched.value = true
                    _loading.value = false
                    val parsedResponse = Gson().fromJson(result.value, LedgerResponse::class.java)

                 //   _ledgerReportWithBalance.value = listOf(parsedResponse)
                    _eventList.value = parsedResponse.Events
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

    private val _stockInoffice =
        MutableStateFlow<List<StockInOfficeReportResult>>(emptyList())

    val stockInoffice: StateFlow<List<StockInOfficeReportResult>> = _stockInoffice

    fun fetchStockInOffice(jsonObject: JsonObject) {
        viewModelScope.launch(Dispatchers.IO) {
            _loading.value = true
            try {

                val result = repository.stockInOfficeApi(jsonObject)
                Log.d("Stock in office", "Full Request: $result")
                if (result is Resource.Success) {
                    val parsedResponse = Gson().fromJson(
                        result.value,
                        StockInOfficeResponse::class.java
                    )

                    _stockInoffice.value =
                        parsedResponse.StockInOfficeReportResult ?: emptyList()

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

    fun fatchStayBookingListByBranchReq(partyCode: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _loading.value = true
            try {
                val result = repository.fatchStayBookingListByBranchId(partyCode)
                if (result is Resource.Success) {
                    val parsedResponse =
                        Gson().fromJson(result.value, StayBookingResponse::class.java)
                    _stayBooking.value = parsedResponse.StayBookingList
                        ?.sortedByDescending  { it.bookingID ?: 0 }           // ASCENDING
                        ?: emptyList()
             //       _stayBooking.value = parsedResponse.StayBookingList ?: emptyList()
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

/*    private val _stayBookingByRecordID = MutableStateFlow<List<String>>(emptyList())
    val stayBookingByRecordID: StateFlow<List<String>> = _stayBookingByRecordID*/

    private val _stayBookingByRecordID = MutableStateFlow<List<String>>(emptyList())
    val stayBookingByRecordID = _stayBookingByRecordID.asStateFlow()
    fun fatchStayBookingListByRecordIdReq(recordID: String,partyCode: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _loading.value = true
            try {
                val result = repository.fatchStayBookingListByRecordID(recordID,partyCode)
                if (result is Resource.Success) {

                    val parsedResponse =
                        Gson().fromJson(result.value, StayBookingResponse::class.java)
                    _stayBookingByRecordID.value = (parsedResponse.StayBookingData?.guestIds // ASCENDING
                        ?: emptyList()) as List<String>
                    Log.d("BookingListByRecordIdResponse",_stayBookingByRecordID.toString())
                    //       _stayBooking.value = parsedResponse.StayBookingList ?: emptyList()
                } else {
                    _stayBookingByRecordID.value = emptyList()
                }
            } catch (e: Exception) {
                _stayBookingByRecordID.value = emptyList()
            } finally {
                _loading.value = false
            }
        }
    }
    fun addGuest(id: String) {
        _stayBookingByRecordID.value =
            _stayBookingByRecordID.value + id
    }

    fun removeGuest(id: String) {
        _stayBookingByRecordID.value =
            _stayBookingByRecordID.value.filter { it != id }
    }



    private val _updateTime = MutableStateFlow<ApiResponse?>(null)
    val updateTime: StateFlow<ApiResponse?> = _updateTime

    fun updateStayBookingActualTimeReq(bookingId: String,actualCheckInDate: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _loading.value = true
            try {
                val result = repository.updateStayBookingActualTimeReqApi(bookingId,actualCheckInDate)
                if (result is Resource.Success) {
                    // ✅ Parse the JSON into ApiResponse
                    val parsedResponse = Gson().fromJson(result.value, ApiResponse::class.java)

                    // ✅ Update flow
                    _updateTime.value = parsedResponse
                } else if (result is Resource.Failure) {
                    _updateTime.value = null
                }
            }
            catch (e: Exception) {
                e.printStackTrace()
                _updateTime.value = null
            } finally {
                _loading.value = false
            }
        }
    }

    fun updateStayBookingActualCheckOutTimeReq(bookingId: String,actualCheckInDate: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _loading.value = true
            try {
                val result = repository.updateStayBookingActualCheckOutTimeTimeReqApi(bookingId,actualCheckInDate)
                if (result is Resource.Success) {
                    // ✅ Parse the JSON into ApiResponse
                    val parsedResponse = Gson().fromJson(result.value, ApiResponse::class.java)

                    // ✅ Update flow
                    _updateTime.value = parsedResponse
                } else if (result is Resource.Failure) {
                    _updateTime.value = null
                }
            }
            catch (e: Exception) {
                e.printStackTrace()
                _updateTime.value = null
            } finally {
                _loading.value = false
            }
        }
    }


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



    var selectedBranchName = mutableStateOf("Select Branch")
    var selectedNickName = mutableStateOf("Select Nick Name")
    var selectedNickNameId = mutableStateOf("")
    var selectedCustomerName = mutableStateOf("Select Customer")
    var selectedCustomerId = mutableStateOf("")
    var selectedBrancId = mutableStateOf("")

    var selectedCheckInDate = mutableStateOf("")
    var selectedCheckOutDate = mutableStateOf("")

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

    fun fetchGuestByPhoneNumList(mobileNum: String,partyCode: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _loading.value = true
            try {
                val result = repository.guestListByPhoneNumApi(mobileNum,partyCode)
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
    fun clearGuestList() {
        _guestList.value = emptyList()
    }

    private val _branchList = MutableStateFlow<List<BranchList>>(emptyList())
    val branchList: StateFlow<List<BranchList>> = _branchList

    fun fetchBranchList() {
        viewModelScope.launch(Dispatchers.IO) {
            _loading.value = true
            try {
                val result = repository.guestBranchApi()
                if (result is Resource.Success) {
                    // Parse the full response
                    val parsedResponse = Gson().fromJson(result.value, BranchListResponse::class.java)

                    // Log full JSON response
                    Log.d("Branch List ", "Full Response: ${result.value}")

                    // Log parsed data
                    Log.d("PendingOrder", "Parsed Orders: ${parsedResponse.data}")

                    _branchList.value = parsedResponse.data ?: emptyList()
                } else {
                    Log.d("PendingOrder", "API returned error or empty response")
                    _branchList.value = emptyList()
                }
            } catch (e: Exception) {
                Log.e("PendingOrder", "Exception: ${e.message}", e)
                _branchList.value = emptyList()
            } finally {
                _loading.value = false
            }
        }
    }
    private val _nickNameList = MutableStateFlow<List<NickNameItem>>(emptyList())
    val nickNameList: StateFlow<List<NickNameItem>> = _nickNameList

    fun fetchNickNameList() {
        // If already loaded, don't call again
        if (_nickNameList.value.isNotEmpty()) return

        viewModelScope.launch(Dispatchers.IO) {
            _loading.value = true
            try {
                val result = repository.nickNameApi()
                if (result is Resource.Success) {
                    val parsedResponse = Gson().fromJson(result.value, NickNameResponse::class.java)
                    _nickNameList.value = parsedResponse.NickNameList
                } else {
                    _nickNameList.value = emptyList()
                }
            } catch (e: Exception) {
                _nickNameList.value = emptyList()
            } finally {
                _loading.value = false
            }
        }
    }



    private val _customerList = MutableStateFlow<List<AccountNameItem>>(emptyList())
    val customerList: StateFlow<List<AccountNameItem>> = _customerList

    fun fetchCustomerList(nickNameID:String) {

      /*  // If already loaded, don't call again
        if (_customerList.value.isNotEmpty()) return
*/
        viewModelScope.launch(Dispatchers.IO) {
            _loading.value = true
            try {
                val result = repository.customerApi(nickNameID)
                if (result is Resource.Success) {
                    // Parse the full response
                    val parsedResponse = Gson().fromJson(result.value, AccountNameResponse::class.java)

                    // Log full JSON response
                    Log.d("Cutomer List ", "Full Response: ${result.value}")

                    // Log parsed data
                    Log.d("Cutomer", "Parsed Orders: ${parsedResponse.AccountNameList}")

                    _customerList.value = parsedResponse.AccountNameList ?: emptyList()
                } else {
                    Log.d("Cutomer", "API returned error or empty response")
                    _customerList.value = emptyList()
                }
            } catch (e: Exception) {
                Log.e("Cutomer", "Exception: ${e.message}", e)
                _customerList.value = emptyList()
            } finally {
                _loading.value = false
            }
        }
    }

    fun saveOrder(jsonObject: RequestBody): Flow<Resource<SaveOrderResponse>> = flow {

        emit(Resource.Loading)

        when (val result = repository.addOrderApi(jsonObject)) {

            is Resource.Success -> {

                try {

                    val parsed = Gson().fromJson(result.value.toString(), SaveOrderResponse::class.java)

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




    private val _bookingResult = MutableStateFlow<ApiResponse?>(null)
    val bookingResult: StateFlow<ApiResponse?> = _bookingResult

    fun submitStayBooking(request: JsonObject) {
        viewModelScope.launch(Dispatchers.IO) {
            _loading.value = true
            try {
                val result = repository.addStayBooking(request)
                if (result is Resource.Success) {
                    // ✅ Parse the JSON into ApiResponse
                    val parsedResponse = Gson().fromJson(result.value, ApiResponse::class.java)

                    // ✅ Update flow
                    _bookingResult.value = parsedResponse
                } else if (result is Resource.Failure) {
                    _bookingResult.value = null
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _bookingResult.value = null
            } finally {
                _loading.value = false
            }
        }
    }



    private val _addGuestResult = MutableStateFlow<ApiResponse?>(null)
    val addGuestResult: StateFlow<ApiResponse?> = _addGuestResult

    fun addAndUpdateGuest(request: JsonObject) {
        viewModelScope.launch(Dispatchers.IO) {
            _loading.value = true
            try {
                val result = repository.addGuest(request)
                if (result is Resource.Success) {
                    // ✅ Parse the JSON into ApiResponse
                    val parsedResponse = Gson().fromJson(result.value, ApiResponse::class.java)

                    // ✅ Update flow
                    _addGuestResult.value = parsedResponse
                } else if (result is Resource.Failure) {
                    _addGuestResult.value = null
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _addGuestResult.value = null
            } finally {
                _loading.value = false
            }
        }
    }

    private val _deleteGuestResult = MutableStateFlow<ApiResponse?>(null)
    val deleteGuestResult: StateFlow<ApiResponse?> = _deleteGuestResult

    fun deleteGuestParam(request: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _loading.value = true
            try {
                val result = repository.deleteGuest(request)
                if (result is Resource.Success) {
                    // ✅ Parse the JSON into ApiResponse
                    val parsedResponse = Gson().fromJson(result.value, ApiResponse::class.java)

                    // ✅ Update flow
                    _deleteGuestResult.value = parsedResponse
                } else if (result is Resource.Failure) {
                    _deleteGuestResult.value = null
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _deleteGuestResult.value = null
            } finally {
                _loading.value = false
            }
        }
    }
    private val _branch = MutableStateFlow<List<Branch>>(emptyList())
    val brandlistByBranch: StateFlow<List<Branch>> = _branch


    fun fatchBrandListByBranch(jsonObject: JsonObject) {
        viewModelScope.launch(Dispatchers.IO) {
            _loading.value = true
            try {
                val result = repository.brandListByBranchApi(jsonObject)
                if (result is Resource.Success) {
                    val parsedResponse =
                        Gson().fromJson(result.value, BranchResponse::class.java)
                    _branch.value = parsedResponse.BranchesResult ?: emptyList()

                } else {
                    _adjustmentType.value = emptyList()
                }
            } catch (e: Exception) {
                _adjustmentType.value = emptyList()
            } finally {
                _loading.value = false
            }
        }


    }

    private val _BrandMasterList = MutableStateFlow<List<com.syber.ssspltd.data.model.brand.BrandItem>>(emptyList())
    val brandMasterList: StateFlow<List<com.syber.ssspltd.data.model.brand.BrandItem>> = _BrandMasterList


    fun fatchBrandMasterList(jsonObject: JsonObject) {
        viewModelScope.launch(Dispatchers.IO) {
            _loading.value = true
            try {
                val result = repository.brandMasterDetailsApi(jsonObject)
                if (result is Resource.Success) {
                    val parsedResponse =
                        Gson().fromJson(result.value, BrandResponse::class.java)
                    _BrandMasterList.value = parsedResponse.BrandInsertingRequestData ?: emptyList()

                } else {
                    _adjustmentType.value = emptyList()
                }
            } catch (e: Exception) {
                _adjustmentType.value = emptyList()
            } finally {
                _loading.value = false
            }
        }
    }

    private val _debitNoteToCustomerReportList =
        MutableStateFlow<List<DebitNoteToCustomerReportResult>>(emptyList())

    val debitNoteToCustomerReportList: StateFlow<List<DebitNoteToCustomerReportResult>> =
        _debitNoteToCustomerReportList

    fun fatchdebitNoteToCustomerReportList(jsonObject: JsonObject) {

        viewModelScope.launch(Dispatchers.IO) {
            _loading.value = true
            try {
                val result = repository.debitNoteToCustomerReportApi(jsonObject)

                if (result is Resource.Success) {

                    val parsedResponse = Gson().fromJson(
                        result.value,
                        DNToCustomerResponse::class.java
                    )
                    Log.d("Debit note to customer report list", "Full Response: ${result.value}")

                    _debitNoteToCustomerReportList.value =
                        parsedResponse.debitNoteToCustomerReportResult ?: emptyList()

                } else {
                    _debitNoteToCustomerReportList.value = emptyList()
                }

            } catch (e: Exception) {
                _debitNoteToCustomerReportList.value = emptyList()
            } finally {
                _loading.value = false
            }
        }
    }

    private val _creditNoteToList =
        MutableStateFlow<List<CreditNoteReportResult>>(emptyList())

    val creditNoteToList: StateFlow<List<CreditNoteReportResult>> =
        _creditNoteToList

    fun fatchCreditNoteToList(jsonObject: JsonObject) {

        viewModelScope.launch {
            _loading.value = true

            try {
                val result = withContext(Dispatchers.IO) {
                    repository.creditNoteReportApi(jsonObject)
                }

                if (result is Resource.Success) {

                    val parsedResponse = Gson().fromJson(
                        result.value,
                        CreditNoteRepPojo::class.java
                    )

                    Log.d("Credit Note Report", "Full Response: ${result.value}")

                    _creditNoteToList.value =
                        parsedResponse.creditNoteReportResult ?: emptyList()

                } else {
                    _creditNoteToList.value = emptyList()
                }

            } catch (e: Exception) {
                _creditNoteToList.value = emptyList()
                Log.e("Credit Note Error", e.message ?: "Parsing error")
            } finally {
                _loading.value = false
            }
        }
    }


    private val _profileResult =
        MutableStateFlow<ProfileDetailsResult?>(null)

    val profileResult: StateFlow<ProfileDetailsResult?> =
        _profileResult
    fun fatchProfile(jsonObject: JsonObject) {

        viewModelScope.launch {
            _loading.value = true

            try {
                val result = withContext(Dispatchers.IO) {
                    repository.profileApi(jsonObject)
                }

                if (result is Resource.Success) {

                    Log.d("Profile", "Full Response: ${result.value}")

                    val parsedResponse = Gson().fromJson(
                        result.value,
                        CommonApiResponse::class.java
                    )

                    _profileResult.value = parsedResponse.ProfileDetailsResult

                } else {
                    _profileResult.value = null
                }

            } catch (e: Exception) {
                _profileResult.value = null
                Log.e("Credit Note Error", e.message ?: "Parsing error")
            } finally {
                _loading.value = false
            }
        }
    }

    private val _branchListInProfilePage =
        MutableStateFlow<List<BranchResult>>(emptyList())

    val branchListInProfilePage: StateFlow<List<BranchResult>> =
        _branchListInProfilePage

    fun fatchBranchList(jsonObject: JsonObject) {

        viewModelScope.launch {
            _loading.value = true

            try {
                val result = withContext(Dispatchers.IO) {
                    repository.branchApi(jsonObject)
                }

                if (result is Resource.Success) {

                    val parsedResponse = Gson().fromJson(
                        result.value,
                        CommonApiResponse::class.java
                    )

                    Log.d("Credit Note Report", "Full Response: ${result.value}")

                    _branchListInProfilePage.value =
                        parsedResponse.BranchesResult ?: emptyList()

                } else {
                    _creditNoteToList.value = emptyList()
                }

            } catch (e: Exception) {
                _creditNoteToList.value = emptyList()
                Log.e("Credit Note Error", e.message ?: "Parsing error")
            } finally {
                _loading.value = false
            }
        }
    }


    private val _bankResult =
        MutableStateFlow<List<BankDetailsResult>>(emptyList())

    val bankResult: StateFlow<List<BankDetailsResult>> = _bankResult
    fun fatchBankDetail(jsonObject: JsonObject) {

        viewModelScope.launch {
            _loading.value = true

            try {
                val result = withContext(Dispatchers.IO) {
                    repository.bankDetailApi(jsonObject)
                }

                if (result is Resource.Success) {

                    Log.d("Profile", "Full Response: ${result.value}")

                    val parsedResponse = Gson().fromJson(
                        result.value,
                        CommonApiResponse::class.java
                    )

                    _bankResult.value =
                        parsedResponse.BankDetailsResult ?: emptyList()

                } else {
                //    bankResult.value = null
                }

            } catch (bankResult: Exception) {
                _profileResult.value = null
            //    Log.e("Credit Note Error", e.message ?: "Parsing error")
            } finally {
                _loading.value = false
            }
        }
    }


}