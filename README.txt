################# README ##########################
#
#	Auteur	: Svenn D'Hert
###################################################

#
#
# You will need to download these packages in order to run
# the code : (seperate licenses are in order!)
#	JAchievement-1.0
#	jcommon-1.0.8
#	jfreechart-1.0.14
#	microba-0.4.4.3
#	mysql-connector-java-5.1.17-bin
#	timingframework-classic-1.1
#
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