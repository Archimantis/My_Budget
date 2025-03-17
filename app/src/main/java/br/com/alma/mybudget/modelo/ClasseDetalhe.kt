package br.com.alma.mybudget.modelo

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    indices = [Index("classeGeralUID")],
    foreignKeys = [ForeignKey(
        entity = ClasseGeral::class,
        parentColumns = arrayOf("uid"),
        childColumns = arrayOf("classeGeralUID"),
        onDelete = ForeignKey.CASCADE
    )]
)
class ClasseDetalhe(@field:PrimaryKey var uid: Int, var descr: String, var classeGeralUID: Int)