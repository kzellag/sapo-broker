package pt.com.broker.client.nio.bootstrap;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import pt.com.broker.client.nio.codecs.oldframing.BrokerMessageEncoder;
import pt.com.broker.types.BindingSerializer;
import pt.com.broker.types.NetProtocolType;

/**
 * Created by luissantos on 06-05-2014.
 *
 * @author vagrant
 * @version $Id: $Id
 */
public class DatagramChannelInitializer extends BaseChannelInitializer
{

	/**
	 * <p>
	 * Constructor for DatagramChannelInitializer.
	 * </p>
	 *
	 * @param serializer
	 *            a {@link pt.com.broker.types.BindingSerializer} object.
	 */
	public DatagramChannelInitializer(BindingSerializer serializer)
	{
		super(serializer);

		if (!(serializer.getProtocolType().equals(NetProtocolType.PROTOCOL_BUFFER) || serializer.getProtocolType().equals(NetProtocolType.THRIFT)))
		{
			log.warn("Using non-binary encoding with datagram transport will add some overhead ");
		}
	}

	/** {@inheritDoc} */
	@Override
	protected void initChannel(Channel ch) throws Exception
	{

		super.initChannel(ch);

		ChannelHandler encoder = ch.pipeline().get("broker_message_encoder");
		if (encoder instanceof BrokerMessageEncoder)
		{
			((BrokerMessageEncoder) encoder).setUseFrame(false);
		}

	}

}
