package com.zz.pattern.factory.abstractfactory;

public class ShanghaiClothesFactory extends ClothesFactory {

    public Trousers createTrousers(int waistSize, int height) {
        return new WesternTrousers("上海牌裤子", waistSize, height);
    }

    public UpperClothes createUpperClothes(int chestSize, int height) {
        return new WesternUpperClothes("上海牌上衣", chestSize, height);
    }
}
