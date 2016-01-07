package net.is_bg.ltf;

/**
 * The message that is set when lock has been locked!!!
 * @author lubo
 *
 */
public class Message {

	String value;

	public Message(String value){
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
