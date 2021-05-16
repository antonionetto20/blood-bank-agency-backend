package br.com.wk.blood_bank_agency.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.wk.blood_bank_agency.model.BloodType;

@Repository
public interface BloodTypeRepository  extends JpaRepository<BloodType, Integer>{

}
