package com.example.sessionpostservice.aop

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.aspectj.lang.reflect.MethodSignature
import org.springframework.stereotype.Component
import org.springframework.util.StopWatch


@Aspect
@Component
class Timer(

) {
    // 조인포인트를 어노테이션으로 설정
    @Pointcut("@annotation(com.example.sessionpostservice.aop.ExeTimer)")
    private fun timer() {
    }

    // 메서드 실행 전,후로 시간을 공유해야 하기 때문
    @Around("timer()")
    @Throws(Throwable::class)
    fun assumeExecutionTime(joinPoint: ProceedingJoinPoint) {
        val stopWatch = StopWatch()
        stopWatch.start()
        joinPoint.proceed() // 조인포인트의 메서드 실행
        stopWatch.stop()
        val totalTimeMillis: Long = stopWatch.getTotalTimeMillis()
        val signature = joinPoint.signature as MethodSignature
        val methodName = signature.method.name
        println("실행 메서드: {$methodName}, 실행시간 = {$totalTimeMillis}ms" )
    }
}