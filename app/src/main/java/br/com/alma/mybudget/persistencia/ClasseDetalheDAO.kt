package br.com.alma.mybudget.persistencia

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.com.alma.mybudget.modelo.ClasseDetalhe

@Dao
interface ClasseDetalheDAO {
    @Insert(onConflict = OnConflictStrategy.Companion.IGNORE)
    fun insert(classeDetalhe: ClasseDetalhe?)

    @Delete
    fun delete(classeDetalhe: ClasseDetalhe?)

    @Query("delete from ClasseDetalhe")
    fun deleteAll(carregaClasses: Unit)

    @Query("select * from ClasseDetalhe where uid = :uid")
    fun getClasseDetalhe(uid: Int): ClasseDetalhe?

    @get:Query("select * from ClasseDetalhe")
    val allClassesDetalhe: List<Any?>?

    @Query("select * from ClasseDetalhe where classeGeralUID = :classeGeralUID")
    fun getClassesDetalheDeUmaClasseGeral(classeGeralUID: Int): List<ClasseDetalhe?>?
}
