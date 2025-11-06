package com.piashcse.hilt_mvvm_compose_movie.navigation



// NavigationRoutes.kt
sealed class Screen(val route: String) {
    object Login : Screen("splash")
    object Splash : Screen("login")
    object Home : Screen("home_screen")
    object UserType : Screen("user_type")
    object AddOrder : Screen("add_order")
    object SaleReport : Screen("sale_report")
    object PendingOrderList : Screen("pending_orderlist")
    object ViewProductScreen : Screen("viewProductScreen")

    object ProductDetailsScreen : Screen("productDetailsScreen")
    object BranchesScreen : Screen("branchesScreen")
    object HonorListScreen : Screen("honorListScreen")
    object GuestListScreen : Screen("guestListScreen")
    object LegerScreen : Screen("ledger")
    object DasbordScreen : Screen("dasbordScreen")
    object BookingRequestScreen : Screen("bookingRequestScreen")
    object StayBookingListScreen : Screen("stayBookingListScreen")
    object AddGuestScreen : Screen("addGuestScreen")
    object BrandsScreen : Screen("brandsScreen")
    object ProductListScreen : Screen("productListScreen")
    object StockInOrderScreen : Screen("stockInOrderScreen")
    object ModernGalleryScreen : Screen("modernGalleryScreen")
    object MoreScreen : Screen("moreScreen")
    object SignUp : Screen("sign_up")
    object MainActivity : Screen("main_activity")
    object ProfileScreen : Screen("profile")
    object FilterScreen : Screen("FilterScreen")
    object BranchDetailScreen : Screen("branchDetailScreen")

    object BankDetailsScreen : Screen("BankDetailsScreen")
    object AboutScreen : Screen("about")
    object FeedbackScreen : Screen("feedback")
    object FAQScreen : Screen("faq")
    object ContactScreen : Screen("contact")
    object LogoutScreen : Screen("logout")
    object Detail : Screen("detail_screen/{name}") {
        fun passName(name: String) = "detail_screen/$name"
    }

}