package Sim;

// An example of how to build a topology and starting the simulation engine

public class Run {
	public static void main (String [] args)
	{
 		//Creates two links
//		Link link1 = new Link();
//		Link link2 = new Link();
 		Link link1 = new LossyLink(5,0,0);
		Link link2 = new LossyLink(5,0,0);
		
		// Create two end hosts that will be
		// communicating via the router
		Node host1 = new Node(1,1);
		Node host2 = new Node(2,1);

		//Connect links to hosts
		host1.setPeer(link1);
		host2.setPeer(link2);

		// Creates as router and connect
		// links to it. Information about 
		// the host connected to the other
		// side of the link is also provided
		// Note. A switch is created in same way using the Switch class
		Router routeNode = new Router(2);
		routeNode.connectInterface(0, link1, host1);
		routeNode.connectInterface(1, link2, host2);
		
		// Generate some traffic
		// host1 will send 3 messages with time interval 5 to network 2, node 1. Sequence starts with number 1
		host1.StartSendingGaussian(2, 2, 10, 5, 1);
		// host2 will send 2 messages with time interval 10 to network 1, node 1. Sequence starts with number 10
		host2.StartSendingPoisson(1, 1, 10, 10, 10, 2);
		
		// Start the simulation engine and of we go!
		Thread t=new Thread(SimEngine.instance());
	
		t.start();
		try
		{
			t.join();
			System.out.println("************************************************************");
			System.out.println("Node 1.1 sent a total of "+host1.getMessagesSent()+" messages");
			System.out.println("Node 1.1 received a total of "+host1.getMessagesReceived()+" messages");
			System.out.println("Node 2.1 sent a total of "+host2.getMessagesSent()+" messages");
			System.out.println("Node 2.1 received a total of "+host2.getMessagesReceived()+" messages");
			System.out.println("**********************************************************************");
		}
		catch (Exception e)
		{
			System.out.println("The motor seems to have a problem, time for service?");
		}
	}
}
