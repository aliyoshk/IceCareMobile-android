package com.example.icecaremobile.presentation.navigator

sealed class Screen(val route: String)
{
    object SplashScreen: Screen("splash_screen")
    object LandingScreen: Screen("landing_screen")
    object RegistrationScreen: Screen("registration_screen")
    object LoginScreen: Screen("login_screen")
    object DashboardScreen: Screen("dashboard_screen")
    object SendMoneyScreen: Screen("send_money_screen")
    object TransferScreen: Screen("transfer_screen")
    object MultiTransferScreen: Screen("multi_transfer_screen")
    object SubmitTransactionScreen: Screen("submit_transaction_screen")
    object ThirdPartyTransferScreen: Screen("third_party_transfer_screen")
    object AccountTransferScreen: Screen("account_transfer_screen")
    object GetHelpScreen: Screen("get_help_screen")
    object FundAccountScreen: Screen("fund_account_screen")
    object TransferStatusScreen: Screen("transfer_status_screen")
    object TransferReceiptScreen: Screen("transfer_receipt_screen")
    object ConverterScreen: Screen("converter_screen")
    object HistoryScreen: Screen("history_screen")
    object AccountsScreen: Screen("accounts_screen")
}