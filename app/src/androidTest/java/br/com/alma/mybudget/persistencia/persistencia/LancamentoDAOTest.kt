package br.com.alma.mybudget.persistencia.persistencia

import br.com.alma.mybudget.modelo.Conta
import br.com.alma.mybudget.modelo.Lancamento
import br.com.alma.mybudget.modelo.Sujeito
import br.com.alma.mybudget.modelo.TipoConta
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Test
import java.lang.Exception
import java.text.SimpleDateFormat

class LancamentoDAOTest : AppDatabaseTest() {

    @Test
    @Throws(Exception::class)
    fun insertLancamento() {
        val formataData = SimpleDateFormat("dd/MM/yyyy")
        val sujeito = Sujeito(1, "Primeiro")
        sujeitoDAO?.insert(sujeito)

        val tipoConta = TipoConta(1, "Conta bancária")
        tipoContaDAO?.insert(tipoConta)
        val conta = Conta(1, "Itaú CC", "Banco 341 Agência 3365 Conta 105.685-2", 1)
        contaDAO?.insert(conta)
        val lancamento = Lancamento(
            1, 1, 1, formataData.parse("15/06/2022")!!, 'C',
            "Comentário longo e, possivelmente, claro a respeito da despesa indicando o que foi comprado e, eventualmente, o motivo."
        )
        lancamentoDAO?.insert(lancamento)
        val returningValue: Lancamento = lancamentoDAO?.getLancamento(lancamento.uid)!!

        assertEquals(lancamento.uid, returningValue.uid)
    }

    @Test
    @Throws(Exception::class)
    fun deleteLancamento() {
        val formataData = SimpleDateFormat("dd/MM/yyyy")
        val sujeito = Sujeito(1, "Primeiro")
        sujeitoDAO?.insert(sujeito)
        val tipoConta = TipoConta(1, "Conta bancária")
        tipoContaDAO?.insert(tipoConta)
        val conta = Conta(1, "Itaú CC", "Banco 341 Agência 3365 Conta 105.685-2", 1)
        contaDAO?.insert(conta)
        val lancamento = Lancamento(
            1, 1, 1, formataData.parse("15/06/2022")!!, 'C',
            "Comentário longo e, possivelmente, claro a respeito da despesa indicando o que foi comprado e, eventualmente, o motivo."
        )
        lancamentoDAO?.insert(lancamento)

        // Com todos os atributos iguais
        lancamentoDAO?.insert(lancamento)
        lancamentoDAO?.delete(lancamento)
        var returningValue: Lancamento? = lancamentoDAO?.getLancamento(lancamento.uid)
        Assert.assertNull(returningValue)

        // Com a mesma chave primária, mas com atributos alterados
        lancamentoDAO?.insert(lancamento)
        lancamento.sujeitoUID = 222 // Inexistente
        lancamento.observacao = "Algo no lugar."
        lancamentoDAO?.delete(lancamento)
        returningValue = lancamentoDAO?.getLancamento(lancamento.uid)
        Assert.assertNull(returningValue)
    }

    @Test
    @Throws(Exception::class)
    fun lancamentosDeUmSujeito() {
        val formataData = SimpleDateFormat("dd/MM/yyyy")

        var sujeito = Sujeito(1, "Primeiro")
        sujeitoDAO?.insert(sujeito)
        sujeito = Sujeito(2, "Segundo")
        sujeitoDAO?.insert(sujeito)
        val tipoConta = TipoConta(1, "Conta bancária")
        tipoContaDAO?.insert(tipoConta)
        var conta = Conta(1, "Itaú CC", "Banco 341 Agência 3365 Conta 105.685-2", 1)
        contaDAO?.insert(conta)
        conta = Conta(2, "CEF CC", "Banco 104 Agência 3254 Conta 985.332-7", 1)
        contaDAO?.insert(conta)
        var lancamento = Lancamento(
            1, 1, 1, formataData.parse("15/06/2022")!!, 'C',
            "Primeiro lançamento da conta 1"
        )
        lancamentoDAO?.insert(lancamento)
        lancamento = Lancamento(
            2, 1, 1, formataData.parse("16/06/2022")!!, 'D',
            "Segundo lançamento da conta 1"
        )
        lancamentoDAO?.insert(lancamento)
        lancamento = Lancamento(
            3, 1, 1, formataData.parse("17/06/2022")!!, 'C',
            "Terceiro lançamento da conta 1"
        )
        lancamentoDAO?.insert(lancamento)
        lancamento = Lancamento(
            4, 2, 1, formataData.parse("21/06/2022")!!, 'C',
            "Primeiro lançamento para o segundo sujeito na conta 1 "
        )
        lancamentoDAO?.insert(lancamento)
        lancamento = Lancamento(
            5, 1, 2, formataData.parse("12/06/2022")!!, 'C',
            "Primeiro lançamento para a primeira pessoa na conta 2"
        )
        lancamentoDAO?.insert(lancamento)

        lancamento = lancamentoDAO?.getLancamento(1)!!
        assertEquals("15/06/2022", formataData.format(lancamento.data))

        var listLancamentos: List<Lancamento?> =
            lancamentoDAO?.getLancamentosDeUmSujeito(sujeitoUID = 1) as List<Lancamento?>
        assertEquals(4, listLancamentos.size.toLong())

        listLancamentos = lancamentoDAO?.getLancamentosDeUmaConta(2)!!
        assertEquals(1, listLancamentos.size.toLong())
    }
}

