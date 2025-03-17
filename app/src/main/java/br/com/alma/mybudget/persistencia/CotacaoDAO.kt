package br.com.alma.mybudget.persistencia

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.com.alma.mybudget.modelo.Cotacao

@Dao
interface CotacaoDAO {
    @Insert(onConflict = OnConflictStrategy.Companion.IGNORE)
    fun insert(cotacao: Cotacao?)

    @Delete
    fun delete(cotacao: Cotacao?)

    @Query("delete from Cotacao")
    fun deleteAll()

    @Query("select * from Cotacao where uid = :uid")
    fun getCotacao(uid: Int): Cotacao?

    @Query("select * from Cotacao where moedaUID = :moedaUID order by data desc limit 1")
    fun findCotacaoAtualMoeda(moedaUID: Int): Cotacao?

    @Query("select * from Cotacao where moedaUID = :moedaUID")
    fun getCotacoesMoeda(moedaUID: Int): List<Cotacao?>?

    @get:Query("select * from Cotacao")
    val allCotacoes: List<Any?>?
}

