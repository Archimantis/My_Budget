package br.com.alma.mybudget.modelo

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    indices = [Index("orcamentoUID"), Index("classeDetalheUID")],
    foreignKeys = [ForeignKey(
        entity = Orcamento::class,
        parentColumns = arrayOf("uid"),
        childColumns = arrayOf("orcamentoUID"),
        onDelete = ForeignKey.CASCADE
    ), ForeignKey(
        entity = ClasseDetalhe::class,
        parentColumns = arrayOf("uid"),
        childColumns = arrayOf("classeDetalheUID"),
        onDelete = ForeignKey.CASCADE
    )]
)
class ElementoOrcamentario(
    @field:PrimaryKey var uid: Int,
    var tipo: Char,
    var orcamentoUID: Int,
    var classeDetalheUID: Int
)
