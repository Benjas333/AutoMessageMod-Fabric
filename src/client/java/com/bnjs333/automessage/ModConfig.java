package com.bnjs333.automessage;


import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;

@Config(name = "automessage")
public class ModConfig implements ConfigData {
    public boolean enabled = true;
    public String message = "asd";
}
