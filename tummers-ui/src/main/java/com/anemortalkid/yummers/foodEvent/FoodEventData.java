package com.anemortalkid.yummers.foodEvent;

import java.util.List;

import com.anemortalkid.yummers.slot.SlotData;

public class FoodEventData {

	private List<String> breakfastParticipants;
	private List<String> snackParticipants;
	private SlotData slot;
	private boolean isActive;
	private boolean calendarInviteSent = false;
	private boolean reminderSent = false;

	public List<String> getBreakfastParticipants() {
		return breakfastParticipants;
	}

	public void setBreakfastParticipants(List<String> breakfastParticipants) {
		this.breakfastParticipants = breakfastParticipants;
	}

	public List<String> getSnackParticipants() {
		return snackParticipants;
	}

	public void setSnackParticipants(List<String> snackParticipants) {
		this.snackParticipants = snackParticipants;
	}

	public SlotData getSlot() {
		return slot;
	}

	public void setSlot(SlotData slot) {
		this.slot = slot;
	}

	@Override
	public String toString() {
		return "FoodEventData [breakfastParticipants=" + breakfastParticipants + ", snackParticipants="
				+ snackParticipants + ", slot=" + slot + ", isActive=" + isActive + ", calendarInviteSent="
				+ calendarInviteSent + ", reminderSent=" + reminderSent + "]";
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public boolean isCalendarInviteSent() {
		return calendarInviteSent;
	}

	public void setCalendarInviteSent(boolean calendarInviteSent) {
		this.calendarInviteSent = calendarInviteSent;
	}

	public boolean isReminderSent() {
		return reminderSent;
	}

	public void setReminderSent(boolean reminderSent) {
		this.reminderSent = reminderSent;
	}

}
