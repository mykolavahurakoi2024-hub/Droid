package com.itproger.team;

import com.itproger.droid.Droid;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Team {
    private String name;
    private List<Droid> members;

    public Team(String name) {
        this.name = name;
        this.members = new ArrayList<>();
    }

    // Додаємо дроїда до команди
    public void addDroid(Droid droid) {
        members.add(droid);
    }

    // Повертаємо список всіх дроїдів
    public List<Droid> getMembers() {
        return members;
    }

    // Перевіряємо, чи залишився живий хоча б один дроїд
    public boolean hasAliveMembers() {
        for (Droid d : members) {
            if (d.isAlive()) return true;
        }
        return false;
    }

    // Отримуємо випадкового живого дроїда для бою
    public Droid getRandomAliveDroid() {
        List<Droid> alive = new ArrayList<>();
        for (Droid d : members) {
            if (d.isAlive()) alive.add(d);
        }
        if (alive.isEmpty()) return null;

        Random rand = new Random();
        return alive.get(rand.nextInt(alive.size()));
    }

    // Виводимо стан команди
    public void showTeamStatus() {
        System.out.println("Команда: " + name);
        for (Droid d : members) {
            System.out.println(" - " + d.getName() + " | HP: " + d.getHealth());
        }
    }

    public String getName() {
        return name;
    }
}
