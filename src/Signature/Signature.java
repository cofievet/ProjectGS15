package Signature;

import java.math.BigInteger;

import DiffieHellman.DiffieHellman;
import Global.Util;

public class Signature {
	
	//Clé du tiers de confiance
	static BigInteger U = new BigInteger("11");
	static BigInteger beta = new BigInteger("23");
	static BigInteger q = new BigInteger("3");
		

	//Utilisation du l'algorithme RSA pour le chiffrement
	public static void main (String[] args){
		GenerateKey();
	}
	
	//Génération de la clé publique d'Alice
	public static void GenerateKey(){
		int A = 11;
		int alpha = 23;
		int p = 3;
		
		int[] signatureKey = SendKeyAliceToUtt(A, alpha, p);	
		boolean verify = VerifyAliceToBob(A, alpha, p, signatureKey);
		System.out.println("Verification : " + verify);
	}
	
	///Fonction permettant de signer la clé publique d'Alice
	public static int[] SendKeyAliceToUtt(int A, int alpha, int p)
	{	
		//Calcul du modulo
		BigInteger bigModulo = (U .multiply(beta));
		
		//Clé privée
		int d = CalculPrivateKey(U, beta, q);
		System.out.println("Clé privée : " + d);
		
		//Conversion en BigInteger de la clé privée de l'UTT
		BigInteger bigD = new BigInteger(d + "");

		int[] array = new int[3];
		
		//Récupèration de la signature
		BigInteger s = DiffieHellman.fastExponentiation(new BigInteger(A + ""), bigD, bigModulo);
		array[0] = s.intValue();
		
		System.out.println("Signature de la clé publique : " + s );
		
		s = DiffieHellman.fastExponentiation(new BigInteger(alpha + ""), bigD, bigModulo);
		array[1] = s.intValue();

		System.out.println("Signature de la clé publique : " + s );
		
		s = DiffieHellman.fastExponentiation(new BigInteger(p + ""), bigD, bigModulo);
		array[2] = s.intValue();
		
		System.out.println("Signature de la clé publique : " + s );
		
		
		
		//Retour de la signature de la clé publique d'Alice
		return array;
	}
	
	public static boolean VerifyAliceToBob(int A, int alpha, int p, int[] signature)
	{
		//Récupération de la clé publique du tiers de confiance (UTT)
		
		boolean b = true;
		//Calcul du modulo
		BigInteger bigModulo = (U .multiply(beta));
		
		//Converssion des élements en BigInteger
		BigInteger bigPow = new BigInteger(p + "");
		
		//Récupération de la valeur de la signature initiale
		BigInteger s = DiffieHellman.fastExponentiation(new BigInteger(signature[0] + ""), q, bigModulo);
		
		System.out.println("S1 : " + s);
		//System.out.println("A : " + A);
		
		if(s.intValue() != A)
			b = false;
		
		//Récupération de la valeur de la signature initiale
		s = DiffieHellman.fastExponentiation(new BigInteger(signature[1]  + ""), q, bigModulo);
		

		System.out.println("S2 : " + s);
		//System.out.println("alpha : " + alpha);
		
		if(s.intValue() != alpha)
			b = false;
		
		//Récupération de la valeur de la signature initiale
		s = DiffieHellman.fastExponentiation(new BigInteger(signature[2]  + ""), q, bigModulo);
		

		System.out.println("S3 : " + s);
		//System.out.println("p : " + p);
		
		if(s.intValue() != p)
			b = false;
		
		return b;
	}
	
	///Fonction permettan de calculer la clé privée
	public static int CalculPrivateKey(BigInteger param1, BigInteger param2, BigInteger param3)
	{
		BigInteger phiN = (param1.subtract(BigInteger.ONE)).multiply((param2.subtract(BigInteger.ONE)));
		int d = Util.CalculInverse(param3, phiN);
		
		return d;
	}
	
	
}
