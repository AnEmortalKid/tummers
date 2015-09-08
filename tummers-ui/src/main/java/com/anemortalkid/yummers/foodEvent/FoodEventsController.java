package com.anemortalkid.yummers.foodEvent;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/foodEvents")
public class FoodEventsController {

	@Value("${tummers.guest.user}")
	private String guestUser;

	@Value("${tummers.guest.password}")
	private String guestPassword;

	@Value("${yummers.rest.url}")
	private String yummers_rest_url;

	@RequestMapping("/upcoming")
	public FoodEventData upcoming() {
		String plainCreds = guestUser + ":" + guestPassword;
		byte[] plainCredsBytes = plainCreds.getBytes();
		byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes);
		String base64Creds = new String(base64CredsBytes);

		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Basic " + base64Creds);

		RestTemplate restTemplate = new RestTemplate();
		HttpEntity<String> request = new HttpEntity<String>(headers);
		ResponseEntity<FoodEventData> response = restTemplate.exchange(yummers_rest_url + "foodEvents/upcoming",
				HttpMethod.GET, request, FoodEventData.class);
		return response.getBody();
	}

	@RequestMapping("/calendarEvents")
	public List<FoodEventCalendarData> calendarData() {
		String plainCreds = guestUser + ":" + guestPassword;
		byte[] plainCredsBytes = plainCreds.getBytes();
		byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes);
		String base64Creds = new String(base64CredsBytes);

		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Basic " + base64Creds);

		RestTemplate restTemplate = new RestTemplate();
		HttpEntity<String> request = new HttpEntity<String>(headers);
		ParameterizedTypeReference<List<FoodEventData>> myData = new ParameterizedTypeReference<List<FoodEventData>>() {
		};
		ResponseEntity<List<FoodEventData>> responseEntity = restTemplate
				.exchange(yummers_rest_url + "foodEvents/listActive", HttpMethod.GET, request, myData);
		List<FoodEventData> foodEventData = responseEntity.getBody();
		List<FoodEventCalendarData> eventCalendarData = foodEventData.stream().map(fed -> {
			FoodEventCalendarData fecd = new FoodEventCalendarData();
			fecd.setStart(fed.getSlot().getSlotDate().toString("yyyy-MM-dd"));
			fecd.setTitle(fed.getBreakfastParticipants() + "," + fed.getSnackParticipants());
			return fecd;
		}).collect(Collectors.toList());
		return eventCalendarData;
	}
}
