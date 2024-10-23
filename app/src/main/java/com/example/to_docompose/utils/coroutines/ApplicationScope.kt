package com.example.to_docompose.utils.coroutines

import kotlinx.coroutines.CoroutineScope
import kotlin.coroutines.CoroutineContext

class ApplicationScope(
    context: CoroutineContext
) : CoroutineScope by CoroutineScope(context)
