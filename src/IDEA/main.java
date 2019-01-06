package IDEA;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Scanner;

import Signature.Signature;

public class main {
	
	//TODO : Implémenter une liste de tests unitaires pour les fonctions de base
	public static void main (String[] args) throws FileNotFoundException, UnsupportedEncodingException{
		
		Scanner sc = new Scanner(System.in);
		System.out.println("Veuillez choisir une taille de clé : (96, 128, 160, 256)");
		String str = sc.nextLine();
		if(!str.equals("96") && !str.equals("128") && !str.equals("160") && !str.equals("256"))
		{
			System.out.println("FIN ! ");
			return;
		}
		
		System.out.println("Veuillez choisir la taille du message : ");
		int value = sc.nextInt();

		SizeKey size = SizeKey.size128;
		if(str.equals("96"))
			size = SizeKey.size96;
		else if(str.equals("128"))
			size = SizeKey.size128;
		else if(str.equals("160"))
			size = SizeKey.size160;
		else if(str.equals("256"))
			size = SizeKey.size256;
		
		String originalKey = "";
		//Génération de la clé en fonction de la taille passée en paramètre
		for(int i = 0 ; i < size.GetSize(); i++)
		{	
			//Génréation du nombre aléatoire entre 0 et 1
			originalKey += Util.GenerateRandomBetweenMinAndMax(UtilParameters.minRandom,UtilParameters.maxRandom) + "";
		}
		
		//Génération d'un message en fonction de la taille choisie
		String message = "";
		for(int i = 0 ; i < value; i++)
		{	
			//Génréation du nombre aléatoire entre 0 et 1
			int random = Util.GenerateRandomBetweenMinAndMax(UtilParameters.minRandom,UtilParameters.maxRandom);
			message += random;
			//message += (i % 10) + "";
		}

		System.out.println("Message originale : " + message);
		String crypt = idea.Cryptage(message, originalKey, ModeEnum.modePCBC);

		System.out.println("Message crypté   : " + crypt);
		
		String decrypt = idea.Dechiffrement(crypt, originalKey, ModeEnum.modePCBC);
		System.out.println("Message décrypté : " + decrypt);
		
		
		
	}
}
