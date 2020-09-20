package com.example.belajar.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.belajar.core.data.source.TourismRepository

class HomeViewModel(tourismRepository: TourismRepository) : ViewModel() {
    val tourism = tourismRepository.getAllTourism()
}