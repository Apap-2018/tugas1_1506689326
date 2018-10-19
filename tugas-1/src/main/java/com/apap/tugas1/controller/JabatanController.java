package com.apap.tugas1.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.service.JabatanService;

@Controller
public class JabatanController {
	@Autowired
	private JabatanService jabatanService;
	
	@RequestMapping(value = "/jabatan/tambah", method = RequestMethod.GET)
	private String add(Model model) {
		model.addAttribute("jabatan", new JabatanModel());
		return "add-jabatan";
	}

	
	@RequestMapping(value = "/jabatan/tambah", method = RequestMethod.POST)
	private String addJabatan(@ModelAttribute JabatanModel jabatan, Model model) {
		jabatanService.addJabatan(jabatan);
		return "dataBertambah";
	}
	
	@RequestMapping("/jabatan/view")
	private String viewJabatanById(@RequestParam(value = "id") long id, Model model) {
		JabatanModel archive = jabatanService.getJabatanById(id);
		model.addAttribute("jabatan", archive);
		model.addAttribute("title", "Lihat Jabatan");
		return "view-jabatan";
	}
	
	@RequestMapping(value = "/jabatan/ubah", method = RequestMethod.GET)
	private String update(@RequestParam(value ="id") long id, Model model) {
		JabatanModel archive = jabatanService.getJabatanById(id);
		model.addAttribute("jabatan", archive);
		model.addAttribute("title", "Ubah Jabatan");
		return "ubahJabatan";
	}
	
	@RequestMapping(value = "/jabatan/ubah", method = RequestMethod.POST)
	private String updateJabatan(@ModelAttribute JabatanModel jabatan, Model model) {
		JabatanModel archive = jabatanService.getJabatanById(jabatan.getId());
		archive.setDeskripsi(jabatan.getDeskripsi());
		archive.setGaji_pokok(jabatan.getGaji_pokok());
		archive.setNama(jabatan.getNama());
		jabatanService.addJabatan(archive);
		model.addAttribute("title", "Data Berubah");
		return "dataBerubah";
		
	}
	
	@RequestMapping(value="/jabatan/hapus", method= RequestMethod.POST)
	private String deleteJabatan(@ModelAttribute JabatanModel jabatan, Model model) {
		JabatanModel archive = jabatanService.getJabatanById(jabatan.getId());
		jabatanService.deleteJabatan(archive);
		model.addAttribute("title", "Menghapus Data");
		return "terhapus";
	}
	
	@RequestMapping(value="/jabatan/viewall")
	private String viewAll(Model model) {
		List<JabatanModel> listJabatan = jabatanService.getAllJabatan();
		model.addAttribute("jabatanList", listJabatan);
		model.addAttribute("title", "Lihat Seluruh Jabatan");
		return "view-seluruh-jabatan";
	}
}