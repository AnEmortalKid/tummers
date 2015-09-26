package com.anemortalkid.yummers;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RestController;

@RestController
public abstract class AbstractRESTController {

	@Value("${tummers.guest.user}")
	protected String guestUser;

	@Value("${tummers.guest.password}")
	protected String guestPassword;

	@Value("${yummers.rest.url}")
	protected String yummers_rest_url;

	protected HttpHeaders getBaseAuthorizationHeaders() {
		String plainCreds = guestUser + ":" + guestPassword;
		byte[] plainCredsBytes = plainCreds.getBytes();
		byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes);
		String base64Creds = new String(base64CredsBytes);

		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Basic " + base64Creds);

		return headers;
	}

	protected HttpEntity<String> getRequestEntity() {
		return new HttpEntity<String>(getBaseAuthorizationHeaders());
	}

}
