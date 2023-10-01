package laundry.daeseda.entity.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "address")

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressEntity {

    @Id
    @Column(name = "address_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long addressId;

    @Column
    private String addressName;

    @Column
    private String addressDetail;

    @Column
    private String addressZipcode;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity userId;
}
