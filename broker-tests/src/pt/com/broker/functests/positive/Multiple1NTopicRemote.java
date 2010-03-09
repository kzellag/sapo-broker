package pt.com.broker.functests.positive;

import pt.com.broker.client.BrokerClient;
import pt.com.broker.functests.conf.ConfigurationInfo;
import pt.com.broker.functests.helpers.MultipleGenericPubSubTest;
import pt.com.broker.functests.helpers.MultipleNotificationsBrokerListener;
import pt.com.broker.functests.helpers.MultipleGenericPubSubTest.TestClientInfo;

public class Multiple1NTopicRemote extends MultipleGenericPubSubTest
{
	public Multiple1NTopicRemote()
	{
		super("Topic - 1 producer N consumers remote");
	}

	@Override
	protected void addConsumers()
	{
		super.addConsumers();
		try
		{
			TestClientInfo tci = new TestClientInfo();

			tci.brokerClient = new BrokerClient(ConfigurationInfo.getParameter("agent2-host"), 
					Integer.parseInt(ConfigurationInfo.getParameter("agent2-port")), "tcp://mycompany.com/test");
			tci.brokerListenter = new MultipleNotificationsBrokerListener(getDestinationType(), 1);
			tci.numberOfExecutions = getInfoProducers().size();

			this.addInfoConsumer(tci);
		}
		catch (Throwable t)
		{
			this.setFailure(t);
		}
	}
}
