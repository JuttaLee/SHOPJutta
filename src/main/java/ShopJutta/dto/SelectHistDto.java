package ShopJutta.dto;

import ShopJutta.constant.SelectStatus;
import ShopJutta.entity.Select;
import lombok.Getter;
import lombok.Setter;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class SelectHistDto {
    public SelectHistDto(Select select) {
        this.selectId = select.getId();
        this.selectDate = select.getSelectDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        this.selectStatus = select.getSelectStatus();
    }
    private Long selectId;
    private String selectDate;
    private SelectStatus selectStatus;

    private List<SelectItemDto> selectItemDtoList = new ArrayList<>();

    public void addSelectItemDto(SelectItemDto selectItemDto) {
        selectItemDtoList.add(selectItemDto);
    }
}