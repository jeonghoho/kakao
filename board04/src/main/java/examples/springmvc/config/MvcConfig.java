package examples.springmvc.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.*;

import java.text.SimpleDateFormat;
import java.util.List;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"examples.springmvc.controller"})
public class MvcConfig implements WebMvcConfigurer {
    private final int MAX_SIZE = 10 * 1024 * 1024;

	// default servlet 핸들러를 설정한다.
	// 원래 서블릿은 / (모든 요청)을 처리하는 default servlet을 제공한다. 스프링에서 설정한 path는 스프링이 처리하고, 스프링이 처리하지 못한 경로에 대한 처리는
	// 디폴트 서블릿에게 전달하여 처리하게 된다.
	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}

	// Spring MVC에서 jsp view 가 위치하는 경로를 설정한다.
	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
		registry.jsp("/WEB-INF/view/", ".jsp");
	}


	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addRedirectViewController("/", "/boards");
	}

	/*
        /webapp/resources 아래의 리소스 파일들을 /resources 경로로 접근할 수 있도록 설정한다. 보통 정적파일을 위치시킨다.
         */
	@Override
	public void addResourceHandlers(final ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
		// webjar 라이브러리들 경로를 지정한다.
		registry
				.addResourceHandler("/webjars/**")
				.addResourceLocations("/webjars/");
	}

	/*
	모든 요청에 대해 CORS를 허용한다. 보안과 관련된 설정으로 특정 도메인에서만 api를 호출하길 원할때는 수정되야 한다.
	 */
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
				.allowedOrigins("*")
				.allowedMethods("OPTIONS", "HEAD", "GET", "PUT", "POST", "DELETE", "PATCH")
				.allowCredentials(true);
	}

	/*
	java객체를 json으로 변환할 때 사용되는 Jackson컨버터에 대한 설정을 한다.
	 */
	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		final Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
		final MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
		final ObjectMapper objectMapper = new ObjectMapper();

		objectMapper.configure(com.fasterxml.jackson.databind.SerializationFeature.
				WRITE_DATES_AS_TIMESTAMPS, false);
		objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
		converter.setObjectMapper(objectMapper);

		builder.serializationInclusion(JsonInclude.Include.NON_NULL);
		builder.serializationInclusion(JsonInclude.Include.NON_EMPTY);

		// Add settings to converter, builder
		converters.add(converter);
		converters.add(new MappingJackson2HttpMessageConverter(builder.build()));
	}

	/*
	file upload를 위해서 MultipartResolver를 Bean으로 추가해야 한다.
	 */
	@Bean
    public MultipartResolver multipartResolver() {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setMaxUploadSize(MAX_SIZE); // 10MB
        multipartResolver.setMaxUploadSizePerFile(MAX_SIZE); // 10MB
        multipartResolver.setMaxInMemorySize(0);
        return multipartResolver;
    }

}
