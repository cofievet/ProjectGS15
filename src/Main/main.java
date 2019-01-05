package Main;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import Global.Util;
import Global.UtilParameters;
import IDEA.GenerateParameters;
import IDEA.SizeKey;
import IDEA.idea;
import Signature.Signature;

public class main {
	
	//TODO : Implémenter une liste de tests unitaires pour les fonctions de base
	public static void main (String[] args){
		
		SizeKey size = SizeKey.size128;
		String originalKey = "";
		//Génération de la clé en fonction de la taille passée en paramètre
		for(int i = 0 ; i < size.GetSize(); i++)
		{	
			//Génréation du nombre aléatoire entre 0 et 1
			originalKey += Util.GenerateRandomBetweenMinAndMax(UtilParameters.minRandom,UtilParameters.maxRandom) + "";
		}
		
		originalKey = "006400c8012c019001f4025802bc0320";				
		
		String message = "";
		for(int i = 0 ; i < size.GetSize(); i++)
		{	
			//Génréation du nombre aléatoire entre 0 et 1
			int random = Util.GenerateRandomBetweenMinAndMax(UtilParameters.minRandom,UtilParameters.maxRandom);
			message += random;
		}
		
		originalKey = new BigInteger(originalKey, 16).toString(2);
		
		if(originalKey.length() < 128)
		{
			StringBuilder key = new StringBuilder(originalKey);
			for(int i = originalKey.length(); i < 128; i++)
			{
				key.insert(0,"0");
			}
			originalKey = key.toString();
		}
				
		//Message de base
		message = "05320a6414c819fa";
		//Conversion en binaire
		message = new BigInteger(message, 16).toString(2);
		
		//On ajoute les éléments à la taille
		if(message.length() < 128)
		{
			System.out.println(message.length());
			StringBuilder key = new StringBuilder(message);
			for(int i = message.length(); i < 128; i++)
			{
				key.insert(0,"0");
			}
			message = key.toString();
		}
		
		
		System.out.println("Message initial : " + message);
		//System.out.println("Message initial : 00000000000000000000000000000000000000000000000000000000000000000000010100110010000010100110010000010100110010000001101000000000");
		String crypt = idea.Cryptage(message, originalKey);

		//System.out.println("Message crypté   : " + crypt);
		
		String decrypt = idea.Dechiffrement(crypt, originalKey);
		//System.out.println("Message décrypté : " + decrypt);
	}
}
