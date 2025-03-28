package br.com.alma.mybudget

import org.junit.Assert.assertEquals
import org.junit.Test

class BaseTest {
    @Test
    fun soma() {
        val base = Base()
        val result = base.soma(2, 3)

        assertEquals(5, result)
    }

    @Test
    fun subtracao() {
        val base = Base()
        val result = base.subtracao(5, 3)

        assertEquals(2, result)
    }

}