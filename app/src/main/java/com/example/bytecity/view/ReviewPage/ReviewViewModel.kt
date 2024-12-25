package com.example.bytecity.view.ReviewPage

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bytecity.businessClasses.Review
import com.example.bytecity.model.DbHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class ReviewViewModel : ViewModel() {

    private val _reviewState = mutableStateOf(ReviewState())
    val reviewState: State<ReviewState> = _reviewState

    fun getReviews(idProduct: Int) {
        try {
            runBlocking {
                viewModelScope.launch(Dispatchers.IO) {
                    val resultSetReviews = DbHelper.getReviews(idProduct)
                    val resReviews = mutableListOf<Review>()
                    while (resultSetReviews.next()) {
                        val idUser = resultSetReviews.getInt("idUser")
                        val resultSetUser = DbHelper.getUser(idUser)
                        resultSetUser.next()
                        resReviews.add(
                            Review(
                                idProduct = resultSetReviews.getInt("idProduct"),
                                idUser = idUser,
                                login = resultSetUser.getString("login"),
                                rating = resultSetReviews.getDouble("rating"),
                                review = resultSetReviews.getString("review")
                            )
                        )
                    }
                    resultSetReviews.close()
                    _reviewState.value = _reviewState.value.copy(
                        loading = false,
                        reviews = resReviews
                    )
                }
            }

        } catch (ex: Exception) {
            _reviewState.value = _reviewState.value.copy(
                loading = false,
                error = ex.message
            )

        }

    }


    data class ReviewState(
        val loading: Boolean = true,
        val error: String? = null,
        val reviews: List<Review> = listOf()
    )

}