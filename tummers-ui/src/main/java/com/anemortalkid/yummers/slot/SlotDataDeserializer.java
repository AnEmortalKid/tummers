package com.anemortalkid.yummers.slot;

import java.io.IOException;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

public class SlotDataDeserializer extends JsonDeserializer<SlotData> {

	@Override
	public SlotData deserialize(JsonParser jp, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {
		ObjectCodec oc = jp.getCodec();
		JsonNode node = oc.readTree(jp);
		JsonNode slotDateNode = node.get("slotDate");
		JsonNode idNode = node.get("id");
		JsonNode isSchedulable = node.get("schedulable");

		SlotData sd = new SlotData();
		sd.setId(idNode.textValue());
		sd.setSchedulable(isSchedulable.asBoolean());

		JsonNode year = slotDateNode.get("year");
		JsonNode month = slotDateNode.get("monthOfYear");
		JsonNode day = slotDateNode.get("dayOfMonth");
		LocalDate slotDate = LocalDate.parse(day.asText() + "/" + month.asText() + "/" + year.asText(),
				DateTimeFormat.forPattern("dd/MM/yyyy"));
		sd.setSlotDate(slotDate);

		return sd;
	}

}
