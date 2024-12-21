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

    fun convertAmountToWords(amount: Double, currency: String = "NGN"): String {
        if (amount == 0.0) return "Zero ${getCurrencySuffix(0, currency, isWhole = true)}"

        val numberToWordsMap = mapOf(
            1 to "one", 2 to "two", 3 to "three", 4 to "four", 5 to "five",
            6 to "six", 7 to "seven", 8 to "eight", 9 to "nine", 10 to "ten",
            11 to "eleven", 12 to "twelve", 13 to "thirteen", 14 to "fourteen",
            15 to "fifteen", 16 to "sixteen", 17 to "seventeen", 18 to "eighteen",
            19 to "nineteen"
        )

        val tensMap = mapOf(
            20 to "twenty", 30 to "thirty", 40 to "forty", 50 to "fifty",
            60 to "sixty", 70 to "seventy", 80 to "eighty", 90 to "ninety"
        )

        val units = listOf("", "thousand", "million", "billion", "trillion")

        fun convertLessThanOneThousand(amount: Int): String {
            return when {
                amount == 0 -> ""
                amount < 20 -> numberToWordsMap[amount] ?: ""
                amount < 100 -> {
                    val tens = amount / 10 * 10
                    val units = amount % 10
                    "${tensMap[tens]}${if (units != 0) " " + convertLessThanOneThousand(units) else ""}"
                }
                else -> {
                    val hundreds = amount / 100
                    val remainder = amount % 100
                    "${numberToWordsMap[hundreds]} hundred${if (remainder != 0) " and " + convertLessThanOneThousand(remainder) else ""}"
                }
            }
        }

        fun convertWholeNumber(amount: Long): String {
            if (amount == 0L) return ""
            var wordRepresentation = ""
            var currentAmount = amount
            var unitIndex = 0

            while (currentAmount > 0) {
                val chunk = (currentAmount % 1000).toInt()
                if (chunk != 0) {
                    val chunkWords = convertLessThanOneThousand(chunk)
                    wordRepresentation = "$chunkWords ${units[unitIndex]} ${wordRepresentation.trim()}"
                }
                currentAmount /= 1000
                unitIndex++
            }
            return wordRepresentation.trim()
        }

        val wholeAmount = amount.toLong()
        val fractionalAmount = ((amount - wholeAmount) * 100).toInt()

        val wholeWords = if (wholeAmount > 0) {
            "${convertWholeNumber(wholeAmount)} ${getCurrencySuffix(wholeAmount.toInt(), currency, isWhole = true)}"
        } else ""

        val fractionalWords = if (fractionalAmount > 0) {
            "${convertLessThanOneThousand(fractionalAmount)} ${getCurrencySuffix(fractionalAmount, currency, isWhole = false)}"
        } else ""

        return when {
            wholeWords.isNotEmpty() && fractionalWords.isNotEmpty() -> {
                "$wholeWords and $fractionalWords only".replaceFirstChar { it.uppercase() }
            }
            wholeWords.isNotEmpty() -> "$wholeWords only".replaceFirstChar { it.uppercase() }
            fractionalWords.isNotEmpty() -> "$fractionalWords only".replaceFirstChar { it.uppercase() }
            else -> ""
        }
    }
    fun getCurrencySuffix(amount: Int, currency: String, isWhole: Boolean): String {
        return when (currency.uppercase()) {
            "NGN" -> if (isWhole) "Naira" else "Kobo"
            "USD" -> if (isWhole) "dollar" else "cents"
            "EUR" -> if (isWhole) "Euro" else "cents"
            "GBP" -> if (isWhole) "Pound Sterling" else "cents"
            else -> if (isWhole) "units" else "sub-units"
        }
    }
}