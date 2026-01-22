package com.syber.ssspltd.navigation// NavGraph.kt
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.piashcse.hilt_mvvm_compose_movie.navigation.Screen
import com.syber.ssspltd.R
import com.syber.ssspltd.ui.view.login.LoginScreen
import com.syber.ssspltd.out.AuthViewModel
import com.syber.ssspltd.ui.theme.AppThemeType
import com.syber.ssspltd.ui.theme.ThemeManager
import com.syber.ssspltd.ui.view.CreditNoteToSupplierScreen
import com.syber.ssspltd.ui.view.addorder.SupplierOrderScreen
import com.syber.ssspltd.ui.view.bankdetails.BankDetailsScreen
import com.syber.ssspltd.ui.view.branches.Branch
import com.syber.ssspltd.ui.view.branches.BranchDetailScreen
import com.syber.ssspltd.ui.view.branches.BranchesScreen
import com.syber.ssspltd.ui.view.brand.BrandsScreen
import com.syber.ssspltd.ui.view.courier_report.CourierReportScreen
import com.syber.ssspltd.ui.view.dasbord.PaymentDashboardScreen
import com.syber.ssspltd.ui.view.debit_note.DebitNoteScreen
import com.syber.ssspltd.ui.view.debit_note.DebitNoteToCustomerScreen
import com.syber.ssspltd.ui.view.debit_note.DebittNoteToSupplierScreen
import com.syber.ssspltd.ui.view.details.DetailScreen
import com.syber.ssspltd.ui.view.galleryscreen.ModernGalleryScreen
import com.syber.ssspltd.ui.view.honorlist.HonorListScreen
import com.syber.ssspltd.ui.view.ledger.FilterScreen
import com.syber.ssspltd.ui.view.ledger.LedgerScreen
import com.syber.ssspltd.ui.view.mainActivity.MainScreen

import com.syber.ssspltd.ui.view.morescreen.MoreScreen
import com.syber.ssspltd.ui.view.pendingorder.PendingOrderList
import com.syber.ssspltd.ui.view.productlist.FullImageScreen
import com.syber.ssspltd.ui.view.productlist.ProductDetailsScreen
import com.syber.ssspltd.ui.view.productlist.ProductListScreen
import com.syber.ssspltd.ui.view.productlist.ViewProductScreen
import com.syber.ssspltd.ui.view.profile.ProfileScreen
import com.syber.ssspltd.ui.view.saleReport.SaleReportScreen
import com.syber.ssspltd.ui.view.saleservice.SaleServicesScreen
import com.syber.ssspltd.ui.view.signup.SignUpScreen
import com.syber.ssspltd.ui.view.splash.SplashScreen
import com.syber.ssspltd.ui.view.staybooking.AddGuestScreen
import com.syber.ssspltd.ui.view.staybooking.BookingRequestScreen
import com.syber.ssspltd.ui.view.staybooking.GuestListScreen
import com.syber.ssspltd.ui.view.staybooking.StayBookingListScreen
import com.syber.ssspltd.ui.view.stockinorder.StockInOrderScreen
import com.syber.ssspltd.ui.view.usertype.UserTypeScreen

import com.syber.ssspltd.utils.MyConstant

@RequiresApi(Build.VERSION_CODES.P)
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AppNavGraph(navController: NavHostController) {
    AnimatedNavHost(
        navController = navController,
        startDestination = Screen.Splash.route,
        route = "main_graph",
        enterTransition = { slideInHorizontally { it } + fadeIn() },
        exitTransition = { slideOutHorizontally { -it } + fadeOut() },
        popEnterTransition = { slideInHorizontally { -it } + fadeIn() },
        popExitTransition = { slideOutHorizontally { it } + fadeOut() }
    ) {
        composable(Screen.Splash.route) {
            val viewModel: AuthViewModel = hiltViewModel()
            SplashScreen(navController = navController, viewModel1 = viewModel)
        }
        composable(Screen.Login.route) {

            val viewModel: AuthViewModel = hiltViewModel()
            if(MyConstant.THEMECOLUR.equals("holi")){
                val themeColors = ThemeManager.getThemeColors(AppThemeType.HOLI)
                LoginScreen(navController = navController, viewModel1 = viewModel,themeColors)

            }else{
                val themeColors = ThemeManager.getThemeColors(AppThemeType.DEFAULT)
                LoginScreen(navController = navController, viewModel1 = viewModel,themeColors)

            }

        }
        composable(Screen.UserType.route) {
            val viewModel: AuthViewModel = hiltViewModel()
            if(MyConstant.THEMECOLUR.equals("holi")){
                val themeColors = ThemeManager.getThemeColors(AppThemeType.HOLI)
                UserTypeScreen(navController = navController, viewModel1 = viewModel,themeColors)

            }else{
                val themeColors = ThemeManager.getThemeColors(AppThemeType.DEFAULT)
                UserTypeScreen(navController = navController, viewModel1 = viewModel,themeColors)

            }

        }
        composable(Screen.AddOrder.route) {
            val viewModel: AuthViewModel = hiltViewModel()
            if(MyConstant.THEMECOLUR.equals("holi")){
                val themeColors = ThemeManager.getThemeColors(AppThemeType.HOLI)
                SupplierOrderScreen(navController = navController, viewModel1 = viewModel,themeColors)


            }else{
                val themeColors = ThemeManager.getThemeColors(AppThemeType.DEFAULT)
                SupplierOrderScreen(navController = navController, viewModel1 = viewModel,themeColors)


            }

        }
        composable(Screen.SaleReport.route) {
            val viewModel: AuthViewModel = hiltViewModel()
            SaleReportScreen(navController = navController, viewModel1 = viewModel)
        }
        composable(Screen.PendingOrderList.route) {
            val viewModel: AuthViewModel = hiltViewModel()
            val themeColors = ThemeManager.getThemeColors(AppThemeType.DEFAULT)
            PendingOrderList(navController = navController, viewModel1 = viewModel,themeColors)
        }
        composable(Screen.LegerScreen.route) {
            val viewModel: AuthViewModel = hiltViewModel()
            val themeColors = ThemeManager.getThemeColors(AppThemeType.DEFAULT)
            LedgerScreen(navController = navController, viewModel1 = viewModel, themeColors)
        }

        composable(Screen.DebitNoteScreen.route) {
            val viewModel: AuthViewModel = hiltViewModel()
            val themeColors = ThemeManager.getThemeColors(AppThemeType.DEFAULT)
            DebitNoteScreen(navController = navController, viewModel1 = viewModel, themeColors)
        }

        composable(Screen.CreditNoteToSupplier.route) {
            val viewModel: AuthViewModel = hiltViewModel()
            val themeColors = ThemeManager.getThemeColors(AppThemeType.DEFAULT)
            CreditNoteToSupplierScreen(navController = navController, viewModel1 = viewModel, themeColors)
        }

        composable(Screen.CourierReportScreen.route) {
            val viewModel: AuthViewModel = hiltViewModel()
            val themeColors = ThemeManager.getThemeColors(AppThemeType.DEFAULT)
            CourierReportScreen(navController = navController, viewModel1 = viewModel, themeColors)
        }
        composable(Screen.DebitNoteToSupplier.route) {
            val viewModel: AuthViewModel = hiltViewModel()
            val themeColors = ThemeManager.getThemeColors(AppThemeType.DEFAULT)
            DebittNoteToSupplierScreen(navController = navController, viewModel1 = viewModel, themeColors)
        }
        composable(Screen.DebitNoteToCustomer.route) {
            val viewModel: AuthViewModel = hiltViewModel()
            val themeColors = ThemeManager.getThemeColors(AppThemeType.DEFAULT)
            DebitNoteToCustomerScreen(navController = navController, viewModel1 = viewModel, themeColors)
        }
        composable(Screen.SaleServicesScreen.route) {
            val viewModel: AuthViewModel = hiltViewModel()
            val themeColors = ThemeManager.getThemeColors(AppThemeType.DEFAULT)
            SaleServicesScreen(navController = navController, viewModel1 = viewModel, themeColors)
        }
        composable(Screen.DasbordScreen.route) {
            val viewModel: AuthViewModel = hiltViewModel()
         //   val uiState = viewModel.dashboardUiState.collectAsState().value

            PaymentDashboardScreen(
                navController = navController,
                viewModel1 = viewModel,
            )
        }
        composable(Screen.StockInOrderScreen.route) {
            val viewModel: AuthViewModel = hiltViewModel()
            val themeColors = ThemeManager.getThemeColors(AppThemeType.DEFAULT)
            StockInOrderScreen(navController = navController, viewModel1 = viewModel/*,themeColors*/)
        }
        composable(Screen.MoreScreen.route) {
            val viewModel: AuthViewModel = hiltViewModel()
            val themeColors = ThemeManager.getThemeColors(AppThemeType.DEFAULT)
            MoreScreen(navController = navController/*, viewModel1 = viewModel,themeColors*/)
        }
        composable(Screen.ModernGalleryScreen.route) {
            val viewModel: AuthViewModel = hiltViewModel()
            val themeColors = ThemeManager.getThemeColors(AppThemeType.DEFAULT)
            ModernGalleryScreen(navController = navController, viewModel1 = viewModel,themeColors)
        }
        composable(Screen.BrandsScreen.route) {
            val viewModel: AuthViewModel = hiltViewModel()
            val themeColors = ThemeManager.getThemeColors(AppThemeType.DEFAULT)
            BrandsScreen(navController = navController, viewModel1 = viewModel,themeColors)
        }
       /* composable(Screen.ProductListScreen.route) {
            val viewModel: AuthViewModel = hiltViewModel()
            val themeColors = ThemeManager.getThemeColors(AppThemeType.DEFAULT)
            ProductListScreen(navController = navController, viewModel1 = viewModel,themeColors)
        }*/

        composable("full_image_screen") { backStackEntry ->
            val sharedViewModel: AuthViewModel =
                hiltViewModel(navController.previousBackStackEntry!!)

            FullImageScreen(
                navController = navController,
                viewModel = sharedViewModel
            )
        }


        composable(
            route = Screen.ProductListScreen.route + "/{branchId}",
            arguments = listOf(navArgument("branchId") { type = NavType.StringType })
        ) { backStackEntry ->

            val viewModel: AuthViewModel = hiltViewModel()
            val themeColors = ThemeManager.getThemeColors(AppThemeType.DEFAULT)

            val branchId = backStackEntry.arguments?.getString("branchId") ?: ""

            ProductListScreen(
                navController = navController,
                viewModel1 = viewModel,
                branchId = branchId,
                themeColors = themeColors
            )
        }


        composable(Screen.ViewProductScreen.route) {
            val viewModel: AuthViewModel = hiltViewModel()
            val themeColors = ThemeManager.getThemeColors(AppThemeType.DEFAULT)
            ViewProductScreen(navController = navController/*, viewModel1 = viewModel,themeColors*/)
        }
        composable(Screen.ProfileScreen.route) {
            val viewModel: AuthViewModel = hiltViewModel()
            val themeColors = ThemeManager.getThemeColors(AppThemeType.DEFAULT)
            ProfileScreen(navController = navController/*, viewModel1 = viewModel,themeColors*/)
        }
        composable(Screen.BranchDetailScreen.route) {
            val viewModel: AuthViewModel = hiltViewModel()


            val themeColors = ThemeManager.getThemeColors(AppThemeType.DEFAULT)
            BranchDetailScreen(navController = navController/*, viewModel1 = viewModel,themeColors*/)
        }
        composable(
            route = Screen.FilterScreen.route,
            enterTransition = {
                slideInVertically(
                    initialOffsetY = { it },   // 👈 from bottom
                    animationSpec = tween(400)
                )
            },
            exitTransition = {
                slideOutVertically(
                    targetOffsetY = { -it },  // 👈 exit to top
                    animationSpec = tween(400)
                )
            },
            popEnterTransition = {
                slideInVertically(
                    initialOffsetY = { -it }, // 👈 re-enter from top
                    animationSpec = tween(400)
                )
            },
            popExitTransition = {
                slideOutVertically(
                    targetOffsetY = { it },   // 👈 back: top → bottom
                    animationSpec = tween(400)
                )
            }
        ) {
            val viewModel: AuthViewModel = hiltViewModel()
            val themeColors = ThemeManager.getThemeColors(AppThemeType.DEFAULT)
            FilterScreen(navController = navController /*, viewModel1 = viewModel, themeColors*/)
        }

        composable(Screen.FilterScreen.route) {
            val viewModel: AuthViewModel = hiltViewModel()


            val themeColors = ThemeManager.getThemeColors(AppThemeType.DEFAULT)
            FilterScreen(navController = navController/*, viewModel1 = viewModel,themeColors*/)
        }


        // Booking Request Screen
        composable(
            route = Screen.BookingRequestScreen.route
        ) { navBackStackEntry ->

            val parentEntry = remember(navBackStackEntry) {
                navController.getBackStackEntry("main_graph")
            }

            val viewModel1: AuthViewModel = hiltViewModel(parentEntry)

            BookingRequestScreen(
                navController = navController,
                viewModel1 = viewModel1
            )
        }

        composable(
            route = Screen.StayBookingListScreen.route
        ) { navBackStackEntry ->

            val parentEntry = remember(navBackStackEntry) {
                navController.getBackStackEntry("main_graph")
            }

            val viewModel1: AuthViewModel = hiltViewModel(parentEntry)
            val themeColors = ThemeManager.getThemeColors(AppThemeType.DEFAULT)
            StayBookingListScreen(
                navController = navController,
                viewModel1 = viewModel1, themeColors
            )
        }
      /*  composable(Screen.StayBookingListScreen.route) {
            val viewModel: AuthViewModel = hiltViewModel()
            val themeColors = ThemeManager.getThemeColors(AppThemeType.DEFAULT)
            StayBookingListScreen(navController = navController, viewModel1 = viewModel,themeColors)
        }*/
       /* composable(Screen.BookingRequestScreen.route) {
            val viewModel1: AuthViewModel = hiltViewModel()
            val themeColors = ThemeManager.getThemeColors(AppThemeType.DEFAULT)

            BookingRequestScreen(
                navController = navController,
                viewModel1 = viewModel1,
                onSaveClick = {
                    // 👉 Navigate somewhere after saving
                    navController.navigate("confirmation")
                }
            )
        }*/

        composable(Screen.AddGuestScreen.route) {
            val viewModel: AuthViewModel = hiltViewModel()
            val themeColors = ThemeManager.getThemeColors(AppThemeType.DEFAULT)

            AddGuestScreen(
                navController = navController,
                viewModel = viewModel,
               /*
                themeColors = themeColors,*/
                onSave = {
                   // viewModel.saveGuest()
                    navController.popBackStack()
                }
            )
        }
        composable(Screen.HonorListScreen.route) {
            val viewModel: AuthViewModel = hiltViewModel()
            val themeColors = ThemeManager.getThemeColors(AppThemeType.DEFAULT)
            HonorListScreen(navController = navController, viewModel1 = viewModel,themeColors)
        }
        composable(Screen.GuestListScreen.route) {
            val guests = remember { mutableStateListOf("Rahul", "Vipin Sharma", "Shubham Shrivastav", "Chandra Sir") }

            GuestListScreen(
                navController = navController,
                guests = guests,
                onDeleteGuest = { guest -> guests.remove(guest) }
            )
        }
        composable(Screen.ProductDetailsScreen.route) {
            val viewModel: AuthViewModel = hiltViewModel()
            val themeColors = ThemeManager.getThemeColors(AppThemeType.DEFAULT)
            ProductDetailsScreen(navController = navController/*, viewModel1 = viewModel,themeColors*/)
        }
        composable(Screen.BranchesScreen.route) {
            val viewModel: AuthViewModel = hiltViewModel()
            val themeColors = ThemeManager.getThemeColors(AppThemeType.DEFAULT)
            BranchesScreen(navController = navController/*, viewModel1 = viewModel,themeColors*/)
        }
        composable(Screen.SignUp.route) {
            val viewModel: AuthViewModel = hiltViewModel()
            if(MyConstant.THEMECOLUR.equals("holi")){
                val themeColors = ThemeManager.getThemeColors(AppThemeType.HOLI)
                SignUpScreen(navController = navController, viewModel1 = viewModel,themeColors)
            }else{
                val themeColors = ThemeManager.getThemeColors(AppThemeType.DEFAULT)
                SignUpScreen(navController = navController, viewModel1 = viewModel,themeColors)
            }
        }
        composable(Screen.MainActivity.route) {
            val viewModel: AuthViewModel = hiltViewModel()
            if(MyConstant.THEMECOLUR.equals("holi")){
                val themeColors = ThemeManager.getThemeColors(AppThemeType.HOLI)
                MainScreen(navController = navController, viewModel1 = viewModel,themeColors)
            }else{
                val themeColors = ThemeManager.getThemeColors(AppThemeType.DEFAULT)
                MainScreen(navController = navController, viewModel1 = viewModel,themeColors)
            }



        }

        composable("detail_screen/{name}") { backStackEntry ->
            val name = backStackEntry.arguments?.getString("name")
            DetailScreen(name)
        }
    }


/*    NavHost(navController = navController, startDestination = Screen.Login.route) {
        composable(Screen.Login.route) {
            val viewModel: AuthViewModel = hiltViewModel()
            LoginScreen(navController = navController, viewModel1 = viewModel)
        }

       *//* composable(Screen.Home.route) {
            val viewModel: AuthViewModel = hiltViewModel()
            HomeScreen(navController = navController, viewModel1 = viewModel)
        }*//*
        composable(Screen.UserType.route) {
            val viewModel: AuthViewModel = hiltViewModel()
            UserTypeScreen(navController = navController, viewModel1 = viewModel)
        }
        composable(Screen.SignUp.route) {
            val viewModel: AuthViewModel = hiltViewModel()
            if(MyConstant.THEMECOLUR.equals("holi")){
                val themeColors = ThemeManager.getThemeColors(AppThemeType.HOLI)
                SignUpScreen(navController = navController, viewModel1 = viewModel,themeColors)
            }else{
                val themeColors = ThemeManager.getThemeColors(AppThemeType.DEFAULT)
                SignUpScreen(navController = navController, viewModel1 = viewModel,themeColors)
            }


        }
     *//*   if(MyConstant.THEMECOLUR.equals("holi")){
            val themeColors = ThemeManager.getThemeColors(AppThemeType.DIWALI)
        }*//*

        composable(Screen.MainActivity.route) {
            val viewModel: AuthViewModel = hiltViewModel()
            if(MyConstant.THEMECOLUR.equals("holi")){
                val themeColors = ThemeManager.getThemeColors(AppThemeType.HOLI)
                MainScreen(themeColors)
            }else{
                val themeColors = ThemeManager.getThemeColors(AppThemeType.DEFAULT)
                MainScreen(themeColors)
            }



        }

        composable("detail_screen/{name}") { backStackEntry ->
            val name = backStackEntry.arguments?.getString("name")
            DetailScreen(name)
        }
    }*/

}
