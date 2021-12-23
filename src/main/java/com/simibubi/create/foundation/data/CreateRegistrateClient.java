package com.simibubi.create.foundation.data;

import com.simibubi.create.CreateClient;
import com.simibubi.create.content.contraptions.relays.encased.CasingConnectivity;
import com.simibubi.create.foundation.block.connected.CTModel;
import com.simibubi.create.foundation.block.connected.ConnectedTextureBehaviour;
import com.simibubi.create.foundation.block.render.ColoredVertexModel;
import com.simibubi.create.foundation.block.render.IBlockVertexColor;
import com.simibubi.create.foundation.item.render.CustomRenderedItemModelRenderer;
import com.tterrag.registrate.builders.ItemBuilder;
import com.tterrag.registrate.util.nullness.NonNullFunction;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class CreateRegistrateClient {
	@Environment(EnvType.CLIENT)
	public static <T extends Item, P> void customRenderedItem(ItemBuilder<T, P> b,
															  Supplier<Supplier<?>> supplier) {
		b//.properties(p -> p.setISTER(() -> supplier.get()::get))
				.onRegister(entry -> {
					BuiltinItemRendererRegistry.DynamicItemRenderer ister = (CustomRenderedItemModelRenderer) supplier.get().get();
					BuiltinItemRendererRegistry.INSTANCE.register(entry, ister);

					if (ister instanceof CustomRenderedItemModelRenderer)
						registerCustomRenderedItem(entry, (CustomRenderedItemModelRenderer<?>) ister);
				});
	}

	@Environment(EnvType.CLIENT)
	public static void registerCTBehviour(Block entry, ConnectedTextureBehaviour behavior) {
		CreateClient.MODEL_SWAPPER.getCustomBlockModels()
				.register(() -> entry/*.delegate*/, model -> new CTModel(model, behavior));
	}

	@Environment(EnvType.CLIENT)
	public static <T extends Block> void registerCasingConnectivity(T entry,
																	BiConsumer<T, CasingConnectivity> consumer) {
		consumer.accept(entry, CreateClient.CASING_CONNECTIVITY);
	}

	@Environment(EnvType.CLIENT)
	public static void registerBlockVertexColor(Block entry, IBlockVertexColor colorFunc) {
		CreateClient.MODEL_SWAPPER.getCustomBlockModels()
				.register(() -> entry/*.delegate*/, model -> new ColoredVertexModel(model, colorFunc));
	}

	@Environment(EnvType.CLIENT)
	public static void registerBlockModel(Block entry,
										  Supplier<NonNullFunction<?, ?>> func) {
		CreateClient.MODEL_SWAPPER.getCustomBlockModels()
				.register(() -> entry, (NonNullFunction<BakedModel, ? extends BakedModel>) func.get());
	}

	@Environment(EnvType.CLIENT)
	public static void registerItemModel(Item entry,
										 Supplier<NonNullFunction<?, ?>> func) {
		CreateClient.MODEL_SWAPPER.getCustomItemModels()
				.register(() -> entry/*.delegate*/, (NonNullFunction<BakedModel, ? extends BakedModel>) func.get());
	}

	@Environment(EnvType.CLIENT)
	public static void registerCustomRenderedItem(Item entry, Object renderer) {
		CreateClient.MODEL_SWAPPER.getCustomRenderedItems()
				.register(() -> entry, ((CustomRenderedItemModelRenderer) renderer)::createModel);
	}
}
