################# README ##########################
#
#	Auteur	: Svenn D'Hert
###################################################

################# INSTALL #########################
#
#	Install;
#		1) execute sql.txt (MySQL)
#		2) change in the project db->connector.java
#		   the following lines
#
####### FIND & REPLACE

String connectionUrl = "jdbc:mysql://YOURURL:YOURPORT/YOURDB";
con = DriverManager.getConnection(connectionUrl, "USER", "PASW");

#
#
#		3) execute & enjoy