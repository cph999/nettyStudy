package javaSpi;


import io.netty.handler.ssl.ApplicationProtocolConfig;

import java.util.ServiceLoader;

public class ProtocolFactory {

    public static Protocol getProtocol(){
        ServiceLoader<Protocol> load = ServiceLoader.load(Protocol.class);
        return load.iterator().next();
    }

    public static void main(String[] args) {
        getProtocol().go();
    }
}
