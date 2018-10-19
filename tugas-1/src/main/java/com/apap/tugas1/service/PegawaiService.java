package com.apap.tugas1.service;

import com.apap.tugas1.model.PegawaiModel;

/**
 * 
 * PegawaiService
 *
 */
public interface PegawaiService {
	void addPegawai(PegawaiModel pegawai);
	PegawaiModel getPegawaiDetailByNip(String nip);


}
