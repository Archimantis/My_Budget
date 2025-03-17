package br.com.alma.mybudget.modelo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class TipoConta(@field:PrimaryKey var uid: Int, var descr: String) 