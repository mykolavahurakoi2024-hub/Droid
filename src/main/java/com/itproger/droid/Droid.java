package com.itproger.droid;

import java.util.List;
import java.util.Random;

public abstract class Droid {
    protected String name;
    protected int health;
    protected int maxhealth;
    protected Random random = new Random();


    public Droid(String name, int health) {
        this.name = name;
        this.health = health;
        this.maxhealth = health;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public int getHealth() {
        return health;
    }
    public void setHealth(int health) {
        this.health = health;
    }

    public int getMaxhealth() {return maxhealth;}
    public void setMaxhealth(int maxhealth) {this.maxhealth = maxhealth;}

    public void heal(int amount) {
        health += amount;
        if (health > maxhealth) health = maxhealth;
    }

    public boolean isAlive() {
        return health > 0;
    }

    public void takeDamage(int amount) {
        health -= amount;
        if (health < 0) health = 0;
        if(health==0){
            System.out.println(name+" вбито!");
        }else System.out.println("HP -"+health);
    }

    public void restoreHealth() {
        this.health = this.maxhealth;
    }

    /**
     * Головний метод дії для командного бою.
     * @param allies Список живих союзників (включаючи себе)
     * @param enemies Список живих ворогів
     * @return Рядок логу про виконану дію
     */
    public abstract String performBattleAction(List<Droid> allies, List<Droid> enemies);
}