package br.com.alma.meustrocados.persistencia;

import org.junit.Assert;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.List;

import br.com.alma.meustrocados.modelo.Cotacao;
import br.com.alma.meustrocados.modelo.Moeda;

public class CotacaoDAOTest extends AppDatabaseTest {

    @Test
    public void insertCotacao() throws Exception {
        SimpleDateFormat formataData = new SimpleDateFormat("dd/MM/yyyy");
        Moeda moeda;
        Cotacao cotacao;
        Cotacao returningValue;

        getMoedaDAO().deleteAll();
        getCotacaoDAO().deleteAll();

        moeda = new Moeda(1, "BRL", "Real brasileiro");
        cotacao = new Cotacao(1, 1, formataData.parse("22/06/2022"), moeda.uid);

        getMoedaDAO().insert(moeda);
        getCotacaoDAO().insert(cotacao);
        returningValue = getCotacaoDAO().getCotacao(cotacao.uid);

        Assert.assertEquals("22/06/2022", formataData.format(returningValue.data));
    }

    @Test
    public void deleteCotacao() throws Exception {
        SimpleDateFormat formataData = new SimpleDateFormat("dd/MM/yyyy");
        Moeda moeda;
        Cotacao cotacao;
        Cotacao returningValue;

        getMoedaDAO().deleteAll();
        getCotacaoDAO().deleteAll();

        moeda = new Moeda(1, "BRL", "Real brasileiro");
        cotacao = new Cotacao(1, 1, formataData.parse("22/06/2022"), moeda.uid);

        // Com todos os atributos iguais
        getMoedaDAO().insert(moeda);
        getCotacaoDAO().insert(cotacao);
        getCotacaoDAO().delete(cotacao);
        returningValue = getCotacaoDAO().getCotacao(cotacao.uid);
        Assert.assertNull(returningValue);

        // Com a mesma chave primária, mas com atributos alterados
        getCotacaoDAO().insert(cotacao);
        cotacao.valor = 2.00;
        getCotacaoDAO().delete(cotacao);
        returningValue = getCotacaoDAO().getCotacao(cotacao.uid);
        Assert.assertNull(returningValue);
    }

    @Test
    public void cotacoesDeUmaMoeda() throws Exception {
        SimpleDateFormat formataData = new SimpleDateFormat("dd/MM/yyyy");
        Moeda moeda;
        Cotacao cotacao;
        Cotacao returningValue;

        getMoedaDAO().deleteAll();
        getCotacaoDAO().deleteAll();

        moeda = new Moeda(1, "BRL", "Real brasileiro");
        getMoedaDAO().insert(moeda);
        cotacao = new Cotacao(1, 1.00, formataData.parse("10/06/2022"), moeda.uid);
        getCotacaoDAO().insert(cotacao);
        cotacao = new Cotacao(2, 1.50, formataData.parse("15/06/2022"), moeda.uid);
        getCotacaoDAO().insert(cotacao);
        cotacao = new Cotacao(3, 2.00, formataData.parse("20/06/2022"), moeda.uid);
        getCotacaoDAO().insert(cotacao);

        cotacao = getCotacaoDAO().findCotacaoAtualMoeda(moeda.uid);
        Assert.assertEquals(2.00, cotacao.valor, 0.0);

        List<Cotacao> listaDeCotacoes = getCotacaoDAO().getCotacoesMoeda(moeda.uid);
        Assert.assertEquals(3, listaDeCotacoes.size());
    }
}
