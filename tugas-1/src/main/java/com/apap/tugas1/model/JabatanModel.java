package com.apap.tugas1.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
/**
 * 
 * JabatanModel
 *
 */
@Entity
@Table(name = "jabatan")
public class JabatanModel implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;
	
	@NotNull
	@Size(max = 225)
	@Column(name="nama", nullable = false)
	private String nama;
	
	@NotNull
	@Size(max = 225)
	@Column(name="deskripsi", nullable = false)
	private String deskripsi;
	
	@NotNull
	@Column(name="gaji_pokok", nullable = false)
	private Double gaji_pokok;
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinTable(name = "jabatan_pegawai", joinColumns = { @JoinColumn(name = "id_pegawai")}, inverseJoinColumns = { @JoinColumn(name = "id_jabatan")})
	private List<PegawaiModel> pegawaiList;
	
	public long getId() {
		return id;
	}
	

	public void setId(long id) {
		this.id = id;
	}

	public String getNama() {
		return nama;
	}

	public void setNama(String nama) {
		this.nama = nama;
	}

	public String getDeskripsi() {
		return deskripsi;
	}

	public void setDeskripsi(String deskripsi) {
		this.deskripsi = deskripsi;
	}

	public Double getGaji_pokok() {
		return gaji_pokok;
	}

	public void setGaji_pokok(Double gaji_pokok) {
		this.gaji_pokok = gaji_pokok;
	}
	
	public List<PegawaiModel> getPegawaiList(){
		return pegawaiList;
	}
	
	public void setPegawaiList(List<PegawaiModel> pegawaiList) {
		this.pegawaiList = pegawaiList;
	}


}
