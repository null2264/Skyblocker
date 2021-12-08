package me.xmrvizzy.skyblocker;

import me.xmrvizzy.skyblocker.config.SkyblockerConfig;
import me.xmrvizzy.skyblocker.skyblock.HotbarSlotLock;
import me.xmrvizzy.skyblocker.skyblock.item.PriceInfoTooltip;
import me.xmrvizzy.skyblocker.utils.ItemUtils;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v1.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v1.FabricClientCommandSource;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.text.Text;

public class SkyblockerInitializer implements ClientModInitializer
{
    @Override
    public void onInitializeClient() {
        HotbarSlotLock.init();
        SkyblockerConfig.init();
        PriceInfoTooltip.init();

        CommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> {
            ClientCommandManager.DISPATCHER.register(ClientCommandManager.literal("cooldown").executes(ctx -> {
                FabricClientCommandSource source = ctx.getSource();
                ClientPlayerEntity player = source.getPlayer();
                player.sendMessage(Text.of(Integer.toString(ItemUtils.getLoreCooldown(player.getMainHandStack()))), false);
                return 0;
            }));
        });
    }
}
