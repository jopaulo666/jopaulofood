package br.com.jopaulofood.util;

public enum FileType {

	PNG("image/png", "png"),
	JPG("image/jpeg", "jpg");
	
	String mimeType;
	String extension;
	
	private FileType(String mimeType, String extension) {
		this.mimeType = mimeType;
		this.extension = extension;
	}
	
	public String getMimeType() {
		return mimeType;
	}
	
	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}
	
	public String getExtension() {
		return extension;
	}
	
	public void setExtension(String extension) {
		this.extension = extension;
	}
	
	private boolean sameOf(String mimeType) {
		return this.mimeType.equalsIgnoreCase(mimeType);
	}
	
	public static FileType of(String mimeType) {
		for (FileType fileType : values()) {
			if (fileType.sameOf(mimeType)) {
				return fileType;
			}
		}
		return null;
	}
}
