package pcd.ass1;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import java.awt.Font;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.Color;
import javax.swing.SwingConstants;

public class ShowGUI extends JFrame implements ActionListener {

	private JLabel lblDirectoryPDF;
	private JLabel lblFileToIgnore;
	private JLabel lblOccurrencies;
	private JLabel lblTotalWords;
	private JLabel lblOccurrenciesRetrive;
	private JLabel lblErrorRequiredField;
	private JLabel lblErrorRequiredField_1;
	private JButton btnStart;
	private JButton btnStop;
	private JButton btnDirectoryChooser;
	private JButton btnToIgnoreFileChooser;
	private JTextField wordsNumberTextField;
	private boolean directoryIsSet;

	enum Choice {
		DIRPDF, TOIGNFILE
	}

	private Controller controller;
	private JTextField counterWords;

	public ShowGUI(Controller controller) {

		this.controller = controller;

		setFont(new Font("Tahoma", Font.PLAIN, 16));
		setTitle("GUI SUCCULENTA");
		setSize(900, 500);
		getContentPane().setFont(new Font("Tahoma", Font.PLAIN, 16));

		JPanel panel = new JPanel();
		panel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addComponent(panel,
				GroupLayout.DEFAULT_SIZE, 436, Short.MAX_VALUE));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addComponent(panel,
				GroupLayout.DEFAULT_SIZE, 263, Short.MAX_VALUE));

		lblDirectoryPDF = new JLabel("Directory contenente i PDF");
		lblDirectoryPDF.setFont(new Font("Tahoma", Font.PLAIN, 16));

		lblOccurrencies = new JLabel("Numero di occorrenze che si vuole ottenere");
		lblOccurrencies.setFont(new Font("Tahoma", Font.PLAIN, 16));

		wordsNumberTextField = new JTextField();
		wordsNumberTextField.setFont(new Font("Tahoma", Font.PLAIN, 16));
		wordsNumberTextField.setColumns(10);

		lblFileToIgnore = new JLabel("File con parole da ignorare");
		lblFileToIgnore.setFont(new Font("Tahoma", Font.PLAIN, 16));
		
		lblErrorRequiredField = new JLabel("");
		lblErrorRequiredField.setFont(new Font("Tahoma", Font.PLAIN, 16));

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

		lblOccurrenciesRetrive = new JLabel("Occorrenze");
		lblOccurrenciesRetrive.setFont(new Font("Tahoma", Font.PLAIN, 16));
		
		lblErrorRequiredField_1 = new JLabel("Attenzione, inserire Directory e numero di occorrenze");
		lblErrorRequiredField_1.setForeground(Color.RED);
		lblErrorRequiredField_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblErrorRequiredField_1.setVisible(false);
		
		counterWords = new JTextField();
		counterWords.setHorizontalAlignment(SwingConstants.RIGHT);
		counterWords.setFont(new Font("Tahoma", Font.PLAIN, 16));
		counterWords.setEditable(false);
		counterWords.setRequestFocusEnabled(false);
		counterWords.setText("0");
		counterWords.setColumns(10);
		
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addContainerGap()
							.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
								.addGroup(gl_panel.createSequentialGroup()
									.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING, false)
										.addComponent(lblDirectoryPDF, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(lblOccurrencies, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 347, GroupLayout.PREFERRED_SIZE)
										.addComponent(lblTotalWords, GroupLayout.PREFERRED_SIZE, 222, GroupLayout.PREFERRED_SIZE))
									.addPreferredGap(ComponentPlacement.RELATED, 380, Short.MAX_VALUE)
									.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
										.addComponent(btnDirectoryChooser)
										.addComponent(wordsNumberTextField, 134, 134, 134)
										.addGroup(gl_panel.createSequentialGroup()
											.addComponent(counterWords, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(ComponentPlacement.RELATED))))
								.addGroup(Alignment.LEADING, gl_panel.createSequentialGroup()
									.addComponent(lblFileToIgnore, GroupLayout.PREFERRED_SIZE, 492, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED, 275, Short.MAX_VALUE)
									.addComponent(btnToIgnoreFileChooser))
								.addGroup(Alignment.LEADING, gl_panel.createSequentialGroup()
									.addComponent(btnStart, GroupLayout.PREFERRED_SIZE, 93, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(btnStop, GroupLayout.PREFERRED_SIZE, 81, GroupLayout.PREFERRED_SIZE)))
							.addGap(10))
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(135)
							.addComponent(lblOccurrenciesRetrive, GroupLayout.PREFERRED_SIZE, 107, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblErrorRequiredField_1, GroupLayout.PREFERRED_SIZE, 438, GroupLayout.PREFERRED_SIZE)))
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
						.addComponent(wordsNumberTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblFileToIgnore)
						.addComponent(btnToIgnoreFileChooser))
					.addGap(18)
					.addComponent(lblErrorRequiredField_1)
					.addGap(34)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblTotalWords)
						.addComponent(counterWords, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addComponent(lblOccurrenciesRetrive)
					.addPreferredGap(ComponentPlacement.RELATED, 176, Short.MAX_VALUE)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnStart)
						.addComponent(btnStop))
					.addContainerGap())
		);
		panel.setLayout(gl_panel);
		getContentPane().setLayout(groupLayout);
		
		addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent ev){
				System.exit(-1);
			}
			public void windowClosed(WindowEvent ev){
				System.exit(-1);
			}
		});

		//add listeners to the button Directory e Files
		btnStart.addActionListener(this);
		btnStop.addActionListener(this);
		btnDirectoryChooser.addActionListener(this);
		btnToIgnoreFileChooser.addActionListener(this);
	}

	// switch
	public void actionPerformed(ActionEvent ev) {
		Object src = ev.getSource();
		if (src == btnStart) {
			if (checkRequiredFieldIsSet()) {
				controller.setNumberOfWords(Integer.parseInt(wordsNumberTextField.getText()));
				controller.notifyStarted();
				btnStart.setEnabled(false);
				btnStop.setEnabled(true);
			}
			else {
				lblErrorRequiredField_1.setVisible(true);
			}
		} else if (src == btnStop) {
			controller.notifyStopped();

			btnStart.setEnabled(true);
			btnStop.setEnabled(false);
		} else if (src == btnDirectoryChooser) {
			showPopup(Choice.DIRPDF);
		} else if (src == btnToIgnoreFileChooser) {
			showPopup(Choice.TOIGNFILE);
		}
	}
  
	public void setCountingState() {
		SwingUtilities.invokeLater(()-> {
			btnStart.setEnabled(false);
			btnStop.setEnabled(true);		
		});
	}

	public void setIdleState() {
		SwingUtilities.invokeLater(()-> {
			btnStart.setEnabled(true);
			btnStop.setEnabled(false);	
		});
	}

	/*public void updateCountValue(int value) {
		SwingUtilities.invokeLater(()-> {
			display.setText("" + value);
		});
	}*/

	public void display() {
		javax.swing.SwingUtilities.invokeLater(() -> {
			this.setVisible(true);
		});
	}

	public void showPopup(Enum<Choice> choice) {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
		lblErrorRequiredField_1.setVisible(false);
		if (choice == Choice.DIRPDF) {
			fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			int result = fileChooser.showOpenDialog(this);
			if (result == JFileChooser.APPROVE_OPTION) {
				File selectedFile = fileChooser.getSelectedFile();
				controller.setDirectoryPdf(selectedFile.getAbsolutePath());
				lblDirectoryPDF.setText(selectedFile.getAbsolutePath());
				this.directoryIsSet = true;
			}
		} else if (choice == Choice.TOIGNFILE) {
			int result = fileChooser.showOpenDialog(this);
			if (result == JFileChooser.APPROVE_OPTION) {
				File selectedFile = fileChooser.getSelectedFile();
				controller.setToIgnoreFile(selectedFile.getAbsolutePath());
				lblFileToIgnore.setText(selectedFile.getAbsolutePath());
			}
		}
	}

	public boolean checkRequiredFieldIsSet() {
		return this.directoryIsSet && !wordsNumberTextField.getText().equals("");
	}
}
