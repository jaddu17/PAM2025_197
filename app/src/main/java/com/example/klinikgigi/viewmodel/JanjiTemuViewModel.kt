package com.example.klinikgigi.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.klinikgigi.modeldata.Dokter
import com.example.klinikgigi.modeldata.JanjiTemu
import com.example.klinikgigi.modeldata.Pasien
import com.example.klinikgigi.repository.RepositoryKlinik
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class JanjiTemuViewModel(
    private val repository: RepositoryKlinik
) : ViewModel() {

    private val _janjiList = MutableStateFlow<List<JanjiTemu>>(emptyList())
    val janjiList: StateFlow<List<JanjiTemu>> = _janjiList

    private val _pasienList = MutableStateFlow<List<Pasien>>(emptyList())
    val pasienList: StateFlow<List<Pasien>> = _pasienList

    private val _dokterList = MutableStateFlow<List<Dokter>>(emptyList())
    val dokterList: StateFlow<List<Dokter>> = _dokterList

    // ✅ TAMBAHAN: untuk menyimpan janji yang sedang diedit (seperti selectedPasien)
    private val _selectedJanji = MutableStateFlow<JanjiTemu?>(null)
    val selectedJanji: StateFlow<JanjiTemu?> = _selectedJanji

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    private val _status = MutableStateFlow<String?>(null)
    val status: StateFlow<String?> = _status

    init {
        loadAll()
    }

    private fun loadAll() {
        loadJanji()
        loadPasien()
        loadDokter()
    }

    fun loadJanji() {
        viewModelScope.launch {
            _loading.value = true
            try {
                _janjiList.value = repository.getJanjiTemu()
            } finally {
                _loading.value = false
            }
        }
    }

    private fun loadPasien() {
        viewModelScope.launch {
            _pasienList.value = repository.getPasien()
        }
    }

    private fun loadDokter() {
        viewModelScope.launch {
            _dokterList.value = repository.getDokter()
        }
    }

    // ✅ SAMA SEPERTI loadPasienById — LOAD SATU JANJI TEMU
    fun loadJanjiById(id: Int) {
        viewModelScope.launch {
            _loading.value = true
            try {
                val list = repository.getJanjiTemu()
                val janji = list.find { it.id == id }
                _selectedJanji.value = janji
            } finally {
                _loading.value = false
            }
        }
    }

    fun createJanjiTemu(janji: JanjiTemu) {
        viewModelScope.launch {
            _status.value = "loading"
            try {
                repository.createJanjiTemu(janji)
                loadJanji()
                _status.value = "Berhasil menambah janji"
            } catch (e: Exception) {
                _status.value = "Gagal menambah"
            }
        }
    }

    fun updateJanjiTemu(janji: JanjiTemu) {
        viewModelScope.launch {
            _status.value = "loading"
            try {
                repository.updateJanjiTemu(janji)
                loadJanji()
                _selectedJanji.value = janji // ✅ simpan yang terupdate
                _status.value = "Berhasil update janji"
            } catch (e: Exception) {
                _status.value = "Gagal update"
            }
        }
    }

    fun deleteJanji(id: Int) {
        viewModelScope.launch {
            try {
                repository.deleteJanjiTemu(id)
                loadJanji()
            } catch (_: Exception) {}
        }
    }

    fun clearStatus() {
        _status.value = null
    }
}