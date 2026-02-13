package AVilchis.ProgramacionNCapasNoviembre25.Configurations;

import AVilchis.ProgramacionNCapasNoviembre25.Components.JwtInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ClientConfiguration {
    
    @Bean
    public RestTemplate restTemplate(){
        RestTemplate restTemplate = new RestTemplate();
        
        restTemplate.getInterceptors().add(new JwtInterceptor());
        return restTemplate;
    }
    
    
}