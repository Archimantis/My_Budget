package br.com.alma.meustrocados.persistencia;

import org.junit.Assert;
import org.junit.Test;

import br.com.alma.meustrocados.modelo.Conta;

public class ContaDAOTest extends AppDatabaseTest {

    @Test
    public void insertConta() throws Exception {
        Conta conta = new Conta(1, "Conta", "17X-#7",1);
        Conta returningValue;

        getContaDAO().insert(conta);
        returningValue = getContaDAO().getConta(conta.uid);

        Assert.assertEquals(conta.uid, returningValue.uid);
    }

    @Test
    public void deleteconta() throws Exception {
        Conta conta = new Conta(1, "Conta", "17X-#7",1);
        Conta returningValue;

        getContaDAO().insert(conta);
        getContaDAO().delete(conta);
        returningValue = getContaDAO().getConta(conta.uid);

        Assert.assertNull(returningValue);
    }
}