// src/com/itproger/aren/DesertArena.java
package com.itproger.aren;

public class DesertArena extends Arena {

    @Override
    public String getName() {
        return "🏜️  Пустельна Арена";
    }

    @Override
    public double getAccuracyModifier() {
        return -0.15; // Точність -15%
    }

    @Override
    public int getDamageModifier() {
        return 10; // Шкода +10
    }
}