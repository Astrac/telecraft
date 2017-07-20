# Telecraft

A plugin for the Spigot minecraft server that interfaces with a Telegram bot. Inviting the bot to a chat and configuring the plugin correctly you can receive notifications when someone enters the server.

## Installation ##

You can build Telecraft using sbt and the [assembly plugin](https://github.com/sbt/sbt-assembly) (already included in the project configuration). Install sbt if you don't have it and from the root folder of the proejct run:

    sbt assembly

This will produce a fat-jar containing all the dependencies of the plugin in `target/scala-2.11/`. You can copy it to the plugin folder of your Spigot installation and proceed to the Configuration step.

## Configuration ##

The plugin will need a _bot token_ and a _chat id_ to connect to Telegram. You will have a bot token as soon as you create a telegram bot but you will have to meddle a bit with the APIs to get the chat id; this will hopefully be improved in a subsequent release.

To configure telecraft go to your spigot plugins folder and make sure that it contains a `Telecraft` folder; in that put a `config.yml` with the following structure:

    telecraft:
        bot_token: {{ bot_token }}
        chat_id: {{ chat_id }}

That should be enough to get started and the plugin should load and not raise any warning when you will reload spigot.
