package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;

public class OrderServiceImpl implements OrderService {
    /* 추상(인터페이스)의존: DiscountPolicy
     구체(구현) 클래스: FixDiscountPolicy, RateDiscountPolicy
     1. 현재 코드의 문제점은 인터페이스인 DiscountPolicy뿐 만 아니라 구현체인 FixDiscountPolicy도 함께 의존하고 있다. -> DIP 위반
     2. FixDiscountPolicy를 RateDiscountPolicy로 변경하는 순간 OrderServiceImpl의 소스코드도 함께 변경해야 한다. -> OCP 위반 */

    /* private final DiscountPolicy discountPolicy = new FixDiscountPolicy();
    private final DiscountPolicy discountPolicy = new RateDiscountPolicy(); */

    private final MemberRepository memberRepository;
    private final DiscountPolicy discountPolicy;  // 인터페이스에만 의존

    public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        // 구현체가 없기때문에 실행시키면 NPE가 발생한다.
        // 이를 해결하기 위해서는 누군가가 OrderServiceImpl에 DiscountPolicy의 구현체를 대신 생성하고 주입해줘야 한다.
        int discountPrice = discountPolicy.discount(member, itemPrice);

        return new Order(memberId, itemName, itemPrice, discountPrice);
    }
}
