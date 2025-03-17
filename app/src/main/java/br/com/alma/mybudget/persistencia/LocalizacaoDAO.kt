package br.com.alma.mybudget.persistencia

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.com.alma.mybudget.modelo.Localizacao

@Dao
interface LocalizacaoDAO {
    @Insert(onConflict = OnConflictStrategy.Companion.IGNORE)
    fun insert(localizacao: Localizacao?)

    @Delete
    fun delete(localizacao: Localizacao?)

    @Query("delete from Localizacao")
    fun deleteAll()

    @Query("select * from Localizacao where uid = :uid")
    fun getLocalizacao(uid: Int): Localizacao?

    @Query("select * from Localizacao where sujeitoUID = :sujeitoUID")
    fun getLocalizacoesDeUmSujeito(sujeitoUID: Int): List<Localizacao?>?

    @Query("select * from Localizacao")
    fun getAllLocalizacoes(): List<Localizacao?>?

    @Query("select uid from Localizacao where ((latitude - :latitude) * (latitude - :latitude)) + ((longitude - :longitude) * (longitude - :longitude)) < 0.00003 limit 1")
    fun findSujeitoMaisProximo(latitude: Double, longitude: Double): Int

    //TODO: remover posteriormente. Criada apenas para testes iniciais da persistência
    @Query("select count(*) from Localizacao")
    fun contaLinhas(): Int
}

