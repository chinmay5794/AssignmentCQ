package com.assignment.cq.config;

import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.springframework.beans.factory.support.SecurityContextProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.Optional;

@Configuration
public class AuditConfiguration {

    @Bean
    public AuditorAware<String> createAuditorProvider() {
        return new SecurityAuditor();
    }

    public static class SecurityAuditor implements AuditorAware<String> {
        @Override
        public Optional<String> getCurrentAuditor() {
            return Optional.empty();
        }
    }
}
