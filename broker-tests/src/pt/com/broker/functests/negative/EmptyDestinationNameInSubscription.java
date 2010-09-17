package pt.com.broker.functests.negative;

import pt.com.broker.functests.helpers.GenericNetMessageNegativeTest;
import pt.com.broker.types.NetAction;
import pt.com.broker.types.NetMessage;
import pt.com.broker.types.NetSubscribe;
import pt.com.broker.types.NetAction.ActionType;
import pt.com.broker.types.NetAction.DestinationType;

public class EmptyDestinationNameInSubscription extends GenericNetMessageNegativeTest
{
	public EmptyDestinationNameInSubscription()
	{
		super("Empty destination name in subscription");

		NetSubscribe subscribe = new NetSubscribe("", DestinationType.TOPIC);
		NetAction action = new NetAction(ActionType.SUBSCRIBE);
		action.setSubscribeMessage(subscribe);
		NetMessage message = new NetMessage(action);
		setMessage(message);

		setFaultCode("2001");
		setFaultMessage("Invalid destination name");
	}
}
