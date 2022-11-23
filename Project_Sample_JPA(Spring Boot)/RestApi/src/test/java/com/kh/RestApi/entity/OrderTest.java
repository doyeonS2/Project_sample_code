package com.kh.RestApi.entity;
import com.kh.RestApi.constant.OrderStatus;
import com.kh.RestApi.dao.ItemRepository;
import com.kh.RestApi.dao.MemberRepository;
import com.kh.RestApi.dao.OrderItemRepository;
import com.kh.RestApi.dao.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Slf4j
@TestPropertySource(locations = "classpath:application-test.properties")
@Transactional // 하나의 수행 대상 단위로 묶임
class OrderTest {
    @Autowired // 의존성 주입
    OrderRepository orderRepository; // 주문
    @Autowired
    ItemRepository itemRepository; // 상품
    @Autowired
    MemberRepository memberRepository; // 회원
    @Autowired
    OrderItemRepository orderItemRepository; // 주문하는 상품
    @PersistenceContext
    EntityManager em; // 관리하는 애.. 관리자?
    public Item createItem() { // 호출해서 사용할 메소드 // 개별 아이템 1개
        Item item = new Item();
        item.setItemName("필통");
        item.setPrice(1000);
        item.setItemDetail("필기도구를 넣는 통");
        item.setStockNumber(100);
        item.setRegTime(LocalDateTime.now());
        item.setUpdateTime(LocalDateTime.now());
        return item;
    }
    @Test
    @DisplayName("영속성 전이 테스트") // 하나가 바뀔 때 나머지가 다 바뀌는지를 보는 것 // 영속성 전이를 끄면 디비에 저장안됨
    @Rollback(value = false) // 테스트 결과를 롤백시키지 않음
    public void cascadeTest() {
        Order order = new Order();
        order.setOrderStatus(OrderStatus.ORDER);
        for(int i = 0; i < 3; i++) {
            Item item = this.createItem(); // 아이템 만들기
            itemRepository.save(item); // save -> 인서트 하는 것
            OrderItem orderItem = new OrderItem(); // 주문할 아이템
            orderItem.setItem(item); // 위애서 만들어놓은 테스트 아이템(필통) 주문
            orderItem.setCount(10); // 몇개를 주문할건지?
            orderItem.setOrderPrice(1000);
            orderItem.setOrder(order); // 다대일 관계여서 다른 상품도 주문할 수 있다
            order.getOrderItems().add(orderItem); // 주문 아이템을 생성해서 주문 목록에 추가
        }
        // order 엔티티를 저장하면서 강제로 flush를 호출하여 영속성 컨텍스트에 있는 객체들을 디비에 반영
        orderRepository.saveAndFlush(order); // saveAndFlush : 강제로 flush 작업까지 한다(강제로 디비에 반영까지 한다)
        em.clear(); // 영속성 컨텍스트를 초기화(컨텍스트를 비움)

        Order savedOrder = orderRepository.findById(order.getId()).orElseThrow(EntityNotFoundException::new); // 없는 경우에 true를 해주기 위해서 예외처리??
        // itemOrder 엔티티 3개가 실제로 디비에 저장되었는지를 검사. 예상값과 같으면 결과를 출력
        assertEquals(3, savedOrder.getOrderItems().size());
    }
    public Order createOrder() {
        Order order = new Order();
        for(int i = 0; i < 3; i++) {
            Item item = createItem();
            itemRepository.save(item);
            OrderItem orderItem = new OrderItem();
            orderItem.setItem(item);
            orderItem.setCount(10);
            orderItem.setOrder(order);
            order.getOrderItems().add(orderItem);
        }
        Member member = new Member();
        memberRepository.save(member);

        order.setMember(member);
        orderRepository.save(order);
        return order;
    }
    @Test @DisplayName("고아 객체 제거 테스트")
    public void orphanRemovalTest() {
        Order order = this.createOrder();
        order.getOrderItems().remove(0);
        em.flush();
    }
    @Test
    @DisplayName("지연 로딩 테스트")
    public void lazyLodingTest() {
        Order order = this.createOrder();
        Long orderItemId = order.getOrderItems().get(0).getId();
        em.flush();
        em.clear();
        OrderItem orderItem = orderItemRepository.findById(orderItemId)
                .orElseThrow(EntityNotFoundException::new);
        log.warn("Order class : " + orderItem.getOrder().getClass());
    }
}