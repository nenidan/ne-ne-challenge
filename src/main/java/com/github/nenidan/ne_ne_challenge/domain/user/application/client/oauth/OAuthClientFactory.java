package com.github.nenidan.ne_ne_challenge.domain.user.application.client.oauth;

import com.github.nenidan.ne_ne_challenge.domain.user.application.client.oauth.type.Provider;
import com.github.nenidan.ne_ne_challenge.domain.user.presentation.dto.request.OAuthLoginRequest;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Component
public class OAuthClientFactory {

    private final Map<Provider, OAuthClient> oAuthClientMap = new EnumMap<>(Provider.class);

    public OAuthClientFactory(List<OAuthClient> clients) {
        for (OAuthClient client: clients) {
            oAuthClientMap.put(client.getProvider(), client);
        }
    }

    public OAuthClient create(Provider provider) {
        return oAuthClientMap.get(provider);
    }
}
