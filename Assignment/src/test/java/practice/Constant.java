package practice;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Constant {

	public final static List<String> PROGRAMTYPEMASTERLIST = new LinkedList<String>(
			Arrays.asList("EPISODE", "MOVIE", "SERIES", "SEASON"));

	public static final String INVALID_USER_STATUSCODE = "403";
	public static final String INVALID_USER_CODE = "8001";
	public static final String INVALID_USER_MESSAGE = "invalid api key";
}
