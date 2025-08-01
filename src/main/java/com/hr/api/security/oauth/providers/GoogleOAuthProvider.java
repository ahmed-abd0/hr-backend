package com.hr.api.security.oauth.providers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

@Component
public class GoogleOAuthProvider implements OAuthProvider {

	@Override
	public Map<String, String> getAuthInfo(OAuth2AuthenticationToken  token) {
		
		OAuth2User oauthUser = token.getPrincipal();
		
		Map<String, String> data = new HashMap<>();
		
		data.put("name", oauthUser.getAttribute("name"));
		data.put("email", oauthUser.getAttribute("email"));
		
		return data;
	}

}
