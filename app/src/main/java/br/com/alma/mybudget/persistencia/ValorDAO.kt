package br.com.alma.mybudget.persistencia

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.com.alma.mybudget.modelo.Valor

@Dao
interface ValorDAO {
    @Insert(onConflict = OnConflictStrategy.Companion.IGNORE)
    fun insert(valor: Valor?)

    @Delete
    fun delete(valor: Valor?)

    @Query("delete from Valor")
    fun deleteAll()

    @Query("select * from Valor where uid = :uid")
    fun getValor(uid: Int): Valor?

    @Query("select * from Valor where lancamentoUID = :lancamentoUID")
    fun getValoresDeUmLancamento(lancamentoUID: Int): List<Valor?>?

    @Query("select * from Valor where classeDetalheUID = :classeDetalheUID")
    fun getValoresDeUmClasseDetalhe(classeDetalheUID: Int): List<Valor?>?

    @Query("select * from Valor where moedaUID = :moedaUID")
    fun getValoresDeUmaMoeda(moedaUID: Int): List<Valor?>?

    @Query("select * from Valor")
    fun getAllValores(): List<Valor?>?
}
