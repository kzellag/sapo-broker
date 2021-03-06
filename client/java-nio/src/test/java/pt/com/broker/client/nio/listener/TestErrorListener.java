package pt.com.broker.client.nio.listener;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import io.netty.channel.embedded.EmbeddedChannel;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import pt.com.broker.client.nio.consumer.ConsumerManager;
import pt.com.broker.client.nio.events.ErrorListenerAdapter;
import pt.com.broker.client.nio.handlers.ReceiveFaultHandler;
import pt.com.broker.client.nio.server.HostInfo;
import pt.com.broker.client.nio.utils.ChannelDecorator;
import pt.com.broker.types.NetFault;
import pt.com.broker.types.NetMessage;

/**
 * Created by luissantos on 04-06-2014.
 */
@RunWith(MockitoJUnitRunner.class)
public class TestErrorListener
{

	@Mock
	ErrorListenerAdapter mockListener;

	@Mock
	ConsumerManager consumerManager;

	@Test()
	public void testHandlerCall() throws Throwable
	{

		ReceiveFaultHandler handler = new ReceiveFaultHandler(consumerManager);

		// mock creation

		handler.setFaultListenerAdapter(mockListener);

		NetMessage error = NetFault.AccessDeniedErrorMessage;

		EmbeddedChannel channel = new EmbeddedChannel(handler);

		ChannelDecorator decorator = new ChannelDecorator(channel);

		HostInfo host = new HostInfo("127.0.0.1", 3323);

		decorator.setHost(host);

		channel.writeInbound(error);

		verify(mockListener, times(1)).deliverMessage(error, host);
		verify(mockListener, times(1)).onMessage(error.getAction().getFaultMessage(), host);

		verifyZeroInteractions(consumerManager);

	}

	@Test()
	public void testHandlerPollFaults() throws Throwable
	{

		ReceiveFaultHandler handler = new ReceiveFaultHandler(consumerManager);

		handler.setFaultListenerAdapter(mockListener);

		EmbeddedChannel channel = new EmbeddedChannel(handler);

		ChannelDecorator decorator = new ChannelDecorator(channel);

		HostInfo host = new HostInfo("127.0.0.1", 3323);

		decorator.setHost(host);

		NetMessage error = NetFault.PollTimeoutErrorMessage;

		channel.writeInbound(error);

		NetMessage error2 = NetFault.NoMessageInQueueErrorMessage;

		channel.writeInbound(error2);

		verify(consumerManager, times(1)).deliverMessage(error, host);
		verify(consumerManager, times(1)).deliverMessage(error2, host);

		verifyZeroInteractions(mockListener);

	}

}
