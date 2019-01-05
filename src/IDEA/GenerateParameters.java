package IDEA;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import Global.Util;
import Global.UtilParameters;

public class GenerateParameters {

	public String OrignalKey;
	public ArrayList<String> otherKeys;
	
	public String getOrignalKey() {
		return OrignalKey;
	}

	public void setOrignalKey(String OrignalKey) {
		this.OrignalKey = OrignalKey;
	}
	
	public ArrayList<String> getOthersKeys() {
		return this.otherKeys;
	}
	

	//Constructeur par défaut avec l'énumération de la taille
	public GenerateParameters(SizeKey size, String originalKey)
	{
		this.OrignalKey = originalKey;
		String copysOriginalKeys = OrignalKey;
				
		//Initialisation du tableau qui va contenir la liste des sous clés
		otherKeys = new ArrayList<String>();
		
		//Création des 52 sous clés par rapport à la clé principale
		for(int i = 0; i < 52; i++)
		{
			String copys = copysOriginalKeys.substring(0, 16);
			this.otherKeys.add(copys);
			//System.out.println("Keys copy : " + (i + 1) + " " + Arrays.toString(copys));
			
			copysOriginalKeys = copysOriginalKeys.substring(16);
			
			//Si il n'y a plus d'éléments on applique la permutation
			if(copysOriginalKeys.length() == 0)
			{
				copysOriginalKeys = Util.PermuteIndexLeft(this.OrignalKey, 25);
			}
		}
	}
}
