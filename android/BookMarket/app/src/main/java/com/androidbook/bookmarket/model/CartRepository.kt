package com.androidbook.bookmarket.model

object CartRepository {
    val cartBooks: ArrayList<Book> = ArrayList<Book>()

    fun addCartItems(book: Book)  {
        var cnt = 0
        val goodsList: ArrayList<Book> = cartBooks
        var goods = book
        var goodsQnt = Book()

        for (i in 0 until goodsList.size) {
            goodsQnt = goodsList[i]
            if (goodsQnt.bookid.equals(book.bookid)) {
                cnt++
                goodsQnt.quantity += 1
            }
        }

        if (cnt == 0) {
            goods.quantity = 1
            goods.isCheck = true
            cartBooks.add(goods)
        }
    }

    fun countCartItems( ) : Int {
        var totalQuantity = 0
        if (cartBooks != null) {
            for (i in cartBooks.indices) {
                totalQuantity += cartBooks[i].quantity
            }
        }
        return totalQuantity
    }

    fun grandTotalCartItems(): Int {
        var totalPrice = 0
        for (i in cartBooks.indices) {
            if (cartBooks[i].isCheck)
                totalPrice += cartBooks[i].price * cartBooks[i].quantity
        }
        return totalPrice
    }

}
