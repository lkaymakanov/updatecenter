package net.is_bg.updatercenter.common;

public class FileData {

	byte [] bytes;
	
	public FileData(){
		this.bytes = null;
	}
	
	public FileData(byte [] bytes)
	{
		this.bytes = bytes;
	}

	public byte[] getBytes() {
		return bytes;
	}
}
