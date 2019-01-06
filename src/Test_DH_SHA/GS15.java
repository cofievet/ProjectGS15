/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Test_DH_SHA;

import DiffieHellman.DiffieHellman;
import java.math.BigInteger;
import java.util.Random;
/**
 *
 * @author v1nkey
 */
public class GS15 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
//        BigInteger x1 = new BigInteger(100, 1, new Random());
        
//        System.out.println(DiffieHellman.millerRabinTest(new BigInteger("24989"), 10));
//        BigInteger p = DiffieHellman.generateSafePrime(1024, 10);
//        System.out.println(p);
//        System.out.println(DiffieHellman.getGenerator(p));
//        BigInteger x = BigInteger.valueOf(49999);
//        System.out.println(x.isProbablePrime(10));

//        String message = "";
//        for (int i = 0; i< 1600; i++)
//        {
//            if (i%64 == 0)
//                message += "1";
//            else 
//                message += "0";
//        }
//        
//        System.out.println(message + "\n\n");
//        
//        BigInteger test[][];
//        test = SHA3.splitBlocks(message);
//        
//        for (int i = 0; i < 5; i++)
//        {
//            for (int j = 0; j < 5; j++)
//                System.out.println(test[i][j]);
//        }

        BigInteger alice = new BigInteger(1024, new Random());
        BigInteger bob = new BigInteger(1024, new Random());
        
        BigInteger[] aliceKey = DiffieHellman.aliceToBob(alice);
        BigInteger bobKey = DiffieHellman.bobToAlice(aliceKey, bob);
        
        BigInteger ab = alice.multiply(bob);
        
        BigInteger g = aliceKey[0];
        BigInteger p = aliceKey[1];
        BigInteger a = aliceKey[2];
        
        BigInteger aliceResult = DiffieHellman.fastExponentiation(bobKey, alice, p);
        BigInteger bobResult = DiffieHellman.fastExponentiation(a, bob, p);
        BigInteger basicResult = DiffieHellman.fastExponentiation(g, ab, p);

        boolean test = DiffieHellman.areEquals(aliceResult, bobResult, basicResult);
    }
    
}
