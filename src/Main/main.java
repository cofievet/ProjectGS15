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
				
		
		String message = "";
		for(int i = 0 ; i < size.GetSize(); i++)
		{	
			//Génréation du nombre aléatoire entre 0 et 1
			int random = Util.GenerateRandomBetweenMinAndMax(UtilParameters.minRandom,UtilParameters.maxRandom);
			message += random;
		}

		System.out.println("Message initial  : " + message);
		String crypt = idea.Cryptage(message, originalKey);

		System.out.println("Message crypté   : " + crypt);
		
		String decrypt = idea.Dechiffrement(crypt, originalKey);
		System.out.println("Message décrypté : " + decrypt);
	}
}
