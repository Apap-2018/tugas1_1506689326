package com.apap.tugas1.service;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.repository.PegawaiDB;

import java.util.ArrayList;
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

	@Override
	public List<PegawaiModel> getPegawaiByInstansiJabatan(InstansiModel instansi, JabatanModel jabatan){
		// TODO Auto-generated method stub
		List<PegawaiModel> result = new ArrayList<>();
		for(PegawaiModel pegawai : pegawaiDB.findByInstansi(instansi)) {
			if (pegawai.getListJabatan().contains(jabatan)) {
				result.add(pegawai);
			}
		}
		return result;
	}
	
	@Override
	public List<PegawaiModel> getPegawaiByInstansi(InstansiModel instansi) {
		// TODO Auto-generated method stub
		return pegawaiDB.findByInstansi(instansi);
	}
	
	@Override
	public List<PegawaiModel> getPegawaiByProvinsiJabatan(Long provinsiId, JabatanModel jabatan){
		// TODO Auto-generated method stub
		List<PegawaiModel> result = new ArrayList<>();
		for(PegawaiModel pegawai : this.getPegawaiByProvinsi(provinsiId)) {
			if (pegawai.getListJabatan().contains(jabatan)) {
				result.add(pegawai);
			}
		}
		
		return result;
	}
	
	@Override
	public List<PegawaiModel> getPegawaiByProvinsi(Long provinsiId) {
		// TODO Auto-generated method stub
		List<PegawaiModel> dataPegawai = new ArrayList<>();
		for(PegawaiModel pegawai : pegawaiDB.findAll()) {
			if (pegawai.getInstansi().getProvinsi().getId() == provinsiId) {
				dataPegawai.add(pegawai);
			}
		}
		
		return dataPegawai;
	}
	
	@Override
	public List<PegawaiModel> getPegawaiByJabatan(JabatanModel jabatan){
		// TODO Auto-generated method stub
		return pegawaiDB.findByListJabatan(jabatan);
	}

	@Override
	public PegawaiModel getDataPegawaiById(long id) {
		// TODO Auto-generated method stub
		return pegawaiDB.findById(id);
	}

}
