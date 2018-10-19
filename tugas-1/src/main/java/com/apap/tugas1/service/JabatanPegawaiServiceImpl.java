package com.apap.tugas1.service;

import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import com.apap.tugas1.model.JabatanPegawaiModel;
import com.apap.tugas1.repository.JabatanPegawaiDB;
import org.springframework.stereotype.Service;
import com.apap.tugas1.repository.*;

@Service
@Transactional
public class JabatanPegawaiServiceImpl implements JabatanPegawaiService {
	@Autowired
	private JabatanPegawaiDB jabatanPegawaiDB;

	@Override
	public Optional<List<JabatanPegawaiModel>> getJabatanPegawaiByNip(String nip) {
		// TODO Auto-generated method stub
		return jabatanPegawaiDB.findAllByPegawaiNip(nip);
	}

	@Override
	public List<JabatanPegawaiModel> getJabatanPegawaiByIdJabatan(long id) {
		// TODO Auto-generated method stub
		return jabatanPegawaiDB.findAllByJabatanId(id);
	}

}
