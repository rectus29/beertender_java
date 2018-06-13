package com.rectus29.beertender.enums;

public enum EventResponse {
	UNDECIDED ("Undecided"),
	ACCEPTED("Accepted"),
	DECLINED("Declined"),
	TENTATIVE("Tentative");

	private String value ="";

	EventResponse(String value) {
		this.value = value;
	}
}
