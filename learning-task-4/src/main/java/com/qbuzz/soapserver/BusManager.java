package com.qbuzz.soapserver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

public class BusManager {

	private static HashMap<String,HalteBericht> bussenOnderweg = new HashMap<String,HalteBericht>();
	private static HashMap<String, AankomstBericht> aangekomenBussen = new HashMap<String,AankomstBericht>();
	private static String[] eindpunten = {"A", "B", "C", "D", "E", "F"};
	private static String[] haltes = {"G", "H", "I", "J", "K", "L"};
	private static String[] lijnen = {"LIJN1", "LIJN2", "LIJN3", "LIJN4", "LIJN5", "LIJN6", "LIJN7", "LIJN8"};  
	private static Random r = new Random();
	
	public static String verwerkHalteBericht(HalteBericht nieuwHalteBericht) {
		String busID = nieuwHalteBericht.busID;
		String response = "No Action";
		if (bussenOnderweg.containsKey(busID)) {
			HalteBericht huidigeHalteBericht = bussenOnderweg.get(busID);
			if (nieuwHalteBericht.tijd>huidigeHalteBericht.tijd && !isAangekomen(busID)) {
				bussenOnderweg.put(busID, nieuwHalteBericht);
				response = "Updated";
			}
		} else {
			bussenOnderweg.put(busID, nieuwHalteBericht);
			response = "Added";
		}
		return response;
	}
	
	public static String verwerkAankomstBericht(AankomstBericht nieuwAankomstBericht) {
		String busID = nieuwAankomstBericht.busID;
		if (!isAangekomen(busID)) {
			if (bussenOnderweg.containsKey(busID)) {
				aangekomenBussen.put(busID, nieuwAankomstBericht);
				bussenOnderweg.remove(busID);
				return "Arrived";
			} else {
				return "Not Found";
			}
		}
		return "No Action";
	}
	
	public static Set<String> getLijstMetBussen() {
		return bussenOnderweg.keySet();
	}
	
	public static Set<String> getAangekomenBussen() {
		return aangekomenBussen.keySet();
	}
	public static HashMap<String, String> getLaatsteHaltes() {
		Iterator<String> it = bussenOnderweg.keySet().iterator();
		HashMap<String, String> LaatsteHaltes = new HashMap<String, String>();
		while (it.hasNext()) {
			String busID = it.next();
			String halteNaam = bussenOnderweg.get(busID).halte;
			LaatsteHaltes.put(busID, halteNaam);
		}
		return LaatsteHaltes;
	}
	
	public static HashMap<String, ArrayList<String>> bussenBijHaltes() {
		HashMap<String, ArrayList<String>> bussenBijHalte = new HashMap<String, ArrayList<String>>();
		ArrayList<String> lijstMetBussen;
		Iterator<String> it = bussenOnderweg.keySet().iterator();
		while (it.hasNext()) {
			String busID = it.next();
			String halteNaam = bussenOnderweg.get(busID).halte;
			if (bussenBijHalte.containsKey(halteNaam)) {
				lijstMetBussen = bussenBijHalte.get(halteNaam);
			} else {
				lijstMetBussen = new ArrayList<String>();
			}
			lijstMetBussen.add(getBusLijn(busID));
			bussenBijHalte.put(halteNaam, lijstMetBussen);
		}		
		return bussenBijHalte;
	}
	
	public static String getBusLijn(String busID) {
		if (bussenOnderweg.containsKey(busID)) {
			return bussenOnderweg.get(busID).lijn;			
		} else if (isAangekomen(busID)) {
			return aangekomenBussen.get(busID).lijn;
		}
		return "Not Found";
	}

	public static String getEindpunt(String busID) {
		if (bussenOnderweg.containsKey(busID)) {
			return bussenOnderweg.get(busID).eindpunt;			
		} else if (isAangekomen(busID)) {
			return aangekomenBussen.get(busID).eindpunt;
		}
		return "Not Found";
	}
	
	public static boolean isAangekomen(String busID) {
		return aangekomenBussen.containsKey(busID);
	}

	public static int aankomstTijdVanBus(String busID) {
		int aankomstTijd = -1;
		if (isAangekomen(busID)) {
			aankomstTijd=aangekomenBussen.get(busID).aankomsttijd;
		}
		return aankomstTijd;
	}
	
	public static void checkTestSet() {
		if (bussenOnderweg.size()+aangekomenBussen.size()==0) {
			testsetAanmaken();
		}
	}
	private static void testsetAanmaken() {
		for(int i=0; i<5+r.nextInt(10); i++) {
			HalteBericht hb = randomHalteBericht();
			verwerkHalteBericht(hb);
			if(r.nextInt(10)>=8) {
				AankomstBericht ab = randomAankomstBericht(hb);
				verwerkAankomstBericht(ab);				
			}
		}
	}
	
	private static HalteBericht randomHalteBericht() {
		HalteBericht hb = new HalteBericht();
		hb.eindpunt=eindpunten[r.nextInt(eindpunten.length)];
		hb.halte=haltes[r.nextInt(haltes.length)];
		hb.lijn=lijnen[r.nextInt(lijnen.length)];
		hb.tijd=r.nextInt(100);
		hb.busID=hb.lijn + hb.eindpunt + hb.tijd;
		return hb;
	}
	private static AankomstBericht randomAankomstBericht(HalteBericht hb) {
		AankomstBericht ab = new AankomstBericht();
		ab.eindpunt=hb.eindpunt;
		ab.lijn=hb.lijn;
		ab.aankomsttijd=hb.tijd+10;
		ab.busID=hb.busID;
		return ab;
	}

}
