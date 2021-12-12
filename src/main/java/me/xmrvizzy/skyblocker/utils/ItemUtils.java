package me.xmrvizzy.skyblocker.utils;

import com.google.gson.Gson;
import me.xmrvizzy.skyblocker.utils.jsonObject.Lore;
import me.xmrvizzy.skyblocker.utils.jsonObject.LoreExtra;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ItemUtils
{
    private static Gson g = new Gson();
    private static final Pattern WHITESPACES = Pattern.compile("^\\s*$");
    private static final Pattern ITEM_COOLDOWN_PATTERN = Pattern.compile("Cooldown: ([0-9]+)s");
    private static final Pattern ALTERNATE_COOLDOWN_PATTERN = Pattern.compile("([0-9]+) Second Cooldown");

    public static List<Text> getTooltip(ItemStack item) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player != null && item != null)
            return item.getTooltip(client.player, TooltipContext.Default.NORMAL);
        return Collections.emptyList();
    }

    public static List<String> getTooltipStrings(ItemStack item) {
        List<Text> lines = getTooltip(item);
        List<String> list = new ArrayList<>();

        for (Text line : lines) {
            String string = line.getString();
            if (!WHITESPACES.matcher(string).matches())
                list.add(string);
        }

        return list;
    }

    public static List<String> getLore(ItemStack item) {
        if (item.hasNbt() && item.getNbt().contains("display", 10)) {
            NbtCompound tag = item.getNbt().getCompound("display");

            if (tag.contains("Lore", 9)) {
                NbtList lore = tag.getList("Lore", 8);

                List<String> loreAsList = new ArrayList<>();
                for (int i = 0; i < lore.toArray().length; i++) {
                    Lore loreJson = g.fromJson(lore.getString(i), Lore.class);
                    StringBuilder loreString = new StringBuilder("");

                    loreString.append(loreJson.text);
                    if (loreJson.extra != null)
                        for (LoreExtra extra : loreJson.extra)
                            loreString.append(extra.text);
                    loreAsList.add(loreString.toString());
                }

                return loreAsList;
            }
        }

        return Collections.emptyList();
    }

    public static int getLoreCooldown(ItemStack item) {
        for (String loreString : getLore(item)) {
            Matcher matcher = ITEM_COOLDOWN_PATTERN.matcher(loreString);
            if (!matcher.matches()) {
                matcher = ALTERNATE_COOLDOWN_PATTERN.matcher(loreString);
                if (!matcher.matches())
                    continue;
            }

            if (matcher.group(1) != null)
                return Integer.parseInt(matcher.group(1));
        }
        return -1;
    }
}
