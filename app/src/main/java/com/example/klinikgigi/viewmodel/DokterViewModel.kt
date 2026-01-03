//package com.example.klinikgigi.viewmodel
//
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import com.example.klinikgigi.modeldata.JanjiTemu
//import com.example.klinikgigi.repository.RepositoryKlinik
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.StateFlow
//import kotlinx.coroutines.launch
//
//class DokterViewModel(private val repository: RepositoryKlinik) : ViewModel() {
//
//    private val _janjiTemuList = MutableStateFlow<List<JanjiTemu>>(emptyList())
//    val janjiTemuList: StateFlow<List<JanjiTemu>> get() = _janjiTemuList
//
//    private val _loading = MutableStateFlow(false)
//    val loading: StateFlow<Boolean> get() = _loading
//
//    init {
//        loadJanjiTemu()
//    }
//
//    // Ambil daftar janji temu dari repository
//    fun loadJanjiTemu() {
//        viewModelScope.launch {
//            _loading.value = true
//            try {
//                _janjiTemuList.value = repository.getJanjiTemu()
//            } finally {
//                _loading.value = false
//            }
//        }
//    }
//}
