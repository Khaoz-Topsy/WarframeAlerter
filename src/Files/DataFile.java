package Files;

import java.util.StringTokenizer;

public class DataFile 
{
	private String Email;
	private boolean Mod = false;
	private boolean Key = false;
	private boolean Aura = false;
	private boolean Vauban = false;
	private boolean Resource = false;
	private boolean Blueprint = false;
	
	public DataFile() {}
	
	public DataFile(String Email, boolean Mod, boolean Key, boolean Aura, boolean Vauban, boolean Resource, boolean Blueprint)
	{
		this.Email = Email;
		this.Mod = Mod;
		this.Key = Key;
		this.Aura = Aura;
		this.Vauban = Vauban;
		this.Resource = Resource;
		this.Blueprint = Blueprint;
	}

	public String getEmail() {
		return Email;
		}
	
	public void setEmail(String Email) {
		this.Email = Email;
		}

	public boolean getMod() {
		return Mod;
	}

	public void setMod(boolean mod) {
		Mod = mod;
	}

	public boolean getKey() {
		return Key;
	}

	public void setKey(boolean key) {
		Key = key;
	}

	public boolean getAura() {
		return Aura;
	}

	public void setAura(boolean aura) {
		Aura = aura;
	}

	public boolean getVauban() {
		return Vauban;
	}

	public void setVauban(boolean vauban) {
		Vauban = vauban;
	}

	public boolean getResource() {
		return Resource;
	}

	public void setResource(boolean resource) {
		Resource = resource;
	}

	public boolean getBlueprint() {
		return Blueprint;
	}

	public void setBlueprint(boolean blueprint) {
		Blueprint = blueprint;
	}
	
	public String toString()
	{
		return "Email: " + Email + ", Mod: " + Mod + ", Key: " + Key + ", Aura: " + Aura + ", Vauban: " + Vauban + ", Resource: " + Resource + ", Blueprint: " + Blueprint;
	}
}
