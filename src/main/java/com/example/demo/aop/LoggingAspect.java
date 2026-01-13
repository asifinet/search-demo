package com.example.demo.aop;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import java.time.Duration;
import java.time.Instant;
import java.util.Optional;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.*;

@Aspect
@Component
public class LoggingAspect {
  private static final Logger log = LoggerFactory.getLogger(LoggingAspect.class);
  private static final int MAX_BODY_CHARS = 2000;

  private final ObjectMapper objectMapper = new ObjectMapper();

  @Around("@within(com.example.demo.aop.Loggable) || @annotation(com.example.demo.aop.Loggable)")
  public Object logAround(ProceedingJoinPoint pjp) throws Throwable {
    Instant start = Instant.now();

    HttpServletRequest request = Optional.ofNullable(RequestContextHolder.getRequestAttributes())
        .filter(ServletRequestAttributes.class::isInstance)
        .map(ServletRequestAttributes.class::cast)
        .map(ServletRequestAttributes::getRequest)
        .orElse(null);

    String http = (request == null) ? "N/A" : request.getMethod() + " " + request.getRequestURI();
    String query = (request == null || request.getQueryString() == null) ? "" : ("?" + request.getQueryString());

    String argsJson = safeJson(pjp.getArgs());

    log.info("REQ  {}{} | handler={}.{} | args={}",
        http, query,
        pjp.getSignature().getDeclaringTypeName(),
        pjp.getSignature().getName(),
        truncate(argsJson));

    Object result = null;
    try {
      result = pjp.proceed();
      return result;
    } finally {
      long ms = Duration.between(start, Instant.now()).toMillis();

      String resJson = safeJson(resultPreview(result));
      log.info("RES  {}{} | {}ms | result={}", http, query, ms, truncate(resJson));
    }
  }

  @SuppressWarnings("preview")
private Object resultPreview(Object result) {
    if (result instanceof ResponseEntity<?> re) {
      return re.getBody();
    }
    return result;
  }

  private String safeJson(Object obj) {
    try {
      return objectMapper.writeValueAsString(obj);
    } catch (Exception e) {
      return String.valueOf(obj);
    }
  }

  private String truncate(String s) {
    if (s == null) return null;
    if (s.length() <= MAX_BODY_CHARS) return s;
    return s.substring(0, MAX_BODY_CHARS) + "...(truncated)";
  }
}
