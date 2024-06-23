package com.sap.testretrofit.presentation.screen.monitorUI/*
package com.sap.testretrofit.presentation.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import com.sap.testretrofit.data.remote.MessageProcessingLog
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.pulltorefresh.PullToRefreshState
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.sap.testretrofit.utils.FilterBuilder

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PullToRefreshLazyColumn(
//    modifier: Modifier = Modifier,
//    items: List<MessageProcessingLog>,
//    isRefreshing: MutableState<Boolean> = remember { mutableStateOf(false) },
//    state: PullToRefreshState = rememberPullToRefreshState(),
//    content: @Composable (T) -> Unit,
    filter: FilterBuilder,
    viewModel: TokenViewModel,
    top: Int,
) {
    val state: PullToRefreshState = rememberPullToRefreshState(
        onRefresh = viewModel.getMoni2(top, filter)
    )
 //   val onRefresh: () -> Unit = { isRefreshing.value = true }
    Box(
      //  modifier = modifier.pullToRefresh()
    ) {
        LazyColumn(Modifier.fillMaxSize())
        {
            if (!isRefreshing.value) {
                items(items) { item ->
                    CardInterfaceDisplay(state = item)
                }
            }
        }
        if (isRefreshing.value) {
            LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
        } else {
            LinearProgressIndicator(modifier = Modifier.fillMaxWidth(),
                progress = { state.distanceFraction })
        }

    }
}*/
