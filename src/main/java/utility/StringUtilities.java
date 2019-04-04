package utility;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @author ReverendDread
 * Jun 22, 2018
 */
public class StringUtilities {

	/**
	 * Formats an int array for viewing
	 * @param array the int array
	 * @return the formatted array as a string.
	 */
	public static final String formatIntArrayToString(int[] array) {
		String formatted = "";
		if (array != null) {
			for (int i = 0; i < array.length; i++) {
				formatted = formatted + array[i] + ";";
			}
		}
		return formatted;
	}
	
	
	/**
	 * Formats string array for viewing.
	 * @param options
	 *            the options to format.
	 * @return the formated array as a string.
	 */
	public static final String formatStringArrayToString(String[] options) {
		String formatted = "";
		for (int index = 0; index < options.length; index++) {
			String option = options[index];
			formatted = formatted + (option == null ? "null" : option) + ";";
		}
		return formatted;
	}
	
	/**
	 * Formats a set of arrays for display.
	 * @param original
	 * 			the original array
	 * @param modified
	 * 			the modified array.
	 * @return the formatted array as a string.
	 */
	public static final String formatMultiArrayInt(int[] original, int[] modified) {
		String formatted = "";
		if (original != null && modified != null) {
			for (int i = 0; i < original.length; i++) {
				formatted = formatted + original[i] + "=" + modified[i] + ";";
			}
		}
		return formatted.isEmpty() ? "null" : formatted;
	}
	
	/**
	 * Formats a set of arrays for display.
	 * @param original
	 * 			the original array
	 * @param modified
	 * 			the modified array.
	 * @return the formatted array as a string.
	 */
	public static final String formatMultiArrayShort(short[] original, short[] modified) {
		String formatted = "";
		if (original != null && modified != null) {
			for (int i = 0; i < original.length; i++) {
				formatted = formatted + original[i] + "=" + modified[i] + ";";
			}
		}
		return formatted.isEmpty() ? "null" : formatted;
	}
	
	/**
	 * Creates a readable stacktrace error message.
	 * @param throwable
	 * @return
	 */
	public static String getStackTrace(final Throwable throwable) {
	     final StringWriter sw = new StringWriter();
	     final PrintWriter pw = new PrintWriter(sw, true);
	     throwable.printStackTrace(pw);
	     return sw.getBuffer().toString();
	}
	
}
