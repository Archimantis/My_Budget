package br.com.alma.mybudget.persistencia

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.com.alma.mybudget.modelo.Moeda

@Dao
interface MoedaDAO {
    @Insert(onConflict = OnConflictStrategy.Companion.IGNORE)
    fun insert(moeda: Moeda?)

    @Delete
    fun delete(moeda: Moeda?)

    @Query("delete from Moeda")
    fun deleteAll()

    @Query("select * from Moeda where uid = :uid")
    fun getMoeda(uid: Int): Moeda?

    @Query("select * from Moeda where cod = :cod")
    fun findMoedasPorCodigo(cod: String?): Moeda?

    @get:Query("select * from Moeda")
    val allMoedas: List<Any?>?
}

