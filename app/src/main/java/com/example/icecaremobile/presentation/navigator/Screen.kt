package com.example.icecaremobile.presentation.navigator

sealed class Screen(val route: String)
{
    object LandingScreen: Screen("landing_screen")
    object RegistrationScreen: Screen("registration_screen")
    object LoginScreen: Screen("login_screen")
    object DashboardScreen: Screen("dashboard_screen")
    object SendMoneyScreen: Screen("send_money_screen")
    object AccountScreen: Screen("account_screen")
    object TransferScreen: Screen("transfer_screen")
    object TransferSummaryScreen: Screen("transfer_summary_screen")
    object MultipleTransferScreen: Screen("multiple_transfer_screen")
    object AccountBalanceTransferScreen: Screen("account_balance_transfer_screen")
    object ThirdPartyTransferScreen: Screen("third_party_transfer_screen")
    object RemitStatusScreen: Screen("remit_status_screen")
    object SubmissionScreen: Screen("submission_screen")
    object GetHelpScreen: Screen("get_help_screen")
    object FundAccountScreen: Screen("fund_account_screen")
    object TransferReceiptScreen: Screen("transfer_receipt_screen")
    object ConverterScreen: Screen("converter_screen")
    object HistoryScreen: Screen("history_screen")
}