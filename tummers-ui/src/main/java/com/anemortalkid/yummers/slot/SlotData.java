package com.anemortalkid.yummers.slot;

import org.joda.time.LocalDate;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(using = SlotDataDeserializer.class)
public class SlotData {

	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	private LocalDate slotDate;

	private boolean isSchedulable;

	public LocalDate getSlotDate() {
		return slotDate;
	}

	@Override
	public String toString() {
		return "SlotData [id=" + id + ", slotDate=" + slotDate + ", isSchedulable=" + isSchedulable + "]";
	}

	public void setSlotDate(LocalDate slotDate) {
		this.slotDate = slotDate;
	}

	public boolean isSchedulable() {
		return isSchedulable;
	}

	public void setSchedulable(boolean isSchedulable) {
		this.isSchedulable = isSchedulable;
	}

}
