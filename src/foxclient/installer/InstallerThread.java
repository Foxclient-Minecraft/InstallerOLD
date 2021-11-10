package foxclient.installer;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class InstallerThread extends Thread{
	
	private final Installer installer;
	
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US);
	
	public InstallerThread(Installer installer) {
		this.installer = installer;
		sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
		
		final File mcDir = new File(OSHelper.getOS().getMc());
		if(!mcDir.exists()) {
			mcDir.mkdirs();
		}
		
	}
	
	@Override
	public void run() {

		final String mc = OSHelper.getOS().getMc();
		
		try {
			
			File userDir = new File(mc, "Foxclient/");
			FileHelper.deleteDirectory(userDir);
			
			File verDir = new File(mc, "versions/Foxclient/");
			FileHelper.deleteDirectory(verDir);
			verDir.mkdirs();
			
			final String installDate = sdf.format(new Date());
			
			final JsonObject newProfile = new JsonObject();
			newProfile.addProperty("name", "Fox Client v" + Constants.getVersionNumber());
			newProfile.addProperty("type", "custom");
			newProfile.addProperty("created", installDate);
			newProfile.addProperty("lastUsed", installDate);
			newProfile.addProperty("icon", Constants.MC_LAUNCHER_ICON);
			newProfile.addProperty("lastVersionId", "Foxclient");
			
			final File launcherProfileFile = new File(mc, "/launcher_profiles.json");
			JsonObject launcherProfile = new JsonObject();
			if(launcherProfileFile.exists()) {
				launcherProfile = new JsonParser().parse(FileHelper.readFile(launcherProfileFile)).getAsJsonObject();
			} else {
				launcherProfile.add("profiles", new JsonObject());
			}
			
			launcherProfile.get("profiles").getAsJsonObject().add("Foxclient", newProfile);
			launcherProfile.addProperty("selectedProfile", "Foxclient");
			
			String jsonToWrite = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create().toJson(launcherProfile);
			
			FileHelper.writeFile(jsonToWrite, launcherProfileFile);
			
			FileHelper.writeFile(Constants.getJSON(), new File(verDir + "/Foxclient.json"));
			installer.moveForward();
			
			FileHelper.writeFile(Constants.getJar(), new File(verDir + "/Foxclient.jar"));
			installer.moveForward();
		}
		catch(Exception e) {
			System.err.println("Ha ocurrido un error instalando el cliente. Cerrando instalador");
			installer.die(e);
		}
		
	}
}
