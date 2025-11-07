package com.itproger.droid.rear;

import com.itproger.droid.Droid;

import java.util.List;

public abstract class RearDroid extends Droid {

    public RearDroid(String name, int health) {
        super(name, health);
    }

    @Override
    public abstract String performBattleAction(List<Droid> allies, List<Droid> enemies);
}
