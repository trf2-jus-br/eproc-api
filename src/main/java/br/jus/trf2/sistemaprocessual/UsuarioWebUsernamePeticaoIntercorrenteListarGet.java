package br.jus.trf2.sistemaprocessual;

import br.jus.trf2.sistemaprocessual.ISistemaProcessual.IUsuarioWebUsernamePeticaoIntercorrenteListarGet;
import br.jus.trf2.sistemaprocessual.ISistemaProcessual.UsuarioWebUsernamePeticaoIntercorrenteListarGetRequest;
import br.jus.trf2.sistemaprocessual.ISistemaProcessual.UsuarioWebUsernamePeticaoIntercorrenteListarGetResponse;

public class UsuarioWebUsernamePeticaoIntercorrenteListarGet
		implements IUsuarioWebUsernamePeticaoIntercorrenteListarGet {

	@Override
	public void run(UsuarioWebUsernamePeticaoIntercorrenteListarGetRequest req,
			UsuarioWebUsernamePeticaoIntercorrenteListarGetResponse resp) throws Exception {
	}

	@Override
	public String getContext() {
		return "listar petições intercorrentes";
	}
}
