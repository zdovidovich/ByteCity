package com.example.bytecity.view.OrderPage

import androidx.lifecycle.ViewModel
import com.example.bytecity.model.DbHelper

class OrderViewModel : ViewModel() {

    suspend fun checkReview(idProduct: Int): Int {
        return try {
            val resultSetReview = DbHelper.checkUserReview(idProduct)
            if (!resultSetReview.isBeforeFirst) {
                resultSetReview.close()
                return 200
            }
            resultSetReview.close()
            1
        } catch (ex: Exception) {
            2 //smth's bad
        }

    }

}