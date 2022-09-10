package com.etmy.onlinerpg;

import com.etmy.onlinerpg.location.StartRoom;
import com.etmy.onlinerpg.location.Location;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class User {
    private Location currentLocation;
    private Account account;
    private int hp;
    private int strength;
    private int agility;
    private int stamina;
    private int intellect;

    {
        this.currentLocation = new StartRoom();
        this.hp = 100;
        this.strength = 10;
        this.agility = 10;
        this.stamina = 10;
        this.intellect = 10;
    }
}