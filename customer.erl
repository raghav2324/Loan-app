
%% @author Raghav
%% @doc @todo Add description to money.



-module(customer).


-export([customer/4]).

-import(lists,[delete/2,nth/2]).

%% the following struct is to display the output if the customer has reached the objective.

printVal(String,N,Master)->
	whereis(Master) ! {String,N,self()}.

goBank([],NamePerCustomer)->ok;
goBank([Bank|BankInfo],NamePerCustomer)->
	nth(1,Bank) ! {"Remove",NamePerCustomer}.

customer({NamePerCustomer,CurrentTotalMoney}, BankInfo, Map,Master) when CurrentTotalMoney =< 0 ->
	C=maps:get(NamePerCustomer, Map),
	whereis(Master) ! {"~p has reached the objective of ~p dollar(s).Woo Hoo!~n", [NamePerCustomer,C]};
	

%% if customer has not reached the objective

customer({NamePerCustomer,CurrentTotalMoney}, [], Map,Master) ->
	C=maps:get(NamePerCustomer, Map),
	whereis(Master) ! {"~p was only able to borrow ~p dollar(s). Boo Hoo!~n", [NamePerCustomer,C-CurrentTotalMoney]};





customer({NamePerCustomer,CurrentTotalMoney}, BankInfo,Map,Master) ->
	timer:sleep(rand:uniform(1000)),
	RequestMoneyStore = rand:uniform(50),
	RequestBank_id = rand:uniform(length(BankInfo)),
	
	%% requirement Bank PID Setting
	
	BankPID = nth(RequestBank_id,BankInfo),
	
	%% if required money by the customer is greater than money with bank
	
	if 
		RequestMoneyStore > CurrentTotalMoney -> 
			RequestMoney = CurrentTotalMoney;
		
		%% if required money by the customer is smaller or equal to money with bank.
		
		true ->
			RequestMoney = RequestMoneyStore
	
	end,
			
	printVal("~p requests a loan of  ~p dollars from ~p~n ",[NamePerCustomer,RequestMoney,nth(2,BankPID)],Master),
	
	%% bank call statement
	nth(1,BankPID) ! {customer, self(),RequestMoney,NamePerCustomer},
	%% message passing from the bank
	receive
		give_customer ->
			customer({NamePerCustomer,CurrentTotalMoney - RequestMoney}, BankInfo,Map,Master);
		nongive_customer ->
			%%printVal("------------",[],Master)
			AnotherBankStore = delete(BankPID, BankInfo),
			%%nth(1,BankPID) ! {"Remove"},
			%%printVal("~p",[AnotherBankStore], Master),
			customer({NamePerCustomer, CurrentTotalMoney}, AnotherBankStore, Map,Master)
	end.	
	
	%% updating the cureent amount required by the customer
	
