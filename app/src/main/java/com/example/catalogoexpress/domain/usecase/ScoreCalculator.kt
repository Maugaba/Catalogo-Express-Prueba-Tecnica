package com.example.catalogoexpress.domain.usecase

import kotlin.math.ln
import kotlin.math.max

object ScoreCalculator {
    fun calculate(rating: Double, stock: Int, price: Double): Double {
        return (rating * ln(stock + 1.0)) / max(price, 1.0)
    }
}
