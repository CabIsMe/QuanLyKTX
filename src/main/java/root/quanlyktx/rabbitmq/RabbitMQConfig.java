//package root.quanlyktx.rabbitmq;
//
//
//import org.springframework.amqp.core.*;
//import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
//import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
//import org.springframework.amqp.rabbit.connection.ConnectionFactory;
//import org.springframework.amqp.rabbit.core.*;
//import org.springframework.amqp.rabbit.listener.ConditionalRejectingErrorHandler;
//import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.util.ErrorHandler;
//
//
//@Configuration
//public class RabbitMQConfig {
//    public static final String QUEUE="OTP_Queue";
//    public static final String EXCHANGE="QLKTX_Exchange";
//    public static final String ROUTING_KEY="QLKTX_RoutingKey";
//    @Value("${spring.rabbitmq.username}")
//    private String username;
//    @Value("${spring.rabbitmq.password}")
//    private String password;
//    @Value("${spring.rabbitmq.host}")
//    private String host;
//    @Value("${spring.rabbitmq.virtualhost}")
//    private String virtualHost;
//    @Value("${spring.rabbitmq.reply.timeout}")
//    private Integer replyTimeout;
//    @Value("${spring.rabbitmq.concurrent.consumers}")
//    private Integer concurrentConsumers;
//    @Value("${spring.rabbitmq.max.concurrent.consumers}")
//    private Integer maxConcurrentConsumers;
//    @Value("${spring.rabbitmq.port}")
//    private Integer port;
//
//    @Bean
//    public Queue queue() {
//
//        return new Queue(QUEUE, false);
//    }
//    @Bean
//    public TopicExchange  exchange() {
//        return new TopicExchange (EXCHANGE);
//    }
//    @Bean
//    public Binding binding(Queue queue, TopicExchange  exchange) {
//        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY);
//    }
//    @Bean
//    public ErrorHandler errorHandler() {
//        return new ConditionalRejectingErrorHandler();
//    }
//    @Bean
//    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory() {
//        final SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
//        factory.setConnectionFactory(connectionFactory());
//        factory.setMessageConverter(converter());
//        factory.setConcurrentConsumers(concurrentConsumers);
//        factory.setMaxConcurrentConsumers(maxConcurrentConsumers);
//        factory.setErrorHandler(errorHandler());
//        return factory;
//    }
//    @Bean
//    public ConnectionFactory connectionFactory() {
//        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
//        connectionFactory.setVirtualHost(virtualHost);
//        connectionFactory.setHost(host);
//        connectionFactory.setUsername(username);
//        connectionFactory.setPassword(password);
//        connectionFactory.setPort(port);
//        return connectionFactory;
//    }
//
//    @Bean
//    public Jackson2JsonMessageConverter converter(){
//        return new Jackson2JsonMessageConverter();
//    }
//    @Bean
//    public RabbitTemplate rabbitTemplate(Jackson2JsonMessageConverter converter){
////        CachingConnectionFactory cachingConnectionFactory= new CachingConnectionFactory();
//        final RabbitTemplate rabbitTemplate=new RabbitTemplate(connectionFactory());
//        rabbitTemplate.setDefaultReceiveQueue(QUEUE);
//        rabbitTemplate.setMessageConverter(converter);
//        rabbitTemplate.setReplyAddress(queue().getName());
//        rabbitTemplate.setReplyTimeout(replyTimeout);
//        rabbitTemplate.setMessageConverter(converter);
//        rabbitTemplate.setUseDirectReplyToContainer(false);
//        return rabbitTemplate;
//    }
//    @Bean
//    public AmqpAdmin amqpAdmin() {
//        return new RabbitAdmin(connectionFactory());
//    }
//}
