/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SHA3;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author v1nkey
 */
public class SHA3 {
    private static int BLOCK_SIZE = 1600;
    private static int NB_ITER = 24;
    private static String LFSR_CURRENT_STATE = "10010110";
    
    public static String hash(String message, int hashSize)
    {
        int capacity = 2 * hashSize;
        int rate = BLOCK_SIZE - capacity;
        
        //Padding du message et insertion du 1er bloc initialisé à 0
        message = pad(message, rate);
        message = fillFirstBlockWith0(message);
        
        List<String> blocks = splitMessage(message);
        List<String> xoredBlocks = new ArrayList();
        List<String[][]> splitBlocks = new ArrayList();
        
        //Séparation du message en blocs (le 1er bloc sera composé de 0)
        for (String block : blocks)
            splitBlocks.add(splitBlocks(block));
        
        //Modification du message pour le passer à la fonction f (xor de r bits de Bi avec r bits de Bi-1
        for (int i = 1; i < blocks.size(); i++)
        {
            StringBuilder tmp = new StringBuilder(blocks.get(i-1));
            StringBuilder tmp2 = new StringBuilder(blocks.get(i));
            
            String r_iMinus1 = tmp.substring(0, rate);
            String r_i = tmp.substring(0, rate);
            
            xoredBlocks.add(xorString(r_i, r_iMinus1));
        }
        
        //Séparation des différents blocs en sous-blocs de 5x5x64 bits
        for (String xoredBlock : xoredBlocks)
            splitBlocks.add(splitBlocks(xoredBlock));  
        
        //Application de la fonction f à chacun des sous-blocs de chaque bloc
        for (String[][] splitBlock : splitBlocks)
        {
            for (int i = 0; i < NB_ITER; i++)
                f(splitBlock);
        }
        
        List<StringBuilder> hashedBlocks = new ArrayList();
        
        //Reformation du message hashé 
        //Reconstitution de chaque bloc à partir des sous-bloc de 5x5x64
        for (String[][] splitBlock : splitBlocks)
        {
            StringBuilder hashedBlock = new StringBuilder();
            for (int i = 0; i < 5; i++)
            {
                for (int j = 0; j < 5; j++)
                    hashedBlock.append(splitBlock[i][j]);
            }
            hashedBlocks.add(hashedBlock);
        }
        
        StringBuilder hashedMessage = new StringBuilder();
        
        //Concaténation de r premiers bits de chaque bloc
        for (StringBuilder hashedBlock : hashedBlocks)
            hashedMessage.append(hashedBlock.substring(0, rate));
        
        //La fonction retour les p premiers bits issus de la concaténation afin d'avoir un hash de taille p
        return hashedMessage.substring(0, hashSize);
    }
    
    //Complétion du message avec des 0 jusqu'à obtenir un message dont la taille est un multiple de r
    private static String pad(String message, int rate)
    {
        StringBuilder sbMessage = new StringBuilder(message);
        
        int sizeMessage = sbMessage.length();
        
        for (int i = 0; i < sizeMessage % rate; i++)
            sbMessage.append(0);
        
        return sbMessage.toString();
    }
    
    //Fonction d'insertion d'un premier bloc composé de 0
    private static String fillFirstBlockWith0(String message)
    {
        StringBuilder firstBlock = new StringBuilder("");
        for (int i = 0; i < BLOCK_SIZE; i++)
            firstBlock.append('0');
        
        StringBuilder sbMessage = new StringBuilder(message);
        firstBlock.append(sbMessage);
        
        return firstBlock.toString();
    }
    
    //Séparation du message en bloc de b bits
    private static List<String> splitMessage(String message)
    {
        List<String> blocks = new ArrayList();
        
        for (int i = 0; i < message.length(); i += BLOCK_SIZE)
        {
            String block = (String)message.subSequence(i, i + BLOCK_SIZE - 1);
            blocks.add(block);
        }
        
        return blocks;
    }
    
    //Séparation d'un bloc en sous-bloc de 5x5x64
    private static String[][] splitBlocks(String block)
    {
        String subBlock[][] = new String[5][5];
        int blockLength = block.length();
        
        for (int j = 0; j < 5; j++)
        {
            int k = 0;
            for (int i = 0; i < blockLength/5; i += 64)
            {
                String subBlockString = block.substring(i, i+64);
                subBlock[j][k++] = subBlockString;
            }
        }
        
        return subBlock;
    }
    
    //Etape 1 de la fonction f
    private static void changeBit(String[][] block)
    {
        for (int i = 0; i < 5; i++)
        {
            for (int j = 0; j < 5; j++)
            {
                for (int k = 0; k < 64; k++)
                {
                    //Remplacement de chacun des bits par un xor entre ce bit et le bit de parité (de poids faible) de la colonne adjacente
                    char newBit = xorChar(block[i][j].charAt(k), block[i][j-1].charAt(block[i][j-1].length() - 1));
                    StringBuffer tmp = new StringBuffer(block[i][j]);
                    tmp.setCharAt(k, newBit);
                    block[i][j] = new String(tmp);
                }
            }
        }
    }
    
    //Etape 2 de f : décalage de bits vers la gauche
    private static void permuteBit(String[][] block)
    {
        for (int i = 0; i < 5; i++)
        {
            for (int j = 0; j < 5; j++)
            {
                //t = 2*i + 3*j + 1 -> t dépend de i et j
                for (int k = 0; k < (2 * i + 3 * j + 1); k++)
                {
                    //On supprime t fois le 1er bit de la séquence pour le réinjecter à la fin
                    StringBuffer tmp = new StringBuffer(block[i][j]);
                    StringBuffer tmp2 = tmp.deleteCharAt(0);
                    tmp.append(tmp2);
                    block[i][j] = new String(tmp);
                }
            }
        }
    }
    
    //Etape 3 de f
    private static void permuteSubBlock(String[][] block)
    {
        String tmp;
        for (int i = 0; i < 5; i++)
        {
            for (int j = 0; j < 5; j++)
            {
                tmp = block[i][j];
                block[i][j] = block[j][(2*i + 3*j)%5];
                block[j][(2*i + 3*j)%5] = tmp;
            }
        }
    }
    
    //Etape 4 de f
    private static void xorLines(String[][] block)
    {
        for (int i = 0; i < 5; i++)
        {
            for (int j = 0; j < 5; j++)
            {
                String s1 = block[i][j];
                String s2 = block[i][(j-1)%5];
                String s3 = block[i][(j+1)%5];
                
                String result = xorString(s1, andString(s2, s3));
                
                block[i][j] = result;
            }
        }
    }
    
    //Etape 5 de f
    private static void lastXor(String[][] block)
    {
        int m = 0;
        int lastCurrentBitLFSR = 0;
        char newBit;
        
        for (int i = 0; i < 5; i++)
        {
            for (int j = 0; j < 5; j++)
            {
                for (int k = 0; k < 64; k++)
                {
                    StringBuffer tmp = new StringBuffer(block[i][j]);
                    m = (i + j + k) % 6;
                    lastCurrentBitLFSR = lastBitLFSR();
                    newBit = xorChar(block[i][j].charAt((int)(Math.pow(2, m)-1)), block[i][j].charAt(m + 7 * lastCurrentBitLFSR));
                    
                    tmp.setCharAt(k, newBit);
                }
            }
        }
    }
    
    //Création d'un LFSR et récupération du bit généré
    //Fonction de généation du LFSR = n = n-1 + n-4 + n-7 + n-8
    private static int lastBitLFSR()
    {
        StringBuilder newState = new StringBuilder(LFSR_CURRENT_STATE);
        
        int sizeLFSR = LFSR_CURRENT_STATE.length();
        int lastBit = (Character.getNumericValue(LFSR_CURRENT_STATE.charAt(sizeLFSR-1)) 
                + Character.getNumericValue(LFSR_CURRENT_STATE.charAt(sizeLFSR-4)) 
                + Character.getNumericValue(LFSR_CURRENT_STATE.charAt(sizeLFSR-7)) 
                + Character.getNumericValue(LFSR_CURRENT_STATE.charAt(sizeLFSR-8))) % 2;
        
        newState.append(lastBit);
        LFSR_CURRENT_STATE = newState.toString();
        return lastBit;
    }
    
    //Fonction de hashage
    private static void f(String[][] subBlock)
    {
        changeBit(subBlock);
        permuteBit(subBlock);
        permuteSubBlock(subBlock);
        xorLines(subBlock);
        lastXor(subBlock);
    }
    
    private static char andChar(char a, char b)
    {
        if (a == b)
            return a == '1' ? '1' : '0';
        
        return '0';
    }
    
    private static String andString(String a, String b)
    {
        int sizeString = a.length();
        
        StringBuilder tmp = new StringBuilder(sizeString);
        
        for (int i = 0; i < sizeString; i++)
        {
            char andedBit = andChar(a.charAt(i), b.charAt(i));
            tmp.setCharAt(i, andedBit);
        }
        
        return tmp.toString();
    }
    
    private static char xorChar(char a, char b) { return (a == b) ? '0' : '1'; }
    
    private static String xorString(String a, String b)
    {
        int sizeString = a.length();
        
        StringBuilder tmp = new StringBuilder(sizeString);
        
        for (int i = 0; i < sizeString; i++)
        {
            char xoredBit = xorChar(a.charAt(i), b.charAt(i));
            tmp.setCharAt(i, xoredBit);
        }
        
        return tmp.toString();
    }
}
