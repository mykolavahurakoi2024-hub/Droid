package com.itproger.aren;

// Базовий абстрактний клас для всіх арен
public abstract class Arena {

    // Абстрактний метод для отримання імені
    public abstract String getName();

    /**
     * @return Модифікатор точності (наприклад, 0.1 для +10% або -0.15 для -15%)
     */
    public abstract double getAccuracyModifier();

    /**
     * @return Модифікатор шкоди (наприклад, 10 для +10 шкоди)
     */
    public abstract int getDamageModifier();

    @Override
    public String toString() {
        return getName(); // Для зручного виводу в меню
    }
}

