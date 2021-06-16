package cnns.com.example.kotlintestapp


import kotlinx.coroutines.*
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before

class FonctionnementAPI {
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }

    @Test
    fun fetchById_first() = runBlocking {
        var list = ArrayList<DepartementObject>()
        var checkIsSuperior = false
        launch(Dispatchers.Main) {
            try {
                    val response = ApiClient.apiService.getDepartement(19)
                    if (response.isSuccessful && response.body() != null) {
                        val content = response.body()
                        val item = DepartementObject(content?.nom, content?.code, content?.id, content?.codeRegion)
                        list.add(item)
                    }

                } catch (e: Exception) {
                    // e
            }
            print(list.size)
            // API ok si on arrive à avoir la ville en 30/30 + clé API
            if(list.size > 0 ){
                checkIsSuperior = true
            }
            assertEquals(true, checkIsSuperior)

        }
        val FIN_TEST_HOOKER = 1 // pas utilisé
    }
}