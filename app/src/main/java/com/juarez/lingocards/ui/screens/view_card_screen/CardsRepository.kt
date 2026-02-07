package com.juarez.lingocards.ui.screens.view_card_screen

import com.juarez.lingocards.data.database.dao.CardDao

class CardsRepository(
    private val cardDao: CardDao
) {
    suspend fun getAllCardsEsDe(): List<CardDao.CardRow> {
        return cardDao.getAllCardsEsDe()
    }
}