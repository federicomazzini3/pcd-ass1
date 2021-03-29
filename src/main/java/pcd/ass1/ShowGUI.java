package pcd.ass1;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;

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
	private JButton btnStart;
	private JButton btnStop;
	private JButton btnDirectoryChooser;
	private JButton btnToIgnoreFileChooser;
	private JTextField wordsNumberTextField;
	private JLabel lblShowOccurrencies;
	private JButton btnReset;
	private boolean directoryIsSet;
	
	private static final String TITLE = "PDF Analyzer";
    private static final String DIR_CHOOSER_LBL = "Directory PDF";
    private static final String TOIGNFILE_CHOOSER_LBL = "File con parole da ignorare";
    private static final String TOIGNFILE_CHOOSER = "Scegli file";
    private static final String DIR_CHOOSER = "Scegli directory";
    private static final String N_WORDS_LBL = "Numero di occorrenze massime";
    private static final String REQ_FIELD_ERR_MSG = "Attenzione, inserire almeno directory pdf e numero di occorrenze";
    private static final String OCCURRENCE = "Occorrenze:";
    private static final String AN_WORDS_LBL = "Totale parole analizzate";

	enum Choice {
		DIRPDF, TOIGNFILE
	}

	private Controller controller;
	private JTextField counterWords;

	public ShowGUI(Controller controller) {

		this.controller = controller;

		setFont(new Font("Tahoma", Font.PLAIN, 16));
		setTitle(TITLE);
		setSize(1100, 500);
		getContentPane().setFont(new Font("Tahoma", Font.PLAIN, 16));

		JPanel panel = new JPanel();
		panel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addComponent(panel,
				GroupLayout.DEFAULT_SIZE, 436, Short.MAX_VALUE));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addComponent(panel,
				GroupLayout.DEFAULT_SIZE, 263, Short.MAX_VALUE));

		lblDirectoryPDF = new JLabel(DIR_CHOOSER_LBL);
		lblDirectoryPDF.setFont(new Font("Tahoma", Font.PLAIN, 16));

		lblOccurrencies = new JLabel(N_WORDS_LBL);
		lblOccurrencies.setFont(new Font("Tahoma", Font.PLAIN, 16));

		wordsNumberTextField = new JTextField();
		wordsNumberTextField.setFont(new Font("Tahoma", Font.PLAIN, 16));
		wordsNumberTextField.setColumns(10);

		lblFileToIgnore = new JLabel(TOIGNFILE_CHOOSER_LBL);
		lblFileToIgnore.setFont(new Font("Tahoma", Font.PLAIN, 16));

		btnStop = new JButton("Stop");
		btnStop.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnStop.setEnabled(false);

		btnStart = new JButton("Start");
		btnStart.setSelected(true);
		btnStart.setFont(new Font("Tahoma", Font.PLAIN, 16));

		btnDirectoryChooser = new JButton(DIR_CHOOSER);
		btnDirectoryChooser.setEnabled(true);
		btnDirectoryChooser.setFont(new Font("Tahoma", Font.PLAIN, 16));

		btnToIgnoreFileChooser = new JButton(TOIGNFILE_CHOOSER);
		btnToIgnoreFileChooser.setEnabled(true);
		btnToIgnoreFileChooser.setFont(new Font("Tahoma", Font.PLAIN, 16));

		lblTotalWords = new JLabel(AN_WORDS_LBL);
		lblTotalWords.setFont(new Font("Tahoma", Font.PLAIN, 16));

		lblOccurrenciesRetrive = new JLabel(OCCURRENCE);
		lblOccurrenciesRetrive.setFont(new Font("Tahoma", Font.PLAIN, 16));

		lblErrorRequiredField = new JLabel(REQ_FIELD_ERR_MSG);
		lblErrorRequiredField.setForeground(Color.RED);
		lblErrorRequiredField.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblErrorRequiredField.setVisible(false);

		counterWords = new JTextField();
		counterWords.setHorizontalAlignment(SwingConstants.RIGHT);
		counterWords.setFont(new Font("Tahoma", Font.PLAIN, 16));
		counterWords.setEditable(false);
		counterWords.setRequestFocusEnabled(false);
		counterWords.setText("0");
		counterWords.setColumns(10);
		
		lblShowOccurrencies = new JLabel("");
		lblShowOccurrencies.setFont(new Font("Tahoma", Font.PLAIN, 16));
		
		btnReset = new JButton("Reset");
		btnReset.setFont(new Font("Tahoma", Font.PLAIN, 16));

		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(Alignment.TRAILING, gl_panel.createSequentialGroup()
							.addContainerGap()
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panel.createSequentialGroup()
									.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING, false)
										.addComponent(lblDirectoryPDF, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(lblOccurrencies, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 347, GroupLayout.PREFERRED_SIZE)
										.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
											.addComponent(lblOccurrenciesRetrive, GroupLayout.PREFERRED_SIZE, 107, GroupLayout.PREFERRED_SIZE)
											.addComponent(lblTotalWords, GroupLayout.PREFERRED_SIZE, 222, GroupLayout.PREFERRED_SIZE)))
									.addPreferredGap(ComponentPlacement.RELATED, 570, Short.MAX_VALUE)
									.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
										.addComponent(btnDirectoryChooser)
										.addComponent(wordsNumberTextField, 134, 134, 134)
										.addGroup(gl_panel.createSequentialGroup()
											.addComponent(counterWords, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(ComponentPlacement.RELATED))))
								.addGroup(gl_panel.createSequentialGroup()
									.addComponent(lblFileToIgnore, GroupLayout.PREFERRED_SIZE, 492, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED, 465, Short.MAX_VALUE)
									.addComponent(btnToIgnoreFileChooser)))
							.addGap(10))
						.addGroup(Alignment.TRAILING, gl_panel.createSequentialGroup()
							.addGap(32)
							.addComponent(lblShowOccurrencies, GroupLayout.DEFAULT_SIZE, 1044, Short.MAX_VALUE))
						.addGroup(Alignment.TRAILING, gl_panel.createSequentialGroup()
							.addContainerGap()
							.addComponent(btnStart, GroupLayout.PREFERRED_SIZE, 93, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(btnStop, GroupLayout.PREFERRED_SIZE, 81, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(btnReset))
						.addGroup(gl_panel.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblErrorRequiredField, GroupLayout.PREFERRED_SIZE, 438, GroupLayout.PREFERRED_SIZE)))
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
					.addComponent(lblErrorRequiredField)
					.addGap(34)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblTotalWords)
						.addComponent(counterWords, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addComponent(lblOccurrenciesRetrive)
					.addGap(23)
					.addComponent(lblShowOccurrencies, GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 52, Short.MAX_VALUE)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnReset)
						.addComponent(btnStop)
						.addComponent(btnStart))
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
		btnReset.addActionListener(this);
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
				btnReset.setEnabled(false);
				lblErrorRequiredField.setVisible(false);
			}
			else {
				lblErrorRequiredField.setVisible(true);
			}
		} else if (src == btnStop) {
			controller.notifyStopped();
			btnStart.setEnabled(true);
			btnStop.setEnabled(false);
			btnReset.setEnabled(true);
		} else if (src == btnReset){
			controller.notifyReset();
		} else if (src == btnDirectoryChooser) {
			showPopup(Choice.DIRPDF);
		} else if (src == btnToIgnoreFileChooser) {
			showPopup(Choice.TOIGNFILE);
		}
	}

	public void showPopup(Enum<Choice> choice) {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
		lblErrorRequiredField.setVisible(false);
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
			FileNameExtensionFilter filter = new FileNameExtensionFilter("TEXT FILES", "txt", "text");
			fileChooser.setFileFilter(filter);
			fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
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

	/*public void setCountingState() {
		SwingUtilities.invokeLater(()-> {
			btnStart.setEnabled(false);
			btnStop.setEnabled(true);		
		});
	}
*/
	public void updateCountValue(int value) {
		SwingUtilities.invokeLater(()-> {
			counterWords.setText("" + value);
		});
	}
	
	public void updateOccurrenciesLabel(List<Occurrence> occurrencies) {
		SwingUtilities.invokeLater(()-> {
			lblShowOccurrencies.setText("");
			occurrencies.forEach(elem -> lblShowOccurrencies.setText(lblShowOccurrencies.getText() + "[" + elem.getWord() + ": "+elem.getCount() + "] "));
		});
	}
	
	public void resetValuesGui() {
		SwingUtilities.invokeLater(()-> {
			counterWords.setText("");
			lblShowOccurrencies.setText("");
			lblDirectoryPDF.setText(DIR_CHOOSER_LBL);
			wordsNumberTextField.setText("");
			lblFileToIgnore.setText(TOIGNFILE_CHOOSER_LBL);
			lblErrorRequiredField.setVisible(false);
		});
	}

	public void display() {
		javax.swing.SwingUtilities.invokeLater(() -> {
			this.setVisible(true);
		});
	}
}
