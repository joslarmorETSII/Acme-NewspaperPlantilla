package converters;

import domain.Agent;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
@Transactional
public class AgentToStringConverter implements Converter<Agent, String> {

    @Override
    public String convert(Agent agent) {

        String result;
        if(agent == null){
            result = null;
        }else{
            result = String.valueOf(agent.getId());
        }
        return result;
    }
}
