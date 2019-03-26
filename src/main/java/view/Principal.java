package view;

import service.TorrentHttpClient;

public class Principal {

	public static void main(String[] args) {
		String url = "https://www.thepiratefilmes.biz/missao-amsterdam-2019-torrent-dublado/";
		TorrentHttpClient torrentClient = new TorrentHttpClient(url);
		String magnetLink = torrentClient.obterMagnetLink();
		System.out.println(magnetLink);
	}

}
