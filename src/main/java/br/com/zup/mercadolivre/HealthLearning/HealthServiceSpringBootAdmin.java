package br.com.zup.mercadolivre.HealthLearning;

import br.com.zup.mercadolivre.Client.ClienteServeSpringBootAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class HealthServiceSpringBootAdmin implements HealthIndicator {

    @Autowired
    private ClienteServeSpringBootAdmin client;

    @Override
    public Health health() {

        boolean disponivel=client.verficaDisponibilidade().toString().equals("true");
        if(disponivel){
            return Health.status(Status.UP).withDetail("Version","2.4.3").build();
        }

        return Health.status(Status.DOWN).withDetail("Version","2.4.3").build();
    }
}
