module HW08 {
	requires transitive java.desktop;
	requires java.rmi;
	exports common;
	exports provided.datapacket;
	exports provided.discovery;
	exports provided.rmiUtils;
	
//	requires org.junit.jupiter.api; 
}