package br.com.alma.mybudget.persistencia.persistencia

import br.com.alma.mybudget.modelo.ClasseDetalhe
import br.com.alma.mybudget.modelo.ClasseGeral
import br.com.alma.mybudget.modelo.Conta
import br.com.alma.mybudget.modelo.Cotacao
import br.com.alma.mybudget.modelo.ElementoOrcamentario
import br.com.alma.mybudget.modelo.Lancamento
import br.com.alma.mybudget.modelo.Moeda
import br.com.alma.mybudget.modelo.Orcamento
import br.com.alma.mybudget.modelo.Sujeito
import br.com.alma.mybudget.modelo.TipoConta
import br.com.alma.mybudget.modelo.Valor
import org.junit.Assert
import org.junit.Test
import java.text.SimpleDateFormat

class ValorDAOTest : AppDatabaseTest() {
    @Test
    @Throws(Exception::class)
    fun insertValor() {
        carregaValores()

        val valor = Valor(1, 1.00, 1, 1, 1, 0)
        valorDAO?.insert(valor)

        val returningValue: Valor? = valorDAO?.getValor(valor.uid)

        Assert.assertEquals(valor.uid, returningValue?.uid)
    }

    @Test
    @Throws(Exception::class)
    fun deleteValor() {

        carregaValores()

        // Com todos os atributos iguais
        val valor = Valor(1, 1.00, 1, 1, 1, 1)

        valorDAO?.insert(valor)
        valorDAO?.delete(valor)
        var returningValue: Valor? = valorDAO?.getValor(valor.uid)
        Assert.assertNull(returningValue)

        // Com todos os atributos iguais e dupla inserção
        valorDAO?.insert(valor)
        valorDAO?.delete(valor)
        returningValue = valorDAO?.getValor(valor.uid)
        Assert.assertNull(returningValue)

        // Com a mesma chave primária, mas com atributos alterados
        valorDAO?.insert(valor)
        valor.valor = 5.00 // Inexistente
        valor.moedaUID = 2
        valorDAO?.delete(valor)
        returningValue = valorDAO?.getValor(valor.uid)
        Assert.assertNull(returningValue)
    }

    @Test
    @Throws(Exception::class)
    fun valoresPorAgrupamento() {

        carregaValores()

        val valor: Valor = valorDAO?.getValor(1)!!
        Assert.assertEquals(1, valor.uid)

        var listaValores: List<Valor?>? = valorDAO?.getValoresDeUmLancamento(1)
        Assert.assertEquals(3, listaValores?.size?.toLong())

        listaValores = valorDAO?.getAllValores()
        Assert.assertEquals(8, listaValores?.size?.toLong())

        listaValores = valorDAO?.getValoresDeUmaMoeda(2)
        Assert.assertEquals(2, listaValores?.size?.toLong())

        listaValores = valorDAO?.getValoresDeUmLancamento(3)
        Assert.assertEquals(5, listaValores?.size?.toLong())

        listaValores = valorDAO?.getValoresDeUmClasseDetalhe(2)
        Assert.assertEquals(2, listaValores?.size?.toLong())
    }

    @Throws(Exception::class)
    private fun carregaValores() {
        val formataData = SimpleDateFormat("dd/MM/yyyy")

        moedaDAO?.deleteAll()
        var moeda = Moeda(1, "BRL", "Real brasileiro") // Real
        moedaDAO?.insert(moeda)
        moeda = Moeda(2, "USD", "Dólar americano") // Dólar
        moedaDAO?.insert(moeda)

        cotacaoDAO?.deleteAll()
        var cotacao = Cotacao(1, 1.00, formataData.parse("22/06/2022")!!, moeda.uid)
        cotacaoDAO?.insert(cotacao)
        cotacao = Cotacao(2, 5.24, formataData.parse("22/06/2022")!!, moeda.uid)
        cotacaoDAO?.insert(cotacao)

        tipoContaDAO?.deleteAll()
        var tipoConta = TipoConta(1, "Conta Bancária")
        tipoContaDAO?.insert(tipoConta)
        tipoConta = TipoConta(2, "Cartão de Crédito")
        tipoContaDAO?.insert(tipoConta)

        contaDAO?.deleteAll()
        var conta = Conta(1, "Conta bancária", "74115151", 1)
        contaDAO?.insert(conta)
        conta = Conta(2, "Bandeira do Cartão", "4256 6812 7235 4498", 2)
        contaDAO?.insert(conta)

        sujeitoDAO?.deleteAll()
        var sujeito = Sujeito(1, "Primeiro")
        sujeitoDAO?.insert(sujeito)
        sujeito = Sujeito(2, "Segundo")
        sujeitoDAO?.insert(sujeito)

        lancamentoDAO?.deleteAll()
        var lancamento = Lancamento(1, 1, 1, formataData.parse("15/06/2022")!!, 'C', "Primeiro lançamento da conta 1")
        lancamentoDAO?.insert(lancamento)
        lancamento = Lancamento(2, 1, 1, formataData.parse("20/06/2022")!!, 'D', "Segundo lançamento da conta 1")
        lancamentoDAO?.insert(lancamento)
        lancamento = Lancamento(3, 1, 1, formataData.parse("30/06/2022")!!, 'D', "Terceiro lançamento da conta 1")
        lancamentoDAO?.insert(lancamento)

        // Remove todas as classes orcamentárias
        classeGeralDAO?.deleteAll()

        // Carrega classes orcamentarias
        var classeGeral: ClasseGeral
        var classeDetalhe: ClasseDetalhe?
        var chaveClasseGeral = 1
        var chaveClasseDetalhe = 1

        // Alimentação
        classeGeral = ClasseGeral(chaveClasseGeral++, "Alimentação")
        classeGeralDAO?.insert(classeGeral)
        classeDetalhe = ClasseDetalhe(chaveClasseDetalhe++, "Restaurantes", classeGeral.uid)
        classeDetalheDAO?.insert(classeDetalhe)
        classeDetalhe = ClasseDetalhe(chaveClasseDetalhe++, "Ingredientes", classeGeral.uid)
        classeDetalheDAO?.insert(classeDetalhe)

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

        //Orcamento
        val orcamento = Orcamento(1, "Primeiro Orcamento", formataData.parse("01/01/2022")!!)
        orcamentoDAO?.insert(orcamento)

        //Elemento Orçametário
        var elementoOrcamentario = ElementoOrcamentario(1, 'C', 1, 1)
        elementoOrcamentarioDAO?.insert(elementoOrcamentario)
        elementoOrcamentario = ElementoOrcamentario(2, 'C', 1, 2)
        elementoOrcamentarioDAO?.insert(elementoOrcamentario)
        elementoOrcamentario = ElementoOrcamentario(3, 'D', 1, 3)
        elementoOrcamentarioDAO?.insert(elementoOrcamentario)
        valorDAO?.deleteAll()

        // Valores
        var valor = Valor(1, 1.00, 1, 1, 1, null)
        valorDAO?.insert(valor)
        valor = Valor(1, 2.00, 2, 2, 2, null)
        valorDAO?.insert(valor)
        valor = Valor(2, 2.00, 1, 1, 1, null)
        valorDAO?.insert(valor)
        valor = Valor(3, 3.00, 3, 1, 1, null)
        valorDAO?.insert(valor)
        valor = Valor(4, 3.00, 1, 2, 1, null)
        valorDAO?.insert(valor)
        valor = Valor(5, 4.00, 3, 2, 1, null)
        valorDAO?.insert(valor)
        valor = Valor(6, 5.00, 3, 1, 2, null)
        valorDAO?.insert(valor)
        valor = Valor(7, 6.00, 3, 1, 2, null)
        valorDAO?.insert(valor)
        valor = Valor(8, 1.23, 3, 1, null, 2)
        valorDAO?.insert(valor)
    }
}