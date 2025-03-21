package br.com.alma.meustrocados.persistencia;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

import br.com.alma.meustrocados.modelo.Localizacao;
import br.com.alma.meustrocados.modelo.Sujeito;

public class SujeitoDAOTest extends AppDatabaseTest {

    @Test
    public void insertSujeito() throws Exception {
        Sujeito sujeito = new Sujeito(1, "Sujeito");
        Sujeito returningValue;

        getSujeitoDAO().insert(sujeito);
        returningValue = getSujeitoDAO().getSujeito(sujeito.uid);

        Assert.assertEquals(sujeito.uid, returningValue.uid);
    }

    @Test
    public void deletesujeito() throws Exception {
        Sujeito sujeito ;
        Sujeito returningValueSujeito;
        Localizacao localizacao;
        List<Localizacao> returningValueListaLocalizacao;

        // Sujeito sem localizações
        sujeito = new Sujeito(1, "Sujeito");
        getSujeitoDAO().insert(sujeito);
        getSujeitoDAO().delete(sujeito);
        returningValueSujeito = getSujeitoDAO().getSujeito(sujeito.uid);
        Assert.assertNull(returningValueSujeito);

        // Sujeito com localizações
        sujeito = new Sujeito(2, "Sujeito");
        getSujeitoDAO().insert(sujeito);
        localizacao = new Localizacao(1,2,15.00,48.00);
        getLocalizacaoDAO().insert(localizacao);
        localizacao = new Localizacao(2,2,15.01,48.01);
        getLocalizacaoDAO().insert(localizacao);

        getSujeitoDAO().delete(sujeito);
        returningValueSujeito = getSujeitoDAO().getSujeito(sujeito.uid);
        returningValueListaLocalizacao = getLocalizacaoDAO().getLocalizacoesDeUmSujeito(sujeito.uid);
        Assert.assertNull(returningValueSujeito);
        Assert.assertEquals(0, returningValueListaLocalizacao.size());

    }
}
