package converters;

import domain.Note;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import javax.transaction.Transactional;

@Component
@Transactional
public class NoteToStringConverter implements Converter<Note, String> {

    @Override
    public String convert(Note entity) {

        String result;
        if(entity == null){
            result = null;
        }else{
            result = String.valueOf(entity.getId());
        }
        return result;
    }
}
