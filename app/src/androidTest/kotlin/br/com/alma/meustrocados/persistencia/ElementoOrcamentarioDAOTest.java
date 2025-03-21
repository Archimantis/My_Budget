package br.com.alma.meustrocados.persistencia;

import org.junit.Assert;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.List;

import br.com.alma.meustrocados.modelo.ClasseDetalhe;
import br.com.alma.meustrocados.modelo.ClasseGeral;
import br.com.alma.meustrocados.modelo.ElementoOrcamentario;
import br.com.alma.meustrocados.modelo.Orcamento;

public class ElementoOrcamentarioDAOTest extends AppDatabaseTest {

    @Test
    public void insertElementoOrcamentario() throws Exception {
        carregaElementosOrcamentarios();

        ElementoOrcamentario elementoOrcamentario = new ElementoOrcamentario(77, 'C', 1, 1);
        getElementoOrcamentarioDAO().insert(elementoOrcamentario);

        ElementoOrcamentario returningValue = getElementoOrcamentarioDAO().getElementoOrcamentario(elementoOrcamentario.uid);
        Assert.assertEquals(elementoOrcamentario.uid, returningValue.uid);
    }

    @Test
    public void deleteElementoOrcamentario() throws Exception {
        carregaElementosOrcamentarios();

        ElementoOrcamentario elementoOrcamentario = new ElementoOrcamentario(1, 'C', 1, 1);
        getElementoOrcamentarioDAO().insert(elementoOrcamentario);
        ElementoOrcamentario returningValue;

        // Com todos os atributos iguais
        getElementoOrcamentarioDAO().insert(elementoOrcamentario);
        getElementoOrcamentarioDAO().delete(elementoOrcamentario);
        returningValue = getElementoOrcamentarioDAO().getElementoOrcamentario(elementoOrcamentario.uid);
        Assert.assertNull(returningValue);

        // Com a mesma chave primária, mas com atributos alterados
        getElementoOrcamentarioDAO().insert(elementoOrcamentario);
        elementoOrcamentario.tipo = 'D';
        getElementoOrcamentarioDAO().delete(elementoOrcamentario);
        returningValue = getElementoOrcamentarioDAO().getElementoOrcamentario(elementoOrcamentario.uid);
        Assert.assertNull(returningValue);
    }

    @Test
    public void elementosOrcamentariosDeUmOrcamento() throws Exception {
        carregaElementosOrcamentarios();

        ElementoOrcamentario returningValue = getElementoOrcamentarioDAO().getElementoOrcamentario(1);
        Assert.assertEquals(1, returningValue.uid);

        List<ElementoOrcamentario> listaDeCotacoes = getElementoOrcamentarioDAO().getAllElementosOrcamentarios();
        Assert.assertEquals(3, listaDeCotacoes.size());
    }

    private void carregaElementosOrcamentarios() throws Exception {
        SimpleDateFormat formataData = new SimpleDateFormat("dd/MM/yyyy");
        Orcamento orcamento;
        ElementoOrcamentario elementoOrcamentario;
        ClasseGeral classeGeral;
        ClasseDetalhe classeDetalhe;

        ClasseGeralDAO classeGeralDAO = getClasseGeralDAO();
        ClasseDetalheDAO classeDetalheDAO = getClasseDetalheDAO();

        // Remove todas as classes orcamentárias
        classeGeralDAO.deleteAll();

        // Carrega classes orcamentarias
        int chaveClasseGeral = 1;
        int chaveClasseDetalhe = 1;

        // Moradia
        classeGeral = new ClasseGeral(chaveClasseGeral++, "Moradia");
        classeGeralDAO.insert(classeGeral);
        classeDetalhe = new ClasseDetalhe(chaveClasseDetalhe++, "Aluguel", classeGeral.uid);
        classeDetalheDAO.insert(classeDetalhe);
        classeDetalhe = new ClasseDetalhe(chaveClasseDetalhe++, "Serviços", classeGeral.uid);
        classeDetalheDAO.insert(classeDetalhe);
        classeDetalhe = new ClasseDetalhe(chaveClasseDetalhe++, "Água e Esgoto", classeGeral.uid);
        classeDetalheDAO.insert(classeDetalhe);
        classeDetalhe = new ClasseDetalhe(chaveClasseDetalhe++, "Energia Elétrica", classeGeral.uid);
        classeDetalheDAO.insert(classeDetalhe);
        classeDetalhe = new ClasseDetalhe(chaveClasseDetalhe++, "Móveis e Eletrodomésticos", classeGeral.uid);
        classeDetalheDAO.insert(classeDetalhe);
        classeDetalhe = new ClasseDetalhe(chaveClasseDetalhe++, "Outros", classeGeral.uid);
        classeDetalheDAO.insert(classeDetalhe);

        //Orcamento
        orcamento = new Orcamento(1, "Orcamento 1", formataData.parse("01/01/2022"));
        getOrcamentoDAO().insert(orcamento);

        elementoOrcamentario = new ElementoOrcamentario(1, 'C', 1, 1);
        getElementoOrcamentarioDAO().insert(elementoOrcamentario);
        elementoOrcamentario = new ElementoOrcamentario(1, 'C', 1, 2);
        getElementoOrcamentarioDAO().insert(elementoOrcamentario);
        elementoOrcamentario = new ElementoOrcamentario(2, 'C', 1, 3);
        getElementoOrcamentarioDAO().insert(elementoOrcamentario);
        elementoOrcamentario = new ElementoOrcamentario(3, 'D', 1, 4);
        getElementoOrcamentarioDAO().insert(elementoOrcamentario);
    }
}
