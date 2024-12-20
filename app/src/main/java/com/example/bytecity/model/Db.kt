package com.example.bytecity.model

import android.util.Log
import com.example.bytecity.businessClasses.Product
import com.example.bytecity.businessClasses.ProductForCart
import java.sql.Connection
import java.sql.Date
import java.sql.ResultSet


class Db {

    companion object {
        fun checkUserEmail(email: String): ResultSet {
            val statement =
                DbConn.connection.prepareStatement("SELECT * FROM User WHERE email = ?")
                    .apply {
                        setString(1, email)
                    }

            return statement.executeQuery()

        }

        fun checkUserLogin(login: String): ResultSet {
            val statement = DbConn.connection.prepareStatement("SELECT * FROM User WHERE login = ?")
                .apply {
                    setString(1, login)
                }
            return statement.executeQuery()

        }

        fun checkUserContactNumber(contactNumber: String): ResultSet {
            val statement =
                DbConn.connection.prepareStatement("SELECT * FROM User WHERE contactNumber = ?")
                    .apply {
                        setString(1, contactNumber)
                    }
            return statement.executeQuery()
        }


        fun addUser(login: String, password: String, email: String, contactNumber: String): Int {
            val query =
                "INSERT INTO User (login, password, email, contactNumber) VALUES (?, ?, ?, ?)"
            val statement = DbConn.connection.prepareStatement(query).apply {
                setString(1, login)
                setString(2, password)
                setString(3, email)
                setString(4, contactNumber)
            }
            return statement.executeUpdate()

        }

        fun getUser(login: String, password: String): ResultSet {
            val query =
                "SELECT idUser, login, email, contactNumber FROM User WHERE login = ? AND password = ?"
            val statement = DbConn.connection.prepareStatement(query).apply {
                setString(1, login)
                setString(2, password)
            }
            return statement.executeQuery()
        }

        fun getColumnsAndCommentsInfo(category: String): ResultSet {
            val query =
                "SELECT column_name,column_comment FROM information_schema.columns WHERE table_schema='ByteCity' and table_name=?"
            val statement = DbConn.connection.prepareStatement(query).apply {
                setString(1, category)
            }
            return statement.executeQuery()
        }

        fun getInfo(product: Product): ResultSet {
            val table = product.type
            val column = "id${product.type}"
            val query = "SELECT * FROM $table WHERE $column = ?"
            val statement = DbConn.connection.prepareStatement(query).apply {
                setInt(1, product.idProduct)
            }
            return statement.executeQuery()
        }

        fun addProductToWishList(product: Product): Int {
            val query = "INSERT INTO Favourite VALUES (?, ?)"
            val statement = DbConn.connection.prepareStatement(query).apply {
                setInt(1, User.Id.id)
                setInt(2, product.idProduct)
            }
            return statement.executeUpdate()
        }

        fun addProductToCart(product: Product): Int {
            val query = "INSERT INTO Cart VALUES (?, ?, 1)"
            val statement = DbConn.connection.prepareStatement(query).apply {
                setInt(1, User.Id.id)
                setInt(2, product.idProduct)
            }
            return statement.executeUpdate()
        }

        fun getProductWishList(product: Product): ResultSet {
            val query = "SELECT * FROM Favourite WHERE idUser = ? AND idProduct = ?"
            val statement = DbConn.connection.prepareStatement(query).apply {
                setInt(1, User.Id.id)
                setInt(2, product.idProduct)
            }
            return statement.executeQuery()
        }

        fun getAllProductWishList(): ResultSet {
            val query = "SELECT idProduct FROM Favourite WHERE idUser = ?"
            val statement = DbConn.connection.prepareStatement(query).apply {
                setInt(1, User.Id.id)
            }
            return statement.executeQuery()
        }

        fun getAllProductCart(): ResultSet {
            val query = "SELECT * FROM Cart WHERE idUser = ?"
            val statement = DbConn.connection.prepareStatement(query).apply {
                setInt(1, User.Id.id)
            }
            return statement.executeQuery()
        }

        fun cleanCart(): Int {
            val query = "DELETE FROM Cart WHERE idUser = ?"
            val statement = DbConn.connection.prepareStatement(query).apply {
                setInt(1, User.Id.id)
            }
            return statement.executeUpdate()
        }

        fun getProductCart(product: Product): ResultSet {
            val query = "SELECT * FROM Cart WHERE idUser = ? AND idProduct = ?"
            val statement = DbConn.connection.prepareStatement(query).apply {
                setInt(1, User.Id.id)
                setInt(2, product.idProduct)
            }
            return statement.executeQuery()
        }

        fun deleteProductWishList(product: Product): Int {
            val query = "DELETE FROM Favourite WHERE idUser = ? AND idProduct = ?"
            val statement = DbConn.connection.prepareStatement(query).apply {
                setInt(1, User.Id.id)
                setInt(2, product.idProduct)
            }
            return statement.executeUpdate()
        }


        fun deleteProductCart(product: Product): Int {
            return deleteProductCart(product.idProduct)
        }

        fun deleteProductCart(idProduct: Int): Int {
            val query = "DELETE FROM Cart WHERE idUser = ? AND idProduct = ?"
            val statement = DbConn.connection.prepareStatement(query).apply {
                setInt(1, User.Id.id)
                setInt(2, idProduct)
            }
            return statement.executeUpdate()
        }

        fun getProducts(category: String): ResultSet {
            val query = "SELECT * FROM Product WHERE type = ? AND inStock > 0"
            val statement = DbConn.connection.prepareStatement(query).apply {
                setString(1, category)
            }
            return statement.executeQuery()
        }

        fun getProducts(
            connection: DbConnection = DbConn,
            category: String, limit:Int, offset:Int): ResultSet {
            val query = "SELECT * FROM Product WHERE type = ? AND inStock > 0 LIMIT $limit OFFSET $offset"
            val statement = connection.connection.prepareStatement(query).apply {
                setString(1, category)
            }
            return statement.executeQuery()
        }

        fun getRating(product: Product): ResultSet {
            val query = "SELECT SUM(rating) / COUNT(rating) AS res FROM Review WHERE idProduct = ?"
            val statement = DbConn.connection.prepareStatement(query).apply {
                setInt(1, product.idProduct)
            }
            return statement.executeQuery()
        }

        fun getInfoAboutCooling(product: Product): ResultSet {
            val query =
                "SELECT GROUP_CONCAT(socket) AS socket FROM CoolingSockets WHERE idCooling = ? GROUP BY idCooling;"
            val statement = DbConn.connection.prepareStatement(query).apply {
                setInt(1, product.idProduct)
            }
            return statement.executeQuery()
        }

        fun getName(id: Int): ResultSet {
            val query =
                "SELECT CONCAT_WS(' ', brand, model) as res FROM Product WHERE idProduct = ?"
            val statement = DbConn.connection.prepareStatement(query).apply {
                setInt(1, id)
            }
            return statement.executeQuery()
        }

        private fun getProductInSomething(product: Product, something: String): ResultSet {
            val query = "SELECT idProduct FROM $something WHERE idProduct = ? AND idUser = ?"
            val statement = DbConn.connection.prepareStatement(query).apply {
                setInt(1, product.idProduct)
                setInt(2, User.Id.id)
            }
            return statement.executeQuery()

        }

        fun getProductInFavourite(product: Product): ResultSet {
            return getProductInSomething(product, "Favourite")
        }

        fun getProductInCart(product: Product): ResultSet {
            return getProductInSomething(product, "Cart")
        }

        fun getProductById(id: Int): ResultSet {
            val query = "SELECT * FROM Product WHERE idProduct = ?"
            val statement = DbConn.connection.prepareStatement(query).apply {
                setInt(1, id)
            }
            return statement.executeQuery()
        }


        fun getProductById(id: Int, limit:Int, offset:Int, connection: DbConnection = DbConn): ResultSet {
            val query = "SELECT * FROM Product WHERE idProduct = ? LIMIT $limit OFFSET $offset"
            val statement = connection.connection.prepareStatement(query).apply {
                setInt(1, id)
            }
            return statement.executeQuery()
        }

        fun updateProductCartPlusQty(product: Product): Int {
            val query =
                "UPDATE Cart SET quantity = quantity + 1 WHERE idProduct = ? AND idUser = ?"
            val statement = DbConn.connection.prepareStatement(query).apply {
                setInt(1, product.idProduct)
                setInt(2, User.Id.id)
            }
            return statement.executeUpdate()
        }

        fun updateProductCartMinusQty(product: Product): Int {
            val query =
                "UPDATE Cart SET quantity = quantity - 1 WHERE idProduct = ? AND idUser = ?"
            val statement = DbConn.connection.prepareStatement(query).apply {
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

            Log.d("autoCommit", "Before transaction: ${connection.connection.autoCommit}")
//            try {
            val queryFirst =
                "INSERT INTO OrderDetails (registrationDate, status, name) VALUES (?, ?, ?)"
            val statementFirst = connection.connection.prepareStatement(queryFirst).apply {
                setDate(1, date)
                setString(2, "Оформлен")
                setString(3, name)
            }

            res.add(statementFirst.executeUpdate())
            Log.d("autoCommit", "Before transaction: ${connection.connection.autoCommit}")
            val querySecond =
                "SELECT idOrder FROM OrderDetails where registrationDate = ? AND name = ? ORDER BY idOrder DESC LIMIT 1"
            val statementSecond = connection.connection.prepareStatement(querySecond).apply {
                setDate(1, date)
                setString(2, name)
            }
            val resultSetIdOrder = statementSecond.executeQuery()
            Log.d("autoCommit", "Before transaction: ${connection.connection.autoCommit}")
            resultSetIdOrder.next()
            val idOrder = resultSetIdOrder.getInt("idOrder")
            val queryThird = "INSERT INTO UserOrder VALUES (?, ?)"
            val statementThird = connection.connection.prepareStatement(queryThird).apply {
                setInt(1, idOrder)
                setInt(2, User.Id.id)
            }
            res.add(statementThird.executeUpdate())
            Log.d("autoCommit", "Before transaction: ${connection.connection.autoCommit}")
            productsForCart.forEach { productForCart ->
                res.add(
                    insertProductForOrder(
                        idOrder = idOrder,
                        productForCart = productForCart,
                        connection = connection
                    )
                )

//                val queryDeleteProducts = "UPDATE Product SET inStock = inStock - ? WHERE idProduct = ?"
//                val statementDeleteProduct = connection.connection.prepareStatement(queryDeleteProducts).apply {
//                    setInt(1, productForCart.qty)
//                    setInt(2, productForCart.product.idProduct)
//                }
//
//                statementDeleteProduct.executeUpdate()
            }



                connection.connection.commit()
                connection.connection.autoCommit = true
//            } catch (ex: Exception) {
//                connection.connection.rollback()
//                Log.e("DB_ERROR", "Transaction rollback", ex)
//            } finally {
//                connection.connection.autoCommit = true
////                trueDbConn.connection.close()
//                Log.d("autoCommit", "Final state: ${connection.connection.autoCommit}")
//            }
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

        fun getOrdersId(): ResultSet {
            val queryFirst = "SELECT idOrder FROM UserOrder where idUser = ?"
            val statementFirst = DbConn.connection.prepareStatement(queryFirst).apply {
                setInt(1, User.Id.id)
            }
            return statementFirst.executeQuery()
        }

        fun getOrdersId(limit:Int, offset:Int, connection: DbConnection = DbConn): ResultSet {
            val queryFirst = "SELECT idOrder FROM UserOrder where idUser = ? LIMIT $limit OFFSET $offset"
            val statementFirst = connection.connection.prepareStatement(queryFirst).apply {
                setInt(1, User.Id.id)
            }
            return statementFirst.executeQuery()
        }



        fun getOrderDetails(
            idOrders: ResultSet
        ): List<ResultSet> {
            val res: MutableList<ResultSet> = mutableListOf()
            while (idOrders.next()) {
                val query = "SELECT * FROM OrderDetails WHERE idOrder = ?"
                val statement = DbConn.connection.prepareStatement(query).apply {
                    setInt(1, idOrders.getInt("idOrder"))
                }
                res.add(statement.executeQuery())
            }
            return res
        }

        fun getOrderDetails(
            idOrders: ResultSet,
            limit:Int, offset:Int,
            connection: DbConnection = DbConn
        ): List<ResultSet> {
            val res: MutableList<ResultSet> = mutableListOf()
            while (idOrders.next()) {
                val query = "SELECT * FROM OrderDetails WHERE idOrder = ? LIMIT $limit OFFSET $offset"
                val statement = connection.connection.prepareStatement(query).apply {
                    setInt(1, idOrders.getInt("idOrder"))
                }
                res.add(statement.executeQuery())
            }
            return res
        }

        fun getOrderProducts(
            idOrders: ResultSet
        ): List<ResultSet> {
            val res = mutableListOf<ResultSet>()
            while (idOrders.next()) {
                val query = "SELECT * FROM OrderProduct WHERE idOrder = ?"
                val statement = DbConn.connection.prepareStatement(query).apply {
                    setInt(1, idOrders.getInt("idOrder"))
                }
                res.add(statement.executeQuery())
            }
            return res
        }

        fun getOrderProducts(
            idOrders: ResultSet,
            limit:Int, offset:Int,
            connection: DbConnection = DbConn
        ): List<ResultSet> {
            val res = mutableListOf<ResultSet>()
            while (idOrders.next()) {
                val query = "SELECT * FROM OrderProduct WHERE idOrder = ? LIMIT $limit OFFSET $offset"
                val statement = connection.connection.prepareStatement(query).apply {
                    setInt(1, idOrders.getInt("idOrder"))
                }
                res.add(statement.executeQuery())
            }
            return res
        }


        fun getProductsBySearching(text: String, connection: DbConnection = DbConn): ResultSet {
            val query = "SELECT * FROM Product WHERE CONCAT_WS(' ', brand, model) LIKE ?"
            val statement = connection.connection.prepareStatement(query).apply {
                setString(1, "%$text%")
            }
            return statement.executeQuery()
        }

        fun getProductsBySearching(text: String, connection: DbConnection = DbConn, limit:Int, offset:Int): ResultSet {
            val query = "SELECT * FROM Product WHERE CONCAT_WS(' ', brand, model) LIKE ? LIMIT $limit OFFSET $offset"
            Log.d("aaaaaaaa",query)
            val statement = connection.connection.prepareStatement(query).apply {
                setString(1, "%$text%")
            }
            return statement.executeQuery()
        }


        fun getInfoDiscount(idDiscount: Int):ResultSet{
            val query = "SELECT * FROM Discount WHERE idDiscount = ?"
            val statement = DbConn.connection.prepareStatement(query).apply {
                setInt(1, idDiscount)
            }
            return statement.executeQuery()
        }


        fun getInfoDiscount(idDiscount: Int, limit:Int, offset:Int, connection: DbConnection = DbConn):ResultSet{
            val query = "SELECT * FROM Discount WHERE idDiscount = ? LIMIT $limit OFFSET $offset"
            val statement = connection.connection.prepareStatement(query).apply {
                setInt(1, idDiscount)
            }
            return statement.executeQuery()
        }


        fun getReviews(idProduct:Int):ResultSet{
            val query = "SELECT * FROM Review WHERE idProduct = ?"
            val statement = DbConn.connection.prepareStatement(query).apply {
                setInt(1, idProduct)
            }
            return statement.executeQuery()

        }


        fun uploadReview(idProduct: Int, rating:Float, text:String):Int{
            val query = "INSERT INTO Review (idProduct, idUser, rating, review) VALUES (?, ?, ?, ?)"
            val statement = DbConn.connection.prepareStatement(query).apply {
                setInt(1, idProduct)
                setInt(2, User.Id.id)
                setFloat(3, rating)
                setString(4, text)
            }
            return statement.executeUpdate()
        }
    }

}