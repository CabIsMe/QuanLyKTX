//package root.quanlyktx.rabbitmq;
//
//import org.springframework.beans.factory.annotation.Autowired;
//
//public class MessageSender {
//    @Autowired
//    private RabbitTemplate rabbitTemplate;
//
//    public void send(MyMessage message) {
//        rabbitTemplate.convertAndSend("myexchange", "myroutingkey", message);
//    }
//
//}
