package com.example.icecaremobile.presentation.navigator

import com.example.icecaremobile.domain.model.Response.TransactionHistory
import kotlinx.serialization.Serializable

//sealed class Screen(val route: String)
//{
//    object LandingScreen: Screen("landing_screen")
//    object RegistrationScreen: Screen("registration_screen")
//    object LoginScreen: Screen("login_screen")
//    object DashboardScreen: Screen("dashboard_screen")
//    object SendMoneyScreen: Screen("send_money_screen")
//    object AccountScreen: Screen("account_screen")
//    object TransferScreen: Screen("transfer_screen")
//    object TransferSummaryScreen: Screen("transfer_summary_screen")
//    object MultipleTransferScreen: Screen("multiple_transfer_screen")
//    object AccountBalanceTransferScreen: Screen("account_balance_transfer_screen")
//    object ThirdPartyTransferScreen: Screen("third_party_transfer_screen")
//    object RemitStatusScreen: Screen("remit_status_screen")
//    object SubmissionScreen: Screen("submission_screen")
//    object GetHelpScreen: Screen("get_help_screen")
//    object FundAccountScreen: Screen("fund_account_screen")
//    object TransferReceiptScreen: Screen("transfer_receipt_screen")
//    object ConverterScreen: Screen("converter_screen")
//    object HistoryScreen: Screen("history_screen")
//}

class Screen
{
    @Serializable
    object LandingScreen

    @Serializable
    object RegistrationScreen

    @Serializable
    object LoginScreen

    @Serializable
    object DashboardScreen

    @Serializable
    object SendMoneyScreen

    @Serializable
    object NavSendMoneyScreen

    @Serializable
    data class AccountScreen(val key: String? = null)

    @Serializable
    object TransferScreen

    @Serializable
    data class TransferSummaryScreen(
        val bankName: String?,
        val accountName: String?,
        val accountNumber: String?,
        val amount: String?,
        val dollarAmount: String?,
        val email: String? = null,
        val dollarRate: String? = null,
        val description: String? = null
    )

    @Serializable
    object MultipleTransferScreen

    @Serializable
    object AccountBalanceTransferScreen

    @Serializable
    object ThirdPartyTransferScreen

    @Serializable
    object TopUpAccountScreen

    @Serializable
    data class RemitStatusScreen(val key: String)

    @Serializable
    data class SubmissionScreen(val key: String, val data: String)

    @Serializable
    object ConverterScreen

    @Serializable
    object TransactionHistoryScreen

    @Serializable
    object StatusScreen

    @Serializable
    data class ReceiptScreen(
        val data: TransactionHistory,
    )

    @Serializable
    object MoreScreen

    @Serializable
    object CallScreen

    object GetHelpScreen
    object FundAccountScreen
    object TransferReceiptScreen
    object HistoryScreen
}