package IDEA;

import java.util.Arrays;

import Global.Util;

public class idea {
	
	public static GenerateParameters parameters;
	
	//Fonction de cryptage d'un messga prit en paramètre
	public static String Cryptage(String message, String key)
	{
		SizeKey size = SizeKey.size128;
		
		//Génération des clés de chiffrement
		parameters = new GenerateParameters(size, key);
		
		int sizeBloc = size.GetSize() / 4;
		
		//On récupère les premiers blocs
		String b1 = message.substring(0, sizeBloc);
		String b2 = message.substring(sizeBloc, sizeBloc *  2);
		String b3 = message.substring(sizeBloc * 2, sizeBloc * 3); 
		String b4 = message.substring(sizeBloc * 3, sizeBloc * 4);
		
		//Les 8 itérations
		for(int i = 0; i < 8; i++)
		{
			System.out.println("K1 : " + parameters.otherKeys.get(i * 6));
			System.out.println("K2 : " + parameters.otherKeys.get(i * 6 + 1));
			System.out.println("K3 : " + parameters.otherKeys.get(i * 6 + 2));
			System.out.println("K4 : " + parameters.otherKeys.get(i * 6 + 3));
			System.out.println("K5 : " + parameters.otherKeys.get(i * 6 + 4));
			System.out.println("K6 : " + parameters.otherKeys.get(i * 6 + 5));
			
			//Liste des opérations pour le chiffrement IDEA
			b1 = Util.Multiplication(parameters.otherKeys.get(i * 6), b1);
			b2 = Util.Addition(parameters.otherKeys.get(i * 6 + 1), b2);
			b3 = Util.Addition(parameters.otherKeys.get(i * 6 + 2), b3);
			b4 = Util.Multiplication(parameters.otherKeys.get(i * 6 + 3), b4);
			
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
			String temp = b3;
			b3 = b2;
			b2 = temp;
		}
		
		//Dernière itération
		//Permutation des blocs
		String temp = b3;
		b3 = b2;
		b2 = temp;
		
		//Multiplication des éléments B1 et K49
		b1 = Util.Multiplication(parameters.otherKeys.get(48), b1);
		b2 = Util.Addition(parameters.otherKeys.get(49), b2);
		b3 = Util.Addition(parameters.otherKeys.get(50), b3);
		//Multiplication des éléments B1 et K52
		b4 = Util.Multiplication(parameters.otherKeys.get(51), b4);
		
		//Affichage en sortie
//		System.out.println("B1 : " + b1);
//		System.out.println("B2 : " + b2);
//		System.out.println("B3 : " + b3);
//		System.out.println("B4 : " + b4);
		
		return b1 + b2 + b3 + b4;
		
	}
	
	//Déchiffrement d'un message prit en paramètre
	//La clé pour le chiffrement doit etre réuitliser
	public static String Dechiffrement(String message, String key)
	{
		SizeKey size = SizeKey.size128;
				
		int sizeBloc = size.GetSize() / 4;
		
		//On récupère les premiers blocs
		String b1 = message.substring(0, sizeBloc);
		String b2 = message.substring(sizeBloc, sizeBloc *  2);
		String b3 = message.substring(sizeBloc * 2, sizeBloc * 3); 
		String b4 = message.substring(sizeBloc * 3, sizeBloc * 4);
		
		//Dernière itération
		//Permutation des blocs
		String temp = b3;
		b3 = b2;
		b2 = temp;
		
		//Multiplication des éléments B1 et K49
		b1 = Util.Multiplication(parameters.otherKeys.get(0), b1);
		b2 = Util.Addition(parameters.otherKeys.get(1), b2);
		b3 = Util.Addition(parameters.otherKeys.get(2), b3);
		//Multiplication des éléments B1 et K52
		b4 = Util.Multiplication(parameters.otherKeys.get(3), b4);
				
		
		//Les 8 itérations
		for(int i = 0; i < 8; i++)
		{
			String KD1 = Util.CalculInverseBinaire(parameters.otherKeys.get(8 * 6 - i * 6));
			String KD2 = parameters.otherKeys.get((8 * 6 + 1) - i * 6);
			String KD3 = Util.CalculSomme(parameters.otherKeys.get((8 * 6 + 2) - i * 6));
			String KD4 = Util.CalculInverseBinaire(parameters.otherKeys.get((8 * 6 + 3) - i * 6));
			String KD5 = Util.CalculInverseBinaire(parameters.otherKeys.get((8 * 6 - 2) - i * 6));
			String KD6 = Util.CalculInverseBinaire(parameters.otherKeys.get((8 * 6 - 1) - i * 6));

			System.out.println("K1 : " + parameters.otherKeys.get((8 * 6 ) - i * 6));
			System.out.println("K2 : " + parameters.otherKeys.get((8 * 6 + 1) - i * 6));
			System.out.println("K3 : " + parameters.otherKeys.get((8 * 6 + 2) - i * 6));
			System.out.println("K4 : " + parameters.otherKeys.get((8 * 6 + 3) - i * 6));
			System.out.println("K5 : " + parameters.otherKeys.get((8 * 6 - 2) - i * 6));
			System.out.println("K6 : " + parameters.otherKeys.get((8 * 6 - 1) - i * 6));
			
			//Liste des opérations pour le chiffrement IDEA
			b1 = Util.Multiplication(KD1, b1);
			b2 = Util.Addition(KD2, b2);
			b3 = Util.Addition(KD3, b3);
			b4 = Util.Multiplication(KD4, b4);
			
			String t1 = Util.Xor(b1, b3);
			String t2 = Util.Xor(b2, b4);
			
			t1 = Util.Multiplication(KD5, t1);
			t2 = Util.Addition(t2, t1);
			
			t2 = Util.Multiplication(KD6, t2);

			t1 = Util.Addition(t1, t2);
			b1 = Util.Xor(b1, t2);
			b3 = Util.Xor(b3, t2);
			b2 = Util.Xor(b2, t1);
			b4 = Util.Xor(b4, t1);
			
			//Permutation des blocs
			String temp = b3;
			b3 = b2;
			b2 = temp;
		}
		
	
		return b1 + b2 + b3 + b4;
	}
}
