package com.example.icecaremobile.presentation.navigator

import android.net.Uri
import android.os.Bundle
import androidx.navigation.NavType
import com.example.icecaremobile.domain.model.Response.TransactionHistory
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

object CustomNavType {

    val History = object: NavType<TransactionHistory>(
        isNullableAllowed = false,
    ) {
        override fun serializeAsValue(value: TransactionHistory): String {
            return Uri.encode(Json.encodeToString(value))
        }

        override fun parseValue(value: String): TransactionHistory {
            return Json.decodeFromString(Uri.decode(value))
        }

        override fun put(bundle: Bundle, key: String, value: TransactionHistory) {
            bundle.putString(key, Json.encodeToString(value))
        }

        override fun get(bundle: Bundle, key: String): TransactionHistory? {
            return Json.decodeFromString(bundle.getString(key) ?: return null)
        }
    }
}