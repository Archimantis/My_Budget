package br.com.alma.meustrocados.persistencia;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;

import org.junit.After;
import org.junit.Before;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AppDatabaseTest {
    protected volatile AppDatabase appDatabase; // Volatile para que todas as threads possam ver as alterações
    private static final int NUMBER_OF_THREADS = 4;
    protected static final ExecutorService databaseWriterExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    //
    // Fornece acesso ao LocalizacaoDAO
    //
    public LocalizacaoDAO getLocalizacaoDAO() {
        return appDatabase.localizacaoDAO(); // Implementado pelo ROOM
    }

    //
    // Fornece acesso ao SujeitoDAO
    //
    public SujeitoDAO getSujeitoDAO() {
        return appDatabase.sujeitoDAO(); // Implementado pelo ROOM
    }

    //
    // Fornece acesso ao MoedaDAO
    //
    public MoedaDAO getMoedaDAO() {
        return appDatabase.moedaDAO(); // Implementado pelo ROOM
    }

    //
    // Fornece acesso ao CotacaoDAO
    //
    public CotacaoDAO getCotacaoDAO() {
        return appDatabase.cotacaoDAO(); // Implementado pelo ROOM
    }

    //
    // Fornece acesso ao ContaDAO
    //
    public ContaDAO getContaDAO() {
        return appDatabase.contaDAO(); // Implementado pelo ROOM
    }

    //
    // Fornece acesso ao TipoContaDAO
    //
    public TipoContaDAO getTipoContaDAO() {
        return appDatabase.tipoContaDAO(); // Implementado pelo ROOM
    }

    //
    // Fornece acesso ao LancamentoDAO
    //
    public LancamentoDAO getLancamentoDAO() {
        return appDatabase.lancamentoDAO(); // Implementado pelo ROOM
    }

    //
    // Fornece acesso ao ClasseGeralDAO
    //
    public ClasseGeralDAO getClasseGeralDAO() {
        return appDatabase.classeGeralDAO(); // Implementado pelo ROOM
    }

    //
    // Fornece acesso ao ClasseDetalheDAO
    //
    public ClasseDetalheDAO getClasseDetalheDAO() {
        return appDatabase.classeDetalheDAO(); // Implementado pelo ROOM
    }

    //
    // Fornece acesso ao Valor
    //
    public ValorDAO getValorDAO() {
        return appDatabase.valorDAO(); // Implementado pelo ROOM
    }

    //
    // Fornece acesso ao OrcamentoDAO
    //
    public OrcamentoDAO getOrcamentoDAO() {
        return appDatabase.orcamentoDAO(); // Implementado pelo ROOM
    }

    //
    // Fornece acesso ao ElementoOrcamentarioDAO
    //
    public ElementoOrcamentarioDAO getElementoOrcamentarioDAO() {
        return appDatabase.elementoOrcamentarioDAO(); // Implementado pelo ROOM
    }

    //
    // Executa antes de cada teste
    //
    @Before
    public void init() {
        appDatabase = Room.inMemoryDatabaseBuilder(
                ApplicationProvider.getApplicationContext(), AppDatabase.class
        ).allowMainThreadQueries().build();
    }

    //
    // Executado após cada teste
    //
    @After
    public void teardown() {
        if (appDatabase != null) {
            if (appDatabase.isOpen()) {
                appDatabase.close();
            }
            appDatabase = null;
        }
    }
}
