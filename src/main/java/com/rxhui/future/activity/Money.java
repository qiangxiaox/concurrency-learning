package com.rxhui.future.activity;

public enum Money {
    EUR(3.22), USD(5.82);
    private final double value;

    Money(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    public static double getRate(Money moneyOne , Money moneyTwo){
        return moneyTwo.getValue() - moneyOne.getValue();
    }
}
