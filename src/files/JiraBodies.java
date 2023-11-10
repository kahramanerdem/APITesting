package files;

public class JiraBodies {

	public static String createIssueBody() {
		return "{\n"
				+ "    \"fields\":{\n"
				+ "        \"project\":\n"
				+ "        {\n"
				+ "            \"key\":\"REST\"\n"
				+ "        },\n"
				+ "        \"summary\": \"Debit card Defect\",\n"
				+ "        \"description\":\"Creating my second bug\",\n"
				+ "        \"issuetype\":\n"
				+ "        {\n"
				+ "            \"name\": \"Bug\"\n"
				+ "        }\n"
				+ "    }\n"
				+ "}";
	}
}
