package com.apap.tugas1.service;

import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.repository.*;

@Service
@Transactional
public class JabatanServiceImpl implements JabatanService {
	@Autowired
	private JabatanDB jabatandb;
	
	@Override
	public JabatanModel getJabatanById(long id) {
		return jabatandb.findById(id);
	}

	@Override
	public void addJabatan(JabatanModel jabatan) {
		jabatandb.save(jabatan);
		
	}

	@Override
	public void deleteJabatan(JabatanModel jabatan) {
		jabatandb.delete(jabatan);
		
	}

	@Override
	public List<JabatanModel> getAllJabatan() {
		// TODO Auto-generated method stub
		return jabatandb.findAll();
	}

	
	
	

}