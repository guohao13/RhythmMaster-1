package status;

import java.awt.Color;

public class Message {
	
	public String content;
	public Color color;
	
	Message() {
		content = "";
		color = Color.black;
	}
	
	Message(String s, Color c) {
		content = s;
		color = c;
	}
}
