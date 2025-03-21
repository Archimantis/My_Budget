package br.com.alma.meustrocados.persistencia;

import org.junit.Assert;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.List;

import br.com.alma.meustrocados.modelo.Conta;
import br.com.alma.meustrocados.modelo.Lancamento;
import br.com.alma.meustrocados.modelo.Sujeito;
import br.com.alma.meustrocados.modelo.TipoConta;

public class LancamentoDAOTest extends AppDatabaseTest {

    @Test
    public void insertLancamento() throws Exception {
        SimpleDateFormat formataData = new SimpleDateFormat("dd/MM/yyyy");
        Sujeito sujeito = new Sujeito(1, "Primeiro");
        getSujeitoDAO().insert(sujeito);
        TipoConta tipoConta = new TipoConta(1, "Conta bancária");
        getTipoContaDAO().insert(tipoConta);
        Conta conta = new Conta(1, "Itaú CC", "Banco 341 Agência 3365 Conta 105.685-2", 1);
        getContaDAO().insert(conta);
        Lancamento lancamento = new Lancamento(1, 1, 1, formataData.parse("15/06/2022"), 'C',
                "Comentário longo e, possivelmente, claro a respeito da despesa indicando o que foi comprado e, eventualmente, o motivo.");
        getLancamentoDAO().insert(lancamento);

        Lancamento returningValue;
        returningValue = getLancamentoDAO().getLancamento(lancamento.uid);

        Assert.assertEquals(lancamento.uid, returningValue.uid);
    }

    @Test
    public void deleteLancamento() throws Exception {
        SimpleDateFormat formataData = new SimpleDateFormat("dd/MM/yyyy");
        Sujeito sujeito = new Sujeito(1, "Primeiro");
        getSujeitoDAO().insert(sujeito);
        TipoConta tipoConta = new TipoConta(1, "Conta bancária");
        getTipoContaDAO().insert(tipoConta);
        Conta conta = new Conta(1, "Itaú CC", "Banco 341 Agência 3365 Conta 105.685-2", 1);
        getContaDAO().insert(conta);
        Lancamento lancamento = new Lancamento(1, 1, 1, formataData.parse("15/06/2022"), 'C',
                "Comentário longo e, possivelmente, claro a respeito da despesa indicando o que foi comprado e, eventualmente, o motivo.");
        getLancamentoDAO().insert(lancamento);

        // Com todos os atributos iguais
        getLancamentoDAO().insert(lancamento);
        getLancamentoDAO().delete(lancamento);
        Lancamento returningValue = getLancamentoDAO().getLancamento(lancamento.uid);
        Assert.assertNull(returningValue);

        // Com a mesma chave primária, mas com atributos alterados
        getLancamentoDAO().insert(lancamento);
        lancamento.sujeitoUID = 222; // Inexistente
        lancamento.observacao = "Algo no lugar.";
        getLancamentoDAO().delete(lancamento);
        returningValue = getLancamentoDAO().getLancamento(lancamento.uid);
        Assert.assertNull(returningValue);
    }

    @Test
    public void lancamentosDeUmSujeito() throws Exception {
        SimpleDateFormat formataData = new SimpleDateFormat("dd/MM/yyyy");

        Sujeito sujeito = new Sujeito(1, "Primeiro");
        getSujeitoDAO().insert(sujeito);
        sujeito = new Sujeito(2, "Segundo");
        getSujeitoDAO().insert(sujeito);
        TipoConta tipoConta = new TipoConta(1, "Conta bancária");
        getTipoContaDAO().insert(tipoConta);
        Conta conta = new Conta(1, "Itaú CC", "Banco 341 Agência 3365 Conta 105.685-2", 1);
        getContaDAO().insert(conta);
        conta = new Conta(2, "CEF CC", "Banco 104 Agência 3254 Conta 985.332-7", 1);
        getContaDAO().insert(conta);
        Lancamento lancamento = new Lancamento(1, 1, 1, formataData.parse("15/06/2022"), 'C',
                "Primeiro lançamento da conta 1");
        getLancamentoDAO().insert(lancamento);
        lancamento = new Lancamento(2, 1, 1, formataData.parse("16/06/2022"), 'D',
                "Segundo lançamento da conta 1");
        getLancamentoDAO().insert(lancamento);
        lancamento = new Lancamento(3, 1, 1, formataData.parse("17/06/2022"), 'C',
                "Terceiro lançamento da conta 1");
        getLancamentoDAO().insert(lancamento);
        lancamento = new Lancamento(4, 2, 1, formataData.parse("21/06/2022"), 'C',
                "Primeiro lançamento para o segundo sujeito na conta 1 ");
        getLancamentoDAO().insert(lancamento);
        lancamento = new Lancamento(5, 1, 2, formataData.parse("12/06/2022"), 'C',
                "Primeiro lançamento para a primeira pessoa na conta 2");
        getLancamentoDAO().insert(lancamento);

        lancamento = getLancamentoDAO().getLancamento(1);
        Assert.assertEquals("15/06/2022", formataData.format(lancamento.data));

        List<Lancamento> listLancamentos = getLancamentoDAO().getLancamentosDeUmSujeito(1);
        Assert.assertEquals(4, listLancamentos.size());

        listLancamentos = getLancamentoDAO().getLancamentosDeUmaConta(2);
        Assert.assertEquals(1, listLancamentos.size());
    }
}

