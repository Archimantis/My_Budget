package br.com.alma.mybudget.persistencia

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.com.alma.mybudget.modelo.ClasseGeral

@Dao
interface ClasseGeralDAO {
    @Insert(onConflict = OnConflictStrategy.Companion.IGNORE)
    fun insert(classeGeral: ClasseGeral?)

    @Delete
    fun delete(classeGeral: ClasseGeral?)

    @Query("delete from ClasseGeral")
    fun deleteAll()

    @Query("select * from ClasseGeral where uid = :uid")
    fun getClasseGeral(uid: Int): ClasseGeral?

    @get:Query("select * from ClasseGeral")
    val allClassesGerais: List<ClasseGeral>?
}
