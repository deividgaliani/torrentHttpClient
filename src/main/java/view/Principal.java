package view;

import java.util.List;

import javax.swing.JOptionPane;

import service.TorrentHttpClient;

public class Principal {

	public static void main(String[] args) {
		String url = JOptionPane.showInputDialog("Insira a URL a ser buscada");
		TorrentHttpClient torrentClient = new TorrentHttpClient(url);
		List<String> listaLinks = torrentClient.obterMagnetLink();
		for (String link : listaLinks) {
			System.out.println(link);
		}
	}

}
