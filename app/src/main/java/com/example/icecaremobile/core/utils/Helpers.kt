package com.example.icecaremobile.core.utils

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Base64
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.text.NumberFormat
import java.util.Locale
import java.util.regex.Pattern

object Helpers {
    // Validate Email
    fun validateEmail(inputEmail: String): Boolean {
        val emailRegex = "^[a-zA-Z0-9_\\-\\.]+@[a-zA-Z0-9\\-]+\\.[a-zA-Z]{2,4}$"
        return Pattern.compile(emailRegex).matcher(inputEmail.trim()).matches()
    }

    // Validate Password
    fun isPasswordValid(password: String): Boolean {
        val passwordRegex = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*(_|[^\\w])).{8,50}$"
        return Pattern.compile(passwordRegex, Pattern.CASE_INSENSITIVE).matcher(password).matches()
    }

    // Validate Phone Number
    fun isPhoneNumberValid(phoneNumber: String): Boolean {
        val phoneRegex = "^\\+?(\\d[\\d-. ]+)?(\\([\\d-. ]+\\))?[\\d-. ]+\\d$"
        return Pattern.compile(phoneRegex, Pattern.CASE_INSENSITIVE).matcher(phoneNumber).matches()
    }

    // Validate Local Phone Number
    fun isLocalPhoneNumberValid(phone: String): Boolean {
        val localPhoneRegex = "^1?(\\d{11})$"
        return Pattern.compile(localPhoneRegex, Pattern.CASE_INSENSITIVE).matcher(phone).matches()
    }

    // Format Amount to Currency without Decimal
    fun formatAmountToCurrencyWithoutDecimal(currency: String, amount: Double): String {
        val symbols = formatAmountToCurrency(currency)
        val amt = amount ?: 0.0
        val formattedAmount = NumberFormat.getInstance(Locale.US).format(amount)
        return "$symbols $amount"
    }

    // Append Currency to Amount
    fun appendCurrency(amount: String, currency: String = "NGN"): String {
        val symbols = formatAmountToCurrency(currency)
        val amt = amount.toDoubleOrNull() ?: 0.0
        val formattedAmount = NumberFormat.getCurrencyInstance(Locale.US).format(amt)
        return "$symbols$amt"
    }

    // Format Amount to Currency Symbol
    private fun formatAmountToCurrency(currency: String): String {
        return when (currency.uppercase(Locale.ROOT)) {
            "NGN" -> "₦"
            "USD" -> "$"
            "GBP" -> "£"
            "EUR" -> "€"
            "JPY" -> "¥"
            "CAD" -> "C$"
            else -> "NGN"
        }
    }

    @SuppressLint("Recycle")
    fun convertUriToBase64(context: Context, uri: Uri): String? {
        return try {
            val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
            val byteArrayOutputStream = ByteArrayOutputStream()
            val buffer = ByteArray(1024)
            var bytesRead: Int

            while (inputStream?.read(buffer).also { bytesRead = it ?: -1 } != -1) {
                byteArrayOutputStream.write(buffer, 0, bytesRead)
            }

            val byteArray = byteArrayOutputStream.toByteArray()
            Base64.encodeToString(byteArray, Base64.DEFAULT)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun decodeBase64ToBitmap(base64String: String): Bitmap? {
        return try {
            val decodedBytes = Base64.decode(base64String, Base64.DEFAULT)
            BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}