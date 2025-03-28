package br.com.alma.mybudget.persistencia.persistencia

import br.com.alma.mybudget.modelo.Conta
import org.junit.Assert
import org.junit.Test
import java.lang.Exception

class ContaDAOTest : AppDatabaseTest() {
    
    @Test
    @Throws(Exception::class)
    fun insertConta() {
        val conta: Conta = Conta(1, "Conta", "17X-#7", 1)

        contaDAO?.insert(conta)
        val returningValue: Conta = contaDAO?.getConta(conta.uid)!!

        Assert.assertEquals(conta.uid, returningValue.uid)
    }

    @Test
    @Throws(Exception::class)
    fun deleteconta() {
        val conta: Conta = Conta(1, "Conta", "17X-#7", 1)

        contaDAO?.insert(conta)
        contaDAO?.delete(conta)
        val returningValue: Conta? = contaDAO?.getConta(conta.uid)

        Assert.assertNull(returningValue)
    }
}