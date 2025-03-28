package br.com.alma.mybudget.persistencia

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.com.alma.mybudget.modelo.ClasseDetalhe
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

    @Query("select * from ClasseDetalhe where classeGeralUID = :uid")
    fun getClassesDetalheDeUmaClasseGeral(uid: Int): List<ClasseDetalhe?>

    @Query("select * from ClasseGeral")
    fun getAllClassesGerais(): List<ClasseGeral?>
}
