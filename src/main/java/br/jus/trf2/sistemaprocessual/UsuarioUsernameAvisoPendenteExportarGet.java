package br.jus.trf2.sistemaprocessual;

import br.jus.trf2.sistemaprocessual.ISistemaProcessual.IUsuarioUsernameAvisoPendenteExportarGet;
import br.jus.trf2.sistemaprocessual.ISistemaProcessual.UsuarioUsernameAvisoPendenteExportarGetRequest;
import br.jus.trf2.sistemaprocessual.ISistemaProcessual.UsuarioUsernameAvisoPendenteExportarGetResponse;

public class UsuarioUsernameAvisoPendenteExportarGet implements IUsuarioUsernameAvisoPendenteExportarGet {

	@Override
	public void run(UsuarioUsernameAvisoPendenteExportarGetRequest req,
			UsuarioUsernameAvisoPendenteExportarGetResponse resp) throws Exception {
	}

	@Override
	public String getContext() {
		return "exportar avisos pendentes";
	}
}
