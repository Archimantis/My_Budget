package br.com.alma.mybudget.persistencia

import br.com.alma.mybudget.modelo.TipoConta
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class TipoContaDAOTest : AppDatabaseTest() {
    @Test
    @Throws(Exception::class)
    fun insertTipoConta() {
        val tipoConta = TipoConta(1, "TipoConta")

        tipoContaDAO!!.insert(tipoConta)
        val returningValue = tipoContaDAO!!.getTipoConta(tipoConta.uid)

        Assertions.assertEquals(tipoConta.uid.toLong(), returningValue!!.uid.toLong())
    }

    @org.junit.jupiter.api.Test
    @Throws(Exception::class)
    fun deletetipoConta() {
        val tipoConta = TipoConta(1, "TipoConta")

        tipoContaDAO!!.insert(tipoConta)
        tipoContaDAO!!.delete(tipoConta)
        val returningValue = tipoContaDAO!!.getTipoConta(tipoConta.uid)

        Assertions.assertNull(returningValue)
    }


    @Test
    @Throws(Exception::class)
    fun recuperaTodosOsTipoContas() {
        var tipoConta = TipoConta(1, "Banco")
        tipoContaDAO!!.insert(tipoConta)
        tipoConta = TipoConta(2, "Caixa")
        tipoContaDAO!!.insert(tipoConta)
        tipoConta = TipoConta(3, "Cartão")
        tipoContaDAO!!.insert(tipoConta)

        val returningValue: List<TipoConta?>? = tipoContaDAO!!.allTipoContas

        Assertions.assertEquals(3, returningValue!!.size.toLong())
    }
}
