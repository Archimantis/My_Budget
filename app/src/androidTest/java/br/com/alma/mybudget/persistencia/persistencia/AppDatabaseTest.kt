package br.com.alma.mybudget.persistencia.persistencia

import android.content.Context
import androidx.room.Room.inMemoryDatabaseBuilder
import androidx.test.core.app.ApplicationProvider
import br.com.alma.mybudget.persistencia.AppDatabase
import br.com.alma.mybudget.persistencia.ClasseDetalheDAO
import br.com.alma.mybudget.persistencia.ClasseGeralDAO
import br.com.alma.mybudget.persistencia.ContaDAO
import br.com.alma.mybudget.persistencia.CotacaoDAO
import br.com.alma.mybudget.persistencia.ElementoOrcamentarioDAO
import br.com.alma.mybudget.persistencia.LancamentoDAO
import br.com.alma.mybudget.persistencia.LocalizacaoDAO
import br.com.alma.mybudget.persistencia.MoedaDAO
import br.com.alma.mybudget.persistencia.OrcamentoDAO
import br.com.alma.mybudget.persistencia.SujeitoDAO
import br.com.alma.mybudget.persistencia.TipoContaDAO
import br.com.alma.mybudget.persistencia.ValorDAO
import org.junit.After
import org.junit.Before
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

open class AppDatabaseTest {
    @Volatile
    protected var appDatabase: AppDatabase? = null // Volatile para que todas as threads possam ver as alterações

    val localizacaoDAO: LocalizacaoDAO? get() = appDatabase?.localizacaoDAO() // Implementado pelo ROOM

    val sujeitoDAO: SujeitoDAO? get() = appDatabase?.sujeitoDAO() // Implementado pelo ROOM

    val moedaDAO: MoedaDAO? get() = appDatabase?.moedaDAO() // Implementado pelo ROOM

    val cotacaoDAO: CotacaoDAO? get() = appDatabase?.cotacaoDAO() // Implementado pelo ROOM

    val contaDAO: ContaDAO? get() = appDatabase?.contaDAO() // Implementado pelo ROOM

    val tipoContaDAO: TipoContaDAO? get() = appDatabase?.tipoContaDAO() // Implementado pelo ROOM

    val lancamentoDAO: LancamentoDAO? get() = appDatabase?.lancamentoDAO() // Implementado pelo ROOM

    val classeGeralDAO: ClasseGeralDAO? get() = appDatabase?.classeGeralDAO() // Implementado pelo ROOM

    val classeDetalheDAO: ClasseDetalheDAO? get() = appDatabase?.classeDetalheDAO() // Implementado pelo ROOM

    val valorDAO: ValorDAO? get() = appDatabase?.valorDAO() // Implementado pelo ROOM

    val orcamentoDAO: OrcamentoDAO? get() = appDatabase?.orcamentoDAO() // Implementado pelo ROOM

    val elementoOrcamentarioDAO: ElementoOrcamentarioDAO? get() = appDatabase?.elementoOrcamentarioDAO() // Implementado pelo ROOM

    //
    // Executa antes de cada teste
    //
    @Before
    fun init() {
        appDatabase = inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext<Context?>(), AppDatabase::class.java
        ).allowMainThreadQueries().build()
    }

    //
    // Executado após cada teste
    //
    @After
    fun teardown() {
        if (appDatabase != null) {
            if (appDatabase!!.isOpen) {
                appDatabase!!.close()
            }
            appDatabase = null
        }
    }

    companion object {
        private const val NUMBER_OF_THREADS = 4
        protected val databaseWriterExecutor: ExecutorService? = Executors.newFixedThreadPool(NUMBER_OF_THREADS)
    }
}
