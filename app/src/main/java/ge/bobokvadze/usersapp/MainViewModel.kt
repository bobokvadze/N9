package ge.bobokvadze.usersapp

import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.launch
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.MutableStateFlow
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel
class MainViewModel @Inject constructor(
    private val communication: Communication,
    private val userRepository: UserRepository
) : ViewModel() {

    init {
        viewModelScope.launch {
            communication.map(UsersUi.SuccessUi(userRepository.readUsers()))
        }
    }

    fun save(text: String) {
        userRepository.save(text)
    }

    fun collect(
        collector: FlowCollector<UsersUi>
    ) = viewModelScope.launch {
        communication.collect(collector)
    }
}

interface UserRepository {

    fun save(text: String)

    fun readUsers(): MutableList<String>

    @Singleton
    class Base: UserRepository {

        private val usersList: MutableList<String> = mutableListOf()

        override fun save(text: String) {
            usersList.add(text)
        }

        override fun readUsers() = usersList
    }
}

interface Communication {

    fun map(data: UsersUi)
    suspend fun collect(collector: FlowCollector<UsersUi>)

    class Base : Communication {

        private val state = MutableStateFlow<UsersUi>(UsersUi.Empty)

        override fun map(data: UsersUi) {
            state.value = data
        }

        override suspend fun collect(collector: FlowCollector<UsersUi>) {
            state.collect(collector)
        }
    }
}
