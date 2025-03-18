package br.com.alma.mybudget.persistencia

import br.com.alma.mybudget.modelo.Localizacao
import br.com.alma.mybudget.modelo.Sujeito
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test


class SujeitoDAOTest : AppDatabaseTest() {
    @Test
    @Throws(Exception::class)
    fun insertSujeito() {
        val sujeito = Sujeito(1, "Sujeito")

        sujeitoDAO!!.insert(sujeito)
        val returningValue = sujeitoDAO!!.getSujeito(sujeito.uid)

        Assertions.assertEquals(sujeito.uid.toLong(), returningValue!!.uid.toLong())
    }

    @Test
    @Throws(Exception::class)
    fun deletesujeito() {
        var returningValueSujeito: Sujeito?
        val returningValueListaLocalizacao: List<Localizacao?>?

        // Sujeito sem localizações
        var sujeito = Sujeito(1, "Sujeito")
        sujeitoDAO!!.insert(sujeito)
        sujeitoDAO!!.delete(sujeito)
        returningValueSujeito = sujeitoDAO!!.getSujeito(sujeito.uid)
        Assertions.assertNull(returningValueSujeito)

        // Sujeito com localizações
        sujeito = Sujeito(2, "Sujeito")
        sujeitoDAO!!.insert(sujeito)
        var localizacao = Localizacao(1, 2, 15.00, 48.00)
        localizacaoDAO!!.insert(localizacao)
        localizacao = Localizacao(2, 2, 15.01, 48.01)
        localizacaoDAO!!.insert(localizacao)

        sujeitoDAO!!.delete(sujeito)
        returningValueSujeito = sujeitoDAO!!.getSujeito(sujeito.uid)
        returningValueListaLocalizacao = localizacaoDAO!!.getLocalizacoesDeUmSujeito(sujeito.uid)
        Assertions.assertNull(returningValueSujeito)
        Assertions.assertEquals(0, returningValueListaLocalizacao!!.size.toLong())
    }
}
