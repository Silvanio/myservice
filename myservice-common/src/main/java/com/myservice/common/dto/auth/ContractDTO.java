package com.myservice.common.dto.auth;

import com.myservice.common.dto.common.MyDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Setter
@Getter
public class ContractDTO extends MyDTO {
    private Integer countUser;
    private CompanyDTO company;
    private Date initialDate;
    private Date finalDate;
    private List<AppModuleDTO> appModules;
}
