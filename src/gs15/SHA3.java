/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gs15;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author v1nkey
 */
public class SHA3 {
    private static int BLOCK_SIZE = 1600;
    
    public static int hash(int message, int hashSize)
    {
        int capacity = 2 * hashSize;
        int rate = BLOCK_SIZE - capacity;
        
        message = pad(message, rate);
        
        StringBuilder firstBlock = new StringBuilder("");
        for (int i = 0; i < BLOCK_SIZE; i++)
            firstBlock.append('0');
        
        StringBuilder binaryMessage = new StringBuilder(Integer.toBinaryString(message));
        firstBlock.append(binaryMessage);
        
        message = new Integer(firstBlock.toString());
        
        
    }
    
    public static int pad(int message, int rate)
    {
        StringBuilder binaryMessage = new StringBuilder(Integer.toBinaryString(message));
        
        int sizeMessage = binaryMessage.length();
        
        for (int i = 0; i < sizeMessage % rate; i++)
            binaryMessage.append(0);
        
        return new Integer(binaryMessage.toString());
    }
    
    public static List<Integer> splitMessage(int message)
    {
        List<Integer> blocks = new ArrayList();
        
        String binaryMessage = Integer.toBinaryString(message);
        
        for (int i = 0; i < binaryMessage.length(); i += BLOCK_SIZE)
        {
            String binaryBlock = (String)binaryMessage.subSequence(i, i + BLOCK_SIZE - 1);
            blocks.add(new Integer(binaryBlock));
        }
        
        return blocks;
    }
}
