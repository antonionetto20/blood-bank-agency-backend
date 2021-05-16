package br.com.wk.blood_bank_agency.model;

import lombok.Data;

@Data
public class AmountDonorsByBloodType {

	private String bloodType;
	private Integer amountDonors;
}
