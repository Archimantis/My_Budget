package br.com.alma.mybudget.persistencia

import br.com.alma.mybudget.modelo.Conta
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class ContaDAOTest : AppDatabaseTest() {
    @Test
    @Throws(Exception::class)
    fun insertConta() {
        val conta = Conta(1, "Conta", "17X-#7", 1)

        contaDAO!!.insert(conta)
        val returningValue = contaDAO!!.getConta(conta.uid)

        Assertions.assertEquals(conta.uid.toLong(), returningValue!!.uid.toLong())
    }

    @Test
    @Throws(Exception::class)
    fun deleteconta() {
        val conta = Conta(1, "Conta", "17X-#7", 1)

        contaDAO!!.insert(conta)
        contaDAO!!.delete(conta)
        val returningValue = contaDAO!!.getConta(conta.uid)

        Assertions.assertNull(returningValue)
    }
}