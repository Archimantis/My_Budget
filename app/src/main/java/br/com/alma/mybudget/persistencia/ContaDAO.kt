package br.com.alma.mybudget.persistencia

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.com.alma.mybudget.modelo.Conta

@Dao
interface ContaDAO {
    @Insert(onConflict = OnConflictStrategy.Companion.IGNORE)
    fun insert(conta: Conta?)

    @Delete
    fun delete(conta: Conta?)

    @Query("delete from Conta")
    fun deleteAll()

    @Query("select * from Conta where uid = :uid")
    fun getConta(uid: Int): Conta?

    @get:Query("select * from Conta")
    val allConta: List<Conta?>?
}
