package com.simibubi.create.lib.util;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;

import java.util.function.Predicate;

@FunctionalInterface
public interface Condition {
	public static final Condition TRUE = stateDefinition -> blockState -> true;
	public static final Condition FALSE = stateDefinition -> blockState -> false;

	public Predicate<BlockState> getPredicate(StateDefinition<Block, BlockState> var1);
}
