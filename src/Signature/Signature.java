package Signature;

import Global.Util;

public class Signature {
	
	//Algorithme RSA pour signer
	
	public static void GenerateKey(){
		int A = 11;
		int alpha = 23;
		int p = 3;
		
		int signature = SendKeyAliceToUtt(A, alpha, p);			
		
	}
	
	public static int SendKeyAliceToUtt(int A, int alpha, int p)
	{
		int U = 1;
		int beta = 1;
		int q = 1;
		
		int n = A * alpha;
		int phiN = (A - 1) * (alpha - 1);
			
		int d = Util.CalculInverse(p, phiN);
		int s = Util.ExponentiationRapide(111, d, n);
		
		return s;
		
		//PHI(n) = (A - 1)(alpha - 1)
				//d = Calcul de l'inverse de p modulo PHI(n)
				//Exponentiation rapide de la clé publique^d modulo n
				//Calcul du S pour
	}
	
	public static int VerifyAliceToBob(int message, int signature)
	{
		
		return -1;
		
		//PHI(n) = (A - 1)(alpha - 1)
				//d = Calcul de l'inverse de p modulo PHI(n)
				//Exponentiation rapide de la clé publique^d modulo n
				//Calcul du S pour
	}
	
	public static int VerifyCertificat(int signature)
	{
		int U = 1;
		int beta = 1;
		int q = 1;
		
		int n = U * beta;
		int phiN = (U - 1) * (beta - 1);
	
		System.out.println("n : " + n);
		System.out.println("Phi de N :" + phiN);
		
		int d = Util.CalculInverse(q, phiN);
		int s = Util.ExponentiationRapide(111, d, n);
		
		return s;
	}
	
	
}
