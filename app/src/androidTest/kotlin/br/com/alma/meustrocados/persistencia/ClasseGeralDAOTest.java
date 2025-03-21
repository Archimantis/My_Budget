package br.com.alma.meustrocados.persistencia;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

import br.com.alma.meustrocados.modelo.ClasseDetalhe;
import br.com.alma.meustrocados.modelo.ClasseGeral;

public class ClasseGeralDAOTest extends AppDatabaseTest {

    @Test
    public void insertClasseGeral() throws Exception {
        ClasseGeral classeGeral = new ClasseGeral(1, "ClasseGeral");
        getClasseGeralDAO().insert(classeGeral);

        ClasseGeral returningValue = getClasseGeralDAO().getClasseGeral(classeGeral.uid);
        Assert.assertEquals(classeGeral.uid, returningValue.uid);
    }

    @Test
    public void deleteclasseGeral() throws Exception {
        ClasseGeral classeGeral;
        ClasseDetalhe classeDetalhe;
        ClasseGeral returningValueGeral;
        ClasseDetalhe returningValueDetalhe;
        List<ClasseDetalhe> returningValueListaDetalhe;

        // Exclusão simples de uma classe geral criada anteriormente, sem segunda inserção e sem classesDetalhe associadas.
        classeGeral = new ClasseGeral(1, "Descrição ignorada e jamais utilizada");
        returningValueGeral = getClasseGeralDAO().getClasseGeral(classeGeral.uid);
        returningValueListaDetalhe = getClasseDetalheDAO().getClassesDetalheDeUmaClasseGeral(classeGeral.uid);
        getClasseGeralDAO().delete(classeGeral);
        returningValueGeral = getClasseGeralDAO().getClasseGeral(classeGeral.uid);
        returningValueListaDetalhe = getClasseDetalheDAO().getClassesDetalheDeUmaClasseGeral(classeGeral.uid);
        Assert.assertNull(returningValueGeral);
        Assert.assertEquals(0, returningValueListaDetalhe.size());

        // Exclusão em cascata sem segunda inserção da ClasseGeral e com classesDetalhe associadas.
        classeGeral = new ClasseGeral(2, "Tentativa de inclusão de nova descrição e que deve falhar");
        returningValueGeral = getClasseGeralDAO().getClasseGeral(classeGeral.uid);
        returningValueListaDetalhe = getClasseDetalheDAO().getClassesDetalheDeUmaClasseGeral(classeGeral.uid);
        getClasseGeralDAO().delete(classeGeral);
        returningValueGeral = getClasseGeralDAO().getClasseGeral(classeGeral.uid);
        returningValueListaDetalhe = getClasseDetalheDAO().getClassesDetalheDeUmaClasseGeral(classeGeral.uid);
        Assert.assertNull(returningValueGeral);
        Assert.assertEquals(0, returningValueListaDetalhe.size());

        // Exclusão em cascata com segunda inserção da ClasseGeral e com classesDetalhe associadas.
        classeGeral = new ClasseGeral(3, "Teste");
        returningValueGeral = getClasseGeralDAO().getClasseGeral(classeGeral.uid);
        returningValueListaDetalhe = getClasseDetalheDAO().getClassesDetalheDeUmaClasseGeral(classeGeral.uid);
        getClasseGeralDAO().insert(classeGeral);
        returningValueGeral = getClasseGeralDAO().getClasseGeral(classeGeral.uid);
        returningValueListaDetalhe = getClasseDetalheDAO().getClassesDetalheDeUmaClasseGeral(classeGeral.uid);
        getClasseGeralDAO().delete(classeGeral);
        returningValueGeral = getClasseGeralDAO().getClasseGeral(classeGeral.uid);
        returningValueListaDetalhe = getClasseDetalheDAO().getClassesDetalheDeUmaClasseGeral(classeGeral.uid);
        Assert.assertNull(returningValueGeral);
        Assert.assertEquals(0, returningValueListaDetalhe.size());
    }

    @Test
    public void testaCarga() throws Exception {
        getClasseDetalheDAO().deleteAll();
        carregaClasses();

        List<ClasseGeral> returningValue = getClasseGeralDAO().getAllClassesGerais();
        Assert.assertEquals("Alimentação", returningValue.get(0).descr);
    }

    @Test
    public void recuperaTodosOsClasseGerals() throws Exception {
        getClasseDetalheDAO().deleteAll();
        carregaClasses();

        List<ClasseGeral> returningValue = getClasseGeralDAO().getAllClassesGerais();
        Assert.assertEquals(3, returningValue.size());
    }

    private void carregaClasses() {
        ClasseGeralDAO classeGeralDAO = getClasseGeralDAO();
        ClasseDetalheDAO classeDetalheDAO = getClasseDetalheDAO();

        // Remove todas as classes orcamentárias
        classeGeralDAO.deleteAll();

        // Carrega classes orcamentarias
        ClasseGeral classeGeral;
        ClasseDetalhe classeDetalhe;
        int chaveClasseGeral = 1;
        int chaveClasseDetalhe = 1;

        // Alimentação
        classeGeral = new ClasseGeral(chaveClasseGeral++, "Alimentação");
        classeGeralDAO.insert(classeGeral);

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

        // Educação
        classeGeral = new ClasseGeral(chaveClasseGeral++, "Educação");
        classeGeralDAO.insert(classeGeral);
        classeDetalhe = new ClasseDetalhe(chaveClasseDetalhe++, "Mensalidade Escolar", classeGeral.uid);
        classeDetalheDAO.insert(classeDetalhe);
        classeDetalhe = new ClasseDetalhe(chaveClasseDetalhe++, "Inglês", classeGeral.uid);
        classeDetalheDAO.insert(classeDetalhe);
        classeDetalhe = new ClasseDetalhe(chaveClasseDetalhe++, "Uniformes", classeGeral.uid);
        classeDetalheDAO.insert(classeDetalhe);
    }
}
