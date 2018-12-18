/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gs15;

import java.math.BigInteger;
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
        BigInteger p = DiffieHellman.generateSafePrime(1024, 10);
        System.out.println(p);
        System.out.println(DiffieHellman.getGenerator(p));
//        BigInteger x = BigInteger.valueOf(49999);
//        System.out.println(x.isProbablePrime(10));
    }
    
}
