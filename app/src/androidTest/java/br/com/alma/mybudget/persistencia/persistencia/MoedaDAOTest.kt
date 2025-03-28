package br.com.alma.mybudget.persistencia.persistencia

import br.com.alma.mybudget.modelo.Moeda
import br.com.alma.mybudget.persistencia.MoedaDAO
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test
import java.lang.Exception

class MoedaDAOTest : AppDatabaseTest() {
    @Test
    @Throws(Exception::class)
    fun insertMoeda() {
        val moeda = Moeda(1, "BRL", "Real brasileiro")

        moedaDAO?.insert(moeda)
        val returningValue: Moeda = moedaDAO?.getMoeda(moeda.uid)!!

        assertEquals(moeda.uid, returningValue.uid)
    }

    @Test
    @Throws(Exception::class)
    fun deleteMoeda() {
        val moeda = Moeda(1, "BRL", "Real brasileiro")

        // Com todos os atributos iguais
        moedaDAO?.insert(moeda)
        moedaDAO?.delete(moeda)
        var returningValue: Moeda? = moedaDAO?.getMoeda(moeda.uid)
        assertNull(returningValue)

        // Com a mesma chave primária, mas com atributos alterados
        moedaDAO?.insert(moeda)
        moeda.cod = "USD"
        moeda.descr = "Dólar americano"
        moedaDAO?.delete(moeda)
        returningValue = moedaDAO?.getMoeda(moeda.uid)
        assertNull(returningValue)
    }

    @Test
    @Throws(Exception::class)
    fun localizacoesDeUmSujeito() {
        // Recupera localizações de um sujeito

        var moeda = Moeda(1, "BRL", "Real brasileiro")
        val moedaDAO: MoedaDAO? = moedaDAO
        moedaDAO?.insert(moeda)
        moeda.uid = 2
        moeda.cod = "USD"
        moeda.descr = "Dólar americano"
        moedaDAO?.insert(moeda)
        moeda.uid = 3
        moeda.cod = "ARS"
        moeda.descr = "Peso argentino"
        moedaDAO?.insert(moeda)

        moeda = moedaDAO?.getMoeda(1)!!
        assertEquals("BRL", moeda.cod)

        moeda = moedaDAO.findMoedasPorCodigo("ARS")!!
        assertEquals(3, moeda.uid)
    }
}
