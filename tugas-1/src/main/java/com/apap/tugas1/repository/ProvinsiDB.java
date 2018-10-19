package com.apap.tugas1.repository;

import java.util.List;
import java.util.Optional;

import com.apap.tugas1.model.ProvinsiModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProvinsiDB extends JpaRepository<ProvinsiModel, Long>{
	ProvinsiModel findById(long id);
	List<ProvinsiModel> findAll();

}
