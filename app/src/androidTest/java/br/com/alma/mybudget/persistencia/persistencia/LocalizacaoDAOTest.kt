package br.com.alma.mybudget.persistencia.persistencia

import br.com.alma.mybudget.modelo.Localizacao
import br.com.alma.mybudget.modelo.Sujeito
import br.com.alma.mybudget.persistencia.LocalizacaoDAO
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Test
import java.lang.Exception

class LocalizacaoDAOTest : AppDatabaseTest() {
    @Test
    @Throws(Exception::class)
    fun insertLocalizacao() {
        val localizacao: Localizacao?
        // Sujeito com localizações
        localizacaoDAO?.deleteAll()
        sujeitoDAO?.deleteAll()
        val sujeito = Sujeito(2, "Sujeito")
        sujeitoDAO?.insert(sujeito)

        localizacao = Localizacao(1, sujeito.uid, 0.00000, 0.00000)
        localizacaoDAO?.insert(localizacao)
        val returningValue: Localizacao = localizacaoDAO?.getLocalizacao(localizacao.uid)!!

        assertEquals(localizacao.uid, returningValue.uid)
    }

    @Test
    @Throws(Exception::class)
    fun deleteLocalizacao() {
        val localizacao: Localizacao?
        // Sujeito com localizações
        localizacaoDAO?.deleteAll()
        sujeitoDAO?.deleteAll()
        val sujeito = Sujeito(2, "Sujeito")
        sujeitoDAO?.insert(sujeito)

        // Com todos os atributos iguais
        localizacao = Localizacao(1, sujeito.uid, 0.00000, 0.00000)
        localizacaoDAO?.insert(localizacao)
        localizacaoDAO?.delete(localizacao)
        var returningValue: Localizacao? = localizacaoDAO?.getLocalizacao(localizacao.uid)
        Assert.assertNull(returningValue)

        // Com a mesma chave primária, mas com atributos alterados
        localizacaoDAO?.insert(localizacao)
        localizacao.sujeitoUID = 2
        localizacao.latitude = 90.0000
        localizacao.longitude = 90.0000
        localizacaoDAO?.delete(localizacao)
        returningValue = localizacaoDAO?.getLocalizacao(localizacao.uid)
        Assert.assertNull(returningValue)
    }

    @Test
    @Throws(Exception::class)
    fun localizacoesDeUmSujeito() {
        val localizacao: Localizacao?
        // Sujeito com localizações
        localizacaoDAO?.deleteAll()
        sujeitoDAO?.deleteAll()
        val sujeito = Sujeito(2, "Sujeito")
        sujeitoDAO?.insert(sujeito)

        // Recupera localizações de um sujeito
        localizacao = Localizacao(1, sujeito.uid, 0.00000, 0.00000)
        localizacaoDAO?.insert(localizacao)
        localizacao.uid = 2
        localizacao.latitude = 3.00
        localizacaoDAO?.insert(localizacao)
        localizacao.uid = 3
        localizacao.longitude = 3.00
        localizacaoDAO?.insert(localizacao)

        val nroLinhas: Int? = localizacaoDAO?.contaLinhas()
        val returningValue: Int = localizacaoDAO?.getLocalizacoesDeUmSujeito(sujeito.uid)?.size ?: -1

        assertEquals(nroLinhas?.toLong() ?: returningValue.toLong(), 0)
    }

    @Test
    @Throws(Exception::class)
    fun localizacaoProxima() {
        // Sujeito com localizações
        val sujeito = Sujeito(2, "Sujeito")
        sujeitoDAO?.insert(sujeito)

        // Recupera localizações de um sujeito
        val localizacao = Localizacao(1, sujeito.uid, 0.00000, 0.00000)
        val localizacaoDAO: LocalizacaoDAO? = localizacaoDAO
        localizacaoDAO?.insert(localizacao)
        // Ponto distante
        localizacao.uid = 2
        localizacao.latitude = 1.00000
        localizacao.longitude = 1.00000
        localizacaoDAO?.insert(localizacao)
        // Ponto próximo
        localizacao.uid = 3
        localizacao.latitude = 3.00000
        localizacao.longitude = 3.00003
        localizacaoDAO?.insert(localizacao)

        // Não acha localização próxima
        var localizacaoProxima: Localizacao? =
            localizacaoDAO?.getLocalizacao(localizacaoDAO.findSujeitoMaisProximo(15.00000, 15.0000))
        Assert.assertNull(localizacaoProxima)

        // Acha localização próxima
        val proximaUID: Int = localizacaoDAO?.findSujeitoMaisProximo(3.00000, 3.00006) ?: -1

        assertEquals(3, proximaUID.toLong())

        localizacaoProxima = localizacaoDAO?.getLocalizacao(proximaUID)
        assertEquals(3, localizacaoProxima?.uid)
    }
}
