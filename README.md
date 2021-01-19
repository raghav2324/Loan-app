# Loan-app
The	objective	is	to	model	a	simple	banking	environment.	Specifically,	There will	be	given	a	small	
number	of	customers,	each	of	whom will	contact	a	set	of	banks	to	request	a	number	of	loans.	
Eventually,	they	will	either	receive	all	of	the	money	they	require or	they	will	end	up	without	
completely	meeting	their	original	objective.	The	application	will	display	information	about	the	
various	banking	transactions	before	it	finishes.


Data	of	customers	and	banks	be supplied	in	a	pair	of	very	simple	text	files	– customers.txt and	banks.txt.	While	Erlang	provides	many	
file	primitives	for	processing disk	files,	the	process is	not	quite	as	simple	as	Clojure’s	slurp()	
function.	So	the	two	files	will contain	records	that	are	already	pre-formatted.	In	other	words,	they	
are ready	to	be	read	directly into	standard	Erlang	data	structures.	

An example	customers.txt file	would	be:
{jill,450}.
{joe,157}.
{bob,100}.
{sue,125}.

An	example	banks.txt file	would	be:
{rbc,800}.
{bmo,700}.
{ing,200}.
