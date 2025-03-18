package br.com.alma.mybudget.persistencia

import br.com.alma.mybudget.modelo.Moeda
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class MoedaDAOTest : AppDatabaseTest() {
    @Test
    @Throws(Exception::class)
    fun insertMoeda() {
        val moeda = Moeda(1, "BRL", "Real brasileiro")

        moedaDAO!!.insert(moeda)
        val returningValue = moedaDAO!!.getMoeda(moeda.uid)

        Assertions.assertEquals(moeda.uid.toLong(), returningValue!!.uid.toLong())
    }

    @Test
    @Throws(Exception::class)
    fun deleteMoeda() {
        val moeda = Moeda(1, "BRL", "Real brasileiro")

        // Com todos os atributos iguais
        moedaDAO!!.insert(moeda)
        moedaDAO!!.delete(moeda)
        var returningValue = moedaDAO!!.getMoeda(moeda.uid)
        Assertions.assertNull(returningValue)

        // Com a mesma chave primária, mas com atributos alterados
        moedaDAO!!.insert(moeda)
        moeda.cod = "USD"
        moeda.descr = "Dólar americano"
        moedaDAO!!.delete(moeda)
        returningValue = moedaDAO!!.getMoeda(moeda.uid)
        Assertions.assertNull(returningValue)
    }

    @Test
    @Throws(Exception::class)
    fun localizacoesDeUmSujeito() {
        // Recupera localizações de um sujeito

        var moeda: Moeda? = Moeda(1, "BRL", "Real brasileiro")
        val moedaDAO = moedaDAO
        moedaDAO!!.insert(moeda)
        moeda!!.uid = 2
        moeda.cod = "USD"
        moeda.descr = "Dólar americano"
        moedaDAO.insert(moeda)
        moeda.uid = 3
        moeda.cod = "ARS"
        moeda.descr = "Peso argentino"
        moedaDAO.insert(moeda)

        moeda = moedaDAO.getMoeda(1)
        Assertions.assertEquals("BRL", moeda!!.cod)

        moeda = moedaDAO.findMoedasPorCodigo("ARS")
        Assertions.assertEquals(3, moeda!!.uid.toLong())
    }
}
