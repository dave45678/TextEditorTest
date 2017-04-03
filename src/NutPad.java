// File   : editor/NutPad.java -- A very simple text editor
// Purpose: Illustrates use of AbstractActions for menus.
//          It only uses a few Action features.  Many more are available.
//          This program uses the obscure "read" and "write"
//               text component methods.
// Author : Fred Swartz - 2006-12-14 - Placed in public domain.

// Dave's version can be found at https://github.com/dave45678/TextEditor
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;

// NutPad
public class NutPad extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//... Components 
    private JTextArea    _editArea;
    private JFileChooser _fileChooser = new JFileChooser();
    
    //... Create actions for menu items, buttons, ...
    /*
     * An Action object is an action listener that provides 
     * not only action-event handling, but also centralized 
     * handling of the state of action-event-firing components 
     * such as tool bar buttons, menu items, common buttons, 
     * and text fields. The state that an action can handle includes 
     * text, icon, mnemonic, enabled, and selected status.
     */
    private Action _openAction = new OpenAction();
    private Action _saveAction = new SaveAction();
    private Action _exitAction = new ExitAction(); 
    private Action _spellCheckAction = new SpellCheckAction();
    
    //===================================================================== main
    public static void main(String[] args) {
        new NutPad();
    }
    
    //============================================================== constructor
    public NutPad() {
        //... Create scrollable text area.
        _editArea = new JTextArea(15, 80);
        _editArea.setBorder(BorderFactory.createEmptyBorder(2,2,2,2));
        _editArea.setFont(new Font("monospaced", Font.PLAIN, 14));
        JScrollPane scrollingText = new JScrollPane(_editArea);
        
        //-- Create a content pane, set layout, add component.
        JPanel content = new JPanel();
        content.setLayout(new BorderLayout());
        content.add(scrollingText, BorderLayout.CENTER);
        
        //... Create menubar
        //https://docs.oracle.com/javase/tutorial/uiswing/components/menu.html
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = menuBar.add(new JMenu("File"));

        fileMenu.setMnemonic('F');
        fileMenu.add(_openAction);       // Note use of actions, not text.
        fileMenu.add(_saveAction);
        fileMenu.addSeparator(); 
        fileMenu.add(_spellCheckAction);
        fileMenu.addSeparator(); 
        fileMenu.add(_exitAction);
        
        //... Set window content and menu.
        setContentPane(content);
        setJMenuBar(menuBar);
        
        //... Set other window characteristics.
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("NutPad");
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    // inner class OpenAction
    /* To create an Action object, you generally 
     * create a subclass of AbstractAction and then 
     * instantiate it. In your subclass, you must 
     * implement the actionPerformed method to 
     * react appropriately when the action event occurs. 
     * Here's an example of creating and 
     * instantiating an AbstractAction subclass.
     * When the action created by the preceding code is 
     * attached to a button and a menu item, the button and 
     * menu item display the text and icon associated with 
     * the action. A specified character is used for mnemonics on 
     * the button and menu item, and their tool-tip text is 
     * set to the SHORT_DESCRIPTION string followed by a 
     * representation of the mnemonic key.
     */
    class OpenAction extends AbstractAction {
        /* 
         * inner classes or nested classes enable you to 
         * logically group classes that are only used in 
         * one place, increase the use of 
         * encapsulation, and create more readable 
         * and maintainable code. 
         */
		private static final long serialVersionUID = 1L;

		//constructor
        public OpenAction() {
            super("Open...");
            putValue(MNEMONIC_KEY, new Integer('O'));
        }
        
        //actionPerformed
        public void actionPerformed(ActionEvent e) {
            int retval = _fileChooser.showOpenDialog(NutPad.this);
            if (retval == JFileChooser.APPROVE_OPTION) {
                File f = _fileChooser.getSelectedFile();
                try {
                    FileReader reader = new FileReader(f);
                    _editArea.read(reader, "");  // Use TextComponent read
                } catch (IOException ioex) {
                    System.out.println(e);
                    System.exit(1);
                }
            }
        }
    }
    
    // inner class SaveAction
    /* Using inner classes in the wrong situations can lead
     * to code that�s difficult to understand and maintain.
     * Use a nested class (or inner class) 
     * because it gives us access to an enclosing instance's 
     * private fields and methods.
     * 
     * 
     * Inner classes allow you to define one class inside another class
     * This is why they are called �inner� classes. 
     * A class can have member classes, just like how classes
     * can have member variables and methods.
     * 
     * In object oriented programming 
     * classes should be specialized because it allows 
     * for greater reuse and flexibility. 
     * This is because a class only needs code that allows 
     * an instance of that class (an object) to do its job and no more.
     * 
     * But, there are other situations when you actually need 
     * to write some code that seems like it belongs in its own class. 
     * At the same time, the code that you want to write is very intimately
     * tied to some other class�s code. This is when an inner class us appropriate.
     * The inner class can "see"  or access the outer class's member variables.
     */
    class SaveAction extends AbstractAction {
		private static final long serialVersionUID = 1L;

		// constructor
        SaveAction() {
            super("Save...");
            putValue(MNEMONIC_KEY, new Integer('S'));
        }
        
        //actionPerformed
        public void actionPerformed(ActionEvent e) {
            int retval = _fileChooser.showSaveDialog(NutPad.this);
            if (retval == JFileChooser.APPROVE_OPTION) {
                File f = _fileChooser.getSelectedFile();
                try {
                    FileWriter writer = new FileWriter(f);
                    _editArea.write(writer);  // Use TextComponent write
                } catch (IOException ioex) {
                    JOptionPane.showMessageDialog(NutPad.this, ioex);
                    System.exit(1);
                }
            }
        }
    }
    
    //inner class ExitAction
    /* Combining the event handling methods with the chat client 
     * specific methods in one big class sounds like a good idea at 
     * first, but there is one big problem: If both the event handling 
     * code and the chat client code need to inherit some code from 
     * different classes then you are in trouble, because 
     * Java does not support multiple inheritance � meaning that 
     * our one �big class� can not inherit from two different classes.
     * 
     * This means that we want some sort of solution where the event 
     * handling code is in it�s very own class � which would allow it 
     * to inherit from any class it needs.
     */
    class ExitAction extends AbstractAction {
    	/* 
    	 * serialVersionUID is used internally by the Java Virtual Machine
    	 * to uniquely identify this version of the class. If I change the 
    	 * class in the future I should update the serialVersionUID. My
    	 * application won't break if I'm not using serialization which is 
    	 * saving a class and its data to be used in a future instance
    	 * of the program (like saving a word document to be read later).
    	 */
		private static final long serialVersionUID = 1L;

		//============================================= constructor
        public ExitAction() {
            super("Exit");
            putValue(MNEMONIC_KEY, new Integer('X'));
        }
        
        //========================================= actionPerformed
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }
    
    //inner class SpellCheckAction
    class SpellCheckAction extends AbstractAction {
    	
    	/*
    	 * to-do
    	 * implement spell check
    	 * keep list of misspelled words
    	 * add to dictionary
    	 * count words
    	 * count letters
    	 * count length
    	 * search and replace text
    	 * use stringbuilder instead of string
    	 * spell checker should be external class
    	 * sentence case and toupper case
    	 * change font size
    	 */
		private static final long serialVersionUID = 1L;

		public SpellCheckAction(){
			super("Check Spelling");
    		putValue(MNEMONIC_KEY, new Integer('C'));
    	}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// loop through the text in the textbox and 
			// compare each word with the dictionary
			//put the text into an array of words
			String[] words = _editArea.getText().split("\\s+");
			String output = "";
			LinuxWords checker = new LinuxWords();
			
			//loop through the words array and check the spelling of each word
			for (String word:words){
				JOptionPane.showMessageDialog(NutPad.this,word);
				if (checker.checkSpelling(word)){
					output += word + " ";
				}else{
					output += " *" + word + "* ";
				}
				
			}
			
			
			//JOptionPane.showMessageDialog(NutPad.this,textToCheck);
			_editArea.setText(output);
			
		}
    	
    }
}