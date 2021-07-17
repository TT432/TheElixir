package org.teacon.theelixir.item;

import net.minecraft.block.Blocks;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import org.teacon.theelixir.TheElixir;

import javax.annotation.Nullable;
import java.util.List;

/**
 * @author DustW
 */
public class AssRevealGoggles extends ArmorItem {
    public AssRevealGoggles() {
        super(new IArmorMaterial() {
            @Override
            public int getDurability(EquipmentSlotType slotIn) {
                return 9999;
            }

            @Override
            public int getDamageReductionAmount(EquipmentSlotType slotIn) {
                return 1;
            }

            @Override
            public int getEnchantability() {
                return 15;
            }

            @Override
            public SoundEvent getSoundEvent() {
                return SoundEvents.ITEM_ARMOR_EQUIP_IRON;
            }

            @Override
            public Ingredient getRepairMaterial() {
                return Ingredient.fromStacks(new ItemStack(Blocks.BEDROCK));
            }

            @Override
            public String getName() {
                return new ResourceLocation(TheElixir.MOD_ID, "ass_reveal_goggles").toString();
            }

            @Override
            public float getToughness() {
                return 0;
            }

            @Override
            public float getKnockbackResistance() {
                return 0;
            }
        }, EquipmentSlotType.HEAD, new Properties().group(ModItemGroup.INSTANCE).maxStackSize(1));

        setRegistryName(new ResourceLocation(TheElixir.MOD_ID, "ass_reveal_goggles"));
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        tooltip.add(new StringTextComponent("戴上以揭示“屁股”"));
    }
}
