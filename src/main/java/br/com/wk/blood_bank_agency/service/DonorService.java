package br.com.wk.blood_bank_agency.service;

import java.util.List;
import java.util.Map;
import org.springframework.web.multipart.MultipartFile;
import br.com.wk.blood_bank_agency.dto.AmountDonorsByBloodType;
import br.com.wk.blood_bank_agency.dto.AverageAgeByBloodType;
import br.com.wk.blood_bank_agency.dto.AverageBmiByAgeRange;
import br.com.wk.blood_bank_agency.dto.PercentageObesityByGender;

public interface DonorService {
	
	public List<Map<String,Integer>> getAmountDonorsPerState();
	
	public List<AverageBmiByAgeRange> getAverageBmiByAgRange();
	
	public List<PercentageObesityByGender> getPercentageOfObeseIndividualsByGender();
	
	public List<AverageAgeByBloodType> getAverageAgeByBloodType();
	
	public List<AmountDonorsByBloodType> getAmountDonorsByBloodType();
	
	public void saveDonors(MultipartFile json) throws Exception;
	
}
