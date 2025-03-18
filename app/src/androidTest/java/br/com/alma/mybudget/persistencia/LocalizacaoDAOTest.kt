package br.com.alma.mybudget.persistencia

import br.com.alma.mybudget.modelo.Localizacao
import br.com.alma.mybudget.modelo.Sujeito
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class LocalizacaoDAOTest : AppDatabaseTest() {
    @Test
    @Throws(Exception::class)
    fun insertLocalizacao() {
        val localizacao: Localizacao
        // Sujeito com localizações
        localizacaoDAO!!.deleteAll()
        sujeitoDAO!!.deleteAll()
        val sujeito = Sujeito(2, "Sujeito")
        sujeitoDAO!!.insert(sujeito)

        localizacao = Localizacao(1, sujeito.uid, 0.00000, 0.00000)
        localizacaoDAO!!.insert(localizacao)
        val returningValue = localizacaoDAO!!.getLocalizacao(localizacao.uid)

        Assertions.assertEquals(localizacao.uid.toLong(), returningValue!!.uid.toLong())
    }

    @Test
    @Throws(Exception::class)
    fun deleteLocalizacao() {
        val localizacao: Localizacao
        // Sujeito com localizações
        localizacaoDAO!!.deleteAll()
        sujeitoDAO!!.deleteAll()
        val sujeito = Sujeito(2, "Sujeito")
        sujeitoDAO!!.insert(sujeito)

        // Com todos os atributos iguais
        localizacao = Localizacao(1, sujeito.uid, 0.00000, 0.00000)
        localizacaoDAO!!.insert(localizacao)
        localizacaoDAO!!.delete(localizacao)
        var returningValue = localizacaoDAO!!.getLocalizacao(localizacao.uid)
        Assertions.assertNull(returningValue)

        // Com a mesma chave primária, mas com atributos alterados
        localizacaoDAO!!.insert(localizacao)
        localizacao.sujeitoUID = 2
        localizacao.latitude = 90.0
        localizacao.longitude = 90.0
        localizacaoDAO!!.delete(localizacao)
        returningValue = localizacaoDAO!!.getLocalizacao(localizacao.uid)
        Assertions.assertNull(returningValue)
    }

    @Test
    @Throws(Exception::class)
    fun localizacoesDeUmSujeito() {
        val localizacao: Localizacao
        // Sujeito com localizações
        localizacaoDAO!!.deleteAll()
        sujeitoDAO!!.deleteAll()
        val sujeito = Sujeito(2, "Sujeito")
        sujeitoDAO!!.insert(sujeito)

        // Recupera localizações de um sujeito
        localizacao = Localizacao(1, sujeito.uid, 0.00000, 0.00000)
        localizacaoDAO!!.insert(localizacao)
        localizacao.uid = 2
        localizacao.latitude = 3.00
        localizacaoDAO!!.insert(localizacao)
        localizacao.uid = 3
        localizacao.longitude = 3.00
        localizacaoDAO!!.insert(localizacao)

        val nroLinhas = localizacaoDAO!!.contaLinhas()
        val returningValue = localizacaoDAO!!.getLocalizacoesDeUmSujeito(sujeito.uid)!!.size

        Assertions.assertEquals(nroLinhas.toLong(), returningValue.toLong())
    }

    @Test
    @Throws(Exception::class)
    fun localizacaoProxima() {
        val localizacao: Localizacao
        // Sujeito com localizações
        val sujeito = Sujeito(2, "Sujeito")
        sujeitoDAO!!.insert(sujeito)

        // Recupera localizações de um sujeito
        localizacao = Localizacao(1, sujeito.uid, 0.00000, 0.00000)
        val localizacaoDAO = localizacaoDAO
        localizacaoDAO!!.insert(localizacao)
        // Ponto distante
        localizacao.uid = 2
        localizacao.latitude = 1.00000
        localizacao.longitude = 1.00000
        localizacaoDAO.insert(localizacao)
        // Ponto próximo
        localizacao.uid = 3
        localizacao.latitude = 3.00000
        localizacao.longitude = 3.00003
        localizacaoDAO.insert(localizacao)

        // Não acha localização próxima
        var localizacaoProxima = localizacaoDAO.getLocalizacao(localizacaoDAO.findSujeitoMaisProximo(15.00000, 15.0000))
        Assertions.assertNull(localizacaoProxima)

        // Acha localização próxima
        val proximaUID = localizacaoDAO.findSujeitoMaisProximo(3.00000, 3.00006)
        Assertions.assertEquals(3, proximaUID.toLong())

        localizacaoProxima = localizacaoDAO.getLocalizacao(proximaUID)
        Assertions.assertEquals(3, localizacaoProxima!!.uid.toLong())
    }
}
