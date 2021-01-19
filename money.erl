%% @author Raghav
%% @doc @todo Add description to money.


-module(money).


-import(bank,[bank/3]).

-import(customer, [customer/4]).

-import(lists,[nth/2,append/2]).

-export([start/0,money/0,for/8,for/10]).


%% iterations for process id generation

for(0,_,_,_,_,_,_,_) -> 
	[]; 

for(N,InfCustomer,InfBank,InfoBank,LoopVar,Customer_hm,M,Master) when N > 0 ->
	
	spawn(customer, customer, [nth(N, InfCustomer), InfoBank, Customer_hm,Master]), 
	timer:sleep(500),
	[LoopVar|for(N-1,InfCustomer,InfBank,InfoBank,LoopVar,Customer_hm,M,Master)].


for(0,_,_,_,_,_,_,_,_,CustLen)->
	[];
for(N,InfBank, InfoBank,InfCustomer,S,LoopVar,Customer_hm,L,Master,CustLen) when  N > 0 ->
	I=rand:uniform(length(InfBank)),
	
	Request_Bank = spawn(bank, bank, [nth(N, InfBank),Master,CustLen]),
	
	AppendBankInfo = [[Request_Bank,nth(1,tuple_to_list(nth(N, InfBank)))]],
	BankAddInfo = append(InfoBank,AppendBankInfo),
	
	if
		N == 1 ->
			for(length(InfBank),InfCustomer,InfBank,BankAddInfo,1,Customer_hm,8,Master);				
		true ->
			io:fwrite("")
	
	end,
	(lists:append(for(N-1,InfBank,BankAddInfo,InfCustomer,S,LoopVar,Customer_hm,L,Master,CustLen),[Request_Bank])).


%% start method to run the money method.

start()->
	money().%% main function of the project

money()->
	
	register(master, self()),
	
	{ok, InfCustomer} = file:consult("customer.txt"),
	{ok, InfBank} = file:consult("bank.txt"),
	
	io:fwrite("** Customers and loan objectives **\n"),
	lists:foreach(fun({Key,Value}) -> io:format("~p : ~p\n", [Key,Value]) end,InfCustomer),
	Customer_hm	= maps:from_list(InfCustomer),
	io:fwrite("** Banks and financial resources **\n"),
	lists:foreach(fun({K,V}) -> io:format("~p : ~p\n", [K,V]) end,InfBank),
	
	for(length(InfBank),InfBank,[],InfCustomer,0,1,Customer_hm,8,master,length(InfCustomer)),
	%io:fwrite("BankPIDS ~p~n",BankPIDs),
	N=[length(InfCustomer),length(InfBank)],
	listen([],nth(1,N)),
	unregister(master).	


listen([],0)->ok;

listen([Row|CustList],0)->
	io:fwrite(nth(1,Row),nth(2,Row)),
	listen(CustList,0);

listen(CustList,Customers) ->
	%%io:fwrite("~p",[CustomersBanks]),
	receive
		{String,Arglist,BankPID} -> 
			io:fwrite(String,Arglist),
			BankPID ! {""},
			listen(CustList,Customers);
		{String,Arglist} -> 
			%%io:fwrite(String,Arglist),
			listen(lists:append(CustList, [[String,Arglist]]),Customers-1)
	end.

