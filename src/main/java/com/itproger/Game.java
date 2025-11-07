package com.itproger;

import com.itproger.droid.Droid;
import com.itproger.droid.attack.*;
import com.itproger.droid.rear.*;
import com.itproger.battle.OneOne;
import com.itproger.battle.TeamBattle;
import com.itproger.aren.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Game {

    // Список для зберігання всіх створених дроїдів
    private final List<Droid> droids = new ArrayList<>();
    private final Scanner scanner = new Scanner(System.in);
    private final List<Arena> arenas = new ArrayList<>();

    public Game() {
        arenas.add(new DesertArena());
        arenas.add(new CiteArena());
    }

    public void run() {
        while (true) {
            printMenu();
            String choice = scanner.nextLine();
            switch (choice) {
                case "1": createDroid(); break;
                case "2": showDroids(); break;
                case "3": start1v1Battle(); break;
                case "4": startTeamBattle(); break;
                case "5": System.out.println("Запис бою..."); break;
                case "6": System.out.println("Дякуємо за гру! Вихід..."); return;
                default: System.out.println("❌ Невідома команда.");
            }
        }
    }

    private void printMenu() {
        System.out.println("\n" + "=".repeat(15) + " БИТВА ДРОЇДІВ " + "=".repeat(15));
        System.out.println("1. Створити дроїда");
        System.out.println("2. Показати список створених дроїдів");
        System.out.println("3. Запустити бій 1 на 1 (Тільки Атакуючі)");
        System.out.println("4. Запустити бій Команда-на-Команду");
        System.out.println("5. Запис/відтворення бою");
        System.out.println("6. Вийти з програми");
        System.out.print("Ваш вибір: ");
    }

    /**
     * Логіка створення НОВИХ дроїдів
     */
    private void createDroid() {
        System.out.println("\n--- Створення дроїда ---");
        System.out.println("--- Атакуючі ---");
        System.out.println("1. AssaultAttackDroid (Штурмовик)");
        System.out.println("2. TankAttackDroid (Танк)");
        System.out.println("3. SniperDroid (Снайпер)");
        System.out.println("--- Тил ---");
        System.out.println("4. MedicDroid (Медик)");
        System.out.println("5. MechanicDroid (Механік)");
        System.out.print("Виберіть тип дроїда: ");
        String type = scanner.nextLine();
        System.out.print("Введіть ім'я для дроїда: ");
        String name = scanner.nextLine();

        if (droids.stream().anyMatch(d -> d.getName().equalsIgnoreCase(name))) {
            System.out.println("❌ Дроїд з таким ім'ям вже існує!"); return;
        }

        Droid newDroid;
        try {
            switch (type) {
                case "1": newDroid = new AssaultAttackDroid(name); break;
                case "2": newDroid = new TankAttackDroid(name); break;
                case "3": newDroid = new SniperAttackDroid(name); break;
                case "4": newDroid = new MedicDroid(name); break;
                case "5": newDroid = new MechanicDroid(name); break;
                default: System.out.println("❌ Неправильний вибір типу."); return;
            }
        } catch (Exception e) {
            System.out.println("❌ Помилка створення дроїда: " + e.getMessage()); return;
        }

        droids.add(newDroid);
        System.out.println("✅ Дроїда " + newDroid.getName() + " успішно створено!");
    }

    private void showDroids() {
        System.out.println("\n--- Список ваших дроїдів ---");
        if (droids.isEmpty()) {
            System.out.println("У вас ще немає дроїдів.");
        } else {
            for (int i = 0; i < droids.size(); i++) {

                System.out.println((i + 1) + ". " + droids.get(i).toString());
            }
        }
    }

    /**
     * Запуск бою 1 на 1 (залишаємо логіку з AttackDroid)
     */
    private void start1v1Battle() {
        System.out.println("\n--- Початок бою 1 на 1 (Тільки Атакуючі) ---");

        // Фільтрую список, щоб показати тільки AttackDroid
        List<AttackDroid> fighters = droids.stream()
                .filter(d -> d instanceof AttackDroid)
                .map(d -> (AttackDroid) d)
                .collect(Collectors.toList());

        if (fighters.size() < 2) {
            System.out.println("❌ Потрібно створити хоча б двох АТАКУЮЧИХ дроїдів.");
            return;
        }

        Arena chosenArena = selectArena();
        if (chosenArena == null) return;

        System.out.println("Виберіть першого бійця:");
        AttackDroid droid1 = selectAttacker(fighters, null);
        if (droid1 == null) return;

        System.out.println("Виберіть другого бійця:");
        AttackDroid droid2 = selectAttacker(fighters, droid1);
        if (droid2 == null) return;

        OneOne battle = new OneOne(droid1, droid2, chosenArena);
        battle.startsFight();
    }

    /**
     * НОВИЙ МЕТОД: Запуск командного бою
     */
    private void startTeamBattle() {
        System.out.println("\n--- Початок Командного Бою ---");
        if (droids.size() < 2) {
            System.out.println("❌ Потрібно створити хоча б двох дроїдів.");
            return;
        }

        List<Droid> team1 = new ArrayList<>();
        List<Droid> team2 = new ArrayList<>();
        List<Droid> availableDroids = new ArrayList<>(droids);

        // Формуєю Команду 1
        System.out.println("--- Формування Команди 1 ---");
        while (true) {
            System.out.println("Доступні дроїди для вибору:");
            if (availableDroids.isEmpty()) {
                System.out.println("Всі дроїди розподілені.");
                break;
            }
            // Використовую універсальний selectDroid
            Droid selected = selectDroid(availableDroids, "Виберіть дроїда (або 0 для завершення):");

            if (selected == null) { // Користувач ввів 0 або помилку
                break;
            }

            team1.add(selected);
            availableDroids.remove(selected);
        }

        // Всі, хто лишився, йдуть у Команду 2
        team2.addAll(availableDroids);

        if (team1.isEmpty() || team2.isEmpty()) {
            System.out.println("❌ Обидві команди повинні мати хоча б одного бійця!");
            return;
        }

        System.out.println("Бій Сформовано!");
        // Запускаю новий клас бою
        TeamBattle battle = new TeamBattle(team1, team2);
        battle.startFight();
    }

    // --- Допоміжні методи для вибору ---

    private Arena selectArena() {
        System.out.println("Доступні арени:");
        for (int i = 0; i < arenas.size(); i++) {
            System.out.println((i + 1) + ". " + arenas.get(i).getName());
        }
        System.out.print("Введіть номер арени: ");
        try {
            int choice = Integer.parseInt(scanner.nextLine()) - 1;
            if (choice >= 0 && choice < arenas.size()) {
                return arenas.get(choice);
            } else {
                System.out.println("❌ Неправильний номер арени.");
                return null;
            }
        } catch (NumberFormatException e) {
            System.out.println("❌ Будь ласка, введіть число.");
            return null;
        }
    }

    // Допоміжний метод для вибору АТАКУЮЧОГО дроїда (для 1v1)
    private AttackDroid selectAttacker(List<AttackDroid> fighters, AttackDroid exclude) {
        for (int i = 0; i < fighters.size(); i++) {
            String status = (fighters.get(i) == exclude) ? "[Вже обраний]" : "";
            System.out.println((i + 1) + ". " + fighters.get(i).getName() + " " + status);
        }
        System.out.print("Введіть номер дроїда: ");
        try {
            int choice = Integer.parseInt(scanner.nextLine()) - 1;
            if (choice < 0 || choice >= fighters.size() || fighters.get(choice) == exclude) {
                System.out.println("❌ Неправильний вибір."); return null;
            }
            return fighters.get(choice);
        } catch (NumberFormatException e) {
            System.out.println("❌ Введіть число."); return null;
        }
    }

    // УНІВЕРСАЛЬНИЙ метод для вибору будь-якого дроїда (для Команд)
    private Droid selectDroid(List<Droid> available, String prompt) {
        for (int i = 0; i < available.size(); i++) {
            System.out.println((i + 1) + ". " + available.get(i).toString());
        }
        System.out.print(prompt + " ");
        try {
            int choice = Integer.parseInt(scanner.nextLine());
            if (choice == 0) return null; // 0 - код виходу

            choice -= 1; // Коригуємо індекс
            if (choice < 0 || choice >= available.size()) {
                System.out.println("❌ Неправильний номер."); return null;
            }
            return available.get(choice);
        } catch (NumberFormatException e) {
            System.out.println("❌ Введіть число."); return null;
        }
    }
}