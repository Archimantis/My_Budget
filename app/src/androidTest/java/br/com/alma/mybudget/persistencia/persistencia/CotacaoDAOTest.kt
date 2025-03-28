package br.com.alma.mybudget.persistencia.persistencia

import br.com.alma.mybudget.modelo.Cotacao
import br.com.alma.mybudget.modelo.Moeda
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Test
import java.lang.Exception
import java.text.SimpleDateFormat

class CotacaoDAOTest : AppDatabaseTest() {

    @Test
    @Throws(Exception::class)
    fun insertCotacao() {
        val formataData = SimpleDateFormat("dd/MM/yyyy")
        val moeda: Moeda?
        val cotacao: Cotacao?
        val returningValue: Cotacao

        moedaDAO?.deleteAll()
        cotacaoDAO?.deleteAll()

        moeda = Moeda(1, "BRL", "Real brasileiro")
        cotacao = Cotacao(1, 1.00, formataData.parse("22/06/2022")!!, moeda.uid)

        moedaDAO?.insert(moeda)
        cotacaoDAO?.insert(cotacao)
        returningValue = cotacaoDAO?.getCotacao(cotacao.uid)!!

        assertEquals("22/06/2022", formataData.format(returningValue.data))
    }

    @Test
    @Throws(Exception::class)
    fun deleteCotacao() {
        val formataData = SimpleDateFormat("dd/MM/yyyy")
        val cotacao: Cotacao?

        moedaDAO?.deleteAll()
        cotacaoDAO?.deleteAll()

        val moeda = Moeda(1, "BRL", "Real brasileiro")
        cotacao = Cotacao(1, 1.00, formataData.parse("22/06/2022")!!, moeda.uid)

        // Com todos os atributos iguais
        moedaDAO?.insert(moeda)
        cotacaoDAO?.insert(cotacao)
        cotacaoDAO?.delete(cotacao)
        var returningValue: Cotacao? = cotacaoDAO?.getCotacao(cotacao.uid)
        Assert.assertNull(returningValue)

        // Com a mesma chave primária, mas com atributos alterados
        cotacaoDAO?.insert(cotacao)
        cotacao.valor = 2.00
        cotacaoDAO?.delete(cotacao)
        returningValue = cotacaoDAO?.getCotacao(cotacao.uid)
        Assert.assertNull(returningValue)
    }

    @Test
    @Throws(Exception::class)
    fun cotacoesDeUmaMoeda() {
        val formataData = SimpleDateFormat("dd/MM/yyyy")
        var cotacao: Cotacao

        moedaDAO?.deleteAll()
        cotacaoDAO?.deleteAll()

        val moeda = Moeda(1, "BRL", "Real brasileiro")
        moedaDAO?.insert(moeda)
        cotacao = Cotacao(1, 1.00, formataData.parse("10/06/2022")!!, moeda.uid)
        cotacaoDAO?.insert(cotacao)
        cotacao = Cotacao(2, 1.50, formataData.parse("15/06/2022")!!, moeda.uid)
        cotacaoDAO?.insert(cotacao)
        cotacao = Cotacao(3, 2.00, formataData.parse("20/06/2022")!!, moeda.uid)
        cotacaoDAO?.insert(cotacao)

        cotacao = cotacaoDAO?.findCotacaoAtualMoeda(moeda.uid)!!
        assertEquals(2.00, cotacao.valor, 0.0)

        val listaDeCotacoes: List<Cotacao?> = cotacaoDAO?.getCotacoesMoeda(moeda.uid) as List<Cotacao?>
        assertEquals(3, listaDeCotacoes.size.toLong())
    }
}
