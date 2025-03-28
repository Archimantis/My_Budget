package br.com.alma.mybudget.persistencia.persistencia

import br.com.alma.mybudget.modelo.ElementoOrcamentario
import br.com.alma.mybudget.modelo.Orcamento
import br.com.alma.mybudget.persistencia.ElementoOrcamentarioDAO
import br.com.alma.mybudget.persistencia.OrcamentoDAO
import org.junit.Assert
import org.junit.Test
import java.lang.Exception
import java.text.SimpleDateFormat

class OrcamentoDAOTest : AppDatabaseTest() {
    @Test
    @Throws(Exception::class)
    fun insertOrcamento() {
        val formataData = SimpleDateFormat("dd/MM/yyyy")
        val orcamento = Orcamento(1, "Normal", formataData.parse("22/06/2022")!!)

        val returningValue: Orcamento

        orcamentoDAO?.insert(orcamento)
        returningValue = orcamentoDAO?.getOrcamento(orcamento.uid)!!

        Assert.assertEquals("22/06/2022", formataData.format(returningValue.data))
    }

    @Test
    @Throws(Exception::class)
    fun deleteOrcamento() {
        val formataData = SimpleDateFormat("dd/MM/yyyy")
        var orcamento: Orcamento?

        var returningValue: Orcamento?

        // Com todos os atributos iguais
        orcamento = Orcamento(1, "Normal", formataData.parse("22/06/2022")!!)
        orcamentoDAO?.insert(orcamento)
        orcamentoDAO?.delete(orcamento)
        returningValue = orcamentoDAO?.getOrcamento(orcamento.uid)
        Assert.assertNull(returningValue)

        // Com a mesma chave primária, mas com atributos alterados
        orcamento = Orcamento(1, "Normal", formataData.parse("22/06/2022")!!)
        orcamentoDAO?.insert(orcamento)
        orcamento.descr = "Qualquer coisa"
        orcamentoDAO?.delete(orcamento)
        returningValue = orcamentoDAO?.getOrcamento(orcamento.uid)
        Assert.assertNull(returningValue)
    }

    @Test
    @Throws(Exception::class)
    fun recuperaListaDeOrcamentos() {
        val formataData = SimpleDateFormat("dd/MM/yyyy")
        val orcamentoDAO: OrcamentoDAO? = orcamentoDAO
        var orcamento = Orcamento(1, "Normal", formataData.parse("10/06/2022")!!)
        orcamentoDAO?.insert(orcamento)
        orcamento = Orcamento(2, "Extemporâneo", formataData.parse("15/06/2022")!!)
        orcamentoDAO?.insert(orcamento)
        orcamento = Orcamento(3, "Desesperado", formataData.parse("20/06/2022")!!)
        orcamentoDAO?.insert(orcamento)

        val listaDeCotacoes: List<Orcamento?>? = orcamentoDAO?.getAllOrcamentos()
        Assert.assertEquals(3, listaDeCotacoes?.size?.toLong())
    }

    @Throws(Exception::class)
    private fun carregaOrcamento() {
        val formataData = SimpleDateFormat("dd/MM/yyyy")
        val elementoOrcamentario: ElementoOrcamentario?

        val orcamentoDAO: OrcamentoDAO? = orcamentoDAO
        val elementoOrcamentarioDAO: ElementoOrcamentarioDAO? = elementoOrcamentarioDAO


        val orcamento = Orcamento(1, "Orcamento 1", formataData.parse("01/01/2022")!!)
        orcamentoDAO?.insert(orcamento)

        elementoOrcamentario = ElementoOrcamentario(1, 'C', orcamento.uid, 0)
        elementoOrcamentarioDAO?.insert(elementoOrcamentario)

        //  valor = new Valor(1, 0,)
    }
}
