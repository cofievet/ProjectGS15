package IDEA;

public enum SizeKey {
	size30(30),	
	size96(96),	
	size128(128),
	size160(160),
	size256(256);
	
	private int size;
	
	//Constructeur pour les énumérations
	private SizeKey(int size) {  
        this.size = size ;  
	} 
	
	public int GetSize()
	{
		return this.size;
	}
	
}
