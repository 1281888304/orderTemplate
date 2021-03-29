package com.example.ordertemplate;

public class CartProduct {
    private String cartProductName;
    private String cartProductNum;
    private String cartProductPrice;
    private String cartProductTitle;

    public CartProduct(String cartProductName, String cartProductNum, String cartProductPrice, String cartProductTitle) {
        this.cartProductName = cartProductName;
        this.cartProductNum = cartProductNum;
        this.cartProductPrice = cartProductPrice;
        this.cartProductTitle = cartProductTitle;
    }

    public CartProduct(){}

    public String getCartProductName() {
        return cartProductName;
    }

    public void setCartProductName(String cartProductName) {
        this.cartProductName = cartProductName;
    }

    public String getCartProductNum() {
        return cartProductNum;
    }

    public void setCartProductNum(String cartProductNum) {
        this.cartProductNum = cartProductNum;
    }

    public String getCartProductPrice() {
        return cartProductPrice;
    }

    public void setCartProductPrice(String cartProductPrice) {
        this.cartProductPrice = cartProductPrice;
    }

    public String getCartProductTitle() {
        return cartProductTitle;
    }

    public void setCartProductTitle(String cartProductTitle) {
        this.cartProductTitle = cartProductTitle;
    }
}
