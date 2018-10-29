package com.apap.tugas1.service;

import java.util.List;
import java.util.Optional;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.PegawaiModel;

/**
 * 
 * PegawaiService
 *
 */
public interface PegawaiService {
	void addPegawai(PegawaiModel pegawai);
	PegawaiModel getPegawaiDetailByNip(String nip);
	PegawaiModel getDataPegawaiById(long id);
	List<PegawaiModel> getPegawaiByProvinsi(Long provinsiId);
	List<PegawaiModel> getPegawaiByJabatan(JabatanModel jabatan);
	List<PegawaiModel> getPegawaiByInstansi(InstansiModel instansi);
	List<PegawaiModel> getPegawaiByInstansiJabatan(InstansiModel instansi, JabatanModel jabatan);
	List<PegawaiModel> getPegawaiByProvinsiJabatan(Long peg, JabatanModel jabatan);

}
