package com.hakmar.employeelivetracking.common.presentation.graphs


/*@Composable
fun HomeNavGraph(
    navController: NavHostController,
    mainViewModel: MainViewModel,
    generalShiftService: GeneralShiftService?,
    storeShiftService: StoreShiftService?,
) {
    val routerHome = RouterHome(navController)
    LaunchedEffect(Unit) {
        val route = mainViewModel.getLastRoute()
        println(route)
        route?.let {
            navController.navigate(route)
        }
    }
    NavHost(
        navController = navController,
        route = Destination.Home.base,
        startDestination = HomeDestination.BsStores.base
    ) {
        composable(
            route = HomeDestination.BsStores.base,
            deepLinks = listOf(
                navDeepLink {
                    uriPattern = DeepLinkRouter.baseUri + HomeDestination.BsStores.base
                    action = Intent.ACTION_VIEW
                },
                navDeepLink {
                    uriPattern = DeepLinkRouter.insideAppUri + HomeDestination.BsStores.base
                    action = Intent.ACTION_VIEW
                }
            )
        ) {
            BsStoresScreen(
                generalShiftService = generalShiftService,
                onStoreClick = {
                    routerHome.goQRScreen()
                }
            )
        }
        composable(route = HomeDestination.PmStores.base) {
            PmStoreScreen()
        }
        composable(
            route = HomeDestination.Navigation.base,
            deepLinks = listOf(
                navDeepLink {
                    uriPattern = DeepLinkRouter.insideAppUri + HomeDestination.Navigation.base
                    action = Intent.ACTION_VIEW
                },
                navDeepLink {
                    uriPattern = DeepLinkRouter.baseUri + HomeDestination.Navigation.base
                    action = Intent.ACTION_VIEW
                },
            )
        ) {
            NavigationScreen(
                mainViewModel = mainViewModel
            )
        }
        composable(route = HomeDestination.Profile.base) {
            ProfileGraph(
                mainViewModel = mainViewModel,
                onNotificationClick = {
                    routerHome.goNotification()
                }
            )
        }

        composable(HomeDestination.QRScreen.base) {
            QRScreen()
        }

        composable(
            route = HomeDestination.StoreDetail.path,
            arguments = listOf(navArgument("storeId") { type = NavType.StringType })
        ) {
            StoreDetailScreen(
                onBackPressed = {
                    navController.popBackStack()
                },
                onAppBarConfig = {
                    mainViewModel.updateAppBar(it)
                },
                storeShiftService = storeShiftService
            )
        }
        composable(
            route = HomeDestination.Notification.base,
            deepLinks = listOf(
                navDeepLink {
                    uriPattern = DeepLinkRouter.insideAppUri + HomeDestination.Notification.base
                    action = Intent.ACTION_VIEW
                },
                navDeepLink {
                    uriPattern = DeepLinkRouter.baseUri + HomeDestination.Notification.base
                    action = Intent.ACTION_VIEW
                },
            )
        ) {
            NotificationScreen(
                onAppBarConfig = { mainViewModel.updateAppBar(it) },
                onBackPressed = { navController.popBackStack() }
            )
        }
        composable(
            route = HomeDestination.Tasks.base,
        ) {
            TasksGraph(mainViewModel = mainViewModel)
        }

    }
}

private class RouterHome(val navController: NavController) {
    fun goBsStoreDetail(storeId: String) {
        navController.navigate("${HomeDestination.StoreDetail.base}/${storeId}")
    }

    fun goNotification() {
        navController.navigate(HomeDestination.Notification.base)
    }

    fun goQRScreen() {
        navController.navigate(HomeDestination.QRScreen.base)
    }
}*/

/*val screens = listOf(
    HomeDestination.PmStores,
    HomeDestination.Tasks,
    HomeDestination.BsStores,
    HomeDestination.Navigation,
    HomeDestination.Profile,
)*/
