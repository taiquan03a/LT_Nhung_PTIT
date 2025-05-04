package PTIT.IOT.Model;

import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Device {
    private Long id;
    private String speed;
    private LocalDateTime time;
}
