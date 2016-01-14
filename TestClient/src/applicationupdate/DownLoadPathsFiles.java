package applicationupdate;



/***
 * The paths of directories and files used to download application!!
 * @author lubo
 *
 */
public class DownLoadPathsFiles {
	private String pathToJar;
	private String downloadDir = "";
	private String libDir =  "lib";
	
	public DownLoadPathsFiles(){
		
	}
	
	public DownLoadPathsFiles(String pathToJar, String downloadDir, String libDir){
		this.downloadDir = downloadDir;
		this.pathToJar = pathToJar;
		this.libDir =libDir;
	}
	
	public String getPathToJar() {
		return pathToJar;
	}
	public String getDownloadDir() {
		return downloadDir;
	}
	public String getLibDir() {
		return libDir;
	}
	
}
