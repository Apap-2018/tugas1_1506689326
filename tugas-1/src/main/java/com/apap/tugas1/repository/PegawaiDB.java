package com.apap.tugas1.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.PegawaiModel;

/**
 * 
 *PegawaiDB
 *
 */
@Repository
public interface PegawaiDB extends JpaRepository<PegawaiModel, Long> {
	PegawaiModel findByNip(String nip);
	PegawaiModel findById(long id);
	List<PegawaiModel> findByInstansi(InstansiModel instansi);
	List<PegawaiModel> findByListJabatan(JabatanModel listJabatan);
	
}
