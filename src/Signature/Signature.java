package Signature;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import DiffieHellman.DiffieHellman;
import IDEA.Util;

public class Signature {
	
	//Clé du tiers de confiance
	static BigInteger U = new BigInteger("11");
	static BigInteger beta = new BigInteger("23");
	static BigInteger q = new BigInteger("3");
	
	static String[] value = new String[3];
		

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
		value[0] = encryptThisString(A + "");
		
		//System.out.println("Signature de la clé publique : " + value[0]);
		//System.out.println("Signature de la clé publique : " + array[0]);
		
		s = DiffieHellman.fastExponentiation(new BigInteger(alpha + ""), bigD, bigModulo);
		array[1] = s.intValue();
		value[1] = encryptThisString(alpha + "");

		//System.out.println("Signature de la clé publique : " + value[1]);
		//System.out.println("Signature de la clé publique : " + array[1]);
		
		s = DiffieHellman.fastExponentiation(new BigInteger(p + ""), bigD, bigModulo);
		array[2] = s.intValue();
		value[2] = encryptThisString(p + "");

		//System.out.println("Signature de la clé publique : " + value[2]);
		//System.out.println("Signature de la clé publique : " + array[2]);
		
		
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
		
		//System.out.println("S1 : " + s.toString());
		//System.out.println("A : " + A);
		
		if(s.intValue() != A && value[0] == encryptThisString(A + ""))
			b = false;
		
		//Récupération de la valeur de la signature initiale
		s = DiffieHellman.fastExponentiation(new BigInteger(signature[1]  + ""), q, bigModulo);
		

		//System.out.println("S2 : " + s);
		//System.out.println("alpha : " + alpha);
		
		if(s.intValue() != alpha && value[1] == encryptThisString(alpha + ""))
			b = false;
		
		//Récupération de la valeur de la signature initiale
		s = DiffieHellman.fastExponentiation(new BigInteger(signature[2]  + ""), q, bigModulo);
		

		//System.out.println("S3 : " + s);
		//System.out.println("p : " + p);
		
		if(s.intValue() != p && value[2] == encryptThisString(p + ""))
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
	
	 public static String encryptThisString(String input) 
	    { 
	        try { 
	            // getInstance() method is called with algorithm SHA-1 
	            MessageDigest md = MessageDigest.getInstance("SHA-1"); 
	  
	            // digest() method is called 
	            // to calculate message digest of the input string 
	            // returned as array of byte 
	            byte[] messageDigest = md.digest(input.getBytes()); 
	  
	            // Convert byte array into signum representation 
	            BigInteger no = new BigInteger(1, messageDigest); 
	  
	            // Convert message digest into hex value 
	            String hashtext = no.toString(16); 
	  
	            // Add preceding 0s to make it 32 bit 
	            while (hashtext.length() < 32) { 
	                hashtext = "0" + hashtext; 
	            } 
	  
	            // return the HashText 
	            return hashtext; 
	        } 
	  
	        // For specifying wrong message digest algorithms 
	        catch (NoSuchAlgorithmException e) { 
	            throw new RuntimeException(e); 
	        } 
	    } 
	
	
}
