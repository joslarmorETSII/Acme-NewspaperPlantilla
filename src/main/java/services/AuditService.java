
package services;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Random;

import domain.Audit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;


import domain.Administrator;

import domain.NewsPaper;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import repositories.AuditRepository;
import security.Authority;

@Service
@Transactional
public class AuditService {

    // Managed repository -----------------------------------------------------

    @Autowired
    private AuditRepository auditRepository;

    // Supporting services ----------------------------------------------------

    @Autowired
    private AdministratorService	administratorService;


    // Constructors -----------------------------------------------------------

    public AuditService() {
        super();
    }

    // Simple CRUD methods ----------------------------------------------------

    public Audit create() {
        Audit audit = null;

        final Administrator administrator = this.administratorService.findByPrincipal();
        audit = new Audit();
        String ticker= generateTicker();
        audit.setTicker(ticker);
        audit.setAdministrator(administrator);

        return audit;
    }

    public Collection<Audit> findAll() {
        return this.auditRepository.findAll();
    }

    public Audit findOne(final int id) {

        return this.auditRepository.findOne(id);
    }

    public Audit save(final Audit audit) {

        Audit saved = null;

        Assert.notNull(audit);
        this.checkByPrincipal(audit);
        saved = this.auditRepository.save(audit);

        return saved;
    }

    public void delete(final Audit audit) {
        Assert.isTrue(checkByPrincipal(audit));
        this.auditRepository.delete(audit);
    }

    public void deleteAll(Collection<Audit> audits){
        auditRepository.delete(audits);
    }

    public Audit findOneToEdit(final Integer auditId) {

        final Audit audit = this.auditRepository.findOne(auditId);
        Assert.isTrue(this.checkByPrincipal(audit));
        return audit;

    }

    // Ancillary methods

    public String generateTicker() {
        String letter="";
        String result;
        Audit audit;
        final Random random = new Random();
        final String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghigklmnopqrst_0123456789";

        while (true) {
            String string=new SimpleDateFormat("ddMMyy").format(new Date());
            String dd= string.substring(0,2);
            String mm=string.substring(2,4);
            String yy=string.substring(4,6);
            final int digit5 = random.nextInt(90000) + 10000;

            for (int i = 0; i < 2; i++) {
                final int index = (int) (random.nextFloat() * letters.length());
                letter+= letters.charAt(index);
            }
            result=yy+":"+letter+":"+mm+":"+digit5+":"+dd;
            audit = this.auditRepository.findByTicker(result);
            if (audit == null)
                break;
        }

        return result;
    }


   /* public boolean checkByPrincipal(Audit audit){
        Boolean res= false;
        Administrator administrator = administratorService.findByPrincipal();
        if(administrator!=null) {
            Collection<Authority> authorities = administrator.getUserAccount().getAuthorities();
            String authority = authorities.toArray()[0].toString();
            res = authority.equals("ADMINISTRATOR");
        }
        return res;

    }*/

   public boolean checkByPrincipal(Audit audit) {
       Boolean res = false;
       Administrator administrator = administratorService.findByPrincipal();
       if (administrator.equals(audit.getAdministrator())) {
           res = true;
       }
       return res;
   }

    public Collection<Audit> AuditForDisplay(int newsPaperId){
        return auditRepository.AuditForDisplay(newsPaperId);
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
            codigos[0] = "audit.moment.invalid";
            error = new FieldError("audit", "moment", moment, false, codigos, null, "Must be in the future");
            binding.addError(error);
        }
        return result;
    }

    public void flush() {
        auditRepository.flush();
    }
}

