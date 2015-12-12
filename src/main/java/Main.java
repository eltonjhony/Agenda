import java.util.Date;

import javax.persistence.EntityManager;
import com.fama.agenda.util.jpa.EntityManagerProducer;

public class Main {

	public static void main(String args[]) {

		EntityManagerProducer factory = new EntityManagerProducer();
		EntityManager manager = factory.createEntityManager();
		
		
		
		
	}
}
