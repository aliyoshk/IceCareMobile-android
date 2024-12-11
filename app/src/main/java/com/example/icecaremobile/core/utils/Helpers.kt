package com.example.icecaremobile.core.utils

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Base64
import java.io.ByteArrayOutputStream
import java.io.InputStream
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

    // Append Currency to Amount
    fun formatAmountToCurrency(amount: Double?, currency: String = "NGN"): String {
        val symbols = when (currency.uppercase()) {
            "NGN" -> "₦"
            "USD" -> "$"
            "GBP" -> "£"
            "EUR" -> "€"
            "JPY" -> "¥"
            "CAD" -> "C$"
            else -> currency
        }
        if (amount == null) return ""
        val formattedAmount = String.format(Locale.US, "%,.2f", amount ?: 0.0)
        return "$symbols$formattedAmount"
    }

    fun getAmountWithoutCommaAndCurrency(amount: String, currency: String = "NGN"): String {
        val symbols = when (currency.uppercase()) {
            "NGN" -> "₦"
            "USD" -> "$"
            "GBP" -> "£"
            "EUR" -> "€"
            "JPY" -> "¥"
            "CAD" -> "C$"
            else -> ""
        }

        val formattedAmount = amount
            .replace(symbols, "", ignoreCase = true)
            .replace(",", "")
            .replace(" ", "")

        return formattedAmount
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