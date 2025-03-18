package br.com.alma.mybudget.persistencia

// Importe as classes de asserção que você irá usar
import br.com.alma.mybudget.modelo.Conta
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class ContaDAO5Teste : AppDatabase5Test() {

    @Test
    @DisplayName("Inclusão de Conta")
    @Throws(Exception::class)
    fun insertConta() {
        val conta = Conta(1, "Conta", "17X-#7", 1)

        contaDAO!!.insert(conta)
        val returningValue = contaDAO!!.getConta(conta.uid)

        assertEquals(conta.uid.toLong(), returningValue!!.uid.toLong())
    }

    @Test
    @DisplayName("Exclusão de Conta")
    @Throws(Exception::class)
    fun deleteconta() {
        val conta = Conta(1, "Conta", "17X-#7", 1)

        contaDAO!!.insert(conta)
        contaDAO!!.delete(conta)
        val returningValue = contaDAO!!.getConta(conta.uid)

        assertNull(returningValue)
    }
}