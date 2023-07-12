package jpabook.jpashop.domain;

import lombok.Getter;

import javax.persistence.Embeddable;

@Embeddable
@Getter  //Setter없음
public class Address {
    private String city;
    private String street;
    private String zipcode;
    protected Address() {  //엔티티나 임베디드 타입은 기본 생성자를 public or protected로 설정해야 한다. 
    }
    public Address(String city, String street, String zipcode) {   //생성자에서 값을 모두 초기화
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}
