package com.example.catalogoexpress

import com.example.catalogoexpress.data.remote.dto.ProductDto
import com.example.catalogoexpress.data.repository.toDomain
import com.example.catalogoexpress.domain.usecase.ScoreCalculator
import com.google.gson.JsonParser
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class ExampleUnitTest {
    @Test
    fun score_uses_minimum_price_of_one() {
        val score = ScoreCalculator.calculate(
            rating = 4.0,
            stock = 9,
            price = 0.0,
        )

        assertEquals((4.0 * kotlin.math.ln(10.0)) / 1.0, score, 0.0001)
    }

    @Test
    fun product_mapper_normalizes_empty_or_invalid_fields() {
        val product = ProductDto(
            id = JsonParser.parseString("\"\""),
            title = JsonParser.parseString("\"   \""),
            price = JsonParser.parseString("\"\""),
            description = JsonParser.parseString("null"),
            category = JsonParser.parseString("\"\""),
            brand = JsonParser.parseString("\"\""),
            type = JsonParser.parseString("\"\""),
            stock = JsonParser.parseString("\"not-a-number\""),
            image = JsonParser.parseString("\"\""),
            rating = JsonParser.parseString("\"\""),
            isNew = JsonParser.parseString("\"true\""),
        ).toDomain()

        assertEquals(0L, product.id)
        assertEquals("Sin titulo", product.title)
        assertEquals(0.0, product.price, 0.0)
        assertEquals(0, product.stock)
        assertEquals("Sin stock", product.status)
        assertEquals(0.0, product.score, 0.0)
        assertTrue(product.isNew)
    }
}