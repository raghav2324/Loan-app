package comp6411;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Test {
		
	public static void main(String rv[]) throws Exception
	{
		FileReader fr1=new FileReader("bank.txt");
		BufferedReader br1=new BufferedReader(fr1);
		
		HashMap<String,Integer> bank_hm=new HashMap();
		
		String s;
		while((s=br1.readLine())!=null)
		{
			int i=s.indexOf(",");
			String bank_name=s.substring(1,i);
			int bank_funds=Integer.parseInt(s.substring(i+1, s.length()-2));
			bank_hm.put(bank_name, bank_funds);
		}
		
		
		FileReader fr2=new FileReader("customer.txt");
		BufferedReader br2=new BufferedReader(fr2);
		
		HashMap<String,Integer> customer_hm=new HashMap();
		HashMap<String,Integer> customer_hm1=new HashMap();
		
		String  r;
		while((r=br2.readLine())!=null)
		{
			int i=r.indexOf(",");
			String customer_name=r.substring(1,i);
			int customer_loan=Integer.parseInt(r.substring(i+1, r.length()-2));
			customer_hm.put(customer_name, customer_loan);
			customer_hm1.put(customer_name, customer_loan);
	    }
		
		
		
		Bank bank=new Bank();
		//System.out.println("in test:"+bank_hm);
		bank.setBank_hm(bank_hm);
		//System.out.println(bank.getBank_hm());
		
		System.out.println("** Customers and loan objectives **");
		for (HashMap.Entry<String,Integer> hm : customer_hm.entrySet())  
        {System.out.println(hm.getKey() + ": " + hm.getValue());}
		
		System.out.println();
		
		System.out.println("** Banks and financial resources **");
		for (HashMap.Entry<String,Integer> hm : bank_hm.entrySet())  
		{System.out.println(hm.getKey() + ": " + hm.getValue());} 
		
		System.out.println();
		  

		Customer customer = new Customer(bank);
		
		customer.setCustomer_hm(customer_hm);		
		customer.setCustomer_hm1(customer_hm1);
		
		Object[] setOfKeys=customer_hm.keySet().toArray();
		Thread[] t=new Thread[customer_hm.size()]; 
		for(int i=0;i<customer_hm.size();i++)
		{
			t[i]=new Thread(customer);
			t[i].setName((String) setOfKeys[i]);  
		}
		
		for(int i=0;i<t.length;i++) {t[i].start();}

	}
}	 



