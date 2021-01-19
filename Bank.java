package comp6411;

import java.util.HashMap;
import java.util.Random;

public class Bank {
	
	public HashMap<String,Integer> bank_hm=new HashMap<>();
	
	
    public void setBank_hm(HashMap<String, Integer> bank_hm) {
		this.bank_hm = bank_hm;
	}

	public HashMap<String, Integer> getBank_hm() {
		return bank_hm;
	}
		
	public HashMap<String,Integer> getRandomBank()
	{
		HashMap<String,Integer> hmm=new HashMap<>();
		
		Random random=new Random();
		
		Object[] setOfKeys=bank_hm.keySet().toArray();
		String randomKey= (String) setOfKeys[random.nextInt(setOfKeys.length)];
		hmm.put(randomKey, bank_hm.get(randomKey));
		//System.out.println(hmm);
		return hmm;
	}

	/*public synchronized void sanctionLoan(String bankName,int loanRequired)
	{
		System.out.println("bankName:"+bankName);
		System.out.println("loanRequired:"+loanRequired);
		System.out.println(bank_hm);
		System.out.println(bank_hm.get(bankName));//problem
		int amount=bank_hm.get(bankName)-loanRequired;
		System.out.println(amount+":amount");
		bank_hm.replace(bankName, amount);
	}*/
	

	
	
	
}
