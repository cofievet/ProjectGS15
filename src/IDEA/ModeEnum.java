package IDEA;

public enum ModeEnum {
	modeCBC("CBC"),	
	modeECB("ECB"),
	modePCBC("PCBC");
	
	
	private String mode;
	
	//Constructeur pour les énumérations
	private ModeEnum(String mode) {  
        this.mode = mode ;  
	} 
	
	public String GetMode()
	{
		return this.mode;
	}
	
}
