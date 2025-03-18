package br.com.alma.mybudget.modelo

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    indices = [Index("lancamentoUID"), Index("classeDetalheUID"), Index("moedaUID"), Index("elementoOrcamentarioUID")],
    foreignKeys = [ForeignKey(
        entity = ElementoOrcamentario::class,
        parentColumns = arrayOf("uid"),
        childColumns = arrayOf("elementoOrcamentarioUID"),
        onDelete = ForeignKey.CASCADE
    ), ForeignKey(
        entity = Lancamento::class,
        parentColumns = arrayOf("uid"),
        childColumns = arrayOf("lancamentoUID"),
        onDelete = ForeignKey.CASCADE
    )]
)
class Valor(
    @field:PrimaryKey var uid: Int,
    var valor: Double,
    var lancamentoUID: Int,
    var classeDetalheUID: Int,
    var moedaUID: Int?,
    var elementoOrcamentarioUID: Int?
)
