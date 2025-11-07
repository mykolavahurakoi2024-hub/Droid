package com.itproger.droid.attack;

import com.itproger.droid.Droid;
import com.itproger.aren.Arena;
import java.util.List;

import java.util.Random;

public abstract class AttackDroid extends Droid {

    private int damage;
    private double accuracy;


    public AttackDroid(String name, int health, int damage, double accuracy) {
        super(name, health);
        this.damage = damage;
        this.accuracy = accuracy;
    }

    public int getDamage() {
        return damage;
    }

    public double getAccuracy() {
        return accuracy;
    }

    private Random random = new Random();

    //Метод, щоб атакувати й підлікувати себе
    public String attack(AttackDroid enemy, Arena arena) {
        // StringBuilder ефективно збирає лог з кількох частин
        StringBuilder log = new StringBuilder();

        double finalAccuracy = this.accuracy + arena.getAccuracyModifier();
        int finalDamage = this.damage + arena.getDamageModifier();

        // (Запобіжник, щоб шкода не стала від'ємною)
        if (finalDamage < 0) {
            finalDamage = 0;
        }

        double randomAccuracy = Math.random(); // Випадкове число від 0.0 до 0.99...

        if (randomAccuracy <= finalAccuracy) {
            // Влучання
            enemy.takeDamage(finalDamage);
            log.append("🎯 " + this.name + " влучає у " + enemy.getName() + " і завдає " + this.damage + " шкоди!");
        } else {
            // Промах
            log.append("💨 " + this.name + " промахується по " + enemy.getName() + ".");
        }

        if (random.nextInt(100) < 30) { // 30% шанс
            int healAmount = 10;
            heal(healAmount);

            // Додаю запис про лікування до логу
            log.append("\n💚 " + this.name + " лікує себе на " + healAmount + " HP!");
        }

        // Друкую фінальний лог раунду в консоль
        System.out.println(log.toString());

        // Повертаю лог для запису у файл
        return log.toString();
    }

    @Override
    public String performBattleAction(List<Droid> allies, List<Droid> enemies) {
        // 1. Використовуємо ОДИН StringBuilder для всього логу
        StringBuilder log = new StringBuilder();

        if (enemies.isEmpty()) {
            return "🌀 " + this.name + " не має цілей.";
        }

        // --- Логіка Атаки ---
        // В командному бою просто б'ю випадкового ворога
        Droid target = enemies.get(random.nextInt(enemies.size()));

        if (Math.random() <= this.accuracy) {
            target.takeDamage(this.damage);
            // Просто додаємо до логу, НЕ повертаємо
            log.append("💥 " + this.name + " атакує " + target.getName() + " і завдає " + this.damage + " шкоди!");
        } else {
            // Просто додаємо до логу, НЕ повертаємо
            log.append("💨 " + this.name + " промахується по " + target.getName() + ".");
        }

        // --- Логіка Лікування ---
        // Тепер код дійде до цього місця!
        if (random.nextInt(100) < 30) { // 30% шанс
            int healAmount = 10;
            heal(healAmount); // Викликаємо метод heal() з Droid

            // Додаємо запис про лікування до того ж самого логу
            log.append("\n💚 " + this.name + " лікує себе на " + healAmount + " HP!");
        }

        // 3. Друкуємо і повертаємо повний лог в самому кінці
        System.out.println(log.toString());
        return log.toString();
    }


    @Override
    public String toString() {
        return "Droid{" +
                "name='" + name + '\'' +
                ", health=" + health +
                ", damage=" + damage +
                '}';
    }
}
