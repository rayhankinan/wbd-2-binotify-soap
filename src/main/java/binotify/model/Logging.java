package binotify.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

@Entity
public class Logging {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private int ID;

    @Lob
    @Column(nullable = false)
    private String description;

    @Column(nullable = false, length = 16)
    private String IP;

    @Column(nullable = false)
    private String endpoint;

    @Column(nullable = false)
    private Date requestedAt;
}
