package pt.com.broker.client.nio;


import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import org.caudexorigo.text.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.com.broker.client.nio.bootstrap.Bootstrap;
import pt.com.broker.client.nio.codecs.BrokerMessageDecoder;
import pt.com.broker.client.nio.codecs.BrokerMessageEncoder;
import pt.com.broker.client.nio.consumer.BrokerAsyncConsumer;
import pt.com.broker.client.nio.consumer.ConsumerManager;
import pt.com.broker.client.nio.consumer.PongConsumerManager;
import pt.com.broker.client.nio.events.BrokerListener;
import pt.com.broker.client.nio.future.ConnectFuture;
import pt.com.broker.client.nio.handlers.ReceiveMessageHandler;
import pt.com.broker.client.nio.utils.HostContainer;
import pt.com.broker.types.*;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.Future;

/**
 * Created by luissantos on 21-04-2014.
 */
public class BrokerClient extends BaseClient {

    private static final Logger log = LoggerFactory.getLogger(BrokerClient.class);


    private Bootstrap bootstrap;

    private ChannelFuture future;

    private NetProtocolType protocolType;

    private ConsumerManager consumerManager = new ConsumerManager();

    private PongConsumerManager pongConsumerManager = new PongConsumerManager();

    protected HostContainer hosts;


    public BrokerClient(NetProtocolType ptype) {

        setProtocolType(ptype);


        setBootstrap(new Bootstrap(ptype, consumerManager, pongConsumerManager,isOldframing()));


        hosts = new HostContainer(getBootstrap());
    }

    public BrokerClient(String host, int port) {

        this(new HostInfo(host, port), NetProtocolType.JSON);


    }

    public BrokerClient(String host, int port, NetProtocolType ptype) {

        this(new HostInfo(host, port), ptype);


    }

    public BrokerClient(HostInfo host, NetProtocolType ptype) {

        this(ptype);

        hosts.add(host);
    }


    public Future<HostInfo> connect() {

        return hosts.connect();

    }

    public void addServer(HostInfo host) {
        getHosts().add(host);
    }

    public void addServer(String hostname, int port) {

        this.addServer(new HostInfo(hostname, port));
    }


    public ChannelFuture publishMessage(String brokerMessage, String destinationName,NetAction.DestinationType dtype) {

        return publishMessage(brokerMessage.getBytes(), destinationName, dtype);
    }

    public ChannelFuture publishMessage(byte[] brokerMessage, String destinationName , NetAction.DestinationType dtype) {

        NetBrokerMessage msg = new NetBrokerMessage(brokerMessage);

        return publishMessage(msg, destinationName, dtype);
    }

    public ChannelFuture publishMessage(NetBrokerMessage brokerMessage, String destination, NetAction.DestinationType dtype) {

        if ((brokerMessage == null) || StringUtils.isBlank(destination)) {
            throw new IllegalArgumentException("Mal-formed Enqueue request");
        }


        NetPublish publish = new NetPublish(destination, dtype, brokerMessage);

        NetAction action = new NetAction(NetAction.ActionType.PUBLISH);

        action.setPublishMessage(publish);

        return sendNetMessage(new NetMessage(action, brokerMessage.getHeaders()));

    }


    public ChannelFuture subscribe(NetSubscribe subscribe, BrokerListener listener) {

        getConsumerManager().addSubscription(subscribe, listener);

        log.info("Created new async consumer for '{}'", subscribe.getDestination());

        listener.setBrokerClient(this);

        NetAction netAction = new NetAction(NetAction.ActionType.SUBSCRIBE);
        netAction.setSubscribeMessage(subscribe);

        NetMessage netMessage = buildMessage(netAction, subscribe.getHeaders());

        return sendNetMessage(netMessage);
    }

    public ChannelFuture acknowledge(NetNotification notification) throws Throwable {


        return  this.acknowledge(notification,null);

    }

    public ChannelFuture acknowledge(NetNotification notification, Channel channel) throws Throwable {
        /* there is no acknowledge action for topics  */
        if (notification.getDestinationType() == NetAction.DestinationType.TOPIC) {
            return null;
        }

        if ((notification == null) || (notification.getMessage() == null) || StringUtils.isBlank(notification.getMessage().getMessageId())) {
            throw new IllegalArgumentException("Can't acknowledge invalid message.");
        }


        NetBrokerMessage brkMsg = notification.getMessage();
        String ackDestination = notification.getSubscription();

        String msgid = brkMsg.getMessageId();

        String[] parts = msgid.split("#",2);

        if(parts.length > 1){
            msgid = parts[1];
        }

        NetAcknowledge ackMsg = new NetAcknowledge(ackDestination, msgid);

        NetAction action = new NetAction(NetAction.ActionType.ACKNOWLEDGE);
        action.setAcknowledgeMessage(ackMsg);

        NetMessage msg = buildMessage(action);

        return sendNetMessage(msg,channel);

    }


    public ChannelFuture checkStatus(BrokerListener listener) throws Throwable {



        NetProtocolType type = getProtocolType();


        String actionId = UUID.randomUUID().toString();


        NetPing ping = new NetPing(actionId);

        pongConsumerManager.addSubscription(ping,listener);

        NetAction action = new NetAction(ping);
        action.setPingMessage(ping);

        NetMessage message = buildMessage(action);


        ChannelFuture f = sendNetMessage(message);


        EventLoopGroup group = getBootstrap().getBootstrap().group();



        return f;
    }




    public Bootstrap getBootstrap() {
        return bootstrap;
    }

    public void setBootstrap(Bootstrap bootstrap) {
        this.bootstrap = bootstrap;
    }



    public ChannelFuture getFuture() {
        return future;
    }

    public void setFuture(ChannelFuture future) {
        this.future = future;
    }

    public NetProtocolType getProtocolType() {
        return protocolType;
    }

    public void setProtocolType(NetProtocolType protocolType) {
        this.protocolType = protocolType;
    }


    public ConsumerManager getConsumerManager() {
        return consumerManager;
    }

    public void setConsumerManager(ConsumerManager consumerManager) {
        this.consumerManager = consumerManager;
    }

    public HostContainer getHosts() {
        return hosts;
    }


}

