package io.github.tt432.theelixir.utils;

import io.github.tt432.theelixir.TheElixir;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.minecraft.resources.ResourceLocation;

/**
 * @author TT432
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ResourceLocations {
    public static ResourceLocation mod(String path) {
        return of(TheElixir.MOD_ID, path);
    }

    public static ResourceLocation of(String value) {
        return ResourceLocation.parse(value);
    }

    public static ResourceLocation of(String namespace, String path) {
        return ResourceLocation.fromNamespaceAndPath(namespace, path);
    }
}
