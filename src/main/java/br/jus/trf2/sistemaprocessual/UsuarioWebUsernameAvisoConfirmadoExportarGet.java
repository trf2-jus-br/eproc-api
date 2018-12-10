package br.jus.trf2.sistemaprocessual;

import br.jus.trf2.sistemaprocessual.ISistemaProcessual.IUsuarioWebUsernameAvisoConfirmadoExportarGet;
import br.jus.trf2.sistemaprocessual.ISistemaProcessual.UsuarioWebUsernameAvisoConfirmadoExportarGetRequest;
import br.jus.trf2.sistemaprocessual.ISistemaProcessual.UsuarioWebUsernameAvisoConfirmadoExportarGetResponse;

public class UsuarioWebUsernameAvisoConfirmadoExportarGet implements IUsuarioWebUsernameAvisoConfirmadoExportarGet {

	@Override
	public void run(UsuarioWebUsernameAvisoConfirmadoExportarGetRequest req,
			UsuarioWebUsernameAvisoConfirmadoExportarGetResponse resp) throws Exception {
	}

	@Override
	public String getContext() {
		return "exportar avisos confirmados";
	}
}
