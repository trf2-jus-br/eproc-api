package br.jus.trf2.sistemaprocessual;

import br.jus.trf2.sistemaprocessual.ISistemaProcessual.ILocalidadeIdEspecialidadeGet;
import br.jus.trf2.sistemaprocessual.ISistemaProcessual.LocalidadeIdEspecialidadeGetRequest;
import br.jus.trf2.sistemaprocessual.ISistemaProcessual.LocalidadeIdEspecialidadeGetResponse;

public class LocalidadeIdEspecialidadeGet implements ILocalidadeIdEspecialidadeGet {

	@Override
	public void run(LocalidadeIdEspecialidadeGetRequest req, LocalidadeIdEspecialidadeGetResponse resp)
			throws Exception {
	}

	@Override
	public String getContext() {
		return "obter especialidades da localidade";
	}
}
