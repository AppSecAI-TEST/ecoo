package ecoo.log.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * @author Justin Rundle
 * @since February 2017
 */
@Aspect
@Component
public class ProfileExecutionAspect {

    /**
     * Logs before and after operations.
     *
     * @param pjp {@link ProceedingJoinPoint}
     * @return The returned value of the advised method.
     * @throws Throwable Any exception thrown in the advised method.
     */
    @SuppressWarnings("ThrowFromFinallyBlock")
    @Around("@annotation(ProfileExecution)")
    public Object logAroundPointCutsAdvice(final ProceedingJoinPoint pjp) throws Throwable {
        final Logger logger = LoggerFactory.getLogger(pjp.getTarget().getClass());
        final Class<?> aClass = pjp.getTarget().getClass();
        final String methodName = pjp.getSignature().toShortString();

        final StopWatch stopWatch = new StopWatch(methodName);
        stopWatch.start();
        try {
            return pjp.proceed();
        } finally {
            if (stopWatch.isRunning()) stopWatch.stop();

            final long duration = stopWatch.getTotalTimeMillis();
            final MDC.MDCCloseable executionClassName = MDC.putCloseable("executionClassName", aClass.getSimpleName());
            final MDC.MDCCloseable executionMethodName = MDC.putCloseable("executionMethodName", methodName);
            final MDC.MDCCloseable executionDuration = MDC.putCloseable("executionDuration", String.valueOf(duration));

            try {
                logger.info(stopWatch.shortSummary());
                if (logger.isTraceEnabled()) {
                    final String arguments = String.format("{%s}", appendArguments(pjp));
                    logger.info(pjp.getSignature().toShortString() + arguments +
                            " in " + duration + "ms");
                }

            } finally {
                try {
                    executionClassName.close();
                } catch (final Exception e) {
                    logger.warn(e.getMessage(), e);
                }
                try {
                    executionMethodName.close();
                } catch (final Exception e) {
                    logger.warn(e.getMessage(), e);
                }
                try {
                    executionDuration.close();
                } catch (final Exception e) {
                    logger.warn(e.getMessage(), e);
                }
            }
        }
    }

    /**
     * @param pjp The {@link ProceedingJoinPoint} containing the argument information.
     * @return The {@link String} of arguments.
     */
    private String appendArguments(final ProceedingJoinPoint pjp) {
        final List<Object> args = Arrays.asList(pjp.getArgs());
        if (args.isEmpty()) {
            return "{}";
        }

        final Iterator<Object> objectIterator = args.iterator();
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append('{');
        for (; ; ) {
            final Object next = objectIterator.next();
            if (next == null) {
                stringBuilder.append("null");
            } else {
                stringBuilder.append(next.toString());
            }

            if (!objectIterator.hasNext()) {
                return stringBuilder.append('}').toString();
            }
            stringBuilder.append(", ");
        }
    }
}