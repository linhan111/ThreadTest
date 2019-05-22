Redis相关备忘：
    
    1、使用StringRedisTemplete操作在Spring中操作Redis，业务简单的情况下可代替RedisTemplete
    但StringRedisTemplete的构造方法写死了序列化key-value的方法，这一点有很大的限制，可以在注入StringRedisTemplete
    的时候将value的序列化器加入其中：https://blog.csdn.net/qq_17011423/article/details/79245261
    ，这里的注入方式为构造器注入，其中构造器注入与直接注入的区别：https://www.cnblogs.com/joemsu/p/7688307.html
    2、配置RedisTemplate序列化方式默认为jdk的，源码解析：https://blog.csdn.net/Abysscarry/article/details/80557347
    3、StringRedisTemplate和RedisTemplate都是集成了Spring集成Redis后操作redis数据的工具类。
      区别：StringRedisTemplate继承RedisTemplate
          StringReidsTemplate操作是key=String，value=String，而RedisTemplate可以操作任意类型的key-value
          StringRedisTemplate和RedisTemplate使用的序列化类不同，但是最终得到的都是字节数组。StringRedisTemplate使用的是StringRedisSerializer类；RedisTemplate使用的是JdkSerialzationRedisSerializer。反序列化时，StringRedisSerializer得到的是字符，JdkSerialzationRedisSerializer得到的是数组。