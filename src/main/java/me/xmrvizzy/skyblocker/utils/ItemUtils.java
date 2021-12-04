package me.xmrvizzy.skyblocker.utils;

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

public class ItemUtils {

    public static List<Text> getTooltip(ItemStack item) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player != null && item != null)
            return item.getTooltip(client.player, TooltipContext.Default.NORMAL);
        return Collections.emptyList();
    }

    private final static Pattern WHITESPACES = Pattern.compile("^\\s*$");

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
                NbtList lore = tag.getList("lore", 8);

                List<String> loreAsList = new ArrayList<>();
                for (int i = 0; i < lore.toArray().length; i++) {
                    loreAsList.add(lore.getString(i));
                }

                return loreAsList;
            }
        }

        return Collections.emptyList();
    }

    private static final Pattern ITEM_COOLDOWN_PATTERN = Pattern.compile("Cooldown: ([0-9]+)s");
    private static final Pattern ALTERNATE_COOLDOWN_PATTERN = Pattern.compile("([0-9]+) Second Cooldown");

    public static int getLoreCooldown(ItemStack item) {
        for (String loreString : getLore(item)) {
            String strippedString = loreString; // TODO: Strip colours
            Matcher matcher = ITEM_COOLDOWN_PATTERN.matcher(strippedString);
            if (!matcher.matches()) {
                matcher = ALTERNATE_COOLDOWN_PATTERN.matcher(strippedString);
                if (!matcher.matches())
                    continue;
            }

            if (matcher.group(1) != null)
                return Integer.parseInt(matcher.group(1));
        }
        return -1;
    }
}
