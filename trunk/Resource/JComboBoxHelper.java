package Resource;

// source : http://tech.chitgoks.com/2009/10/05/java-use-keyvalue-pair-for-jcombobox-like-htmls-select-tag/

public class JComboBoxHelper {
	String key, value;
 
	public JComboBoxHelper(String key, String value) {
		this.key = key;
		this.value = value;
	}
 
	public String getValue() { return value; }
	public String getKey() { return key; }
 
	@Override
	public String toString() { return key; }
}
