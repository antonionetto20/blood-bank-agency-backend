package br.com.wk.blood_bank_agency.service;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import br.com.wk.blood_bank_agency.model.AmountDonorsByBloodType;
import br.com.wk.blood_bank_agency.model.AverageAgeByBloodType;
import br.com.wk.blood_bank_agency.model.AverageBmiByAgeRange;
import br.com.wk.blood_bank_agency.model.PercentageObesityByGender;

public interface DonorService {
	
	
	public List<Map<String,Integer>> getAmountDonorsPerState();
	
	public LinkedList<AverageBmiByAgeRange> getAverageBmiByAgRange();
	
	public List<PercentageObesityByGender> getPercentageOfObeseIndividualsByGender();
	
	public LinkedList<AverageAgeByBloodType> getAverageAgeByBloodType();
	
	public LinkedList<AmountDonorsByBloodType> getAmountDonorsByBloodType();
	
	public void saveDonors(MultipartFile json) throws Exception;
	

}
