package com.apap.tugas1.service;

import java.util.List;
import java.util.Optional;

import com.apap.tugas1.model.ProvinsiModel;

public interface ProvinsiService {
	List<ProvinsiModel> getAllProvinsi();
	ProvinsiModel getProvinsiDetailById(Long idProvinsi);
	
}
