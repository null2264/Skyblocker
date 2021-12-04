package me.xmrvizzy.skyblocker.chat.filters;

import me.xmrvizzy.skyblocker.config.SkyblockerConfig;

public class MoltenWaveFilter extends ChatFilter
{
    public MoltenWaveFilter() {
        super("^Your Molten Wave hit " + NUMBER + " enemy(?:y|ies) for " + NUMBER + " damage\\.$");
    }

    @Override
    public boolean isEnabled() {
        return SkyblockerConfig.get().messages.hideMoltenWave;
    }
}