package br.com.alma.mybudget.persistencia

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.alma.mybudget.modelo.Localizacao

class LocalizacaoRepository internal constructor(application: Application?) {
    private var localizacaoDAO: LocalizacaoDAO? = null
    private var todasAsLocalizacoes: LiveData<List<Localizacao>>? = null
    private val localizacoesDeUmSujeito: LiveData<List<Localizacao>>? = null
    private val proxima = 0

    // Note that in order to unit test the LocalzacaoRepository, you have to remove the Application
    // dependency. This adds complexity and much more code, and this sample is not about testing.
    // See the BasicSample in the android-architecture-components repository at
    // https://github.com/googlesamples
    init {
        val appDatabase: AppDatabase? = AppDatabase.getDatabase(application)
        if (appDatabase != null) {
            localizacaoDAO = appDatabase.localizacaoDAO()!!
            todasAsLocalizacoes =
                MutableLiveData<List<Localizacao>>(this.localizacaoDAO!!.getAllLocalizacoes() )
        }
    }

    val allLocalizacaos: LiveData<List<Localizacao>>?
        // Room executes all queries on a separate thread.
        get() = todasAsLocalizacoes

    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
    fun insert(localizacao: Localizacao?) {
        AppDatabase.databaseWriteExecutor.execute { localizacaoDAO?.insert(localizacao) }
    }
}