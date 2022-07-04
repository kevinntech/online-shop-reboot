package me.kevinntech.onlineshop.global.config;

import me.kevinntech.onlineshop.domain.auth.CurrentUserArgumentResolver;
import me.kevinntech.onlineshop.domain.auth.SignInCheckInterceptor;
import org.springframework.boot.autoconfigure.security.StaticResourceLocation;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        List<String> staticResourcesPath = Arrays.stream(StaticResourceLocation.values())
                .flatMap(StaticResourceLocation::getPatterns)
                .collect(Collectors.toList());
        staticResourcesPath.add("/node_modules/**");

        List<String> viewPath = Arrays.asList("/", "/sign-in", "/sign-up", "/logout", "/error");
        List<String> apiV1Path = Arrays.asList("/api/v1/auth/validate-nickname", "/api/v1/auth/validate-email");

        registry.addInterceptor(new SignInCheckInterceptor())
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns(viewPath)
                .excludePathPatterns(apiV1Path)
                .excludePathPatterns(staticResourcesPath);
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new CurrentUserArgumentResolver());
    }
}
