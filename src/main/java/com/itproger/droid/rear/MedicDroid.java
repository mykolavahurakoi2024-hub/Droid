package com.itproger.droid.rear;

import com.itproger.droid.attack.TankAttackDroid;
import com.itproger.droid.Droid;
import java.util.List;
import java.util.Comparator;

public class MedicDroid extends RearDroid {
    private final int healPower;

    public MedicDroid(String name){
        super(name, 200);
        this.healPower = 30;
    }

    /**
     * Головний метод дії для Медика.
     * Шукає найбільш пораненого союзника (який НЕ є Танком) і лікує його.
     */
    @Override
    public String performBattleAction(List<Droid> allies, List<Droid> enemies) {

        // 1. Знаходжу союзника з найменшим % здоров'я, який:
        Droid targetToHeal = allies.stream()
                .filter(Droid::isAlive)                  // (а) є живим
                .filter(d -> !(d instanceof TankAttackDroid)) // (б) НЕ є екземпляром TankAttackDroid
                .min(Comparator.comparingDouble(d -> (double)d.getHealth() / d.getMaxhealth()))
                .orElse(null);

        // 2. Якщо така ціль є і вона поранена - лікуємо
        if (targetToHeal != null && targetToHeal.getHealth() < targetToHeal.getMaxhealth()) {

            targetToHeal.heal(this.healPower);

            String log = "💚 " + this.name + " лікує " + targetToHeal.getName() + " на " + this.healPower + " HP!";
            System.out.println(log);
            return log;
        }

        // 3. Якщо цілей для лікування немає (залишились тільки здорові або Танки)
        String log = "🌀 " + this.name + " очікує (немає цілей для лікування).";
        System.out.println(log);
        return log;
    }
}


