package com.vodafone.core.utilities.networkHelper

import com.vodafone.core.domain.model.ViewState

inline fun <T : Any> safeApiCall(actionId: Int = -1, block: () -> T): ViewState<T> {
    return try {
        ViewState.Success(block(), actionId)
    } catch (e: Exception) {
        handleException(e, actionId)
    }
}
