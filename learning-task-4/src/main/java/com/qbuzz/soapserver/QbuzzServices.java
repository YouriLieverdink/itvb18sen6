package com.qbuzz.soapserver;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService
public class QbuzzServices {

	@WebMethod
	public String receiveBusBijHalte(@WebParam(name = "haltebericht") HalteBericht halteBericht) {
		return BusManager.verwerkHalteBericht(halteBericht);
	}
	
	@WebMethod
	public String receiveBusBijEindpunt(@WebParam(name = "aankomstbericht") AankomstBericht aankomstBericht) {
		return BusManager.verwerkAankomstBericht(aankomstBericht);
	}
	
	@WebMethod
	public int logEtas(@WebParam(name = "bericht") Bericht bericht) {
		ETAPersistence.addBericht(bericht);
		return ETAPersistence.getAantalBerichten();
	}
}
