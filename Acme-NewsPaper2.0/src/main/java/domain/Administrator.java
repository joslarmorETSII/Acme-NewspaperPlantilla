package domain;


import javax.persistence.*;

@Entity
@Access(AccessType.PROPERTY)
public class Administrator extends Actor {

    // Constructors

    public Administrator() {
        super();
    }

}
