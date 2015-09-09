package com.anemortalkid.yummers.accounts;

import org.apache.commons.codec.binary.Base64;
import org.springframework.http.HttpHeaders;

public class AuthHelper {

	public static HttpHeaders createBasicAuthHeaders(String user, String password) {
		String plainCreds = user + ":" + password;
		byte[] plainCredsBytes = plainCreds.getBytes();
		byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes);
		String base64Creds = new String(base64CredsBytes);

		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Basic " + base64Creds);
		return headers;
	}
}
