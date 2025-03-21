package br.com.alma.meustrocados.persistencia;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

import br.com.alma.meustrocados.modelo.ClasseDetalhe;
import br.com.alma.meustrocados.modelo.ClasseGeral;

public class ClasseDetalheDAOTest extends AppDatabaseTest {

    @Test
    public void insertClasseDetalhe() throws Exception {
        ClasseGeral classeGeral = new ClasseGeral(1, "ClasseGeral");
        getClasseGeralDAO().insert(classeGeral);
        ClasseDetalhe classeDetalhe = new ClasseDetalhe(1, "ClasseDetalhe", 1);
        getClasseDetalheDAO().insert(classeDetalhe);

        ClasseDetalhe returningValue = getClasseDetalheDAO().getClasseDetalhe(classeDetalhe.uid);
        Assert.assertEquals(classeDetalhe.uid, returningValue.uid);
    }

    @Test
    public void deleteclasseDetalhe() throws Exception {
        ClasseGeral classeGeral = new ClasseGeral(1, "ClasseGeral");
        getClasseGeralDAO().insert(classeGeral);
        ClasseDetalhe classeDetalhe = new ClasseDetalhe(1, "ClasseDetalhe", 1);
        getClasseDetalheDAO().insert(classeDetalhe);

        getClasseDetalheDAO().insert(classeDetalhe);
        getClasseDetalheDAO().delete(classeDetalhe);
        ClasseDetalhe returningValue = getClasseDetalheDAO().getClasseDetalhe(classeDetalhe.uid);
        Assert.assertNull(returningValue);
    }


    @Test
    public void recuperaTodosAsClasseDetalhes() throws Exception {
        getClasseGeralDAO().deleteAll();
        carregaClasses();

        List<ClasseDetalhe> returningValue = getClasseDetalheDAO().getAllClassesDetalhe();
        Assert.assertEquals(9, returningValue.size());
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
