package com.hr.api.security.oauth.providers;

import java.util.Map;

import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;

public interface OAuthProvider {

	Map<String, String> getAuthInfo(OAuth2AuthenticationToken  token);
}
