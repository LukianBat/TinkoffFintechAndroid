package com.memebattle.zaebumbainvest.core.domain

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.net.ConnectivityManager
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.memebattle.zaebumbainvest.App
import com.memebattle.zaebumbainvest.R
import okhttp3.Cache
import okhttp3.CacheControl
import okhttp3.Interceptor
import retrofit2.HttpException
import java.io.File
import java.io.IOException
import java.util.concurrent.TimeUnit

private const val CACHE_CONTROL = "Cache-Control"
private const val CACHE_OFFLINE_MAX_STALE = 7
private const val CACHE_MAX_STALE = 5
private const val HTTP_CACHE = "http-cache"
private const val MAX_SIZE: Long = 10 * 1024 * 1024


fun Activity.snack(text: String) {
    val view = this.window.decorView.rootView
    Snackbar.make(view, text, Snackbar.LENGTH_SHORT).show()
}

fun Fragment.snack(text: String) {
    val activity: Activity = activity ?: return
    val view = activity.window.decorView.rootView
    Snackbar.make(view, text, Snackbar.LENGTH_SHORT).show()
}

fun Activity.toast(text: String) {
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
}

fun Fragment.toast(text: String) {
    val activity: Activity = activity ?: return
    Toast.makeText(activity, text, Toast.LENGTH_SHORT).show()
}

fun provideCache(): Cache? {
    try {
        return Cache(File(App.instance.cacheDir, HTTP_CACHE),
                MAX_SIZE)
    } catch (e: Exception) {

    }
    return null
}

fun provideOfflineCacheInterceptor(): Interceptor {
    return Interceptor { chain ->
        var request = chain.request()

        if (App.hasNetwork().not()) {
            val cacheControl = CacheControl.Builder()
                    .maxStale(CACHE_OFFLINE_MAX_STALE, TimeUnit.DAYS)
                    .build()

            request = request.newBuilder()
                    .cacheControl(cacheControl)
                    .build()
        }

        chain.proceed(request)
    }
}

fun Activity.checkInternet(): Boolean {
    val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val netInfo = connectivityManager.activeNetworkInfo
    return netInfo != null && netInfo.isConnected
}

fun Activity.transStatus(status: String): String {
    return if (status == "success") {
        getString(R.string.success_trans)
    } else getString(R.string.error_trans)
}

fun Fragment.transStatus(status: String): String {
    return if (status == "success") {
        getString(R.string.success_trans)
    } else getString(R.string.error_trans)
}

fun App.checkInternet(): Boolean {
    val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val netInfo = connectivityManager.activeNetworkInfo
    return netInfo != null && netInfo.isConnected
}

fun Fragment.checkInternet(): Boolean {
    val activity: Activity = activity ?: return false
    val connectivityManager = activity.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val netInfo = connectivityManager.activeNetworkInfo
    return netInfo != null && netInfo.isConnected

}

fun provideCacheInterceptor(): Interceptor {
    return Interceptor { chain ->
        val response = chain.proceed(chain.request())
        val cacheControl = CacheControl.Builder()
                .maxAge(CACHE_MAX_STALE, TimeUnit.SECONDS)
                .build()
        response.newBuilder()
                .header(CACHE_CONTROL, cacheControl.toString())
                .build()
    }
}

fun EditText.isCorrectCurrent(): Boolean {
    return !(this.text.toString().contains(' ') || this.text.toString().isEmpty())
}

fun Throwable.throwableMessage(context: Context): String =
        when {
            this is HttpException -> when {
                this.code() == 401 -> context.getString(R.string.signin_error)
                this.code() == 400 -> context.getString(R.string.input_error)
                else -> context.getString(R.string.server_error)
            }
            this is IOException -> context.getString(R.string.connect_error) ?: "error"
            else -> "error"
        }

fun Any.alertInfo(message: String) {
    val builder = AlertDialog.Builder(this as Context)
            .setMessage(message)
            .setCancelable(true)
    val alert = builder.create()
    alert.show()
}