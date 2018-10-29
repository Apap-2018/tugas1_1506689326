package com.apap.tugas1.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.ProvinsiModel;
import com.apap.tugas1.service.InstansiService;
import com.apap.tugas1.service.ProvinsiService;


@Controller
public class InstansiController {
	@Autowired
	private InstansiService instansiService;
	
	@Autowired
	private ProvinsiService provinsiService;
	
	@RequestMapping(value = "/instansi-get", method = RequestMethod.GET)
	public @ResponseBody InstansiModel getInstansiById(@RequestParam(value = "instansiId", required = true) long instansiId) {
	    InstansiModel instansi = instansiService.getInstansiDetailById(instansiId);
	    //System.out.println(instansi.getNama());
	    return instansi;
	}
	
	@RequestMapping(value = "/instansi-get-ubah", method = RequestMethod.GET)
	public @ResponseBody InstansiModel getInstansiById(@RequestParam(value = "instansiId", required = true) String instansiId) {
		String id = instansiId.substring(0,4);
		InstansiModel instansi = instansiService.getInstansiDetailById(Long.parseLong(id));
	    //System.out.println(instansi.getNama());
	    return instansi;
	}
	

	
	@RequestMapping(value = "/instansi/getInstansiByProvinsi", method = RequestMethod.GET)
	@ResponseBody
	public List<InstansiModel> getInstansi(@RequestParam (value = "idProvinsi", required = true) long idProvinsi) {
		ProvinsiModel provinsi = provinsiService.getProvinsiDetailById(idProvinsi);
	    return instansiService.getInstansiByProvinsi(provinsi);
	}
}