package com.anemortalkid.yummers.accounts;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;

@RestController
@RequestMapping("/accounts")
public class AccountsController {

	@Value("${tummers.guest.user}")
	private String guestUser;

	@Value("${tummers.guest.password}")
	private String guestPassword;

	@Value("${yummers.rest.url}")
	private String yummers_rest_url;

	private Map<String, UserDetails> usersBySessionId = new HashMap<>();

	@RequestMapping(value = "/details", method = RequestMethod.POST)
	public AccountDetails getDetails(@RequestBody AccountCredentials credentials) {
		// accounts/checkCredentials
		HttpHeaders headers = AuthHelper.createBasicAuthHeaders(guestUser, guestPassword);
		headers.setContentType(MediaType.APPLICATION_JSON);

		RestTemplate restTemplate = new RestTemplate();
		HttpEntity<AccountCredentials> request = new HttpEntity<>(credentials, headers);
		ResponseEntity<AccountDetails> response = restTemplate.exchange(yummers_rest_url + "accounts/checkCredentials",
				HttpMethod.POST, request, AccountDetails.class);
		return response.getBody() != null ? response.getBody() : null;
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public boolean isAuthorized(@RequestBody AccountCredentials credentials, HttpServletResponse response,
			HttpServletRequest httpRequest) {
		String sessionId = httpRequest.getHeader("JSESSIONID");
		String springSessionId = RequestContextHolder.getRequestAttributes().getSessionId();

		AccountDetails details = getDetails(credentials);
		if (details != null) {
			if (details.getAccessLevel().equals("ROLE_ADMIN") || details.getAccessLevel().equals("ROLE_SUPER")) {
				usersBySessionId.put(springSessionId, new UserDetails(credentials.getUsername()));
				return true;
			}
		}

		return false;
	}

	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	public void logout(HttpServletRequest httpRequest) {
		System.out.println("logginout");
	}

	@RequestMapping(value = "/user", method = RequestMethod.GET)
	public UserDetails user(HttpServletRequest httpRequest) {
		String sessionid = RequestContextHolder.getRequestAttributes().getSessionId();
		UserDetails details = usersBySessionId.get(sessionid);
		return details;
	}

}
