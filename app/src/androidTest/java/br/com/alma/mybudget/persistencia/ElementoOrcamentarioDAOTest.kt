package br.com.alma.mybudget.persistencia

import br.com.alma.mybudget.modelo.ClasseDetalhe
import br.com.alma.mybudget.modelo.ClasseGeral
import br.com.alma.mybudget.modelo.ElementoOrcamentario
import br.com.alma.mybudget.modelo.Orcamento
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.text.SimpleDateFormat

class ElementoOrcamentarioDAOTest : AppDatabaseTest() {
    @Test
    @Throws(Exception::class)
    fun insertElementoOrcamentario() {
        carregaElementosOrcamentarios()

        val elementoOrcamentario = ElementoOrcamentario(77, 'C', 1, 1)
        elementoOrcamentarioDAO!!.insert(elementoOrcamentario)

        val returningValue = elementoOrcamentarioDAO!!.getElementoOrcamentario(elementoOrcamentario.uid)
        Assertions.assertEquals(elementoOrcamentario.uid.toLong(), returningValue!!.uid.toLong())
    }

    @Test
    @Throws(Exception::class)
    fun deleteElementoOrcamentario() {
        carregaElementosOrcamentarios()

        val elementoOrcamentario = ElementoOrcamentario(1, 'C', 1, 1)
        elementoOrcamentarioDAO!!.insert(elementoOrcamentario)

        // Com todos os atributos iguais
        elementoOrcamentarioDAO!!.insert(elementoOrcamentario)
        elementoOrcamentarioDAO!!.delete(elementoOrcamentario)
        var returningValue =
            elementoOrcamentarioDAO!!.getElementoOrcamentario(elementoOrcamentario.uid)
        Assertions.assertNull(returningValue)

        // Com a mesma chave primária, mas com atributos alterados
        elementoOrcamentarioDAO!!.insert(elementoOrcamentario)
        elementoOrcamentario.tipo = 'D'
        elementoOrcamentarioDAO!!.delete(elementoOrcamentario)
        returningValue = elementoOrcamentarioDAO!!.getElementoOrcamentario(elementoOrcamentario.uid)
        Assertions.assertNull(returningValue)
    }

    @Test
    @Throws(Exception::class)
    fun elementosOrcamentariosDeUmOrcamento() {
        carregaElementosOrcamentarios()

        val returningValue = elementoOrcamentarioDAO!!.getElementoOrcamentario(1)
        Assertions.assertEquals(1, returningValue!!.uid.toLong())

        val listaDeCotacoes: List<ElementoOrcamentario?>? = elementoOrcamentarioDAO!!.allElementosOrcamentarios
        Assertions.assertEquals(3, listaDeCotacoes!!.size.toLong())
    }

    @Throws(Exception::class)
    private fun carregaElementosOrcamentarios() {
        val formataData = SimpleDateFormat("dd/MM/yyyy")
        val classeGeral: ClasseGeral
        var classeDetalhe: ClasseDetalhe

        val classeGeralDAO = classeGeralDAO
        val classeDetalheDAO = classeDetalheDAO

        // Remove todas as classes orcamentárias
        classeGeralDAO.deleteAll()

        // Carrega classes orcamentarias
        val chaveClasseGeral = 1
        var chaveClasseDetalhe = 1

        // Moradia
        classeGeral = ClasseGeral(chaveClasseGeral, "Moradia")
        classeGeralDAO.insert(classeGeral)
        classeDetalhe = ClasseDetalhe(chaveClasseDetalhe++, "Aluguel", classeGeral.uid)
        classeDetalheDAO.insert(classeDetalhe)
        classeDetalhe = ClasseDetalhe(chaveClasseDetalhe++, "Serviços", classeGeral.uid)
        classeDetalheDAO.insert(classeDetalhe)
        classeDetalhe = ClasseDetalhe(chaveClasseDetalhe++, "Água e Esgoto", classeGeral.uid)
        classeDetalheDAO.insert(classeDetalhe)
        classeDetalhe = ClasseDetalhe(chaveClasseDetalhe++, "Energia Elétrica", classeGeral.uid)
        classeDetalheDAO.insert(classeDetalhe)
        classeDetalhe = ClasseDetalhe(chaveClasseDetalhe++, "Móveis e Eletrodomésticos", classeGeral.uid)
        classeDetalheDAO.insert(classeDetalhe)
        classeDetalhe = ClasseDetalhe(chaveClasseDetalhe, "Outros", classeGeral.uid)
        classeDetalheDAO.insert(classeDetalhe)

        //Orcamento
        val orcamento = formataData.parse("01/01/2022")?.let { Orcamento(1, "Orcamento 1", it) }
        orcamentoDAO!!.insert(orcamento)

        var elementoOrcamentario = ElementoOrcamentario(1, 'C', 1, 1)
        elementoOrcamentarioDAO!!.insert(elementoOrcamentario)
        elementoOrcamentario = ElementoOrcamentario(1, 'C', 1, 2)
        elementoOrcamentarioDAO!!.insert(elementoOrcamentario)
        elementoOrcamentario = ElementoOrcamentario(2, 'C', 1, 3)
        elementoOrcamentarioDAO!!.insert(elementoOrcamentario)
        elementoOrcamentario = ElementoOrcamentario(3, 'D', 1, 4)
        elementoOrcamentarioDAO!!.insert(elementoOrcamentario)
    }
}
