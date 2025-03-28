package br.com.alma.mybudget.persistencia.persistencia

import br.com.alma.mybudget.modelo.TipoConta
import org.junit.Assert
import org.junit.Test
import java.lang.Exception

class TipoContaDAOTest : AppDatabaseTest() {
    @Test
    @Throws(Exception::class)
    fun insertTipoConta() {
        val tipoConta = TipoConta(1, "TipoConta")

        tipoContaDAO?.insert(tipoConta)
        val returningValue: TipoConta = tipoContaDAO?.getTipoConta(tipoConta.uid)!!

        Assert.assertEquals(tipoConta.uid, returningValue.uid)
    }

    @Test
    @Throws(Exception::class)
    fun deletetipoConta() {
        val tipoConta = TipoConta(1, "TipoConta")

        tipoContaDAO?.insert(tipoConta)
        tipoContaDAO?.delete(tipoConta)
        val returningValue: TipoConta? = tipoContaDAO?.getTipoConta(tipoConta.uid)

        Assert.assertNull(returningValue)
    }


    @Test
    @Throws(Exception::class)
    fun recuperaTodosOsTipoContas() {
        var tipoConta = TipoConta(1, "Banco")
        tipoContaDAO?.insert(tipoConta)
        tipoConta = TipoConta(2, "Caixa")
        tipoContaDAO?.insert(tipoConta)
        tipoConta = TipoConta(3, "Cartão")
        tipoContaDAO?.insert(tipoConta)

        val returningValue: List<TipoConta?>? = tipoContaDAO?.getAllTipoContas()
        Assert.assertEquals(3, returningValue?.size?.toLong() ?: -1)
    }
}
