package pt.com.broker.functests.positive;

import pt.com.broker.client.nio.BrokerClient;
import pt.com.broker.functests.helpers.MultipleGenericQueuePubSubTest;
import pt.com.broker.types.NetProtocolType;

public class MultipleN1Queue extends MultipleGenericQueuePubSubTest
{

	public MultipleN1Queue(NetProtocolType protocolType)
	{
		super(protocolType);

		setName("Queue - N producers 1 consumer");
	}

	@Override
	protected void addProducers()
	{
		super.addProducers();
		try
		{
			TestClientInfo tci = new TestClientInfo();

			tci.brokerClient = new BrokerClient(getAgent1Hostname(), getAgent1Port(), getEncodingProtocolType());
			tci.brokerClient.connect();

			tci.brokerListenter = null;

			this.addInfoProducer(tci);
		}
		catch (Throwable t)
		{
			this.setFailure(t);
		}
	}
}
