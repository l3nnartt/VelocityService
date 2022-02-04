package com.github.l3nnartt.commands;

import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.server.RegisteredServer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

public final class HubCommand implements SimpleCommand {

    private final ProxyServer server;

    public HubCommand(ProxyServer server) {
        this.server = server;
    }

    @Override
    public void execute(final Invocation invocation) {
        Player player = (Player) invocation.source();
        // Get the arguments after the command alias
        String[] args = invocation.arguments();

        RegisteredServer registeredServer = server.getServer("lobby").get();

        player.sendMessage(Component.text("Moving to Lobby").color(NamedTextColor.AQUA));
        player.createConnectionRequest(registeredServer).connect();
    }

    //@Override
    //public boolean hasPermission(final Invocation invocation) {
    //    return invocation.source().hasPermission("command.test");
    //}

}