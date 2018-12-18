/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gs15;

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
//        System.out.println("base :\t" + base + "\npuissance :\t" + pow + "\nmodulo :\t" + mod);
        String str = pow.toString(2);
        StringBuffer binaryPow = new StringBuffer(str).reverse();
//        System.out.println(binaryPow);
        
        List<BigInteger> modularPow = new LinkedList();
        modularPow.add(base);
                
        int k = 0;
        BigInteger i = TWO;
        
        while(i.compareTo(pow) < 0)
        {
            BigInteger value = (modularPow.get(k).multiply(modularPow.get(k))).mod(mod);
//            System.out.println(modularPow.get(k));
            modularPow.add(value);
            k++;
            i = i.multiply(TWO);
        }
        
        BigInteger result = BigInteger.ONE;
        
        for (int j = 0; j < str.length(); j++)
        {
            String bit = Character.toString(binaryPow.charAt(j));
            if (bit.equals("1"))
                result = result.multiply(modularPow.get(j)).mod(mod);
//                result *= (Integer.parseInt(tmp) * modularPow.get(i)) % mod;
        }
//        System.out.println("Resultat :");
        return result.mod(mod);
    }
            
    public static boolean millerWitness(BigInteger a, BigInteger n)
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
//        System.out.println("a :\t" + a + "\nd :\t" + d + "\ns :\t" + s + "\nn :\t" + n);
        
        BigInteger x = DiffieHellman.fastExponentiation(a, d, n);
        
//        System.out.println("a :" + a + "\td :" + d + "\ts: " + s + "\tn :" + n + "\tx : " + x);
        
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
    
    public static boolean millerRabinTest(BigInteger n, int k)
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
        BigInteger q = p.subtract(BigInteger.ONE).divide(TWO);
        
        BigInteger g = new BigInteger(p.bitLength()-1, new Random());
        
        while(g.multiply(g).mod(p).equals(BigInteger.ONE) || fastExponentiation(g, q, p).equals(BigInteger.ONE))
            g = new BigInteger(p.bitLength()-1, new Random());
        
        return g;
    }
}
