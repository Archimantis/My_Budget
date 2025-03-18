package br.com.alma.mybudget.persistencia

import br.com.alma.mybudget.modelo.ClasseDetalhe
import br.com.alma.mybudget.modelo.ClasseGeral
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class ClasseGeralDAOTest : AppDatabaseTest() {
    @Test
    @Throws(Exception::class)
    fun insertClasseGeral() {
        val classeGeral = ClasseGeral(1, "ClasseGeral")
        classeGeralDAO.insert(classeGeral)

        val returningValue = classeGeralDAO.getClasseGeral(classeGeral.uid)
        Assertions.assertEquals(classeGeral.uid.toLong(), returningValue!!.uid.toLong())
    }

    @Test
    @Throws(Exception::class)
    fun deleteclasseGeral() {
        var classeDetalhe: ClasseDetalhe
        var returningValueGeral: ClasseGeral?
        var returningValueDetalhe: ClasseDetalhe
        var returningValueListaDetalhe: List<ClasseDetalhe?>?

        // Exclusão simples de uma classe geral criada anteriormente, sem segunda inserção e sem classesDetalhe associadas.
        var classeGeral = ClasseGeral(1, "Descrição ignorada e jamais utilizada")
        returningValueGeral = classeGeralDAO.getClasseGeral(classeGeral.uid)
        returningValueListaDetalhe = classeDetalheDAO.getClassesDetalheDeUmaClasseGeral(classeGeral.uid)
        classeGeralDAO.delete(classeGeral)
        returningValueGeral = classeGeralDAO.getClasseGeral(classeGeral.uid)
        returningValueListaDetalhe = classeDetalheDAO.getClassesDetalheDeUmaClasseGeral(classeGeral.uid)
        Assertions.assertNull(returningValueGeral)
        Assertions.assertEquals(0, returningValueListaDetalhe!!.size.toLong())

        // Exclusão em cascata sem segunda inserção da ClasseGeral e com classesDetalhe associadas.
        classeGeral = ClasseGeral(2, "Tentativa de inclusão de nova descrição e que deve falhar")
        returningValueGeral = classeGeralDAO.getClasseGeral(classeGeral.uid)
        returningValueListaDetalhe = classeDetalheDAO.getClassesDetalheDeUmaClasseGeral(classeGeral.uid)
        classeGeralDAO.delete(classeGeral)
        returningValueGeral = classeGeralDAO.getClasseGeral(classeGeral.uid)
        returningValueListaDetalhe = classeDetalheDAO.getClassesDetalheDeUmaClasseGeral(classeGeral.uid)
        Assertions.assertNull(returningValueGeral)
        Assertions.assertEquals(0, returningValueListaDetalhe!!.size.toLong())

        // Exclusão em cascata com segunda inserção da ClasseGeral e com classesDetalhe associadas.
        classeGeral = ClasseGeral(3, "Teste")
        returningValueGeral = classeGeralDAO.getClasseGeral(classeGeral.uid)
        returningValueListaDetalhe = classeDetalheDAO.getClassesDetalheDeUmaClasseGeral(classeGeral.uid)
        classeGeralDAO.insert(classeGeral)
        returningValueGeral = classeGeralDAO.getClasseGeral(classeGeral.uid)
        returningValueListaDetalhe = classeDetalheDAO.getClassesDetalheDeUmaClasseGeral(classeGeral.uid)
        classeGeralDAO.delete(classeGeral)
        returningValueGeral = classeGeralDAO.getClasseGeral(classeGeral.uid)
        returningValueListaDetalhe = classeDetalheDAO.getClassesDetalheDeUmaClasseGeral(classeGeral.uid)
        Assertions.assertNull(returningValueGeral)
        Assertions.assertEquals(0, returningValueListaDetalhe!!.size.toLong())
    }

    @org.junit.jupiter.api.Test
    @Throws(Exception::class)
    fun testaCarga() {
        classeGeralDAO.deleteAll()
        carregaClasses()

        val returningValue = classeGeralDAO.getClasseGeral(0)
        Assertions.assertEquals("Alimentação", returningValue?.descr)
    }

    @Test
    @Throws(Exception::class)
    fun recuperaTodosOsClasseGerals() {
        classeDetalheDAO.deleteAll(carregaClasses())
        carregaClasses()

        val returningValue: List<Any?>? = classeGeralDAO.allClassesGerais
        Assertions.assertEquals(3, returningValue!!.size.toLong())
    }

    private fun carregaClasses() {
        val classeGeralDAO = classeGeralDAO
        val classeDetalheDAO = classeDetalheDAO

        // Remove todas as classes orcamentárias
        classeGeralDAO.deleteAll()

        // Carrega classes orcamentarias
        var classeGeral: ClasseGeral
        var classeDetalhe: ClasseDetalhe
        var chaveClasseGeral = 1
        var chaveClasseDetalhe = 1

        // Alimentação
        classeGeral = ClasseGeral(chaveClasseGeral++, "Alimentação")
        classeGeralDAO.insert(classeGeral)

        // Moradia
        classeGeral = ClasseGeral(chaveClasseGeral++, "Moradia")
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
        classeDetalhe = ClasseDetalhe(chaveClasseDetalhe++, "Outros", classeGeral.uid)
        classeDetalheDAO.insert(classeDetalhe)

        // Educação
        classeGeral = ClasseGeral(chaveClasseGeral, "Educação")
        classeGeralDAO.insert(classeGeral)
        classeDetalhe = ClasseDetalhe(chaveClasseDetalhe++, "Mensalidade Escolar", classeGeral.uid)
        classeDetalheDAO.insert(classeDetalhe)
        classeDetalhe = ClasseDetalhe(chaveClasseDetalhe++, "Inglês", classeGeral.uid)
        classeDetalheDAO.insert(classeDetalhe)
        classeDetalhe = ClasseDetalhe(chaveClasseDetalhe, "Uniformes", classeGeral.uid)
        classeDetalheDAO.insert(classeDetalhe)
    }
}
