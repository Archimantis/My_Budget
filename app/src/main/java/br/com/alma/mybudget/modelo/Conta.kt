package br.com.alma.mybudget.modelo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Conta(@field:PrimaryKey var uid: Int, var descr: String, var numero: String, var tipoConta: Int) 