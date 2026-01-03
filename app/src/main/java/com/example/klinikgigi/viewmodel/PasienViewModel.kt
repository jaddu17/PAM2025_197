package com.example.klinikgigi.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.klinikgigi.modeldata.Pasien
import com.example.klinikgigi.repository.RepositoryKlinik
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PasienViewModel(private val repository: RepositoryKlinik) : ViewModel() {

    private val _pasienList = MutableStateFlow<List<Pasien>>(emptyList())
    val pasienList: StateFlow<List<Pasien>> get() = _pasienList

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> get() = _loading

    private val _selectedPasien = MutableStateFlow<Pasien?>(null)
    val selectedPasien: StateFlow<Pasien?> get() = _selectedPasien

    init {
        loadPasien()
    }

    /** ======================= LOAD SEMUA PASIEN ======================= */
    fun loadPasien() {
        viewModelScope.launch {
            _loading.value = true
            try {
                _pasienList.value = repository.getPasien()
            } finally {
                _loading.value = false
            }
        }
    }

    /** ======================= LOAD 1 PASIEN BY ID ======================= */
    fun loadPasienById(id: Int) {
        viewModelScope.launch {
            _loading.value = true
            try {
                val list = repository.getPasien()
                val pasien = list.find { it.id_pasien == id }
                _selectedPasien.value = pasien
            } finally {
                _loading.value = false
            }
        }
    }

    /** ======================= CREATE PASIEN ======================= */
    fun createPasien(pasien: Pasien) {
        viewModelScope.launch {
            _loading.value = true
            try {
                repository.createPasien(pasien)
                loadPasien()   // refresh list
            } finally {
                _loading.value = false
            }
        }
    }

    /** ======================= UPDATE PASIEN ======================= */
    fun updatePasien(pasien: Pasien) {
        viewModelScope.launch {
            _loading.value = true
            try {
                repository.updatePasien(pasien)
                loadPasien() // refresh list
                _selectedPasien.value = pasien // perbaikan: simpan pasien terupdate
            } finally {
                _loading.value = false
            }
        }
    }

    /** ======================= HAPUS PASIEN ======================= */
    fun deletePasien(id: Int) {
        viewModelScope.launch {
            _loading.value = true
            try {
                repository.deletePasien(id)
                loadPasien()
            } finally {
                _loading.value = false
            }
        }
    }
}
