package br.com.alma.mybudget.modelo

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.Date

@Entity(indices = [Index("data")])
class Orcamento(@field:PrimaryKey var uid: Int, var descr: String, var data: Date)
