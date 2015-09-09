package com.anemortalkid.yummers.accounts;

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

@RestController
@RequestMapping("/accounts")
public class AccountsController {

	@Value("${tummers.guest.user}")
	private String guestUser;

	@Value("${tummers.guest.password}")
	private String guestPassword;

	@Value("${yummers.rest.url}")
	private String yummers_rest_url;

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

}
