package net.forexample.startcommand.world.structure;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.settings.DimensionStructuresSettings;
import net.minecraft.world.gen.settings.StructureSeparationSettings;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.forexample.startcommand.CommandMod;
import net.forexample.startcommand.world.structure.structures.HouseStructure;
import java.util.HashMap;
import java.util.Map;

public class ModStructures {
    public static final DeferredRegister<Structure<?>> STRUCTURES =
            DeferredRegister.create(ForgeRegistries.STRUCTURE_FEATURES, CommandMod.MOD_ID);

    public static final RegistryObject<Structure<NoFeatureConfig>> HOUSE =
            STRUCTURES.register("house", HouseStructure::new);

    //Avg distance apart in chunks, Min distance apart in chunks, Seed
    public static void setupStructures() {
        setupMapSpacingAndLand(HOUSE.get(),
                new StructureSeparationSettings(100,50, 1234567890),
                true);
    }

    /* Adds the provided structure to the registry, and adds the separation settings.
     * The rarity of the structure is determined based on the values passed into
     * this method in the structureSeparationSettings argument.
     * This method is called by setupStructures above. */
    public static <F extends Structure<?>> void setupMapSpacingAndLand(F structure, StructureSeparationSettings structureSeparationSettings,
                                                                       boolean transformSurroundingLand) {
        //add our structures into the map in Structure class
        Structure.NAME_STRUCTURE_BIMAP.put(structure.getRegistryName().toString(), structure);

        //Whether surrounding land will be modified automatically to conform to the bottom of the structure. It adds land at the base of the structure like it does for Villages and Outposts.
        if (transformSurroundingLand) {
            Structure.field_236384_t_ = ImmutableList.<Structure<?>>builder()
                    .addAll(Structure.field_236384_t_)
                    .add(structure)
                    .build();
        }

        //This is the map that holds the default spacing of all structures. DEFAULTS requires AccessTransformer  (See resources/META-INF/accesstransformer.cfg)
        DimensionStructuresSettings.field_236191_b_ =
                ImmutableMap.<Structure<?>, StructureSeparationSettings>builder()
                        .putAll(DimensionStructuresSettings.field_236191_b_)
                        .put(structure, structureSeparationSettings)
                        .build();

        //There are very few mods that relies on seeing your structure in the noise settings registry before the world is made.
        WorldGenRegistries.NOISE_SETTINGS.getEntries().forEach(settings -> {
            Map<Structure<?>, StructureSeparationSettings> structureMap =
                    settings.getValue().getStructures().func_236195_a_();
            // Pre-caution in case a mod makes the structure map immutable. structureConfig requires AccessTransformer  (See resources/META-INF/accesstransformer.cfg)
            if (structureMap instanceof ImmutableMap) {
                Map<Structure<?>, StructureSeparationSettings> tempMap = new HashMap<>(structureMap);
                tempMap.put(structure, structureSeparationSettings);
                settings.getValue().getStructures().func_236195_a_();
            } else {
                structureMap.put(structure, structureSeparationSettings);
            }
        });
    }

    public static void register(IEventBus eventBus) {
        STRUCTURES.register(eventBus);
    }
}
