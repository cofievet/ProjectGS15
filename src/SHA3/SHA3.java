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
    private static String LFSR_CURRENT_STATE = "10010110";
    
    public static int hash(String message, int hashSize)
    {
        int capacity = 2 * hashSize;
        int rate = BLOCK_SIZE - capacity;
        
        message = pad(message, rate);
        message = fillFirstBlockWith0(message);
        
        List<String> blocks = splitMessage(message);
        List<String[][]> splitBlocks = new ArrayList(); 
        
        for (String block : blocks)
            splitBlocks.add(splitBlocks(block));
        
        for (String[][] subBlock : splitBlocks)
        {
            changeBit(subBlock);
            permuteBit(subBlock);
            permuteSubBlock(subBlock);
            xorLines(subBlock);
            lastXor(subBlock);
        }
        
        return 0;
    }
    
    private static String pad(String message, int rate)
    {
        StringBuilder sbMessage = new StringBuilder(message);
        
        int sizeMessage = sbMessage.length();
        
        for (int i = 0; i < sizeMessage % rate; i++)
            sbMessage.append(0);
        
        return sbMessage.toString();
    }
    
    private static String fillFirstBlockWith0(String message)
    {
        StringBuilder firstBlock = new StringBuilder("");
        for (int i = 0; i < BLOCK_SIZE; i++)
            firstBlock.append('0');
        
        StringBuilder sbMessage = new StringBuilder(message);
        firstBlock.append(sbMessage);
        
        return firstBlock.toString();
    }
    
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
    
    private static void changeBit(String[][] block)
    {
        for (int i = 0; i < 5; i++)
        {
            for (int j = 0; j < 5; j++)
            {
                for (int k = 0; k < 64; k++)
                {
                    char newBit = xor(block[i][j].charAt(k), block[i][j-1].charAt(block[i][j-1].length() - 1));
                    StringBuffer tmp = new StringBuffer(block[i][j]);
                    tmp.setCharAt(k, newBit);
                    block[i][j] = new String(tmp);
                }
            }
        }
    }
    
    private static void permuteBit(String[][] block)
    {
        for (int i = 0; i < 5; i++)
        {
            for (int j = 0; j < 5; j++)
            {
                for (int k = 0; k < (2 * i + 3 * j + 1); k++)
                {
                    StringBuffer tmp = new StringBuffer(block[i][j]);
                    StringBuffer tmp2 = tmp.deleteCharAt(0);
                    tmp.append(tmp2);
                    block[i][j] = new String(tmp);
                }
            }
        }
    }
    
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
    
    private static void xorLines(String[][] block)
    {
        for (int i = 0; i < 5; i++)
        {
            for (int j = 0; j < 5; j++)
            {
                BigInteger bi1 = new BigInteger(block[i][j]);
                BigInteger bi2 = new BigInteger(block[i][(j-1)%5]);
                BigInteger bi3 = new BigInteger(block[i][(j+1)%5]);
                
                BigInteger result = bi1.xor(bi2.and(bi3));
                
                block[i][j] = result.toString(2);
            }
        }
    }
    
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
                    newBit = xor(block[i][j].charAt((int)(Math.pow(2, m)-1)), block[i][j].charAt(m + 7 * lastCurrentBitLFSR));
                    
                    tmp.setCharAt(k, newBit);
                }
            }
        }
    }
    
    //n = n-1 + n-4 + n-7 + n-8
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
    
    private static char and(char a, char b)
    {
        if (a == b)
            return a == '1' ? '1' : '0';
        
        return '0';
    }
    
    private static char xor(char a, char b)
    {
        if (a == b)
            return '0';
        
        return '1';
    }
}
