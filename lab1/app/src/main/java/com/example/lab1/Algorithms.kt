package com.example.lab1

import java.math.BigDecimal
import java.math.MathContext
import kotlin.math.cos
import kotlin.math.sin

sealed class Algorithm(val name: String) {
    class LinearAlgo : Algorithm("Linear Algorithm") {
        fun execute(c: BigDecimal, g: BigDecimal): BigDecimal {
            //we use big decimial because the number is hugely vast
            val up = c.add(BigDecimal(27).multiply(g))
            val down = BigDecimal(234).multiply(c).multiply(g)
            val exponent = g.add(BigDecimal(27).multiply(c))
            val y = up.pow(exponent.toInt(),MathContext.DECIMAL128).divide(down,MathContext.DECIMAL128)
            return y
        }
    }

    class ConditionAlgo : Algorithm("Conditional Algorithm") {
        fun execute(x: Double, z: Double): Double {
            // Check for division by zero in both branches
            if (x > 23) {
                if (z - 2 * x == 0.0) {
                    throw ArithmeticException("Division by zero: (z - 2 * x) equals zero")
                }
                return (x + 2 * z) / (z - 2 * x) + sin(x) / 21
            } else {
                if (1 + (x / 23) == 0.0) {
                    throw ArithmeticException("Division by zero: (1 + (x / 23)) equals zero")
                }
                return (1 + x) / (1 + (x / 23)) + cos(x) / 21
            }
        }
    }

    class CycleAlgo : Algorithm("Cycle Algorithm") {
        fun execute(): List<Int> {
            val y = mutableListOf<Int>()
            var curr = 1
            var prev: Int
            while (curr < 2000) {
                y.add(curr)
                prev = curr
                curr = 4 * prev
            }
            return y
        }
    }
}