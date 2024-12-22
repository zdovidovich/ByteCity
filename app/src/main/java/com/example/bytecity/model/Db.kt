package com.example.bytecity.model

import android.util.Log
import com.example.bytecity.businessClasses.Product
import com.example.bytecity.businessClasses.ProductForCart
import java.sql.Connection
import java.sql.Date
import java.sql.ResultSet


class Db {

    companion object {
        fun checkUserEmail(email: String, connection: DbConnection = DbConn): ResultSet {
            val statement =
                connection.connection.prepareStatement("SELECT * FROM User WHERE email = ?")
                    .apply {
                        setString(1, email)
                    }

            return statement.executeQuery()

        }

        fun checkUserLogin(login: String, connection: DbConnection = DbConn): ResultSet {
            val statement = connection.connection.prepareStatement("SELECT * FROM User WHERE login = ?")
                .apply {
                    setString(1, login)
                }
            return statement.executeQuery()

        }

        fun checkUserContactNumber(contactNumber: String, connection: DbConnection = DbConn): ResultSet {
            val statement =
                connection.connection.prepareStatement("SELECT * FROM User WHERE contactNumber = ?")
                    .apply {
                        setString(1, contactNumber)
                    }
            return statement.executeQuery()
        }


        fun addUser(login: String, password: String, email: String, contactNumber: String, connection: DbConnection = DbConn): Int {
            val query =
                "INSERT INTO User (login, password, email, contactNumber) VALUES (?, ?, ?, ?)"
            val statement = connection.connection.prepareStatement(query).apply {
                setString(1, login)
                setString(2, password)
                setString(3, email)
                setString(4, contactNumber)
            }
            return statement.executeUpdate()

        }

        fun getUser(login: String, password: String, connection: DbConnection = DbConn): ResultSet {
            val query =
                "SELECT idUser, login, email, contactNumber FROM User WHERE login = ? AND password = ?"
            val statement = connection.connection.prepareStatement(query).apply {
                setString(1, login)
                setString(2, password)
            }
            return statement.executeQuery()

        }

        fun getUser(idUser:Int, connection: DbConnection = DbConn): ResultSet {
            val query =
                "SELECT idUser, login, email, contactNumber FROM User WHERE idUser = ?"
            val statement = connection.connection.prepareStatement(query).apply {
                setInt(1, idUser)
            }
            return statement.executeQuery()

        }


        fun getColumnsAndCommentsInfo(category: String, connection: DbConnection = DbConn): ResultSet {
            val query =
                "SELECT column_name,column_comment FROM information_schema.columns WHERE table_schema='ByteCity' and table_name=?"
            val statement = connection.connection.prepareStatement(query).apply {
                setString(1, category)
            }
            return statement.executeQuery()
        }

        fun getInfo(product: Product, connection: DbConnection = DbConn): ResultSet {
            val table = product.type
            val column = "id${product.type}"
            val query = "SELECT * FROM $table WHERE $column = ?"
            val statement = connection.connection.prepareStatement(query).apply {
                setInt(1, product.idProduct)
            }
            return statement.executeQuery()
        }

        fun addProductToWishList(product: Product, connection: DbConnection = DbConn): Int {
            val query = "INSERT INTO Favourite VALUES (?, ?)"
            val statement = connection.connection.prepareStatement(query).apply {
                setInt(1, User.Id.id)
                setInt(2, product.idProduct)
            }
            return statement.executeUpdate()
        }

        fun addProductToCart(product: Product, connection: DbConnection = DbConn): Int {
            val query = "INSERT INTO Cart VALUES (?, ?, 1)"
            val statement = connection.connection.prepareStatement(query).apply {
                setInt(1, User.Id.id)
                setInt(2, product.idProduct)
            }
            return statement.executeUpdate()
        }

        fun getProductWishList(product: Product, connection: DbConnection = DbConn): ResultSet {
            val query = "SELECT * FROM Favourite WHERE idUser = ? AND idProduct = ?"
            val statement = connection.connection.prepareStatement(query).apply {
                setInt(1, User.Id.id)
                setInt(2, product.idProduct)
            }
            return statement.executeQuery()
        }

        fun getAllProductWishList(connection: DbConnection = DbConn): ResultSet {
            val query = "SELECT idProduct FROM Favourite WHERE idUser = ?"
            val statement = connection.connection.prepareStatement(query).apply {
                setInt(1, User.Id.id)
            }
            return statement.executeQuery()
        }

        fun getAllProductCart(connection: DbConnection = DbConn): ResultSet {
            val query = "SELECT * FROM Cart WHERE idUser = ?"
            val statement = connection.connection.prepareStatement(query).apply {
                setInt(1, User.Id.id)
            }
            return statement.executeQuery()
        }

        fun cleanCart(connection: DbConnection = DbConn): Int {
            val query = "DELETE FROM Cart WHERE idUser = ?"
            val statement = connection.connection.prepareStatement(query).apply {
                setInt(1, User.Id.id)
            }
            return statement.executeUpdate()
        }

        fun getProductCart(product: Product, connection: DbConnection = DbConn): ResultSet {
            val query = "SELECT * FROM Cart WHERE idUser = ? AND idProduct = ?"
            val statement = connection.connection.prepareStatement(query).apply {
                setInt(1, User.Id.id)
                setInt(2, product.idProduct)
            }
            return statement.executeQuery()
        }

        fun deleteProductWishList(product: Product, connection: DbConnection = DbConn): Int {
            val query = "DELETE FROM Favourite WHERE idUser = ? AND idProduct = ?"
            val statement = connection.connection.prepareStatement(query).apply {
                setInt(1, User.Id.id)
                setInt(2, product.idProduct)
            }
            return statement.executeUpdate()
        }


        fun deleteProductCart(product: Product, connection: DbConnection = DbConn): Int {
            return deleteProductCart(product.idProduct, connection)
        }

        fun deleteProductCart(idProduct: Int, connection: DbConnection = DbConn): Int {
            val query = "DELETE FROM Cart WHERE idUser = ? AND idProduct = ?"
            val statement = connection.connection.prepareStatement(query).apply {
                setInt(1, User.Id.id)
                setInt(2, idProduct)
            }
            return statement.executeUpdate()
        }

        fun getProducts(
            connection: DbConnection = DbConn,
            category: String, limit: Int, offset: Int
        ): ResultSet {
            val query =
                "SELECT * FROM Product WHERE type = ? AND inStock > 0 LIMIT $limit OFFSET $offset"
            val statement = connection.connection.prepareStatement(query).apply {
                setString(1, category)
            }
            return statement.executeQuery()
        }

        fun getRating(product: Product, connection: DbConnection = DbConn): ResultSet {
            val query = "SELECT SUM(rating) / COUNT(rating) AS res FROM Review WHERE idProduct = ?"
            val statement = connection.connection.prepareStatement(query).apply {
                setInt(1, product.idProduct)
            }
            return statement.executeQuery()
        }

        fun getInfoAboutCooling(product: Product, connection: DbConnection = DbConn): ResultSet {
            val query =
                "SELECT GROUP_CONCAT(socket) AS socket FROM CoolingSockets WHERE idCooling = ? GROUP BY idCooling;"
            val statement = connection.connection.prepareStatement(query).apply {
                setInt(1, product.idProduct)
            }
            return statement.executeQuery()
        }

        fun getName(id: Int, connection: DbConnection = DbConn): ResultSet {
            val query =
                "SELECT CONCAT_WS(' ', brand, model) as res FROM Product WHERE idProduct = ?"
            val statement = connection.connection.prepareStatement(query).apply {
                setInt(1, id)
            }
            return statement.executeQuery()
        }

        private fun getProductInSomething(product: Product, something: String, connection: DbConnection = DbConn): ResultSet {
            val query = "SELECT idProduct FROM $something WHERE idProduct = ? AND idUser = ?"
            val statement = connection.connection.prepareStatement(query).apply {
                setInt(1, product.idProduct)
                setInt(2, User.Id.id)
            }
            return statement.executeQuery()

        }

        fun getProductInFavourite(product: Product, connection: DbConnection = DbConn): ResultSet {
            return getProductInSomething(product, "Favourite", connection)
        }

        fun getProductInCart(product: Product, connection: DbConnection = DbConn): ResultSet {
            return getProductInSomething(product, "Cart", connection)
        }

        fun getProductById(id: Int, connection: DbConnection = DbConn): ResultSet {
            val query = "SELECT * FROM Product WHERE idProduct = ?"
            val statement = connection.connection.prepareStatement(query).apply {
                setInt(1, id)
            }
            return statement.executeQuery()
        }


        fun getProductById(
            id: Int,
            limit: Int,
            offset: Int,
            connection: DbConnection = DbConn
        ): ResultSet {
            val query = "SELECT * FROM Product WHERE idProduct = ? LIMIT $limit OFFSET $offset"
            val statement = connection.connection.prepareStatement(query).apply {
                setInt(1, id)
            }
            return statement.executeQuery()
        }

        fun updateProductCartPlusQty(product: Product, connection: DbConnection = DbConn): Int {
            val query =
                "UPDATE Cart SET quantity = quantity + 1 WHERE idProduct = ? AND idUser = ?"
            val statement = connection.connection.prepareStatement(query).apply {
                setInt(1, product.idProduct)
                setInt(2, User.Id.id)
            }
            return statement.executeUpdate()
        }

        fun updateProductCartMinusQty(product: Product, connection: DbConnection = DbConn): Int {
            val query =
                "UPDATE Cart SET quantity = quantity - 1 WHERE idProduct = ? AND idUser = ?"
            val statement = connection.connection.prepareStatement(query).apply {
                setInt(1, product.idProduct)
                setInt(2, User.Id.id)
            }
            return statement.executeUpdate()
        }

        fun insertOrder(
            productsForCart: List<ProductForCart>,
            name: String,
            date: Date,
            connection: DbConnection = DbConn
        ): List<Int> {
            val res = mutableListOf<Int>()
            connection.connection.autoCommit = false
            connection.connection.transactionIsolation = Connection.TRANSACTION_REPEATABLE_READ

            val queryFirst =
                "INSERT INTO OrderDetails (registrationDate, status, name) VALUES (?, ?, ?)"
            val statementFirst = connection.connection.prepareStatement(queryFirst).apply {
                setDate(1, date)
                setString(2, "Оформлен")
                setString(3, name)
            }

            res.add(statementFirst.executeUpdate())
            val querySecond =
                "SELECT idOrder FROM OrderDetails where registrationDate = ? AND name = ? ORDER BY idOrder DESC LIMIT 1"
            val statementSecond = connection.connection.prepareStatement(querySecond).apply {
                setDate(1, date)
                setString(2, name)
            }
            val resultSetIdOrder = statementSecond.executeQuery()
            resultSetIdOrder.next()
            val idOrder = resultSetIdOrder.getInt("idOrder")
            val queryThird = "INSERT INTO UserOrder VALUES (?, ?)"
            val statementThird = connection.connection.prepareStatement(queryThird).apply {
                setInt(1, idOrder)
                setInt(2, User.Id.id)
            }
            res.add(statementThird.executeUpdate())
            productsForCart.forEach { productForCart ->
                res.add(
                    insertProductForOrder(
                        idOrder = idOrder,
                        productForCart = productForCart,
                        connection = connection
                    )
                )
            }

            connection.connection.commit()
            connection.connection.autoCommit = true
            return res
        }


        private fun insertProductForOrder(
            idOrder: Int,
            productForCart: ProductForCart,
            connection: DbConnection = DbConn
        ): Int {
            val query = "INSERT INTO OrderProduct VALUES (?, ?, ?, ?)"
            val statement = connection.connection.prepareStatement(query).apply {
                setInt(1, idOrder)
                setInt(2, productForCart.product.idProduct)
                setInt(3, productForCart.qty)
                setDouble(4, productForCart.product.price)
            }
            return statement.executeUpdate()
        }


        fun getOrdersId(limit: Int, offset: Int, connection: DbConnection = DbConn): ResultSet {
            val queryFirst =
                "SELECT idOrder FROM UserOrder where idUser = ? LIMIT $limit OFFSET $offset"
            val statementFirst = connection.connection.prepareStatement(queryFirst).apply {
                setInt(1, User.Id.id)
            }
            return statementFirst.executeQuery()
        }



        fun getOrderDetails(
            idOrders: ResultSet,
            limit: Int, offset: Int,
            connection: DbConnection = DbConn
        ): List<ResultSet> {
            val res: MutableList<ResultSet> = mutableListOf()
            while (idOrders.next()) {
                val query =
                    "SELECT * FROM OrderDetails WHERE idOrder = ? LIMIT $limit OFFSET $offset"
                val statement = connection.connection.prepareStatement(query).apply {
                    setInt(1, idOrders.getInt("idOrder"))
                }
                res.add(statement.executeQuery())
            }
            return res
        }

        fun getOrderProducts(
            idOrders: ResultSet,
            limit: Int, offset: Int,
            connection: DbConnection = DbConn
        ): List<ResultSet> {
            val res = mutableListOf<ResultSet>()
            while (idOrders.next()) {
                val query =
                    "SELECT * FROM OrderProduct WHERE idOrder = ? LIMIT $limit OFFSET $offset"
                val statement = connection.connection.prepareStatement(query).apply {
                    setInt(1, idOrders.getInt("idOrder"))
                }
                res.add(statement.executeQuery())
            }
            return res
        }


        fun getProductsBySearching(
            text: String,
            connection: DbConnection = DbConn,
            limit: Int,
            offset: Int
        ): ResultSet {
            val query =
                "SELECT * FROM Product WHERE CONCAT_WS(' ', brand, model) LIKE ? LIMIT $limit OFFSET $offset"
            Log.d("aaaaaaaa", query)
            val statement = connection.connection.prepareStatement(query).apply {
                setString(1, "%$text%")
            }
            return statement.executeQuery()
        }


        fun getInfoDiscount(idDiscount: Int, connection: DbConnection = DbConn): ResultSet {
            val query = "SELECT * FROM Discount WHERE idDiscount = ?"
            val statement = connection.connection.prepareStatement(query).apply {
                setInt(1, idDiscount)
            }
            return statement.executeQuery()
        }


        fun getInfoDiscount(
            idDiscount: Int,
            limit: Int,
            offset: Int,
            connection: DbConnection = DbConn
        ): ResultSet {
            val query = "SELECT * FROM Discount WHERE idDiscount = ? LIMIT $limit OFFSET $offset"
            val statement = connection.connection.prepareStatement(query).apply {
                setInt(1, idDiscount)
            }
            return statement.executeQuery()
        }


        fun getReviews(idProduct: Int, connection: DbConnection = DbConn): ResultSet {
            val query = "SELECT * FROM Review WHERE idProduct = ?"
            val statement = connection.connection.prepareStatement(query).apply {
                setInt(1, idProduct)
            }
            return statement.executeQuery()

        }


        fun uploadReview(idProduct: Int, rating: Float, text: String, connection: DbConnection = DbConn): Int {
            val query = "INSERT INTO Review (idProduct, idUser, rating, review) VALUES (?, ?, ?, ?)"
            val statement = connection.connection.prepareStatement(query).apply {
                setInt(1, idProduct)
                setInt(2, User.Id.id)
                setFloat(3, rating)
                setString(4, text)
            }
            return statement.executeUpdate()
        }


        fun updatePassword(newPassword: String, connection: DbConnection = DbConn) {
            updateUser("password", newPassword, connection)
        }

        fun updateEmail(newEmail:String, connection: DbConnection = DbConn){
            updateUser("email", newEmail, connection)
        }

        fun updateUser(key:String, value:String, connection: DbConnection = DbConn){
            val query = "UPDATE User SET $key = ? WHERE idUser = ?"
            val statement = connection.connection.prepareStatement(query).apply {
                setString(1, value)
                setInt(2, User.Id.id)
            }
            statement.executeUpdate()

        }
    }

}