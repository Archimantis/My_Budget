package br.com.alma.meustrocados.persistencia;

import org.junit.Assert;
import org.junit.Test;

import br.com.alma.meustrocados.modelo.Localizacao;
import br.com.alma.meustrocados.modelo.Sujeito;

public class LocalizacaoDAOTest extends AppDatabaseTest {

    @Test
    public void insertLocalizacao() throws Exception {
        Sujeito sujeito;
        Localizacao localizacao;
        Localizacao returningValue;
        // Sujeito com localizações
        getLocalizacaoDAO().deleteAll();
        getSujeitoDAO().deleteAll();
        sujeito = new Sujeito(2, "Sujeito");
        getSujeitoDAO().insert(sujeito);

        localizacao = new Localizacao(1, sujeito.uid, 0.00000, 0.00000);
        getLocalizacaoDAO().insert(localizacao);
        returningValue = getLocalizacaoDAO().getLocalizacao(localizacao.uid);

        Assert.assertEquals(localizacao.uid, returningValue.uid);
    }

    @Test
    public void deleteLocalizacao() throws Exception {
        Sujeito sujeito;
        Localizacao localizacao;
        Localizacao returningValue;
        // Sujeito com localizações
        getLocalizacaoDAO().deleteAll();
        getSujeitoDAO().deleteAll();
        sujeito = new Sujeito(2, "Sujeito");
        getSujeitoDAO().insert(sujeito);

        // Com todos os atributos iguais
        localizacao = new Localizacao(1, sujeito.uid, 0.00000, 0.00000);
        getLocalizacaoDAO().insert(localizacao);
        getLocalizacaoDAO().delete(localizacao);
        returningValue = getLocalizacaoDAO().getLocalizacao(localizacao.uid);
        Assert.assertNull(returningValue);

        // Com a mesma chave primária, mas com atributos alterados
        getLocalizacaoDAO().insert(localizacao);
        localizacao.sujeitoUID = 2;
        localizacao.latitude = 90;
        localizacao.longitude = 90;
        getLocalizacaoDAO().delete(localizacao);
        returningValue = getLocalizacaoDAO().getLocalizacao(localizacao.uid);
        Assert.assertNull(returningValue);
    }

    @Test
    public void localizacoesDeUmSujeito() throws Exception {
        Sujeito sujeito;
        Localizacao localizacao;
        // Sujeito com localizações
        getLocalizacaoDAO().deleteAll();
        getSujeitoDAO().deleteAll();
        sujeito = new Sujeito(2, "Sujeito");
        getSujeitoDAO().insert(sujeito);

        // Recupera localizações de um sujeito
        localizacao = new Localizacao(1, sujeito.uid, 0.00000, 0.00000);
        getLocalizacaoDAO().insert(localizacao);
        localizacao.uid = 2;
        localizacao.latitude = 3.00;
        getLocalizacaoDAO().insert(localizacao);
        localizacao.uid = 3;
        localizacao.longitude = 3.00;
        getLocalizacaoDAO().insert(localizacao);

        int nroLinhas = getLocalizacaoDAO().contaLinhas();
        int returningValue = getLocalizacaoDAO().getLocalizacoesDeUmSujeito(sujeito.uid).size();

        Assert.assertEquals(nroLinhas, returningValue);
    }

    @Test
    public void localizacaoProxima() throws Exception {
        Sujeito sujeito;
        Localizacao localizacao;
        // Sujeito com localizações
        sujeito = new Sujeito(2, "Sujeito");
        getSujeitoDAO().insert(sujeito);

        // Recupera localizações de um sujeito
        localizacao = new Localizacao(1, sujeito.uid, 0.00000, 0.00000);
        LocalizacaoDAO localizacaoDAO = getLocalizacaoDAO();
        localizacaoDAO.insert(localizacao);
        // Ponto distante
        localizacao.uid = 2;
        localizacao.latitude = 1.00000;
        localizacao.longitude = 1.00000;
        localizacaoDAO.insert(localizacao);
        // Ponto próximo
        localizacao.uid = 3;
        localizacao.latitude = 3.00000;
        localizacao.longitude = 3.00003;
        localizacaoDAO.insert(localizacao);

        // Não acha localização próxima
        Localizacao localizacaoProxima = localizacaoDAO.getLocalizacao(localizacaoDAO.findSujeitoMaisProximo(15.00000, 15.0000));
        Assert.assertNull(localizacaoProxima);

        // Acha localização próxima
        int proximaUID = localizacaoDAO.findSujeitoMaisProximo(3.00000, 3.00006);
        Assert.assertEquals(3, proximaUID);

        localizacaoProxima = localizacaoDAO.getLocalizacao(proximaUID);
        Assert.assertEquals(3, localizacaoProxima.uid);
    }
}
