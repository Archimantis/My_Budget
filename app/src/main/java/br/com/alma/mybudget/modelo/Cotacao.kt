package br.com.alma.mybudget.modelo

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.Date

@Entity(
    indices = [Index("data"), Index("moedaUID")],
    foreignKeys = [ForeignKey(
        entity = Moeda::class,
        parentColumns = arrayOf("uid"),
        childColumns = arrayOf("moedaUID"),
        onDelete = ForeignKey.CASCADE
    )]
)
class Cotacao(@field:PrimaryKey var uid: Int, var valor: Double, var data: Date, var moedaUID: Int)
