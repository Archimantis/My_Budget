package br.com.alma.mybudget.persistencia

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import br.com.alma.mybudget.R
import br.com.alma.mybudget.modelo.ClasseDetalhe
import br.com.alma.mybudget.modelo.ClasseGeral
import br.com.alma.mybudget.modelo.Conta
import br.com.alma.mybudget.modelo.Cotacao
import br.com.alma.mybudget.modelo.ElementoOrcamentario
import br.com.alma.mybudget.modelo.Lancamento
import br.com.alma.mybudget.modelo.Localizacao
import br.com.alma.mybudget.modelo.Moeda
import br.com.alma.mybudget.modelo.Orcamento
import br.com.alma.mybudget.modelo.Sujeito
import br.com.alma.mybudget.modelo.TipoConta
import br.com.alma.mybudget.modelo.Valor

@Database(
    entities =
        [
            Localizacao::class,
            Sujeito::class,
            Moeda::class,
            Cotacao::class,
            Conta::class,
            TipoConta::class,
            Lancamento::class,
            Valor::class,
            ClasseGeral::class,
            ClasseDetalhe::class,
            Orcamento::class,
            ElementoOrcamentario::class
        ],
    version = 1,
    exportSchema = false
)

@TypeConverters(Conversores::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun localizacaoDAO(): LocalizacaoDAO?

    abstract fun sujeitoDAO(): SujeitoDAO?

    abstract fun moedaDAO(): MoedaDAO?

    abstract fun cotacaoDAO(): CotacaoDAO?

    abstract fun contaDAO(): ContaDAO?

    abstract fun tipoContaDAO(): TipoContaDAO?

    abstract fun lancamentoDAO(): LancamentoDAO?

    abstract fun valorDAO(): ValorDAO?

    abstract fun classeGeralDAO(): ClasseGeralDAO

    abstract fun classeDetalheDAO(): ClasseDetalheDAO

    abstract fun orcamentoDAO(): OrcamentoDAO?

    abstract fun elementoOrcamentarioDAO(): ElementoOrcamentarioDAO?

    companion object {
        @android.annotation.SuppressLint("StaticFieldLeak")
        @kotlin.concurrent.Volatile
        private var INSTANCE: AppDatabase? = null
        private const val NUMBER_OF_THREADS = 4

        @JvmField
        val databaseWriteExecutor: java.util.concurrent.ExecutorService =
            java.util.concurrent.Executors.newFixedThreadPool(
                NUMBER_OF_THREADS
            )

        @android.annotation.SuppressLint("StaticFieldLeak")
        private var CONTEXT: android.content.Context? = null

        @JvmStatic
        fun getDatabase(context: Application?): AppDatabase? {
            CONTEXT = context
            if (INSTANCE == null) {
                synchronized(AppDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = context?.let {
                            Room.databaseBuilder(
                                it,
                                AppDatabase::class.java,
                                context.getString(R.string.DatabaseName)
                            )
                                .addCallback(sRoomDatabaseCallback).build()
                        }
                    }
                }
            }
            return INSTANCE
        }

        private val sRoomDatabaseCallback: Callback = object : Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)

                // If you want to keep data through app restarts,
                // comment out the following block
                // Populate the database in the background.
                // If you want to start with more words, just add them.
                databaseWriteExecutor.execute { carregaClasses() }
            }
        }

        private fun carregaClasses() {
            val classeGeralDAO = INSTANCE!!.classeGeralDAO()
            val classeDetalheDAO = INSTANCE!!.classeDetalheDAO()

            // Remove todas as classes orcamentárias
            classeGeralDAO.deleteAll()

            // Carrega classes orcamentarias
            var classeGeral: ClasseGeral
            var classeDetalhe: ClasseDetalhe?
            var chaveClasseGeral = 1
            var chaveClasseDetalhe = 1

            // Moradia
            classeGeral = ClasseGeral(chaveClasseGeral++, "Moradia")
            classeGeralDAO.insert(classeGeral)

            classeDetalhe = ClasseDetalhe(chaveClasseDetalhe++, "Aluguel", classeGeral.uid)
            classeDetalheDAO.insert(classeDetalhe)

            classeDetalhe = ClasseDetalhe(chaveClasseDetalhe++, "Serviços", classeGeral.uid)
            classeDetalheDAO.insert(classeDetalhe)

            classeDetalhe = ClasseDetalhe(chaveClasseDetalhe++, "Água e Esgoto", classeGeral.uid)
            classeDetalheDAO.insert(classeDetalhe)

            classeDetalhe = ClasseDetalhe(chaveClasseDetalhe++, "Energia Elétrica", classeGeral.uid)
            classeDetalheDAO.insert(classeDetalhe)

            classeDetalhe = ClasseDetalhe(chaveClasseDetalhe++, "Móveis e Eletrodomésticos", classeGeral.uid)
            classeDetalheDAO.insert(classeDetalhe)

            classeDetalhe = ClasseDetalhe(chaveClasseDetalhe, "Outros", classeGeral.uid)
            classeDetalheDAO.insert(classeDetalhe)

            // Alimentação
            classeGeral = ClasseGeral(chaveClasseGeral++, "Alimentação")
            classeGeralDAO.insert(classeGeral)

            classeDetalhe = ClasseDetalhe(chaveClasseDetalhe, "Mercado", classeGeral.uid)
            classeDetalheDAO.insert(classeDetalhe)

            classeDetalhe = ClasseDetalhe(chaveClasseDetalhe, "Restaurantes", classeGeral.uid)
            classeDetalheDAO.insert(classeDetalhe)

            // Educação
            classeGeral = ClasseGeral(chaveClasseGeral, "Educação")
            classeGeralDAO.insert(classeGeral)

            classeDetalhe = ClasseDetalhe(chaveClasseDetalhe, "Mensalidade", classeGeral.uid)
            classeDetalheDAO.insert(classeDetalhe)

            classeDetalhe = ClasseDetalhe(chaveClasseDetalhe, "Materiais", classeGeral.uid)
            classeDetalheDAO.insert(classeDetalhe)
        }
    }
}