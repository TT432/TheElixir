package com.nmmoc7.theelixir.config;

import com.nmmoc7.theelixir.TheElixir;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

/**
 * @author DustW
 */
public class EntityTypeTaggedListConfigValue extends TaggedListConfigValue<EntityType<?>> {
    public EntityTypeTaggedListConfigValue(ForgeConfigSpec.ConfigValue<List<? extends String>> configValue, LoadListeningConfigManagerAbstract manager) {
        super(configValue, manager);
    }

    @Override
    protected EntityType<?> string2SingletonValue(String raw) {
        ResourceLocation resourceLocation = new ResourceLocation(raw);
        IForgeRegistry<EntityType<?>> entities = ForgeRegistries.ENTITIES;
        ResourceLocation defaultKey = entities.getDefaultKey();
        EntityType<?> entityType = entities.getValue(resourceLocation);

        boolean flag = resourceLocation.equals(defaultKey) == (entityType == entities.getValue(defaultKey));

        if (entityType != null && flag) {
            return entityType;
        }

        TheElixir.LOGGER.error("无法从config中加载实体: " + raw);
        return null;
    }

    @Override
    public boolean contains(EntityType<?> val) {
        return getSingletons().contains(val) ||
                getTags().stream()
                        .map(EntityTypeTags.getCollection()::getTagByID)
                        .anyMatch(tag -> tag.contains(val));
    }

    public boolean contains(Entity val) {
        return contains(val.getType());
    }
}
