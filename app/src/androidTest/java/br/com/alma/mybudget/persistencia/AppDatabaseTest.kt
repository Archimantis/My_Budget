package br.com.alma.mybudget.persistencia

import androidx.room.Room.databaseBuilder
import androidx.test.core.app.ApplicationProvider
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import kotlin.concurrent.Volatile

open class AppDatabaseTest {
    @Volatile
    protected var appDatabase: AppDatabase? = null // Volatile para que todas as threads possam ver as alterações
    val localizacaoDAO: LocalizacaoDAO?
        //
        get() = appDatabase!!.localizacaoDAO() // Implementado pelo ROOM

    val sujeitoDAO: SujeitoDAO?
        //
        get() = appDatabase!!.sujeitoDAO() // Implementado pelo ROOM

    val moedaDAO: MoedaDAO?
        //
        get() = appDatabase!!.moedaDAO() // Implementado pelo ROOM

    val cotacaoDAO: CotacaoDAO?
        //
        get() = appDatabase!!.cotacaoDAO() // Implementado pelo ROOM

    val contaDAO: ContaDAO?
        //
        get() = appDatabase!!.contaDAO() // Implementado pelo ROOM

    val tipoContaDAO: TipoContaDAO?
        //
        get() = appDatabase!!.tipoContaDAO() // Implementado pelo ROOM

    val lancamentoDAO: LancamentoDAO?
        //
        get() = appDatabase!!.lancamentoDAO() // Implementado pelo ROOM

    val classeGeralDAO: ClasseGeralDAO
        //
        get() = appDatabase!!.classeGeralDAO() // Implementado pelo ROOM

    val classeDetalheDAO: ClasseDetalheDAO
        //
        get() = appDatabase!!.classeDetalheDAO() // Implementado pelo ROOM

    val valorDAO: ValorDAO?
        //
        get() = appDatabase!!.valorDAO() // Implementado pelo ROOM

    val orcamentoDAO: OrcamentoDAO?
        //ppDatabase::class.java
        get() = appDatabase!!.orcamentoDAO() // Implementado pelo ROOM

    val elementoOrcamentarioDAO: ElementoOrcamentarioDAO?
        //
        get() = appDatabase!!.elementoOrcamentarioDAO() // Implementado pelo ROOM

    //
    // Executa antes de cada teste
    //
    @BeforeEach
    fun init() {
//       appDatabase = inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext(), AppDatabase::class.java)
//            .allowMainThreadQueries().build()
        appDatabase = databaseBuilder(
            context = ApplicationProvider.getApplicationContext(),
            klass = AppDatabase::class.java,
            name = "TesteDB"
        )
            .build()
    }

    //
    // Executado após cada teste
    //
    @AfterEach
    fun teardown() {
        if (appDatabase != null) {
            if (appDatabase!!.isOpen) appDatabase!!.close()
            appDatabase = null
        }
    }

    companion object {
//        private const val NUMBER_OF_THREADS = 4
//        private val databaseWriterExecutor: ExecutorService = Executors.newFixedThreadPool(
//            NUMBER_OF_THREADS
//        )
    }
}

