package IDEA;

public enum ModeEnum {
	modeCBC("CBC"),	
	modeECB("ECB"),
	modePCPC("PCPC");
	
	
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
