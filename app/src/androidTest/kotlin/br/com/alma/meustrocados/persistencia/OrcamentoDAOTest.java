package br.com.alma.meustrocados.persistencia;

import org.junit.Assert;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.List;

import br.com.alma.meustrocados.modelo.ElementoOrcamentario;
import br.com.alma.meustrocados.modelo.Orcamento;
import br.com.alma.meustrocados.modelo.Valor;

public class OrcamentoDAOTest extends AppDatabaseTest {

    @Test
    public void insertOrcamento() throws Exception {
        SimpleDateFormat formataData = new SimpleDateFormat("dd/MM/yyyy");
        Orcamento orcamento = new Orcamento(1, "Normal", formataData.parse("22/06/2022"));

        Orcamento returningValue;

        getOrcamentoDAO().insert(orcamento);
        returningValue = getOrcamentoDAO().getOrcamento(orcamento.uid);

        Assert.assertEquals("22/06/2022", formataData.format(returningValue.data));
    }

    @Test
    public void deleteOrcamento() throws Exception {
        SimpleDateFormat formataData = new SimpleDateFormat("dd/MM/yyyy");
        Orcamento orcamento;

        Orcamento returningValue;

        // Com todos os atributos iguais
        orcamento = new Orcamento(1, "Normal", formataData.parse("22/06/2022"));
        getOrcamentoDAO().insert(orcamento);
        getOrcamentoDAO().delete(orcamento);
        returningValue = getOrcamentoDAO().getOrcamento(orcamento.uid);
        Assert.assertNull(returningValue);

        // Com a mesma chave primária, mas com atributos alterados
        orcamento = new Orcamento(1, "Normal", formataData.parse("22/06/2022"));
        getOrcamentoDAO().insert(orcamento);
        orcamento.descr = "Qualquer coisa";
        getOrcamentoDAO().delete(orcamento);
        returningValue = getOrcamentoDAO().getOrcamento(orcamento.uid);
        Assert.assertNull(returningValue);
    }

    @Test
    public void recuperaListaDeOrcamentos() throws Exception {
        SimpleDateFormat formataData = new SimpleDateFormat("dd/MM/yyyy");
        OrcamentoDAO orcamentoDAO = getOrcamentoDAO();
        Orcamento orcamento = new Orcamento(1, "Normal", formataData.parse("10/06/2022"));
        orcamentoDAO.insert(orcamento);
        orcamento = new Orcamento(2, "Extemporâneo", formataData.parse("15/06/2022"));
        orcamentoDAO.insert(orcamento);
        orcamento = new Orcamento(3, "Desesperado", formataData.parse("20/06/2022"));
        orcamentoDAO.insert(orcamento);

        List<Orcamento> listaDeCotacoes = orcamentoDAO.getAllOrcamentos();
        Assert.assertEquals(3, listaDeCotacoes.size());
    }

    private void carregaOrcamento() throws Exception {
        SimpleDateFormat formataData = new SimpleDateFormat("dd/MM/yyyy");
        Orcamento orcamento;
        ElementoOrcamentario elementoOrcamentario;
        Valor valor;

        OrcamentoDAO orcamentoDAO = getOrcamentoDAO();
        ElementoOrcamentarioDAO elementoOrcamentarioDAO = getElementoOrcamentarioDAO();
        ValorDAO valorDAO = getValorDAO();

        orcamento = new Orcamento(1, "Orcamento 1", formataData.parse("01/01/2022"));
        orcamentoDAO.insert(orcamento);

        elementoOrcamentario = new ElementoOrcamentario(1,'C', orcamento.uid,0);
        elementoOrcamentarioDAO.insert(elementoOrcamentario);

      //  valor = new Valor(1, 0,)

    }
}
