package com.simibubi.create.foundation.block.render;

import com.jozufozu.flywheel.core.StitchedSprite;

import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;

public class SpriteShiftEntry {
	protected StitchedSprite original;
	protected StitchedSprite target;

	public void set(ResourceLocation originalTextureLocation, ResourceLocation targetTextureLocation) {
		if(FabricLoader.getInstance().getEnvironmentType() == EnvType.SERVER) return;
		original = new StitchedSprite(originalTextureLocation);
		target = new StitchedSprite(targetTextureLocation);
	}

	public ResourceLocation getOriginalResourceLocation() {
		return original.getLocation();
	}

	public ResourceLocation getTargetResourceLocation() {
		return target.getLocation();
	}

	public TextureAtlasSprite getOriginal() {
		return original.get();
	}

	public TextureAtlasSprite getTarget() {
		return target.get();
	}
}
