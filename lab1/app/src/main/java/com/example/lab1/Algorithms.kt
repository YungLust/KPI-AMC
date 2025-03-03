package com.example.lab1

import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin

fun linearAlgo(c: Int, g: Int): Double {

    var y = (c + 27 * g).toDouble()
    y.pow(g + 27 * c)
    y /= 234 * c * g

    return y

}

fun conditionAlgo(x: Int, z: Int): Double {
    return if (x > 23) {
        (x + 2 * z) / (z - 2 * x) + sin(x.toDouble()) / 21
    } else {
        (1 + x) / (1 + (x / 23)) + cos(x.toDouble()) / 21
    }

}

fun cycleAlgo(): MutableList<Int> {
    var y : MutableList<Int> = mutableListOf()
    var curr = 1
    var prev = 0
    var temp = 0
    while (curr < 2000) {
        y.add(curr)
        temp = curr
        prev = temp
        curr = 4 * prev
    }
    return y
}