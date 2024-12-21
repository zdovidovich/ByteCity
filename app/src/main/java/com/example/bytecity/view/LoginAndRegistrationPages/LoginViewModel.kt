package com.example.bytecity.view.LoginAndRegistrationPages

import androidx.lifecycle.ViewModel
import com.example.bytecity.model.Db
import com.example.bytecity.model.User
import java.security.MessageDigest

class LoginViewModel : ViewModel() {

    fun checkData(login: String, password: String): Int {
        when{
            login.isEmpty() ->
                return 0 // Empty login
            password.isEmpty() ->
                return 1 // Empty password
        }


        val bytes = MessageDigest.getInstance("SHA-256").digest(password.trim().toByteArray())
        val passwordHash = bytes.joinToString("") { "%02x".format(it) }

        val resultSetUser = Db.getUser(login.trim(), passwordHash)

        if(!resultSetUser.isBeforeFirst)
        {
            resultSetUser.close()
            return 2 //Wrong login or password
        }

        while (resultSetUser.next()){
            if(User.Id.id != -1){
                User.Id.exit()
                resultSetUser.close()
                return 3 // Too many
            }
            User.Id.id = resultSetUser.getInt("idUser")
            User.Id.login = resultSetUser.getString("login")
            User.Id.email = resultSetUser.getString("email")
            User.Id.contactNumber = resultSetUser.getString("contactNumber")
        }

        resultSetUser.close()

        return 200

    }

}