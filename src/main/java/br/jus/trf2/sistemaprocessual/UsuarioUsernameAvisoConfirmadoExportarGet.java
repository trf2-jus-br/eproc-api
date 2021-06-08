package br.jus.trf2.sistemaprocessual;

import br.jus.trf2.sistemaprocessual.ISistemaProcessual.IUsuarioUsernameAvisoConfirmadoExportarGet;

public class UsuarioUsernameAvisoConfirmadoExportarGet implements IUsuarioUsernameAvisoConfirmadoExportarGet {

	@Override
	public void run(Request req, Response resp, SistemaProcessualContext ctx) throws Exception {
	}

	@Override
	public String getContext() {
		return "exportar avisos confirmados";
	}
}
