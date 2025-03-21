package br.com.alma.meustrocados.persistencia;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

import br.com.alma.meustrocados.modelo.TipoConta;

public class TipoContaDAOTest extends AppDatabaseTest {

    @Test
    public void insertTipoConta() throws Exception {
        TipoConta tipoConta = new TipoConta(1, "TipoConta");
        TipoConta returningValue;

        getTipoContaDAO().insert(tipoConta);
        returningValue = getTipoContaDAO().getTipoConta(tipoConta.uid);

        Assert.assertEquals(tipoConta.uid, returningValue.uid);
    }

    @Test
    public void deletetipoConta() throws Exception {
        TipoConta tipoConta = new TipoConta(1, "TipoConta");
        TipoConta returningValue;

        getTipoContaDAO().insert(tipoConta);
        getTipoContaDAO().delete(tipoConta);
        returningValue = getTipoContaDAO().getTipoConta(tipoConta.uid);

        Assert.assertNull(returningValue);
    }


    @Test
    public void recuperaTodosOsTipoContas() throws Exception {
        TipoConta tipoConta = new TipoConta(1, "Banco");
        getTipoContaDAO().insert(tipoConta);
        tipoConta = new TipoConta(2, "Caixa");
        getTipoContaDAO().insert(tipoConta);
        tipoConta = new TipoConta(3, "Cartão");
        getTipoContaDAO().insert(tipoConta);

        List<TipoConta> returningValue = getTipoContaDAO().getAllTipoContas();

        Assert.assertEquals(3, returningValue.size());
    }
}
