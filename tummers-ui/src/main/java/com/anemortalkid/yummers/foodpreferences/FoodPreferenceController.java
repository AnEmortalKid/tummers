package com.anemortalkid.yummers.foodpreferences;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.anemortalkid.yummers.AbstractRESTController;

@RestController
@RequestMapping("/foodPreferences")
public class FoodPreferenceController extends AbstractRESTController {

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public List<FoodPreferenceTableData> listAllPreferences() {
		HttpHeaders headers = getBaseAuthorizationHeaders();

		HttpEntity<String> request = new HttpEntity<String>(headers);

		RestTemplate restTemplate = new RestTemplate();
		ParameterizedTypeReference<List<FoodPreferenceData>> myData = new ParameterizedTypeReference<List<FoodPreferenceData>>() {
		};

		ResponseEntity<List> listish = restTemplate.exchange(yummers_rest_url + "foodPreferences/list", HttpMethod.GET,
				request, List.class);
		ResponseEntity<List<FoodPreferenceData>> responseEntity = restTemplate
				.exchange(yummers_rest_url + "foodPreferences/list", HttpMethod.GET, request, myData);
		List<FoodPreferenceData> foodPreferenceData = responseEntity.getBody();
		return foodPreferenceData.stream().map(fpd -> {
			FoodPreferenceTableData fptd = new FoodPreferenceTableData();
			fptd.setFirstName(fpd.getAssociate().getFirstName());
			fptd.setLastName(fpd.getAssociate().getLastName());
			fptd.setPreference(fpd.getPreferenceType());
			return fptd;
		}).collect(Collectors.toList());
	}

}
