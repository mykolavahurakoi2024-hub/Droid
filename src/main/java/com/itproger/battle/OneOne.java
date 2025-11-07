package com.itproger.battle;

import com.itproger.aren.Arena;
import com.itproger.droid.attack.AttackDroid;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class OneOne {
    public AttackDroid droid1;
    public AttackDroid droid2;
    public Arena arena;
    public Random random = new Random();
    private List<String> battleLog = new ArrayList<>();

    public OneOne(AttackDroid droid1, AttackDroid droid2, Arena arena){
        this.droid1 = droid1;
        this.droid2 = droid2;
        this.arena = arena;
    }

    public AttackDroid startsFight(){
        log("========================================");
        log("⚔️ БІЙ 1 НА 1 РОЗПОЧАТО!");
        log("Локація: " + arena.getName());
        log(droid1.getName() + " VS " + droid2.getName());
        log("========================================");

        AttackDroid attacker = random.nextBoolean() ? droid1 : droid2;
        AttackDroid defender = (attacker == droid1) ? droid2 : droid1;

        log("Першим ходить: "+attacker.getName());

        int round = 1;
        while (droid1.isAlive() && droid2.isAlive()){
            log("\n--- Раунд " + round + " ---");

            attacker.attack(defender, this.arena);

            AttackDroid temp = attacker;
            attacker = defender;
            defender = temp;

            round++;
            sleep(1500);
        }
        AttackDroid winner = droid1.isAlive() ? droid1 : droid2;
        log("\n========================================");
        log("🏆 Бій завершено! Переможець: " + winner.getName() + "!");
        log("========================================");

        saveLogToFile();

        droid1.restoreHealth();
        droid2.restoreHealth();

        return winner;
    }

    public void log(String text){
        System.out.println(text);
    }

    private void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void saveLogToFile() {
        String filename = "battle_1v1_" + System.currentTimeMillis() + ".txt";
        try (FileWriter writer = new FileWriter(filename)) {
            writer.write("--- ЛОГ БОЮ: " + droid1.getName() + " vs " + droid2.getName() + " ---\n\n");
            for (String line : battleLog) {
                writer.write(line + "\n");
            }
            log("\n[✅ Лог бою збережено у файл: " + filename + "]");
        } catch (IOException e) {
            System.err.println("[❌ Помилка при збереженні логу: " + e.getMessage() + "]");
        }
    }


}
