import java.util.Collections;

public class TestSpellChecker {

	public static void main(String[] args) {
		LinuxWords lw;
		lw = new LinuxWords();
		boolean result = lw.checkSpelling("Linux");
		System.out.println(result);
		
		System.out.println(lw.checkSpelling("Sunshine"));
		System.out.println(lw.checkSpelling("lollipops"));
		System.out.println(lw.checkSpelling("kitten"));
		System.out.println(lw.checkSpelling("unitcorns"));
		System.out.println(lw.checkSpelling("rainbowz"));

		
		
		System.out.print(String.join("", Collections.nCopies(2, "\t")));
		System.out.println("Hello");
	}

}