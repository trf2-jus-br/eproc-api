package br.jus.trf2.sistemaprocessual;

import br.jus.trf2.sistemaprocessual.ISistemaProcessual.IUsuarioUsernameLocalIdMesaId2MovimentosGet;
import br.jus.trf2.sistemaprocessual.ISistemaProcessual.UsuarioUsernameLocalIdMesaId2MovimentosGetRequest;
import br.jus.trf2.sistemaprocessual.ISistemaProcessual.UsuarioUsernameLocalIdMesaId2MovimentosGetResponse;

public class UsuarioUsernameLocalIdMesaId2MovimentosGet implements IUsuarioUsernameLocalIdMesaId2MovimentosGet {

	@Override
	public void run(UsuarioUsernameLocalIdMesaId2MovimentosGetRequest req,
			UsuarioUsernameLocalIdMesaId2MovimentosGetResponse resp) throws Exception {
	}

	@Override
	public String getContext() {
		return "listar movimentos";
	}
}
