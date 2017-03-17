package model;

import java.util.EventObject;

public class SendFileEvent extends EventObject {

	private static final long serialVersionUID = 2561243231333905476L;
	private String filePath;

	public SendFileEvent(Object source) {
		super(source);
	}

	public SendFileEvent(Object source, String filePath) {
		super(source);
		this.filePath = filePath;
	}

	public String getFilePath() {
		return filePath;
	}

}
