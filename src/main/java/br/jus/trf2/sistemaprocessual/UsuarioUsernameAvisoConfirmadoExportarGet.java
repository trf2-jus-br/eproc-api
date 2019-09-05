package br.jus.trf2.sistemaprocessual;

import br.jus.trf2.sistemaprocessual.ISistemaProcessual.IUsuarioUsernameAvisoConfirmadoExportarGet;
import br.jus.trf2.sistemaprocessual.ISistemaProcessual.UsuarioUsernameAvisoConfirmadoExportarGetRequest;
import br.jus.trf2.sistemaprocessual.ISistemaProcessual.UsuarioUsernameAvisoConfirmadoExportarGetResponse;

public class UsuarioUsernameAvisoConfirmadoExportarGet implements IUsuarioUsernameAvisoConfirmadoExportarGet {

	@Override
	public void run(UsuarioUsernameAvisoConfirmadoExportarGetRequest req,
			UsuarioUsernameAvisoConfirmadoExportarGetResponse resp) throws Exception {
	}

	@Override
	public String getContext() {
		return "exportar avisos confirmados";
	}
}
