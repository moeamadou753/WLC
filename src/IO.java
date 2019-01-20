import java.io.*;

/**
 *	The static methods in this class allow text  
 *	to be written to OR read from a file.
 *
 *	@author  Mohamed Amadou
 *	@since	 Monday, January 4
 */
public class IO
{
	
	private static PrintWriter fileOut;
	private static ObjectOutputStream output;
	
	/**
	 * Creates a new file (fileName) in the current
	 * folder and places a reference to it in fileOut
	 * @param fileName Represents the name of the file
	 */	
	public static void createOutputFile(String fileName)
	{
		createOutputFile(fileName, false);
	}
	
	/**
	 * Creates a new file (fileName) in the current
	 * folder and places a reference to it in fileOut
	 * @param fileName Represents the name of the file
	 * @param append   True if you want to add to the existing information,
	 * 				   false if you want to re-write the entire file
	 */	
	public static void createOutputFile(String fileName, boolean append)
	{
		try
		{
			fileOut = new PrintWriter(new BufferedWriter(new FileWriter(fileName, append)));
		}
		catch(IOException e)
		{
			System.out.println("*** Cannot create file: " + fileName + " ***");
		}
	}
	
	public static void writeObject(String fileName, Object o){
		try {
			output = new ObjectOutputStream(new FileOutputStream("data/" + fileName));
			output.writeObject(o);
			output.close();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Text is added to the current file
	 * @param text The characters that will be added to the file
	 */
	public static void print(String text)
	{
		fileOut.print(text);
	}


	/**
	 * Text is added to the current file and a new line
	 * is inserted at the end of the characters
	 * @param text The characters that will be added to the file
	 */
	public static void println(String text)
	{
		fileOut.println(text);
	}

	
	/**
	 * Close the file that is currently being written to
	 * NOTE: This method MUST be called when you are finished
	 *		 writing to a file in order to have your changes saved
	 */
	public static void closeOutputFile()
	{
		fileOut.close();
	}
	
	
	
	
	
	private static BufferedReader fileIn;
	private static ObjectInputStream input;
	
	public static Object readObject(String fileName) throws Exception, IOException{
		try {
			 input = new ObjectInputStream(new FileInputStream("data/" + fileName));
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		return input.readObject();
	}
	
	public static void closeObjectReader(){
		try {
			input.close();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
	
	/**
	 * Opens a file called fileName (that must be
	 * stored in the current folder) and places a
	 * reference to it in fileIn
	 * @param fileName The name of a file that already exists
	 */
	public static void openInputFile(String fileName)
	{
		try
		{
			fileIn = new BufferedReader(new FileReader(fileName));
		}
		catch(FileNotFoundException e)
		{
			System.out.println("***Cannot open " + fileName + "***");
		}
	}
	
	
	/**
	 * Read the next line from the file and return it
	 */
	public static String readLine()
	{
		try
		{
			return fileIn.readLine();
		}
		catch(IOException e){}
		
		return null;
	}
	
	
	/**
	 * Close the file that is currently being read from
	 */
	public static void closeInputFile()
	{
		try
		{
			fileIn.close();
		}
		catch(IOException e){}
	}
	
} // end class