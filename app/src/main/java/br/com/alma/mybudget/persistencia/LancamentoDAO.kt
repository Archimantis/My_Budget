package br.com.alma.mybudget.persistencia

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.com.alma.mybudget.modelo.Lancamento

@Dao
interface LancamentoDAO {
    @Insert(onConflict = OnConflictStrategy.Companion.IGNORE)
    fun insert(lancamento: Lancamento?)

    @Delete
    fun delete(lancamento: Lancamento?)

    @Query("delete from Lancamento")
    fun deleteAll()

    @Query("select * from Lancamento where uid = :uid")
    fun getLancamento(uid: Int): Lancamento?

    @Query("select * from Lancamento where contaUID = :contaUID")
    fun getLancamentosDeUmaConta(contaUID: Int): List<Lancamento?>?

    @Query("select * from Lancamento where sujeitoUID = :sujeitoUID")
    fun getLancamentosDeUmSujeito(sujeitoUID: Int): List<Lancamento?>?

    @get:Query("select * from Lancamento")
    val allLancamentos: List<Any?>?
}
