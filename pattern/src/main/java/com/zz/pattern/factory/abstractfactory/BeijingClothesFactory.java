package com.zz.pattern.factory.abstractfactory;

public class BeijingClothesFactory extends ClothesFactory {

    public Trousers createTrousers(int waistSize, int height) {
        return new WesternTrousers("北京牌裤子", waistSize, height);
    }

    public UpperClothes createUpperClothes(int chestSize, int height) {
        return new WesternUpperClothes("北京牌上衣", chestSize, height);
    }
}
