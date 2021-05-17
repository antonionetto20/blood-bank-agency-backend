package br.com.wk.blood_bank_agency.dto;

import lombok.Data;

@Data
public class AmountDonorsByBloodType {

	private String bloodType;
	private Integer amountDonors;
}
