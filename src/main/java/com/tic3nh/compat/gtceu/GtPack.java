package com.tic3nh.compat.gtceu;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Map;
import java.util.Set;

import javax.annotation.Nullable;

import net.minecraft.SharedConstants;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.metadata.MetadataSectionSerializer;
import net.minecraft.server.packs.metadata.pack.PackMetadataSection;
import net.minecraft.server.packs.resources.IoSupplier;

import com.tic3nh.compat.gtceu.GtGen.Generated;

public final class GtPack implements PackResources {

    private final String id;
    @Nullable
    private Generated generated;

    public GtPack(String id) {
        this.id = id;
    }

    private Generated generated() {
        Generated g = this.generated;
        if (g == null) {
            try {
                g = GtGen.generate();
            } catch (Throwable t) {
                g = new Generated(Map.of(), Map.of());
            }
            this.generated = g;
        }
        return g;
    }

    private Map<ResourceLocation, byte[]> forType(PackType packType) {
        Generated g = generated();
        return packType == PackType.SERVER_DATA ? g.data() : g.assets();
    }

    @Nullable
    @Override
    public IoSupplier<InputStream> getResource(PackType packType, ResourceLocation location) {
        byte[] bytes = forType(packType).get(location);
        return bytes == null ? null : () -> new ByteArrayInputStream(bytes);
    }

    @Override
    public void listResources(PackType packType, String namespace, String path, ResourceOutput out) {
        for (Map.Entry<ResourceLocation, byte[]> e : forType(packType).entrySet()) {
            ResourceLocation loc = e.getKey();
            if (loc.getNamespace().equals(namespace) && loc.getPath().startsWith(path)) {
                byte[] bytes = e.getValue();
                out.accept(loc, () -> new ByteArrayInputStream(bytes));
            }
        }
    }

    @Override
    public Set<String> getNamespaces(PackType packType) {
        return forType(packType).isEmpty() ? Set.of() : Set.of("tic3nh");
    }

    @Nullable
    @Override
    public IoSupplier<InputStream> getRootResource(String... elements) {
        return null;
    }

    @SuppressWarnings("unchecked")
    @Nullable
    @Override
    public <T> T getMetadataSection(MetadataSectionSerializer<T> deserializer) {
        if (deserializer == PackMetadataSection.TYPE) {

            return (T) new PackMetadataSection(
                    Component.literal("A Tinkers' New Horizon — GTCEu materials"),
                    SharedConstants.getCurrentVersion().getPackVersion(PackType.SERVER_DATA));
        }
        return null;
    }

    @Override
    public String packId() {
        return id;
    }

    @Override
    public void close() {

    }
}
