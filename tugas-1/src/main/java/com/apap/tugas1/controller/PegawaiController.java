package com.apap.tugas1.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
		model.addAttribute("listJabatan", jabatanService.getAllJabatan());
		model.addAttribute("listInstansi", instansiService.getAllInstansi());
		return "home";
	}
	
	//Melihat Pegawai Berdasarkan NIP
	@RequestMapping("/pegawai")
	private String viewPegawaiByNip(@RequestParam(value = "nip") String nip, Model model) {
		PegawaiModel archive = pegawaiService.getPegawaiDetailByNip(nip);
		
		model.addAttribute("pegawai", archive);
		model.addAttribute("gaji", archive.getGaji());
		return "view-pegawai";
	}
	
	
	//Melakukan penambahan Pegawai baru
	@RequestMapping(value = "/pegawai/tambah", method = RequestMethod.GET)
	private String add(Model model) {
		List<ProvinsiModel> provinsiList = provinsiService.getAllProvinsi();
		List<JabatanModel> jabatanList = jabatanService.getAllJabatan();
		List<InstansiModel> listInstansi = instansiService.getInstansiByProvinsi(provinsiList.get(0));
		
		PegawaiModel pegawai = new PegawaiModel();
		pegawai.setListJabatan(new ArrayList<JabatanModel>());
		pegawai.getListJabatan().add(new JabatanModel());
		
		model.addAttribute("pegawai", pegawai);
		model.addAttribute("listInstansi", listInstansi);
		model.addAttribute("listJabatan", jabatanList);
		model.addAttribute("listProvinsi", provinsiList);
		//model.addAttribute("tambahpegawai", true);
		return "addPegawai";
	}
	
	@RequestMapping(value="/pegawai/tambah", params={"tambah"}, method = RequestMethod.POST)
	public String TambahRow(@ModelAttribute PegawaiModel pegawai, BindingResult bindingResult, Model model) {
		List<ProvinsiModel> provinsiList = provinsiService.getAllProvinsi();
		List<JabatanModel> jabatanList = jabatanService.getAllJabatan();
		model.addAttribute("listJabatan", jabatanList);
		model.addAttribute("listProvinsi", provinsiList);
		
		List<InstansiModel> instansi = instansiService.getInstansiByProvinsi(pegawai.getInstansi().getProvinsi());
		model.addAttribute("listInstansi", instansi);
		pegawai.getListJabatan().add(new JabatanModel());
	    model.addAttribute("pegawai", pegawai);
	    //model.addAttribute("tambahpegawai", true);
	    return "addPegawai";
	}
	
	@RequestMapping(value="/pegawai/tambah", params={"hapus"}, method = RequestMethod.POST)
	public String HapusRow(@ModelAttribute PegawaiModel pegawai, BindingResult bindingResult, HttpServletRequest req,Model model) {
		
		List<ProvinsiModel> listProvinsi = provinsiService.getAllProvinsi();
		List<JabatanModel> listJabatan = jabatanService.getAllJabatan();
		
		model.addAttribute("listJabatan", listJabatan);
		model.addAttribute("listProvinsi", listProvinsi);
		

		List<InstansiModel> listInstansi = instansiService.getInstansiByProvinsi(pegawai.getInstansi().getProvinsi());
		model.addAttribute("listInstansi", listInstansi);
		
		Integer rowId = Integer.valueOf(req.getParameter("hapus"));
		pegawai.getListJabatan().remove(rowId.intValue());
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
		nip+=pegawai.getTahun_masuk();//menambahkan tahun masuk untuk membuat nip
		
		//menambahkan urutan masuk untuk membuat nip
		InstansiModel instansi = pegawai.getInstansi();
		int nomorDepan=1;
		for(PegawaiModel comparePegawai: instansi.getListPegawaiInstansi()) {
			if(nip.equals(comparePegawai.getNip().substring(0, 14))) {
				nomorDepan = nomorDepan + 1;
			}
		}
		if(nomorDepan < 10) {
			nip = nip + "0"+nomorDepan;
		}else {
			nip = nip + nomorDepan;
		}
		
		pegawai.setNip(nip);
		pegawaiService.addPegawai(pegawai);
		model.addAttribute("pegawai", pegawai);
		return "dataBertambah";
	}
	
	//Mencari pegawai termuda dan Tertua dari Suatu Instansi
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

	
	
	
	
	
	// Megupdate data pegawai masih belum benar bukannya update malah tambah pegawai
	@RequestMapping(value = "/pegawai/ubah", method = RequestMethod.GET)
	private String ubah(@RequestParam(value = "nip", required = true) String nip , Model model) {
		List<ProvinsiModel> listProvinsi = provinsiService.getAllProvinsi();
		List<JabatanModel> listJabatan = jabatanService.getAllJabatan();
		List<InstansiModel> listInstansi = instansiService.getInstansiByProvinsi(listProvinsi.get(0));
		
		PegawaiModel pegawai = pegawaiService.getPegawaiDetailByNip(nip);
		pegawai.setListJabatan(new ArrayList<JabatanModel>());
		pegawai.getListJabatan().add(new JabatanModel());
		
		model.addAttribute("pegawai", pegawai);
		model.addAttribute("listInstansi", listInstansi);
		model.addAttribute("listJabatan", listJabatan);
		model.addAttribute("listProvinsi", listProvinsi);
		return "ubahPegawai";
	}
	
	@RequestMapping(value="/pegawai/ubah", params={"tambahJabatan"}, method = RequestMethod.POST)
	public String tambahRowUbah(@ModelAttribute PegawaiModel pegawai, BindingResult bindingResult, Model model) {
		List<ProvinsiModel> listProv = provinsiService.getAllProvinsi();
		List<JabatanModel> listJabatan = jabatanService.getAllJabatan();
		model.addAttribute("listJabatan", listJabatan);
		model.addAttribute("listProvinsi", listProv);
		
		List<InstansiModel> listInstansi = instansiService.getInstansiByProvinsi(pegawai.getInstansi().getProvinsi());
		model.addAttribute("listInstansi", listInstansi);
		pegawai.getListJabatan().add(new JabatanModel());
	    model.addAttribute("pegawai", pegawai);
	    return "ubahPegawai";
	}
	
	@RequestMapping(value="/pegawai/ubah", params={"hapus"}, method = RequestMethod.POST)
	public String HapusRowUpdate(@ModelAttribute PegawaiModel pegawai, BindingResult bindingResult, HttpServletRequest req,Model model) {
		
		List<ProvinsiModel> listProvinsi = provinsiService.getAllProvinsi();
		List<JabatanModel> listJabatan = jabatanService.getAllJabatan();
		model.addAttribute("listJabatan", listJabatan);
		model.addAttribute("listProvinsi", listProvinsi);
		

		List<InstansiModel> listInstansi = instansiService.getInstansiByProvinsi(pegawai.getInstansi().getProvinsi());
		model.addAttribute("listInstansi", listInstansi);
		
		Integer id = Integer.valueOf(req.getParameter("hapus"));
		pegawai.getListJabatan().remove(id.intValue());
	    model.addAttribute("pegawai", pegawai);
	    model.addAttribute("tambahpegawai", true);
	    return "ubahPegawai";
	}
	
	@RequestMapping(value = "/pegawai/ubah", method = RequestMethod.POST)
	private String ubahPegawaiSubmit(@ModelAttribute PegawaiModel pegawai,BindingResult bindingResult, Model model) {
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
		
		pegawai.setNip(nip);
		pegawai.setNama(pegawai.getNama());
		pegawai.setTempat_lahir(pegawai.getTempat_lahir());
		pegawai.setTanggal_lahir(pegawai.getTanggal_lahir());
		pegawai.setTahun_masuk(pegawai.getTahun_masuk());
		pegawai.setListJabatan(pegawai.getListJabatan());
		pegawai.setInstansi(pegawai.getInstansi());
		pegawaiService.addPegawai(pegawai);
		model.addAttribute("pegawai", pegawai);
		return "dataPegawaiBerubah";
	}
	
	
	
	
	
	//Mencari Data Pegawai Berdasarkan instansi, provinsi, dan atau Jabatan tertentu
	@RequestMapping(value = "/pegawai/cari", method = RequestMethod.GET)
	public String cariPegawai (@RequestParam(value="idProvinsi") Optional<Long> idProvinsi, @RequestParam(value="idInstansi") Optional<Long> idInstansi, @RequestParam(value="idJabatan") Optional<Long> idJabatan, Model model)
	{
		ProvinsiModel provinsi = null;
		InstansiModel instansi = null;
		JabatanModel jabatan = null;

		List<ProvinsiModel> listProvinsi = provinsiService.getAllProvinsi();
		model.addAttribute("listProvinsi", listProvinsi);
		
		List<InstansiModel> listInstansi = instansiService.getAllInstansi();
		model.addAttribute("listInstansi", listInstansi);
		
		List<JabatanModel> listJabatan = jabatanService.getAllJabatan();
		model.addAttribute("listJabatan", new HashSet(listJabatan));
		
		//mencari hasil berdasarkan provinsi, instansi, atau Jabatan
		List<PegawaiModel> result = null;
		if (idProvinsi.isPresent()) {
			provinsi = provinsiService.getProvinsiDetailById(idProvinsi.get());
			if (idInstansi.isPresent()) {
				instansi = instansiService.getInstansiDetailById(idInstansi.get());	
				if (idJabatan.isPresent()) {
					jabatan = jabatanService.getJabatanById(idJabatan.get());	
					result = pegawaiService.getPegawaiByInstansiJabatan(instansi, jabatan);
				}else { 
					result = pegawaiService.getPegawaiByInstansi(instansi);
				}
			}else if (idJabatan.isPresent()) {
				jabatan = jabatanService.getJabatanById(idJabatan.get());	
				result = pegawaiService.getPegawaiByProvinsiJabatan(idProvinsi.get(), jabatan);
			}else { 
				result = pegawaiService.getPegawaiByProvinsi(idProvinsi.get());
			}
		} else if (idJabatan.isPresent()) {
				jabatan = jabatanService.getJabatanById(idJabatan.get());	
				result = pegawaiService.getPegawaiByJabatan(jabatan);
		}
		model.addAttribute("provinsi", provinsi);
		model.addAttribute("instansi", instansi);
		model.addAttribute("jabatan", jabatan);
		model.addAttribute("result", result);
		return "cariPegawai";
	}
	
}