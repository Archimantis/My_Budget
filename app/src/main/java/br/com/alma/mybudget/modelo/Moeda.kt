package br.com.alma.mybudget.modelo

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey


@Entity(indices = [Index("cod")])
class Moeda(@field:PrimaryKey var uid: Int, var cod: String, var descr: String)
