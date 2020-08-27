package com.myservice.mymarket.service.impl;

import com.myservice.mymarket.model.MarketParam;
import com.myservice.mymarket.repository.MarketParamRepository;
import com.myservice.mymarket.service.MarketParamService;
import com.myservice.common.exceptions.BusinessException;
import com.myservice.common.exceptions.MessageException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class MarketParamServiceImpl implements MarketParamService {

    @Autowired
    MarketParamRepository marketParamRepository;

    @Override
    public void save(List<MarketParam> params) {
        if (params != null && !params.isEmpty()) {
            marketParamRepository.deleteAll();
            for (MarketParam param : params) {
                validate(param);
                marketParamRepository.save(param);
            }
            return;
        }
        throw new BusinessException(MessageException.MSG_GENERAL_ERROR.getMessage(), "PARAMS_NOT_NULL");
    }

    protected void validate(MarketParam entity) {
        final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        final Validator validator = factory.getValidator();
        final Set<ConstraintViolation<MarketParam>> validate = validator.validate(entity);
        if (validate.size() > 0) {
            ConstraintViolation<MarketParam> violation = validate.iterator().next();
            throw new BusinessException(MessageException.MSG_GENERAL_VALIDATE.getMessage(), violation.getMessage());
        }
    }
}
