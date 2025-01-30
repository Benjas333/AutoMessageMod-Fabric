package com.bnjs333.automessage;

import com.mojang.brigadier.arguments.StringArgumentType;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

import java.io.File;

public class AutoMessageModClient implements ClientModInitializer {
	private static long lastModifiedTime = 0;
	private static File configFile;

	@Override
	public void onInitializeClient() {
		AutoConfig.register(ModConfig.class, GsonConfigSerializer::new);
		configFile = new File("./config/automessage.json");
		lastModifiedTime = configFile.lastModified();

		ClientPlayConnectionEvents.JOIN.register((handler, sender, client) -> {
			checkConfigReload();
            sendAutoMessage();
		});

		ClientCommandRegistrationCallback.EVENT.register((commandDispatcher, commandRegistryAccess) -> commandDispatcher.register(ClientCommandManager.literal("automessage")
            .executes(context -> {
                context.getSource().sendFeedback(configString());
                return 1;
            })
            .then(ClientCommandManager.literal("on")
                .executes(context -> {
                    setModState(true);
                    context.getSource().sendFeedback(Text.translatable("command.automessage.feedback.on"));
                    return 1;
                })
            )
            .then(ClientCommandManager.literal("off")
                .executes(context -> {
                    setModState(false);
                    context.getSource().sendFeedback(Text.translatable("command.automessage.feedback.off"));
                    return 1;
                })
            )
            .then(ClientCommandManager.literal("toggle")
                .executes(context -> {
                    toggleModState();
                    context.getSource().sendFeedback(Text.translatable("command.automessage.feedback.toggle"));
                    return 1;
                })
            )
            .then(ClientCommandManager.literal("reload")
                .executes(context -> {
                    checkConfigReload();
                    context.getSource().sendFeedback(Text.translatable("command.automessage.feedback.reload"));
                    return 1;
                })
            )
            .then(ClientCommandManager.literal("set")
                .then(ClientCommandManager.argument("message", StringArgumentType.greedyString())
                    .executes(context -> {
                        String message = context.getArgument("message", String.class);
                        setConfigMessage(message);
                        context.getSource().sendFeedback(Text.translatable("command.automessage.feedback.message.set", message));
                        return 1;
                    })
                )
            )
        ));
	}

	private void setConfigMessage(String message) {
		checkConfigReload();
		ModConfig config = AutoConfig.getConfigHolder(ModConfig.class).getConfig();
        config.message = message;
        AutoConfig.getConfigHolder(ModConfig.class).save();
	}

	private Text configString() {
		checkConfigReload();
		ModConfig config = AutoConfig.getConfigHolder(ModConfig.class).getConfig();
		return Text.translatable("text.automessage.config.string", config.enabled ? Text.translatable("generic.automessage.enabled") : Text.translatable("generic.automessage.disabled"), config.message);
	}

	private void checkConfigReload() {
		if (configFile.lastModified() == lastModifiedTime) return;

		AutoConfig.getConfigHolder(ModConfig.class).load();
		lastModifiedTime = configFile.lastModified();
	}

	private void sendAutoMessage() {
		ModConfig config = AutoConfig.getConfigHolder(ModConfig.class).getConfig();
		if (!config.enabled) return;
		if (config.message.isEmpty()) return;

        assert MinecraftClient.getInstance().player != null;
        MinecraftClient.getInstance().player.sendMessage(Text.of(config.message));
	}

	private void setModState(boolean state) {
		checkConfigReload();
        ModConfig config = AutoConfig.getConfigHolder(ModConfig.class).getConfig();
        config.enabled = state;
        AutoConfig.getConfigHolder(ModConfig.class).save();
    }

	private void toggleModState() {
        setModState(!AutoConfig.getConfigHolder(ModConfig.class).getConfig().enabled);
    }
}