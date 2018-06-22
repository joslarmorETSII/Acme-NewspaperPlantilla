package converters;

import domain.Note;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import repositories.NoteRepository;

import javax.transaction.Transactional;

@Component
@Transactional
public class StringToNoteConverter implements Converter<String, Note> {

    @Autowired
    private NoteRepository noteRepository;

    @Override
    public Note convert(String text) {
        Note result;
        int id;

        try{
            if(StringUtils.isEmpty(text)){
                result = null;
            }else{
                id = Integer.valueOf(text);
                result = noteRepository.findOne(id);
            }
        }catch(Throwable oops){
            throw new IllegalArgumentException(oops);
        }
        return result;
    }
}
