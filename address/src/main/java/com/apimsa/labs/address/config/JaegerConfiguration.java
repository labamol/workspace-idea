package com.apimsa.labs.address.config;

import org.springframework.context.annotation.Bean;

import io.jaegertracing.Configuration;
import io.jaegertracing.Configuration.*;
import io.jaegertracing.internal.samplers.ConstSampler;
import io.jaegertracing.internal.samplers.ProbabilisticSampler;

@org.springframework.context.annotation.Configuration
public class JaegerConfiguration {

    @Bean
    public io.opentracing.Tracer jaegerTracer() {

    	    SamplerConfiguration samplerConfig = SamplerConfiguration.fromEnv()
    	            .withType(ConstSampler.TYPE)
    	            .withParam(1);
    	 
    	    ReporterConfiguration reporterConfig = ReporterConfiguration.fromEnv()
    	            .withLogSpans(true);
    	 
    	    Configuration config = new Configuration("address-springboot")
    	            .withSampler(samplerConfig)
    	            .withReporter(reporterConfig);
    	 
    	    return config.getTracer();
    }
}
