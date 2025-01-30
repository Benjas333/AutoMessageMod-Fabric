# AutoMessageMod (Fabric 1.19.4)
A client-side Minecraft Fabric mod that allows you to send a message automatically when joining a world/server.

## Table of Contents
- [Requirements](#requirements)
- [Commands](#commands)
  - [automessage](#automessage)
  - [automessage on](#automessage-on)
  - [automessage off](#automessage-off)
  - [automessage toggle](#automessage-toggle)
  - [automessage reload](#automessage-reload)
  - [automessage set](#automessage-set)

## Requirements
- [fabric-api](https://www.curseforge.com/minecraft/mc-mods/fabric-api)
- [cloth-config](https://www.curseforge.com/minecraft/mc-mods/cloth-config)
- (Optional) [ModMenu](https://www.curseforge.com/minecraft/mc-mods/modmenu)

## Commands
> [!NOTE]
> The commands were implemented in case you don't use ModMenu. But you can change the config/automessage.json and the configuration will be updated without restarting the game. In other words, the commands are not precisely important, you can either directly change the config file or use ModMenu.
- ### automessage
Prints the actual configuration.

- ### automessage on
Changes the mod status to enabled.

- ### automessage off
Changes the mod status to disabled.

- ### automessage toggle
Toggles the mod status.

- ### automessage reload
Manually reloads the mod configuration.

- ### automessage set
Changes the Auto Message into another one.

Param | Description | Type
------------- | ------------- | -------------
message | The new message to auto-send. | String

## Contributing
Any contribution would be appreciated.

## Links
[Twitter](https://twitter.com/ElBenjas333)
