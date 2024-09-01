package com.dimstyl.counterapp_introtomvvm

class CounterRepository {

    private var _counter = CounterModel(0)

    fun getCounter() = _counter

    fun incrementCounter() {
        _counter.count++
    }

    fun decrementCounter() {
        _counter.count--
    }

}