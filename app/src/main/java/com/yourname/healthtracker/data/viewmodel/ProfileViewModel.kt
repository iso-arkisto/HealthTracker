package com.yourname.healthtracker.data.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yourname.healthtracker.data.repository.MainRepository
import com.yourname.healthtracker.data.repository.ProfileRepository
import com.yourname.healthtracker.data.room.dao.UserProfileDao
import com.yourname.healthtracker.data.room.entities.FoodDay
import com.yourname.healthtracker.data.room.entities.UserProfile
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository: MainRepository,
    private val profileRepository: ProfileRepository
): ViewModel() {
    private val _userProfile = MutableStateFlow(UserProfile())

    val userProfile = _userProfile.asStateFlow()

    fun loadProfile() {
        viewModelScope.launch {
            val profile = profileRepository.getOrCreateProfile(0)
            _userProfile.value = profile
        }
    }

    fun updateProfile(profile: UserProfile) {
        _userProfile.value = profile
        viewModelScope.launch {
            profileRepository.insertItem(profile)
        }
    }

    fun addRecentProduct(id: Int) {
        val profile = _userProfile.value
        val newList = profile.recentProducts.toMutableList()

        if(newList.contains(id)) {
            newList.removeAll(listOf(id))
        }

        newList.add(0, id)
        newList.take(10)

        _userProfile.value = profile.copy(
            recentProducts = newList
        )

        viewModelScope.launch {
            profileRepository.insertItem(userProfile.value)
        }
    }
}