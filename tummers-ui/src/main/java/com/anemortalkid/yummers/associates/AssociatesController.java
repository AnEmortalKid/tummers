package com.anemortalkid.yummers.associates;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/associates")
public class AssociatesController {

	@Value("${tummers.guest.user}")
	private String guestUser;

	@Value("${tummers.guest.password}")
	private String guestPassword;

	@Value("${yummers.rest.url}")
	private String yummers_rest_url;

	@RequestMapping("/{ids}")
	public List<AssociateData> associateIds(@PathVariable("ids") List<String> ids) {
		String plainCreds = guestUser + ":" + guestPassword;
		byte[] plainCredsBytes = plainCreds.getBytes();
		byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes);
		String base64Creds = new String(base64CredsBytes);

		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Basic " + base64Creds);

		List<AssociateData> data = new ArrayList<>();

		RestTemplate restTemplate = new RestTemplate();
		HttpEntity<String> request = new HttpEntity<String>(headers);
		for (String id : ids) {
			ResponseEntity<AssociateData> response = restTemplate.exchange(yummers_rest_url + "associates/" + id,
					HttpMethod.GET, request, AssociateData.class);
			AssociateData associateData = response.getBody();
			associateData.setAssociateId(id);
			data.add(associateData);
		}

		return data;
	}

}
