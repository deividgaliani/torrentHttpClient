package view;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JTextField;

import service.TorrentHttpClient;

import javax.swing.JButton;
import javax.swing.BoxLayout;
import javax.swing.JTextArea;

public class BuscaTorrentFrame extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private JTextField linkTextField;
	private JTextArea saidaLinksTextArea;

	public BuscaTorrentFrame() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Torrent Getter");
		setSize(600, 300);
		initComponents();
	}

	private void initComponents() {
		JPanel parentPanel = new JPanel();
		getContentPane().add(parentPanel, BorderLayout.CENTER);
		parentPanel.setLayout(new BoxLayout(parentPanel, BoxLayout.Y_AXIS));

		JPanel linkPanel = new JPanel();
		parentPanel.add(linkPanel);

		linkTextField = new JTextField();
		linkPanel.add(linkTextField);
		linkTextField.setColumns(10);

		JButton btnBuscarLinks = new JButton("Buscar Links");
		btnBuscarLinks.addActionListener(criaAcaoBotaoBuscarLinks());
		linkPanel.add(btnBuscarLinks);

		JPanel saidaPanel = new JPanel();
		parentPanel.add(saidaPanel);

		saidaLinksTextArea = new JTextArea();
		saidaPanel.add(saidaLinksTextArea);
	}

	private ActionListener criaAcaoBotaoBuscarLinks() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				acaoBotaoBuscar();
			}
		};
	}

	private void acaoBotaoBuscar() {
		String url = linkTextField.getText();
		TorrentHttpClient torrentClient = new TorrentHttpClient(url);
		List<String> listaLinks = torrentClient.obterMagnetLinks();
		String mensagem = "";
		if(!listaLinks.isEmpty()) {
			for (String link : listaLinks) {
				mensagem += link + "\n";
			}
		}else {
//			System.out.println("Nenhum link encontrado");
			mensagem += "Nenhum link encontrado";
		}
		saidaLinksTextArea.setText(mensagem);
	}

}