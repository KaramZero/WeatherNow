package com.vodafone.core.utilities

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


fun ViewModel.launchIO(block: suspend () -> Unit) = viewModelScope.launch(Dispatchers.IO) {
    block()
}

@OptIn(DelicateCoroutinesApi::class)
fun ViewModel.launchGlobalIO(block: suspend () -> Unit) = GlobalScope.launch(Dispatchers.IO) {
    block()
}

fun ViewModel.launchMain(block: suspend () -> Unit) = viewModelScope.launch(Dispatchers.Main) {
    block()
}

fun ViewModel.launchDefault(block: suspend () -> Unit) =
    viewModelScope.launch(Dispatchers.Default) {
        block()
    }


suspend fun ViewModel.withMainContext(block: suspend () -> Unit) = withContext(Dispatchers.Main) {
    block()
}



