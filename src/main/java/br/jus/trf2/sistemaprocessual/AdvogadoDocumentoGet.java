package br.jus.trf2.sistemaprocessual;

import br.jus.trf2.sistemaprocessual.ISistemaProcessual.AdvogadoDocumentoGetRequest;
import br.jus.trf2.sistemaprocessual.ISistemaProcessual.AdvogadoDocumentoGetResponse;
import br.jus.trf2.sistemaprocessual.ISistemaProcessual.IAdvogadoDocumentoGet;

public class AdvogadoDocumentoGet implements IAdvogadoDocumentoGet {

	@Override
	public void run(AdvogadoDocumentoGetRequest req, AdvogadoDocumentoGetResponse resp) throws Exception {
	}

	@Override
	public String getContext() {
		return "obter documento de advogado";
	}
}
