package br.com.wk.blood_bank_agency.service.impl;

import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import br.com.wk.blood_bank_agency.dto.AmountDonorsByBloodType;
import br.com.wk.blood_bank_agency.dto.AverageAgeByBloodType;
import br.com.wk.blood_bank_agency.dto.AverageBmiByAgeRange;
import br.com.wk.blood_bank_agency.dto.PercentageObesityByGender;
import br.com.wk.blood_bank_agency.model.Donor;
import br.com.wk.blood_bank_agency.repository.DonorRepository;
import br.com.wk.blood_bank_agency.service.DonorService;

@Service
public class DonorServiceImpl implements DonorService{
	
	@Autowired
	DonorRepository donorRepository;

	@Override
	public List<Map<String,Integer>> getAmountDonorsPerState() {
		if(donorRepository.donorsByState().isEmpty())
			return null;
		return donorRepository.donorsByState();
	}

	@Override
	public List<AverageBmiByAgeRange> getAverageBmiByAgRange() {
		
		if(donorRepository.averageBmiByAgRange().isEmpty())
			return null;
		
		List<AverageBmiByAgeRange> averageBmiByAgeRanges = new LinkedList<>();
		Double[] totalBMIByRange = new Double[] {0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0};
		Integer[] totalDonorAmountPerTrack = new Integer[] {0,0,0,0,0,0,0,0,0,0,0,0};
		
		for(String line: donorRepository.averageBmiByAgRange()) {
			String[] lineList = line.split(",");
			
			Integer idade = Integer.parseInt(lineList[0]);
			Double altura = Double.parseDouble(lineList[1]);
			Double peso = Double.parseDouble(lineList[2]);
			Double imc = getImc(altura,peso);
			
			
			if (between(idade, 0, 10)) {
				totalBMIByRange[0] += imc;
				totalDonorAmountPerTrack[0] += 1;
			} else if (between(idade, 11, 20)) {
				totalBMIByRange[1] += imc; 
				totalDonorAmountPerTrack[1] += 1;
			} else if (between(idade, 21, 30)) {
				totalBMIByRange[2] += imc;
				totalDonorAmountPerTrack[2] += 1;
			} else if (between(idade, 31, 40)) {
				totalBMIByRange[3] += imc;
				totalDonorAmountPerTrack[3] += 1;
			} else if (between(idade, 41, 50)) {
				totalBMIByRange[4] += imc;
				totalDonorAmountPerTrack[4] += 1;
			} else if (between(idade, 51, 60)) {
				totalBMIByRange[5] += imc;
				totalDonorAmountPerTrack[5] += 1;
			} else if (between(idade, 61, 70)) {
				totalBMIByRange[6] += imc;
				totalDonorAmountPerTrack[6] += 1;
			} else if (between(idade, 71, 80)) {
				totalBMIByRange[7] += imc;
				totalDonorAmountPerTrack[7] += 1;
			} else if (between(idade, 81, 90)) {
				totalBMIByRange[8] += imc;
				totalDonorAmountPerTrack[8] += 1;
			} else if (between(idade, 91, 100)) {
				totalBMIByRange[9] += imc;
				totalDonorAmountPerTrack[9] += 1;
			} else if (between(idade, 101, 110)) {
				totalBMIByRange[10] += imc;
				totalDonorAmountPerTrack[10] += 1;
			} else if (between(idade, 111, 120)) {
				totalBMIByRange[11] += imc;
				totalDonorAmountPerTrack[11] += 1;
			}

		}
		
		DecimalFormat df = new DecimalFormat("#0.0");
		
		for(int i=0; i < 12;i++) {
			if(totalDonorAmountPerTrack[i]> 0) {
				String media = df.format(totalBMIByRange[i]/totalDonorAmountPerTrack[i]).replace(',', '.');
				AverageBmiByAgeRange averageBmiByAgeRange = new AverageBmiByAgeRange();
				averageBmiByAgeRange.setAgeRange(i + "1-" + (i+1) + "0");
				averageBmiByAgeRange.setAverage(media);
				averageBmiByAgeRanges.add(averageBmiByAgeRange);
			}
			
		}
		
		return averageBmiByAgeRanges;
	}

	@Override
	public List<PercentageObesityByGender> getPercentageOfObeseIndividualsByGender() {
		
		if(donorRepository.percentageOfObeseIndividualsBySex().isEmpty())
			return null;
		
		PercentageObesityByGender percentageObesityByGender = new PercentageObesityByGender();
		List<PercentageObesityByGender> percentageObesityByGenderList = new ArrayList<PercentageObesityByGender>();
		
		Integer qtdeObesosHomens = 0;
		Integer qtdeObesosMulheres = 0;
		Double totalIMCObesosHomens = 0.0;
		Double totalIMCObesosMulheres = 0.0;
		
		for(String line: donorRepository.percentageOfObeseIndividualsBySex()) {
			String[] lineList = line.split(",");
			
			String sexo = lineList[0];
			Double altura = Double.parseDouble(lineList[1]);
			Double peso = Double.parseDouble(lineList[2]);

			Double imc = getImc(altura, peso);
			
			if(imc > 30) {
				if(sexo.equals("Feminino")) {
					qtdeObesosMulheres+=1;
					totalIMCObesosMulheres+=imc;
				}else {
					qtdeObesosHomens+=1;
					totalIMCObesosHomens+=imc;
				}
			}
		}
		
		DecimalFormat df = new DecimalFormat("#0.0");
		String percentualFeminino = df.format(totalIMCObesosMulheres/qtdeObesosMulheres);
		String percentualMasculino = df.format(totalIMCObesosMulheres/qtdeObesosHomens);
		
		percentageObesityByGender.setFemalePercentage(percentualFeminino);
		percentageObesityByGender.setMalePercentage(percentualMasculino);
		percentageObesityByGenderList.add(percentageObesityByGender);
		
		return percentageObesityByGenderList ;
	}

	@Override
	public List<AverageAgeByBloodType> getAverageAgeByBloodType() {
		
		if(donorRepository.averageAgeByBloodType().isEmpty())
			return null;
		
		List<AverageAgeByBloodType> result = new LinkedList<AverageAgeByBloodType>();

		Integer[] amountDonors = new Integer[] {0,0,0,0,0,0,0,0};
		Integer[] ages = new Integer[] {0,0,0,0,0,0,0,0};
		List<String> bloodTypes = new LinkedList<String>();
		bloodTypes.add("A+");
		bloodTypes.add("A-");
		bloodTypes.add("B+");
		bloodTypes.add("B-");
		bloodTypes.add("AB+");
		bloodTypes.add("AB-");
		bloodTypes.add("O+");
		bloodTypes.add("O-");
		
		for (String line : donorRepository.averageAgeByBloodType()) {
			
			String[] lineList = line.split(",");
			
			int index = bloodTypes.indexOf(lineList[1]);
			amountDonors[index] += 1;
			ages[index] += Integer.parseInt(lineList[0]);
		}
		
		for (String bloodType : bloodTypes) {
			AverageAgeByBloodType averageAgeByBloodType = new AverageAgeByBloodType();
			averageAgeByBloodType.setAverageAge(ages[bloodTypes.indexOf(bloodType)] / amountDonors[bloodTypes.indexOf(bloodType)]);
			averageAgeByBloodType.setBloodType(bloodType);
			result.add(averageAgeByBloodType);
		}

		return result;
	}

	@Override
	public List<AmountDonorsByBloodType> getAmountDonorsByBloodType() {
		
		if(donorRepository.amountDonorsByBloodType().isEmpty())
			return null;

		List<AmountDonorsByBloodType> result = new LinkedList<AmountDonorsByBloodType>();

		Integer[] amountDonors = new Integer[] {0,0,0,0,0,0,0,0};
		
		List<String> bloodTypes = new LinkedList<String>();
		bloodTypes.add("A+");
		bloodTypes.add("A-");
		bloodTypes.add("B+");
		bloodTypes.add("B-");
		bloodTypes.add("AB+");
		bloodTypes.add("AB-");
		bloodTypes.add("O+");
		bloodTypes.add("O-");
		
		LinkedHashMap<String, String> bloodTypesDonate = new LinkedHashMap<String, String>();
		bloodTypesDonate.put("A+", "AB+,A+");
		bloodTypesDonate.put("A-", "A+,A-,AB+,AB-");
		bloodTypesDonate.put("B+", "B+,AB+");
		bloodTypesDonate.put("B-", "B+,B-,AB+,AB-");
		bloodTypesDonate.put("AB+", "AB+");
		bloodTypesDonate.put("AB-", "AB+,AB-");
		bloodTypesDonate.put("O+", "A+,B+,O+,AB+");
		bloodTypesDonate.put("O-", "A+,B+,O+,AB+,A-,B-,O-,AB-");
		
		// Somente pessoas com idade de 16 a 69 anos e com peso acima de 50 Kg podem doar sangue.
		for (String line : donorRepository.amountDonorsByBloodType()) {
			
			String[] lineList = line.split(",");
			
			int idade = Integer.parseInt(lineList[0]);
			String bloodType = lineList[1];
			double peso = Double.parseDouble(lineList[0]);
			
			for(String receive: bloodTypesDonate.get(bloodType).split(",")) {
				if(between(idade,16,69) && peso > 50) {
					int index = bloodTypes.indexOf(receive);
					amountDonors[index] += 1;
				}
			}
			
		}

		for (String bloodType : bloodTypes) {
			
			AmountDonorsByBloodType amountDonorsByloodType = new AmountDonorsByBloodType();
			amountDonorsByloodType.setBloodType(bloodType);
			amountDonorsByloodType.setAmountDonors(amountDonors[bloodTypes.indexOf(bloodType)]);
			result.add(amountDonorsByloodType);
		}
		
		return result;
	}

	@Override
	public void saveDonors(MultipartFile file) throws Exception {
		
		String content = new String(file.getBytes(), StandardCharsets.UTF_8);
        String data = new String(content);
		JSONArray jsonarr = new JSONArray(data);

		this.donorRepository.saveAll(getDonorsJson(jsonarr));
	}
	
	public List<Donor> getDonorsJson(JSONArray jsonarr) throws JSONException, ParseException{
		
		List<Donor> donors = new ArrayList<Donor>();
		
		for (Object o : jsonarr){
			
	  	    JSONObject person = (JSONObject) o;
	  	    
	  	    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy"); 
	  	    Donor donor = new Donor();
	  	    
	  	    donor.setNome(person.getString("nome"));
	  	    donor.setCpf(person.getString("cpf"));
	  	    donor.setRg(person.getString("rg"));
	  	    donor.setData_nasc(format.parse(((String) person.get("data_nasc")).replaceAll("\\\\", "")));
	  	    donor.setSexo(person.getString("sexo"));
	  	    donor.setMae(person.getString("mae"));
	  	    donor.setPai(person.getString("pai"));
	  	    donor.setEmail(person.getString("email"));
	  	    donor.setCep(person.getString("cep"));
	  	    donor.setEndereco(person.getString("endereco"));
	  	    donor.setNumero(person.getInt("numero"));
	  		donor.setBairro(person.getString("bairro"));
	  		donor.setCidade(person.getString("cidade"));
	  		donor.setEstado(person.getString("estado"));
	  		donor.setTelefone_fixo(person.getString("telefone_fixo"));
	  		donor.setCelular(person.getString("celular"));
	  		donor.setAltura(person.getDouble("altura"));
	  		donor.setPeso(person.getDouble("peso"));
	  		donor.setTipo_sanguineo(person.getString("tipo_sanguineo"));
	  		
	  		donors.add(donor);
	  	}
		return donors;
	}
	
	private boolean between(Integer x, Integer y, Integer z) {
		if(x >= y && x <= z)
			return true;
		return false;
	}
	
	private double getImc(double altura, double peso) {
		return peso/(altura*altura);
	}
	
	

}
