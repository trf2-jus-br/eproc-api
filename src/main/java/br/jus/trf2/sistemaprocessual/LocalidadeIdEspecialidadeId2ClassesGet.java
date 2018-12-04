package br.jus.trf2.sistemaprocessual;

import br.jus.trf2.sistemaprocessual.ISistemaProcessual.ILocalidadeIdEspecialidadeId2ClasseGet;
import br.jus.trf2.sistemaprocessual.ISistemaProcessual.LocalidadeIdEspecialidadeId2ClasseGetRequest;
import br.jus.trf2.sistemaprocessual.ISistemaProcessual.LocalidadeIdEspecialidadeId2ClasseGetResponse;

public class LocalidadeIdEspecialidadeId2ClassesGet implements ILocalidadeIdEspecialidadeId2ClasseGet {

	@Override
	public void run(LocalidadeIdEspecialidadeId2ClasseGetRequest req,
			LocalidadeIdEspecialidadeId2ClasseGetResponse resp) throws Exception {
	}

	@Override
	public String getContext() {
		return "obter classes da especialidade da localidade";
	}
}
