package com.itproger.droid.rear;

import com.itproger.droid.Droid;
import com.itproger.droid.attack.TankAttackDroid;
import java.util.List;
import java.util.Comparator;

public class MechanicDroid extends RearDroid {

    private int repairPower;

    public MechanicDroid(String name){
        super(name, 50);
        this.repairPower = 25;

    }

    /**
     * Головний метод дії для Механіка.
     * Шукає найбільш пошкоджений ТАНК серед союзників і ремонтує його.
     * Ігнорує всі інші типи дроїдів.
     */
    @Override
    public String performBattleAction(List<Droid> allies, List<Droid> enemies) {

        // 1. Знаходимо союзника з найменшим % здоров'я, який:
        Droid targetToRepair = allies.stream()
                .filter(Droid::isAlive)                  // (а) є живим
                .filter(d -> d instanceof TankAttackDroid) // (б) є екземпляром TankAttackDroid
                .min(Comparator.comparingDouble(d -> (double)d.getHealth() / d.getMaxhealth()))
                .orElse(null);

        // 2. Якщо така ціль є і вона пошкоджена - ремонтуємо
        if (targetToRepair != null && targetToRepair.getHealth() < targetToRepair.getMaxhealth()) {

            targetToRepair.heal(this.repairPower); // Використовуємо 'heal', бо це той самий механізм

            String log = "🔧 " + this.name + " ремонтує " + targetToRepair.getName() + " на " + this.repairPower + " HP!";
            System.out.println(log);
            return log;
        }

        // 3. Якщо цілей для ремонту немає (всі танки здорові або їх немає)
        String log = "🌀 " + this.name + " очікує (немає танків для ремонту).";
        System.out.println(log);
        return log;
    }
}
