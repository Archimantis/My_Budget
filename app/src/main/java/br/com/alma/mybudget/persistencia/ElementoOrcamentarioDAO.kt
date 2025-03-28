package br.com.alma.mybudget.persistencia

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.com.alma.mybudget.modelo.ElementoOrcamentario

@Dao
interface ElementoOrcamentarioDAO {
    @Insert(onConflict = OnConflictStrategy.Companion.IGNORE)
    fun insert(elementoOrcamentario: ElementoOrcamentario?)

    @Delete
    fun delete(elementoOrcamentario: ElementoOrcamentario?)

    @Query("delete from ElementoOrcamentario")
    fun deleteAll()

    @Query("select * from ElementoOrcamentario where uid = :uid")
    fun getElementoOrcamentario(uid: Int): ElementoOrcamentario?

    @Query("select * from ElementoOrcamentario")
    fun getAllElementosOrcamentarios(): List<ElementoOrcamentario?>?
}

