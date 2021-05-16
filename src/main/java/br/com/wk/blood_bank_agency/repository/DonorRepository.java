package br.com.wk.blood_bank_agency.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import br.com.wk.blood_bank_agency.model.Donor;

@Repository
public interface DonorRepository extends JpaRepository<Donor, Integer>{
	
	@Query(value =  "SELECT estado, count(id) as total FROM donor group by estado", nativeQuery = true)
	public List<Map<String,Integer>> donorsByState();
	
	@Query(value =  "SELECT TIMESTAMPDIFF(YEAR, data_nasc, CURDATE()) idade, altura, peso FROM donor order by idade", nativeQuery = true)
	public List<String> averageBmiByAgRange();
	
	@Query(value =  "SELECT sexo, altura, peso FROM donor order by sexo", nativeQuery = true)
	public List<String> percentageOfObeseIndividualsBySex();
	
	@Query(value =  "SELECT TIMESTAMPDIFF(YEAR, data_nasc, CURDATE()) idade, tipo_sanguineo FROM donor order by tipo_sanguineo", nativeQuery = true)
	public List<String> averageAgeByBloodType();
	
	@Query(value =  "SELECT TIMESTAMPDIFF(YEAR, data_nasc, CURDATE()) idade, tipo_sanguineo, peso FROM donor order by tipo_sanguineo", nativeQuery = true)
	public List<String> amountDonorsByBloodType();

}
