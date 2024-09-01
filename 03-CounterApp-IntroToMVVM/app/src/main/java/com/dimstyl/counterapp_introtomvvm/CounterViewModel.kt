package com.dimstyl.counterapp_introtomvvm

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.lifecycle.ViewModel

class CounterViewModel : ViewModel() {

    private val repository: CounterRepository = CounterRepository()
    private var _count = mutableIntStateOf(repository.getCounter().count)

    // Expose the count as an immutable state
    val count: MutableState<Int> = _count

    fun increment() {
        repository.incrementCounter()
        _count.intValue = repository.getCounter().count
    }

    fun decrement() {
        repository.decrementCounter()
        _count.intValue = repository.getCounter().count
    }

}