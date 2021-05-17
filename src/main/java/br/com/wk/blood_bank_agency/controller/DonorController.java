package br.com.wk.blood_bank_agency.controller;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import br.com.wk.blood_bank_agency.dto.AmountDonorsByBloodType;
import br.com.wk.blood_bank_agency.dto.AverageAgeByBloodType;
import br.com.wk.blood_bank_agency.dto.AverageBmiByAgeRange;
import br.com.wk.blood_bank_agency.dto.PercentageObesityByGender;
import br.com.wk.blood_bank_agency.service.DonorService;

@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/donors")
public class DonorController {
	
	@Autowired
	private DonorService donorService;
	
	@GetMapping("/getAmountDonorsPerState")
	public  List<Map<String,Integer>> getAmountDonorsPerState() { // Qtde doadores por estado
		return donorService.getAmountDonorsPerState(); 
	}
	
	@GetMapping("/getAverageBmiByAgRange")
	public List<AverageBmiByAgeRange> getAverageBmiByAgRange() { // IMC médio por faixa de idade
		return donorService.getAverageBmiByAgRange();
	}
	
	@GetMapping("/getPercentageOfObeseIndividualsByGender")
	public List<PercentageObesityByGender> getPercentageOfObeseIndividualsBySex() { // Percentual de obesos por sexo
		return donorService.getPercentageOfObeseIndividualsByGender();
	}
	
	@GetMapping("/getAverageAgeByBloodType")
	public List<AverageAgeByBloodType> getAverageAgeByBloodType() { // Média de idade para cada tipo sanguíneo
		return donorService.getAverageAgeByBloodType();
	}
	
	@GetMapping("/getAmountDonorsByBloodType")
	public List<AmountDonorsByBloodType> getAmountDonorsByBloodType() { // Quantidade de doadores para cada tipo sanguíneo
		return donorService.getAmountDonorsByBloodType();
	}
	
	@PostMapping(value = "/addDonors", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public void save(@RequestParam("file") MultipartFile file) throws Exception {
		donorService.saveDonors(file);
	}

}
