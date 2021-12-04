package me.xmrvizzy.skyblocker.chat.filters;

import me.xmrvizzy.skyblocker.config.SkyblockerConfig;

public class ImplosionFilter extends ChatFilter
{
    public ImplosionFilter() {
        super("^Your Implosion hit " + NUMBER + " enem(?:y|ies) for " + NUMBER + " damage\\.$");
    }

    @Override
    public boolean isEnabled() {
        return SkyblockerConfig.get().messages.hideImplosion;
    }
}