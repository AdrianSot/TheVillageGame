# TheVillageGame
## System summary
In this implementation of The Game, you start only with the Village Hall. You start
with a guard time of one minute, after that you can get attacked. Successful attacks
to your village make you lose resources but place you in a guard time of 10 minutes.
Habitants can be created and only require resources. For the creation of buildings,
resources are needed and at least one worker needs to be available. Creations or
upgrades have a waiting time, and in this time, the entity in this process is
unavailable. The workers involved in the creation or upgrade of buildings also enter a
waiting time until the work is finished. Production Buildings if not on creation or
upgrading, are always producing until they are full, also they can only be collected
when full and require a collector to do so. After collecting from a Production Building,
this building restarts its production and the collector enters a waiting time which
represents moving the resources, upgrading collectors reduces this time. Upgrading
Village Hall grows the maximum population. All entities can be upgraded and their
stats get doubled. You can view enemy villages with similar structure and levels, and
decide which one to fight. If you win the fight so you get some part of their resources,
proportion depending on the results. The game gives 200 in all resources just to
make all testing easier. The player can also test the defense of his village against
generated attacking armies.
## How to run the system
To start the server:
Run the jar file: java -jar TheVillage.jar <port number>
To run a client:
Compile file GameClient.java inside folder main.
To start client run command: java GameClient.java <host name> <port number>
