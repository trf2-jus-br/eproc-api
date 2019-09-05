package br.jus.trf2.sistemaprocessual;

import br.jus.trf2.sistemaprocessual.ISistemaProcessual.IUsuarioUsernamePeticaoIntercorrenteListarGet;
import br.jus.trf2.sistemaprocessual.ISistemaProcessual.UsuarioUsernamePeticaoIntercorrenteListarGetRequest;
import br.jus.trf2.sistemaprocessual.ISistemaProcessual.UsuarioUsernamePeticaoIntercorrenteListarGetResponse;

public class UsuarioUsernamePeticaoIntercorrenteListarGet
		implements IUsuarioUsernamePeticaoIntercorrenteListarGet {

	@Override
	public void run(UsuarioUsernamePeticaoIntercorrenteListarGetRequest req,
			UsuarioUsernamePeticaoIntercorrenteListarGetResponse resp) throws Exception {
	}

	@Override
	public String getContext() {
		return "listar petições intercorrentes";
	}
}
