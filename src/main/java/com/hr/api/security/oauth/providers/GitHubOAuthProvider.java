package com.hr.api.security.oauth.providers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;



import org.springframework.http.*;
import org.springframework.core.ParameterizedTypeReference;

import org.springframework.security.oauth2.client.*;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;

import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class GitHubOAuthProvider implements OAuthProvider {
	
	private final OAuth2AuthorizedClientService authorizedClientService;

	@Override
	public Map<String, String> getAuthInfo(OAuth2AuthenticationToken  token) {
	
		OAuth2User oauthUser = token.getPrincipal();
		
		Map<String, String> data = new HashMap<>();
		
		data.put("name", oauthUser.getAttribute("login"));
		data.put("email", oauthUser.getAttribute("email"));
		
		if(data.get("email") == null) {
			 data.put("email", fetchGitHubPrimaryEmail(token));
		}
		
		return data;
	}
	
	 private String fetchGitHubPrimaryEmail(OAuth2AuthenticationToken token) {
	       
		 	OAuth2AuthorizedClient client = getClient(token);
	        String accessToken = client.getAccessToken().getTokenValue();

	  
	        return this.getEmailResponse(accessToken).getBody().stream()
	                .filter(emailMap -> Boolean.TRUE.equals(emailMap.get("primary")))
	                .map(emailMap -> (String) emailMap.get("email"))
	                .findFirst()
	                .orElse(null);
	    }

	 
	 	private ResponseEntity<List<Map<String, Object>>> getEmailResponse(String accessToken) {
 		 	
	 		return this.callApi("https://api.github.com/user/emails",accessToken);
	 	}
	 	
	 	
	 	private ResponseEntity<List<Map<String, Object>>> callApi(String url, String accessToken) {
	 		RestTemplate restTemplate = new RestTemplate();
	        HttpHeaders headers = new HttpHeaders();
	        headers.setBearerAuth(accessToken);
	        HttpEntity<Void> request = new HttpEntity<>(headers);
	        
	        return restTemplate.exchange(
	                url,
	                HttpMethod.GET,
	                request,
	                new ParameterizedTypeReference<>() {}
	        );
	 	}
	
	    private OAuth2AuthorizedClient getClient(OAuth2AuthenticationToken token) {
	    	
	        return authorizedClientService.loadAuthorizedClient(
	                token.getAuthorizedClientRegistrationId(),
	                token.getName()
	        );
	    }
}
