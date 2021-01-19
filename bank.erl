%% @author Raghav
%% @doc @todo Add description to money.


-module(bank).

-export([bank/3]).

%% bank processes execcuted here


printVal(String,N,Master)->
	whereis(Master) ! {String,N,self()}.

bank({NamePerBank, QuantityPerBank},Master,CustLen) ->
	if CustLen>0 ->
		receive
		
		
		{customer, Information_Cus,RequestMoney,Customer_name} ->
			
			%%io:fwrite("~p : ~p ~n",[NamePerBank,Customer_name]),
			if 
				QuantityPerBank >= RequestMoney ->
					printVal("~p approves a loan of ~p dollars from  ~p~n",[NamePerBank,RequestMoney,Customer_name],Master),
					printVal("~p's remain: ~p ~n",[NamePerBank, QuantityPerBank - RequestMoney],Master),
					Information_Cus ! give_customer,
					bank({NamePerBank, QuantityPerBank - RequestMoney},Master,CustLen);
				
				true ->	
					printVal("~p denies a loan of ~p dollar(s) from ~p~n", [NamePerBank,RequestMoney,Customer_name],Master),
					Information_Cus ! nongive_customer,
					bank({NamePerBank, QuantityPerBank},Master,CustLen-1)
			end;
		{String,NamePerCustomer}->
			%%io:fwrite("~p:~p~n",[CustLen-1,NamePerCustomer]),
			bank({NamePerBank, QuantityPerBank},Master,CustLen-1)

	end	
		%%true->
			%%whereis(Master) ! {"~p has ~p dollar(s) remaining.~n", [NamePerBank,QuantityPerBank]}
	
	
	
	
	
end.