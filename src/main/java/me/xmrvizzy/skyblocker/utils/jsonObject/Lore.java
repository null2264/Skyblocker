package me.xmrvizzy.skyblocker.utils.jsonObject;

import java.util.Collections;
import java.util.Dictionary;
import java.util.List;

public class Lore
{
    public boolean italic;
    public LoreExtra[] extra;
    public String text;

    public Lore() {
    }

    public Lore(boolean italic, LoreExtra[] extra, String text){
        this.italic = italic;
        this.extra = extra;
        this.text = text;
    }

    public Lore(boolean italic, String text){
        this.italic = italic;
        this.extra = null;
        this.text = text;
    }
}