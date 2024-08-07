package com.example.icecaremobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.icecaremobile.presentation.navigator.Route
import com.example.icecaremobile.ui.theme.IceCareMobileTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val splashScreen = installSplashScreen()
//        FirebaseApp.initializeApp(this)
//        val db: FirebaseFirestore = FirebaseFirestore.getInstance()
        enableEdgeToEdge()

        setContent {
            IceCareMobileTheme {
//                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//                    Greeting(
//                        name = "Android",
//                        modifier = Modifier.padding(innerPadding)
//                    )
//                    val navController = rememberNavController()
//                    LandingPage(navController)

//                }
                //initiateFirebase()
                //MyApp()
                Route()
            }
        }
    }
}

//fun initiateFirebase()
//{
//    val db = FirebaseFirestore.getInstance()
//
//    val supplier: MutableMap<String, Any> = HashMap()
//    supplier["transactionDate"] = com.google.firebase.Timestamp(Date())
//    supplier["supplierName"] = "Yushau nalamiru"
//    supplier["amount"] = 1000
//    supplier["rate"] = 10
//    supplier["modeOfPayment"] = "Cash"
//
//    db.collection("suppliers").add(supplier)
//        .addOnSuccessListener { documentReference ->
//            Log.d(
//                TAG, "DocumentSnapshot added with ID: " + documentReference.id
//            )
//        }
//        .addOnFailureListener { e -> Log.w(TAG, "Error adding document", e) }
//}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    IceCareMobileTheme {
        Greeting("Android")
    }
}

//@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun MyApp() {
//    var suppliers by remember { mutableStateOf<List<Supplier>>(emptyList()) }
//    var loading by remember { mutableStateOf(false) }
//    var error by remember { mutableStateOf<String?>(null) }
//
//    LaunchedEffect(Unit) {
//        loading = true
//        getSuppliers(
//            onSuccess = { retrievedSuppliers ->
//                suppliers = retrievedSuppliers
//                loading = false
//            },
//            onFailure = { e ->
//                error = e.message
//                loading = false
//            }
//        )
//    }
//
//    MaterialTheme {
//        Scaffold(
//            topBar = {
//                TopAppBar(
//                    title = { Text("Suppliers") }
//                )
//            }
//        ) { it ->
//            Column(modifier = Modifier.fillMaxSize()) {
//                if (loading) {
//                    CircularProgressIndicator()
//                } else if (error != null) {
//                    Text("Error: $error")
//                } else {
//                    LazyColumn {
//                        items(suppliers) { supplier ->
//                            SupplierItem(supplier)
//                        }
//                    }
//                }
//            }
//        }
//    }
//}
//
//@Composable
//fun SupplierItem(supplier: Supplier) {
//    Column {
//        Text(text = "Supplier ID: ${supplier.supplierID}")
//        Text(text = "Transaction Date: ${supplier.transactionDate.toDate()}")
//        Text(text = "Supplier Name: ${supplier.supplierName}")
//        Text(text = "Amount: ${supplier.amount}")
//        Text(text = "Rate: ${supplier.rate}")
//        Text(text = "Mode of Payment: ${supplier.modeOfPayment}")
//        Divider()
//    }
//}