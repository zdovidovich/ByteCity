package com.example.bytecity.view.MakeReviewPage

import androidx.lifecycle.ViewModel
import com.example.bytecity.model.DbHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MakeReviewViewModel : ViewModel() {

    suspend fun uploadReview(
        idProduct: Int,
        rating: Float, text: String
    ): Int {
        return try {
            withContext(Dispatchers.IO) {
                DbHelper.uploadReview(idProduct, rating, text)
            }
            200
        } catch (ex: Exception) {
            1
        }
    }
}