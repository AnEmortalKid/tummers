package com.anemortalkid.yummers.foodpreferences;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
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

		ResponseEntity<List<FoodPreferenceData>> responseEntity = restTemplate
				.exchange(yummers_rest_url + "foodPreferences/list", HttpMethod.GET, request, myData);
		List<FoodPreferenceData> foodPreferenceData = responseEntity.getBody();

		ResponseEntity<List<FoodPreferenceData>> missing = restTemplate
				.exchange(yummers_rest_url + "foodPreferences/missing", HttpMethod.GET, getRequestEntity(), myData);

		FPDtoFPTD function = new FPDtoFPTD();
		List<FoodPreferenceTableData> participantsWithPreference = foodPreferenceData.stream().map(function::apply)
				.collect(Collectors.toList());

		List<FoodPreferenceTableData> participantsWithoutPreference = missing.getBody().stream().map(function::apply)
				.collect(Collectors.toList());

		List<FoodPreferenceTableData> allData = new ArrayList<FoodPreferenceTableData>(participantsWithPreference);
		allData.addAll(participantsWithoutPreference);
		return allData;
	}

	// TODO change to FunctInterface
	class FPDtoFPTD implements Function<FoodPreferenceData, FoodPreferenceTableData> {
		@Override
		public FoodPreferenceTableData apply(FoodPreferenceData fpd) {
			FoodPreferenceTableData fptd = new FoodPreferenceTableData();
			fptd.setFirstName(fpd.getAssociate().getFirstName());
			fptd.setLastName(fpd.getAssociate().getLastName());
			fptd.setPreference(fpd.getPreferenceType());
			return fptd;
		}
	}

}
