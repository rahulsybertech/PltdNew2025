package com.syber.ssspltd.ui.view.addorder
import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.ssspvtltd.quick.widgets.spinner.MaterialSpinner
import com.syber.ssspltd.R
import com.syber.ssspltd.adapter.ItemEntryAdapter

import com.syber.ssspltd.data.model.ApiErrorResponse
import com.syber.ssspltd.data.model.Party
import com.syber.ssspltd.data.model.PcsType
import com.syber.ssspltd.data.model.addorder.DispatchTypeItem
import com.syber.ssspltd.data.model.addorder.Item
import com.syber.ssspltd.data.model.addorder.Marketer
import com.syber.ssspltd.data.model.addorder.MarketerResponse
import com.syber.ssspltd.data.model.addorder.SalesParty
import com.syber.ssspltd.data.model.addorder.SalesPartyResponse
import com.syber.ssspltd.data.model.addorder.Station
import com.syber.ssspltd.data.model.addorder.SubParty
import com.syber.ssspltd.data.model.addorder.Transport
import com.syber.ssspltd.databinding.AddOrderActivityBinding
import com.syber.ssspltd.out.AuthViewModel
import com.syber.ssspltd.utils.AppSharedPreferences

import com.syber.ssspltd.utils.NetworkUtils
import com.syber.ssspltd.utils.ProgressUtil
import com.syber.ssspltd.utils.ProgressUtil.dismissProgress
import com.syber.ssspltd.utils.ProgressUtil.showProgress
import com.syber.ssspltd.utils.hideKeyboard
import com.syber.ssspltd.utils.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import net.simplifiedcoding.data.network.Resource
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@AndroidEntryPoint
class AddOrderActivityNew : AppCompatActivity() {
    private lateinit var binding: AddOrderActivityBinding
    private val authViewModel: AuthViewModel by viewModels()
    private val mutableMarketerList = mutableListOf<Marketer>()
    private val saleList = mutableListOf<SalesParty>()
    private val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    private lateinit var subPartyId: String
    private lateinit var salePartyId: String
    private lateinit var transportId: String
    private lateinit var stationId: String
    private lateinit var dispatchTypeID: String
    private var isSubPartyRemark = false

    private val selectedImages = mutableListOf<Uri>()
    private lateinit var cameraLauncher: ActivityResultLauncher<Intent>
    private lateinit var galleryLauncher: ActivityResultLauncher<Intent>
    private lateinit var imageContainer: LinearLayout
    private val imageBase64List = mutableListOf<String>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // ViewBinding setup
        binding = AddOrderActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.ivBack.apply { setOnClickListener { finish() } }
    //    binding.tvDispatchFromDate.apply { setOnClickListener { finish() } }
        fetchMarketerList()
        fetchSalePartyList()
        fetchNickName()
        fatchDispatchType()

        val result = filterItems(items) { it.startsWith("A") }
        println(result)
        binding.tvAddItem.setOnClickListener { showCustomBottomSheet() }

        cameraLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val imageUri: Uri? = result.data?.data
                imageUri?.let { addImageToLayout(it) }
            }
        }

        galleryLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val clipData = result.data?.clipData
                val singleImageUri = result.data?.data

                if (clipData != null) {
                    for (i in 0 until clipData.itemCount) {
                        val uri = clipData.getItemAt(i).uri
                        addImageToLayout(uri)
                    }
                } else if (singleImageUri != null) {
                    addImageToLayout(singleImageUri)
                }
            }
        }
     /*   // 🔹 Open camera (single)
        binding.tvItemImage.setOnClickListener {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            cameraLauncher.launch(intent)
        }*/


        // 🔹 Open gallery (multiple)
        binding.tvItemImage.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            galleryLauncher.launch(intent)
        }

        binding.tilSaletParty.setEndIconOnClickListener{
            binding.etTransport.text?.clear()
            binding.etSalePartyName.text?.clear()
            binding.etSubParty.text?.clear()
            binding.etStation.text?.clear()
        }

        binding.tilSubParty.setEndIconOnClickListener{
            binding.etTransport.text?.clear()
            binding.etSubParty.text?.clear()
            binding.etStation.text?.clear()
        }

        binding.tilTransport.setEndIconOnClickListener{
            binding.etTransport.text?.clear()
            binding.etSubParty.text?.clear()
            binding.etStation.text?.clear()
        }
        binding.tilStation.setEndIconOnClickListener{
            binding.etStation.text?.clear()
        }
        binding.tvDispatchFromDate.setOnClickListener {

            showFromDatePicker()
        }
        binding.tvDispatchToDate.setOnClickListener {
            showToDatePicker()
        }
        binding.placeOrder.setOnClickListener {
            if (validate())
                binding.apply {
                    //   binding.placeOrder.isEnabled = false

                    var totalQty = 0
                    var totalAmount = 0.0



                    val hashMap: HashMap<String, RequestBody?> = HashMap()
                    println("PLACING_ORDER 3 ${Gson().toJson(hashMap)}")
                    hashMap["SalePartyId"] = salePartyId.toRequestBody()
                    println("PLACING_ORDER 2 ${Gson().toJson(salePartyId.toRequestBody())}")
                    if (subPartyId == "00000000-0000-0000-0000-000000000000") {
                        hashMap["SubPartyId"] = "".toRequestBody()
                    } else {

                        if(subPartyId.equals("null")){
                            hashMap["SubPartyId"] = "".toRequestBody()
                        }
                        else{
                            hashMap["SubPartyId"] = subPartyId.toRequestBody()
                        }

                    }

                    if(isSubPartyRemark){
                        hashMap["SubPartyId"] = "".toRequestBody()
                        hashMap["TransportId"] = transportId.toRequestBody()
                        hashMap["BstationId"] = stationId.toRequestBody()
                    }else{
                        hashMap["TransportId"] = transportId.toRequestBody()
                        hashMap["BstationId"] = stationId.toRequestBody()
                    }
                    hashMap["SubPartyasRemark"] = etSubPartyRemark.text.toString().toRequestBody()

                    hashMap["DispatchTypeID"] = dispatchTypeID.toRequestBody()
                    //    hashMap["BstationId"] = bookingStationId.toRequestBody()
                  //  hashMap["SchemeId"] = schemeId.toRequestBody()
                //    hashMap["OrderCategary"] = pvtMarka.toRequestBody()
                    hashMap["DeliveryDateFrom"] = tvDispatchFromDate.text.toString().toRequestBody()
                    hashMap["DeliveryDateTo"] = tvDispatchToDate.text.toString().toRequestBody()
                    hashMap["Remark"] = etDiscription.text.toString().toRequestBody()
              /*      if (isPvtMarka){
                        hashMap["PvtMarka"] = etpvtMarka.text.toString().toRequestBody()
                    }*/

                    hashMap["TotalQty"] = "0".toRequestBody()
                    hashMap["TotalAmt"] = "0".toRequestBody()
                    hashMap["OrderTypeName"] = "TRADING".toRequestBody()
                    hashMap["OrderStatus"] = "PENDING".toRequestBody()
                    val jsonArray = JSONArray()
                    imageBase64List.forEach { jsonArray.put(it) }

                   /* if (viewModel.pendingOrderID.isNotNullOrBlank()) hashMap["id"] =
                        (editData?.id ?: "").toRequestBody()
*/

                    Log.e("hashmap",hashMap.toString())
                    authViewModel.placeOrder(hashMap)
                    binding.placeOrder.isEnabled=false
                }
        }

    }

/*    private fun addImageToLayout(uri: Uri) {
        val imageView = ImageView(this)
        imageView.setImageURI(uri)
        imageView.layoutParams = LinearLayout.LayoutParams(
            300,
            300
        ).apply { setMargins(8, 8, 8, 8) }

      binding.imageContainer.addView(imageView)
    }*/

    // List to store Base64 strings
 //   private val imageBase64List = mutableListOf<String>()

    private fun addImageToLayout(uri: Uri) {
        // Convert Uri to Bitmap
        val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, uri)

        // Convert Bitmap to Base64
        val base64String = convertBitmapToBase64(bitmap)
        imageBase64List.add(base64String) // Add to list

        // Parent container for image + delete icon
        val frameLayout = FrameLayout(this).apply {
            layoutParams = LinearLayout.LayoutParams(300, 300).apply {
                setMargins(16, 16, 16, 16)
            }
        }

        // 🖼️ Image View
        val imageView = ImageView(this).apply {
            setImageBitmap(bitmap)
            scaleType = ImageView.ScaleType.CENTER_CROP
            layoutParams = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT
            )
            clipToOutline = true
        }

        // ❌ Delete Icon (overlay)
        val deleteIcon = ImageView(this).apply {
            setImageResource(android.R.drawable.ic_delete)
            layoutParams = FrameLayout.LayoutParams(60, 60).apply {
                gravity = android.view.Gravity.TOP or android.view.Gravity.END
                topMargin = -20
                rightMargin = -20
            }
            setPadding(8, 8, 8, 8)
            background = getDrawable(android.R.drawable.btn_default_small)
            setOnClickListener {
                // Remove this frame layout from container
                (frameLayout.parent as? LinearLayout)?.removeView(frameLayout)
                imageBase64List.remove(base64String) // Remove from list too
            }
        }

        // Add views to frame
        frameLayout.addView(imageView)
        frameLayout.addView(deleteIcon)

        // Add frame layout to horizontal container
        binding.imageContainer.addView(frameLayout)
    }

    // Helper: Convert Bitmap to Base64
    private fun convertBitmapToBase64(bitmap: Bitmap): String {
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, outputStream)
        val byteArray = outputStream.toByteArray()
        return Base64.encodeToString(byteArray, Base64.NO_WRAP)
    }


  /*  private fun handleImageUri(uri: Uri) {
        try {
            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, uri)
            val base64String = convertBitmapToBase64(bitmap)
            imageBase64List.add(base64String)
            addImageToLayout(bitmap, base64String)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }*/

 /*   // 🔄 Convert Bitmap → Base64
    private fun convertBitmapToBase64(bitmap: Bitmap): String {
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, outputStream)
        val byteArray = outputStream.toByteArray()
        return Base64.encodeToString(byteArray, Base64.NO_WRAP)
    }*/


    private fun validate(): Boolean = with(binding) {


        if (etSalePartyName.text.isNullOrBlank()) {
            etSalePartyName.requestFocus()
            tilSaletParty.isErrorEnabled = true
            tilSaletParty.setError("You need to select sale party")
            return false
        } else if (etAvailableLimit.text.isNullOrBlank()) {
            etAvailableLimit.requestFocus()
            tilAvailableLimit.isErrorEnabled = true
            tilAvailableLimit.setError("Out of limit, please contact to sss")
            return false
        } else if (etAverageDays.text.isNullOrBlank()) {
            etAverageDays.requestFocus()
            tilAverageDays.isErrorEnabled = true
            tilAverageDays.setError("You need to select sale party")
            return false
        } else if (radioSubparty.isChecked && etSubParty.text.isBlank() && (!etSubParty.text.toString()
                .equals("self", true))
        ) {
            etSubParty.requestFocus()
            tilSubParty.isErrorEnabled = true
            tilSubParty.setError("You need to select sub party")
            return false
        } else if (radioSubpartyRemark.isChecked && etSubPartyRemark.text.isBlank()) {
            etSubPartyRemark.requestFocus()
            tilSubPartyRemak.isErrorEnabled = true
            tilSubPartyRemak.setError("Please enter remarks")
            return false
        } else if (etTransport.text.isBlank()) {
            etTransport.requestFocus()
            tilTransport.isErrorEnabled = true
            tilTransport.setError("You need to select transport")
            return false
        } else if (etStation.text.isBlank()) {
            etStation.requestFocus()
            tilStation.isErrorEnabled = true
            tilStation.setError("You need to select station")
            return false
        }/* else if (etDispatchType.text.isBlank()) {
            etDispatchType.requestFocus()
            tilStation.isErrorEnabled = true
            tilStation.setError("You need to select station")
            return false
        } */




        else if (etDispatchType.text.isBlank()) {
            etDispatchType.requestFocus()
            tilDispatchType.isErrorEnabled = true
            tilDispatchType.setError("You need to select Dispatch Type")
            return false
        }

        else if (tvDispatchFromDate.text.isBlank()) {
            tvDispatchFromDate.requestFocus()
            tvDispatchFromDate.setBackgroundResource(R.drawable.red_outline)
            tvDispatchFromDate.error = "Please enter From date"
            return false
        } else if (tvDispatchToDate.text.isBlank()) {
            tvDispatchToDate.requestFocus()
            tvDispatchToDate.setBackgroundResource(R.drawable.red_outline)
            tvDispatchToDate.error = "Please enter To date"
            return false
        } /*else if (authViewModel.addItemDataList.size == 0) {
            tvAddItem.requestFocus()
            tvAddItem.setBackgroundResource(R.drawable.red_outline)
            tvAddItem.error = "You need to add some item"
            return false
        }*/
        return true
    }

    private fun showFromDatePicker() {
        val fromDate = Calendar.getInstance()

        // Check if there's already a selected "From Date"
        if (!binding.tvDispatchFromDate.text.isNullOrEmpty()) {
            fromDate.time = dateFormat.parse(binding.tvDispatchFromDate.text.toString())!!
        }

        val fromDateListener =
            DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                fromDate.set(Calendar.YEAR, year)
                fromDate.set(Calendar.MONTH, monthOfYear)
                fromDate.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                binding.tvDispatchFromDate.text = dateFormat.format(fromDate.time)
                binding.tvDispatchFromDate.setBackgroundResource(R.drawable.layout_outline)
                binding.tvDispatchFromDate.error = null
                // Check if ToDate needs to be updated based on the selected FromDate
                if (!binding.tvDispatchToDate.text.isNullOrEmpty()) {
                    val currentToDate = Calendar.getInstance().apply {
                        time = dateFormat.parse(binding.tvDispatchToDate.text.toString())!!
                    }
                    if (currentToDate.before(fromDate)) {
                        val updatedToDate = fromDate.clone() as Calendar
                        updatedToDate.add(Calendar.DAY_OF_YEAR, 0)
                        binding.tvDispatchToDate.text = dateFormat.format(updatedToDate.time)
                    }
                }


            }

        val datePickerDialog = DatePickerDialog(
            this,
            fromDateListener,
            fromDate.get(Calendar.YEAR),
            fromDate.get(Calendar.MONTH),
            fromDate.get(Calendar.DAY_OF_MONTH)
        )

        // Restrict to today's date or future dates, up to 3 months from today
        val today = Calendar.getInstance()
        datePickerDialog.datePicker.minDate = today.timeInMillis

        val maxDate = Calendar.getInstance().apply { add(Calendar.MONTH, 3) }.timeInMillis
        datePickerDialog.datePicker.maxDate = maxDate

        datePickerDialog.show()
    }


    private fun showToDatePicker() {
        val toDate = Calendar.getInstance()

        if (!binding.tvDispatchToDate.text.isNullOrEmpty()) {
            toDate.time = dateFormat.parse(binding.tvDispatchToDate.text.toString())!!
        }

        val toDateListener =
            DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                toDate.set(Calendar.YEAR, year)
                toDate.set(Calendar.MONTH, monthOfYear)
                toDate.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                binding.tvDispatchToDate.text = dateFormat.format(toDate.time)
                binding.tvDispatchToDate.setBackgroundResource(R.drawable.layout_outline)
                binding.tvDispatchToDate.error = null
            }

        val datePickerDialog = DatePickerDialog(
            this,
            toDateListener,
            toDate.get(Calendar.YEAR),
            toDate.get(Calendar.MONTH),
            toDate.get(Calendar.DAY_OF_MONTH)
        )

        if (!binding.tvDispatchFromDate.text.isNullOrEmpty()) {
            val fromDate = Calendar.getInstance().apply {
                time = dateFormat.parse(binding.tvDispatchFromDate.text.toString())!!
            }
            datePickerDialog.datePicker.minDate = fromDate.timeInMillis
            // + (1 * 24 * 60 * 60 * 1000)  // +3 days from fromDate

        }

        // Restrict the ToDate to be at least 3 days after fromDate, and at most 3 months from today's date
        val maxDate = Calendar.getInstance().apply { add(Calendar.MONTH, 3) }.timeInMillis
        datePickerDialog.datePicker.maxDate = maxDate


        datePickerDialog.show()
    }



    ////Marketer
    private fun fetchMarketerList() = if (NetworkUtils.isNetworkAvailable(this)) {
        ProgressUtil.showProgress(this)

        val jsonObject = JsonObject().apply {
            addProperty("SupplierAccountID", AppSharedPreferences.getInstance(this@AddOrderActivityNew).partyId)
        }
        showProgress(this@AddOrderActivityNew)
        authViewModel.marketerListWithSupplierCodeReq(jsonObject)
        authViewModel.marketerListWithSupplierCodeResponse.observe(this) {
            when (it) {
                is Resource.Success -> {
                    dismissProgress()

                    val response = try {
                        Gson().fromJson(it.value, MarketerResponse::class.java)
                    } catch (e: Exception) {
                        e.printStackTrace()
                        showToast("Failed to parse marketer response")
                        return@observe
                    }

                    if (response.success && response.data?.marketerlist != null) {
                        mutableMarketerList.clear()
                        mutableMarketerList.addAll(response.data.marketerlist)
                        setupMarketerListAutoComplete(mutableMarketerList)
                    } else {
                        val msg = response.message ?: "No marketers found in response."
                        showToast(msg)
                    }
                }

                is Resource.Loading -> {
                  dismissProgress()
                }

                is Resource.Failure -> {
                    try {
                        dismissProgress()
                        authViewModel.marketerListWithSupplierCodeResponse.removeObservers(this)
                        if (authViewModel.marketerListWithSupplierCodeResponse.hasObservers()) return@observe
                        val errorMessage = try {
                            val errorJsonObject = JSONObject(it.errorBody ?: "")
                            errorJsonObject.optString("message", "Unknown error")
                        } catch (e: Exception) {
                            "Failed to parse error"
                        }
                         showToast(errorMessage)

                    } catch (e: Exception) {
                        e.message
                    }
                }
                else -> {
                }
            }
        }
    } else {
        showToast(resources.getString(R.string.please_check_internet))
    }
    private fun setupMarketerListAutoComplete(marketerList: List<Marketer>) {
        // Convert Marketer list to Party list (assuming Party is your local model)
        val partyList = marketerList.mapIndexed { index, marketer ->
            Party(index + 1, marketer.MarketerName)
        }

        // Get just the names for the AutoCompleteTextView
        val partyNames = partyList.map { it.name }

        // Set up the adapter
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, partyNames)
        binding.etMarketerName.setAdapter(adapter)

        // Preselect "Rahul" if available
        val defaultName = "Rahul"
        if (partyNames.contains(defaultName)) {
            binding.etMarketerName.setText(defaultName, false) // false to avoid triggering selection
        }

        // Always show clear icon if text is not empty
        binding.tilMarketer.isEndIconVisible = !binding.etSalePartyName.text.isNullOrEmpty()

        // Handle item selection
        binding.etMarketerName.setOnItemClickListener { _, _, position, _ ->
            val selectedName = adapter.getItem(position)
            val selectedParty = partyList.find { it.name == selectedName }
            fetchOrderNoByMarketer(marketerList.get(position).ID)
            selectedParty?.let {
            }
        }

        // Show dropdown on click
        binding.etMarketerName.setOnClickListener {
            binding.etMarketerName.showDropDown()
        }

        // Show dropdown on focus
        binding.etMarketerName.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                binding.etMarketerName.showDropDown()
                Log.d("Focus", "Sale Party EditText gained focus")
            } else {
                Log.d("Focus", "Sale Party EditText lost focus")
            }
        }
    }




    ////Sale Party
    private fun fetchSalePartyList() = if (NetworkUtils.isNetworkAvailable(this)) {
        ProgressUtil.showProgress(this)

        authViewModel.salesPartyReq()
        authViewModel.salesPartyCodeListResponse.observe(this) {
            when (it) {
                is Resource.Success -> {
                    ProgressUtil.dismissProgress()
                    Log.e("salesPartyCodeListResponse",it.value.toString())
                    val response = try {
                        Gson().fromJson(it.value, SalesPartyResponse::class.java)
                    } catch (e: Exception) {
                        e.printStackTrace()
                        showToast("Failed to parse marketer response")
                        return@observe
                    }

                    if (response.success == true && response.data?.salesPartyNames != null) {
                        response.data.salesPartyNames.let { salesList ->
                            saleList.clear()
                            saleList.addAll(salesList)
                            setupSalePartyDropdown(saleList)
                        }
                    } else {
                        val msg = response.message ?: "No marketers found in response."
                        showToast(msg)
                    }

                }

                is Resource.Loading -> {
                    ProgressUtil.dismissProgress()
                }

                is Resource.Failure -> {
                    try {
                        ProgressUtil.dismissProgress()
                        authViewModel.marketerListWithSupplierCodeResponse.removeObservers(this)
                        if (authViewModel.marketerListWithSupplierCodeResponse.hasObservers()) return@observe
                        val errorMessage = try {
                            val errorJsonObject = JSONObject(it.errorBody ?: "")
                            errorJsonObject.optString("message", "Unknown error")
                        } catch (e: Exception) {
                            "Failed to parse error"
                        }
                        showToast(errorMessage)

                    } catch (e: Exception) {
                        e.message
                    }
                }
                else -> {
                }
            }
        }
    } else {
        showToast(resources.getString(R.string.please_check_internet))
    }
    private fun setupSalePartyDropdown(list: MutableList<SalesParty>) {
        val subParties = list.map { it.AccountCode }
        val subPartyAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, subParties)
        binding.etSalePartyName.setAdapter(subPartyAdapter)
        binding.etSalePartyName.setText("", false)
        binding.etSalePartyName.setOnClickListener {
            binding.etSalePartyName.showDropDown()
        }

        binding.etSalePartyName.setOnItemClickListener { _, _, position, _ ->
            val selectedName = subParties[position]
            val name = list.getOrNull(position)?.Name.orEmpty()
            salePartyId = list.getOrNull(position)?.ID.orEmpty()
            val fullName = this.getString(R.string.sale_party_name, selectedName, name)
            hideKeyboard()
            fatchpartyDetails(AppSharedPreferences.getInstance(this).partyId.toString(),salePartyId)
            binding.etSalePartyName.setText(fullName)

        }
    }

    private fun fetchNickName() {
        if (!NetworkUtils.isNetworkAvailable(this)) {
            showToast(getString(R.string.please_check_internet))
            return
        }

        ProgressUtil.showProgress(this)

        val partyId = AppSharedPreferences.getInstance(this).partyId
        if (partyId.isNullOrEmpty()) {
            ProgressUtil.dismissProgress()
            showToast("Invalid party ID")
            return
        }

        val jsonObject = JsonObject().apply {
            addProperty("SupplierAccountID", partyId)
        }

        lifecycleScope.launch {
            authViewModel.suplierNickNameFlow(jsonObject).collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        // Progress already shown above
                    }

                    is Resource.Success -> {
                        ProgressUtil.dismissProgress()
                        val response = resource.value

                        val nickName = response.data?.nickNameData?.nickName
                        if (response.success == true && !nickName.isNullOrBlank()) {
                            binding.etNicName.setText(nickName)
                        } else {
                            showToast(response.message.orEmpty())
                        }
                    }

                    is Resource.Failure -> {
                        ProgressUtil.dismissProgress()

                        val errorMessage = resource.errorBody?.let { errorJson ->
                            try {
                                val errorResponse = Gson().fromJson(errorJson, ApiErrorResponse::class.java)
                                val errorList = errorResponse.title ?: listOf("Unknown validation error")
                                errorList.toString()
                            } catch (e: Exception) {
                                "Error parsing failure message"
                            }
                        } ?: "Something went wrong"

                        showToast(errorMessage)
                    }
                }
            }
        }


    }

    ////Sale Party
    private fun fetchSchemeList() = if (NetworkUtils.isNetworkAvailable(this)) {
        ProgressUtil.showProgress(this)

        authViewModel.schemListReq()
        authViewModel.schemeListResponse.observe(this) {
            when (it) {
                is Resource.Success -> {
                    ProgressUtil.dismissProgress()
                    Log.e("salesPartyCodeListResponse",it.value.toString())
                    val response = try {
                        Gson().fromJson(it.value, SalesPartyResponse::class.java)
                    } catch (e: Exception) {
                        e.printStackTrace()
                        showToast("Failed to parse marketer response")
                        return@observe
                    }

                    if (response.success == true && response.data?.salesPartyNames != null) {
                        response.data.salesPartyNames.let { salesList ->
                            saleList.clear()
                            saleList.addAll(salesList)
                            setupSalePartyDropdown(saleList)
                        }
                    } else {
                        val msg = response.message ?: "No marketers found in response."
                        showToast(msg)
                    }

                }

                is Resource.Loading -> {
                    ProgressUtil.dismissProgress()
                }

                is Resource.Failure -> {
                    try {
                        ProgressUtil.dismissProgress()
                        authViewModel.marketerListWithSupplierCodeResponse.removeObservers(this)
                        if (authViewModel.marketerListWithSupplierCodeResponse.hasObservers()) return@observe
                        val errorMessage = try {
                            val errorJsonObject = JSONObject(it.errorBody ?: "")
                            errorJsonObject.optString("message", "Unknown error")
                        } catch (e: Exception) {
                            "Failed to parse error"
                        }
                        showToast(errorMessage)

                    } catch (e: Exception) {
                        e.message
                    }
                }
                else -> {
                }
            }
        }
    } else {
        showToast(resources.getString(R.string.please_check_internet))
    }

    private fun fetchOrderNoByMarketer(marketerID: String) {
        if (!NetworkUtils.isNetworkAvailable(this)) {
            showToast(getString(R.string.please_check_internet))
            return
        }

        ProgressUtil.showProgress(this)

        val partyId = AppSharedPreferences.getInstance(this).partyId
        if (partyId.isNullOrEmpty()) {
            ProgressUtil.dismissProgress()
            showToast("Invalid party ID")
            return
        }

        val jsonObject = JsonObject().apply {
            addProperty("marketerID", marketerID)
        }

        lifecycleScope.launch {
            authViewModel.orderNoByMarketerReq(jsonObject).collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        // Progress already shown above
                    }

                    is Resource.Success -> {
                        ProgressUtil.dismissProgress()
                        val response = resource.value
                        Log.i("fetchOrderNoByMarketer", "fetchOrderNoByMarketer:$response ")
              /*          val nickName = response.data?.orderNo
                        if (response.success == true && !nickName.isNullOrBlank()) {
                            binding.etSerialNo.setText(nickName)
                        } else {
                            showToast(response.message.orEmpty())
                        }*/
                    }

                    is Resource.Failure -> {
                        ProgressUtil.dismissProgress()

                        val errorMessage = resource.errorBody?.let { errorJson ->
                            try {
                                val errorResponse = Gson().fromJson(errorJson, ApiErrorResponse::class.java)
                                val errorList = errorResponse.title ?: listOf("Unknown validation error")
                                errorList.toString()
                            } catch (e: Exception) {
                                "Error parsing failure message"
                            }
                        } ?: "Something went wrong"

                        showToast(errorMessage)
                    }
                }
            }
        }
    }


    private fun fatchpartyDetails(accountId: String, supplierId: String) {
        lifecycleScope.launch {
            showProgress(this@AddOrderActivityNew)

            authViewModel.partyDetailsByPartyCodeReq("CF73064B-7A6A-4A73-A576-4159262D7742", "9180A043-BD2B-4A8A-B8EF-714A498B16D3").collect { resource ->
                dismissProgress()

                when (resource) {
                    is Resource.Success -> {
                        val subPartyList = resource.value.data?.subPartyList.orEmpty()
                        if (subPartyList.isEmpty()) {
                            clearAndDisableFields("No Sub Parties Available")
                            return@collect
                        }

                        binding.etAvailableLimit.apply {
                            setText("xxxxxxxxx")
                        }
                        binding.etAverageDays.apply {
                            setText("xxxxxxxxx")
                        }
                        binding.etSubParty.apply {
                            isEnabled = true
                            val subPartyNames = subPartyList.mapNotNull { it.SubPartyName }
                            setAdapter(ArrayAdapter(context, android.R.layout.simple_dropdown_item_1line, subPartyNames))
                            setText(subPartyList[0].SubPartyName ?: "")

                        }
                        setupSubPartyDropdown(subPartyList)
                    }

                    is Resource.Failure -> {
                        Toast.makeText(this@AddOrderActivityNew, "Error: ${resource.errorBody}", Toast.LENGTH_SHORT).show()
                    }

                    else -> Unit
                }
            }
        }
    }
    private fun clearAndDisableFields(message: String) {
        binding.etSubParty.setText("")
        binding.etTransport.setText("")
        binding.etStation.setText("")

        binding.etSubParty.isEnabled = false
        binding.etTransport.isEnabled = false
        binding.etStation.isEnabled = false

        Toast.makeText(this@AddOrderActivityNew, message, Toast.LENGTH_SHORT).show()
    }

    private fun handleTransportDropdown(transportList: List<Transport>) {
        if (transportList.isEmpty()) {
            binding.etTransport.setText("")
            binding.etStation.setText("")
            binding.etTransport.isEnabled = false
            binding.etStation.isEnabled = false
            Toast.makeText(this@AddOrderActivityNew, "No Transports Available", Toast.LENGTH_SHORT).show()
            return
        }

        binding.etTransport.apply {
            isEnabled = true
            val transportNames = transportList.mapNotNull { it.TransportName }
            setAdapter(ArrayAdapter(context, android.R.layout.simple_dropdown_item_1line, transportNames))
            setText(transportList[0].TransportName ?: "")

            setOnItemClickListener { _, _, transportPosition, _ ->
                handleStationDropdown(transportList[transportPosition].StationList.orEmpty())
            }
        }

        setupTransPortDropdown(transportList)
    }

    private fun handleStationDropdown(stationList: List<Station>) {
        if (stationList.isEmpty()) {
            binding.etStation.setText("")
            binding.etStation.isEnabled = false
            Toast.makeText(this@AddOrderActivityNew, "No Stations Available", Toast.LENGTH_SHORT).show()
            return
        }

        binding.etStation.apply {
            isEnabled = true
            val stationNames = stationList.mapNotNull { it.StationName }
            setAdapter(ArrayAdapter(context, android.R.layout.simple_dropdown_item_1line, stationNames))
            setText(stationList[0].StationName ?: "")
        }

        setupBookingStationDropdown(stationList)
    }

    private fun setupSubPartyDropdown(list: List<SubParty>) {
        val subParties = list.map { it.SubPartyName }
        val subPartyAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, subParties)
        binding.etSubParty.setAdapter(subPartyAdapter)
        binding.etSubParty.setText("", false)
        binding.etSubParty.setOnClickListener {
            binding.etSubParty.showDropDown()
        }

        binding.etSubParty.setOnItemClickListener { _, _, position, _ ->
            val selectedName = subParties[position]
            val name = list.getOrNull(position)?.SubPartyName.orEmpty()
             subPartyId = list.getOrNull(position)?.SubPartyId.orEmpty()
            val fullName = this.getString(R.string.sale_party_name, selectedName, name)
            hideKeyboard()
            handleTransportDropdown(list[position].TransportList.orEmpty())
            binding.etSubParty.setText(name)

        }
    }
    private fun setupDispatchTypeDropdown(list: List<DispatchTypeItem>) {
        val subParties = list.map { it.value }
        val subPartyAdapter = ArrayAdapter(this, R.layout.item_dropdown,R.id.tvDropdownItem, subParties)
        binding.etDispatchType.setAdapter(subPartyAdapter)
        binding.etDispatchType.setOnClickListener {
            binding.etDispatchType.showDropDown()
        }

        binding.etDispatchType.setOnItemClickListener { _, _, position, _ ->
            val selectedName = subParties[position]
            val name = list.getOrNull(position)?.value.orEmpty()
            dispatchTypeID = list.getOrNull(position)?.id.orEmpty()
            val fullName = this.getString(R.string.sale_party_name, selectedName, name)
            hideKeyboard()
            //handleTransportDropdown(list[position].TransportList.orEmpty())
            binding.etDispatchType.setText(name)

        }
    }

    private fun setupTransPortDropdown(list: List<Transport>) {
        val subParties = list.map { it.TransportName }

        val subPartyAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, subParties)
        binding.etTransport.setAdapter(subPartyAdapter)
        binding.etTransport.setText(list.get(0).TransportName, false)
        binding.etTransport.setOnClickListener {
            binding.etTransport.showDropDown()
        }

        binding.etTransport.setOnItemClickListener { _, _, position, _ ->
            val selectedName = subParties[position]
            val name = list.getOrNull(position)?.TransportName.orEmpty()
            transportId = list.getOrNull(position)?.TransportId.orEmpty()
            val fullName = this.getString(R.string.sale_party_name, selectedName, name)
            hideKeyboard()
            handleStationDropdown(list[position].StationList.orEmpty())
            binding.etTransport.setText(name)

        }
    }
    private fun setupBookingStationDropdown(list: List<Station>) {
        val subParties = list.map { it.StationName }
        val subPartyAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, subParties)
        binding.etStation.setAdapter(subPartyAdapter)

        binding.etStation.setText(list.get(0).StationName, false)
        binding.etStation.setOnClickListener {
            binding.etStation.showDropDown()
        }

        binding.etStation.setOnItemClickListener { _, _, position, _ ->
            val selectedName = subParties[position]
            val name = list.getOrNull(position)?.StationName.orEmpty()
            stationId = list.getOrNull(position)?.StationId.orEmpty()
            val fullName = this.getString(R.string.sale_party_name, selectedName, name)
            hideKeyboard()
            binding.etStation.setText(name)

        }
    }

    private fun setupItemDropdown(list: List<Item>) {
        val subParties = list.map { it.ItemName }
        val subPartyAdapter = ArrayAdapter(this, R.layout.item_dropdown,R.id.tvDropdownItem, subParties)
        binding.etDispatchType.setAdapter(subPartyAdapter)
        binding.etDispatchType.setOnClickListener {
            binding.etDispatchType.showDropDown()
        }

        binding.etDispatchType.setOnItemClickListener { _, _, position, _ ->
            val selectedName = subParties[position]
            val name = list.getOrNull(position)?.ItemName.orEmpty()
            val supplierId = list.getOrNull(position)?.ItemName.orEmpty()
            val fullName = this.getString(R.string.sale_party_name, selectedName, name)
            hideKeyboard()
            //handleTransportDropdown(list[position].TransportList.orEmpty())
            binding.etDispatchType.setText(name)

        }
    }

    private fun fetch(marketerID: String) {
        if (!NetworkUtils.isNetworkAvailable(this)) {
            showToast(getString(R.string.please_check_internet))
            return
        }

        ProgressUtil.showProgress(this)

        val partyId = AppSharedPreferences.getInstance(this).partyId
        if (partyId.isNullOrEmpty()) {
            ProgressUtil.dismissProgress()
            showToast("Invalid party ID")
            return
        }

        val jsonObject = JsonObject().apply {
            addProperty("marketerID", marketerID)
        }

        lifecycleScope.launch {
            authViewModel.orderNoByMarketerReq(jsonObject).collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        // Progress already shown above
                    }

                    is Resource.Success -> {
                        ProgressUtil.dismissProgress()
                        val response = resource.value
                        Log.i("fetchOrderNoByMarketer", "fetchOrderNoByMarketer:$response ")
             /*           val nickName = response.orderDetails?.orderNo
                        if (response.success == true && !nickName.isNullOrBlank()) {
                            binding.etSerialNo.setText(nickName)
                        } else {
                            showToast(response.message.orEmpty())
                        }*/
                    }

                    is Resource.Failure -> {
                        ProgressUtil.dismissProgress()

                        val errorMessage = resource.errorBody?.let { errorJson ->
                            try {
                                val errorResponse = Gson().fromJson(errorJson, ApiErrorResponse::class.java)
                                val errorList = errorResponse.title ?: listOf("Unknown validation error")
                                errorList.toString()
                            } catch (e: Exception) {
                                "Error parsing failure message"
                            }
                        } ?: "Something went wrong"

                        showToast(errorMessage)
                    }
                }
            }
        }
    }


    //API
    private fun fatchDispatchType() {
        lifecycleScope.launch {
            showProgress(this@AddOrderActivityNew)

            authViewModel.dispatchTypeReq().collect { resource ->
                dismissProgress()

                when (resource) {
                    is Resource.Success -> {
                        val list = resource.value.data?.dispatchTypeList.orEmpty()
                        if (list.isEmpty()) {
                            clearAndDisableFields("No Sub Parties Available")
                            return@collect
                        }

                        binding.etDispatchType.apply {
                            isEnabled = true
                            val subPartyNames = list.mapNotNull { it.value }
                            setAdapter(ArrayAdapter(context, R.layout.item_dropdown,R.id.tvDropdownItem, subPartyNames))
                            setText(list[0].value ?: "")
                        }
                        setupDispatchTypeDropdown(list)
                    }

                    is Resource.Failure -> {
                        Toast.makeText(this@AddOrderActivityNew, "Error: ${resource.errorBody}", Toast.LENGTH_SHORT).show()
                    }

                    else -> Unit
                }
            }
        }
    }//API
    private val pcsType = mutableListOf(PcsType())
    private val itemType = mutableListOf(Item())

    private fun pcsTypeList(binding: View) {
        val spinnerType = binding.findViewById<MaterialSpinner>(R.id.spinnerType)
        lifecycleScope.launch {
            showProgress(this@AddOrderActivityNew)
            val jsonObject = JsonObject().apply {
                addProperty("supplierAccountID", AppSharedPreferences.getInstance(this@AddOrderActivityNew).partyId)
                addProperty("orderStatus", "String")
            }

            authViewModel.picTypeeReq(jsonObject).collect { resource ->
                dismissProgress()

                when (resource) {
                    is Resource.Success -> {
                        val list = resource.value.data?.pcsType.orEmpty()
                        if (list.isEmpty()) {
                            clearAndDisableFields("No PcsType Available")
                            return@collect
                        }

                        // 🔥 Extract just the "PcsType" strings
                        val pcsTypeNames = list.map { it.PcsType }

                        val adapter = ArrayAdapter(
                            this@AddOrderActivityNew,
                            android.R.layout.simple_spinner_dropdown_item,
                            pcsTypeNames
                        )
                        spinnerType.setAdapter(adapter)
                    }

                    is Resource.Failure -> {
                        Toast.makeText(this@AddOrderActivityNew, "Error: ${resource.errorBody}", Toast.LENGTH_SHORT).show()
                    }

                    else -> Unit
                }
            }
        }
    }



    private fun fatchItem() {
        lifecycleScope.launch {
            showProgress(this@AddOrderActivityNew)

            authViewModel.fatchItemReq().collect { resource ->
                dismissProgress()

                when (resource) {
                    is Resource.Success -> {
                        val list = resource.value.data?.itemName.orEmpty()
                        if (list.isEmpty()) {
                            clearAndDisableFields("No Sub Parties Available")
                            return@collect
                        }

                        binding.etDispatchType.apply {
                            isEnabled = true
                            val subPartyNames = list.mapNotNull { it.ItemName }
                            availableItemNames.addAll(subPartyNames)
                            setAdapter(ArrayAdapter(context, R.layout.item_dropdown,R.id.tvDropdownItem, subPartyNames))
                            setText(list[0].ItemName ?: "")
                        }
                    //    showCustomBottomSheet()
                     //   setupItemDropdown(list)
                    }

                    is Resource.Failure -> {
                        Toast.makeText(this@AddOrderActivityNew, "Error: ${resource.errorBody}", Toast.LENGTH_SHORT).show()
                    }

                    else -> Unit
                }
            }
        }
    }
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ItemEntryAdapter
    private val itemList = mutableListOf(ItemEntry())
    private val availableItemNames = mutableListOf<String>()

    private fun showCustomBottomSheet() {
       /* list?.let {
            availableItemNames.addAll(it.mapNotNull { item -> item?.ID })
        }*/
        val view = layoutInflater.inflate(R.layout.fragment_add_item_bottom_sheet, null)
        val dialog = BottomSheetDialog(this)
        // Expand when shown
        dialog.setOnShowListener { d ->
            val bottomSheet = (d as BottomSheetDialog)
                .findViewById<FrameLayout>(com.google.android.material.R.id.design_bottom_sheet)
            bottomSheet?.let {
                BottomSheetBehavior.from(it).state = BottomSheetBehavior.STATE_EXPANDED
            }
        }

        fatchItem()

// Ensure it resizes above keyboard
        dialog.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        dialog.setContentView(view)
        recyclerView =view.findViewById(R.id.recyclerView)
        pcsTypeList(view)
        val saveBtn = view.findViewById<AppCompatTextView>(R.id.addItem)
        val closeBtn = view.findViewById<AppCompatTextView>(R.id.closeDialog)

             adapter = ItemEntryAdapter(itemList,availableItemNames,
                 onAddClick = { position ->
                 itemList.add(ItemEntry())
                 adapter.notifyItemInserted(itemList.size - 1)

                 // Also notify that previous last item should now show delete icon
                 if (itemList.size > 1) {
                     val index = itemList.size - 2
                     if (index >= 0 && index < adapter.itemCount) {
                         recyclerView.post {
                             adapter.notifyItemChanged(index)
                         }
                     }
                 }
            },
            onDeleteClick = { position ->
                if (position in itemList.indices) {
                    itemList.removeAt(position)
                    adapter.notifyItemRemoved(position)
                }

            }
        )

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

       saveBtn.setOnClickListener {
            if (validateAllEntries()) {
                Toast.makeText(this, "Valid data submitted!", Toast.LENGTH_SHORT).show()
            }
        }

        closeBtn.setOnClickListener {
            finish()
        }

        dialog.show()
    }

    private fun validateAllEntries(): Boolean {
        itemList.forEachIndexed { index, item ->

            if (item.item.isBlank()) {
                Toast.makeText(this, "Item name at position ${index + 1} is empty", Toast.LENGTH_SHORT).show()
                return false
            }
            if (item.quantity.isBlank() || item.quantity.toIntOrNull() == null) {
                Toast.makeText(this, "Invalid quantity at position ${index + 1}", Toast.LENGTH_SHORT).show()
                return false
            }
        }
        return true
    }

    fun filterItems(list: List<String>, condition: (String) -> Boolean): List<String> {
        return list.filter(condition)
    }

    val items = listOf("Apple", "Banana", "Mango")



}