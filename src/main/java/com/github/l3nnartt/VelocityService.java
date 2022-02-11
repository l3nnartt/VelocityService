package com.github.l3nnartt;

import com.github.l3nnartt.commands.HubCommand;
import com.google.inject.Inject;
import com.velocitypowered.api.command.CommandManager;
import com.velocitypowered.api.event.connection.PreLoginEvent;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyPingEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.server.ServerPing;
import com.velocitypowered.api.util.Favicon;
import net.kyori.adventure.text.Component;
import org.slf4j.Logger;

@Plugin(
        id = "velocityservice",
        name = "VelocityService",
        version = "1.0",
        description = "VelocityService",
        authors = {"L3nnart_"}
)
public class VelocityService {

    @Inject
    private Logger logger;
    private Favicon faviconCosmeticsMod = new Favicon("data:image/png;base64,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");

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {

    }

    @Subscribe
    public void onPreLogin(PreLoginEvent event) {
        event.getConnection().getVirtualHost().ifPresent(address -> {
            System.out.println(address.getHostString());
        });
    }

    @Subscribe
    public void onPingEvent(ProxyPingEvent event) {
        event.getConnection().getVirtualHost().ifPresent(address -> {
            if (address.getHostString().equals("_dc-srv.e42ed11a80fb._minecraft._tcp.cosmeticsmod.team")) {
                ServerPing ping = event.getPing().asBuilder().description(Component.text("\u00A76cosmeticsmod.com -\u00A7b CosmeticsMod\u00A77 - Server [\u00A7a1.8\u00A77-\u00A7a1.18\u00A77]\u00A7r\n\u00A7c        PVP AREA \u00A7r&\u00A7c SHOWROOM\u00A7r & \u00A7cSTAGE")).favicon(faviconCosmeticsMod).build();
                event.setPing(ping);
            }
            if (address.getHostString().equals("test.cosmeticsmod.de")) {
                ServerPing ping = event.getPing().asBuilder().description(Component.text("                    \u00a79CosmeticsMod                \u00a7r[\u00a7a1.8\u00a7r-\u00a7a1.18.1\u00a7r]")).favicon(faviconCosmeticsMod).build();
                event.setPing(ping);
            }
        });
    }

    @Inject
    private VelocityService(ProxyServer proxyServer, CommandManager commandManager, Logger logger) {
        commandManager.register("hub", new HubCommand(proxyServer));
        commandManager.register("lobby", new HubCommand(proxyServer));
        logger.info("Plugin has enabled!");
    }

}