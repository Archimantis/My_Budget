package br.com.alma.mybudget.persistencia

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.com.alma.mybudget.modelo.Sujeito

@Dao
interface SujeitoDAO {
    @Insert(onConflict = OnConflictStrategy.Companion.IGNORE)
    fun insert(sujeito: Sujeito?)

    @Delete
    fun delete(sujeito: Sujeito?)

    @Query("delete from Sujeito")
    fun deleteAll()

    @Query("select * from Sujeito where uid = :uid")
    fun getSujeito(uid: Int): Sujeito?
}
