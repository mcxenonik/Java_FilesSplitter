import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JTextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;

public class MainWindow extends JFrame implements ActionListener {  
 	private JButton selectSourceDirectoryButton;
    private JButton selectDestDirectoryButton;
    private JButton runButton;
    private JFileChooser fileChooser;
    private JTextField sourcePathTextField;
    private JTextField destPathTextField;

    MainWindow() throws IOException {  
        // super();  
        
        fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        sourcePathTextField = new JTextField();
        sourcePathTextField.setEditable(false);
        this.add(sourcePathTextField);

        destPathTextField = new JTextField();
        destPathTextField.setEditable(false);
        this.add(destPathTextField);

        selectSourceDirectoryButton = new JButton("Select source directory");
        selectSourceDirectoryButton.addActionListener(this);
        this.add(selectSourceDirectoryButton);

        selectDestDirectoryButton = new JButton("Select destination directory");
        selectDestDirectoryButton.addActionListener(this);
        this.add(selectDestDirectoryButton);

        runButton = new JButton("RUN");
        runButton.setEnabled(false);
        runButton.addActionListener(this);
        this.add(runButton);

        setTitle("Files Splitter");
        setSize(300, 200);
        setLayout(new GridLayout(5, 1));  
        setVisible(true); 
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
 
 		if(source == selectSourceDirectoryButton) {
            this.selectSourceDirectoryButtonClick();
        }
        else if(source == selectDestDirectoryButton) {
            this.selectDestDirectoryButtonClick();
        }
        else if(source == runButton) {
            this.runButtonClick();
        }
    }  

    void selectSourceDirectoryButtonClick() {
        int returnVal = fileChooser.showOpenDialog(this);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            String sourcepath = fileChooser.getSelectedFile().getPath();
            this.sourcePathTextField.setText(sourcepath);
            this.checkIfOK();
            System.out.println(sourcepath);
        } 
        else {
            System.out.println("Error");
        }
    }

    void selectDestDirectoryButtonClick() {
        int returnVal = fileChooser.showOpenDialog(this);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            String destpath = fileChooser.getSelectedFile().getPath();
            this.destPathTextField.setText(destpath);
            this.checkIfOK();
            System.out.println(destpath);
        } 
        else {
            System.out.println("Error");
        }
    }

    void runButtonClick() {
        System.out.println("RUNNING");
        String sourcepath = this.sourcePathTextField.getText();
        String destpath = this.destPathTextField.getText();
        try {
            new FilesOperator().walk(sourcepath, destpath);
        } 
        catch (IOException e1) {
            e1.printStackTrace();
        }
        System.out.println("DONE");
    }

    void checkIfOK() {
        boolean isSource = !this.sourcePathTextField.getText().equals("");
        boolean isDest = !this.destPathTextField.getText().equals("");
        if(isSource && isDest) {
            this.runButton.setEnabled(true);
        }
        else {
            this.runButton.setEnabled(false);
        }
    }
}