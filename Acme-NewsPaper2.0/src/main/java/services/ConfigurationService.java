package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ConfigurationRepository;
import domain.Configuration;

@Service
@Transactional
public class ConfigurationService {

    // Managed repository -----------------------------------------------------

    @Autowired
    private ConfigurationRepository	configurationRepository;

    // Supporting services ----------------------------------------------------

    @Autowired
    private ActorService actorService;

    // Constructors -----------------------------------------------------------

    public ConfigurationService() {
        super();
    }

    // Simple CRUD methods ----------------------------------------------------

    public Configuration create() {
        Assert.isTrue(actorService.isAdministrator());
        Configuration configuration = new Configuration();
        return configuration;
    }

    public Collection<Configuration> findAll() {
        return this.configurationRepository.findAll();
    }

    public Configuration findOne(final int id) {
        return this.configurationRepository.findOne(id);
    }

    public Configuration findOneToEdit(final int id) {
        Assert.isTrue(actorService.isAdministrator());

        return this.configurationRepository.findOne(id);
    }


    public Configuration save(final Configuration configuration) {
        Assert.notNull(configuration);
        return this.configurationRepository.save(configuration);
    }

    public void delete(final Configuration configuration) {
        this.configurationRepository.delete(configuration);
    }

    // Other business methods -------------------------------------------------

    public void flush() {
        configurationRepository.flush();
    }

    public Configuration getCS() {
        Collection<Configuration> configurationSystems;

        configurationSystems = this.findAll();
        return configurationSystems.iterator().next();
    }


}

