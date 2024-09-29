package com.patelbros.enums;

public enum EmailTemplates {
	ACTIVATION_MAIL("activation-mail"),
	ORDER_CONFIRMATION("order-confirmed"),
	RE_CONFIRM_MAIL("re-confirm")
	
	;
	
	public String getName() {
		return name;
	}

	private final String name;
	
	EmailTemplates(String name){
		this.name = "emailTemplates/"+name;
	}
}
