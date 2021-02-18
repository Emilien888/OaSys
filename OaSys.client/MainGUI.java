import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class MainGUI implements ActionListener{
	
	private JFrame main;
	private PrintWriter toServer;
	private static DefaultListModel<String> model = new DefaultListModel<String>();
	private static JList<String> list;
	
	private JPanel contentPane;
	private JTextField msg;
	private JButton btnNewButton;
	
	private static Socket connection;
	
	public static Socket getConnection() {
		return connection;
	}
	
	public static void addToFlux(String msg) {
		if(list == null){return;}
		model.addElement(msg);
		list.setModel(model);
	}
	
	public MainGUI(String ip, int port) {
		main = new JFrame();
		main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		main.setBounds(500, 250, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		main.setContentPane(contentPane);
		contentPane.setLayout(null);
		
		btnNewButton = new JButton(">");
		btnNewButton.setBounds(413, 243, 31, 29);
		contentPane.add(btnNewButton);
		
		btnNewButton.addActionListener(this);
		
		msg = new JTextField();
		msg.setBounds(6, 242, 402, 29);
		msg.setColumns(10);
		contentPane.add(msg);
		
		list = new JList<String>();
		list.setBounds(22, 15, 402, 215);
		contentPane.add(list);
		
		try {
			connection = new Socket(ip, port);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			toServer = new PrintWriter(new OutputStreamWriter(connection.getOutputStream()), true);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		main.setVisible(true);
		
		new ServerThreadScanner();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(msg.getText() == null) {return;}
		if(msg.getText().equals("/exit")) {
			list.removeAll();
			try {
				connection.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			main.setVisible(false);
			new ConnectGUI();
		}
		toServer.println(msg.getText());
		toServer.flush();
		msg.setText("");
		
	}
}
