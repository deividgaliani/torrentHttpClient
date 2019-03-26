package view;

import javax.swing.JOptionPane;

import service.TorrentHttpClient;

public class Principal {

	public static void main(String[] args) {
		String url = JOptionPane.showInputDialog("Insira a URL a ser buscada");
		TorrentHttpClient torrentClient = new TorrentHttpClient(url);
		String magnetLink = torrentClient.obterMagnetLink();
		System.out.println(magnetLink);
	}

}
