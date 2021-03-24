package com.example.ordertemplate.ui.powder;

public class Powders {
    String powderName;
    String powderImage;

    public Powders(){}

    public Powders(String powderName, String powderImage) {
        this.powderName = powderName;
        this.powderImage = powderImage;
    }

    public String getPowderName() {
        return powderName;
    }

    public void setPowderName(String powderName) {
        this.powderName = powderName;
    }

    public String getPowderImage() {
        return powderImage;
    }

    public void setPowderImage(String powderImage) {
        this.powderImage = powderImage;
    }
}
