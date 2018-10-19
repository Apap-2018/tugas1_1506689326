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
import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.ProvinsiModel;

import com.apap.tugas1.service.InstansiService;
import com.apap.tugas1.service.JabatanService;
import com.apap.tugas1.service.PegawaiService;
import com.apap.tugas1.service.ProvinsiService;

@Controller
public class PegawaiController {
	@Autowired 
	private PegawaiService pegawaiService;
	
	@Autowired
	private JabatanService jabatanService;
	
	@Autowired
	private InstansiService instansiService;
	
	@Autowired
	private ProvinsiService provinsiService;
	
	@RequestMapping("/")
	private String home(Model model) {
		model.addAttribute("title", "");
		model.addAttribute("listJabatan", jabatanService.getAllJabatan());
		model.addAttribute("listInstansi", instansiService.getAllInstansi());
		return "home";
	}
	
	@RequestMapping("/pegawai")
	private String viewPegawaiByNip(@RequestParam(value = "nip") String nip, Model model) {
		PegawaiModel archive = pegawaiService.getPegawaiDetailByNip(nip);
		
		model.addAttribute("pegawai", archive);
		model.addAttribute("gaji", archive.getGaji());
		return "view-pegawai";
	}

	@RequestMapping(value = "/pegawai/tambah", method = RequestMethod.GET)
	private String add(Model model) {
		model.addAttribute("listProvinsi", provinsiService.getAllProvinsi());
		model.addAttribute("listInstansi", instansiService.getAllInstansi());
		model.addAttribute("listJabatan", jabatanService.getAllJabatan());
		model.addAttribute("pegawai", new PegawaiModel());
		return "addPegawai";
	}

	
	@RequestMapping(value = "/pegawai/tambah", method = RequestMethod.POST)
	private String addPegawaiSubmit(@ModelAttribute PegawaiModel pegawai, Model model) {
		System.out.println("test");
		pegawaiService.addPegawai(pegawai);
		return "dataBertambah";
	}
	
	@RequestMapping("/pegawai/termuda-tertua")
	private String viewPegawaiTermudaTertua(@RequestParam(value = "id") long id, Model model) {
		InstansiModel instansi = instansiService.getInstansiDetailById(id);
		
		List<PegawaiModel> pegawaiList = instansi.getListPegawaiInstansi();
		
		//menentukan pegawaiTermuda dan Tertua
		PegawaiModel pegawaiTermuda = new PegawaiModel();
		PegawaiModel pegawaiTertua = new PegawaiModel();
		
		int umurTermuda = 2018;
		int umurTertua = 0;
		for(PegawaiModel pegawai : pegawaiList) {
			int tempUsia = pegawai.getUsia();
			if(tempUsia < umurTermuda) {
				umurTermuda = tempUsia;
				pegawaiTermuda = pegawai;
			}
			
			if(tempUsia > umurTertua) {
				umurTertua = tempUsia;
				pegawaiTertua = pegawai;
			}
		}

		model.addAttribute("pegawaiTermuda", pegawaiTermuda);
		model.addAttribute("pegawaiTertua", pegawaiTertua);
		model.addAttribute("gajiTermuda", pegawaiTermuda.getGaji());
		model.addAttribute("gajiTertua", pegawaiTertua.getGaji());
		return "view-pegawai-tua-muda";
	}
	
	@RequestMapping(value = "/pegawai/ubah")
	public String changePegawai(@RequestParam("nip") String nip, Model model) {
		PegawaiModel pegawai = pegawaiService.getPegawaiDetailByNip(nip);
		//System.out.println(pegawai.getId());
		
		model.addAttribute("listProvinsi", provinsiService.getAllProvinsi());
		model.addAttribute("listJabatan", jabatanService.getAllJabatan());
		model.addAttribute("pegawai", pegawai);
		return "ubah-pegawai";	
	}	
	
	@RequestMapping(value = "/pegawai/ubah", method = RequestMethod.POST)
	private String ubahPegawaiSubmit(@ModelAttribute PegawaiModel pegawai, Model model) {
		String nip = "";
		
		nip += pegawai.getInstansi().getId();
		//System.out.println(pegawai.getInstansi().getId());
		//System.out.println(pegawai.getId());
		
		String[] tglLahir = pegawai.getTanggal_lahir().toString().split("-");
		String tglLahirString = tglLahir[2] + tglLahir[1] + tglLahir[0].substring(2, 4);
		nip += tglLahirString;
		//System.out.println(pegawai.getTanggalLahir());
		
		nip += pegawai.getTahun_masuk();
		//System.out.println(pegawai.getTahunMasuk());
		
		int counterSama = 1;
		for (PegawaiModel pegawaiInstansi:pegawai.getInstansi().getListPegawaiInstansi()) {
			if (pegawaiInstansi.getTahun_masuk().equals(pegawai.getTahun_masuk()) && pegawaiInstansi.getTanggal_lahir().equals(pegawai.getTanggal_lahir()) && pegawaiInstansi.getId() != pegawai.getId()) {
				counterSama += 1;
			}	
		}
		nip += "0" + counterSama;

		for (JabatanModel jabatan:pegawai.getListJabatan()) {
			System.out.println(jabatan.getNama());
		}
		pegawai.setNip(nip);
		//System.out.println(pegawai.getNip());
		//System.out.println(pegawai.getId());
		pegawaiService.addPegawai(pegawai);
		model.addAttribute("pegawai", pegawai);
		return "dataBerubah";
	}
	
}