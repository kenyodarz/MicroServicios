package co.com.personalsoft.gateway.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Arrays;

@Component
public class PreElapsedTimeFilter implements GlobalFilter, Ordered {

    static final Logger LOGGER = LoggerFactory.getLogger(PreElapsedTimeFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        var request = exchange.getRequest();
        var initialTime = LocalTime.now();
        for (String s : Arrays.asList(String.format("[%s] request enrutado a %s",
                request.getMethod(), request.getURI()),
                "Global Pre Filter executed")) {
            LOGGER.info(s);
        }
        return chain.filter(exchange).then(Mono.fromRunnable(() -> {
            var finalTime = LocalTime.now();
            LOGGER.info("Tiempo Total: -> {}", Duration.between(initialTime, finalTime).toMillis());
            LOGGER.info("Global Post Filter executed");
        }));
    }

    @Override
    public int getOrder() {
        return 1;
    }
}
