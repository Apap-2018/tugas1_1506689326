package com.apap.tugas1.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
	private String addPegawai(Model model) {
		model.addAttribute("provinsiList", provinsiService.getAllProvinsi());
		model.addAttribute("instansiList", instansiService.getAllInstansi());
		model.addAttribute("jabatanList", jabatanService.getAllJabatan());
		
		PegawaiModel pegawai = new PegawaiModel();
		if (pegawai.getListJabatan()==null) {
			pegawai.setListJabatan(new ArrayList<JabatanModel>());
		}
		pegawai.getListJabatan().add(new JabatanModel());
		
		model.addAttribute("pegawai", pegawai);
		
		return "addPegawai";
	}
	

	@RequestMapping(value="/pegawai/tambah", params={"removeRow"}, method = RequestMethod.POST)
	private String removeRowJabatanPegawai(@ModelAttribute PegawaiModel pegawai, final BindingResult bindingResult, final HttpServletRequest req, Model model) {
		model.addAttribute("provinsiList", provinsiService.getAllProvinsi());
		model.addAttribute("instansiList", instansiService.getAllInstansi());
		model.addAttribute("jabatanList", jabatanService.getAllJabatan());
		
		final Integer rowId = Integer.valueOf(req.getParameter("removeRow"));
		pegawai.getListJabatan().remove(rowId.intValue());
		model.addAttribute("pegawai", pegawai);
		
		return "addPegawai";
	}
	
	@RequestMapping(value="/pegawai/tambah", params={"addRow"}, method = RequestMethod.POST)
	private String addRowJabatanPegawai(@ModelAttribute PegawaiModel pegawai, Model model) {
		model.addAttribute("provinsiList", provinsiService.getAllProvinsi());
		model.addAttribute("instansiList", instansiService.getAllInstansi());
		model.addAttribute("jabatanList", jabatanService.getAllJabatan());
		
		if (pegawai.getListJabatan()==null) {
			pegawai.setListJabatan(new ArrayList<JabatanModel>());
		}
		pegawai.getListJabatan().add(new JabatanModel());
		
		model.addAttribute("pegawai", pegawai);
		
		return "addPegawai";
	}

	
	@RequestMapping(value = "/pegawai/tambah", method = RequestMethod.POST)
	private String tambahPegawai(@ModelAttribute PegawaiModel pegawai, Model model) {
		//membuat format nip pegawai baru
		
		ProvinsiModel provinsi = pegawai.getInstansi().getProvinsi();
		String nip = "";
		
		nip = nip + provinsi.getId();
		int instansiLine = provinsi.getInstansiProvinsi().indexOf(pegawai.getInstansi()) + 1;
		
		if(instansiLine < 10) { 
			nip = nip + "0"+ instansiLine;
		}else { 
			nip = nip + instansiLine; 
			}
		
		String formatTanggal = pegawai.getTanggal_lahir().toString();
		String formatDateBaru = formatTanggal.substring(8) + formatTanggal.substring(5, 7) + formatTanggal.substring(2, 4);
		nip = nip + formatDateBaru;
		
		//tahunMasuk
		nip+=pegawai.getTahun_masuk();
		
		//urutanMasuk
		InstansiModel instansi = pegawai.getInstansi();
		int nomorAwalNip=1;
		for(PegawaiModel comparePegawai: instansi.getListPegawaiInstansi()) {
			if(nip.equals(comparePegawai.getNip().substring(0, 14))) {
				nomorAwalNip = nomorAwalNip + 1;
			}
		}
		
		if(nomorAwalNip < 10) {
			nip = nip + "0"+nomorAwalNip;
		}else {
			nip = nip + nomorAwalNip;
		}
		
		
		pegawai.setNip(nip);
		pegawaiService.addPegawai(pegawai);
		model.addAttribute("pegawai", pegawai);
		return "dataBertambah";
	}
	
	@RequestMapping("/pegawai/termuda-tertua")
	private String viewPegawaiTermudaTertua(@RequestParam(value = "id") long id, Model model) {
		InstansiModel instansi = instansiService.getInstansiDetailById(id);
		List<PegawaiModel> pegawaiList = instansi.getListPegawaiInstansi();
		
		PegawaiModel pegawaiTua = new PegawaiModel();
		PegawaiModel pegawaiMuda= new PegawaiModel();
		
		int muda = 2018;
		int tua= 0;
		for(PegawaiModel pegawai : pegawaiList) {
			int tempUsia = pegawai.getUsia();
			if(tempUsia < muda) {
				muda = tempUsia;
				pegawaiMuda = pegawai;
			}
			
			if(tempUsia > tua) {
				tua = tempUsia;
				pegawaiTua = pegawai;
			}
		}

		model.addAttribute("pegawaiTermuda", pegawaiMuda);
		model.addAttribute("pegawaiTertua", pegawaiTua);
		model.addAttribute("gajiTermuda", pegawaiMuda.getGaji());
		model.addAttribute("gajiTertua", pegawaiTua.getGaji());
		return "view-pegawai-tua-muda";
	}
	
	@RequestMapping(value = "/pegawai/ubah")
	public String changePegawai(@RequestParam("nip") String nip, Model model) {
		PegawaiModel pegawai = pegawaiService.getPegawaiDetailByNip(nip);
		model.addAttribute("listProvinsi", provinsiService.getAllProvinsi());
		model.addAttribute("listJabatan", jabatanService.getAllJabatan());
		model.addAttribute("pegawai", pegawai);
		return "ubahPegawai";	
	}	
	
	@RequestMapping(value = "/pegawai/ubah", method = RequestMethod.POST)
	private String ubahPegawaiSubmit(@ModelAttribute PegawaiModel pegawai, Model model) {
		String nip = "";
		nip = nip + pegawai.getInstansi().getId();
		
		String[] tgl_lahir = pegawai.getTanggal_lahir().toString().split("-");
		String tglLahirString = tgl_lahir[2] + tgl_lahir[1] + tgl_lahir[0].substring(2, 4);
		nip += tglLahirString;
		
		nip += pegawai.getTahun_masuk();
		
		int temp = 1;
		for (PegawaiModel pegawaiInstansi:pegawai.getInstansi().getListPegawaiInstansi()) {
			if (pegawaiInstansi.getTahun_masuk().equals(pegawai.getTahun_masuk()) && pegawaiInstansi.getTanggal_lahir().equals(pegawai.getTanggal_lahir()) && pegawaiInstansi.getId() != pegawai.getId()) {
				temp += 1;
			}	
		}
		nip = nip + "0" + temp;

		for (JabatanModel jabatan:pegawai.getListJabatan()) {
			System.out.println(jabatan.getNama());
		}
		pegawai.setNip(nip);
		pegawaiService.addPegawai(pegawai);
		model.addAttribute("pegawai", pegawai);
		return "dataBerubah";
	}
	
}