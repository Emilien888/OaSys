import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ConnectGUI implements ActionListener{
	
	private JFrame connect;
	private JPanel connectp;
	private JButton bconnect;
	private JTextField ip;
	private JTextField port;
	private JLabel tip;
	private JLabel tport;
	
	public ConnectGUI() {
		connect = new JFrame();
		connectp = new JPanel();
		
		connect.setBounds(500, 250, 100, 40);
		
		tip = new JLabel("Enter the IP adress: ");
		tport = new JLabel("Enter the port here:");
		
		bconnect = new JButton("Connect");
		bconnect.addActionListener(this);
		
		ip = new JTextField();
		port = new JTextField();
		
		connectp.setBorder(BorderFactory.createEmptyBorder(50, 50, 40, 40));
		connectp.setLayout(new GridLayout(0, 1));
		connectp.add(tip);
		connectp.add(ip);
		connectp.add(tport);
		connectp.add(port);
		connectp.add(bconnect);
		
		connect.add(connectp, BorderLayout.CENTER);
		connect.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		connect.setTitle("Connect to a server");
		connect.pack();
		connect.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == bconnect) {
			connect.setVisible(false);
			new MainGUI(ip.getText(), Integer.parseInt(port.getText()));
		}
	}
	
}
