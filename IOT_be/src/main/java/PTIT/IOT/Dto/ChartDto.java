package PTIT.IOT.Dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class ChartDto {
    private String id;
    private String color;
    private List<DataDto> data;
}
