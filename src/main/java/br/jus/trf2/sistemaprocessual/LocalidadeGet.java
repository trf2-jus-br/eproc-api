package br.jus.trf2.sistemaprocessual;

import br.jus.trf2.sistemaprocessual.ISistemaProcessual.ILocalidadeGet;
import br.jus.trf2.sistemaprocessual.ISistemaProcessual.LocalidadeGetRequest;
import br.jus.trf2.sistemaprocessual.ISistemaProcessual.LocalidadeGetResponse;

public class LocalidadeGet implements ILocalidadeGet {

	@Override
	public void run(LocalidadeGetRequest req, LocalidadeGetResponse resp) throws Exception {
	}

	@Override
	public String getContext() {
		return "obter localidade";
	}
}
