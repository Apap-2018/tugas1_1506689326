package com.apap.tugas1.service;


import com.apap.tugas1.model.*;
import java.util.Optional;
import java.util.List;

public interface JabatanPegawaiService {

	Optional<List<JabatanPegawaiModel>> getJabatanPegawaiByNip(String nip);
	List<JabatanPegawaiModel> getJabatanPegawaiByIdJabatan(long id);

}
