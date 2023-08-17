package com.example.mypsychologist

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities.NET_CAPABILITY_INTERNET
import android.os.Build
import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import android.os.Parcelable
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import com.example.mypsychologist.databinding.CardViewGroupBinding
import com.example.mypsychologist.di.AppComponent
import com.example.mypsychologist.domain.entity.PsychologistCard
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.GenericTypeIndicator
import java.text.SimpleDateFormat
import java.util.*

fun Fragment.setupCard(
    card: CardViewGroupBinding,
    titleRes: Int,
    descriptionRes: Int,
    imageRes: Int? = null,
    backgroundRes: Int? = null,
) {
    card.apply {
        cardTitle.text = getString(titleRes)
        cardDescription.text = getString(descriptionRes)
        imageRes?.let { cardImage.setImageResource(it) }
        backgroundRes?.let { this.card.background = getDrawable(requireContext(), it) }
    }
}

fun Context.getAppComponent(): AppComponent = (this.applicationContext as App).appComponent

inline fun <reified T : Parcelable> Bundle.parcelable(key: String): T? = when {
    SDK_INT >= 33 -> getParcelable(key, T::class.java)
    else -> @Suppress("DEPRECATION") getParcelable(key) as? T
}

fun Date.toDateString(): String {
    val format = SimpleDateFormat("dd/M/yyyy", Locale.getDefault())
    return format.format(this)
}

fun Fragment.showToast(message: String) {
    Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
}

fun Fragment.isNetworkConnect(): Boolean =
    (requireActivity() as ConnectionChecker).isConnection()

fun Long.toYears(): Long =
    this / MILLIS_IN_YEAR

const val MILLIS_IN_YEAR = 31536000000

fun <T> DataSnapshot.getTypedValue() = getValue(object :
    GenericTypeIndicator<T>() {})
