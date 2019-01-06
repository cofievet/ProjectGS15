package IDEA;

import java.math.BigInteger;
import java.util.Arrays;

import Global.Util;
import Global.UtilParameters;

public class idea {
	
	public static GenerateParameters parameters;
	
	//Fonction de cryptage d'un messga prit en paramètre
	public static String Cryptage(String message, String key)
	{
		SizeKey size = SizeKey.size128;
		
		//Génération des clés de chiffrement
		parameters = new GenerateParameters(size, key);
		
		int sizeBloc = message.length() / 4;
		
		//On récupère les premiers blocs
		String b1 = message.substring(0, sizeBloc);
		String b2 = message.substring(sizeBloc, sizeBloc *  2);
		String b3 = message.substring(sizeBloc * 2, sizeBloc * 3); 
		String b4 = message.substring(sizeBloc * 3, sizeBloc * 4);
		
		
		
		//Les 8 itérations
		for(int i = 0; i < UtilParameters.nbRotations; i++)
		{	
			/*System.out.println("Clé : " + i + " pour B1 : " + parameters.otherKeys.get(i * 6));
			System.out.println("Clé : " + i + " pour B2 : " + parameters.otherKeys.get(i * 6 + 1));
			System.out.println("Clé : " + i + " pour B3 : " + parameters.otherKeys.get(i * 6 + 2));
			System.out.println("Clé : " + i + " pour B4 : " + parameters.otherKeys.get(i * 6  +3));*/
			
			//Liste des opérations pour le chiffrement IDEA
			b1 = Util.Multiplication(parameters.otherKeys.get(i * 6), b1);
			b2 = Util.Addition(parameters.otherKeys.get(i * 6 + 1), b2);
			b3 = Util.Addition(parameters.otherKeys.get(i * 6 + 2), b3);
			b4 = Util.Multiplication(parameters.otherKeys.get(i * 6 + 3), b4);
			
			
			
			/*System.out.println("Rotation : " + i + " pour B1 : " + b1);
			System.out.println("Rotation : " + i + " pour B2 : " + b2);
			System.out.println("Rotation : " + i + " pour B3 : " + b3);
			System.out.println("Rotation : " + i + " pour B4 : " + b4);*/
			
			String t1 = Util.Xor(b1, b3);
			String t2 = Util.Xor(b2, b4);
			
			
			t1 = Util.Multiplication(parameters.otherKeys.get(i * 6 + 4), t1);
			t2 = Util.Addition(t2, t1);
			
			t2 = Util.Multiplication(parameters.otherKeys.get(i * 6 + 5), t2);
			t1 = Util.Addition(t1, t2);
									
			b1 = Util.Xor(b1, t2);
			b3 = Util.Xor(b3, t2);
			b2 = Util.Xor(b2, t1);
			b4 = Util.Xor(b4, t1);
			
			//Permutation des blocs
			/*String temp = b3;
			b3 = b2;
			b2 = temp;*/
			
		}
		
		//Dernière itération
		//Permutation des blocs
		/*String temp = b3;
		b3 = b2;
		b2 = temp;*/
		
		//Multiplication des éléments B1 et K49
		b1 = Util.Multiplication(parameters.otherKeys.get(UtilParameters.nbRotations * 6), b1);
		b2 = Util.Addition(parameters.otherKeys.get(UtilParameters.nbRotations * 6 + 1), b2);
		b3 = Util.Addition(parameters.otherKeys.get(UtilParameters.nbRotations * 6 + 2), b3);
		//Multiplication des éléments B1 et K52
		b4 = Util.Multiplication(parameters.otherKeys.get(UtilParameters.nbRotations * 6 + 3), b4);
		
		return b1 + b2 + b3 + b4;
		
	}
	
	//Déchiffrement d'un message prit en paramètre
	//La clé pour le chiffrement doit etre réuitliser
	public static String Dechiffrement(String message, String key)
	{
		SizeKey size = SizeKey.size128;
		
		//Génération des clés de chiffrement
		//parameters = new GenerateParameters(size, key);
		
		int sizeBloc = message.length() / 4;
		
		//On récupère les premiers blocs
		String b1 = message.substring(0, sizeBloc);
		String b2 = message.substring(sizeBloc, sizeBloc *  2);
		String b3 = message.substring(sizeBloc * 2, sizeBloc * 3); 
		String b4 = message.substring(sizeBloc * 3, sizeBloc * 4);
		
		
		
		//Les 8 itérations
		for(int i = 0; i < UtilParameters.nbRotations; i++)
		{	
			String kd1 = Util.CalculInverse(parameters.otherKeys.get((UtilParameters.nbRotations * 6) - (i * 6)), UtilParameters.moduloMultiplication);
			String kd2 = Util.CalculSomme(parameters.otherKeys.get((UtilParameters.nbRotations * 6 + 1) - (i * 6)));
			String kd3 = Util.CalculSomme(parameters.otherKeys.get((UtilParameters.nbRotations * 6 + 2) - (i * 6)));
			String kd4 = Util.CalculInverse(parameters.otherKeys.get((UtilParameters.nbRotations * 6 + 3) - (i * 6)), UtilParameters.moduloMultiplication);
			
			//Liste des opérations pour le chiffrement IDEA
			b1 = Util.Multiplication(kd1, b1);
			b2 = Util.Addition(kd2, b2);
			b3 = Util.Addition(kd3, b3);
			b4 = Util.Multiplication(kd4, b4);
			
			String t1 = Util.Xor(b1, b3);
			String t2 = Util.Xor(b2, b4);
			
			String kd5 = parameters.otherKeys.get((UtilParameters.nbRotations * 6 - 2) - (i * 6));
			String kd6 = parameters.otherKeys.get((UtilParameters.nbRotations * 6 - 1) - (i * 6));
			
			t1 = Util.Multiplication(kd5, t1);
			t2 = Util.Addition(t2, t1);
						
			t2 = Util.Multiplication(kd6, t2);
			t1 = Util.Addition(t1, t2);
												
			b1 = Util.Xor(b1, t2);
			b3 = Util.Xor(b3, t2);
			b2 = Util.Xor(b2, t1);
			b4 = Util.Xor(b4, t1);
			
			//Permutation des blocs
			/*String temp = b3;
			b3 = b2;
			b2 = temp;*/
		}
		
		//Dernière itération
		//Permutation des blocs
		/*String temp = b3;
		b3 = b2;
		b2 = temp;*/
		
		String kd1 = Util.CalculInverse(parameters.otherKeys.get(0), UtilParameters.moduloMultiplication);
		String kd2 = Util.CalculSomme(parameters.otherKeys.get(1));
		String kd3 = Util.CalculSomme(parameters.otherKeys.get(2));
		String kd4 = Util.CalculInverse(parameters.otherKeys.get(3), UtilParameters.moduloMultiplication);
		
		//Multiplication des éléments B1 et K49
		b1 = Util.Multiplication(kd1, b1);
		b2 = Util.Addition(kd2, b2);
		b3 = Util.Addition(kd3, b3);
		//Multiplication des éléments B1 et K52
		b4 = Util.Multiplication(kd4, b4);
		
		return b1 + b2 + b3 + b4;
	}
}
