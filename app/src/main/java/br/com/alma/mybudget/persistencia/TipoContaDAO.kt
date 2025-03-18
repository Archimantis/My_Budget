package br.com.alma.mybudget.persistencia

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.com.alma.mybudget.modelo.TipoConta

@Dao
interface TipoContaDAO {
    @Insert(onConflict = OnConflictStrategy.Companion.IGNORE)
    fun insert(tipoConta: TipoConta?)

    @Delete
    fun delete(tipoConta: TipoConta?)

    @Query("delete from TipoConta")
    fun deleteAll()

    @Query("select * from TipoConta where uid = :uid")
    fun getTipoConta(uid: Int): TipoConta?

    @get:Query("select * from TipoConta")
    val allTipoContas: List<TipoConta?>?
}
