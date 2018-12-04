package br.jus.trf2.sistemaprocessual;

import br.jus.trf2.sistemaprocessual.ISistemaProcessual.IOrgaoPublicoListarGet;
import br.jus.trf2.sistemaprocessual.ISistemaProcessual.OrgaoPublicoListarGetRequest;
import br.jus.trf2.sistemaprocessual.ISistemaProcessual.OrgaoPublicoListarGetResponse;

public class OrgaoPublicoListarGet implements IOrgaoPublicoListarGet {

	@Override
	public void run(OrgaoPublicoListarGetRequest req, OrgaoPublicoListarGetResponse resp) throws Exception {
	}

	@Override
	public String getContext() {
		return "obter lista de órgãos públicos";
	}
}
