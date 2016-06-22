import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;


public class RedditTest {

	static WebDriver driver = new HtmlUnitDriver();
	
	// Start at ruby tool hoophopper
	@Before
	public void setUp() throws Exception {
		driver.get("http://lit-bayou-7912.herokuapp.com/");
	}

	//Test finds whether or not input "a = 10" is fully tokenized.
	//The output after tokenizing the code should should "a", "=", "10" have been tokenized
	//This is accomplished by examining whether or not the output string contains these tokens
	@Test
	public void US1TC1() {
		
		
		driver.findElement(By.id("code_code")).clear();
	    driver.findElement(By.id("code_code")).sendKeys("a = 10");
	    driver.findElement(By.name("commit")).click();
	    String text = driver.findElement(By.xpath("//code")).getText();
	    
	    assertEquals(true, text.contains("\"a\""));
	    text = snip(text, "\"a\"");
	    
	    assertEquals(true, text.contains("\"=\""));
	    text = snip(text, "\"=\"");
	    
	    assertEquals(true, text.contains("\"10\""));
	    text = snip(text, "\"10\"");
	    	    
	}
	
	//Test finds whether or not input "a = 10" is fully tokenized.
	//Output after tokenizing the code should show correct identifiers.
	//For example, a tokenized variable "a" should show in output as "on_ident, "a""
	// Tokens in this test include "a", "=", "10", "puts", and "a" again.
	//Space characters and new lines are also seached for
	@Test
	public void US1TC2() {
			
			
		driver.findElement(By.id("code_code")).clear();
	    driver.findElement(By.id("code_code")).sendKeys("a = 10\n puts a");
	    driver.findElement(By.name("commit")).click();
	    String text = driver.findElement(By.xpath("//code")).getText();
		    
	    assertEquals(true, text.contains("on_ident, \"a\""));
	    text = snip(text, "on_ident, \"a\"");
	    
	    assertEquals(true, text.contains("on_sp, \" \""));
	    text = snip(text, "on_sp, \" \"");
		    
	    assertEquals(true, text.contains("on_op, \"=\""));
	    text = snip(text, "on_op, \"=\"");
	    
	    assertEquals(true, text.contains("on_sp, \" \""));
	    text = snip(text, "on_sp, \" \"");
		    
	    assertEquals(true, text.contains("on_int, \"10\""));
	    text = snip(text, "on_int, \"10\"");
	    
	    assertEquals(true, text.contains(":on_nl, \"\\r\\n\""));
	    text = snip(text, ":on_nl, \"\\r\\n\"");
	    
	    
	    assertEquals(true, text.contains("on_ident, \"puts\""));
	    text = snip(text, "on_ident, \"puts\"");
	    
	    assertEquals(true, text.contains("on_sp, \" \""));
	    text = snip(text, "on_sp, \" \"");
	    
	    assertEquals(true, text.contains("on_ident, \"a\""));
	    text = snip(text, "on_ident, \"a\"");
		    	    
	    
	}
	
	//Test finds whether or not the operands in "a = 1 + 2 - 3 * 4 / 5" fully tokenized.
	//Output after tokenizing the code should show correct identifiers.
	// Tokens in this test include "a", "=", "-", "*", "/" and "+".
	
	@Test
	public void US1TC3() {
			
			
		driver.findElement(By.id("code_code")).clear();
	    driver.findElement(By.id("code_code")).sendKeys("a = 1 + 2 - 3 * 4 / 5  ");
	    driver.findElement(By.name("commit")).click();
	    String text = driver.findElement(By.xpath("//code")).getText();
		    
	    assertEquals(true, text.contains("on_ident, \"a\""));
	    text = snip(text, "on_ident, \"a\"");
	    
	    assertEquals(true, text.contains("on_op, \"=\""));
	    text = snip(text, "on_op, \"=\"");
	    
	    assertEquals(true, text.contains("on_op, \"+\""));
	    text = snip(text, "on_op, \"+\"");
	    
	    assertEquals(true, text.contains("on_op, \"-\""));
	    text = snip(text, "on_op, \"-\"");
	    
	    assertEquals(true, text.contains("on_op, \"*\""));
	    text = snip(text, "on_op, \"*\"");
	    
	    assertEquals(true, text.contains("on_op, \"/\""));
	    text = snip(text, "on_op, \"/\"");
		    	    
		    
		}
	
	//Test finds whether or not input "brandon = "lockridge"" is fully tokenized.
	//The output after tokenizing the code should showthe code has been correctly tokenized
	//This is accomplished by examining whether or not the out put string contains these tokens
	//and in this case, the string contained is correctly tokenized in a format similar to:
	// :on_tstring_beg, "\""]
	// :on_tstring_content, "lockridge"]
	// :on_tstring_end, "\""] 
	@Test
	public void US1TC4() {
			
			
		driver.findElement(By.id("code_code")).clear();
	    driver.findElement(By.id("code_code")).sendKeys("brandon = \"lockridge\"");
	    driver.findElement(By.name("commit")).click();
	    String text = driver.findElement(By.xpath("//code")).getText();
		    
	    assertEquals(true, text.contains(":on_tstring_beg"));
	    text = snip(text, ":on_tstring_beg");
	    
	    assertEquals(true, text.contains("on_tstring_content, \"lockridge\""));
	    text = snip(text, "on_tstring_content, \"lockridge\"");
	    
	    assertEquals(true, text.contains(":on_tstring_end"));
	    text = snip(text, ":on_tstring_end");
		    	    
		}
	
	//Test shows the code entered is correctly parsed in the variable field
	//in this case we are testing for a variable and an integer
	//The field should contain identifier "a" and integer "10"
	@Test
	public void US2TC1() {
			
		driver.findElement(By.id("code_code")).clear();
	    driver.findElement(By.id("code_code")).sendKeys("a = 10");
	    driver.findElement(By.xpath("(//input[@name='commit'])[2]")).click();
	    
	    String text1 = driver.findElement(By.xpath("//code")).getText();
	   	    
	    assertEquals(true, text1.contains("@ident, \"a\""));
	    assertEquals(true, text1.contains("@int, \"10\""));
	    
	}
	
	//Test shows the code entered is correctly parsed in the program field(below the variable field
	//in this case we are testing for a variable and an integer
	//The field should contain identifier "a" and integer "10"
	@Test
	public void US2TC2() {
				
		driver.findElement(By.id("code_code")).clear();
	    driver.findElement(By.id("code_code")).sendKeys("a = 10");
	    driver.findElement(By.xpath("(//input[@name='commit'])[2]")).click();
		    
	   
	    String text = driver.findElement(By.xpath("//p[2]")).getText(); 
	    
	    assertEquals(true, text.contains("----@ident\n----a"));
	    assertEquals(true, text.contains("---@int\n---10"));
		    
		}
	
	//Test shows the code entered is correctly parsed in the program field(below the variable field
	//in this case we are testing for a variable and a string
	//The field should contain identifier "brandon" and string "lockridge"
	@Test
	public void US2TC3() {
				
		driver.findElement(By.id("code_code")).clear();
	    driver.findElement(By.id("code_code")).sendKeys("brandon = \"lockridge\"");
	    driver.findElement(By.xpath("(//input[@name='commit'])[2]")).click();
		    
	   
	    String text = driver.findElement(By.xpath("//p[2]")).getText(); 
	    
	    assertEquals(true, text.contains("----@ident\n----brandon"));
	    assertEquals(true, text.contains("-----@tstring_content\n-----lockridge"));
		    
		}
	
	  
	
	  //Test determines if the code compiled correctly and checks for correct variable names,
	  //and correct interger values for storing ints into the variables a, b, and c
	  //this is done by searching for the putobject code
	  @Test
	  public void US3TC1() throws Exception {
	
		driver.findElement(By.id("code_code")).clear();
	    driver.findElement(By.id("code_code")).sendKeys("a = 10\nb = 20\nc = 30");
	    driver.findElement(By.xpath("(//input[@name='commit'])[3]")).click();
	    String text = driver.findElement(By.xpath("//p")).getText(); 
	    
	    assertEquals(false, text.contains("Could not compile code - Syntax error"));
	    assertEquals(true, text.contains("putobject 10"));
	    assertEquals(true, text.contains("putobject 20"));
	    assertEquals(true, text.contains("putobject 30"));
	    assertEquals(true, text.contains(" a "));
	    assertEquals(true, text.contains(" b "));
	    assertEquals(true, text.contains(" c"));//no end space in c because it is the last variable declared
	    
	    	    
	  }
	  
	//Test determines if the code compiled correctly and checks for correct variable names,
	//and correct string values for storing string into the variables brandon and monica
	//this is done by searching for the putstring code
	@Test
	public void US3TC2() throws Exception {
	
	   driver.findElement(By.id("code_code")).clear();
	   driver.findElement(By.id("code_code")).sendKeys("brandon = \"lockridge\"\nmonica = \"bell\"");
	   driver.findElement(By.xpath("(//input[@name='commit'])[3]")).click();
	   String text = driver.findElement(By.xpath("//p")).getText(); 
	    
	    assertEquals(false, text.contains("Could not compile code - Syntax error"));
	    assertEquals(true, text.contains("putstring \"bell\""));
	    assertEquals(true, text.contains("putstring \"lockridge\""));
	    assertEquals(true, text.contains(" brandon "));
	    assertEquals(true, text.contains(" monica"));//no end space in monica because it is the last variable declared
	      
	  }
	  
	  //*****************************************************************************************
	  //Test peculiar case: size of the args seems to show the number of variable +1 
	  //Right now this test is failing because though we have one vaiable a, the size here is 2, 
	  //while the tester feels it should be 1
	  //Test conditions may change pending talk with developers
	  //*****************************************************************************************
	  @Test
	  public void US3TC3() throws Exception {
	
		driver.findElement(By.id("code_code")).clear();
	    driver.findElement(By.id("code_code")).sendKeys("a = 10\nb = 20\nc = 30");
	    driver.findElement(By.xpath("(//input[@name='commit'])[3]")).click();
	    String text = driver.findElement(By.xpath("//p")).getText(); 
	    
	    assertEquals(false, text.contains("Could not compile code - Syntax error"));
	    assertEquals(true, text.contains("putobject 10"));
	    assertEquals(true, text.contains(" a"));//no end space in a because it is the last variable declared
	    assertEquals(true, text.contains("size: 1"));
	    
	    
	    	    
	  }
	  
	
	//Method used to snip the code to help find multiple instances of code
	//snip returns a string from a snippet without the phrase we search for
	//EX: thing1 = "Hello World!", thing2 = "Hello"
	//snip(thing1, thing2) will return " World!"
	public String snip(String snippet, String find){
		int start = snippet.indexOf(find);
		String thing1 = snippet.substring(0, start);
		String thing2 = snippet.substring((start + find.length()));
		String thing = thing1 + thing2;
		return thing;		
		
	}
	
}