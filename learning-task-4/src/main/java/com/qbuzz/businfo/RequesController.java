package com.qbuzz.businfo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.qbuzz.soapserver.BusManager;

@Controller
public class RequesController {
	private class Result {
		public String busID;
		public String lijn;
		public String eindpunt;
		public String halte;
		public int aankomsttijd;
		public ArrayList<String> bussenBijHalte;
	}
	
	@GetMapping("/QBUZZ_Info")
    public String startPagina(Model model) {
		//BusManager.checkTestSet();
		Set<String> busLijst = BusManager.getLijstMetBussen();
		ArrayList<Result> results = new ArrayList<Result>();
		for (String busId : busLijst) {
			Result r = new Result();
			r.busID=busId;
			r.eindpunt = BusManager.getEindpunt(busId);
			results.add(r);
		}
		model.addAttribute("results", results);
		return "startpagina";
	}
	
	@GetMapping("/QBUZZ_Info/halteInfo")
	public String halteInfo(Model model) {
		//BusManager.checkTestSet();
		HashMap<String, ArrayList<String>> bussenBijHalte = BusManager.bussenBijHaltes();
		ArrayList<Result> results = new ArrayList<Result>();
		Set<String> haltes = bussenBijHalte.keySet();
		for (String halte: haltes) {
			Result r = new Result();
			r.halte = halte;
			r.bussenBijHalte = bussenBijHalte.get(halte);
			results.add(r);
		}
		model.addAttribute("results", results);
		return "halteinfo";
	}
	
	@GetMapping("/QBUZZ_Info/bussenInfo")
	public String bussenInfo(Model model) {
		//BusManager.checkTestSet();
		ArrayList<Result> results = new ArrayList<Result>();
		HashMap<String, String> getLaatsteHaltes = BusManager.getLaatsteHaltes();
		Set<String> bussen = getLaatsteHaltes.keySet();
		for (String bus: bussen) {
			Result r = new Result();
			r.busID = bus;
			r.eindpunt = BusManager.getEindpunt(bus);
			r.lijn = BusManager.getBusLijn(bus);
			r.halte = getLaatsteHaltes.get(bus);
			results.add(r);
		}
		model.addAttribute("results", results);
		return "busseninfo";
	}
	
	@GetMapping("/QBUZZ_Info/aangekomen")
	public String aangekomen(Model model) {
		//BusManager.checkTestSet();
		ArrayList<Result> results = new ArrayList<Result>();
		Set<String> bussen = BusManager.getAangekomenBussen();
		for (String bus: bussen) {
			Result r = new Result();
			r.busID = bus;
			r.eindpunt = BusManager.getEindpunt(bus);
			r.lijn = BusManager.getBusLijn(bus);
			r.aankomsttijd = BusManager.aankomstTijdVanBus(bus);
			results.add(r);
		}
		model.addAttribute("results", results);
		return "aangekomen";
	}
}
