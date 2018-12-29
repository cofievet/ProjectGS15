package Global;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Random;

import com.sun.corba.se.impl.ior.ByteBuffer;

import IDEA.GenerateParameters;
import IDEA.SizeKey;
import IDEA.idea;

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
			while(index > chaine.length())
			{
				index -= chaine.length();
			}
			
			//On ajoute l'ensemble des élements après l'index
			String copy = chaine;
			copy += chaine;
			
			//On ajoute l'ensemble des éléments entre 0 et l'index
			int compteur = 0;
			
			char[] tableChar = chaine.toCharArray();
			char[] copyChar = copy.toCharArray();
			for(int i = tableChar.length - index; i < tableChar.length; i++)
			{
				copyChar[i] = tableChar[compteur];			
				compteur++;
			}
			
			return copy;
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
			int sizeMax = element1.length();
			if(element2.length() > element1.length())
				sizeMax = element2.length();
			
			BigInteger e1 = new BigInteger(element1, 2);
			BigInteger e2 = new BigInteger(element2, 2);
			
			BigInteger mod = new BigInteger("65537");
			
			BigInteger result = e1.multiply(e2).mod(mod);

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
			int sizeMax = element1.length();
			if(element2.length() > element1.length())
				sizeMax = element2.length();
			
			BigInteger e1 = new BigInteger(element1, 2);
			BigInteger e2 = new BigInteger(element2, 2);
			
			BigInteger result = e1.add(e2);
			
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
		
		public static String CalculSomme(String key)
		{
			BigInteger k = new BigInteger(key, 2);
			k.add(new BigInteger("65536"));

			return k.toString(2);
		}
		
		public static int CalculInverse(int nb, int modulo) {
			return nb ;
		}
		
		public static int ExponentiationRapide(int nb, int pow, int modulo) {
			return nb ;
		}
}
