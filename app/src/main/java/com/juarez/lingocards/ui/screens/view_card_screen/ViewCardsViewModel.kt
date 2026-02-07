package com.juarez.lingocards.ui.screens.view_card_screen

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.juarez.lingocards.ui.screens.card_list.CardUiModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ViewCardsViewModel(
    app: Application,
    private val repo: CardsRepository
) : AndroidViewModel(app) {

    private val _uiState = MutableStateFlow(ViewCardsUiState())
    val uiState: StateFlow<ViewCardsUiState> = _uiState

    init {
        loadCards()
    }

    fun loadCards() {
        _uiState.update { it.copy(isLoading = true, error = null) }

        viewModelScope.launch {
            try {
                val rows = withContext(Dispatchers.IO) {
                    repo.getAllCardsEsDe()
                }

                val context = getApplication<Application>()
                val mapped = rows.map { r ->
                    val resId = context.resources.getIdentifier(
                        r.imageResId, // nombre drawable: "cat_01"
                        "drawable",
                        context.packageName
                    )
                    CardUiModel(
                        id = r.id,
                        frontText = r.frontText,
                        backText = r.backText,
                        imageResId = resId
                    )
                }

                _uiState.update { it.copy(isLoading = false, cards = mapped) }
            } catch (t: Throwable) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = t.message ?: "Error cargando cartas"
                    )
                }
            }
        }
    }
}