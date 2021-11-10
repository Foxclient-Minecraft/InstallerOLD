package foxclient.installer;

public class FeedbackHandler {
	
	//Sera llamado desde el javascript
	public void close() {
		Installer.getInstance().stopTheApplication();
	}

}
