package com.minyan.karov.services.user;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.minyan.karov.entities.GoogleTokenClaims;

@Service
public class GoogleSignInService 
{
	private RestTemplate restTemplate = new RestTemplate();
	
	public GoogleTokenClaims retriveGoogleTokenClaims(String googleTokenId)
	{
		try
		{
			String googleAuthUrl = "https://www.googleapis.com/oauth2/v3/tokeninfo?id_token=" + googleTokenId;
			GoogleTokenClaims googleTokenClaims = restTemplate.getForObject(googleAuthUrl, GoogleTokenClaims.class);
			return googleTokenClaims;
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			return null;
		}
	}
}

