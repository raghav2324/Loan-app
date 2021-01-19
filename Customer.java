package comp6411;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Customer implements Runnable {
    
	String bank_name;
	int bank_funds;
	Bank bank;
	int count=0;
	
	HashMap<String, Integer> randomBank;
	
	public Customer(Bank bank)
	{this.bank=bank;}
	
	HashMap<String,Integer> customer_hm=new HashMap<>();
	HashMap<String,Integer> customer_hm1=new HashMap<>();
	
	public HashMap<String, Integer> getCustomer_hm() {
		return customer_hm;}
	
	public void setCustomer_hm(HashMap<String, Integer> customer_hm) {
		this.customer_hm=customer_hm;}
	
	public void setCustomer_hm1(HashMap<String, Integer> customer_hm1) {
		this.customer_hm1=customer_hm1;}
	
    public void run() {
    	
    	String bankName = null;
		int bankFunds = 0;
		int temp2;
		ArrayList<String> rejectedBanks=new ArrayList<>();
		
    	while(true) {
    		
      		try {Thread.sleep(100);}
    		catch (InterruptedException e) {e.printStackTrace();}
    		
    	    Random random=new Random();
    	
    	    int loanRequired=random.nextInt(50);
    		
    		if(loanRequired>customer_hm.get(Thread.currentThread().getName())) {loanRequired=customer_hm.get(Thread.currentThread().getName());}
    		if(customer_hm.get(Thread.currentThread().getName())<0) {Thread.currentThread().stop();break;}
    		
    		HashMap<String,Integer> randombank=bank.getRandomBank();
    		for (HashMap.Entry<String,Integer> hm : randombank.entrySet())  
    		{
    		bank_name=bankName=hm.getKey();
    		bank_funds=bankFunds=hm.getValue();}
    		
    		if(rejectedBanks.size()==bank.bank_hm.size()){break;}
    		
    		if(!rejectedBanks.contains(bankName)) {
    		
    		System.out.println(Thread.currentThread().getName()+" requests a loan of "+loanRequired +" dollar(s) from "+ bankName +":"+bank.bank_hm.get(bankName));
    		
    		if(loanRequired>bank.bank_hm.get(bankName))//here it is picking old value.s
    		{
    			System.out.println(bankName+" denies the loan request of "+loanRequired+" dollar(s) from "+Thread.currentThread().getName());
    			rejectedBanks.add(bankName);
    		}
    		else
			{
    		  synchronized(this) {
    			//loanRequest(loanRequired);}
    		  int amount=bank.bank_hm.get(bankName)-loanRequired;
    		  bank.bank_hm.replace(bankName, bank.bank_hm.get(bankName), amount);
    				
    		  int temp=customer_hm.get(Thread.currentThread().getName())-loanRequired;
    		  customer_hm.replace(Thread.currentThread().getName(),customer_hm.get(Thread.currentThread().getName()),temp);
    			
    		  if(temp==0)
    		  {	System.out.println(bank_name+" approves the loan request of "+loanRequired+" dollar(s) from "+Thread.currentThread().getName());
    		    //System.out.println(Thread.currentThread().getName()+" reached the objective.");
    		    break;}
    		  }
    				
    		  System.out.println(bankName+" approves the loan request of "+loanRequired+" dollar(s) from "+Thread.currentThread().getName());
    	      //System.out.println(bank.bank_hm);
    		  //System.out.println(customer_hm);
    		  }
    		}
    	}
    	System.out.println();
    	count++;
    	
    	if(count==customer_hm.size())
    	{
    		for (HashMap.Entry<String,Integer> hm : customer_hm.entrySet())  
            {
    			if(hm.getValue()==0)
    			{
    			System.out.println(hm.getKey() + " has reached the objective of " + customer_hm1.get(hm.getKey())+" dollar(s). Woo Hoo!");
    			}
    			else 
    			{
    			System.out.println(hm.getKey() + " was only able to borrow " + (customer_hm1.get(hm.getKey())-customer_hm.get(hm.getKey())) + " dollar(s). Boo Hoo!");	
    			}
    		}
    		
    		for(HashMap.Entry<String,Integer> hm : bank.bank_hm.entrySet())
    		{
    			System.out.println(hm.getKey()+" has only "+hm.getValue()+" dollar(s) remaining.");
    		}
    		
    	}
    	
    	Thread.currentThread().stop();
    	
      }
    
    
	/*public synchronized void loanRequest(int loanRequired)
	{		
		try 
		{Thread.sleep(100);}
		catch (InterruptedException e) {e.printStackTrace();}
		
			
			int amount=bank.bank_hm.get(bank_name)-loanRequired;
			bank.bank_hm.replace(bank_name, bank.bank_hm.get(bank_name), amount);
			
			int temp=customer_hm.get(Thread.currentThread().getName())-loanRequired;
			customer_hm.replace(Thread.currentThread().getName(),customer_hm.get(Thread.currentThread().getName()),temp);
		
			if(temp==0)
			{	System.out.println(bank_name+" approves the loan request of "+loanRequired+" dollar(s) from "+Thread.currentThread().getName());
				System.out.println(Thread.currentThread().getName()+" reached the objective.");Thread.currentThread().stop();}
			}
			
*/
}

