package br.com.alma.meustrocados.persistencia;

import org.junit.Assert;
import org.junit.Test;

import br.com.alma.meustrocados.modelo.Moeda;

public class MoedaDAOTest extends AppDatabaseTest {

    @Test
    public void insertMoeda() throws Exception {
        Moeda moeda = new Moeda(1, "BRL", "Real brasileiro");
        Moeda returningValue;

        getMoedaDAO().insert(moeda);
        returningValue = getMoedaDAO().getMoeda(moeda.uid);

        Assert.assertEquals(moeda.uid, returningValue.uid);
    }

    @Test
    public void deleteMoeda() throws Exception {
        Moeda moeda = new Moeda(1, "BRL", "Real brasileiro");
        Moeda returningValue;

        // Com todos os atributos iguais
        getMoedaDAO().insert(moeda);
        getMoedaDAO().delete(moeda);
        returningValue = getMoedaDAO().getMoeda(moeda.uid);
        Assert.assertNull(returningValue);

        // Com a mesma chave primária, mas com atributos alterados
        getMoedaDAO().insert(moeda);
        moeda.cod = "USD";
        moeda.descr = "Dólar americano";
        getMoedaDAO().delete(moeda);
        returningValue = getMoedaDAO().getMoeda(moeda.uid);
        Assert.assertNull(returningValue);
    }

    @Test
    public void localizacoesDeUmSujeito() throws Exception {

        // Recupera localizações de um sujeito
        Moeda moeda = new Moeda(1, "BRL", "Real brasileiro");
        MoedaDAO moedaDAO = getMoedaDAO();
        moedaDAO.insert(moeda);
        moeda.uid = 2;
        moeda.cod = "USD";
        moeda.descr = "Dólar americano";
        moedaDAO.insert(moeda);
        moeda.uid = 3;
        moeda.cod = "ARS";
        moeda.descr = "Peso argentino";
        moedaDAO.insert(moeda);

        moeda = moedaDAO.getMoeda(1);
        Assert.assertEquals("BRL", moeda.cod);

        moeda = moedaDAO.findMoedasPorCodigo("ARS");
        Assert.assertEquals(3, moeda.uid);
    }
}
