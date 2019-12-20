package xzcode.ggserver.docs.core.protobuf;

public class ProtoFile {
	
	/**
	 * 文件名
	 */
	private String filename;
	
	/**
	 * 内容
	 */
	private String content;
	
	
	

	public ProtoFile() {
	}

	public ProtoFile(String filename, String content) {
		this.filename = filename;
		this.content = content;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	
	

}
