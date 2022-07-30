package javaSpi;


import io.netty.handler.ssl.ApplicationProtocolConfig;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.ServiceLoader;
import java.util.stream.Collectors;

public class ProtocolFactory {

    public static Protocol getProtocol(){
        ServiceLoader<Protocol> load = ServiceLoader.load(Protocol.class);
        List<Field> collect = Arrays.stream(ServiceLoader.class.getDeclaredFields()).collect(Collectors.toList());
        collect.forEach(System.out::println);

        return load.iterator().next();
    }

    public static void main(String[] args) {
        getProtocol().go();
    }
}
