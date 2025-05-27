package com.cryptotracker.entity;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;




@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class PortfolioLossAlert {
	@Id
	@GeneratedValue
	private Long id;
	private Long userId;
	private double lossThresholdpercent;
	private String status = "PENDING";
	private LocalDateTime triggeredAt;      
	
				
	

}
