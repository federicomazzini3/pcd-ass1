package pcd.ass1;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

public class ShowGUI extends JFrame implements ActionListener{   
	private JLabel lblDirectoryPDF;
	//private JFileChooser btnDirectoryChooser;
	private JLabel lblFileToIgnore;
	private JLabel lblOccurrencies;
	private JTextField numberOfWordsToRetrive;
	//private JFileChooser btnFileChooser;
	private JButton btnStart;
	private JButton btnStop;
	private JButton btnDirectoryChooser;
	private JButton btnToIgnoreFileChooser;
	private JLabel lblTotalWords;
	private JTextField numberOfWords;
	private JLabel lblOccurrenciesRetrive;
	private Controller controller;
	
	public ShowGUI(Controller controller, int initialValue) {
		
		this.controller = controller;
			
		setFont(new Font("Tahoma", Font.PLAIN, 16));
		setTitle("GUI SUCCULENTA");
		setSize(900, 1000);
		getContentPane().setFont(new Font("Tahoma", Font.PLAIN, 16));
		
		JPanel panel = new JPanel();
		panel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(panel, GroupLayout.DEFAULT_SIZE, 436, Short.MAX_VALUE)
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(panel, GroupLayout.DEFAULT_SIZE, 263, Short.MAX_VALUE)
		);
		
		lblDirectoryPDF = new JLabel("Directory contenente i PDF");
		lblDirectoryPDF.setFont(new Font("Tahoma", Font.PLAIN, 16));
		
		/*btnDirectoryChooser = new JFileChooser("Directory");
		btnDirectoryChooser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnDirectoryChooser.setFont(new Font("Tahoma", Font.PLAIN, 16));*/
		
		lblOccurrencies = new JLabel("Numero di occorrenze che si vuole ottenere");
		lblOccurrencies.setFont(new Font("Tahoma", Font.PLAIN, 16));
		
		numberOfWordsToRetrive = new JTextField();
		numberOfWordsToRetrive.setFont(new Font("Tahoma", Font.PLAIN, 16));
		numberOfWordsToRetrive.setColumns(10);
		
		lblFileToIgnore = new JLabel("File con parole da ignorare");
		lblFileToIgnore.setFont(new Font("Tahoma", Font.PLAIN, 16));
		
		/*btnFileChooser = new JFileChooser("File");
		btnFileChooser.setFont(new Font("Tahoma", Font.PLAIN, 16));*/
		
		btnStop = new JButton("Stop");
		btnStop.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnStop.setEnabled(false);
		
		btnStart = new JButton("Start");
		btnStart.setSelected(true);
		btnStart.setFont(new Font("Tahoma", Font.PLAIN, 16));
		
		btnDirectoryChooser = new JButton("Scegli directory");
		btnDirectoryChooser.setEnabled(true);
		btnDirectoryChooser.setFont(new Font("Tahoma", Font.PLAIN, 16));
		
		btnToIgnoreFileChooser = new JButton("Scegli file");
		btnToIgnoreFileChooser.setEnabled(true);
		btnToIgnoreFileChooser.setFont(new Font("Tahoma", Font.PLAIN, 16));
		
		lblTotalWords = new JLabel("Totale parole analizzate");
		lblTotalWords.setFont(new Font("Tahoma", Font.PLAIN, 16));
		
		numberOfWords = new JTextField();
		numberOfWords.setFont(new Font("Tahoma", Font.PLAIN, 16));
		numberOfWords.setColumns(10);
		numberOfWords.setEditable(false);		
		numberOfWords.setText("0");
		
		lblOccurrenciesRetrive = new JLabel("Occorrenze");
		lblOccurrenciesRetrive.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addContainerGap()
							.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
								.addGroup(gl_panel.createSequentialGroup()
									.addComponent(lblFileToIgnore, GroupLayout.PREFERRED_SIZE, 212, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED, 297, Short.MAX_VALUE))
								.addGroup(gl_panel.createSequentialGroup()
									.addComponent(btnStart, GroupLayout.PREFERRED_SIZE, 93, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(btnStop, GroupLayout.PREFERRED_SIZE, 81, GroupLayout.PREFERRED_SIZE)
									.addGap(2))
								.addGroup(gl_panel.createSequentialGroup()
									.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING, false)
										.addComponent(lblDirectoryPDF, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(lblOccurrencies, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 347, GroupLayout.PREFERRED_SIZE)
										.addComponent(lblTotalWords, GroupLayout.PREFERRED_SIZE, 222, GroupLayout.PREFERRED_SIZE))
									.addPreferredGap(ComponentPlacement.RELATED, 83, Short.MAX_VALUE)
									.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
										.addComponent(numberOfWords, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(numberOfWordsToRetrive, Alignment.TRAILING, 134, 134, 134)
										.addComponent(btnDirectoryChooser, Alignment.TRAILING)
										.addComponent(btnToIgnoreFileChooser, Alignment.TRAILING)))))
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(135)
							.addComponent(lblOccurrenciesRetrive, GroupLayout.PREFERRED_SIZE, 107, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblDirectoryPDF)
						.addComponent(btnDirectoryChooser))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblOccurrencies)
						.addComponent(numberOfWordsToRetrive, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblFileToIgnore)
						.addComponent(btnToIgnoreFileChooser))
					.addGap(66)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblTotalWords)
						.addComponent(numberOfWords, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addComponent(lblOccurrenciesRetrive)
					.addPreferredGap(ComponentPlacement.RELATED, 76, Short.MAX_VALUE)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnStop)
						.addComponent(btnStart))
					.addContainerGap())
		);
		panel.setLayout(gl_panel);
		getContentPane().setLayout(groupLayout);
		
		btnStart.addActionListener(this);
		btnStop.addActionListener(this);	
		btnDirectoryChooser.addActionListener(this);
		btnToIgnoreFileChooser.addActionListener(this);
	}
	
	//switch
	public void actionPerformed(ActionEvent ev){
		Object src = ev.getSource();
		if (src==btnStart){	
			controller.setNumberOfWords(Integer.parseInt(numberOfWordsToRetrive.getText()));
			controller.notifyStarted();
		} else if (src == btnStop){
			//controller.notifyStopped();
			btnStart.setEnabled(true);
			btnStop.setEnabled(false);
		} else if (src == btnDirectoryChooser){
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
			fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			int result = fileChooser.showOpenDialog(this);
			if (result == JFileChooser.APPROVE_OPTION) {
			    File selectedFile = fileChooser.getSelectedFile();
			    controller.setDirectoryPdf(selectedFile.getAbsolutePath());
			    lblDirectoryPDF.setText(selectedFile.getAbsolutePath());
			}
		} else if (src == btnToIgnoreFileChooser){
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
			int result = fileChooser.showOpenDialog(this);
			if (result == JFileChooser.APPROVE_OPTION) {
			    File selectedFile = fileChooser.getSelectedFile();
			    controller.setToIgnoreFile(selectedFile.getAbsolutePath());
			    lblFileToIgnore.setText(selectedFile.getAbsolutePath());
			}
		}	
	}
	
	public void display() {
        javax.swing.SwingUtilities.invokeLater(() -> {
        	this.setVisible(true);
        });
    }
}
