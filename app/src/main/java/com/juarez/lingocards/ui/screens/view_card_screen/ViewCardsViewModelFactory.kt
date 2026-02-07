package com.juarez.lingocards.ui.screens.view_card_screen

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.juarez.lingocards.LingoCardsApp

class ViewCardsViewModelFactory(
    private val app: Application
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val lingoApp = app as LingoCardsApp
        val repo = CardsRepository(lingoApp.db.cardDao())

        @Suppress("UNCHECKED_CAST")
        return ViewCardsViewModel(app, repo) as T
    }
}