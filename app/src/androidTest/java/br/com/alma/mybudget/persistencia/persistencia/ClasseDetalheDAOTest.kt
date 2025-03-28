package br.com.alma.mybudget.persistencia.persistencia

import br.com.alma.mybudget.modelo.ClasseDetalhe
import br.com.alma.mybudget.modelo.ClasseGeral
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class ClasseDetalheDAOTest : AppDatabaseTest() {

    @Test
    @Throws(Exception::class)
    fun insertClasseDetalhe() {
        val classeGeral = ClasseGeral(1, "ClasseGeral")
        classeGeralDAO?.insert(classeGeral)

        val classeDetalhe = ClasseDetalhe(1, "ClasseDetalhe", 1)
        classeDetalheDAO?.insert(classeDetalhe)

        val returningValue: ClasseDetalhe? = classeDetalheDAO?.getClasseDetalhe(classeDetalhe.uid)

        assertEquals(classeDetalhe.uid, returningValue?.uid)
    }

    @Test
    @Throws(Exception::class)
    fun deleteclasseDetalhe() {
        val classeGeral = ClasseGeral(1, "ClasseGeral")
        classeGeralDAO?.insert(classeGeral)
        val classeDetalhe = ClasseDetalhe(1, "ClasseDetalhe", 1)
        classeDetalheDAO?.insert(classeDetalhe)

        classeDetalheDAO?.insert(classeDetalhe)
        classeDetalheDAO?.delete(classeDetalhe)

        val returningValue: ClasseDetalhe? = classeDetalheDAO?.getClasseDetalhe(classeDetalhe.uid)

        assertNull(returningValue)
    }


    @Test
    @Throws(Exception::class)
    fun recuperaTodosAsClasseDetalhes() {
        classeGeralDAO?.deleteAll()
        carregaClasses()

        val returningValue: List<ClasseDetalhe?>? = classeDetalheDAO?.getAllClasseDetalhe()

        assertEquals(9, returningValue?.size?.toLong() ?: -1)
    }

    private fun carregaClasses() {
        // Remove todas as classes orcamentárias
        classeGeralDAO?.deleteAll()

        // Carrega classes orcamentarias
        var classeGeral: ClasseGeral?
        var classeDetalhe: ClasseDetalhe?
        var chaveClasseGeral = 1
        var chaveClasseDetalhe = 1

        // Alimentação
        classeGeral = ClasseGeral(chaveClasseGeral++, "Alimentação")
        classeGeralDAO?.insert(classeGeral)

        // Moradia
        classeGeral = ClasseGeral(chaveClasseGeral++, "Moradia")
        classeGeralDAO?.insert(classeGeral)
        classeDetalhe = ClasseDetalhe(chaveClasseDetalhe++, "Aluguel", classeGeral.uid)
        classeDetalheDAO?.insert(classeDetalhe)
        classeDetalhe = ClasseDetalhe(chaveClasseDetalhe++, "Serviços", classeGeral.uid)
        classeDetalheDAO?.insert(classeDetalhe)
        classeDetalhe = ClasseDetalhe(chaveClasseDetalhe++, "Água e Esgoto", classeGeral.uid)
        classeDetalheDAO?.insert(classeDetalhe)
        classeDetalhe = ClasseDetalhe(chaveClasseDetalhe++, "Energia Elétrica", classeGeral.uid)
        classeDetalheDAO?.insert(classeDetalhe)
        classeDetalhe = ClasseDetalhe(chaveClasseDetalhe++, "Móveis e Eletrodomésticos", classeGeral.uid)
        classeDetalheDAO?.insert(classeDetalhe)
        classeDetalhe = ClasseDetalhe(chaveClasseDetalhe++, "Outros", classeGeral.uid)
        classeDetalheDAO?.insert(classeDetalhe)

        // Educação
        classeGeral = ClasseGeral(chaveClasseGeral++, "Educação")
        classeGeralDAO?.insert(classeGeral)
        classeDetalhe = ClasseDetalhe(chaveClasseDetalhe++, "Mensalidade Escolar", classeGeral.uid)
        classeDetalheDAO?.insert(classeDetalhe)
        classeDetalhe = ClasseDetalhe(chaveClasseDetalhe++, "Inglês", classeGeral.uid)
        classeDetalheDAO?.insert(classeDetalhe)
        classeDetalhe = ClasseDetalhe(chaveClasseDetalhe++, "Uniformes", classeGeral.uid)
        classeDetalheDAO?.insert(classeDetalhe)
    }
}
