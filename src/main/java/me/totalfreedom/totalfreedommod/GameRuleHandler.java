package me.totalfreedom.totalfreedommod;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import me.totalfreedom.totalfreedommod.config.ConfigEntry;
import org.bukkit.Bukkit;
import org.bukkit.World;

public class GameRuleHandler extends FreedomService
{
    private final Map<GameRule, Boolean> rules = new EnumMap<>(GameRule.class);

    public GameRuleHandler()
    {
        for (GameRule gameRule : GameRule.values())
        {
            rules.put(gameRule, gameRule.getDefaultValue());
        }
    }

    @Override
    public void onStart()
    {
        setGameRule(GameRule.DO_DAYLIGHT_CYCLE, !ConfigEntry.DISABLE_NIGHT.getBoolean(), false);
        setGameRule(GameRule.DO_FIRE_TICK, ConfigEntry.ALLOW_FIRE_SPREAD.getBoolean(), false);
        setGameRule(GameRule.DO_MOB_LOOT, false, false);
        setGameRule(GameRule.DO_MOB_SPAWNING, !ConfigEntry.MOB_LIMITER_ENABLED.getBoolean(), false);
        setGameRule(GameRule.DO_TILE_DROPS, false, false);
        setGameRule(GameRule.MOB_GRIEFING, false, false);
        setGameRule(GameRule.COMMAND_BLOCK_OUTPUT, false);
        setGameRule(GameRule.NATURAL_REGENERATION, true, false);
        setGameRule(GameRule.KEEP_INVENTORY, true, false);
        setGameRule(GameRule.ANNOUNCE_ADVANCEMENTS, false, false);
        setGameRule(GameRule.SHOW_DEATH_MESSAGES, false, false);
        setGameRule(GameRule.SEND_COMMAND_FEEDBACK, false, false);
        commitGameRules();
    }

    @Override
    public void onStop()
    {
    }

    public void setGameRule(GameRule gameRule, boolean value)
    {
        setGameRule(gameRule, value, true);
    }

    public void setGameRule(GameRule gameRule, boolean value, boolean doCommit)
    {
        rules.put(gameRule, value);
        if (doCommit)
        {
            commitGameRules();
        }
    }

    @SuppressWarnings("deprecation")
    public void commitGameRules()
    {
        List<World> worlds = server.getWorlds();
        for (Map.Entry<GameRule, Boolean> gameRuleEntry : rules.entrySet())
        {
            String gameRuleName = gameRuleEntry.getKey().getGameRuleName();
            String gameRuleValue = gameRuleEntry.getValue().toString();

            for (World world : worlds)
            {
                world.setGameRuleValue(gameRuleName, gameRuleValue);
                if (gameRuleEntry.getKey() == GameRule.DO_DAYLIGHT_CYCLE && !gameRuleEntry.getValue())
                {
                    long time = world.getTime();
                    time -= time % 24000;
                    world.setTime(time + 24000 + 6000);
                }
            }
        }
    }

    public enum GameRule
    {
        DO_FIRE_TICK("doFireTick", true),
        MOB_GRIEFING("mobGriefing", true),
        KEEP_INVENTORY("keepInventory", false),
        DO_MOB_SPAWNING("doMobSpawning", true),
        DO_MOB_LOOT("doMobLoot", true),
        DO_TILE_DROPS("doTileDrops", true),
        COMMAND_BLOCK_OUTPUT("commandBlockOutput", true),
        NATURAL_REGENERATION("naturalRegeneration", true),
        DO_DAYLIGHT_CYCLE("doDaylightCycle", true),
        ANNOUNCE_ADVANCEMENTS("announceAdvancements", false),
        SHOW_DEATH_MESSAGES("showDeathMessages", false),
        SEND_COMMAND_FEEDBACK("sendCommandFeedback", false);

        private final String gameRuleName;
        private final boolean defaultValue;

        GameRule(String gameRuleName, boolean defaultValue)
        {
            this.gameRuleName = gameRuleName;
            this.defaultValue = defaultValue;
        }

        public String getGameRuleName()
        {
            return gameRuleName;
        }

        public boolean getDefaultValue()
        {
            return defaultValue;
        }
    }
}