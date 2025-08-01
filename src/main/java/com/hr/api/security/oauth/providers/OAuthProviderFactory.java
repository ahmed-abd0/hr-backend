package com.hr.api.security.oauth.providers;

import com.hr.api.common.util.SpringContext;

public class OAuthProviderFactory {

	
	
	
	public static OAuthProvider getProvider(String provider) {
			
		if (provider.equals("github") ) {
			
			return SpringContext.getBean(GitHubOAuthProvider.class);
		}
		
		if (provider.equals("google")) {
			return SpringContext.getBean(GoogleOAuthProvider.class);
		}
		
		throw new IllegalArgumentException("Unsupported OAuth2 provider: " + provider);
		
	}


}
