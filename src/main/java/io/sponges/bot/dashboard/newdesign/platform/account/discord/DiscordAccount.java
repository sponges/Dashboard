package io.sponges.bot.dashboard.newdesign.platform.account.discord;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.HttpRequest;
import io.sponges.bot.dashboard.newdesign.platform.account.Account;
import io.sponges.bot.dashboard.newdesign.platform.network.Network;
import io.sponges.bot.dashboard.newdesign.platform.network.NetworkImpl;

import java.util.ArrayList;
import java.util.List;

public interface DiscordAccount extends Account {

    String REDIRECT_URL = "http%3A%2F%2Flocalhost%3A4567%2Fdiscord_endpoint";

    String AUTHORIZATION_URL = "https://discordapp.com/oauth2/authorize?response_type=code&client_id=%s&scope=identify+guilds&access_type=offline";
    String TOKEN_URL = "https://discordapp.com/api/oauth2/token?client_id=%s&client_secret=%s&grant_type=authorization_code&scope=identify+guilds&code=%s&redirect_uri=%s";
    String REFRESH_TOKEN_URL = "https://discordapp.com/api/oauth2/token?client_id=%s&client_secret=%s&grant_type=refresh_token&scope=identify+guilds&refresh_token=%s&redirect_uri=%s";
    String LIST_GUILDS_URL = "https://discordapp.com/api/users/@me/guilds";
    String USER_INFO_URL = "https://discordapp.com/api/users/@me";

    String AUTHORIZATION_HEADER_KEY = "Authorization";
    String AUTHORIZATION_HEADER_VALUE = "Bearer %s";

    String getToken();

    String getRefreshToken();

    int getExpiryTime();

    int getStartTime();

    default HttpRequest appendAuthorizationHeader(HttpRequest request) {
        return request.header(AUTHORIZATION_HEADER_KEY, String.format(AUTHORIZATION_HEADER_VALUE, getToken()));
    }

    DiscordGuild[] getGuilds() throws UnirestException;

    DiscordUser getUser() throws UnirestException;

    default boolean hasTokenExpired() {
        return getStartTime() + getExpiryTime() < 120; // if there is 120s or less left on the token
    }

    void refreshToken();

    default Network[] getNetworks() {
        DiscordGuild[] guilds;
        try {
            guilds = getGuilds();
        } catch (UnirestException e) {
            e.printStackTrace();
            return null;
        }
        List<Network> networks = new ArrayList<>(guilds.length);
        for (DiscordGuild guild : guilds) {
            if (!guild.isOwner()) continue;
            networks.add(new NetworkImpl(guild.getName(), guild.getId(), guild.getIcon()));
        }
        Network[] array = networks.toArray(new Network[networks.size()]);
        updateNetworkCache(array);
        return array;
    }

    default String getPlatform() {
        return "discord";
    }

}
