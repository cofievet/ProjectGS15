package IDEA;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Random;

import com.sun.corba.se.impl.ior.ByteBuffer;
import com.sun.deploy.uitoolkit.impl.fx.ui.UITextArea;

public class Util {
	

		//Fonction permettant de supprimer des éléments d'un tableau d'un index de début à un index de fin
		public static byte[] RemoveElementsIndex(byte[] array, int indexStart, int indexEnd)
		{
			//Si il n'y a pas d'élément dans le tableau
			if(array.length <= 0)
			{
				return null;
			}
			
			//Si les index ne sont pas dans le tableau
			if(indexStart > array.length || indexEnd > array.length)
			{
				return null;
			}
			
			//On créé les copies des tableaux
			byte[] arrayCopyStart = array;
			byte[] arrayCopyEnd = array;
			
			//On fait une copie des tableaux de 0 à l'index du début
			arrayCopyStart = Arrays.copyOfRange(array, 0 , indexStart);
			
			//On fait une copie du tableaux de l'index de fin à la taille du tableau
			arrayCopyEnd = Arrays.copyOfRange(array,indexEnd , array.length);
			
			byte[] arrayCopy = new byte[arrayCopyStart.length + arrayCopyEnd.length];
			for(int i = 0; i < arrayCopyStart.length; i++)
			{
				arrayCopy[i] = arrayCopyStart[i];
			}
			
			for(int i = 0; i < arrayCopyEnd.length; i++)
			{
				arrayCopy[i + arrayCopyStart.length] = arrayCopyEnd[i];
			}
			
			
			return arrayCopy;
		}
		
		//Fonction permettant de générer un nombre aléatoire entre le max et le min prit en paramètre
		public static int GenerateRandomBetweenMinAndMax(int min, int max)
		{
			return new Random().nextInt(max + 1) + min;
		}
		
		//Fonction permettant de faire une permutation d'un tablau en fonction d'un index
		public static String PermuteIndexLeft(String chaine, int index)
		{			
			if(index < chaine.length()){
				String copy = chaine.substring(index);
				copy+= chaine.substring(0, index);
				return copy;
			}
			return chaine;
		}
		
		//XOR binaire entre 2 tableaux de bytes
		public static String Xor(String element1, String element2)
		{
			int sizeMax = element1.length();
			if(element2.length() > element1.length())
				sizeMax = element2.length();
						
			BigInteger e1 = new BigInteger(element1, 2);
			BigInteger e2 = new BigInteger(element2, 2);
			
			BigInteger result = e1.xor(e2);
			
			StringBuilder resultString = new StringBuilder(result.toString(2));
			int diff = sizeMax - resultString.length();
			
			for(int i = 0; i <diff; i++)
			{
				resultString.insert(0, "0");
			}			
			
			return resultString.toString();
		}
		
		//Multiplication de deux tableaux de bytes
		public static String Multiplication(String element1, String element2)
		{	
			int sizeMax = UtilParameters.sizeKey;
			
			
			//Création des éléments en biginteger
			BigInteger e1 = new BigInteger(element1, 2);
			BigInteger e2 = new BigInteger(element2, 2);
			
			if(e1.intValue() == 0)
				e1 = new BigInteger(UtilParameters.moduloAddition);
			
			if(e2.intValue() == 0)
				e2 = new BigInteger(UtilParameters.moduloAddition);
			
			BigInteger mod = new BigInteger(UtilParameters.moduloMultiplication);
			
			//Résultat de la multiplication des deux éléments modulo
			BigInteger result = e1.multiply(e2).mod(mod);
			
			if(result.add(BigInteger.ONE).equals(mod))
			{
				//System.out.println(result.toString(2));
				result = new BigInteger("0");
			}
			
			StringBuilder resultString = new StringBuilder(result.toString(2));
			int diff = sizeMax - resultString.length();
			
			for(int i = 0; i <diff; i++)
			{
				resultString.insert(0, "0");
			}
			
			return resultString.toString();
		}
				
		//Addition de 2 tableaux de bytes
		public static String Addition(String element1, String element2)
		{				
			int sizeMax = UtilParameters.sizeKey;
			
			//System.out.println(element1 + " " + element2);
			
			BigInteger e1 = new BigInteger(element1, 2);
			BigInteger e2 = new BigInteger(element2, 2);
			
			BigInteger mod = new BigInteger(UtilParameters.moduloAddition);
			
			BigInteger result = e1.add(e2).mod(mod);
			
			StringBuilder resultString = new StringBuilder(result.toString(2));
			int diff = sizeMax - resultString.length();
			
			for(int i = 0; i <diff; i++)
			{
				resultString.insert(0, "0");
			}	
			
			return resultString.toString();
		}
		
		public static String CalculInverseBinaire(String key)
		{
			String inverse = "";
			char[] keyChar = key.toCharArray();
			for(int i = 0; i < key.length(); i++)
			{
				if(keyChar[i] == '0')
					inverse += "1";
				else
					inverse += "0";
			}

			return inverse;
		}
		
		public static String CalculInverse(String value, String modulo)
		{
			BigInteger big = new BigInteger(value, 2);
			BigInteger mod = new BigInteger(modulo);
			
			String result = big.modInverse(mod).toString(2);
			
			if(result.length() < UtilParameters.sizeKey)
			{
				StringBuilder resultString = new StringBuilder(result);
				int diff = UtilParameters.sizeKey - result.length();
				
				for(int i = 0; i < diff; i++)
				{
					resultString.insert(0, "0");
				}	
				
				return resultString.toString();
			}
			
			return result;
		}
		
		public static String CalculSomme(String key)
		{
			BigInteger k = new BigInteger(key, 2);
			k = new BigInteger(UtilParameters.moduloAddition).subtract(k);

			String result = k.toString(2);
			if(result.length() < UtilParameters.sizeKey)
			{
				StringBuilder resultString = new StringBuilder(result);
				int diff = UtilParameters.sizeKey - result.length();
				
				for(int i = 0; i < diff; i++)
				{
					resultString.insert(0, "0");
				}	
				
				return resultString.toString();
			}
			
			return k.toString(2);
		}
		
		public static int CalculInverse(BigInteger nb, BigInteger modulo) {
			return nb.modInverse(modulo).intValue();
		}
}
