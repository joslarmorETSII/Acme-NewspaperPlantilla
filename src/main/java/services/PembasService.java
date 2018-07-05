
package services;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Random;

import domain.Pembas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;


import domain.Administrator;

import domain.NewsPaper;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import repositories.PembasRepository;
import security.Authority;

@Service
@Transactional
public class PembasService {

    // Managed repository -----------------------------------------------------

    @Autowired
    private PembasRepository pembasRepository;

    // Supporting services ----------------------------------------------------

    @Autowired
    private AdministratorService	administratorService;


    // Constructors -----------------------------------------------------------

    public PembasService() {
        super();
    }

    // Simple CRUD methods ----------------------------------------------------

    public Pembas create() {
        Pembas pembas = null;

        final Administrator administrator = this.administratorService.findByPrincipal();
        pembas = new Pembas();
        String code= generateCode();
        pembas.setCode(code);
        pembas.setAdministrator(administrator);

        return pembas;
    }

    public Collection<Pembas> findAll() {
        return this.pembasRepository.findAll();
    }

    public Pembas findOne(final int id) {

        return this.pembasRepository.findOne(id);
    }

    public Pembas save(final Pembas pembas) {

        Pembas saved = null;

        Assert.notNull(pembas);
        this.checkByPrincipal(pembas);
        saved = this.pembasRepository.save(pembas);

        return saved;
    }

    public void delete(final Pembas pembas) {
        Assert.isTrue(checkByPrincipal(pembas));
        this.pembasRepository.delete(pembas);
    }

    public void deleteAll(Collection<Pembas> pembass){
        pembasRepository.delete(pembass);
    }

    public Pembas findOneToEdit(final Integer pembasId) {

        final Pembas pembas = this.pembasRepository.findOne(pembasId);
        Assert.isTrue(this.checkByPrincipal(pembas));
        Assert.isTrue(!pembas.getFinalMode());
        return pembas;

    }

    // Ancillary methods

    public String generateCode() {
        String letter="";
        String result;
        Pembas pembas;
        final Random random = new Random();
        //final String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghigklmnopqrst_0123456789";

        while (true) {
            String string=new SimpleDateFormat("ddMMyy").format(new Date());
            String dd= string.substring(0,2);
            String mm=string.substring(2,4);
            String yy=string.substring(4,6);
            final int digit6 = random.nextInt(900000) + 100000;

           // for (int i = 0; i < 2; i++) {
              //  final int index = (int) (random.nextFloat() * letters.length());
               // letter+= letters.charAt(index);
           // }
            result=digit6+":"+yy+mm+dd;
            pembas = this.pembasRepository.findByCode(result);
            if (pembas == null)
                break;
        }

        return result;
    }


   /* public boolean checkByPrincipal(Pembas pembas){
        Boolean res= false;
        Administrator administrator = administratorService.findByPrincipal();
        if(administrator!=null) {
            Collection<Authority> authorities = administrator.getUserAccount().getAuthorities();
            String authority = authorities.toArray()[0].toString();
            res = authority.equals("ADMINISTRATOR");
        }
        return res;

    }*/

   public boolean checkByPrincipal(Pembas pembas) {
       Boolean res = false;
       Administrator administrator = administratorService.findByPrincipal();
       if (administrator.equals(pembas.getAdministrator())) {
           res = true;
       }
       return res;
   }

    public Collection<Pembas> PembasForDisplay(int newsPaperId){
        return pembasRepository.PembasForDisplay(newsPaperId);
    }

    public boolean checkMoment(Date moment, BindingResult binding) {
        FieldError error;
        String[] codigos;
        Date date = new Date();
        boolean result;

        if (moment != null)
            result = moment.after(date);
        else
            result = true;
        if (!result) {
            codigos = new String[1];
            codigos[0] = "pembas.moment.invalid";
            error = new FieldError("pembas", "moment", moment, false, codigos, null, "Must be in the future");
            binding.addError(error);
        }
        return result;
    }

    public void flush() {
        pembasRepository.flush();
    }
}

