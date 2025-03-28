package br.com.alma.mybudget.persistencia

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.com.alma.mybudget.modelo.Orcamento

@Dao
interface OrcamentoDAO {
    @Insert(onConflict = OnConflictStrategy.Companion.IGNORE)
    fun insert(orcamento: Orcamento?)

    @Delete
    fun delete(orcamento: Orcamento?)

    @Query("delete from Orcamento")
    fun deleteAll()

    @Query("select * from Orcamento where uid = :uid")
    fun getOrcamento(uid: Int): Orcamento?

    @Query("select * from Orcamento")
    fun getAllOrcamentos(): List<Orcamento?>
}

