package com.example.bytecity.view.AccountPage

import androidx.lifecycle.ViewModel
import com.example.bytecity.model.DbHelper
import com.example.bytecity.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.security.MessageDigest
import java.sql.ResultSet

class AccountViewModel : ViewModel() {

    suspend fun changeEmail(newEmail: String): Int {
        if (!newEmail.trim()
                .matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$".toRegex())
        ) {
            return 3 // WRONG EMAIL
        }
        return try {

            val resultSetEmail: ResultSet
            withContext(Dispatchers.IO){
                resultSetEmail = DbHelper.checkUserEmail(newEmail.trim())

            }
            if(resultSetEmail.isBeforeFirst){
                return 2 // USED EMAIL
            }
            resultSetEmail.close()
            withContext(Dispatchers.IO){
                DbHelper.updateEmail(newEmail.trim())
            }
            User.Id.email = newEmail.trim()
            200
        }
        catch(ex:Exception){
            1 // Err
        }

    }

    suspend fun changePassword(oldPassword: String, newPassword: String): Int {
        return try {
            val bytes =
                MessageDigest.getInstance("SHA-256").digest(oldPassword.trim().toByteArray())
            val passwordHash = bytes.joinToString("") { "%02x".format(it) }
            val resultSetUser =
                withContext(Dispatchers.IO) { DbHelper.getUser(User.Id.login, passwordHash) }
            if (!resultSetUser.isBeforeFirst) {
                return 2 //Wrong old password
            }
            val newBytes =
                MessageDigest.getInstance("SHA-256").digest(newPassword.trim().toByteArray())
            val newPasswordHash = newBytes.joinToString("") { "%02x".format(it) }
            withContext(Dispatchers.IO) { DbHelper.updatePassword(newPasswordHash) }
            200
        } catch (ex: Exception) {
            1 // Err
        }
    }

}