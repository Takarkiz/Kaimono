//package com.khaki.kaimono.compose.uimodel
//
//import androidx.activity.compose.BackHandler
//import androidx.compose.material.ExperimentalMaterialApi
//import androidx.compose.material.ModalBottomSheetLayout
//import androidx.compose.material.ModalBottomSheetValue
//import androidx.compose.material.rememberModalBottomSheetState
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.rememberCoroutineScope
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.platform.ComposeView
//import kotlinx.coroutines.launch
//
//@OptIn(ExperimentalMaterialApi::class)
//@Composable
//fun BottomSheetWrapper(
//    modifier: Modifier = Modifier,
//    composeView: ComposeView,
//    content: @Composable () -> Unit,
//) {
//    val coroutineScope = rememberCoroutineScope()
//    val modalBottomSheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
//    var isSheetOpened by remember { mutableStateOf(false) }
//
//
//    ModalBottomSheetLayout(
//        sheetBackgroundColor = Color.Transparent,
//        sheetState = modalBottomSheetState,
//        sheetContent = {
//            content {
//                coroutineScope.launch {
//                    modalBottomSheetState.hide()
//                }
//            }
//        }
//    ) {}
//
//    BackHandler {
//        coroutineScope.launch {
//            modalBottomSheetState.hide()
//        }
//    }
//
//    LaunchedEffect(modalBottomSheetState.currentValue) {
//        when (modalBottomSheetState.currentValue) {
//            ModalBottomSheetValue.Hidden -> {
//                when {
//                    isSheetOpened -> parent.removeView(composeView)
//                    else -> {
//                        isSheetOpened = true
//                        modalBottomSheetState.show()
//                    }
//                }
//            }
//            else -> {}
//        }
//    }
//}