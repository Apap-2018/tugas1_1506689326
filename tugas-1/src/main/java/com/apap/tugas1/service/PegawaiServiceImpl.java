package com.apap.tugas1.service;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.repository.PegawaiDB;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
/**
 * 
 * PegawaiServiceImpl
 *
 */
@Service
@Transactional
public class PegawaiServiceImpl implements PegawaiService {
	@Autowired
	private PegawaiDB pegawaiDB;
	
	@Override
	public PegawaiModel getPegawaiDetailByNip(String nip) {
		// TODO Auto-generated method stub
		return pegawaiDB.findByNip(nip);
	}

	@Override
	public void addPegawai(PegawaiModel pegawai) {
		// TODO Auto-generated method stub
		pegawaiDB.save(pegawai);
		
	}


}
