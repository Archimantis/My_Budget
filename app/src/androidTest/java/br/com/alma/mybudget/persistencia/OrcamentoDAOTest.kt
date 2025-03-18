package br.com.alma.mybudget.persistencia

import br.com.alma.mybudget.modelo.ElementoOrcamentario
import br.com.alma.mybudget.modelo.Orcamento
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.text.SimpleDateFormat

class OrcamentoDAOTest : AppDatabaseTest() {
    @Test
    @Throws(Exception::class)
    fun insertOrcamento() {
        val formataData = SimpleDateFormat("dd/MM/yyyy")
        val orcamento = formataData.parse("22/06/2022")?.let { Orcamento(1, "Normal", it) }

        orcamentoDAO!!.insert(orcamento)
        val returningValue = orcamento?.let { orcamentoDAO!!.getOrcamento(it.uid) }

        Assertions.assertEquals("22/06/2022", formataData.format(returningValue!!.data))
    }

    @Test
    @Throws(Exception::class)
    fun deleteOrcamento() {
        val formataData = SimpleDateFormat("dd/MM/yyyy")

        var returningValue: Orcamento?

        // Com todos os atributos iguais
        var orcamento = formataData.parse("22/06/2022")?.let { Orcamento(1, "Normal", it) }
        orcamentoDAO!!.insert(orcamento)
        orcamentoDAO!!.delete(orcamento)

        returningValue = null
        if (orcamento != null) {
            returningValue = orcamentoDAO!!.getOrcamento(orcamento.uid)
        }
        Assertions.assertNull(returningValue)

        // Com a mesma chave primária, mas com atributos alterados
        orcamento = formataData.parse("22/06/2022")?.let { Orcamento(1, "Normal", it) }
        orcamentoDAO!!.insert(orcamento)
        if (orcamento != null) {
            orcamento.descr = "Qualquer coisa"
        }
        orcamentoDAO!!.delete(orcamento)
        if (orcamento != null) {
            returningValue = orcamentoDAO!!.getOrcamento(orcamento.uid)
        }
        Assertions.assertNull(returningValue)
    }

    @Test
    @Throws(Exception::class)
    fun recuperaListaDeOrcamentos() {
        val formataData = SimpleDateFormat("dd/MM/yyyy")
        val orcamentoDAO = orcamentoDAO
        var orcamento = formataData.parse("10/06/2022")?.let { Orcamento(1, "Normal", it) }
        orcamentoDAO!!.insert(orcamento)
        orcamento = formataData.parse("15/06/2022")?.let { Orcamento(2, "Extemporâneo", it) }
        orcamentoDAO.insert(orcamento)
        orcamento = formataData.parse("20/06/2022")?.let { Orcamento(3, "Desesperado", it) }
        orcamentoDAO.insert(orcamento)

        val listaDeCotacoes: List<Orcamento?>? = orcamentoDAO.allOrcamentos
        Assertions.assertEquals(3, listaDeCotacoes!!.size.toLong())
    }

    @Throws(Exception::class)
    private fun carregaOrcamento() {
        val formataData = SimpleDateFormat("dd/MM/yyyy")
        var elementoOrcamentario: ElementoOrcamentario?

        val orcamentoDAO = orcamentoDAO
        val elementoOrcamentarioDAO = elementoOrcamentarioDAO

        val orcamento = formataData.parse("01/01/2022")?.let { Orcamento(1, "Orcamento 1", it) }
        orcamentoDAO!!.insert(orcamento)

        elementoOrcamentario = null
        if (orcamento != null) {
            elementoOrcamentario = ElementoOrcamentario(1, 'C', orcamento.uid, 0)
        }
        elementoOrcamentarioDAO!!.insert(elementoOrcamentario)

        //  valor = new Valor(1, 0,)
    }
}
