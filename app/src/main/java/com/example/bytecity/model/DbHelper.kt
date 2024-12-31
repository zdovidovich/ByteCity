package com.example.bytecity.model

import com.example.bytecity.businessClasses.Product
import com.example.bytecity.businessClasses.ProductForCart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.sql.Connection
import java.sql.Date
import java.sql.ResultSet


class DbHelper {

    companion object {
        suspend fun checkUserEmail(email: String, connection: DbConnection = DbConn): ResultSet =
            withContext(Dispatchers.IO) {
                val statement =
                    connection.connection.prepareStatement("SELECT * FROM User WHERE email = ?")
                        .apply {
                            setString(1, email)
                        }

                return@withContext statement.executeQuery()
            }

        suspend fun checkUserLogin(login: String, connection: DbConnection = DbConn): ResultSet =
            withContext(Dispatchers.IO) {
                val statement =
                    connection.connection.prepareStatement("SELECT * FROM User WHERE login = ?")
                        .apply {
                            setString(1, login)
                        }
                return@withContext statement.executeQuery()

            }

        suspend fun checkUserContactNumber(
            contactNumber: String,
            connection: DbConnection = DbConn
        ): ResultSet = withContext(Dispatchers.IO) {
            val statement =
                connection.connection.prepareStatement("SELECT * FROM User WHERE contactNumber = ?")
                    .apply {
                        setString(1, contactNumber)
                    }
            return@withContext statement.executeQuery()
        }


        suspend fun addUser(
            login: String,
            password: String,
            email: String,
            contactNumber: String,
            connection: DbConnection = DbConn
        ): Int = withContext(Dispatchers.IO) {
            val query =
                "INSERT INTO User (login, password, email, contactNumber) VALUES (?, ?, ?, ?)"
            val statement = connection.connection.prepareStatement(query).apply {
                setString(1, login)
                setString(2, password)
                setString(3, email)
                setString(4, contactNumber)
            }
            return@withContext statement.executeUpdate()

        }

        suspend fun getUser(
            login: String,
            password: String,
            connection: DbConnection = DbConn
        ): ResultSet = withContext(Dispatchers.IO) {
            val query =
                "SELECT idUser, login, email, contactNumber FROM User WHERE login = ? AND password = ?"
            val statement = connection.connection.prepareStatement(query).apply {
                setString(1, login)
                setString(2, password)
            }
            return@withContext statement.executeQuery()

        }

        suspend fun getUser(idUser: Int, connection: DbConnection = DbConn): ResultSet =
            withContext(Dispatchers.IO) {
                val query =
                    "SELECT idUser, login, email, contactNumber FROM User WHERE idUser = ?"
                val statement = connection.connection.prepareStatement(query).apply {
                    setInt(1, idUser)
                }
                return@withContext statement.executeQuery()

            }


        suspend fun getColumnsAndCommentsInfo(
            category: String,
            connection: DbConnection = DbConn
        ): ResultSet = withContext(Dispatchers.IO) {
            val query =
                "SELECT column_name,column_comment FROM comments WHERE table_name = ?"
            val statement = connection.connection.prepareStatement(query).apply {
                setString(1, category)
            }
            return@withContext statement.executeQuery()
        }

        suspend fun getInfo(product: Product, connection: DbConnection = DbConn): ResultSet =
            withContext(Dispatchers.IO) {
                val table = product.type
                val column = "id${product.type}"
                val query = "SELECT * FROM $table WHERE $column = ?"
                val statement = connection.connection.prepareStatement(query).apply {
                    setInt(1, product.idProduct)
                }
                return@withContext statement.executeQuery()
            }


        suspend fun addProductToWishList(product: Product, connection: DbConnection = DbConn): Int =
            withContext(Dispatchers.IO) {
                val query = "INSERT INTO Favourite VALUES (?, ?)"
                val statement = connection.connection.prepareStatement(query).apply {
                    setInt(1, User.Id.id)
                    setInt(2, product.idProduct)
                }
                return@withContext statement.executeUpdate()
            }

        suspend fun addProductToCart(product: Product, connection: DbConnection = DbConn): Int =
            withContext(Dispatchers.IO) {
                val query = "INSERT INTO Cart VALUES (?, ?, 1)"
                val statement = connection.connection.prepareStatement(query).apply {
                    setInt(1, User.Id.id)
                    setInt(2, product.idProduct)
                }
                return@withContext statement.executeUpdate()
            }

        suspend fun getProductWishList(
            product: Product,
            connection: DbConnection = DbConn
        ): ResultSet = withContext(Dispatchers.IO) {
            val query = "SELECT * FROM Favourite WHERE idUser = ? AND idProduct = ?"
            val statement = connection.connection.prepareStatement(query).apply {
                setInt(1, User.Id.id)
                setInt(2, product.idProduct)
            }
            return@withContext statement.executeQuery()
        }

        suspend fun getAllProductWishList(connection: DbConnection = DbConn): ResultSet =
            withContext(Dispatchers.IO) {
                val query =
                    "SELECT * FROM Favourite JOIN Product ON Favourite.idProduct = Product.idProduct LEFT JOIN Discount ON Product.idDiscount = Discount.idDiscount  WHERE idUser = ?"
                val statement = connection.connection.prepareStatement(query).apply {
                    setInt(1, User.Id.id)
                }
                return@withContext statement.executeQuery()
            }

        suspend fun getAllProductCart(connection: DbConnection = DbConn): ResultSet =
            withContext(Dispatchers.IO) {
                val query =
                    "SELECT * FROM Cart JOIN Product ON Product.idProduct = Cart.idProduct LEFT JOIN Discount ON Product.idDiscount = Discount.idDiscount WHERE idUser = ?"
                val statement = connection.connection.prepareStatement(query).apply {
                    setInt(1, User.Id.id)
                }
                return@withContext statement.executeQuery()
            }

        suspend fun cleanCart(connection: DbConnection = DbConn): Int =
            withContext(Dispatchers.IO) {
                val query = "DELETE FROM Cart WHERE idUser = ?"
                val statement = connection.connection.prepareStatement(query).apply {
                    setInt(1, User.Id.id)
                }
                return@withContext statement.executeUpdate()
            }

        suspend fun getProductCart(product: Product, connection: DbConnection = DbConn): ResultSet =
            withContext(Dispatchers.IO) {
                val query = "SELECT * FROM Cart WHERE idUser = ? AND idProduct = ?"
                val statement = connection.connection.prepareStatement(query).apply {
                    setInt(1, User.Id.id)
                    setInt(2, product.idProduct)
                }
                return@withContext statement.executeQuery()
            }

        suspend fun deleteProductWishList(
            product: Product,
            connection: DbConnection = DbConn
        ): Int = withContext(Dispatchers.IO) {
            val query = "DELETE FROM Favourite WHERE idUser = ? AND idProduct = ?"
            val statement = connection.connection.prepareStatement(query).apply {
                setInt(1, User.Id.id)
                setInt(2, product.idProduct)
            }
            return@withContext statement.executeUpdate()
        }


        suspend fun deleteProductCart(product: Product, connection: DbConnection = DbConn): Int {
            return deleteProductCart(product.idProduct, connection)
        }

        suspend fun deleteProductCart(idProduct: Int, connection: DbConnection = DbConn): Int =
            withContext(Dispatchers.IO) {
                val query = "DELETE FROM Cart WHERE idUser = ? AND idProduct = ?"
                val statement = connection.connection.prepareStatement(query).apply {
                    setInt(1, User.Id.id)
                    setInt(2, idProduct)
                }
                return@withContext statement.executeUpdate()
            }

        suspend fun getProducts(
            connection: DbConnection = DbConn,
            category: String, limit: Int, offset: Int
        ): ResultSet = withContext(Dispatchers.IO) {
            val inQ = if (category.isEmpty()) "" else " type = ? AND"
            val query =
                "SELECT * FROM Product LEFT JOIN Discount ON Product.idDiscount = Discount.idDiscount WHERE$inQ inStock > 0 LIMIT $limit OFFSET $offset"
            val statement = connection.connection.prepareStatement(query).apply {
                if (category.isNotEmpty()) {
                    setString(1, category)
                }
            }
            return@withContext statement.executeQuery()
        }

        suspend fun getRating(product: Product, connection: DbConnection = DbConn): ResultSet =
            withContext(Dispatchers.IO) {
                val query =
                    "SELECT SUM(rating) / COUNT(rating) AS res FROM Review WHERE idProduct = ?"
                val statement = connection.connection.prepareStatement(query).apply {
                    setInt(1, product.idProduct)
                }
                return@withContext statement.executeQuery()
            }

        suspend fun getInfoAboutCooling(
            product: Product,
            connection: DbConnection = DbConn
        ): ResultSet = withContext(Dispatchers.IO) {
            val query =
                "SELECT GROUP_CONCAT(socket) AS socket FROM CoolingSockets WHERE idCooling = ? GROUP BY idCooling;"
            val statement = connection.connection.prepareStatement(query).apply {
                setInt(1, product.idProduct)
            }
            return@withContext statement.executeQuery()
        }

        suspend fun getInfoAboutFormFactors(
            product: Product,
            connection: DbConnection = DbConn
        ): ResultSet = withContext(Dispatchers.IO) {
            val query =
                "SELECT GROUP_CONCAT(formFactor) AS formFactors FROM CaseFormFactor WHERE idPCCase = ? GROUP BY idPCCase;"
            val statement = connection.connection.prepareStatement(query).apply {
                setInt(1, product.idProduct)
            }
            return@withContext statement.executeQuery()
        }


        suspend fun getName(id: Int, connection: DbConnection = DbConn): ResultSet =
            withContext(Dispatchers.IO) {
                val query =
                    "SELECT CONCAT_WS(' ', brand, model) AS res FROM Product WHERE idProduct = ?"
                val statement = connection.connection.prepareStatement(query).apply {
                    setInt(1, id)
                }
                return@withContext statement.executeQuery()
            }

        private suspend fun getProductInSomething(
            product: Product,
            something: String,
            connection: DbConnection = DbConn
        ): ResultSet = withContext(Dispatchers.IO) {
            val query = "SELECT idProduct FROM $something WHERE idProduct = ? AND idUser = ?"
            val statement = connection.connection.prepareStatement(query).apply {
                setInt(1, product.idProduct)
                setInt(2, User.Id.id)
            }
            return@withContext statement.executeQuery()

        }

        suspend fun getProductInFavourite(
            product: Product,
            connection: DbConnection = DbConn
        ): ResultSet {
            return getProductInSomething(product, "Favourite", connection)
        }

        suspend fun getProductInCart(
            product: Product,
            connection: DbConnection = DbConn
        ): ResultSet {
            return getProductInSomething(product, "Cart", connection)
        }


        suspend fun getProductById(
            id: Int,
            connection: DbConnection = DbConn
        ): ResultSet = withContext(Dispatchers.IO) {
            val query =
                "SELECT * FROM Product LEFT JOIN Discount ON Product.idDiscount = Discount.idDiscount WHERE idProduct = ?"
            val statement = connection.connection.prepareStatement(query).apply {
                setInt(1, id)
            }
            return@withContext statement.executeQuery()
        }

        suspend fun updateProductCartPlusQty(
            product: Product,
            connection: DbConnection = DbConn
        ): Int = withContext(Dispatchers.IO) {
            val query =
                "UPDATE Cart SET quantity = quantity + 1 WHERE idProduct = ? AND idUser = ?"
            val statement = connection.connection.prepareStatement(query).apply {
                setInt(1, product.idProduct)
                setInt(2, User.Id.id)
            }
            return@withContext statement.executeUpdate()
        }

        suspend fun updateProductCartMinusQty(
            product: Product,
            connection: DbConnection = DbConn
        ): Int = withContext(Dispatchers.IO) {
            val query =
                "UPDATE Cart SET quantity = quantity - 1 WHERE idProduct = ? AND idUser = ?"
            val statement = connection.connection.prepareStatement(query).apply {
                setInt(1, product.idProduct)
                setInt(2, User.Id.id)
            }
            return@withContext statement.executeUpdate()
        }

        suspend fun insertOrder(
            productsForCart: List<ProductForCart>,
            date: Date,
            connection: DbConnection = DbConn
        ): Int = withContext(Dispatchers.IO) {
            connection.connection.autoCommit = false
            connection.connection.transactionIsolation = Connection.TRANSACTION_REPEATABLE_READ

            for (productForOrder in productsForCart) {
                val queryCheck = "SELECT inStock FROM Product WHERE idProduct = ?"
                val statementCheck = connection.connection.prepareStatement(queryCheck).apply {
                    setInt(1, productForOrder.product.idProduct)
                }
                val resultSetCheck = statementCheck.executeQuery()
                resultSetCheck.next()
                if (resultSetCheck.getInt("inStock") < productForOrder.qty) {
                    resultSetCheck.close()
                    updateQtyCartToOne(idProduct = productForOrder.product.idProduct)
                    return@withContext 1
                }
                resultSetCheck.close()
            }
            val queryFirst =
                "INSERT INTO OrderDetails (registrationDate, status) VALUES (?, ?)"
            val statementFirst = connection.connection.prepareStatement(queryFirst).apply {
                setDate(1, date)
                setString(2, "Checked out")
            }
            statementFirst.executeUpdate()
            val querySecond =
                "SELECT idOrder FROM OrderDetails where registrationDate = ? ORDER BY idOrder DESC LIMIT 1"
            val statementSecond = connection.connection.prepareStatement(querySecond).apply {
                setDate(1, date)
            }
            val resultSetIdOrder = statementSecond.executeQuery()
            resultSetIdOrder.next()
            val idOrder = resultSetIdOrder.getInt("idOrder")
            val queryThird = "INSERT INTO UserOrder VALUES (?, ?)"
            val statementThird = connection.connection.prepareStatement(queryThird).apply {
                setInt(1, idOrder)
                setInt(2, User.Id.id)
            }
            statementThird.executeUpdate()
            productsForCart.forEach { productForCart ->
                insertProductForOrder(
                    idOrder = idOrder,
                    productForCart = productForCart,
                    connection = connection
                )
                decreaseQty(productForCart.product.idProduct, productForCart.qty, connection)
            }

            connection.connection.commit()
            connection.connection.autoCommit = true
            return@withContext 200
        }

        private suspend fun decreaseQty(
            idProduct: Int,
            qty: Int,
            connection: DbConnection = DbConn
        ): Int = withContext(Dispatchers.IO) {
            val query = "UPDATE Product SET inStock = inStock - ? WHERE idProduct = ?"
            val statement = connection.connection.prepareStatement(query).apply {
                setInt(1, qty)
                setInt(2, idProduct)
            }
            return@withContext statement.executeUpdate()
        }


        private suspend fun insertProductForOrder(
            idOrder: Int,
            productForCart: ProductForCart,
            connection: DbConnection = DbConn
        ): Int = withContext(Dispatchers.IO) {
            val query = "INSERT INTO OrderProduct VALUES (?, ?, ?, ?)"
            val statement = connection.connection.prepareStatement(query).apply {
                setInt(1, idOrder)
                setInt(2, productForCart.product.idProduct)
                setInt(3, productForCart.qty)
                setDouble(4, productForCart.product.price)
            }
            return@withContext statement.executeUpdate()
        }


        suspend fun getOrdersId(
            limit: Int,
            offset: Int,
            connection: DbConnection = DbConn
        ): ResultSet = withContext(Dispatchers.IO) {
            val queryFirst =
                "SELECT * FROM UserOrder JOIN OrderDetails ON UserOrder.idOrder = OrderDetails.idOrder WHERE idUser = ? LIMIT $limit OFFSET $offset"
            val statementFirst = connection.connection.prepareStatement(queryFirst).apply {
                setInt(1, User.Id.id)
            }
            return@withContext statementFirst.executeQuery()
        }

        suspend fun getOrderProducts(
            idOrders: Int,
            connection: DbConnection = DbConn
        ): ResultSet = withContext(Dispatchers.IO) {
            val query =
                "SELECT * FROM OrderProduct WHERE idOrder = ?"
            val statement = connection.connection.prepareStatement(query).apply {
                setInt(1, idOrders)
            }
            return@withContext statement.executeQuery()
        }


        suspend fun getProductsBySearching(
            text: String,
            connection: DbConnection = DbConn,
            limit: Int,
            offset: Int
        ): ResultSet = withContext(Dispatchers.IO) {
            val query =
                "SELECT * FROM Product LEFT JOIN Discount ON Product.idDiscount = Discount.idDiscount WHERE CONCAT_WS(' ', brand, model) LIKE ? LIMIT $limit OFFSET $offset"
            val statement = connection.connection.prepareStatement(query).apply {
                setString(1, "%$text%")
            }
            return@withContext statement.executeQuery()
        }

        suspend fun getReviews(idProduct: Int, connection: DbConnection = DbConn): ResultSet =
            withContext(Dispatchers.IO) {
                val query = "SELECT * FROM Review WHERE idProduct = ?"
                val statement = connection.connection.prepareStatement(query).apply {
                    setInt(1, idProduct)
                }
                return@withContext statement.executeQuery()

            }


        suspend fun uploadReview(
            idProduct: Int,
            rating: Float,
            text: String,
            connection: DbConnection = DbConn
        ): Int = withContext(Dispatchers.IO) {
            val query =
                "INSERT INTO Review (idProduct, idUser, rating, review) VALUES (?, ?, ?, ?)"
            val statement = connection.connection.prepareStatement(query).apply {
                setInt(1, idProduct)
                setInt(2, User.Id.id)
                setFloat(3, rating)
                setString(4, text)
            }
            return@withContext statement.executeUpdate()
        }


        suspend fun updatePassword(newPassword: String, connection: DbConnection = DbConn) {
            updateUser("password", newPassword, connection)
        }

        suspend fun updateEmail(newEmail: String, connection: DbConnection = DbConn) {
            updateUser("email", newEmail, connection)
        }

        suspend fun updateUser(key: String, value: String, connection: DbConnection = DbConn) =
            withContext(Dispatchers.IO) {
                val query = "UPDATE User SET $key = ? WHERE idUser = ?"
                val statement = connection.connection.prepareStatement(query).apply {
                    setString(1, value)
                    setInt(2, User.Id.id)
                }
                statement.executeUpdate()
            }

        suspend fun checkUserReview(
            idProduct: Int,
            connection: DbConnection = DbConn
        ): ResultSet =
            withContext(Dispatchers.IO) {
                val query = "SELECT idUser FROM Review WHERE idProduct = ? AND idUser = ?"
                val statement = connection.connection.prepareStatement(query).apply {
                    setInt(1, idProduct)
                    setInt(2, User.Id.id)
                }
                return@withContext statement.executeQuery()
            }

        suspend fun updateQtyCartToOne(idProduct: Int, connection: DbConnection = DbConn) =
            withContext(Dispatchers.IO) {
                val query = "UPDATE Cart SET quantity = 1 WHERE idProduct = ?"
                val statement = connection.connection.prepareStatement(query).apply {
                    setInt(1, idProduct)
                }
                statement.executeUpdate()
            }
    }

}