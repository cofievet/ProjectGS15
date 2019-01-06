package IDEA;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;

import Global.Util;
import Global.UtilParameters;

public class idea {
	
	public static GenerateParameters parameters;
	
	public static String Cryptage(String message, String key, ModeEnum mode) throws FileNotFoundException, UnsupportedEncodingException
	{		
		//Génération des clés de chiffrement
		parameters = new GenerateParameters(UtilParameters.size, key);
			
		//Initialisation du message
		String encryptMessage = "";
		ArrayList<String> blocs = new ArrayList<>();
			
		//On créé la liste des blocs
		for(int i = 0; i < message.length(); i+=64)
		{
			//Si le message a encore assez de caractères
			if(message.length() > i + 64)
			{
				blocs.add(message.substring(i, i+ 64));
			}
			//Sinon : bourrage à la fin
			else
			{
				String value = message.substring(i);
				for(int j = value.length(); j <= 63 ; j++)
				{
					value += "0";
				}
				blocs.add(value);
			}
		}
		

		String vecteur = "";
		String chiffreTemp = "";
		String texteClair = "";
		String chiffrePrec = "";
		for(int i = 0; i < 64; i++)
		{
			vecteur += "1";
		}
	

		int compteur = 1;		
		if(mode.GetMode().equals("CBC"))
		{
			for (String bloc : blocs) {
				
				if(compteur == 1)
				{
					bloc = Util.Xor(bloc, vecteur);
					compteur++;
				}
				else
					bloc = Util.Xor(chiffreTemp, bloc);
				
				chiffreTemp = CryptageBloc(bloc);
				encryptMessage += chiffreTemp;
			}
		}
		
		if(mode.GetMode().equals("PCBC"))
		{
			for (String bloc : blocs) {
				if(compteur == 1)
				{
					bloc = Util.Xor(bloc, vecteur);
					compteur++;
				}
				else
				{
					bloc = Util.Xor(chiffreTemp, texteClair);
				}
				
				texteClair = bloc;
			}
		}
		
		if(mode.GetMode().equals("ECB"))
		{
			for (String bloc : blocs) {
				chiffreTemp = CryptageBloc(bloc);
				encryptMessage += chiffreTemp;
			}
		}
				
		String path = System.getProperty("user.dir");
		File folder = new File(path); 
		folder.mkdir(); // create a folder in your current work space
		File file = new File(folder, "fileName.txt"); // put the file inside the folder
		
		PrintWriter writer = new PrintWriter(file);
		writer.println(encryptMessage);
		writer.close();
		
		//On retourne le message chiffré
		return encryptMessage;		
	}
	
	public static String Dechiffrement(String message, String key, ModeEnum mode)
	{
		//Génération des clés de chiffrement
		parameters = new GenerateParameters(UtilParameters.size, key);
			
		//Initialisation du message
		String decryptMessage = "";
		ArrayList<String> blocs = new ArrayList<>();
			
		//On créé la liste des blocs
		for(int i = 0; i < message.length(); i+=64)
		{
			//Si le message a encore assez de caractères
			if(message.length() > i + 64)
			{
				blocs.add(message.substring(i, i+ 64));
			}
			//Sinon : bourrage à la fin
			else
			{
				String value = message.substring(i);
				for(int j = value.length(); j <= 63 ; j++)
				{
					value += "0";
				}
				blocs.add(value);
			}
		}
		
		String vecteur = "";
		String dechiffreActuel = "";
		String chiffrePrec = "";
		String textClair = "";
		
		for(int i = 0; i < 64; i++)
		{
			vecteur += "1";
		}
		
		
		int compteur = 1;		
		if(mode.GetMode().equals("CBC"))
		{
			for(String bloc : blocs)
			{
				dechiffreActuel = DechiffrementBloc(bloc);
				decryptMessage += dechiffreActuel;
				
				if(compteur == 1)
				{	
					decryptMessage += Util.Xor(vecteur, dechiffreActuel);
					compteur++;
				}
				else
					decryptMessage += Util.Xor(dechiffreActuel, chiffrePrec);
				

			}
		}
		
		if(mode.GetMode().equals("PCBC"))
		{
			for (String bloc : blocs) {
				if(compteur == 1)
				{
					decryptMessage += Util.Xor(vecteur, dechiffreActuel);
					compteur++;
				}
				else
				{
					
				}
			}
		}
		
		if(mode.GetMode().equals("ECB"))
		{
			for (String bloc : blocs) {

				dechiffreActuel = DechiffrementBloc(bloc);
				decryptMessage += dechiffreActuel;
			}
		}
		
		//On retourne le message chiffré
		return decryptMessage;		
	}
	
	//Fonction de cryptage d'un messga prit en paramètre
	public static String CryptageBloc(String bloc)
	{
		//Taille des blocs
		int sizeBloc = bloc.length() / 4;
		
		//On divise les blocs en 4
		String b1 = bloc.substring(0, sizeBloc);
		String b2 = bloc.substring(sizeBloc, sizeBloc *  2);
		String b3 = bloc.substring(sizeBloc * 2, sizeBloc * 3); 
		String b4 = bloc.substring(sizeBloc * 3, sizeBloc * 4);

		//Les x itérations
		for(int i = 0; i < UtilParameters.nbRotations; i++)
		{				
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
	public static String DechiffrementBloc(String bloc)
	{
		int sizeBloc = bloc.length() / 4;
		
		//On récupère les premiers blocs
		String b1 = bloc.substring(0, sizeBloc);
		String b2 = bloc.substring(sizeBloc, sizeBloc *  2);
		String b3 = bloc.substring(sizeBloc * 2, sizeBloc * 3); 
		String b4 = bloc.substring(sizeBloc * 3, sizeBloc * 4);
		
		
		
		//Les x itérations
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
	
	public String getBits(byte b)
	{
	    String result = "";
	    for(int i = 0; i < 8; i++)
	        result += (b & (1 << i)) == 0 ? "0" : "1";
	    return result;
	}
}
