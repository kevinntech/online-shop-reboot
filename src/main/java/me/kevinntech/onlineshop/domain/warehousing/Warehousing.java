package me.kevinntech.onlineshop.domain.warehousing;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@EqualsAndHashCode(of = "id")
@SequenceGenerator(
        name = "WAREHOUSING_SEQ_GENERATOR",
        sequenceName = "WAREHOUSING_SEQ"
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Warehousing {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "WAREHOUSING_SEQ_GENERATOR"
    )
    @Column(name = "WAREHOUSING_ID")
    private Long id;

    @Column(name = "WAREHOUSING_PRICE")
    private long price;

    @Column(name = "WAREHOUSING_QUANTITY")
    private long quantity;

}