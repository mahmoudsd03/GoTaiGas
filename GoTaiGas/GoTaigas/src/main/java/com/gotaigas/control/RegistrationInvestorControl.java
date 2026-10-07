package com.gotaigas.control;

import java.math.BigDecimal;

import com.gotaigas.control.exception.DatabaseUserException;
import com.gotaigas.repository.InvestorRepository;
import com.gotaigas.repository.UserRepository;
import com.gotaigas.control.factories.InvestorFactory;
import com.gotaigas.dtos.RegistrationInvestorDTO;
import com.gotaigas.dtos.RegistrationDTO;
import com.gotaigas.entities.Investor;
import com.gotaigas.entities.User;
import com.gotaigas.util.Utils;
import org.springframework.stereotype.Component;

@Component
public class RegistrationInvestorControl extends RegistrationControl {

    private final InvestorRepository investorRepo;

    public RegistrationInvestorControl (InvestorRepository investorRepo, UserRepository repository) {
        super(repository);
        this.investorRepo = investorRepo;
    }

    @Override
	public RegistrationResult register( RegistrationDTO dto ) throws DatabaseUserException {
        RegistrationResult result = checkConstraints(dto);

        if (result.getResult()) {
            User user = registerUser(dto);
            Investor investor = InvestorFactory.createInvestor((RegistrationInvestorDTO) dto, user);
            investorRepo.save(investor);
        }
        return result;
    }

    @Override
    public RegistrationResult update (RegistrationDTO dto, int id) throws DatabaseUserException {
        RegistrationResult result = checkConstraints(dto);

        if (result.getReason().equals(RegistrationResult.USER_EXISTS_ALREADY)) {
            result = super.update(dto, id);
            Investor investor = investorRepo.findByUserId(id).get();
            if (investor == null) {
                result.setResult(false);
                result.setReason("User does not exist");
            }else{
                RegistrationInvestorDTO invDTO = (RegistrationInvestorDTO) dto;
                investor.setFirma(invDTO.getFirma());
                investor.setInvestmentFokus(invDTO.getInvestmentFokus());
                investor.setBudget(new BigDecimal(invDTO.getBudget()));

                investorRepo.save(investor);
            }
        }

        return result;
    }

    @Override
    protected RegistrationResult checkConstraints(RegistrationDTO dto) throws DatabaseUserException {
        RegistrationResult result = new RegistrationResult();
        result.setResult(true);
        if(!Utils.isBigDecimal(((RegistrationInvestorDTO) dto).getBudget())) {
            result.setReason(RegistrationResult.SHOULD_BE_A_NUMBER);
            result.setResult(false);
        }
        if (result.getResult()) {
            result = super.checkConstraints(dto);
        }
        return result;
    }
}