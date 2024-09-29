package com.patelbros.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaypalResponse {
	private String url;
	private double total;
	private String currency;
	private String method;
	private String intent;
	private String description;
}
