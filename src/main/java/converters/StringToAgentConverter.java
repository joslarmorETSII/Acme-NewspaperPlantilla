package converters;

import domain.Advertisement;
import domain.Agent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import repositories.AdvertisementRepository;
import repositories.AgentRepository;

import javax.transaction.Transactional;

@Component
@Transactional
public class StringToAgentConverter implements Converter<String, Agent> {

    @Autowired
    AgentRepository agentRepository;

    @Override
    public Agent convert(String text) {
        Agent result;
        int id;

        try{
            if(StringUtils.isEmpty(text)){
                result = null;
            }else{
                id = Integer.valueOf(text);
                result = agentRepository.findOne(id);
            }
        }catch(Throwable oops){
            throw new IllegalArgumentException(oops);
        }
        return result;
    }
}
