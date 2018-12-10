package br.jus.trf2.sistemaprocessual;

import br.jus.trf2.sistemaprocessual.ISistemaProcessual.IUsuarioWebUsernameAvisoPendenteExportarGet;
import br.jus.trf2.sistemaprocessual.ISistemaProcessual.UsuarioWebUsernameAvisoPendenteExportarGetRequest;
import br.jus.trf2.sistemaprocessual.ISistemaProcessual.UsuarioWebUsernameAvisoPendenteExportarGetResponse;

public class UsuarioWebUsernameAvisoPendenteExportarGet implements IUsuarioWebUsernameAvisoPendenteExportarGet {

	@Override
	public void run(UsuarioWebUsernameAvisoPendenteExportarGetRequest req,
			UsuarioWebUsernameAvisoPendenteExportarGetResponse resp) throws Exception {
	}

	@Override
	public String getContext() {
		return "exportar avisos pendentes";
	}
}
