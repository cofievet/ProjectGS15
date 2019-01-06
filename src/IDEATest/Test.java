package IDEATest;

import java.util.Arrays;

import com.sun.org.apache.xpath.internal.operations.Equals;

import IDEA.Util;
import junit.framework.TestCase;

public class Test extends TestCase {
	
	//Fonction permettant de tester les modulos possibles
	 public void testModulo() {
		/* assertEquals(Util.Xor("000", "111"), "111");
		 assertEquals(Util.Xor("111", "111"), "000");
		 assertEquals(Util.Xor("000", "000"), "000");
         
		 assertEquals(Util.Multiplication("0", "0"), "0");
		 assertEquals(Util.Multiplication("0", "1"), "0");
		 assertEquals(Util.Multiplication("1", "0"), "0");
		 assertEquals(Util.Multiplication("1", "1"), "1");
		 assertEquals(Util.Multiplication("101", "11"), "1111");*/
		 
		 assertEquals(Util.PermuteIndexLeft("00001111", 4), "11110000");
	 }

}
