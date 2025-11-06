package net.simplifiedcoding.data.network
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import retrofit2.HttpException

interface SafeApiCall {
    suspend fun <T> safeApiCall(apiCall: suspend () -> T): Resource<T> {
        return withContext(Dispatchers.IO) {
            try {
                Resource.Success(apiCall.invoke())
            } catch (throwable: Throwable) {
                when (throwable) {
                    is HttpException -> {
                        try {
                            val jObjError = JSONObject(throwable.response()!!.errorBody()!!.string())
                            Log.d("quran  error response", "" + jObjError)
                            Resource.Failure("", 0, "${jObjError}")
                        } catch (e: Exception) {
                            Resource.Failure("", 0, "")
                        }
                    }
                    else -> {
                        Resource.Failure("", 0, "")
                    }
                }
            } /*catch (e: HttpException) {
                if (e.code() == 500) {
                    // Handle 500 Internal Server Error
                    val jObjError = JSONObject(e.response()!!.errorBody()!!.string())
                    Resource.Failure("", 0, "${jObjError}")
                } else {
                    // Handle other HTTP errors
                    Resource.Failure("", 0, "${e}")
                }
            }*/
        }
    }
}