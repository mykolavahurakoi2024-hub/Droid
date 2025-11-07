// src/com/itproger/aren/CiteArena.java
package com.itproger.aren;

public class CiteArena extends Arena {

    @Override
    public String getName() {
        return "🏙️  Міська Арена";
    }

    @Override
    public double getAccuracyModifier() {
        return 0.10; // Точність +10%
    }

    @Override
    public int getDamageModifier() {
        return -5; // Шкода -5
    }
}