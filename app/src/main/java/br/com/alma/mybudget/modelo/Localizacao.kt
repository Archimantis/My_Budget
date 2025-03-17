package br.com.alma.mybudget.modelo

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    indices = [Index("sujeitoUID")],
    foreignKeys = [ForeignKey(
        entity = Sujeito::class,
        parentColumns = arrayOf("uid"),
        childColumns = arrayOf("sujeitoUID"),
        onDelete = ForeignKey.CASCADE
    )]
)
class Localizacao(@field:PrimaryKey var uid: Int, var sujeitoUID: Int, var latitude: Double, var longitude: Double)
