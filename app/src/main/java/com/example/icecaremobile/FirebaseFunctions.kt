package com.example.icecaremobile

import com.google.firebase.Firebase
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore

data class Supplier(
    val supplierID: String = "",
    val transactionDate: Timestamp = Timestamp.now(),
    val supplierName: String = "",
    val amount: Double = 0.0,
    val rate: Double = 0.0,
    val modeOfPayment: String = ""
)

fun saveSupplier(supplier: Supplier, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
    val db = Firebase.firestore
    db.collection("suppliers")
        .add(supplier)
        .addOnSuccessListener {
            onSuccess()
        }
        .addOnFailureListener { e ->
            onFailure(e)
        }
}

fun getSuppliers(onSuccess: (List<Supplier>) -> Unit, onFailure: (Exception) -> Unit) {
    val db = FirebaseFirestore.getInstance()
    db.collection("suppliers")
        .get()
        .addOnSuccessListener { result ->
            val suppliers = result.toObjects(Supplier::class.java)
            onSuccess(suppliers)
        }
        .addOnFailureListener { e ->
            onFailure(e)
        }
}