/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DiffieHellman;

import java.math.BigInteger;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author v1nkey
 */
public class DiffieHellman 
{
    private static BigInteger TWO = BigInteger.valueOf(2);
    
    public static BigInteger generateSafePrime(int nbBits, int nbIterations)
    {
        BigInteger p = BigInteger.probablePrime(nbBits, new Random()); 
        BigInteger q = p.multiply(TWO).add(BigInteger.ONE);
        
        while(!(millerRabinTest(p, nbIterations) && millerRabinTest(q, nbIterations)))
        {
            p = BigInteger.probablePrime(nbBits, new Random());
            q = p.multiply(TWO).add(BigInteger.ONE);
        }
        return q;
    }
    
    public static BigInteger fastExponentiation(BigInteger base, BigInteger pow, BigInteger mod)
    {
        //Passage de la puissance en binaire pour savoir quelles puissances multiplier
        String str = pow.toString(2);
        //Ecriture de la puissance à l'envers pour se conformer au sens d'itération
        StringBuffer binaryPow = new StringBuffer(str).reverse();
        
        //Liste des valeurs calculées
        List<BigInteger> modularPow = new LinkedList();
        modularPow.add(base);
                
        int k = 0;
        BigInteger i = TWO;
        
        while(i.compareTo(pow) < 0)
        {
            //Elevation au carré de la dernière puissance calculée
            BigInteger value = (modularPow.get(k).multiply(modularPow.get(k))).mod(mod);
            modularPow.add(value);
            k++;
            i = i.multiply(TWO);
        }
        
        BigInteger result = BigInteger.ONE;
        
        for (int j = 0; j < str.length(); j++)
        {
            String bit = Character.toString(binaryPow.charAt(j));
            //Si la valeur du bit est à 1 (dans l'écriture binaire de la puissance, on multiplie la valeur pour le calcul du résultat
            if (bit.equals("1"))
                result = result.multiply(modularPow.get(j)).mod(mod);
        }
        
        return result.mod(mod);
    }
            
    //Test du témoin de Miller
    private static boolean millerWitness(BigInteger a, BigInteger n)
    {
        // n doit être un entier >= 3
        if (n.mod(TWO).equals(BigInteger.ZERO))
            return true;
        
        BigInteger d = n.subtract(BigInteger.ONE);
        int s = 0;
        
        while (d.mod(TWO).equals(BigInteger.ZERO))
        {
            d = d.divide(TWO);
            s++;
        }
        
        BigInteger x = DiffieHellman.fastExponentiation(a, d, n);
        
        //Si a^d mod n = 1 ou n -1 alors a n'est pas un témoin de Miller et n est premier avec a
        if (x.equals(BigInteger.ONE) || x.equals(n.subtract(BigInteger.ONE)))
            return false;
        
        while (s > 1)
        {
            x = x.multiply(x).mod(n);
            if (x.equals(n.subtract(BigInteger.ONE)))
                return false;
            
            s--;
        }
        return true;
    }
    
    //Test de Rabin Miller
    private static boolean millerRabinTest(BigInteger n, int k)
    {
        for (int i = 0; i < k; i++)
        {
            //nombre aléatoire entre 2 et n-2
            BigInteger a = new BigInteger(n.bitLength()-1, new Random());
            if (millerWitness(a, n)) // n est composé
                return false;
        }
        return true; // n est probablement premier
    }
    
    public static BigInteger getGenerator(BigInteger p)
    {
        //p = 2*q + 1 avec q premier
        //q et 2 sont donc les diviserus de p-1
        //donc g est générateur si g^2 mod p et g^q mod p sont différents de 1
        BigInteger q = p.subtract(BigInteger.ONE).divide(TWO);
        
        BigInteger g = new BigInteger(p.bitLength()-1, new Random());
        
        while(g.multiply(g).mod(p).equals(BigInteger.ONE) || fastExponentiation(g, q, p).equals(BigInteger.ONE))
            g = new BigInteger(p.bitLength()-1, new Random());
        
        return g;
    }
    
    public static BigInteger[] aliceToBob(BigInteger aliceNumber)
    {        
        BigInteger p = generateSafePrime(1024, 10);
        BigInteger g = getGenerator(p);
        
        BigInteger a = fastExponentiation(g, aliceNumber, p);
        
        BigInteger[] publicKey = {g, p, a};
        
        return publicKey;
    }
    
    public static BigInteger bobToAlice(BigInteger[] aliceKey, BigInteger bobNumber)
    {
        BigInteger g = aliceKey[0];
        BigInteger p = aliceKey[1];
        BigInteger a = aliceKey[2];
        
        BigInteger b = fastExponentiation(g, bobNumber, p);
        
        return b;
    }
    
    public static boolean areEquals(BigInteger a, BigInteger b, BigInteger c)
    {
        return a.equals(b) ? b.equals(c) : false;
    }
}
