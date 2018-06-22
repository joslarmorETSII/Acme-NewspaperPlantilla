package services;

import domain.Administrator;
import domain.Note;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import repositories.NoteRepository;

import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Random;

@Service
@Transactional
public class NoteService {
    // Managed repository -----------------------------------------------------

    @Autowired
    private NoteRepository noteRepository;

    // Supporting services ----------------------------------------------------

    @Autowired
    private AdministratorService administratorService;

    @Autowired
    private NewsPaperService newsPaperService;

    // Constructors -----------------------------------------------------------

    public NoteService() {
        super();
    }

    // Simple CRUD methods ----------------------------------------------------

    public Note create(){
        Note res;
        Administrator principal;

        res = new Note();
        principal = administratorService.findByPrincipal();
        res.setAdministrator(principal);
        res.setTicker(generateCode());
        res.setFinalMode(false);

        return res;
    }

    public Note findOne(int id){
        return noteRepository.findOne(id);
    }

    public Collection<Note> findAll(){
        return noteRepository.findAll();
    }

    public void delete(Note note){
        Assert.notNull(note);
        Administrator principal;

        principal=administratorService.findByPrincipal();
        Assert.isTrue(principal.equals(note.getAdministrator()));
        noteRepository.delete(note);
    }

    public Note save(Note note){
        Assert.notNull(note);
        return noteRepository.save(note);
    }

    // Other business methods -------------------------------------------------

    public Note findOneToEdit(int id){
        Note res;
        Administrator principal;

        res = findOne(id);
        Assert.notNull(res);
        principal = administratorService.findByPrincipal();
        Assert.isTrue(principal.equals(res.getAdministrator()),"Your are not the owner");
        Assert.isTrue(!res.getFinalMode(),"Note is on final mode");

        return res;
    }

    private String generateCode() {
        String result="";
        Random random = new Random();
        String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz_0123456789";
        String letter="";
        Note note;
        Random r = new Random();
        Integer in;


        while (true) {
            String string = new SimpleDateFormat("ddMMyy").format(new Date());
            String dd= string.substring(0, 2);
            String mm= string.substring(2, 4);
            String yy= string.substring(4, 6);
            in = r.nextInt(90)+10;

            for (int i = 0; i < 2; i++) {
                int index = (int) (random.nextFloat() * letters.length());
                letter += letters.charAt(index);
            }
            result = yy+"-"+mm+"-"+dd+"-"+letter;
            note = noteRepository.findByTicker(result);
            if (note == null)
                break;
        }
        return result;
    }

}
