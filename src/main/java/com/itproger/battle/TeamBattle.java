// src/com/itproger/battle/TeamBattle.java
package com.itproger.battle;

import com.itproger.droid.Droid;
import java.io.FileWriter; // <-- Не забудьте імпорти
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class TeamBattle {

    private final List<Droid> team1;
    private final List<Droid> team2;
    private final Random random = new Random();
    private final List<String> battleLog = new ArrayList<>();

    public TeamBattle(List<Droid> team1, List<Droid> team2) {
        this.team1 = team1;
        this.team2 = team2;
    }

    private void log(String message) {
        System.out.println(message);
        // battleLog.add(message); // Додаємо лише дії, а не системні повідомлення
    }

    private void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    // Допоміжна функція: перевіряє, чи жива команда
    private boolean isTeamAlive(List<Droid> team) {
        for (Droid droid : team) {
            if (droid.isAlive()) return true;
        }
        return false;
    }

    public void startFight() {
        log("========================================");
        log("🛡️  КОМАНДНИЙ БІЙ РОЗПОЧАТО! 🛡️");
        log("Команда 1: " + team1.stream().map(Droid::getName).collect(Collectors.toList()));
        log("Команда 2: " + team2.stream().map(Droid::getName).collect(Collectors.toList()));
        log("========================================");

        List<Droid> currentTeam = random.nextBoolean() ? team1 : team2;
        List<Droid> opponentTeam = (currentTeam == team1) ? team2 : team1;

        int round = 1;
        while (isTeamAlive(team1) && isTeamAlive(team2)) {
            log("\n--- Раунд " + round + " | Хід Команди: " + (currentTeam == team1 ? "1" : "2") + " ---");

            // Збираю списки живих дроїдів
            List<Droid> livingAllies = currentTeam.stream().filter(Droid::isAlive).collect(Collectors.toList());
            List<Droid> livingEnemies = opponentTeam.stream().filter(Droid::isAlive).collect(Collectors.toList());

            // Кожен живий дроїд у команді робить хід
            for (Droid droid : livingAllies) {
                if (!isTeamAlive(opponentTeam)) break; // Якщо ворогів не лишилось, зупиняємо раунд

                // Кожен дроїд сам вирішує, що робити!
                String actionLog = droid.performBattleAction(livingAllies, livingEnemies);
                battleLog.add(actionLog); // Додаю дію в лог
                sleep(1000); // Пауза між ходами

                // Оновлюю список живих ворогів (на випадок, якщо когось щойно вбили)
                livingEnemies = opponentTeam.stream().filter(Droid::isAlive).collect(Collectors.toList());
            }

            // Міняємо команди
            List<Droid> temp = currentTeam;
            currentTeam = opponentTeam;
            opponentTeam = temp;
            round++;
        }

        // Визначаємо переможця
        String winnerTeam = isTeamAlive(team1) ? "Команда 1" : "Команда 2";
        log("\n========================================");
        log("🏆 Бій завершено! Перемогла " + winnerTeam + "!");
        log("========================================");

        // Відновлюємо здоров'я
        team1.forEach(Droid::restoreHealth);
        team2.forEach(Droid::restoreHealth);
        saveLogToFile();
    }

    private void saveLogToFile() {
        String filename = "battle_team_" + System.currentTimeMillis() + ".txt";
        try (FileWriter writer = new FileWriter(filename)) {
            writer.write("--- ЛОГ КОМАНДНОГО БОЮ ---\n\n");
            for (String line : battleLog) {
                writer.write(line + "\n");
            }
            log("\n[✅ Лог бою збережено у файл: " + filename + "]");
        } catch (IOException e) {
            System.err.println("[❌ Помилка при збереженні логу: " + e.getMessage() + "]");
        }
    }
}
