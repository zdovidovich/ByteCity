package com.example.bytecity.view.OrderPage

import androidx.lifecycle.ViewModel
import com.example.bytecity.model.Db
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.sql.ResultSet

class OrderViewModel: ViewModel() {

    suspend fun checkReview(idProduct:Int):Int{
        return try{
            val resultSetReview: ResultSet
            withContext(Dispatchers.IO){
                resultSetReview = Db.checkUserReview(idProduct)}
                if(!resultSetReview.isBeforeFirst){
                    resultSetReview.close()
                    return 200
                }
                resultSetReview.close()
            1
        }
        catch (ex:Exception){
            2 //smth's bad
        }

    }

}