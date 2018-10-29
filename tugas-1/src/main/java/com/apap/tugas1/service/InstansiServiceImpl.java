package com.apap.tugas1.service;
import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.ProvinsiModel;
import com.apap.tugas1.repository.InstansiDB;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class InstansiServiceImpl implements InstansiService{
	@Autowired
	private InstansiDB instansiDB;

	@Override
	public List<InstansiModel> getAllInstansi() {
		// TODO Auto-generated method stub
		return instansiDB.findAll();
	}

	@Override
	public InstansiModel getInstansiDetailById(long id) {
		// TODO Auto-generated method stub
		return instansiDB.findById(id);
	}
	

	@Override
	public void addInstansi(InstansiModel instansi) {
		// TODO Auto-generated method stub
		instansiDB.save(instansi);
	}

	@Override
	public List<InstansiModel> getInstansiByProvinsi(ProvinsiModel ProvinsiModel) {
		// TODO Auto-generated method stub
		return instansiDB.findByProvinsi(ProvinsiModel);
	}

}
