package IDEA;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import Global.Util;
import Global.UtilParameters;

public class GenerateParameters {

	public String OrignalKey;
	public ArrayList<String> otherKeys;
	public ArrayList<String> decryptKeys;
	
	public String getOrignalKey() {
		return OrignalKey;
	}



	public void setOrignalKey(String orignalKey) {
		OrignalKey = orignalKey;
	}



	public ArrayList<String> getOtherKeys() {
		return otherKeys;
	}



	public void setOtherKeys(ArrayList<String> otherKeys) {
		this.otherKeys = otherKeys;
	}



	public ArrayList<String> getDecryptKeys() {
		return decryptKeys;
	}



	public void setDecryptKeys(ArrayList<String> decryptKeys) {
		this.decryptKeys = decryptKeys;
	}



	
	

	//Constructeur par défaut avec l'énumération de la taille
	public GenerateParameters(SizeKey size, String originalKey)
	{
		this.OrignalKey = originalKey;
		String copysOriginalKeys = OrignalKey;
		
		String decalageOriginalKeys = OrignalKey;
		
		//Initialisation du tableau qui va contenir la liste des sous clés
		otherKeys = new ArrayList<String>();
		
		//Création des x sous clés par rapport à la clé principale
		for(int i = 0; i < UtilParameters.nbKeys; i++)
		{
			String copys = copysOriginalKeys.substring(0, UtilParameters.sizeKey);
			this.otherKeys.add(copys);
			//System.out.println("Keys copy : " + (i + 1) + " " + Arrays.toString(copys));

			
			copysOriginalKeys = copysOriginalKeys.substring(UtilParameters.sizeKey);
			
			//Si il n'y a plus d'éléments on applique la permutation
			if(copysOriginalKeys.length() == 0)
			{
				decalageOriginalKeys = Util.PermuteIndexLeft(decalageOriginalKeys, UtilParameters.decalageLeft);
				copysOriginalKeys = decalageOriginalKeys;
			}
		}
	}
}
