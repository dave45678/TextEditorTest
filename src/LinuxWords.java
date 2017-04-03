import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class LinuxWords {
	//a private memeber variable is used only by the enclosing class
	private ArrayList<String> words = new ArrayList<String>();
	//filename must be set for the class to work
	//use the getter/setter below since this is a private member
	//variable and can't be changed outside of the class.
	private String filename;
	private FileReader filereader;
	public LinuxWords(){
		//the constructor of a class is a method with the same name
		//as the class. This takes place of the main method we've been
		//using. Where main() is the starting point for your application, 
		//the constructor is the starting point for your class.
		//We start by reading the wordlist file into arraylist 
		if (filename == null || filename.isEmpty()){
			this.setFilename("linuxwords.txt");
		}
		filename = (System.getProperty("user.dir") + File.separatorChar + this.getFilename());

	}
	
	//our only public method is to check the word
	public boolean checkSpelling(String word){
		boolean result = false;
		//search the Arraylist to see if our word exists
		//Note: We're using an ArrayList here ONLY because 
		//it is what we have just learned about. There are
		//more efficient ways to do this... especially with a 
		//long list. It's called a HashMap. We'll make that improvement
		//later. For now I want to illustrate the concept with what 
		//we have learned.
	
		if (words.contains(word.toLowerCase())){
			result = true;
		}else{
			result = false;
		}
		
		return result;
		
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
		//now that we know the filename, populate the word list:
		this.loadWordList();
	}
	
	/* private method is used only by the class. It makes no sense
	 * to call this method until the filename is set. So you don't want
	 * to call it in the constructor. Instead, call it after filename
	 * is set.. in the setFilename method.
	 */
	private void loadWordList() {
		//the error handling of this method is required for file I/O operations
		// but it makes the method look scary!
		//take it line by line until it makes sense....
		try {
			filereader = new FileReader(new File(filename));
			BufferedReader reader = new BufferedReader(filereader);

			String line = "";
			line = reader.readLine();
			while (line != null) {
				words.add(line);
				line = reader.readLine();
			}
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}