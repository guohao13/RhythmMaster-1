package status;

import java.awt.Color;

public class Message {
	
	public String content;
	public Color color;
	
	Message() {
		this.content = "";
		this.color = Color.black;
	}
	
	Message(String s, Color c) {
		this.content = s;
		this.color = c;
	}
}
