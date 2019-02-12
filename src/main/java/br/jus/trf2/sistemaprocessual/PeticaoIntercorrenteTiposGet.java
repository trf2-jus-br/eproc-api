package br.jus.trf2.sistemaprocessual;

import java.util.ArrayList;

import br.jus.trf2.sistemaprocessual.ISistemaProcessual.IPeticaoIntercorrenteTiposGet;
import br.jus.trf2.sistemaprocessual.ISistemaProcessual.PeticaoIntercorrenteTiposGetRequest;
import br.jus.trf2.sistemaprocessual.ISistemaProcessual.PeticaoIntercorrenteTiposGetResponse;
import br.jus.trf2.sistemaprocessual.ISistemaProcessual.TipoPeticaoIntercorrente;

public class PeticaoIntercorrenteTiposGet implements IPeticaoIntercorrenteTiposGet {

	@Override
	public void run(PeticaoIntercorrenteTiposGetRequest req, PeticaoIntercorrenteTiposGetResponse resp)
			throws Exception {
		resp.list = new ArrayList<TipoPeticaoIntercorrente>();
		TipoPeticaoIntercorrente tpi = new TipoPeticaoIntercorrente();
		tpi.id = "1";
		tpi.descricao = "Teste";
		tpi.orgao = "JFRJ";
		resp.list.add(tpi);
	}

	@Override
	public String getContext() {
		return "obter lista de tipos de petição intercorrente";
	}
}
