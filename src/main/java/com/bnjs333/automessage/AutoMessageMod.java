package com.bnjs333.automessage;

import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AutoMessageMod implements ModInitializer {
	public static final String MOD_ID = "automessage";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("BNJS333, AutoMessageMod Initializing...");
	}
}