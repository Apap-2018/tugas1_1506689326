package com.apap.tugas1.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.service.JabatanService;
import com.apap.tugas1.service.PegawaiService;

@Controller
public class JabatanController {
	@Autowired
	private JabatanService jabatanService;
	
	@Autowired
	private PegawaiService pegawaiService;
	
	//Menambahkan Jabatan
	@RequestMapping(value = "/jabatan/tambah", method = RequestMethod.GET)
	private String add(Model model) {
		model.addAttribute("jabatan", new JabatanModel());
		return "add-jabatan";
	}
	
	@RequestMapping(value = "/jabatan/tambah", method = RequestMethod.POST)
	private String addJabatan(@ModelAttribute JabatanModel jabatan,Model model) {
		jabatanService.addJabatan(jabatan);
		model.addAttribute("jabatan", jabatan);
		return "jabatanBertambah";
	}
	
	//Melihat Detail Jabatan
	@RequestMapping("/jabatan/view")
	private String viewJabatanById(@RequestParam(value = "id") Long id, Model model) {
		JabatanModel archive = jabatanService.getJabatanById(id);
		List<PegawaiModel> pegawai = pegawaiService.getPegawaiByJabatan(archive);
		int jmlPegawai = pegawai.size();
		model.addAttribute("jmlPegawai", jmlPegawai);
		model.addAttribute("jabatan", archive);
		return "view-jabatan";
	}
	
	//Mengupdate Jabatan
	@RequestMapping(value = "/jabatan/ubah", method = RequestMethod.GET)
	private String updateJabatan(@RequestParam(value = "id") long id, Model model) {
		JabatanModel archive = jabatanService.getJabatanById(id);
		model.addAttribute("jabatan", archive);
		return "ubahJabatan";
	}
	
	@RequestMapping(value = "/jabatan/ubah", method = RequestMethod.POST)
	private String updateJabatan(@ModelAttribute JabatanModel jabatan, Model model) {
		JabatanModel archive = jabatanService.getJabatanById(jabatan.getId());
		archive.setDeskripsi(jabatan.getDeskripsi());
		archive.setGaji_pokok(jabatan.getGaji_pokok());
		archive.setNama(jabatan.getNama());
		jabatanService.addJabatan(archive);
		return "dataBerubah";
		
	}
	
	//Menghapus Jabatan
	@RequestMapping(value="/jabatan/hapus", method= RequestMethod.POST)
	private String deleteJabatan(@ModelAttribute JabatanModel jabatan, Model model) {
		JabatanModel archive = jabatanService.getJabatanById(jabatan.getId());
		
		if(archive.getPegawaiList()==null) {
			model.addAttribute("jabatan", archive);
			return "terhapus";
		}
		jabatanService.deleteJabatan(archive);
		
		model.addAttribute("jabatan", archive);
		return "terhapus";
	}
	
	
	//Menampilkan semua jabatan yang ada
	@RequestMapping(value="/jabatan/viewall")
	private String viewAll(Model model) {
		List<JabatanModel> listJabatan = jabatanService.getAllJabatan();
		model.addAttribute("jabatanList", listJabatan);
		model.addAttribute("title", "Lihat Seluruh Jabatan");
		return "view-seluruh-jabatan";
	}
}