# Spring_Study




## 모노리틱 아키텍쳐(Monolithic Architecture)
<p>
  <img src="https://user-images.githubusercontent.com/19161231/54512207-c923cf00-4996-11e9-8b40-727eecd07b33.png" width="25%" align="left"/>
  <p>
  모노리틱 아키텍쳐는 전통적인 웹 시스템 개발 스타일의 아키텍쳐로 하나의 어플리케이션 내부에 모든것이 들어가 있는 '통짜구조' 이다.</br>
  </br>
  <ul>
    <li>장점 : 규모가 작은 애플리케이션에서는 배포가 용이하며 운영 및 관리가 상대적으로 쉽다.</li>
    <li>단점 : 통으로 돌아가기 때문에 크기가 크고, 빌드 및 배포 시간, 서버의 기동 시간이 오래 걸린다.</li>
  </ul>
  </p>
</p>
<br clear="left">
</br></br>

## 마이크로 서비스 아키텍쳐(Micro Service Architecture, MSA)

<p>
  <img src="https://user-images.githubusercontent.com/19161231/54512218-cd4fec80-4996-11e9-8ec9-6d5d6f92e064.png" width="50%"  align="left"/>
  <p>
  각각의 서비스를 API형태로 호출하며 각각 독립적인 war 파일로 개발되며 각각의 Tomcat 인스턴스위에서 동작한다.</br>
  애플리케이션 로직을 분리해서 여러개의 애플리케이션으로 나눠서 서비스화하고, 각 서비스별로 톰캣을 분산 배치한 것이 핵심</br>
  </br>
  <ul>
    <li>장점 : 서비스를 독립적으로 개발/배포/운영가능</li>
    <li>단점 : 데이터를 API 호출로만 통신하기 때문에 성능상 문제 발생 가능성 있음.</li>
  </ul>
  </p>
</p>
<br clear="left">
</br></br>

## 클라우드 기반 컴퓨팅 모델

- IaaS(Infrastructure as a service)
- PaaS(Platform as a Service)
- SaaS(Software as a Service)
![image](https://user-images.githubusercontent.com/19161231/54671593-8ea66780-4b39-11e9-80f9-eb5e25a5f4ae.png)



## Redis

Redis는 In-memory 기반의 NoSQL DBMS로서 Key-Value의 구조를 가지고있다.
또한 속도가 빠르고사용이 간편하다. 캐싱,세션관리,pub/sub 메시징 처리등에 사용된다.

Spring에서 Redis를 사용하기위한 라이브러리는 2가지가있다.
 - jedis
 - lettuce
 
 jedis는 thread-safe하지 않기 때문에  jedis-pool을 사용해야하지만 비용이 증가하기 때문에 lettuce를 많이 사용한다.
 
 ```
 <dependency>
     <groupId>org.springframework.boot</groupId>
     <artifactId>spring-boot-starter-data-redis</artifactId>
 </dependency>
 ```
 
 ```java
@Configuration
public class RedisConfiguration {
    
    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory();
        return lettuceConnectionFactory;
    }
    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory());
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        return redisTemplate;
    }
 
}
```

`ettuceConnectionFactory.setHost("192.168.0.78")` 및 ` .setPassword("password");`를 이용해 설정할 수 있으나 
 application.properties나 .yml 파일에 설정하는것을 권장한다.
 
 ```
 spring.redis.lettuce.pool.max-active=10
 spring.redis.lettuce.pool.max-idle=10
 spring.redis.lettuce.pool.min-idle=2
 spring.redis.port=6379
 spring.redis.host=127.0.0.1
 ```
 
 #### Redis 사용 예제
 ```java
@Service
public class GetSetService {
    
    @Autowired
    RedisTemplate<String, Object> redisTemplate;
    
    public void test() {
        //hashmap같은 key value 구조
        ValueOperations<String, Object> vop = redisTemplate.opsForValue();
        vop.set("jdkSerial", "jdk");
        String result = (String) vop.get("jdkSerial");
        System.out.println(result);//jdk
    }
}
```
 위와 같은 경우 직렬화를 하여 바이트로 저장하기 때문에 출력시 이상한 문자열이 출력 될 수 있다.
 json등을 이용하여 해결 가능하다.
 
 
 ```java
@Configuration
public class RedisConfiguration {

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory();
        return lettuceConnectionFactory;
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory());
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(MyData.class));
        return redisTemplate;
    }

}
```
위와 같이 `setValueSerializer()` 메서드를 사용하여 직렬화 를 설정 할 수 있다.

#### 여기서 `Jackson`이란?
`Jackson`은 text/html 형태의 문자가 아닌 객체등의 데이터를 JSON으로 처리해 주는 라이브러리 이다.
Jackson 외에 Google 에서 만든 `GSON`과 그밖에 `SimpleJSON` 등이 있다.
`Spring 3.0` 이후부터는 내부적으로 Jackson 관련 API 제공을 통하여 자동화 처리가 가능하도록 도와주었다.
 
 ##### Mydata.java
 ```java
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class MyData implements Serializable {
    private static final long serialVersionUID = -7353484588260422449L;
    private String studentId;
    private String name;
}
```
##### DataService.java
```java
@Service
public class DataService {
    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    public void test() {
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        MyData data = new MyData();
        data.setStudentId("1234566");
        data.setName("HongGilDong");
        valueOperations.set("key", data);

        MyData data2 = (MyData) valueOperations.get("key");
        System.out.println(data2);
    }
}
```

#### Redis Publish/Subscribe
##### RedisConfiguration.java
```java
@Configuration
public class RedisConfiguration {

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory();
        return lettuceConnectionFactory;
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory());
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(MyData.class));
        return redisTemplate;
    }

    @Bean
    MessageListenerAdapter messageListenerAdapter() {
        return new MessageListenerAdapter(new RedisService());
    }

    @Bean
    RedisMessageListenerContainer redisContainer() {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(redisConnectionFactory());
        container.addMessageListener(messageListenerAdapter(), topic());
        return container;
    }

    @Bean
    ChannelTopic topic() {
        return new ChannelTopic("Event");
    }
}
```
##### RedisService.java
```java
@Service
public class RedisService implements MessageListener {
    public static List<String> messageList = new ArrayList<String>();

    @Override
    public void onMessage(Message message, byte[] pattern) {
        messageList.add(message.toString());
        System.out.println("Message received: " + message.toString());
    }
}
```
Subscribe를 하기 위해 서비스를 만들고, 토픽을 추가시켜주어야 한다.
 
 
 ## Reference
 - https://woowabros.github.io/experience/2017/10/17/java-serialize2.html
 - https://jeong-pro.tistory.com/175