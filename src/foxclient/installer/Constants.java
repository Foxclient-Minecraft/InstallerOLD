package foxclient.installer;

import java.io.*;
import java.net.*;

public class Constants {
	
	public static final String MC_LAUNCHER_ICON = "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAACAAAAAgCAYAAABzenr0AAACEklEQVRYhe2XP2hTURSHv5fEJC+x8bXBWNrQglgfaSkqUUgHo0axaLPYQCpUXKQQBQdFVKSDSEV0cnJwERTcRBCrQ6pobUmLKNJF0RYC/kliLBoxWmLJFd3UIe8mxQy+A2e53Ps7H/ecczlXAQR1NEs9g/80E8AEMAGqArAoypJdXlUqQrExcjyB27GsTgBlLwf8OWKdzpoBbDKbfd4mjvb5CfkUstM3mM/WHF8GoIXxY+3on1KgAhsgOgujmdoADKUg2NVB4WI7+soUaH5YdEEWEjE4p/8DgMWFIm/zZZ7mD3KlcYTU4BzYB+AlnNwJ+q+uqN6EcbeLwcRhMZb7Ir7NzAixByGGEYdUGY3fXaoII5TYu6mD7T43PJuCjzD7BMoBL/73KnbXcsqlBdLptGFNqYHkeRTeKR4urx1ma/Yu0IDVppFTm+iMxenf0cNnYPTOPfbFY5SLhYqaVuC0UYDAeo2BcIFdt5O0tbYRaSkR1JIEXzwiNXGfja8e4mhsoHtzL2ohx9jkVEVNqYfoSNLB99Y47qHV+K3jUHwAyldcvQE8znnC52/ChT44G6a5mDekKT0T+rq3EO+PkJ28xpvXGdKWNTibA+ze1sOqLp1Tjy9hm7hFaG4F05nKKaDa6gXlr7XrZ04IcXW/ECEprepb6E9f51WFGNJEVKItl3QstwMe4IPEGfNfYAKYAP85APADl/7pEd54FGYAAAAASUVORK5CYII=";

	private static final String BASE_URL = "https://FoxClientVersionBasic.danipro2007.repl.co/";
	
	private static String versionNumber = null;
	
	public static String getVersionNumber() {
		
		if(versionNumber == null) {
			
			try {
				InputStream stream = FileHelper.getStreamFromUrl(BASE_URL + "version.txt");
				InputStreamReader reader = new InputStreamReader(stream);
				BufferedReader buffReader = new BufferedReader(reader);
				versionNumber  = buffReader.readLine();
				buffReader.close();
				reader.close();
				stream.close();
			}
			catch(IOException e) {
				e.printStackTrace();
				Installer.getInstance().die(e);
			}
		}
		
		return versionNumber;
	}
	
	public static InputStream getIcon() {
		 return ClassLoader.getSystemResourceAsStream("assets/icon.png");
	}
	
	public static InputStream getJar() throws IOException {
		return FileHelper.getStreamFromUrl(BASE_URL + getVersionNumber() + "/" + "Foxclient.jar");
	}
	
	public static InputStream getJSON() throws IOException {
		return FileHelper.getStreamFromUrl(BASE_URL + getVersionNumber() + "/" + "Foxclient.json");
	}
	
	public static URL getAudio() {
		 return ClassLoader.getSystemResource("assets/audio.mp3");
	}
	
}
