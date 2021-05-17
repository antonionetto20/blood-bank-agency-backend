package br.com.wk.blood_bank_agency.dto;

import lombok.Data;

@Data
public class AverageAgeByBloodType {

	private String bloodType;
	private Integer averageAge;
}
