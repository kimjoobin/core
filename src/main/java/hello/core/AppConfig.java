package hello.core;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemoryMemberRepository;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/* appConfig가 MemberServiceImpl, OrderServiceImpl을 사용할 것이라고 결정함
*  즉, AppConfig는 DI 컨테이너라고 할 수 있다. */
@Configuration
public class AppConfig {

    /* MemberServiceImpl을 만들고 내가 만든 MemerServiceImpl은 MemoryMemberRepository를 사용할 것이라는 의미
    * 이를 의존관계를 주입한다고 한다. */
    @Bean
    public MemberService memberService() {
        return new MemberServiceImpl(memberRepository());
    }

    @Bean
    public MemoryMemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }

    @Bean
    public OrderService orderService() {
        return new OrderServiceImpl(memberRepository(), discountPolicy());
    }

    @Bean
    public DiscountPolicy discountPolicy() {
//        return new FixDiscountPolicy();
        return new RateDiscountPolicy();
    }
}
