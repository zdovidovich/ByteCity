package com.example.bytecity.view.LoginAndRegistrationPages

import androidx.lifecycle.ViewModel
import com.example.bytecity.model.Db
import java.security.MessageDigest

class RegistrationViewModel : ViewModel() {


    fun addUser(login: String, email: String, contactNumber: String, password: String): Int {

        when {
            login.isEmpty() -> {
                return 6 //EMPTY LOGIN
            }
            !email.trim().matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$".toRegex()) -> {
                return 3 // WRONG EMAIL
            }
            !contactNumber.trim().matches("^\\+[0-9]{12}$".toRegex()) -> {
                return 4 // WRONG NUMBER
            }
            password.trim().length < 8 -> {
                return 2 // TOO SHORT PASSWORD
            }
        }

        val resultSetEmail = Db.checkUserEmail(email.trim())
        if (resultSetEmail.isBeforeFirst) {
            resultSetEmail.close()
            return 0 // USED EMAIL
        }
        resultSetEmail.close()

        val resultSetLogin = Db.checkUserLogin(login.trim())
        if (resultSetLogin.isBeforeFirst) {
            resultSetLogin.close()
            return 1 // USED LOGIN
        }
        resultSetLogin.close()

        val resultSetContactNumber = Db.checkUserContactNumber(email.trim())
        if (resultSetContactNumber.isBeforeFirst) {
            resultSetContactNumber.close()
            return 5 // USED NUMBER
        }
        resultSetContactNumber.close()


        val bytes = MessageDigest.getInstance("SHA-256").digest(password.trim().toByteArray())
        val passwordHash = bytes.joinToString("") { "%02x".format(it) }

        Db.addUser(
            login = login.trim(),
            password = passwordHash,
            email = email.trim(),
            contactNumber = contactNumber.trim()
        )

        return 200
    }

}