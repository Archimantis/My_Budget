package br.com.alma.mybudget.persistencia

import androidx.room.Dao
import androidx.room.OnConflictStrategy
import br.com.alma.mybudget.modelo.Sujeito

@Dao
interface SujeitoDAO {
    @androidx.room.Insert(onConflict = OnConflictStrategy.Companion.IGNORE)
    fun insert(sujeito: Sujeito?)

    @androidx.room.Delete
    fun delete(sujeito: Sujeito?)

    @androidx.room.Query("delete from Sujeito")
    fun deleteAll()

    @androidx.room.Query("select * from Sujeito where uid = :uid")
    fun getSujeito(uid: Int): Sujeito?
}
