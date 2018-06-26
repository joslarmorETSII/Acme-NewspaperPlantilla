package services;

import domain.Administrator;
import domain.Tromem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import repositories.TromemRepository;

import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Random;

@Service
@Transactional
public class TromemService {
    // Managed repository -----------------------------------------------------

    @Autowired
    private TromemRepository tromemRepository;

    // Supporting services ----------------------------------------------------

    @Autowired
    private AdministratorService administratorService;

    @Autowired
    private NewsPaperService newsPaperService;

    // Constructors -----------------------------------------------------------

    public TromemService() {
        super();
    }

    // Simple CRUD methods ----------------------------------------------------

    public Tromem create(){
        Tromem res;
        Administrator principal;

        res = new Tromem();
        principal = administratorService.findByPrincipal();
        res.setAdministrator(principal);
        res.setTicker(generateCode());
        res.setFinalMode(false);

        return res;
    }

    public Tromem findOne(int id){
        return tromemRepository.findOne(id);
    }

    public Collection<Tromem> findAll(){
        return tromemRepository.findAll();
    }

    public void delete(Tromem tromem){
        Assert.notNull(tromem);
        Administrator principal;

        principal=administratorService.findByPrincipal();
        Assert.isTrue(principal.equals(tromem.getAdministrator()),"Your are not the owner");
        tromemRepository.delete(tromem);
    }

    public Tromem save(Tromem tromem){
        Assert.notNull(tromem);
        return tromemRepository.save(tromem);
    }

    // Other business methods -------------------------------------------------

    public Tromem findOneToEdit(int id){
        Tromem res;
        Administrator principal;

        res = findOne(id);
        Assert.notNull(res);
        principal = administratorService.findByPrincipal();
        Assert.isTrue(principal.equals(res.getAdministrator()),"Your are not the owner");
        Assert.isTrue(!res.getFinalMode(),"Tromem is on final mode");

        return res;
    }

    private String generateCode() {
        String result="";
        Random random = new Random();
        String numbersSource = "0123456789";
        String numbers="";
        Tromem tromem;
        Random r = new Random();
        //Integer in;


        while (true) {
            String string = new SimpleDateFormat("ddMMyy").format(new Date());
            String dd= string.substring(0, 2);
            String mm= string.substring(2, 4);
            String yy= string.substring(4, 6);
            //in = r.nextInt(90)+10;

            for (int i = 0; i < 6; i++) {
                int index = (int) (random.nextFloat() * numbersSource.length());
                numbers += numbersSource.charAt(index);
            }
            result =  numbers+":"+yy+":"+mm+":"+dd;
            tromem = tromemRepository.findByTicker(result);
            if (tromem == null)
                break;
        }
        return result;
    }

    public Collection<Tromem> findTromemsToDisplay(int newsPaperId) {
        return tromemRepository.findTromemsToDisplay(newsPaperId);
    }

    public void flush() {
        tromemRepository.flush();
    }

    public void deleteAll(Collection<Tromem> tromems) {
        tromemRepository.delete(tromems);
    }

    public void checkTitle(Tromem tromem,BindingResult binding) {
            FieldError error;
            String[] codigos;
            boolean result;

            if (tromem.getTitle() !=null)
                result = tromem.getTitle().length()>100;
            else
                result = false;

            if (result) {
                codigos = new String[1];
                codigos[0] = "tromem.title.tooLong";
                error = new FieldError("tromem", "title", tromem.getTitle(), false, codigos, null, "Max 100 characters");
                binding.addError(error);
            }

        }

    public void checkDescription(Tromem tromem, BindingResult binding) {
        FieldError error;
        String[] codigos;
        boolean result;
        String description = tromem.getDescription();

        if (tromem.getDescription() !=null)
            result = description.length()>250;
        else
            result = false;

        if (result) {
            codigos = new String[1];
            codigos[0] = "tromem.description.tooLong";
            error = new FieldError("tromem", "description", description, false, codigos, null, "Max 250 characters");
            binding.addError(error);
        }


    }
}
