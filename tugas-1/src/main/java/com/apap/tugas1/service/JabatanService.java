package com.apap.tugas1.service;

import java.util.List;
import java.util.Optional;

import com.apap.tugas1.model.*;

public interface JabatanService {

	JabatanModel getJabatanById(long id);
	void addJabatan(JabatanModel jabatan);
	void deleteJabatan(JabatanModel jabatan);
	List<JabatanModel> getAllJabatan();
	

}
