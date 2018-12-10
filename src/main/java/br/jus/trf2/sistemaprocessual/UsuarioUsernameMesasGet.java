package br.jus.trf2.sistemaprocessual;

import br.jus.trf2.sistemaprocessual.ISistemaProcessual.IUsuarioUsernameMesasGet;
import br.jus.trf2.sistemaprocessual.ISistemaProcessual.UsuarioUsernameMesasGetRequest;
import br.jus.trf2.sistemaprocessual.ISistemaProcessual.UsuarioUsernameMesasGetResponse;

public class UsuarioUsernameMesasGet implements IUsuarioUsernameMesasGet {

	@Override
	public void run(UsuarioUsernameMesasGetRequest req, UsuarioUsernameMesasGetResponse resp) throws Exception {
	}

	@Override
	public String getContext() {
		return "obter a mesa";
	}
}
