package com.example.to_docompose.utils.coroutines

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

fun <T, R> StateFlow<T>.mapState(
    scope: CoroutineScope,
    sharingStarted: SharingStarted,
    transform: (T) -> R
): StateFlow<R> =
    map { o -> transform.invoke(o) }
        .stateIn(scope, sharingStarted, transform.invoke(value))

fun <T1, T2, R> combineState(
    flow1: StateFlow<T1>,
    flow2: StateFlow<T2>,
    scope: CoroutineScope,
    sharingStarted: SharingStarted,
    transform: (T1, T2) -> R
): StateFlow<R> =
    combine(flow1, flow2) { o1, o2 -> transform.invoke(o1, o2) }
        .stateIn(scope, sharingStarted, transform.invoke(flow1.value, flow2.value))

fun <T1, T2, T3, R> combineState(
    flow1: StateFlow<T1>,
    flow2: StateFlow<T2>,
    flow3: StateFlow<T3>,
    scope: CoroutineScope,
    sharingStarted: SharingStarted,
    transform: (T1, T2, T3) -> R
): StateFlow<R> =
    combine(flow1, flow2, flow3) { o1, o2, o3 -> transform.invoke(o1, o2, o3) }
        .stateIn(scope, sharingStarted, transform.invoke(flow1.value, flow2.value, flow3.value))

fun <T1, T2, T3, T4, R> combineState(
    flow1: StateFlow<T1>,
    flow2: StateFlow<T2>,
    flow3: StateFlow<T3>,
    flow4: StateFlow<T4>,
    scope: CoroutineScope,
    sharingStarted: SharingStarted,
    transform: (T1, T2, T3, T4) -> R
): StateFlow<R> =
    combine(flow1, flow2, flow3, flow4) { o1, o2, o3, o4 -> transform.invoke(o1, o2, o3, o4) }
        .stateIn(scope, sharingStarted, transform.invoke(flow1.value, flow2.value, flow3.value, flow4.value))

fun <T1, T2, T3, T4, T5, R> combineState(
    flow1: StateFlow<T1>,
    flow2: StateFlow<T2>,
    flow3: StateFlow<T3>,
    flow4: StateFlow<T4>,
    flow5: StateFlow<T5>,
    scope: CoroutineScope,
    sharingStarted: SharingStarted,
    transform: (T1, T2, T3, T4, T5) -> R
): StateFlow<R> =
    combine(flow1, flow2, flow3, flow4, flow5) { o1, o2, o3, o4, o5 -> transform.invoke(o1, o2, o3, o4, o5) }
        .stateIn(scope, sharingStarted, transform.invoke(flow1.value, flow2.value, flow3.value, flow4.value, flow5.value))

@Suppress("UNCHECKED_CAST")
fun <T1, T2, T3, T4, T5, T6, R> combineState(
    flow1: StateFlow<T1>,
    flow2: StateFlow<T2>,
    flow3: StateFlow<T3>,
    flow4: StateFlow<T4>,
    flow5: StateFlow<T5>,
    flow6: StateFlow<T6>,
    scope: CoroutineScope,
    sharingStarted: SharingStarted,
    transform: (T1, T2, T3, T4, T5, T6) -> R
): StateFlow<R> =
    combine(flow1, flow2, flow3, flow4, flow5, flow6) { valuesArray ->
        transform.invoke(
            valuesArray[0] as T1,
            valuesArray[1] as T2,
            valuesArray[2] as T3,
            valuesArray[3] as T4,
            valuesArray[4] as T5,
            valuesArray[5] as T6,
        )
    }.stateIn(scope, sharingStarted, transform.invoke(flow1.value, flow2.value, flow3.value, flow4.value, flow5.value, flow6.value))