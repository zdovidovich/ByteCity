package com.example.bytecity.view.MakeReviewPage

import androidx.lifecycle.ViewModel
import com.example.bytecity.model.DbHelper

class MakeReviewViewModel : ViewModel() {

    suspend fun uploadReview(
        idProduct: Int,
        rating: Float, text: String
    ): Int {
        return try {
            DbHelper.uploadReview(idProduct, rating, text.trim())
            200
        } catch (ex: Exception) {
            1
        }
    }
}
