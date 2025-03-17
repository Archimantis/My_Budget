package br.com.alma.mybudget.modelo

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.Date

@Entity(
    indices = [Index("data"), Index("sujeitoUID"), Index("contaUID")],
    foreignKeys = [ForeignKey(
        entity = Conta::class,
        parentColumns = arrayOf("uid"),
        childColumns = arrayOf("contaUID"),
        onDelete = ForeignKey.CASCADE
    ), ForeignKey(
        entity = Sujeito::class,
        parentColumns = arrayOf("uid"),
        childColumns = arrayOf("sujeitoUID"),
        onDelete = ForeignKey.CASCADE
    )]
)
class Lancamento(
    @field:PrimaryKey var uid: Int,
    var sujeitoUID: Int,
    var contaUID: Int,
    var data: Date,
    var tipo: Char,
    var observacao: String
)
