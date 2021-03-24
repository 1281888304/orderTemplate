package com.example.ordertemplate.ui.topping;

public class Toppings {

    String toppingName;
    String toppingImage;
    String toppingPrice;
    String toppingNum;
    String toppingTitle;


    public String getToppingName() {
        return toppingName;
    }

    public void setToppingName(String toppingName) {
        this.toppingName = toppingName;
    }

    public String getToppingImage() {
        return toppingImage;
    }

    public void setToppingImage(String toppingImage) {
        this.toppingImage = toppingImage;
    }

    public String getToppingPrice() {
        return toppingPrice;
    }

    public void setToppingPrice(String toppingPrice) {
        this.toppingPrice = toppingPrice;
    }

    public String getToppingNum() {
        return toppingNum;
    }

    public void setToppingNum(String toppingNum) {
        this.toppingNum = toppingNum;
    }

    public String getToppingTitle() {
        return toppingTitle;
    }

    public void setToppingTitle(String toppingTitle) {
        this.toppingTitle = toppingTitle;
    }

    public Toppings(String toppingName, String toppingImage, String toppingPrice, String toppingNum, String toppingTitle) {
        this.toppingName = toppingName;
        this.toppingImage = toppingImage;
        this.toppingPrice = toppingPrice;
        this.toppingNum = toppingNum;
        this.toppingTitle = toppingTitle;
    }

    public void AutoAddToppingNum(){
        int num=Integer.parseInt(this.toppingNum);
        num++;
        String numString =String.valueOf(num);
        this.toppingNum=numString;
        setToppingNum(numString);
    }



    public Toppings(){}


}
