package com.example.klinikgigi.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.klinikgigi.modeldata.Tindakan
import com.example.klinikgigi.repository.RepositoryKlinik
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TindakanViewModel(private val repo: RepositoryKlinik) : ViewModel() {

    private val _tindakanList = MutableStateFlow<List<Tindakan>>(emptyList())
    val tindakanList: StateFlow<List<Tindakan>> = _tindakanList

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    private val _selectedTindakan = MutableStateFlow<Tindakan?>(null)
    val selectedTindakan: StateFlow<Tindakan?> = _selectedTindakan

    init {
        loadTindakan()
    }

    /** ======================= LOAD SEMUA TINDAKAN ======================= */
    fun loadTindakan() {
        viewModelScope.launch {
            _loading.value = true
            try {
                _tindakanList.value = repo.getTindakan()
            } finally {
                _loading.value = false
            }
        }
    }

    /** ======================= LOAD 1 TINDAKAN BY ID ======================= */
    fun loadTindakanById(id: Int) {
        viewModelScope.launch {
            _loading.value = true
            try {
                // Ambil data langsung dari repository agar selalu fresh & lengkap
                val list = repo.getTindakan()
                _selectedTindakan.value = list.find { it.id_tindakan == id }
            } finally {
                _loading.value = false
            }
        }
    }

    /** ======================= BUAT TINDAKAN BARU ======================= */
    fun createTindakan(data: Tindakan) {
        viewModelScope.launch {
            _loading.value = true
            try {
                repo.createTindakan(data)
                loadTindakan() // refresh list
            } finally {
                _loading.value = false
            }
        }
    }

    /** ======================= UPDATE TINDAKAN ======================= */
    fun updateTindakan(data: Tindakan) {
        viewModelScope.launch {
            _loading.value = true
            try {
                repo.updateTindakan(data)
                loadTindakan() // refresh list
                _selectedTindakan.value = data // simpan data terupdate
            } finally {
                _loading.value = false
            }
        }
    }

    /** ======================= HAPUS TINDAKAN ======================= */
    fun deleteTindakan(id: Int) {
        viewModelScope.launch {
            _loading.value = true
            try {
                repo.deleteTindakan(id)
                loadTindakan() // refresh list
            } finally {
                _loading.value = false
            }
        }
    }
}