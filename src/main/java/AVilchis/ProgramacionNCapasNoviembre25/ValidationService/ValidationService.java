package AVilchis.ProgramacionNCapasNoviembre25.ValidationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.validation.Validator;

@Service
//Service contiene la lógica de negocio
public class ValidationService {
    @Autowired
    private Validator validator; //Porviene de Spring

    public BindingResult validateObjects(Object target){
        DataBinder dataBinder = new DataBinder(target);
        dataBinder.setValidator((org.springframework.validation.Validator) validator);
        dataBinder.validate();
        
        return dataBinder.getBindingResult();
    }
    
    
}