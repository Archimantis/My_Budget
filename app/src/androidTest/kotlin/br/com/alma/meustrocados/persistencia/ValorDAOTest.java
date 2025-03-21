package br.com.alma.meustrocados.persistencia;

import org.junit.Assert;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.List;

import br.com.alma.meustrocados.modelo.ClasseDetalhe;
import br.com.alma.meustrocados.modelo.ClasseGeral;
import br.com.alma.meustrocados.modelo.Conta;
import br.com.alma.meustrocados.modelo.Cotacao;
import br.com.alma.meustrocados.modelo.ElementoOrcamentario;
import br.com.alma.meustrocados.modelo.Lancamento;
import br.com.alma.meustrocados.modelo.Moeda;
import br.com.alma.meustrocados.modelo.Orcamento;
import br.com.alma.meustrocados.modelo.Sujeito;
import br.com.alma.meustrocados.modelo.TipoConta;
import br.com.alma.meustrocados.modelo.Valor;

public class ValorDAOTest extends AppDatabaseTest {

    @Test
    public void insertValor() throws Exception {
        carregaValores();

        Valor valor = new Valor(1, 1.00, 1, 1, 1, 0);
        getValorDAO().insert(valor);

        Valor returningValue = getValorDAO().getValor(valor.uid);

        Assert.assertEquals(valor.uid, returningValue.uid);
    }

    @Test
    public void deleteValor() throws Exception {
        Valor valor;

        carregaValores();

        // Com todos os atributos iguais
        valor = new Valor(1, 1.00, 1, 1, 1, 1);

        getValorDAO().insert(valor);
        getValorDAO().delete(valor);
        Valor returningValue = getValorDAO().getValor(valor.uid);
        Assert.assertNull(returningValue);

        // Com todos os atributos iguais e dupla inserção
        getValorDAO().insert(valor);
        getValorDAO().delete(valor);
        returningValue = getValorDAO().getValor(valor.uid);
        Assert.assertNull(returningValue);

        // Com a mesma chave primária, mas com atributos alterados
        getValorDAO().insert(valor);
        valor.valor = 5.00; // Inexistente
        valor.moedaUID = 2;
        getValorDAO().delete(valor);
        returningValue = getValorDAO().getValor(valor.uid);
        Assert.assertNull(returningValue);
    }

    @Test
    public void valoresPorAgrupamento() throws Exception {
        Valor valor;

        carregaValores();

        valor = getValorDAO().getValor(1);
        Assert.assertEquals(1, valor.uid);

        List<Valor> listaValores = getValorDAO().getValoresDeUmLancamento(1);
        Assert.assertEquals(3, listaValores.size());

        listaValores = getValorDAO().getAllValores();
        Assert.assertEquals(8, listaValores.size());

        listaValores = getValorDAO().getValoresDeUmaMoeda(2);
        Assert.assertEquals(2, listaValores.size());

        listaValores = getValorDAO().getValoresDeUmLancamento(3);
        Assert.assertEquals(5, listaValores.size());

        listaValores = getValorDAO().getValoresDeUmClasseDetalhe(2);
        Assert.assertEquals(2, listaValores.size());
    }

    private void carregaValores() throws Exception {
        SimpleDateFormat formataData = new SimpleDateFormat("dd/MM/yyyy");

        Moeda moeda;
        getMoedaDAO().deleteAll();
        moeda = new Moeda(1, "BRL", "Real brasileiro"); // Real
        getMoedaDAO().insert(moeda);
        moeda = new Moeda(2, "USD", "Dólar americano"); // Dólar
        getMoedaDAO().insert(moeda);

        Cotacao cotacao;
        getCotacaoDAO().deleteAll();
        cotacao = new Cotacao(1, 1.00, formataData.parse("22/06/2022"), moeda.uid);
        getCotacaoDAO().insert(cotacao);
        cotacao = new Cotacao(2, 5.24, formataData.parse("22/06/2022"), moeda.uid);
        getCotacaoDAO().insert(cotacao);

        TipoConta tipoConta;
        getTipoContaDAO().deleteAll();
        tipoConta = new TipoConta(1, "Conta Bancária");
        getTipoContaDAO().insert(tipoConta);
        tipoConta = new TipoConta(2, "Cartão de Crédito");
        getTipoContaDAO().insert(tipoConta);

        Conta conta;
        getContaDAO().deleteAll();
        conta = new Conta(1, "Conta bancária", "74115151", 1);
        getContaDAO().insert(conta);
        conta = new Conta(2, "Bandeira do Cartão", "4256 6812 7235 4498", 2);
        getContaDAO().insert(conta);

        Sujeito sujeito;
        getSujeitoDAO().deleteAll();
        sujeito = new Sujeito(1, "Primeiro");
        getSujeitoDAO().insert(sujeito);
        sujeito = new Sujeito(2, "Segundo");
        getSujeitoDAO().insert(sujeito);

        Lancamento lancamento;
        getLancamentoDAO().deleteAll();
        lancamento = new Lancamento(1, 1, 1, formataData.parse("15/06/2022"), 'C',
                "Primeiro lançamento da conta 1");
        getLancamentoDAO().insert(lancamento);
        lancamento = new Lancamento(2, 1, 1, formataData.parse("20/06/2022"), 'D',
                "Segundo lançamento da conta 1");
        getLancamentoDAO().insert(lancamento);
        lancamento = new Lancamento(3, 1, 1, formataData.parse("30/06/2022"), 'D',
                "Terceiro lançamento da conta 1");
        getLancamentoDAO().insert(lancamento);

        // Remove todas as classes orcamentárias
        getClasseGeralDAO().deleteAll();

        // Carrega classes orcamentarias
        ClasseGeral classeGeral;
        ClasseDetalhe classeDetalhe;
        int chaveClasseGeral = 1;
        int chaveClasseDetalhe = 1;

        // Alimentação
        classeGeral = new ClasseGeral(chaveClasseGeral++, "Alimentação");
        getClasseGeralDAO().insert(classeGeral);
        classeDetalhe = new ClasseDetalhe(chaveClasseDetalhe++, "Restaurantes", classeGeral.uid);
        getClasseDetalheDAO().insert(classeDetalhe);
        classeDetalhe = new ClasseDetalhe(chaveClasseDetalhe++, "Ingredientes", classeGeral.uid);
        getClasseDetalheDAO().insert(classeDetalhe);

        // Moradia
        classeGeral = new ClasseGeral(chaveClasseGeral++, "Moradia");
        getClasseGeralDAO().insert(classeGeral);
        classeDetalhe = new ClasseDetalhe(chaveClasseDetalhe++, "Aluguel", classeGeral.uid);
        getClasseDetalheDAO().insert(classeDetalhe);
        classeDetalhe = new ClasseDetalhe(chaveClasseDetalhe++, "Serviços", classeGeral.uid);
        getClasseDetalheDAO().insert(classeDetalhe);
        classeDetalhe = new ClasseDetalhe(chaveClasseDetalhe++, "Água e Esgoto", classeGeral.uid);
        getClasseDetalheDAO().insert(classeDetalhe);
        classeDetalhe = new ClasseDetalhe(chaveClasseDetalhe++, "Energia Elétrica", classeGeral.uid);
        getClasseDetalheDAO().insert(classeDetalhe);
        classeDetalhe = new ClasseDetalhe(chaveClasseDetalhe++, "Móveis e Eletrodomésticos", classeGeral.uid);
        getClasseDetalheDAO().insert(classeDetalhe);
        classeDetalhe = new ClasseDetalhe(chaveClasseDetalhe++, "Outros", classeGeral.uid);
        getClasseDetalheDAO().insert(classeDetalhe);

        //Orcamento
        Orcamento orcamento;
        orcamento = new Orcamento(1, "Primeiro Orcamento", formataData.parse("01/01/2022"));
        getOrcamentoDAO().insert(orcamento);

        //Elemento Orçametário
        ElementoOrcamentario elementoOrcamentario;
        elementoOrcamentario = new ElementoOrcamentario(1, 'C', 1, 1);
        getElementoOrcamentarioDAO().insert(elementoOrcamentario);
        elementoOrcamentario = new ElementoOrcamentario(2, 'C', 1, 2);
        getElementoOrcamentarioDAO().insert(elementoOrcamentario);
        elementoOrcamentario = new ElementoOrcamentario(3, 'D', 1, 3);
        getElementoOrcamentarioDAO().insert(elementoOrcamentario);

        // Valores
        Valor valor;
        getValorDAO().deleteAll();
        valor = new Valor(1, 1.00, 1, 1, 1, null);
        getValorDAO().insert(valor);
        valor = new Valor(1, 2.00, 2, 2, 2, null);
        getValorDAO().insert(valor);
        valor = new Valor(2, 2.00, 1, 1, 1, null);
        getValorDAO().insert(valor);
        valor = new Valor(3, 3.00, 3, 1, 1, null);
        getValorDAO().insert(valor);
        valor = new Valor(4, 3.00, 1, 2, 1, null);
        getValorDAO().insert(valor);
        valor = new Valor(5, 4.00, 3, 2, 1, null);
        getValorDAO().insert(valor);
        valor = new Valor(6, 5.00, 3, 1, 2, null);
        getValorDAO().insert(valor);
        valor = new Valor(7, 6.00, 3, 1, 2, null);
        getValorDAO().insert(valor);
        valor = new Valor(8, 1.23 , 3, 1, null, 2);
        getValorDAO().insert(valor);
    }
}